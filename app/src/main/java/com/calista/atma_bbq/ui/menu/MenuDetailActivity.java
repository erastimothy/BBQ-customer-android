package com.calista.atma_bbq.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.calista.atma_bbq.R;
import com.calista.atma_bbq.api.ApiClient;
import com.calista.atma_bbq.api.ApiService;
import com.calista.atma_bbq.model.Menu;
import com.calista.atma_bbq.model.Pembayaran;
import com.calista.atma_bbq.response.Detail_PesananResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDetailActivity extends AppCompatActivity {
    private ImageView gambar_menu_iv;
    private TextView nama_menu_tv,serving_size_tv,harga_menu_tv,deskripsi_tv;
    private MaterialButton add_pesanan_btn;
    private EditText quantity_edt;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        gambar_menu_iv = findViewById(R.id.gambar_menu_iv);
        nama_menu_tv = findViewById(R.id.nama_menu_tv);
        serving_size_tv = findViewById(R.id.serving_size_tv);
        harga_menu_tv = findViewById(R.id.harga_menu_tv);
        deskripsi_tv = findViewById(R.id.deskripsi_tv);
        add_pesanan_btn = findViewById(R.id.add_pesanan_btn);
        quantity_edt = findViewById(R.id.quantity_edt);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!= null){
            menu = (Menu) bundle.getSerializable("menu");
            nama_menu_tv.setText(menu.getNama_menu());
            serving_size_tv.setText(String.valueOf(menu.getUkuran_penyajian()) + " "+menu.getUnit_bahan() + " /"+menu.getUnit());
            harga_menu_tv.setText("Rp. "+menu.getHarga_menu());
            deskripsi_tv.setText(menu.getDeskripsi());

            Glide.with(getApplicationContext())
                    .load(ApiClient.BASE_URL + menu.getGambar_menu())
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(gambar_menu_iv);
        }

        add_pesanan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_pesanan_btn.setEnabled(false);
                if(checkValid()){
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    int qty = Integer.valueOf(String.valueOf(quantity_edt.getText()));
                    float subtotal = (float) (qty * menu.getHarga_menu());
                    String status = "Proses";


                    Call<Detail_PesananResponse> call =apiService.createPesanan(qty,subtotal,status,menu.getId_menu(), Pembayaran.id);
                    call.enqueue(new Callback<Detail_PesananResponse>() {
                        @Override
                        public void onResponse(Call<Detail_PesananResponse> call, Response<Detail_PesananResponse> response) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(Call<Detail_PesananResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            add_pesanan_btn.setEnabled(true);
                        }
                    });

                }else {
                    Toast.makeText(MenuDetailActivity.this, "Tidak Valid !", Toast.LENGTH_SHORT).show();
                    add_pesanan_btn.setEnabled(true);
                }
            }
        });
    }

    private boolean checkValid(){
        int qty = Integer.valueOf(String.valueOf(quantity_edt.getText()));
        if( qty > 0 && qty < menu.getSisa_stok() && Pembayaran.id > 0){
            return true;
        }
        return false;
    }

}
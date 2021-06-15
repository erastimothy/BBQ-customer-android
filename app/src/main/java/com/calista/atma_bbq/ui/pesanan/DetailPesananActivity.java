package com.calista.atma_bbq.ui.pesanan;

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
import com.calista.atma_bbq.model.Detail_Pesanan;
import com.calista.atma_bbq.model.Menu;
import com.calista.atma_bbq.model.Pembayaran;
import com.calista.atma_bbq.response.Detail_PesananResponse;
import com.calista.atma_bbq.ui.menu.MenuDetailActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.DELETE;

public class DetailPesananActivity extends AppCompatActivity {
    private ImageView gambar_menu_iv;
    private TextView nama_menu_tv,harga_menu_tv,deskripsi_tv,status_pesanan_tv;
    private MaterialButton update_btn;
    private TextInputEditText quantity_edt;
    private Detail_Pesanan detail_pesanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        gambar_menu_iv = findViewById(R.id.gambar_menu_iv);
        nama_menu_tv = findViewById(R.id.nama_menu_tv);
        harga_menu_tv = findViewById(R.id.harga_menu_tv);
        deskripsi_tv = findViewById(R.id.deskripsi_tv);
        status_pesanan_tv = findViewById(R.id.status_pesanan_tv);
        update_btn = findViewById(R.id.update_btn);
        quantity_edt = findViewById(R.id.quantity_edt);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!= null){
            detail_pesanan = (Detail_Pesanan) bundle.getSerializable("detail_pesanan");
            nama_menu_tv.setText(detail_pesanan.getMenu().getNama_menu());
            harga_menu_tv.setText("Rp. "+detail_pesanan.getMenu().getHarga_menu());
            deskripsi_tv.setText(detail_pesanan.getMenu().getDeskripsi());
            status_pesanan_tv.setText(detail_pesanan.getStatus_pesanan());
            quantity_edt.setText(String.valueOf(detail_pesanan.getKuantitas()));


            Glide.with(getApplicationContext())
                    .load(ApiClient.BASE_URL + detail_pesanan.getMenu().getGambar_menu())
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(gambar_menu_iv);
        }

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_btn.setEnabled(false);
                if(checkValid()){
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Call<Detail_PesananResponse> edit = apiService.updatePesanan(detail_pesanan.getId_detail(),Integer.valueOf(String.valueOf(quantity_edt.getText())));
                    edit.enqueue(new Callback<Detail_PesananResponse>() {
                        @Override
                        public void onResponse(Call<Detail_PesananResponse> call, Response<Detail_PesananResponse> response) {
                            Toast.makeText(DetailPesananActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(Call<Detail_PesananResponse> call, Throwable t) {
                            Toast.makeText(DetailPesananActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            update_btn.setEnabled(true);
                        }
                    });

                }else {
                    Toast.makeText(DetailPesananActivity.this, "Tidak Valid !", Toast.LENGTH_SHORT).show();
                    update_btn.setEnabled(true);
                }
            }
        });
    }


    private boolean checkValid(){
        int qty = Integer.valueOf(String.valueOf(quantity_edt.getText()));
        if( qty > 0  && Pembayaran.id > 0){
            return true;
        }
        return false;
    }
}
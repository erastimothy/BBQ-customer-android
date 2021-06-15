package com.calista.atma_bbq.ui.pesanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.calista.atma_bbq.R;
import com.calista.atma_bbq.adapter.MenuAdapter;
import com.calista.atma_bbq.adapter.PesananAdapter;
import com.calista.atma_bbq.api.ApiClient;
import com.calista.atma_bbq.api.ApiService;
import com.calista.atma_bbq.model.Detail_Pesanan;
import com.calista.atma_bbq.model.Menu;
import com.calista.atma_bbq.model.Pembayaran;
import com.calista.atma_bbq.response.Detail_PesananResponse;
import com.calista.atma_bbq.response.MenuResponse;
import com.calista.atma_bbq.ui.menu.MenuActivity;
import com.calista.atma_bbq.ui.scanner.ScannerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananActivity extends AppCompatActivity {
    private int id_pembayaran;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Detail_Pesanan> detail_pesanans;
    private PesananAdapter pesananAdapter;
    private MaterialButton btn_add,btn_selesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        id_pembayaran = Pembayaran.id;


        if(id_pembayaran < 1){ // tidak ada id pembayaran
            Toast.makeText(this, "Mohon Scan QR Code Terlebih dahulu ", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), ScannerActivity.class));
        }else{
            loadPesanan(id_pembayaran);
        }


        //set selected nav
        bottomNavigationView.setSelectedItemId(R.id.pesanan_navigation);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        searchView = findViewById(R.id.searchMenu);
        recyclerView = findViewById(R.id.pesanan_rv);
        btn_add = findViewById(R.id.btn_add);
        btn_selesai = findViewById(R.id.btn_selesai);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                finish();
            }
        });

        btn_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pembayaran.id = 0;
                startActivity(new Intent(getApplicationContext(), ScannerActivity.class));
                finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPesanan(id_pembayaran);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_navigation:
                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scanner_navigation:
                        startActivity(new Intent(getApplicationContext(), ScannerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pesanan_navigation:
                        return true;
                }
                return false;
            }
        });
    }

    private void loadPesanan(int id_pembayaran){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Detail_PesananResponse> detail_pesananResponseCall = apiService.getDetailPesanans(id_pembayaran);

        detail_pesananResponseCall.enqueue(new Callback<Detail_PesananResponse>() {
            @Override
            public void onResponse(Call<Detail_PesananResponse> call, Response<Detail_PesananResponse> response) {
                detail_pesanans = response.body().getDetail_pesanans();
                pesananAdapter = new PesananAdapter(getApplicationContext(),detail_pesanans);

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(pesananAdapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        pesananAdapter.getFilter().filter(s);
                        pesananAdapter.notifyDataSetChanged();
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        pesananAdapter.getFilter().filter(s);
                        pesananAdapter.notifyDataSetChanged();
                        return false;
                    }
                });
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<Detail_PesananResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }


    @Override
    protected void onPostResume() {
        loadPesanan(Pembayaran.id);
        super.onPostResume();
    }
}
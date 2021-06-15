package com.calista.atma_bbq.ui.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.calista.atma_bbq.R;
import com.calista.atma_bbq.adapter.MenuAdapter;
import com.calista.atma_bbq.api.ApiClient;
import com.calista.atma_bbq.api.ApiService;
import com.calista.atma_bbq.model.Menu;
import com.calista.atma_bbq.response.MenuResponse;
import com.calista.atma_bbq.ui.pesanan.PesananActivity;
import com.calista.atma_bbq.ui.scanner.ScannerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Menu> menuList;
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        searchView = findViewById(R.id.searchMenu);
        recyclerView = findViewById(R.id.menu_rv);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMenu();
            }
        });

        //load data\
        loadMenu();


        //set selected nav
        bottomNavigationView.setSelectedItemId(R.id.menu_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_navigation:
                        return true;
                    case R.id.scanner_navigation:
                        startActivity(new Intent(getApplicationContext(), ScannerActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pesanan_navigation:
                        startActivity(new Intent(getApplicationContext(), PesananActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    public void loadMenu(){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MenuResponse> menuResponseCall = apiService.getAllMenu();

        menuResponseCall.enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                menuList = response.body().getMenus();
                menuAdapter = new MenuAdapter(getApplicationContext(),menuList);

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(menuAdapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        menuAdapter.getFilter().filter(s);
                        menuAdapter.notifyDataSetChanged();
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        menuAdapter.getFilter().filter(s);
                        menuAdapter.notifyDataSetChanged();
                        return false;
                    }
                });

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
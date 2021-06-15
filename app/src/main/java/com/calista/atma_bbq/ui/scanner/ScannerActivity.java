package com.calista.atma_bbq.ui.scanner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.calista.atma_bbq.R;
import com.calista.atma_bbq.adapter.MenuAdapter;
import com.calista.atma_bbq.api.ApiClient;
import com.calista.atma_bbq.api.ApiService;
import com.calista.atma_bbq.model.Pembayaran;
import com.calista.atma_bbq.ui.menu.MenuActivity;
import com.calista.atma_bbq.ui.pesanan.PesananActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //scanner
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Pembayaran.id = Integer.valueOf(result.getText());
                        startActivity(new Intent(ScannerActivity.this, PesananActivity.class));
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });


        //set selected nav
        bottomNavigationView.setSelectedItemId(R.id.scanner_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_navigation:
                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scanner_navigation:
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

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }


}
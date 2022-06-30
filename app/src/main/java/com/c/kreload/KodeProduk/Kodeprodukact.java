package com.c.kreload.KodeProduk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.c.kreload.R;

public class Kodeprodukact extends AppCompatActivity {

    Button linkProduk;
    EditText tokennid;
    LocalBroadcastManager localBroadcastManager;
    WebView webproduk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kodeprodukact);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Kode Produk WA <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        linkProduk = findViewById(R.id.linkProduk);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        webproduk = findViewById(R.id.webproduk);

        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya

        webproduk.loadUrl("https://bit.ly/3KCntm8");

        linkProduk.setOnClickListener(v -> {

            webproduk.loadUrl("https://bit.ly/3KCntm8");
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
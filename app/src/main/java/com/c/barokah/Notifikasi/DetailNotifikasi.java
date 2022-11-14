package com.c.barokah.Notifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.c.barokah.Helper.utils;
import com.c.barokah.R;

public class DetailNotifikasi extends AppCompatActivity {
    TextView statusND, tanggalND, waktuND, transaksiND, SaldoND, ProdukND, notujuanND, TotalND;
    ImageView iconND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notifikasi);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Detail Notifikasi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        tanggalND = findViewById(R.id.tanggalND);
        waktuND = findViewById(R.id.waktuND);
        transaksiND = findViewById(R.id.transaksiND);
        SaldoND = findViewById(R.id.SaldoND);
        statusND = findViewById(R.id.StatusND);
        iconND = findViewById(R.id.iconND);

        ProdukND = findViewById(R.id.ProdukND);
        notujuanND = findViewById(R.id.notujuanND);

        tanggalND.setText(getIntent().getStringExtra("tanggal"));
        waktuND.setText(getIntent().getStringExtra("waktu"));
        transaksiND.setText(getIntent().getStringExtra("transaksid"));
        SaldoND.setText(utils.ConvertRP(getIntent().getStringExtra("saldo")));
        statusND.setText(getIntent().getStringExtra("status"));
        notujuanND.setText(getIntent().getStringExtra("no"));
        ProdukND.setText(getIntent().getStringExtra("produk"));

        if (getIntent().getStringExtra("status").equals("PENDING")) {
            iconND.setBackground(getDrawable(R.drawable.ic_baseline_access_time_filled_24));
        } else if (getIntent().getStringExtra("status").equals("GAGAL")) {
            iconND.setBackground(getDrawable(R.drawable.failed));
        }

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
package com.c.kreload.Transaksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;

import com.c.kreload.R;
import com.c.kreload.TopUpSaldoku.TrasferBank;

public class BayarViaBank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_via_bank);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pilih Bank <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        LinearLayout linearLayoutbankBCA = findViewById(R.id.LinearBankBCA);
        linearLayoutbankBCA.setOnClickListener(v -> {
            Intent intent = new Intent(BayarViaBank.this, TrasferBank.class);
            intent.putExtra("Title","Transfer Bank BCA");
            intent.putExtra("NoRekening","4970756308");
            startActivity(intent);
        });

        LinearLayout linearLayoutbankMandiri = findViewById(R.id.LinearBankMandiri);
        linearLayoutbankMandiri.setOnClickListener(v -> {
            Intent intent = new Intent(BayarViaBank.this, TrasferBank.class);
            intent.putExtra("Title","Transfer Bank Mandiri");
            intent.putExtra("NoRekening","9000027726034");
            startActivity(intent);
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
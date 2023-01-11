package com.c.faizpay.TopUpSaldoku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.faizpay.R;
import com.c.faizpay.TopUpSaldoku.HistoryTopUp.Activity_HistoryTopUp;
import com.c.faizpay.Transaksi.BayarViaBank;
import com.c.faizpay.Transfer.transferKonter;
import com.c.faizpay.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

public class topup_saldoku_activity extends AppCompatActivity {

    Button bayarsaldoku;
    LinearLayout linsaldo100, linsaldo200, linsaldo500, linsaldo1000,linRiwayat;
    EditText isisaldoku;
    TextView saldosaatini,transfersaldo;

    public static Activity b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_saldoku_activity);

        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Top UP Saldoku <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        b = this;

        linsaldo100 = findViewById(R.id.linsaldo100);
        linsaldo200 = findViewById(R.id.linsaldo200);
        transfersaldo = findViewById(R.id.transfersaldo);
        saldosaatini = findViewById(R.id.saldosaatini);
        isisaldoku = findViewById(R.id.isisaldoku);
        linsaldo500 = findViewById(R.id.linsaldo500);
        linsaldo1000 = findViewById(R.id.linsaldo1000);
        linRiwayat = findViewById(R.id.linRiwayat);

        Preference.getSharedPreference(getApplicationContext());
        bayarsaldoku = findViewById(R.id.bayarsaldoku);
        Intent intent = getIntent();
        String saldosaatin = intent.getStringExtra("saldoku");
        saldosaatini.setText(saldosaatin);

        linRiwayat.setOnClickListener(view -> {
          Intent intent1 = new Intent(topup_saldoku_activity.this, Activity_HistoryTopUp.class);
          startActivity(intent1);
        });

        transfersaldo.setOnClickListener(view -> {
            Intent intent1 = new Intent(topup_saldoku_activity.this, transferKonter.class);
            startActivity(intent1);
        });

        isisaldoku.addTextChangedListener(new NumberChageLive(isisaldoku));

        bayarsaldoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saldoku = isisaldoku.getText().toString();
                if (saldoku.equals("")) {
                    StyleableToast.makeText(getApplicationContext(), "Jumlah Saldo Masi Kosong", Toast.LENGTH_LONG, R.style.mytoast2).show();
                } else {
                    Preference.setSaldoku(getApplicationContext(), isisaldoku.getText().toString());
                    String codebayar = "saldoku";
                    Intent intent = new Intent(getApplicationContext(), BayarViaBank.class);
                    intent.putExtra("saldotipe",codebayar);
                    startActivity(intent);
                }

            }
        });

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();

                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));

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

    public void onclickLin(View view) {
        switch (view.getId()) {

            case R.id.linsaldo100:
                isisaldoku.setText("100000");
                linsaldo100.setBackground(getDrawable(R.drawable.bg_saldoku_choose));
                linsaldo200.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo500.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo1000.setBackground(getDrawable(R.drawable.bg_saldo));
                break;
            case R.id.linsaldo200:
                isisaldoku.setText("200000");
                linsaldo200.setBackground(getDrawable(R.drawable.bg_saldoku_choose));
                linsaldo100.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo500.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo1000.setBackground(getDrawable(R.drawable.bg_saldo));
                break;
            case R.id.linsaldo500:
                isisaldoku.setText("500000");

                linsaldo500.setBackground(getDrawable(R.drawable.bg_saldoku_choose));
                linsaldo100.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo200.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo1000.setBackground(getDrawable(R.drawable.bg_saldo));
                break;
            case R.id.linsaldo1000:
                isisaldoku.setText("1000000");
                linsaldo1000.setBackground(getDrawable(R.drawable.bg_saldoku_choose));
                linsaldo100.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo200.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo500.setBackground(getDrawable(R.drawable.bg_saldo));
        }


    }
}
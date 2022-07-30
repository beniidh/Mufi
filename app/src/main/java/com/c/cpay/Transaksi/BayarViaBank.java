package com.c.cpay.Transaksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.c.cpay.Api.Api;
import com.c.cpay.Helper.RetroClient;
import com.c.cpay.R;
import com.c.cpay.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarViaBank extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterBankOption adapterBankOption;
    ArrayList<mBankOption.Data> data = new ArrayList<>();
    String kodebayar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_via_bank);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pilih Bank <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

         kodebayar = getIntent().getStringExtra("saldotipe");

        recyclerView = findViewById(R.id.reyOptionsBank);

        adapterBankOption = new AdapterBankOption(getApplicationContext(),data,kodebayar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterBankOption);


        getPaymentOptionsBank();

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                    // DO WHATEVER YOU WANT.
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

    private void getPaymentOptionsBank(){
        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<mBankOption> call = api.getBankOptionsd(token,Preference.getServerID(getApplicationContext()));
        call.enqueue(new Callback<mBankOption>() {
            @Override
            public void onResponse(Call<mBankOption> call, Response<mBankOption> response) {
                String code = response.body().getCode();

                if(code.equals("200")){

                    data = response.body().getData();
                    adapterBankOption = new AdapterBankOption(getApplicationContext(),data,kodebayar);
                    recyclerView.setAdapter(adapterBankOption);
                }else {

                    Toast.makeText(getApplicationContext(),response.body().getCode(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<mBankOption> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });






    }


}
package com.c.kreload.Transaksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.c.kreload.Api.Api;
import com.c.kreload.Helper.RetroClient;
import com.c.kreload.R;
import com.c.kreload.TopUpSaldoku.TrasferBank;
import com.c.kreload.menuUtama.PulsaPrabayar.AdapterPulsaPrabayar;
import com.c.kreload.sharePreference.Preference;

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
package com.c.barokah.menuUtama.ListrikPLN;

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

import com.c.barokah.Api.Api;
import com.c.barokah.Helper.RetroClient;
import com.c.barokah.R;
import com.c.barokah.menuUtama.HolderPulsa.ResponGroup;
import com.c.barokah.menuUtama.HolderPulsa.ResponSub;
import com.c.barokah.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class groupPLN extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterGroupPLN adapterHolder;
    ArrayList<ResponGroup.Data> data = new ArrayList<>();
    ArrayList<ResponSub.mData> data2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_holder);

        //get judul
        String judul = "Pilihan Paket";
        recyclerView = findViewById(R.id.reyGroupHolder);

        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>" + judul + "<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Broadcase for kill activity from another activity
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

        adapterHolder = new AdapterGroupPLN(groupPLN.this, data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterHolder);
        String type = getIntent().getStringExtra("type");
        Preference.setType(getApplicationContext(),type);
        getProdukSubb();

    }

    public void getProdukSub(String id) {

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponGroup> call = api.getProdukGroup(token, id);
        call.enqueue(new Callback<ResponGroup>() {
            @Override
            public void onResponse(Call<ResponGroup> call, Response<ResponGroup> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data = response.body().getData();
                    adapterHolder = new AdapterGroupPLN(getApplicationContext(), data);
                    recyclerView.setAdapter(adapterHolder);

                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG);

                }
            }

            @Override
            public void onFailure(Call<ResponGroup> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil,silahkan ulangi", Toast.LENGTH_LONG);
            }
        });
    }


    public void getProdukSubb() {
        String id = getIntent().getStringExtra("id");
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSub> call = api.getSubHolder(token, id);
        call.enqueue(new Callback<ResponSub>() {
            @Override
            public void onResponse(Call<ResponSub> call, Response<ResponSub> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data2 = response.body().getData();

                    getProdukSub(data2.get(0).getId());


                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG);

                }
            }

            @Override
            public void onFailure(Call<ResponSub> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil,silahkan ulangi", Toast.LENGTH_LONG);
            }
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
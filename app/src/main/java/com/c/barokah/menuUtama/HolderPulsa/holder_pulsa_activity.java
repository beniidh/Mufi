package com.c.barokah.menuUtama.HolderPulsa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.barokah.Api.Api;
import com.c.barokah.Helper.RetroClient;
import com.c.barokah.R;
import com.c.barokah.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class holder_pulsa_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterHolder adapterHolder;
    ArrayList<ResponSub.mData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holder_pulsa_activity);
        //get judul
        String judul = getIntent().getStringExtra("name");
        String url = getIntent().getStringExtra("url");

        recyclerView = findViewById(R.id.reyHolderPulsa);

        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>" + judul + "<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        // inflate data

        adapterHolder = new AdapterHolder(holder_pulsa_activity.this, data, holder_pulsa_activity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterHolder);

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

        getProdukSub();

    }

    public void getProdukSub() {
        String id = getIntent().getStringExtra("id");
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSub> call = api.getSubHolder(token, id);
        call.enqueue(new Callback<ResponSub>() {
            @Override
            public void onResponse(Call<ResponSub> call, Response<ResponSub> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data = response.body().getData();
                    adapterHolder = new AdapterHolder(getApplicationContext(), data, holder_pulsa_activity.this);
                    recyclerView.setAdapter(adapterHolder);

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
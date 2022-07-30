package com.c.cpay.Notifikasi.Pesan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpay.Api.Api;
import com.c.cpay.Helper.RetroClient;
import com.c.cpay.R;
import com.c.cpay.sharePreference.Preference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_pesan_activity extends AppCompatActivity {

    TextView judulpesan, Tanggaljudul, isijudul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesan);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Detail Pesan <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        isijudul = findViewById(R.id.isijudul);
        Tanggaljudul = findViewById(R.id.Tanggaljudul);
        judulpesan = findViewById(R.id.judulpesan);
        getPesanNotifbyID(getIntent().getStringExtra("id"));

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

    private void getPesanNotifbyID(String id) {

        String token = "Bearer " + Preference.getToken(getApplication());
        Api api = RetroClient.getApiServices();
        Call<mPesanDetail> call = api.getHistoriPesanNbyID(token, id);
        call.enqueue(new Callback<mPesanDetail>() {
            @Override
            public void onResponse(Call<mPesanDetail> call, Response<mPesanDetail> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    judulpesan.setText(response.body().getData().getTitle());
                    Tanggaljudul.setText(response.body().getData().getCreated_at().substring(0,10).replaceAll("-","/"));
                    isijudul.setText(response.body().getData().getMessage());

                } else {

                    Toast.makeText(getApplication(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<mPesanDetail> call, Throwable t) {
                Toast.makeText(getApplication(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
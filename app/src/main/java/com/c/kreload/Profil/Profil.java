package com.c.kreload.Profil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c.kreload.Api.Api;
import com.c.kreload.Helper.utils;
import com.c.kreload.ResetPIN;
import com.c.kreload.Respon.ResponProfil;
import com.c.kreload.Helper.RetroClient;
import com.c.kreload.R;
import com.c.kreload.SaldoServer.AjukanLimit;
import com.c.kreload.SaldoServer.TopupSaldoServer;
import com.c.kreload.sharePreference.Preference;
import com.c.kreload.TopUpSaldoku.topup_saldoku_activity;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profil extends AppCompatActivity {
    TextView namaprofil, phone,saldokuprofil,saldoserverprofil;
    ImageView iconprofile;
    TextView Pubahpin,Pubahprofil,Ppoint,Pdevice;
    LinearLayout KlikSaldoku,kliksaldoserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Profil <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        namaprofil = findViewById(R.id.namaprofil);
        phone = findViewById(R.id.nomorprofil);
        iconprofile = findViewById(R.id.iconprofile);
        saldokuprofil = findViewById(R.id.saldokuprofil);
        saldoserverprofil = findViewById(R.id.saldoserverprofil);

        Pubahpin = findViewById(R.id.PubahPin);
        KlikSaldoku = findViewById(R.id.KlikSaldoku);
        kliksaldoserver = findViewById(R.id.kliksaldoserver);

        Pubahprofil = findViewById(R.id.PubahProfil);
        Ppoint = findViewById(R.id.Ppoint);
        Pdevice = findViewById(R.id.Pdevice);
        Typeface type = ResourcesCompat.getFont(getApplicationContext(), R.font.abata);
        Pubahpin.setTypeface(type);
        Pubahprofil.setTypeface(type);
        Ppoint.setTypeface(type);
        Pdevice.setTypeface(type);


        getContentProfil();

        KlikSaldoku.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), topup_saldoku_activity.class);
            intent.putExtra("saldoku",saldokuprofil.getText().toString());
            startActivity(intent);
        });

        kliksaldoserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopupSaldoServer.class);
                startActivity(intent);
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

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {
                namaprofil.setText(response.body().getData().getName());
                phone.setText(response.body().getData().getPhone());
                Picasso.get().load(response.body().getData().getAvatar()).into(iconprofile);
                saldokuprofil.setText(utils.ConvertRP(response.body().getData().getWallet().getSaldoku()));
                saldoserverprofil.setText(utils.ConvertRP(response.body().getData().getWallet().getPaylatter()));
                String statuspayletter = response.body().getData().getPaylater_status();

                if (statuspayletter.equals("0")) {
                    saldoserverprofil.setText("0");
                    saldoserverprofil.setOnClickListener(v -> {

                        Intent intent = new Intent(getApplicationContext(), AjukanLimit.class);
//                            intent.putExtra("saldoku", saldoku.getText().toString());
                        startActivity(intent);

                    });


                } else {

                    saldoserverprofil.setOnClickListener(v -> {

                        Intent intent = new Intent(getApplicationContext(), TopupSaldoServer.class);
//                        intent.putExtra("saldoku", saldoku.getText().toString());
                        startActivity(intent);

                    });

                }

            }


            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {

            }
        });
    }

    public void UbahProfil(View view){

        Intent intent = new Intent(Profil.this,ubah_profil.class);
        startActivity(intent);
    }
    public void UbahPin(View view){

        Intent intent = new Intent(Profil.this, ResetPIN.class);
        startActivity(intent);
    }
    public void point(View view){

        Intent intent = new Intent(Profil.this,Point.class);
        startActivity(intent);
    }
    public void Device(View view){

        Intent intent = new Intent(Profil.this,Device.class);
        startActivity(intent);
    }
}




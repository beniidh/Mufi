package com.c.atmpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.c.atmpay.Api.Api;
import com.c.atmpay.Api.Value;
import com.c.atmpay.Helper.RetroClient;
import com.c.atmpay.Model.mOTP;
import com.c.atmpay.sharePreference.Preference;
import com.chaos.view.PinView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otpvery extends AppCompatActivity {

    PinView otpverify;
    Button VerifikasiOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpvery);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Insert OTP <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        otpverify = findViewById(R.id.otpverify);
        VerifikasiOTP = findViewById(R.id.VerifikasiOTP);

        VerifikasiOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otpverify.length() == 6) {

                    verif(otpverify.getText().toString());

                } else {
                    Toast.makeText(getApplicationContext(), "Pin tidak boleh kurang dari 6", Toast.LENGTH_LONG).show();
                }

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

    private void verif(String numberOTP) {

        String id = getIntent().getStringExtra("userid");
        String otp = getIntent().getStringExtra("otp");
        String push = getIntent().getStringExtra("pushid");
        Api api = RetroClient.getApiServices();
        mOTP motp = new mOTP(id, otp, numberOTP, Value.getMacAddress(getApplicationContext()),
                Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), push, Double.parseDouble(Preference.getLang(
                getApplicationContext())), Double.parseDouble(Preference.getLong(getApplicationContext())));
        Call<mOTP> call = api.SetverifyOtp(motp);
        call.enqueue(new Callback<mOTP>() {
            @Override
            public void onResponse(Call<mOTP> call, Response<mOTP> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    Intent home = new Intent(otpvery.this, drawer_activity.class);
                    startActivity(home);
                    String token = response.body().getData().getToken();
                    Preference.getSharedPreference(getApplicationContext());
                    Preference.setToken(getApplicationContext(), token);
                    finish();
                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<mOTP> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
package com.c.barokah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.Toast;

import com.c.barokah.Api.Api;
import com.c.barokah.Api.Value;
import com.c.barokah.Helper.GpsTracker;
import com.c.barokah.Helper.RetroClient;
import com.c.barokah.Helper.utils;
import com.c.barokah.Model.MResestPIN;
import com.c.barokah.Respon.ResponResetPin;
import com.c.barokah.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPIN extends AppCompatActivity {

    PinEditText pinlamareset, pinsatureset, pinduareset;
    Button resetPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_p_i_n);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Reset PIN <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        pinlamareset = findViewById(R.id.pinlamareset);
        pinsatureset = findViewById(R.id.pinsatureset);
        pinduareset = findViewById(R.id.pinduareset);
        resetPIN = findViewById(R.id.resetPIN);

        resetPIN.setOnClickListener(v -> ResetPin(utils.hmacSha(pinlamareset.getText().toString()),utils.hmacSha(pinsatureset.getText().toString()),utils.hmacSha(pinduareset.getText().toString())));

    }

    private void ResetPin(String pinlama, String pinbaru, String pinbaruconfirm) {

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();

        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        MResestPIN mResestPIN = new MResestPIN(pinlama, pinbaru, pinbaruconfirm, Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude());
        Call<ResponResetPin> call = api.resetPIN(token, mResestPIN);
        call.enqueue(new Callback<ResponResetPin>() {
            @Override
            public void onResponse(Call<ResponResetPin> call, Response<ResponResetPin> response) {

                assert response.body() != null;
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    finish();

                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();

                }
            }

            @Override
            public void onFailure(Call<ResponResetPin> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan Internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
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
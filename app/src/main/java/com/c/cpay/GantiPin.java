package com.c.cpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.c.cpay.Api.Api;
import com.c.cpay.Api.Value;
import com.c.cpay.Helper.GpsTracker;
import com.c.cpay.Helper.RetroClient;
import com.c.cpay.Model.mResetPassword;
import com.c.cpay.Respon.ResponResetPassword;
import com.c.cpay.databinding.ActivityGantiPinBinding;
import com.c.cpay.databinding.ActivitySplashActivityBinding;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiPin extends AppCompatActivity {

    private ActivityGantiPinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGantiPinBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Lupa PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        binding.ResetPINGanti.setOnClickListener(v -> {

            if (binding.inputEmailGantiPin.getText().toString().isEmpty()) {
                StyleableToast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            } else {
                resetPassword(binding.inputEmailGantiPin.getText().toString());
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
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        finish();
    }

    public void resetPassword(String email) {
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        Api api = RetroClient.getApiServices();
        mResetPassword mResetPassword = new mResetPassword(email, Value.getIPaddress(),
                Value.getMacAddress(getApplicationContext()),
                Value.getUserAgent(getApplicationContext()),
                gpsTracker.getLongitude(), gpsTracker.getLatitude());

        Call<ResponResetPassword> call = api.resetPassword(mResetPassword);
        call.enqueue(new Callback<ResponResetPassword>() {
            @Override
            public void onResponse(Call<ResponResetPassword> call, Response<ResponResetPassword> response) {
                String respon = response.body().getCode();
                if (respon.equals("200")) {

                    binding.keterangangantipin.setText(response.body().getData().getMessage());
                } else {
                    binding.keterangangantipin.setText(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResponResetPassword> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });


    }
}
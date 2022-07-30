package com.c.cpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.Toast;

import com.c.cpay.Api.Api;
import com.c.cpay.Helper.RetroClient;
import com.c.cpay.Modal.OTPinsert;
import com.c.cpay.Model.MRegisData;
import com.c.cpay.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPsend extends AppCompatActivity {

    Button sendEmail,SendWaOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_psend);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pilih metode OTP <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        sendEmail = findViewById(R.id.sendEmailOtp);
        sendEmail.setOnClickListener(v -> intentOTP());
        SendWaOtp = findViewById(R.id.SendWaOtp);
        SendWaOtp.setOnClickListener(v -> intentOTPWA());
    }

    public void intentOTP() {

        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());
        String user_code = Preference.getKeyUserCode(getBaseContext());
        String phone = Preference.getKeyPhone(getBaseContext());
        String otp_id = Preference.getKeyOtpId(getBaseContext());



        Api api = RetroClient.getApiServices();
        MRegisData mRegisData = new MRegisData(user_id, user_code, phone, otp_id);
        Call<MRegisData> call = api.SendOTP(mRegisData);
        call.enqueue(new Callback<MRegisData>() {
            @Override
            public void onResponse(Call<MRegisData> call, Response<MRegisData> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "OTP Telah dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    Intent otpInsert = new Intent(OTPsend.this, OTPinsert.class);
                    otpInsert.putExtra("user_id", user_id);
                    otpInsert.putExtra("otp_id", otp_id);
                    otpInsert.putExtra("otp","Kode telah dikirim ke Email, silahkan cek Email Anda");
                    Preference.setTrackRegister(getApplicationContext(), "2");
                    startActivity(otpInsert);

                } else {

                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<MRegisData> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            }
        });


    }

    public void intentOTPWA() {

        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());
        String user_code = Preference.getKeyUserCode(getBaseContext());
        String phone = Preference.getKeyPhone(getBaseContext());
        String otp_id = Preference.getKeyOtpId(getBaseContext());



        Api api = RetroClient.getApiServices();
        MRegisData mRegisData = new MRegisData(user_id, user_code, phone, otp_id);
        Call<MRegisData> call = api.SendOTPWA(mRegisData);
        call.enqueue(new Callback<MRegisData>() {
            @Override
            public void onResponse(Call<MRegisData> call, Response<MRegisData> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "OTP Telah dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    Intent otpInsert = new Intent(OTPsend.this, OTPinsert.class);
                    otpInsert.putExtra("user_id", user_id);
                    otpInsert.putExtra("otp_id", otp_id);
                    otpInsert.putExtra("otp","Kode telah dikirim ke WA, silahkan cek WA Anda");
                    Preference.setTrackRegister(getApplicationContext(), "2");
                    startActivity(otpInsert);

                } else {

                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<MRegisData> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

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
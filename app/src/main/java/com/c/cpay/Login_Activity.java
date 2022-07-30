package com.c.cpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.c.cpay.Api.Api;
import com.c.cpay.Helper.GpsTracker;
import com.c.cpay.Helper.RetroClient;
import com.c.cpay.Model.Mphone;
import com.c.cpay.databinding.ActivityLoginBinding;
import com.c.cpay.pinNew.pinnew;
import com.c.cpay.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import info.androidhive.fontawesome.FontDrawable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {


    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        //get informasi lokasi login
        getLocation();

        // set drawable end to editText Login
        FontDrawable drawable = new FontDrawable(this,R.string.userabata,true,false);
        Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.abata);
        drawable.setTypeface(type2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawable.setTextColor(getColor(R.color.black));
        }
        drawable.setTextSize(20);
        binding.numberphone.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);

        // Event Onclick for register activity
        binding.register.setOnClickListener(v -> {
            Intent register = new Intent(Login_Activity.this, Register_activity.class);
            startActivity(register);
        });

        // Event Onclick for login activity
        binding.loginButtn.setOnClickListener(v -> validation(binding.numberphone.getText().toString()));

        binding.savecheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){

                Preference.getSharedPreference(getApplicationContext());
                Preference.setkredentials(getApplicationContext(),binding.numberphone.getText().toString());

            }

        });
    }

    private void validation(String number) {
        if (number.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
        } else {
            Api api = RetroClient.getApiServices();
            Mphone mphone = new Mphone(number);
            Call<Mphone> call = api.ChekPhone(mphone);
            call.enqueue(new Callback<Mphone>() {
                @Override
                public void onResponse(Call<Mphone> call, Response<Mphone> response) {
                    String code = response.body().getCode();
                    if (code.equals("200")){
                        Intent intent = new Intent(Login_Activity.this, pinnew.class);
                        intent.putExtra("number",number);
                        Preference.getSharedPreference(getApplicationContext());
                        Preference.setkredentials(getApplicationContext(),binding.numberphone.getText().toString());
                        startActivity(intent);
                        finish();
                        binding.numberphone.setText("");
                    }else {
                        StyleableToast.makeText(getApplicationContext(), "Nomor belum terdaftar", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Mphone> call, Throwable t) {
                    StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

                }
            });


        }

    }


    public void getLocation() {
        GpsTracker gpsTracker = new GpsTracker(Login_Activity.this);
        if (gpsTracker.canGetLocation()) {

        } else {
            gpsTracker.showSettingsAlert();
        }
    }





}

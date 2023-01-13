package com.c.atmpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.c.atmpay.Api.Api;
import com.c.atmpay.Helper.GpsTracker;
import com.c.atmpay.Helper.LoadingPrimer;
import com.c.atmpay.Helper.RetroClient;
import com.c.atmpay.Model.Mphone;
import com.c.atmpay.databinding.ActivityLoginBinding;
import com.c.atmpay.pinNew.pinnew;
import com.c.atmpay.sharePreference.Preference;
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

        binding.privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.termsfeed.com/live/191ba483-4cf7-493c-94b7-e8dbeaeae818";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // set drawable end to editText Login
        FontDrawable drawable = new FontDrawable(this, R.string.userabata, true, false);
        Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.abata);
        drawable.setTypeface(type2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            drawable.setTextColor(getColor(R.color.black));
        }
        drawable.setTextSize(20);
        binding.numberphone.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

        // Event Onclick for register activity
        binding.register.setOnClickListener(v -> {
            Intent register = new Intent(Login_Activity.this, Register_activity.class);
            startActivity(register);
        });

        // Event Onclick for login activity
        binding.loginButtn.setOnClickListener(v -> validation(binding.numberphone.getText().toString()));

        binding.savecheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                Preference.getSharedPreference(getApplicationContext());
                Preference.setkredentials(getApplicationContext(), binding.numberphone.getText().toString());

            }

        });
    }

    private void validation(String number) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(Login_Activity.this);
        loadingPrimer.startDialogLoadingCancleAble();

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
                    if (code.equals("200")) {
                        Intent intent = new Intent(Login_Activity.this, pinnew.class);
                        intent.putExtra("number", number);
                        Preference.getSharedPreference(getApplicationContext());
                        Preference.setkredentials(getApplicationContext(), binding.numberphone.getText().toString());
                        startActivity(intent);
                        finish();
                        binding.numberphone.setText("");
                    } else {
                        StyleableToast.makeText(getApplicationContext(), "Nomor belum terdaftar", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                    }
                    loadingPrimer.dismissDialog();
                }

                @Override
                public void onFailure(@NonNull Call<Mphone> call, Throwable t) {
                    StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                    loadingPrimer.dismissDialog();
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

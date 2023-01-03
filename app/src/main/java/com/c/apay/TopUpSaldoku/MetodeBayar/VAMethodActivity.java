package com.c.apay.TopUpSaldoku.MetodeBayar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.c.apay.Api.Api;
import com.c.apay.Api.Value;
import com.c.apay.Helper.LoadingPrimer;
import com.c.apay.Helper.RetroClient;
import com.c.apay.Helper.utils;
import com.c.apay.R;
import com.c.apay.TopUpSaldoku.HistoryTopUp.Activity_HistoryTopUp;
import com.c.apay.TopUpSaldoku.ReqSaldoku;
import com.c.apay.TopUpSaldoku.mCancel;
import com.c.apay.databinding.ActivitySplashActivityBinding;
import com.c.apay.databinding.ActivityVamethodBinding;
import com.c.apay.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VAMethodActivity extends AppCompatActivity {

  private   ActivityVamethodBinding binding;
  LoadingPrimer loadingPrimer;
    String primaryid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVamethodBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>"+getIntent().getStringExtra("name")+" <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        binding.JenisVA.setText(getIntent().getStringExtra("name"));

        Picasso.get().load(getIntent().getStringExtra("icon")).into(binding.ImageVA);

        binding.batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(VAMethodActivity.this);
                alertdialog.setTitle("TopUp Saldoku");
                alertdialog.setMessage("Apakah anda yakin ingin Membatalkan ?");
                alertdialog.setPositiveButton("yes", (dialog, which) -> {
                    String token = "Bearer " + Preference.getToken(getApplicationContext());
                    mCancel cancel = new mCancel(getPrimaryid(),"CANCEL",Value.getMacAddress(getApplicationContext()),Value.getIPaddress(),Value.getUserAgent(getApplicationContext()));

                    Api api = RetroClient.getApiServices();
                    Call<mCancel> call = api.CancelTopup(token,cancel);
                    call.enqueue(new Callback<mCancel>() {
                        @Override
                        public void onResponse(Call<mCancel> call, Response<mCancel> response) {
                            if(response.body().getCode().equals("200")){
                                Intent intent = new Intent(VAMethodActivity.this, Activity_HistoryTopUp.class);
                                startActivity(intent);
                                Intent intent2 = new Intent("finish_activity");
                                sendBroadcast(intent2);
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<mCancel> call, Throwable t) {

                        }
                    });
                });

                alertdialog.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();

                });

                AlertDialog alertDialog = alertdialog.create();
                alertDialog.show();
            }
        });

        binding.copas.setOnClickListener(view1 -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", binding.NoVA.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
        });


        loadingPrimer = new LoadingPrimer(VAMethodActivity.this);
        loadingPrimer.startDialogLoadingCancleAble();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ajukanlimitt(Double.valueOf(Preference.getSaldoku(getApplicationContext()).replaceAll(",","")));

            }
        },1500);
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

    private void ajukanlimitt( double amount) {

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        String id = getIntent().getStringExtra("id");

        ReqSaldoku reqSaldoku = new ReqSaldoku(id, Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), amount);

        Call<ReqSaldoku> call = api.AddRequestSaldoku(token, reqSaldoku);
        call.enqueue(new Callback<ReqSaldoku>() {
            @Override
            public void onResponse(Call<ReqSaldoku> call, Response<ReqSaldoku> response) {
                assert response.body() != null;
                String code = response.body().getCode();

                if (code.equals("200")) {
                    loadingPrimer.dismissDialog();
                    setPrimaryid(response.body().getData().getId());
                    binding.NoVA.setText(response.body().getData().getVa_number());
                    binding.admin.setText(utils.ConvertRP(response.body().getData().getVa_admin()));
                    binding.JumlahBayar.setText(utils.ConvertRP(response.body().getData().getAmount()));
                    double total = Double.parseDouble(response.body().getData().getVa_admin())+ Double.parseDouble(response.body().getData().getAmount());

                    binding.total.setText(utils.ConvertRP(String.valueOf(total)));
                    Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_LONG).show();


                } else {
                    loadingPrimer.dismissDialog();
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }

            }

            @Override
            public void onFailure(Call<ReqSaldoku> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            }
        });

    }

    public String getPrimaryid() {
        return primaryid;
    }

    public void setPrimaryid(String primaryid) {
        this.primaryid = primaryid;
    }
}
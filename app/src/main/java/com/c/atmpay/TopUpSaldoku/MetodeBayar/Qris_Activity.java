package com.c.atmpay.TopUpSaldoku.MetodeBayar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.c.atmpay.Api.Api;
import com.c.atmpay.Api.Value;
import com.c.atmpay.Helper.LoadingPrimer;
import com.c.atmpay.Helper.RetroClient;
import com.c.atmpay.Helper.utils;
import com.c.atmpay.R;
import com.c.atmpay.TopUpSaldoku.HistoryTopUp.Activity_HistoryTopUp;
import com.c.atmpay.TopUpSaldoku.ReqSaldoku;
import com.c.atmpay.TopUpSaldoku.mCancel;
import com.c.atmpay.databinding.ActivityQrisBinding;
import com.c.atmpay.databinding.ActivityVamethodBinding;
import com.c.atmpay.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Qris_Activity extends AppCompatActivity {

    private ActivityQrisBinding binding;
    private String url;
    String primaryid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrisBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>QRIS <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        ajukanlimitt(Double.parseDouble(Preference.getSaldoku(getApplicationContext()).replaceAll(",","")));

        binding.swipe.setOnRefreshListener(() -> {
            binding.swipe.setRefreshing(false);
            Picasso.get().load(getUrl()).into(binding.ImageQris);
        });

        binding.batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(Qris_Activity.this);
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
                                Intent intent = new Intent(Qris_Activity.this, Activity_HistoryTopUp.class);
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

        LoadingPrimer loadingPrimer = new LoadingPrimer(Qris_Activity.this);
        loadingPrimer.startDialogLoadingCancleAble();

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
                    setPrimaryid(response.body().getData().getId());
                    setUrl(response.body().getData().getQris_image());
                    Picasso.get().load(response.body().getData().getQris_image()).into(binding.ImageQris);
                    binding.admin.setText(utils.ConvertRP(response.body().getData().getVa_admin()));
                    binding.Nominal.setText(utils.ConvertRP(response.body().getData().getAmount()));
                    binding.exp.setText("Berlaku hingga : "+response.body().getData().getVa_expired().substring(0,11));

                    double total = Double.parseDouble(response.body().getData().getVa_admin())+ Double.parseDouble(response.body().getData().getAmount());

                    binding.total.setText(utils.ConvertRP(String.valueOf(total)));

                    Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_LONG).show();


                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }

                loadingPrimer.dismissDialog();

            }

            @Override
            public void onFailure(Call<ReqSaldoku> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                loadingPrimer.dismissDialog();
            }
        });

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrimaryid() {
        return primaryid;
    }

    public void setPrimaryid(String primaryid) {
        this.primaryid = primaryid;
    }
}
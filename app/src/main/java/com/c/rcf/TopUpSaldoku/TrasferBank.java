package com.c.rcf.TopUpSaldoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.rcf.Api.Api;
import com.c.rcf.Api.Value;
import com.c.rcf.Helper.LoadingPrimer;
import com.c.rcf.Helper.RetroClient;
import com.c.rcf.Helper.utils;
import com.c.rcf.R;
import com.c.rcf.TopUpSaldoku.HistoryTopUp.Activity_HistoryTopUp;
import com.c.rcf.sharePreference.Preference;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrasferBank extends AppCompatActivity implements ModalPinTopUpSaldoku.BottomSheetListeneridUpload {
    TextView saldokubank,titleakunbank,noRekenning,NamaRekening;
    Button oktransaksi, uploadBuktiTBSaldo,batal;
    ImageView SalinTotal,SalinRekening;
    int up = 0;
    String jumlah="";
    String primaryid = "";
    LoadingPrimer loadingPrimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasfer_bank);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pembayaran Saldoku <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

         loadingPrimer = new LoadingPrimer(TrasferBank.this);
        loadingPrimer.startDialogLoadingCancleAble();

        saldokubank = findViewById(R.id.saldokubank);
        uploadBuktiTBSaldo = findViewById(R.id.uploadBuktiTBSaldo);
        oktransaksi = findViewById(R.id.oktransaksi);
        titleakunbank = findViewById(R.id.titleakunbank);
        noRekenning = findViewById(R.id.noRekenning);
        NamaRekening = findViewById(R.id.NamaRekening);
        batal = findViewById(R.id.batal);

        SalinTotal = findViewById(R.id.salinTotal);
        SalinRekening = findViewById(R.id.salinRekening);


        String noRekening = getIntent().getStringExtra("NoRekening");
        String titleakun = getIntent().getStringExtra("Title");
        String namaRekening = getIntent().getStringExtra("NamaRekening");

        SalinTotal.setOnClickListener(view -> {

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", getJumlah());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();

        });

        batal.setOnClickListener(view -> {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(TrasferBank.this);
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
                            Intent intent = new Intent(TrasferBank.this, Activity_HistoryTopUp.class);
                            startActivity(intent);
                            Intent intent2 = new Intent("finish_activity");
                            sendBroadcast(intent2);
                            finish();

                        }else {
                            Toast.makeText(getApplicationContext(),response.body().error,Toast.LENGTH_LONG).show();
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

        });

        SalinRekening.setOnClickListener(view -> {

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", noRekenning.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();

        });

        noRekenning.setText(noRekening);
        titleakunbank.setText(titleakun);
        NamaRekening.setText(namaRekening);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ajukanlimitt(Double.valueOf(Preference.getSaldoku(getApplicationContext()).replaceAll(",","")));

            }
        },1500);


        uploadBuktiTBSaldo.setOnClickListener(v -> {
            String idprimary = getPrimaryid();
            if (idprimary.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Lakukan Pengajuan terlebih dahulu", Toast.LENGTH_SHORT).show();
            } else {

                ImagePicker.with(TrasferBank.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if (resultCode == RESULT_OK) {

                Uri imageUri = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap photo = bitmap;
                    uploadBukti(photo);


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

    }

    @Override
    public void onButtonClickIdUpload(String id) {
        setPrimaryid(id);
        if (!id.isEmpty()) {
            oktransaksi.setText("Pengajuan Berhasil");
        }
    }

    private void uploadBukti(Bitmap photo) {
        Preference.getSharedPreference(getBaseContext());
        final LoadingPrimer loadingPrimerd = new LoadingPrimer(TrasferBank.this);
        loadingPrimerd.startDialogLoading();
        String typee = "proof_saldoku";
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), typee);
        RequestBody primary_id = RequestBody.create(MediaType.parse("text/plain"), getPrimaryid());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getFileDataFromDrawable(photo));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", "image.jpg", requestFile);
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        //creating retrofit object
        Api api = RetroClient.getApiServices();
//        progresktp.setVisibility(View.VISIBLE);
        Call<ResponTopUp> call = api.uploadBuktiBayar(token, body, type, primary_id);
        call.enqueue(new Callback<ResponTopUp>() {


            @Override
            public void onResponse(Call<ResponTopUp> call, Response<ResponTopUp> response) {
                String code = response.body().getCode();
//                progresktp.setVisibility(View.INVISIBLE);
//                foto2 ="1";
                if (code.equals("200")) {
                    Toast.makeText(getApplicationContext(), "Foto Berhasil diupload", Toast.LENGTH_SHORT).show();
                    loadingPrimerd.dismissDialog();
                    Intent intent = new Intent(TrasferBank.this, Activity_HistoryTopUp.class);
                    startActivity(intent);
                    Intent intent2 = new Intent("finish_activity");
                    sendBroadcast(intent2);
                    finish();

//                    uploadKTP.setImageDrawable(getDrawable(R.drawable.check));
                } else {
                    loadingPrimerd.dismissDialog();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ResponTopUp> call, Throwable t) {
//                progresktp.setVisibility(View.GONE);
                StyleableToast.makeText(getApplicationContext(), "Yuk upload lagi,Koneksimu kurang baik", Toast.LENGTH_LONG, R.style.mytoast).show();
                loadingPrimerd.dismissDialog();

            }
        });


    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String getPrimaryid() {
        return primaryid;
    }

    public void setPrimaryid(String primaryid) {
        this.primaryid = primaryid;
    }

    private void ajukanlimitt( double amount) {

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        String id = getIntent().getStringExtra("id");

        ReqSaldoku reqSaldoku = new ReqSaldoku(id,Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), amount);

        Call<ReqSaldoku> call = api.AddRequestSaldoku(token, reqSaldoku);
        call.enqueue(new Callback<ReqSaldoku>() {
            @Override
            public void onResponse(Call<ReqSaldoku> call, Response<ReqSaldoku> response) {
                assert response.body() != null;
                String code = response.body().getCode();

                if (code.equals("200")) {
                    loadingPrimer.dismissDialog();
                    saldokubank.setText(utils.ConvertRP(response.body().getData().getAmount()));
                    setJumlah(response.body().getData().getAmount());
                    setPrimaryid(response.body().getData().getId());

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

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
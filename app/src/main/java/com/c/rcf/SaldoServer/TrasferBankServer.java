package com.c.rcf.SaldoServer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.c.rcf.Api.Api;
import com.c.rcf.Helper.LoadingPrimer;
import com.c.rcf.Helper.RetroClient;
import com.c.rcf.Helper.utils;
import com.c.rcf.R;
import com.c.rcf.TopUpSaldoku.ResponTopUp;
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

public class TrasferBankServer extends AppCompatActivity implements ModalPinUPPServer.BottomSheetListeneridUpload {
    TextView saldokubank,BankTujuan,saldokubankserver,rekeningserver,namaRekeningServer;
    Button oktransaksi, uploadBuktiTBSaldo;
    int up = 0;
    String primaryid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasfer_bank_server);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pembayaran Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        saldokubank = findViewById(R.id.saldokubank);
        BankTujuan = findViewById(R.id.BankTujuan);
        rekeningserver = findViewById(R.id.rekeningserver);
        namaRekeningServer = findViewById(R.id.namaRekeningServer);

        String noRekening = getIntent().getStringExtra("NoRekening");
        String titleakun = getIntent().getStringExtra("Title");
        String namaRekening = getIntent().getStringExtra("NamaRekening");

        rekeningserver.setText(noRekening);
        BankTujuan.setText(titleakun);
        namaRekeningServer.setText(namaRekening);


        uploadBuktiTBSaldo = findViewById(R.id.uploadBuktiTBSaldo);

        uploadBuktiTBSaldo.setOnClickListener(v -> {
            String idprimary = getPrimaryid();
            if(idprimary.isEmpty()){

                Toast.makeText(getApplicationContext(),"Lakukan Pengajuan terlebih dahulu",Toast.LENGTH_SHORT).show();
            }else {

                ImagePicker.with(TrasferBankServer.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }

        });

        saldokubank.setText(utils.ConvertRP(Preference.getSaldoServer(getApplicationContext())));
        oktransaksi = findViewById(R.id.oktransaksi);
        oktransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("kode", "bank");
                ModalPinUPPServer modalPinServer = new ModalPinUPPServer();
                modalPinServer.setArguments(bundle);
                modalPinServer.show(getSupportFragmentManager(), "topupsaldoku");

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
        final LoadingPrimer loadingPrimerd = new LoadingPrimer(TrasferBankServer.this);
        loadingPrimerd.startDialogLoading();
        String typee = "proof_saldo_server";
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), typee);
        RequestBody primary_id = RequestBody.create(MediaType.parse("text/plain"), getPrimaryid());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getFileDataFromDrawable(photo));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", "image.jpg", requestFile);
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        //creating retrofit object
        Api api = RetroClient.getApiServices();
//        progresktp.setVisibility(View.VISIBLE);
        Call<ResponTopUp> call = api.uploadBuktiBayar(token,body, type, primary_id);
        call.enqueue(new Callback<ResponTopUp>() {


            @Override
            public void onResponse(Call<ResponTopUp> call, Response<ResponTopUp> response) {
                String code = response.body().getCode();
//                progresktp.setVisibility(View.INVISIBLE);
//                foto2 ="1";
                if (code.equals("200")) {
                  Toast.makeText(getApplicationContext(), "Foto Berhasil diupload", Toast.LENGTH_SHORT).show();
                    loadingPrimerd.dismissDialog();
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
}
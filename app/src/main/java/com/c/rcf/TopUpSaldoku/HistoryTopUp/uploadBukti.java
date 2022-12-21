package com.c.rcf.TopUpSaldoku.HistoryTopUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.widget.Button;
import android.widget.Toast;

import com.c.rcf.Api.Api;
import com.c.rcf.Helper.LoadingPrimer;
import com.c.rcf.Helper.RetroClient;
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

public class uploadBukti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_bukti);

        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Upload <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        Button upload = findViewById(R.id.upload);
        upload.setOnClickListener(view -> {

            ImagePicker.with(uploadBukti.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
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

    private void uploadBukti(Bitmap photo) {
        String id = getIntent().getStringExtra("id");
        Preference.getSharedPreference(getBaseContext());
        final LoadingPrimer loadingPrimerd = new LoadingPrimer(uploadBukti.this);
        loadingPrimerd.startDialogLoading();
        String typee = "proof_saldoku";
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), typee);
        RequestBody primary_id = RequestBody.create(MediaType.parse("text/plain"), id);
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

                if (code.equals("200")) {
                    Toast.makeText(getApplicationContext(), "Foto Berhasil diupload", Toast.LENGTH_SHORT).show();
                    loadingPrimerd.dismissDialog();
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
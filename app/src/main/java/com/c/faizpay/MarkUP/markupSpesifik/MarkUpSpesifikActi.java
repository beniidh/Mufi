package com.c.faizpay.MarkUP.markupSpesifik;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.faizpay.Api.Api;
import com.c.faizpay.Helper.RetroClient;
import com.c.faizpay.R;
import com.c.faizpay.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkUpSpesifikActi extends AppCompatActivity implements ModalProdukDHS.BottomSheetListenerProduk,ModalSubProdukDHS.BottomSheetListenerProdukSub,ModalInputSpesifikMarkup.BottomSheetListenerProdukS {

    EditText idprodukDaftarHarga,idProviderDaftarHarga;
    RecyclerView reyIDDaftarHarga;
    AdapterProdukDHListM adapterProdukDHList;
    ArrayList<ResponProdukListM.mData> mData = new ArrayList<>();
    String idDH;
    String idsalesProduk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_up_spesifik);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Markup Spesifik <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        idprodukDaftarHarga = findViewById(R.id.idprodukDaftarHarga);
        idprodukDaftarHarga.setFocusable(false);
        idProviderDaftarHarga = findViewById(R.id.idProviderDaftarHarga);
        reyIDDaftarHarga = findViewById(R.id.reymarkup);

        adapterProdukDHList = new AdapterProdukDHListM(getApplicationContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reyIDDaftarHarga.setLayoutManager(mLayoutManager);
        reyIDDaftarHarga.setAdapter(adapterProdukDHList);

        idprodukDaftarHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalProdukDHS modalProdukDH = new ModalProdukDHS();
                modalProdukDH.show(getSupportFragmentManager(),"produk");
            }
        });

        idProviderDaftarHarga.setOnClickListener(v1 -> {

            ModalSubProdukDHS modalSubProdukDH = new ModalSubProdukDHS();
            Bundle bundle = new Bundle();
            bundle.putString("ID",getIdDH());
            modalSubProdukDH.setArguments(bundle);
            modalSubProdukDH.show(getSupportFragmentManager(),"produkDH");

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

    public void getDaftarHarga(String id){


        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukListM> call = api.getProdukDHListMM(token,id);
        call.enqueue(new Callback<ResponProdukListM>() {
            @Override
            public void onResponse(Call<ResponProdukListM> call, Response<ResponProdukListM> response) {
                String respon = response.body().getCode();
                if (respon.equals("200")){
                    mData = response.body().getData();
                    adapterProdukDHList = new AdapterProdukDHListM(getApplicationContext(),mData);
                    reyIDDaftarHarga.setAdapter(adapterProdukDHList);
                }
            }

            @Override
            public void onFailure(Call<ResponProdukListM> call, Throwable t) {

            }
        });
    }

    @Override
    public void onButtonClick(String name, String id) {
        idprodukDaftarHarga.setText(name);
        setIdDH(id);

    }

    public String getIdDH() {
        return idDH;
    }

    public void setIdDH(String idDH) {
        this.idDH = idDH;
    }

    @Override
    public void onButtonClicksub(String name, String id) {
        idProviderDaftarHarga.setText(name);
        mData.clear();
        getDaftarHarga(id);
        Preference.setIDsalesProduk(getApplicationContext(),id);


    }

    @Override
    public void onButtonClickS(String name, String id) {

        Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
        mData.clear();
        getDaftarHarga(Preference.getIDsalesProduk(getApplicationContext()));
    }

    public String getIdsalesProduk() {
        return idsalesProduk;
    }

    public void setIdsalesProduk(String idsalesProduk) {
        this.idsalesProduk = idsalesProduk;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
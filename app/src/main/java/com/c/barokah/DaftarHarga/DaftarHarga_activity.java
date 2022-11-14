package com.c.barokah.DaftarHarga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.c.barokah.Api.Api;
import com.c.barokah.Helper.RetroClient;
import com.c.barokah.R;
import com.c.barokah.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarHarga_activity extends AppCompatActivity implements ModalProdukDH.BottomSheetListenerProduk, ModalSubProdukDH.BottomSheetListenerProdukSub {

    EditText idprodukDaftarHarga, idProviderDaftarHarga;
    RecyclerView reyIDDaftarHarga;
    AdapterProdukDHList adapterProdukDHList;
    ArrayList<ResponProdukList.mData> mData = new ArrayList<>();
    String idDH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_harga_activity);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Daftar Harga <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        idprodukDaftarHarga = findViewById(R.id.idprodukDaftarHarga);
        idprodukDaftarHarga.setFocusable(false);
        idProviderDaftarHarga = findViewById(R.id.idProviderDaftarHarga);
        reyIDDaftarHarga = findViewById(R.id.idRecycleDaftarHarga);

        adapterProdukDHList = new AdapterProdukDHList(getApplicationContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reyIDDaftarHarga.setLayoutManager(mLayoutManager);
        reyIDDaftarHarga.setAdapter(adapterProdukDHList);

        idprodukDaftarHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalProdukDH modalProdukDH = new ModalProdukDH();
                modalProdukDH.show(getSupportFragmentManager(), "produk");
            }
        });

        idProviderDaftarHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModalSubProdukDH modalSubProdukDH = new ModalSubProdukDH();
                Bundle bundle = new Bundle();
                bundle.putString("ID", getIdDH());
                modalSubProdukDH.setArguments(bundle);
                modalSubProdukDH.show(getSupportFragmentManager(), "produkDH");

            }
        });

    }

    public void getDaftarHarga(String id) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukList> call = api.getProdukDHList(token, id);
        call.enqueue(new Callback<ResponProdukList>() {
            @Override
            public void onResponse(Call<ResponProdukList> call, Response<ResponProdukList> response) {
                String respon = response.body().getCode();
                if (respon.equals("200")) {
                    mData = response.body().getData();
                } else {
                    mData.clear();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
                adapterProdukDHList = new AdapterProdukDHList(getApplicationContext(), mData);
                reyIDDaftarHarga.setAdapter(adapterProdukDHList);

            }

            @Override
            public void onFailure(Call<ResponProdukList> call, Throwable t) {

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

    }
}
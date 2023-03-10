package com.c.atmpay.TagihanKonterSales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;

import com.c.atmpay.Api.Api;
import com.c.atmpay.Helper.RetroClient;
import com.c.atmpay.R;
import com.c.atmpay.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagihanKonterbySales extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterKonterTagihan adapterKonterTagihan;
    ArrayList<ResponTagihanKonterSales.mData> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_user_pay_later);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Tagihan Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        recyclerView = findViewById(R.id.ReyTagihanKonterSales);
        adapterKonterTagihan = new AdapterKonterTagihan(getApplicationContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterKonterTagihan);
        getTagihankonter();


    }

    public void getTagihankonter(){

        Api api = RetroClient.getApiServices();
        Call<ResponTagihanKonterSales> call = api.getTagihanSalesKonter("Bearer "+ Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponTagihanKonterSales>() {
            @Override
            public void onResponse(Call<ResponTagihanKonterSales> call, Response<ResponTagihanKonterSales> response) {
                String code = response.body().getCode();

                if(code.equals("200")){

                    mData = response.body().getData();
                    adapterKonterTagihan = new AdapterKonterTagihan(getApplicationContext(), mData);
                    recyclerView.setAdapter(adapterKonterTagihan);

                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponTagihanKonterSales> call, Throwable t) {

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
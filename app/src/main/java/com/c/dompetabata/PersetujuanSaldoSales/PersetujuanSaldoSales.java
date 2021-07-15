package com.c.dompetabata.PersetujuanSaldoSales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.PengajuanLimit.AdapterPengajuanLimit;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
import com.google.gson.internal.$Gson$Preconditions;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersetujuanSaldoSales extends AppCompatActivity {

    RecyclerView recyclerView;

    AdapterPersetujuanSaldoServer adapterPersetujuanSaldoServer;
    ArrayList<ModelPersetujuanSaldo> modelPersetujuanSaldos = new ArrayList<>();
    TextView nominal, tanggal, status;
    LinearLayout LinearKlikPersetujuanSaldo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persetujuan_saldo_sales);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Persetujuan Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        recyclerView = findViewById(R.id.ReyPersetujuanSaldoServer);

        adapterPersetujuanSaldoServer = new AdapterPersetujuanSaldoServer(getApplicationContext(), modelPersetujuanSaldos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterPersetujuanSaldoServer);
        getDataPayletter();


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

    public void getDataPayletter(){
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        Api api = RetroClient.getApiServices();
        Call<ResponPersetujuanSaldo> call = api.getDataAprroval(token);
        call.enqueue(new Callback<ResponPersetujuanSaldo>() {
            @Override
            public void onResponse(Call<ResponPersetujuanSaldo> call, Response<ResponPersetujuanSaldo> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    modelPersetujuanSaldos = response.body().getData();
                    adapterPersetujuanSaldoServer = new AdapterPersetujuanSaldoServer(getApplicationContext(), modelPersetujuanSaldos);
                    recyclerView.setAdapter(adapterPersetujuanSaldoServer);

                }
            }

            @Override
            public void onFailure(Call<ResponPersetujuanSaldo> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataPayletter();
    }
}
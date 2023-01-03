package com.c.apay.KomisiSales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.apay.Api.Api;
import com.c.apay.Helper.LoadingPrimer;
import com.c.apay.Helper.RetroClient;
import com.c.apay.Helper.utils;
import com.c.apay.R;
import com.c.apay.sharePreference.Preference;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Komisi_sales extends AppCompatActivity {

    EditText TanggalMulaiKomisi, TanggalSelesaiKomisi;
    TextView jumlahKomisi;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<mListSales> data = new ArrayList<>();
    AdapterHistoryKomisi adapterHistoryKomisi;
    Button periksa;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komisi_sales);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Komisi Referal<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        TanggalSelesaiKomisi = findViewById(R.id.TanggalSelesaiKomisi);
        TanggalMulaiKomisi = findViewById(R.id.TanggalMulaiKomisi);
        jumlahKomisi = findViewById(R.id.jumlahKomisi);
        periksa = findViewById(R.id.PeriksaKomisi);
        recyclerView = findViewById(R.id.RiwayatKomisi);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);


        periksa.setOnClickListener(view -> {

            if (TanggalMulaiKomisi.getText().toString().equals("") || TanggalSelesaiKomisi.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();

            } else {

                getKomisiHistory(TanggalMulaiKomisi.getText().toString(), TanggalSelesaiKomisi.getText().toString());
            }

        });

        TanggalMulaiKomisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogStart();
            }
        });
        TanggalSelesaiKomisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogEnd();
            }
        });


    }

    public void showDateDialogStart() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(Komisi_sales.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String bulan = String.valueOf(monthOfYear + 1);
                    String day = String.valueOf(dayOfMonth);
                    if (bulan.length() == 1) {
                        bulan = "0" + bulan;

                    }
                    if (day.length() == 1) {

                        day = "0" + day;
                    }

                    String tnggl = year + "-" + bulan + "-" + day;
                    TanggalMulaiKomisi.setText(tnggl);
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void showDateDialogEnd() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(Komisi_sales.this,
                (view, year, monthOfYear, dayOfMonth) -> {

                    String bulan = String.valueOf(monthOfYear + 1);
                    String day = String.valueOf(dayOfMonth);
                    if (bulan.length() == 1) {
                        bulan = "0" + bulan;

                    }
                    if (day.length() == 1) {

                        day = "0" + day;
                    }

                    String tnggl = year + "-" + bulan + "-" + day;
                    TanggalSelesaiKomisi.setText(tnggl);
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
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


    public void getKomisiHistory(String dateStart, String dateEnd) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(Komisi_sales.this);
        loadingPrimer.startDialogLoading();

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSales> call = api.getKomisiHistory(token, dateStart, dateEnd);
        call.enqueue(new Callback<ResponSales>() {
            @Override
            public void onResponse(Call<ResponSales> call, Response<ResponSales> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data = response.body().getData().getUser_history();
                    if (data == null) {
                        Toast.makeText(getApplicationContext(), "Belum ada komisi", Toast.LENGTH_SHORT).show();
                        loadingPrimer.dismissDialog();
                    } else {

                        jumlahKomisi.setText(utils.ConvertRP(response.body().getData().getKomisi_wallet()));
                        adapterHistoryKomisi = new AdapterHistoryKomisi(getApplicationContext(), data);
                        recyclerView.setAdapter(adapterHistoryKomisi);
                        loadingPrimer.dismissDialog();
                    }

                } else {
                    loadingPrimer.dismissDialog();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponSales> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                loadingPrimer.dismissDialog();
            }
        });


    }
}
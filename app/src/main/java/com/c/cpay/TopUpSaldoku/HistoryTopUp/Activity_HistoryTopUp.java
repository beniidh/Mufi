package com.c.cpay.TopUpSaldoku.HistoryTopUp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.c.cpay.Api.Api;
import com.c.cpay.Helper.RetroClient;
import com.c.cpay.R;
import com.c.cpay.sharePreference.Preference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_HistoryTopUp extends AppCompatActivity implements ModalCancel.BottomSheetListener {
    RecyclerView recyclerView;
    AdapterGetHistoryTopUp adapterGetHistoryTopUp;
    ArrayList<ResponHistory.mData> data = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    EditText tanggaltopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_top_up);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>History TopUp <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        recyclerView = findViewById(R.id.linTopup);
        tanggaltopup = findViewById(R.id.tanggaltopup);

        adapterGetHistoryTopUp = new AdapterGetHistoryTopUp(getApplicationContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterGetHistoryTopUp);
        String datee = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tanggaltopup.setText(datee);
        getData(datee,datee);

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        tanggaltopup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar myCalendar2 = Calendar.getInstance();
                DatePickerDialog datePickerDialog=  new DatePickerDialog(Activity_HistoryTopUp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(myCalendar2.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tanggaltopup.setText(sdf.format(myCalendar.getTime()));
        getData(sdf.format(myCalendar.getTime()),sdf.format(myCalendar.getTime()));
    }
    private void getData(String start,String end){
        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponHistory> call = api.getDataTopup(token,start,end);
        call.enqueue(new Callback<ResponHistory>() {
            @Override
            public void onResponse(Call<ResponHistory> call, Response<ResponHistory> response) {
                if(response.body().code.equals("200")){
                    data = response.body().data;
                }else {
                    data.clear();
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                }

                adapterGetHistoryTopUp = new AdapterGetHistoryTopUp(getApplicationContext(),data);
                recyclerView.setAdapter(adapterGetHistoryTopUp);
            }

            @Override
            public void onFailure(Call<ResponHistory> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

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

        String datee = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        getData(datee,datee);

    }

    @Override
    protected void onResume() {
        super.onResume();

        String datee = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        getData(datee,datee);
    }
}
package com.c.cpay.Profil.Poin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.cpay.Api.Api;
import com.c.cpay.Helper.RetroClient;
import com.c.cpay.R;
import com.c.cpay.Respon.ResponProfil;
import com.c.cpay.databinding.ActivityPointBinding;
import com.c.cpay.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Point extends AppCompatActivity implements ModalTukarPin.callback {

    EditText idpointTanggalEditTExt;
    TextView idpointTotalTextView;
    RecyclerView idRecyclepoint;
    AdaptergetReward adaptergetReward;
    ArrayList<mReward.DataEntity> data = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private ActivityPointBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPointBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Point <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        idpointTanggalEditTExt = findViewById(R.id.idpointtanggalEditText);
        idpointTanggalEditTExt.setFocusable(false);
        idpointTotalTextView = findViewById(R.id.idpointTotalPointTextView);
        idRecyclepoint = findViewById(R.id.idRecyclePoint);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.idRecyclePoint.setLayoutManager(mLayoutManager);
        binding.idRecyclePoint.setAdapter(adaptergetReward);


        getContentProfil();

        idpointTanggalEditTExt.setOnClickListener(v -> {

          showDateDialog();

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

    public void showDateDialog(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> idpointTanggalEditTExt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();



    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                int poin = response.body().getData().getUser_poin().getPoin();

                binding.idpointTotalPointTextView.setText(String.valueOf(poin));
                getDataReward(poin);

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    private void getDataReward(int poin){

        Api api = RetroClient.getApiServices();
        Call<mReward> call = api.getRewards("Bearer " + Preference.getToken(getApplicationContext()),Preference.getServerID(getApplicationContext()));
        call.enqueue(new Callback<mReward>() {
            @Override
            public void onResponse(Call<mReward> call, Response<mReward> response) {
                if(response.body().code ==200){

                    for (mReward.DataEntity data1 :  response.body().data){
                        if (data1.status !=0){

                            data.add(data1);
                        }

                    }

                    adaptergetReward = new AdaptergetReward(getApplicationContext(),data,poin);
                    binding.idRecyclePoint.setAdapter(adaptergetReward);
                }else {

                    Toast.makeText(getApplicationContext(),response.body().error,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<mReward> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onClick(String hasil) {
        getContentProfil();
    }
}
package com.c.barokah.konter.DrawSaldo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.c.barokah.R;
import com.c.barokah.TopUpSaldoku.NumberChageLive;

public class drawsaldoku extends AppCompatActivity {

    EditText nominaltarikSaldo;
    Button buttonTarikSaldo;
    TextView namatarik;

    static Activity drawsaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawsaldoku);
        drawsaldo = this;

        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Tarik Saldo <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        nominaltarikSaldo = findViewById(R.id.nominaltarikSaldo);
        namatarik = findViewById(R.id.namatarik);
        namatarik.setText("Tarik saldo : "+getIntent().getStringExtra("namakonter"));
        buttonTarikSaldo = findViewById(R.id.buttonTarikSaldo);
        nominaltarikSaldo.addTextChangedListener(new NumberChageLive(nominaltarikSaldo));

        buttonTarikSaldo.setOnClickListener(v -> {

            if(nominaltarikSaldo.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Nominal tidak boleh kosong",Toast.LENGTH_SHORT).show();

            }else {

                Bundle bundle = new Bundle();
                modalPinDraw modalPinDraw = new modalPinDraw();
                bundle.putString("nominal",nominaltarikSaldo.getText().toString().replaceAll(",",""));
                bundle.putString("id",getIntent().getStringExtra("id"));
                modalPinDraw.setArguments(bundle);
                modalPinDraw.show(getSupportFragmentManager(),"Modal draw");
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
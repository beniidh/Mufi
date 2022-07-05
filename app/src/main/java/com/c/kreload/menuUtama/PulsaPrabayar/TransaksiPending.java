package com.c.kreload.menuUtama.PulsaPrabayar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.kreload.Api.Api;
import com.c.kreload.CetakStruk.DetailTransaksiTruk;
import com.c.kreload.CetakStruk.ResponCodeSubPS;
import com.c.kreload.CetakStruk.StrukPLNPasca.CetakPlnPasca;
import com.c.kreload.CetakStruk.StrukPLNPra.CetakPlnPra;
import com.c.kreload.CetakStruk.StrukPLNPra.cetakBank;
import com.c.kreload.Helper.LoadingPrimer;
import com.c.kreload.Helper.RetroClient;
import com.c.kreload.Helper.utils;
import com.c.kreload.R;
import com.c.kreload.sharePreference.Preference;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPending extends AppCompatActivity {
    ImageView expand, copySNTransaksi, copyTransaksi;
    LinearLayout linearExpand;
    TextView KeteranganTP, namaTP, produkTransaksi, noSN, hargaprodukTP, nomorTP, nominalTP, saldokuterpakai, tanggalDetail, waktuDetail, NomorTransaksiDetail, hargatotalDetail;
    Button tutuppending, cetakStruk;
    ImageView iconTP, iconTPP;
    LoadingPrimer loadingPrimer;
    SwipeRefreshLayout swipeTransaksi;
    String productcode;
    String harga, tanggaldet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_pending);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(),R.color.green )).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Transaksi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        expand = findViewById(R.id.expandtransaksi);
        linearExpand = findViewById(R.id.linearexpand);
        KeteranganTP = findViewById(R.id.KeteranganTP);
        hargaprodukTP = findViewById(R.id.hargaprodukTP);
        nomorTP = findViewById(R.id.nomorTP);
        namaTP = findViewById(R.id.namaTP);
        nominalTP = findViewById(R.id.nominalTP);
        tutuppending = findViewById(R.id.tutuppending);
        iconTP = findViewById(R.id.iconTP);
        produkTransaksi = findViewById(R.id.produkTransaksi);
        iconTPP = findViewById(R.id.iconTPP);
        noSN = findViewById(R.id.noSN);
        cetakStruk = findViewById(R.id.refreshstatus);
        copySNTransaksi = findViewById(R.id.copySNTransaksi);
        copyTransaksi = findViewById(R.id.copyTransaksi);

        saldokuterpakai = findViewById(R.id.saldokuterpakai);
        tanggalDetail = findViewById(R.id.tanggalDetail);
        waktuDetail = findViewById(R.id.waktuDetail);
        NomorTransaksiDetail = findViewById(R.id.NomorTransaksiDetail);
        hargatotalDetail = findViewById(R.id.hargatotalDetail);

        swipeTransaksi = findViewById(R.id.swipeTransaksi);
        String transaksiid = getIntent().getStringExtra("transaksid");
        loadingPrimer = new LoadingPrimer(TransaksiPending.this);
        swipeTransaksi.setOnRefreshListener(() -> {
            ChekTransaksi(transaksiid);
            swipeTransaksi.setRefreshing(false);
        });


        //Broadcase for Refresh Transaksi
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("refresh")) {
                    ChekTransaksi(transaksiid);
                    // DO WHATEVER YOU WANT.
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("refresh"));





        ChekTransaksi(transaksiid);
        cetakStruk.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("nomor", nomorTP.getText().toString());
            bundle.putString("produk", produkTransaksi.getText().toString());
            bundle.putString("harga", getHarga());
            bundle.putString("nama", namaTP.getText().toString());
            bundle.putString("tanggal", tanggalDetail.getText().toString());
            bundle.putString("waktu", waktuDetail.getText().toString());
            bundle.putString("waktu2", getTanggaldet());
            bundle.putString("sn", noSN.getText().toString());
            bundle.putString("transaksid", NomorTransaksiDetail.getText().toString());

            LoadingPrimer loadingPrimer = new LoadingPrimer(TransaksiPending.this);
            loadingPrimer.startDialogLoading();
            String token = "Bearer " + Preference.getToken(getApplicationContext());
            Api api = RetroClient.getApiServices();
            Call<ResponCodeSubPS> call = api.getSubCodePS(token, getProductcode());
            call.enqueue(new Callback<ResponCodeSubPS>() {
                @Override
                public void onResponse(Call<ResponCodeSubPS> call, Response<ResponCodeSubPS> response) {

                    if (response.body().getCode().equals("200")) {
                        String productSub = response.body().getData().getProduct_subcategory().getId();
                        if (productSub.equals("SUBCATID062802100000024")) {

                            Intent intent = new Intent(TransaksiPending.this, CetakPlnPasca.class);
                            intent.putExtra("nomor", nomorTP.getText().toString());
                            intent.putExtra("produk", produkTransaksi.getText().toString());
                            intent.putExtra("harga", getHarga());
                            intent.putExtra("nama", namaTP.getText().toString());
                            intent.putExtra("tanggal", tanggalDetail.getText().toString());
                            intent.putExtra("waktu", waktuDetail.getText().toString());
                            intent.putExtra("waktu2", getTanggaldet());
                            intent.putExtra("sn", noSN.getText().toString());
                            intent.putExtra("transaksid", NomorTransaksiDetail.getText().toString());
                            startActivity(intent);

                        } else if (productSub.equals("SUBCATID062802100000023")) {

                            Intent intent = new Intent(TransaksiPending.this, CetakPlnPra.class);
                            intent.putExtra("nomor", nomorTP.getText().toString());
                            intent.putExtra("produk", produkTransaksi.getText().toString());
                            intent.putExtra("harga", getHarga());
                            intent.putExtra("nama", namaTP.getText().toString());
                            intent.putExtra("tanggal", tanggalDetail.getText().toString());
                            intent.putExtra("waktu", waktuDetail.getText().toString());
                            intent.putExtra("waktu2", getTanggaldet());
                            intent.putExtra("sn", noSN.getText().toString());
                            intent.putExtra("transaksid", NomorTransaksiDetail.getText().toString());
                            startActivity(intent);

                        }  else if (productSub.equals("SUBCATID110102100000108")) {

                            Intent intent = new Intent(TransaksiPending.this, cetakBank.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }else {

                            Intent intent = new Intent(TransaksiPending.this, DetailTransaksiTruk.class);
                            intent.putExtra("nomor", nomorTP.getText().toString());
                            intent.putExtra("produk", produkTransaksi.getText().toString());
                            intent.putExtra("harga", getHarga());
                            intent.putExtra("nama", namaTP.getText().toString());
                            intent.putExtra("tanggal", tanggalDetail.getText().toString());
                            intent.putExtra("waktu", waktuDetail.getText().toString());
                            intent.putExtra("waktu2", getTanggaldet());
                            intent.putExtra("sn", noSN.getText().toString());
                            intent.putExtra("transaksid", NomorTransaksiDetail.getText().toString());
                            startActivity(intent);
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                    }
                    loadingPrimer.dismissDialog();
                }

                @Override
                public void onFailure(Call<ResponCodeSubPS> call, Throwable t) {

                }
            });


        });

        copySNTransaksi.setOnClickListener(v -> {

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", noSN.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
        });

        copyTransaksi.setOnClickListener(v -> {

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", NomorTransaksiDetail.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
        });



        if (Preference.getIconUrl(getApplicationContext()).isEmpty()) {
            iconTP.setBackground(getDrawable(R.drawable.logobarusetianobg));
        } else {

            Picasso.get().load(Preference.getIconUrl(getApplicationContext())).into(iconTP);
        }

        tutuppending.setOnClickListener(v -> {
            String code = getIntent().getStringExtra("code");

            if (code == null) {

                finish();


            } else {

                if (code.equals("saldo")) {

                    finish();
                }
            }

        });


        expand.setOnClickListener(v -> {

            if (linearExpand.getVisibility() == View.GONE) {

                TransitionManager.beginDelayedTransition(linearExpand, new AutoTransition());
                linearExpand.setVisibility(View.VISIBLE);
                expand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up);
            } else {

                TransitionManager.beginDelayedTransition(linearExpand, new AutoTransition());
                linearExpand.setVisibility(View.GONE);
                expand.setImageResource(R.drawable.ic_baseline_expand_more_24);

            }

        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Preference.setUrlIcon(getApplicationContext(),"");
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void ChekTransaksi(String id) {
        loadingPrimer.startDialogLoading();

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<Mchek> call = api.CekTransaksi(token, id);
        call.enqueue(new Callback<Mchek>() {
            @Override
            public void onResponse(Call<Mchek> call, Response<Mchek> response) {
                if (response.body().getCode().equals("200")) {
                    saldokuterpakai.setText(utils.ConvertRP(response.body().getData().getTotal_price()));

                    String status = response.body().getData().getStatus();
                    if (status.equals("SUKSES")) {
                        KeteranganTP.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green4));
                        iconTPP.setBackground(getDrawable(R.drawable.check));
                        cetakStruk.setVisibility(View.VISIBLE);
                    } else if (status.equals("GAGAL")) {
                        KeteranganTP.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                        iconTPP.setBackground(getDrawable(R.drawable.failed));
                    }

                    KeteranganTP.setText(response.body().getData().getStatus());
                    hargaprodukTP.setText(utils.ConvertRP(response.body().getData().getTotal_price()));
                    nomorTP.setText(response.body().getData().getCustomer_no());
                    nominalTP.setText(utils.ConvertRP(response.body().getData().getTotal_price()));
                    String create = response.body().getData().getUpdated_at();
                    namaTP.setText(response.body().getData().getCustomer_name());
                    String tahun = create.substring(0, 4);
                    String bulan = utils.convertBulan(create.substring(5, 7));
                    setProductcode(response.body().getData().getProduct_code());
                    String hari = create.substring(8, 10);
                    String jam = create.substring(11, 16);
                    setTanggaldet(create.substring(0, 10) + " " + jam);
                    tanggalDetail.setText(hari + " " + bulan + " " + tahun);
                    waktuDetail.setText(jam);
                    produkTransaksi.setText(response.body().getData().getProduct_name());
                    NomorTransaksiDetail.setText(response.body().getData().getId());
                    noSN.setText(response.body().getData().getSn());
                    hargatotalDetail.setText(utils.ConvertRP(response.body().getData().getTotal_price()));
                    setHarga(response.body().getData().getTotal_price());
                    loadingPrimer.dismissDialog();


                } else if (response.body().getCode().equals("401")) {
                    Toast.makeText(getApplicationContext(), "Token telah berakhir,silahkan login ulang", Toast.LENGTH_LONG).show();

                    loadingPrimer.dismissDialog();
                } else {
                    loadingPrimer.dismissDialog();

                }

            }

            @Override
            public void onFailure(Call<Mchek> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                loadingPrimer.dismissDialog();
            }
        });


    }


    public String getHarga() {
        return harga;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTanggaldet() {
        return tanggaldet;
    }

    public void setTanggaldet(String tanggaldet) {
        this.tanggaldet = tanggaldet;
    }
}
package com.c.kreload.CetakStruk.StrukPLNPasca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.kreload.Api.Api;
import com.c.kreload.CetakStruk.Epos.AsyncBluetoothEscPosPrint;
import com.c.kreload.CetakStruk.Epos.AsyncEscPosPrinter;
import com.c.kreload.CetakStruk.StrukPLNPra.CetakPlnPra;
import com.c.kreload.CetakStruk.cetak;
import com.c.kreload.Helper.LoadingPrimer;
import com.c.kreload.Helper.RetroClient;
import com.c.kreload.Helper.utils;
import com.c.kreload.R;
import com.c.kreload.Respon.ResponProfil;
import com.c.kreload.sharePreference.Preference;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CetakPlnPasca extends AppCompatActivity {

    private final Locale locale = new Locale("id", "ID");
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    Button setAdmin, CetakPasca, shareStrukP;
    EditText pilihPerangkat;

    TextView KonterPS, AlamatPS, namaPS, TagihanPS,
            jumlahBulanPS, TarifDayaPS, jmlhTagihanPS, SMPS, adminPS,
            totaltagihanPS, RefPS, WaktuPS, IdPS;
    LoadingPrimer loadingPrimer = new LoadingPrimer(CetakPlnPasca.this);

    TextView header, footer;
    ImageView EditHeader, EditFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak_pln_pasca);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Cetak Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        EditFooter = findViewById(R.id.EditFooter);
        EditHeader = findViewById(R.id.EditHeader);
        header = findViewById(R.id.header);
        footer = findViewById(R.id.footer);
        IdPS = findViewById(R.id.IdPS);

        IdPS.setText("ID : " + getIntent().getStringExtra("nomor"));

        header.setText(Preference.getHeader(getApplicationContext()));
        footer.setText(Preference.getFooter(getApplicationContext()));

        EditFooter.setOnClickListener(v -> {
            popUpEditFooter();

        });

        EditHeader.setOnClickListener(v -> {

            popUpEditHeader();

        });

        //Button
        setAdmin = findViewById(R.id.setAdmin);
        CetakPasca = findViewById(R.id.CetakPasca);
        //TextView
        KonterPS = findViewById(R.id.KonterPS);
        AlamatPS = findViewById(R.id.AlamatPS);
        WaktuPS = findViewById(R.id.WaktuPS);

        namaPS = findViewById(R.id.namaPS);
        shareStrukP = findViewById(R.id.shareStrukP);
        TagihanPS = findViewById(R.id.TagihanPS);
        jumlahBulanPS = findViewById(R.id.jumlahBulanPS);
        TarifDayaPS = findViewById(R.id.TarifDayaPS);
        jmlhTagihanPS = findViewById(R.id.jmlhTagihanPS);
        SMPS = findViewById(R.id.SMPS);
        adminPS = findViewById(R.id.adminPS);
        totaltagihanPS = findViewById(R.id.totaltagihanPS);
        RefPS = findViewById(R.id.RefPS);

        namaPS.setText("Nama : " + getIntent().getStringExtra("nama"));
        WaktuPS.setText(getIntent().getStringExtra("waktu2"));
        String SN = getIntent().getStringExtra("sn");
        totaltagihanPS.setText("Total Tagihan : " + utils.ConvertRP(getIntent().getStringExtra("harga")));
        jmlhTagihanPS.setText("Tagihan : " + utils.ConvertRP(getIntent().getStringExtra("harga")));
        String[] sn = SN.split(",");
        TarifDayaPS.setText("Tarif Daya :" + sn[3].substring(3));
        namaPS.setText(sn[0]);
        TagihanPS.setText("Periode Tagihan :" + sn[1].substring(5));
        jumlahBulanPS.setText("Jumlah Bulan :" + sn[2].substring(5));
        SMPS.setText("Stand Meter :" + sn[4].substring(3));
        RefPS.setText(sn[8]);

        getContentProfil();

        setAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpMenuSetHargajual();

            }
        });
        CetakPasca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printBluetooth();

            }
        });
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        pilihPerangkat.setOnClickListener(v -> {
            browseBluetoothDevice();

        });

        shareStrukP.setOnClickListener(v -> {

            Rect bounds = new Rect();
            Bitmap bmp = Bitmap.createBitmap(1400, 1850, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bmp);
            canvas.drawColor(-1);
            Paint paint = new Paint();
            Typeface type3 = ResourcesCompat.getFont(getApplicationContext(), R.font.exobold);
            paint.setTypeface(type3);
            paint.setTextSize(70);
            paint.setColor(Color.rgb(0, 0, 0));

            Paint paint5 = new Paint();

            paint5.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray4));
            Typeface type5 = ResourcesCompat.getFont(getApplicationContext(), R.font.mukta);
            paint5.setTypeface(type5);
            paint5.setTextSize(52);

            String headerr = header.getText().toString();
            canvas.drawText(headerr, 80, 150, paint5);

            int tambahan2 = 100;
            int y = 150+tambahan2; // x = 10,
            int x = 10;

            String text = KonterPS.getText().toString();

            paint.getTextBounds(text, 0, text.length(), bounds);
            x = (canvas.getWidth() / 2) - (bounds.width() / 2);
            canvas.drawText(text, x, y, paint);
            Paint paint4 = new Paint();
            paint4.setTextSize(73);
            String jenis = " * Struk PLN Pasca *";
            paint4.getTextBounds(jenis, 0, jenis.length(), bounds);
            x = (canvas.getWidth() / 2) - (bounds.width() / 2);
            canvas.drawText(jenis, x, 250 +tambahan2, paint4);

            Paint paint1 = new Paint();

            paint1.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray4));
            Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.courierprimereguler);
            paint1.setTypeface(type2);

            paint1.setTextSize(72);

            int left = 120;
            int tambahan = 50+tambahan2;

            canvas.drawText("Nama  : ", left, 400 + tambahan, paint1);
            canvas.drawText(namaPS.getText().toString().substring(6), 460, 400 + tambahan, paint1);

            canvas.drawText("ID    : ", left, 500 + tambahan, paint1);
            canvas.drawText(IdPS.getText().toString().substring(5), 460, 500 + tambahan, paint1);

            tambahan = 150+ tambahan2;
            canvas.drawText("PTAG  : ", left, 500 + tambahan, paint1);
            canvas.drawText(TagihanPS.getText().toString().substring(18), 460, 500 + tambahan, paint1);
//
            canvas.drawText("JBLN  : ", left, 600 + tambahan, paint1);
            canvas.drawText(jumlahBulanPS.getText().toString().substring(15), 460, 600 + tambahan, paint1);
//
            canvas.drawText("TD    : ", left, 700 + tambahan, paint1);
            canvas.drawText(TarifDayaPS.getText().toString().substring(13), 460, 700 + tambahan, paint1);
//
            canvas.drawText("SM    : ", left, 800 + tambahan, paint1);
            canvas.drawText(SMPS.getText().toString().substring(14), 460, 800 + tambahan, paint1);
//
            canvas.drawText("TGHN  : ", left, 900 + tambahan, paint1);
            canvas.drawText(jmlhTagihanPS.getText().toString().substring(10), 460, 900 + tambahan, paint1);
//
            canvas.drawText("Admin : ", left, 1000 + tambahan, paint1);
            canvas.drawText(adminPS.getText().toString(), 460, 1000 + tambahan, paint1);

            canvas.drawText("Total : ", left, 1100 + tambahan, paint1);
            canvas.drawText(totaltagihanPS.getText().toString().substring(16), 460, 1100 + tambahan, paint1);
//            paint4.setTextSize(63);
//            paint4.setTypeface(Typeface.DEFAULT_BOLD);
//            canvas.drawText("SN :" + SNPR.getText().toString().substring(8), left+100, 1230, paint4);

            Paint paint3 = new Paint();

            paint3.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray4));
            Typeface type4 = ResourcesCompat.getFont(getApplicationContext(), R.font.mukta);
            paint3.setTypeface(type4);
            paint3.setTextSize(36);

            canvas.drawText("* Struk ini merupakan bukti pembayaran yang sah ", left + 200, 1280 + tambahan, paint3);
            canvas.drawText("mohon disimpan,Terimakasih", left + 350, 1330 + tambahan, paint3);

            String Footer = footer.getText().toString();

            if (Footer.length() > 45) {
                canvas.drawText(Footer.substring(0, 45), left, 1430 + tambahan, paint5);
                canvas.drawText(Footer.substring(45), left, 1480 + tambahan, paint5);
            } else {
                canvas.drawText(Footer, left, 1430 + tambahan, paint5);
            }

            saveImageExternal(bmp);
            File imagePath = new File(getApplicationContext().getCacheDir(), "images");
            File newFile = new File(imagePath, "image.png");
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.c.kreload.fileprovider", newFile);

            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                startActivity(Intent.createChooser(shareIntent, "Choose an app"));
            }

        });
    }

    private void saveImageExternal(Bitmap image) {
        //TODO - Should be processed in another thread
        try {

            File cachePath = new File(getApplicationContext().getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void popUpEditFooter() {

        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CetakPlnPasca.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_footer, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.EFooter);
        editText.setText(Preference.getFooter(getApplicationContext()));
        Button button1 = (Button) dialogView.findViewById(R.id.BEFoter);

        button1.setOnClickListener(v -> {
            footer.setText(editText.getText().toString());
            Preference.setFooter(getApplicationContext(), editText.getText().toString());

            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

    public void popUpEditHeader() {

        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CetakPlnPasca.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_header, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.EFooter);
        editText.setText(Preference.getHeader(getApplicationContext()));
        Preference.setHeader(getApplicationContext(), editText.getText().toString());
        Button button1 = (Button) dialogView.findViewById(R.id.BEFoter);

        button1.setOnClickListener(v -> {
            header.setText(editText.getText().toString());
            Preference.setHeader(getApplicationContext(), editText.getText().toString());
            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }


    private BluetoothConnection selectedDevice;

    public void browseBluetoothDevice() {
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();

        if (bluetoothDevicesList != null) {
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Default printer";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                items[++i] = device.getDevice().getName();
            }

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(CetakPlnPasca.this);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = i - 1;
                    if (index == -1) {
                        selectedDevice = null;
                    } else {
                        selectedDevice = bluetoothDevicesList[index];
                    }
//
                    pilihPerangkat.setText(items[i]);
                }
            });

            android.app.AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }

    public static final int PERMISSION_BLUETOOTH = 1;

    public void printBluetooth() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, cetak.PERMISSION_BLUETOOTH);
        } else {
            new AsyncBluetoothEscPosPrint(this).execute(this.getAsyncEscPosPrinter(selectedDevice));
        }
    }

    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'' yyyy-MM-dd ':' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.setTextToPrint(
                "[L]" + header.getText().toString() + "\n" + "\n" +
                        "[C]<u><font size='tall' color ='bg-black'>" + KonterPS.getText().toString() + "</font></u>\n" +
                        "[C]\n" +
                        "[C]" + AlamatPS.getText().toString() + "\n" +
                        "[C]<u type='double'>" + WaktuPS.getText().toString() + "</u>\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[C]\n" +
                        "[C]" + "<b>Struk Pembayaran PLN Pasca<b>" + "\n" +
                        "[C]\n" +
                        "[L]Produk [L]: PLN PASCA" + "\n" +
                        "[L]Nama [L]: " + namaPS.getText().toString().substring(6) + "\n" +
                        "[L]ID [L]: " + IdPS.getText().toString().substring(5) + "\n" +
                        "[L]Periode TAG [L]: " + TagihanPS.getText().toString().substring(18) + "\n" +
                        "[L]Jumlah BLN [L]:" + jumlahBulanPS.getText().toString().substring(14) + "\n" +
                        "[L]Tarif Daya [L]:" + TarifDayaPS.getText().toString().substring(12) + "\n" +
                        "[L]Stand Meter [L]:" + SMPS.getText().toString().substring(13) + "\n" +
                        "[L]Tagihan" + "[L]: " + jmlhTagihanPS.getText().toString().substring(10) + "\n" +
                        "[L]Admin" + "[L]: " + adminPS.getText().toString() + "\n" +

                        "[l]\n" +
                        "[L]<b>Total Bayar<b>" + "[L]: " + totaltagihanPS.getText().toString().substring(15) + "\n" +
                        "[C]--------------------------------\n" +
                        "[l]" + RefPS.getText().toString() + "\n" +
                        "[C]\n" +
                        "[C]struk ini merupakan bukti\n" +
                        "[C]pembayaran yang SAH\n" +
                        "[C]harap disimpan\n" +
                        "[C]\n" +
                        "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                        "[L]\n" +
                        "[L]" + footer.getText().toString() + "\n" +
                        "[L]\n"
        );
    }


    public void doPrint() {

        try {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, cetak.PERMISSION_BLUETOOTH);
            } else {
                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                loadingPrimer.startDialogLoading();
                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                    final String text =
                            "[C]<u><font size='tall' color ='bg-black'>" + namaPS.getText().toString() + "</font></u>\n" +
                                    "[C]\n" +
                                    "[C]" + KonterPS.getText().toString() + "\n" +
                                    "[C]<u type='double'>" + WaktuPS.getText().toString() + "</u>\n" +
                                    "[C]\n" +
                                    "[C]--------------------------------\n" +
                                    "[C]\n" +
                                    "[C]" + "<b>Struk Pembayaran PLN Pasca<b>" + "\n" +
                                    "[C]\n" +
                                    "[L]Produk [L]: PLN PASCA" + "\n" +
                                    "[L]Nama [L]: +" + namaPS.getText().toString().substring(5) + "\n" +
                                    "[L]Periode TAG [L]:" + TagihanPS.getText().toString().substring(16) + "\n" +
                                    "[L]Jumlah BLN [L]:" + jumlahBulanPS.getText().toString().substring(5) + "\n" +
                                    "[L]Tarif Daya [L]:" + TarifDayaPS.getText().toString().substring(12) + "\n" +
                                    "[L]Stand Meter [L]:" + SMPS.getText().toString().substring(13) + "\n" +
                                    "[L]Tagihan" + "[L]: " + jmlhTagihanPS.getText().toString().substring(8) + "\n" +
                                    "[L]Admin" + "[L]: " + adminPS.getText().toString().substring(6) + "\n" +

                                    "[l]\n" +
                                    "[L]<b>Total Bayar<b>" + "[L]: " + totaltagihanPS.getText().toString().substring(15) + "\n" +
                                    "[C]--------------------------------\n" +
                                    "[l]" + RefPS.getText().toString() + "\n" +
                                    "[C]\n" +
                                    "[C]struk ini merupakan bukti\n" +
                                    "[C]pembayaran yang SAH\n" +
                                    "[C]harap disimpan\n" +
                                    "[C]\n" +
                                    "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                                    "[L]\n";

                    printer.printFormattedText(text);
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();

                } else {
                    loadingPrimer.dismissDialog();
                    Toast.makeText(this, "Tidak Bisa Menghubungkan ,Coba Lagi!", Toast.LENGTH_SHORT).show();

                }

            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }

    public void popUpMenuSetHargajual() {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(CetakPlnPasca.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_hargaadmin, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.edithargajual);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonCancelhargajual);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonSavehargajual);

        button1.setOnClickListener(view -> dialogBuilder.dismiss());
        button2.setOnClickListener(view -> {
            // DO SOMETHINGS
            String harga = utils.ConvertRP(editText.getText().toString());
            int total = Integer.parseInt(editText.getText().toString()) + Integer.parseInt(getIntent().getStringExtra("harga"));
            adminPS.setText(harga);
            totaltagihanPS.setText("Total Tagihan : " + utils.ConvertRP(String.valueOf(total)));
            dialogBuilder.dismiss();

        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }


    public void doPrintPra(View view) {
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd ':' HH:mm:ss");
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, cetak.PERMISSION_BLUETOOTH);
            } else {
                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                    final String text =
                            "[C]<u><font size='tall' color ='bg-black'>" + "Garuntang Cell" + "</font></u>\n" +
                                    "[C]\n" +
                                    "[C]" + "Jl. Ir Sutami KM 18" + "\n" +
                                    "[C]<u type='double'>" + format.format(new Date()) + "</u>\n" +
                                    "[C]\n" +
                                    "[C]--------------------------------\n" +
                                    "[C]\n" +
                                    "[C]" + "<b>Struk Pembayaran PLN Prabayar<b>" + "\n" +
                                    "[C]\n" +
                                    "[L]Nama [L]: IKHWANUDIN" + "\n" +
                                    "[L]Nominal [L]: 200.000" + "\n" +
                                    "[L]Tarif [L]: R1" + "\n" +
                                    "[L]Daya [L]: 3500" + "\n" +
                                    "[L]KWH [L]: 66" + "\n" +
                                    "[L]Harga" + "[L]: " + nf.format(54000) + "\n" +
                                    "[L]Admin" + "[L]: " + nf.format(2000) + "\n" +

                                    "[l]\n" +
                                    "[L]<b>Total Bayar<b>" + "[L]: " + nf.format(56000) + "\n" +
                                    "[C]--------------------------------\n" +
                                    "[C]SN: 2467-5885-8160-1665-5801\n" +
                                    "[C]\n" +
                                    "[C]struk ini merupakan bukti\n" +
                                    "[C]pembayaran yang SAH\n" +
                                    "[C]harap disimpan\n" +
                                    "[C]\n" +
                                    "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                                    "[L]\n";

                    printer.printFormattedText(text);
                } else {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
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

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                KonterPS.setText(response.body().getData().getStore_name());
                AlamatPS.setText(response.body().getData().getAddress());

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}
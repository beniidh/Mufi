package com.c.kreload.CetakStruk.StrukPLNPra;

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
import com.c.kreload.CetakStruk.cetak;
import com.c.kreload.Helper.RetroClient;
import com.c.kreload.Helper.utils;
import com.c.kreload.R;
import com.c.kreload.Respon.ResponProfil;
import com.c.kreload.sharePreference.Preference;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CetakPlnPra extends AppCompatActivity {

    TextView KonterPR, AlamatPR, WaktuPR, namaPR, tarifPR, DayaPR,
            KWHPR, NominalPR, adminPR, totaltagihanPR, SNPR, nomorPR;
    Button setAdmin, CetakPra, shareStruk;
    EditText pilihPerangkat;

    TextView header, footer;
    ImageView EditHeader, EditFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak_pln_pra);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Cetak Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        EditFooter = findViewById(R.id.EditFooter);
        EditHeader = findViewById(R.id.EditHeader);
        header = findViewById(R.id.header);
        footer = findViewById(R.id.footer);

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
        CetakPra = findViewById(R.id.CetakPra);
        //TextView
        KonterPR = findViewById(R.id.KonterPR);
        AlamatPR = findViewById(R.id.AlamatPR);
        WaktuPR = findViewById(R.id.WaktuPR);
        shareStruk = findViewById(R.id.shareStruk);
        nomorPR = findViewById(R.id.nomorPR);
        namaPR = findViewById(R.id.namaPR);
        tarifPR = findViewById(R.id.tarifPR);
        DayaPR = findViewById(R.id.DayaPR);
        KWHPR = findViewById(R.id.KWHPR);
        NominalPR = findViewById(R.id.NominalPR);
        adminPR = findViewById(R.id.adminPR);
        totaltagihanPR = findViewById(R.id.totaltagihanPR);
        SNPR = findViewById(R.id.SNPR);
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        String SN = getIntent().getStringExtra("sn");
        String[] sn = SN.split(",");

        nomorPR.setText("Nomor : " + getIntent().getStringExtra("nomor"));
        namaPR.setText("Nama : " + sn[1].substring(5));
        WaktuPR.setText(getIntent().getStringExtra("waktu2"));
        tarifPR.setText("Tarif/Daya : " + sn[2].substring(5));
        totaltagihanPR.setText("Total Tagihan : " + utils.ConvertRP(getIntent().getStringExtra("harga")));
        NominalPR.setText("Nominal : " + utils.ConvertRP(getIntent().getStringExtra("harga")));
        SNPR.setText("Token : " + sn[0].substring(6));
        KWHPR.setText("KWH : " + sn[3].substring(4));

        setAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpMenuSetHargajual();

            }
        });
        CetakPra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printBluetooth();

            }
        });
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        pilihPerangkat.setOnClickListener(v -> {
            browseBluetoothDevice();

        });


        getContentProfil();

        shareStruk.setOnClickListener(v -> {

            Rect bounds = new Rect();
            Bitmap bmp = Bitmap.createBitmap(1400, 1700, Bitmap.Config.ARGB_4444);
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

            int y = 150; // x = 10,
            int tambahan2 = 100;
            int x = 10;


            String text = KonterPR.getText().toString();
            String headerr = header.getText().toString();
            canvas.drawText(headerr, 80, y, paint5);


            paint.getTextBounds(text, 0, text.length(), bounds);
            x = (canvas.getWidth() / 2) - (bounds.width() / 2);
            canvas.drawText(text, x, y + tambahan2, paint);
            Paint paint4 = new Paint();
            paint4.setTextSize(73);
            String jenis = " * Struk PLN Prabayar *";
            paint4.getTextBounds(jenis, 0, jenis.length(), bounds);
            x = (canvas.getWidth() / 2) - (bounds.width() / 2);
            canvas.drawText(jenis, x, 250 + tambahan2, paint4);
            canvas.drawText(WaktuPR.getText().toString(), 120, 320 + tambahan2, paint5);

            Paint paint1 = new Paint();

            paint1.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray4));
            Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.courierprimereguler);
            paint1.setTypeface(type2);

            paint1.setTextSize(72);

            int left = 120;
            int tambahan = 50 + tambahan2;

            canvas.drawText("Nomor  : ", left, 400 + tambahan, paint1);
            canvas.drawText(nomorPR.getText().toString().substring(7), 460, 400 + tambahan, paint1);

            canvas.drawText("Nama   : ", left, 500 + tambahan, paint1);
            canvas.drawText(namaPR.getText().toString().substring(7), 460, 500 + tambahan, paint1);

            canvas.drawText("Tarif  : ", left, 600 + tambahan, paint1);
            canvas.drawText(tarifPR.getText().toString().substring(13), 460, 600 + tambahan, paint1);

            canvas.drawText("KWH    : ", left, 700 + tambahan, paint1);
            canvas.drawText(KWHPR.getText().toString().substring(6), 460, 700 + tambahan, paint1);

//            canvas.drawText("Harga  : ", left, 800 + tambahan, paint1);
//            canvas.drawText(NominalPR.getText().toString().substring(9), 460, 800 + tambahan, paint1);
//
//            canvas.drawText("Admin  : ", left, 900 + tambahan, paint1);
//            canvas.drawText(" " + adminPR.getText().toString(), 460, 900 + tambahan, paint1);

            canvas.drawText("Total  : ", left, 800 + tambahan, paint1);
            canvas.drawText(" "+adminPR.getText().toString(), 460, 800 + tambahan, paint1);
            paint4.setTextSize(63);
            paint4.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText("SN :" + SNPR.getText().toString().substring(8), left + 100, 1230 + tambahan, paint4);

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

    public void popUpEditFooter() {

        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CetakPlnPra.this).create();
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

        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(CetakPlnPra.this).create();
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

                KonterPR.setText(response.body().getData().getStore_name());
                AlamatPR.setText(response.body().getData().getAddress());

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
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

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(CetakPlnPra.this);
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
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.setTextToPrint(
                "[L]" + header.getText().toString() + "\n" + "\n" +
                        "[C]<u><font size='tall' color ='bg-black'>" + KonterPR.getText().toString() + "</font></u>\n" +
                        "[C]\n" +
                        "[C]" + AlamatPR.getText().toString() + "\n" +
                        "[C]<u type='double'>" + WaktuPR.getText().toString() + "</u>\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[C]\n" +
                        "[C]" + "<b>Struk Pembayaran PLN Prabayar<b>" + "\n" +
                        "[C]" + WaktuPR.getText().toString() + "\n" +
                        "[C]\n" +
                        "[C]\n" +
                        "[L]Nomor [L]: " + nomorPR.getText().toString().substring(7) + "\n" +
                        "[L]Nama [L]: " + namaPR.getText().toString().substring(7) + "\n" +
                        "[L]Tarif/Daya [L]: " + tarifPR.getText().toString().substring(13) + "\n" +
                        "[L]KwH [L]: " + KWHPR.getText().toString().substring(6) + "\n" +

                        "[l]\n" +
                        "[L]<b>Total Bayar<b>" + "[L]: " + adminPR.getText().toString().substring(15) + "\n" +
                        "[C]--------------------------------\n" +
                        "[C]<font size='tall'>" + SNPR.getText().toString().substring(7) + "</font>" + "\n" +
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

    public void popUpMenuSetHargajual() {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(CetakPlnPra.this).create();
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
            adminPR.setText(harga);
            dialogBuilder.dismiss();

        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

}
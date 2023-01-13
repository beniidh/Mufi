package com.c.atmpay.CetakStruk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.atmpay.Api.Api;
import com.c.atmpay.CetakStruk.Epos.AsyncBluetoothEscPosPrint;
import com.c.atmpay.CetakStruk.Epos.AsyncEscPosPrinter;
import com.c.atmpay.Helper.RetroClient;
import com.c.atmpay.R;
import com.c.atmpay.Respon.ResponProfil;
import com.c.atmpay.sharePreference.Preference;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cetak extends AppCompatActivity {

    TextView idNomorStruk, namaTPCC, idProdukStruk, hargastruk, titlestrukC, idTanggalStruk, idWaktuStruk, idNomorSNStruk, idNomorTransaksiStruk, idTotalPembelianStruk;
    Button buttondownloadPDF, cetakStrukPDF;
    TextView header, footer;
    ImageView EditHeader, EditFooter;
    LinearLayout linCetak;
    int PERMISSION_ALL = 1;

    TextView myLabel;
    String alamat;


    String title;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,

    };
    EditText pilihPerangkat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak);
        String color = Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.green)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Cetak<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        myLabel = findViewById(R.id.myLabel);

        EditFooter = findViewById(R.id.EditFooter);
        EditHeader = findViewById(R.id.EditHeader);
        header = findViewById(R.id.header);
        footer = findViewById(R.id.footer);
        linCetak = findViewById(R.id.linCetak);

        header.setText(Preference.getHeader(getApplicationContext()));
        footer.setText(Preference.getFooter(getApplicationContext()));

        EditFooter.setOnClickListener(v -> {
            popUpEditFooter();

        });

        EditHeader.setOnClickListener(v -> {

            popUpEditFooterHeader();

        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (!hasPermissions(cetak.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(cetak.this, PERMISSIONS, PERMISSION_ALL);
        }
        File file = new File(this.getExternalFilesDir(null).getAbsolutePath(), "pdfsdcard_location");
        if (!file.exists()) {
            file.mkdir();
        }

        buttondownloadPDF = findViewById(R.id.buttondownloadPDF);
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        cetakStrukPDF = findViewById(R.id.cetakStrukPDF);
        namaTPCC = findViewById(R.id.namaTPCC);

        idNomorStruk = findViewById(R.id.idNomorStrukC);
        idProdukStruk = findViewById(R.id.idProdukStrukC);
        idTanggalStruk = findViewById(R.id.idTanggalStrukC);
        idWaktuStruk = findViewById(R.id.idWaktuStrukC);
        idNomorSNStruk = findViewById(R.id.idNomorSNStrukC);
        idNomorTransaksiStruk = findViewById(R.id.idNomorTransaksiStrukC);
        idTotalPembelianStruk = findViewById(R.id.idTotalPembelianStrukC);
        titlestrukC = findViewById(R.id.titlestrukC);

        idNomorStruk.setText(getIntent().getStringExtra("nomor"));
        namaTPCC.setText(getIntent().getStringExtra("nama"));
        idProdukStruk.setText(getIntent().getStringExtra("produk"));
        idTanggalStruk.setText(getIntent().getStringExtra("tanggal"));
        idWaktuStruk.setText(getIntent().getStringExtra("waktu"));
        idNomorSNStruk.setText(getIntent().getStringExtra("sn"));
        idNomorTransaksiStruk.setText(getIntent().getStringExtra("transaksid"));
        idTotalPembelianStruk.setText(getIntent().getStringExtra("hargajual"));
        title = getIntent().getStringExtra("title");
        getContentProfil();

        buttondownloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(footer.getText().toString().isEmpty()){
                    footer.setVisibility(View.INVISIBLE);

                }if(header.getText().toString().isEmpty()){
                    header.setVisibility(View.INVISIBLE);
                }

                Bitmap bitmap = Bitmap.createBitmap(linCetak.getWidth(),linCetak.getHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                linCetak.draw(canvas);

                saveImageExternal(bitmap);
                File imagePath = new File(getApplicationContext().getCacheDir(), "images");
                File newFile = new File(imagePath, "image.png");
                Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.c.atmpay.fileprovider", newFile);

                if (contentUri != null) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                    shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                    startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                }


            }
        });

        cetakStrukPDF.setOnClickListener(v -> printBluetooth());

        pilihPerangkat.setOnClickListener(v -> {
            browseBluetoothDevice();

        });
    }

    public void popUpEditFooter() {

        AlertDialog dialogBuilder = new AlertDialog.Builder(cetak.this).create();
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

    public void popUpEditFooterHeader() {

        android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(cetak.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_footerheader, null);

        EditText editTextF = (EditText) dialogView.findViewById(R.id.EFooter);
        EditText editTextH = (EditText) dialogView.findViewById(R.id.EHeader);
        editTextF.setText(Preference.getFooter(getApplicationContext()));
        editTextH.setText(Preference.getHeader(getApplicationContext()));
        Button button1 = (Button) dialogView.findViewById(R.id.BEFoter);


        button1.setOnClickListener(v -> {
             footer.setText(editTextF.getText().toString());
            Preference.setFooter(getApplicationContext(), editTextF.getText().toString());
             header.setText(editTextH.getText().toString());
            Preference.setHeader(getApplicationContext(), editTextH.getText().toString());

            dialogBuilder.dismiss();
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

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


    public void getContentProfil() {
        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                titlestrukC.setText(response.body().getData().getStore_name());
                setAlamat(response.body().getData().getAddress());

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private BluetoothConnection selectedDevice;

    @SuppressLint("MissingPermission")
    public void browseBluetoothDevice() {
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();

        if (bluetoothDevicesList != null) {
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Default printer";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                items[++i] = device.getDevice().getName();
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(cetak.this);
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

                    pilihPerangkat.setText(items[i]);
                }
            });

            AlertDialog alert = alertDialog.create();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case cetak.PERMISSION_BLUETOOTH:
                    this.printBluetooth();
                    break;
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd ':' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.setTextToPrint(
                "[L]" + header.getText().toString() + "\n" + "\n" +
                        "[C]<u><font size='big'>" + titlestrukC.getText().toString() + "</font></u>\n" +
                        "[C]\n" +
                        "[C]" + "JL " + getAlamat() + "\n" +
                        "[C]<u type='double'>" + format.format(new Date()) + "</u>\n" +
                        "[C]\n" +
                        "[C]<b>STRUK PEMBELIAN<b>\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]<b>Nomor</b>[L]:" + idNomorStruk.getText().toString() + "\n" +
//                        "[L]\n" +
                       TextAdd(namaTPCC.getText().toString(),"Nama") + "\n" +
                        //"[L]\n" +
                       TextAdd(idProdukStruk.getText().toString(),"Produk") + "\n" +
//                        "[L]\n" +
                        TextAdd(idNomorTransaksiStruk.getText().toString(),"Transaksi") + "\n" +
                        "[C]--------------------------------\n" +
//                        "[L]\n" +
                        "[L]<b>Total Bayar</b>[R]" + idTotalPembelianStruk.getText().toString() + "\n" +
                        "[L]\n" +
                        "[C]" + "SN " + idNomorSNStruk.getText().toString() + "\n" +
                        "[L]\n" +
                        "[L]" + footer.getText().toString() + "\n" +
                        "[L]\n"
        );
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


    private String TextAdd(String caption,String judul){

        String value="";

        if(caption.length()<15){
            value = "[L]<b>"+judul+"</b>[L]:" + caption+"\n";
        }else if(caption.length()>=15 && caption.length()<30 ){
            value = "[L]<b>"+judul+"</b>[L]:" + caption.substring(0,14)+"\n"+"[L][L] " + caption.substring(14)+"\n";
        }else {
            value = "[L]<b>"+judul+"</b>[L]:" + caption.substring(0,14)+"\n"+"[L][L] " + caption.substring(14,28)+"\n"+"[L][L] " + caption.substring(28)+"\n";
        }

        return value;
    }


}
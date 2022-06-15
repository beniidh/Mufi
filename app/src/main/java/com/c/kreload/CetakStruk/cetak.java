package com.c.kreload.CetakStruk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
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
import com.c.kreload.CetakStruk.StrukPLNPasca.CetakPlnPasca;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cetak extends AppCompatActivity {

    TextView idNomorStruk, namaTPCC, idProdukStruk, hargastruk, titlestrukC, idTanggalStruk, idWaktuStruk, idNomorSNStruk, idNomorTransaksiStruk, idTotalPembelianStruk;
    Button buttondownloadPDF, cetakStrukPDF;
    TextView header, footer;
    ImageView EditHeader, EditFooter;
    int PERMISSION_ALL = 1;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    TextView myLabel;
    String alamat;

    private final Locale locale = new Locale("id", "ID");
    private final DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", locale);
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    String[] options;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    String title;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    EditText pilihPerangkat;
    private ListPopupWindow serverpopup;

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

        header.setText(Preference.getHeader(getApplicationContext()));
        footer.setText(Preference.getFooter(getApplicationContext()));

        EditFooter.setOnClickListener(v -> {
            popUpEditFooter();

        });

        EditHeader.setOnClickListener(v -> {

            popUpEditHeader();

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
                saveImage();
            }
        });

        cetakStrukPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printBluetooth();

            }
        });

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

    public void popUpEditHeader() {

        AlertDialog dialogBuilder = new AlertDialog.Builder(cetak.this).create();
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


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImage() {

        Rect bounds = new Rect();
        Bitmap bmp = Bitmap.createBitmap(1400, 1700, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(-1);
        Paint paint = new Paint();
        Typeface type3 = ResourcesCompat.getFont(getApplicationContext(), R.font.exobold);
        paint.setTypeface(type3);
        paint.setTextSize(70);
        paint.setColor(Color.rgb(0, 0, 0));

        Paint paint4 = new Paint();

        paint4.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray4));
        Typeface type5 = ResourcesCompat.getFont(getApplicationContext(), R.font.mukta);
        paint4.setTypeface(type5);
        paint4.setTextSize(52);

        int y = 150; // x = 10,
        int tambahan2 = 150;
        int x = 10;

        String text = titlestrukC.getText().toString();
        String headerr = header.getText().toString();

        paint.getTextBounds(text, 0, text.length(), bounds);
        x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        canvas.drawText(text, x, y + tambahan2, paint);

        Paint paint1 = new Paint();

        paint1.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray4));
        Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.courierprimereguler);
        paint1.setTypeface(type2);
        paint1.setTextSize(72);
        canvas.drawText(headerr, 120, y, paint4);
        int left = 120;
        int tambahan = 50 + tambahan2;

        canvas.drawText("Nomor  : ", left, 250 + tambahan, paint1);
        canvas.drawText(idNomorStruk.getText().toString(), 460, 250 + tambahan, paint1);

        String produk = idProdukStruk.getText().toString();

        if (produk.length() >= 50) {
            canvas.drawText("Produk : ", left, 350 + tambahan, paint1);
            canvas.drawText(produk.substring(0, 18), 460, 350 + tambahan, paint1);
            canvas.drawText(produk.substring(19, 36), 460, 450 + tambahan, paint1);
            canvas.drawText(produk.substring(37), 460, 550 + tambahan, paint1);
            tambahan = 230;

        } else if (produk.length() >= 20) {

            canvas.drawText("Produk : ", left, 350 + tambahan, paint1);
            canvas.drawText(produk.substring(0, 18), 460, 350 + tambahan, paint1);
            canvas.drawText(produk.substring(19), 460, 450 + tambahan, paint1);
            tambahan = 130;
        } else {

            canvas.drawText("Produk : ", left, 350 + tambahan, paint1);
            canvas.drawText(produk, 460, 350 + tambahan, paint1);
        }
//
        canvas.drawText("Tanggal:", left, 450 + tambahan, paint1);
        canvas.drawText(idTanggalStruk.getText().toString(), 460, 450 + tambahan, paint1);
//
        canvas.drawText("Waktu  :", left, 550 + tambahan, paint1);
        canvas.drawText(idWaktuStruk.getText().toString(), 460, 550 + tambahan, paint1);

        canvas.drawText("Trx ID :", left, 650 + tambahan, paint1);
        canvas.drawText(idNomorTransaksiStruk.getText().toString(), 460, 650 + tambahan, paint1);
//
        canvas.drawText("Total  :", left, 750 + tambahan, paint1);
        canvas.drawText(idTotalPembelianStruk.getText().toString(), 460, 750 + tambahan, paint1);
        canvas.drawText("---------------------------", left, 850 + tambahan, paint1);
        String sn = idNomorSNStruk.getText().toString();
        if (sn.length() >= 42) {
            canvas.drawText("SN :", left, 950 + tambahan, paint1);
            canvas.drawText(sn.substring(0, 21), 300, 950 + tambahan, paint1);
            canvas.drawText(sn.substring(22, 41), 300, 1050 + tambahan, paint1);
            canvas.drawText(sn.substring(41), 300, 1150 + tambahan, paint1);

        } else if (sn.length() >= 25) {
            canvas.drawText("SN :", left, 950 + tambahan, paint1);
            canvas.drawText(sn.substring(0, 21), 300, 950 + tambahan, paint1);
            canvas.drawText(sn.substring(22), 300, 1050 + tambahan, paint1);
        } else {
            canvas.drawText("SN :", left, 950 + tambahan, paint1);
            canvas.drawText(sn, 300, 950 + tambahan, paint1);
        }

        Paint paint3 = new Paint();

        paint3.setColor(ContextCompat.getColor(getApplicationContext(), R.color.gray4));
        Typeface type4 = ResourcesCompat.getFont(getApplicationContext(), R.font.mukta);
        paint3.setTypeface(type4);
        paint3.setTextSize(36);

        canvas.drawText("* Struk ini merupakan bukti pembayaran yang sah ", left, 1250 + tambahan, paint3);
        canvas.drawText("mohon disimpan,Terimakasih", left, 1300 + tambahan, paint3);

        String Footer = footer.getText().toString();

        if (Footer.length() > 45) {
            canvas.drawText(Footer.substring(0,45), left, 1400 + tambahan, paint4);
            canvas.drawText(Footer.substring(45), left, 1450 + tambahan, paint4);
        } else {
            canvas.drawText(Footer, left, 1400 + tambahan, paint4);
        }


        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);


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
                        "[L]<b>Nomor</b>[R]" + idNomorStruk.getText().toString() + "\n" +
//                        "[L]\n" +
                        "[L]<b>Nama</b>[R]" + namaTPCC.getText().toString() + "\n" +
                        //"[L]\n" +
                        "[L]<b>Produk</b>[R]" + idProdukStruk.getText().toString() + "\n" +
//                        "[L]\n" +
                        "[L]<b>Transaksi</b>[R]" + idNomorTransaksiStruk.getText().toString() + "\n" +
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

    public void doPrint(View view) {
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd ':' HH:mm:ss");
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, cetak.PERMISSION_BLUETOOTH);
            } else {
                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                    final String text =
                            "[C]<u><font size='big'>" + titlestrukC.getText().toString() + "</font></u>\n" +
                                    "[C]\n" +
                                    "[C]" + "JL " + getAlamat() + "\n" +
                                    "[C]<u type='double'>" + format.format(new Date()) + "</u>\n" +
                                    "[C]\n" +
                                    "[C]<b>STRUK PEMBELIAN<b>\n" +
                                    "[C]================================\n" +
                                    "[L]\n" +
                                    "[L]<b>Nomor</b>[R]" + idNomorStruk.getText().toString() + "\n" +
//                        "[L]\n" +
                                    "[L]<b>Nama</b>[R]" + namaTPCC.getText().toString() + "\n" +
                                    //"[L]\n" +
                                    "[L]<b>Produk</b>[R]" + idProdukStruk.getText().toString() + "\n" +
//                        "[L]\n" +
                                    "[L]<b>Transaksi</b>[R]" + idNomorTransaksiStruk.getText().toString() + "\n" +
                                    "[C]--------------------------------\n" +
//                        "[L]\n" +
                                    "[L]<b>Total Bayar</b>[R]" + idTotalPembelianStruk.getText().toString() + "\n" +
                                    "[L]\n" +
                                    "[C]" + "SN " + idNomorSNStruk.getText().toString() + "\n" +
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

}
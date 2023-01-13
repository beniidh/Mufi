package com.c.atmpay.Notifikasi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.c.atmpay.Notifikasi.Pesan.detail_pesan_activity;
import com.c.atmpay.R;
import com.c.atmpay.menuUtama.PulsaPrabayar.TransaksiPending;
import com.c.atmpay.sharePreference.Preference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {

    LocalBroadcastManager localBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    public void createNotification(String judul, String isi, Context context, Intent intent, String transaksi, String id) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent resultIntent;
        if (transaksi.equals("NOTIF")) {
            resultIntent = new Intent(this, detail_pesan_activity.class);
            resultIntent.putExtra("id", id);

        }else {
            resultIntent = new Intent(this, TransaksiPending.class);
            resultIntent.putExtra("transaksid", transaksi);


        }
        PendingIntent PI = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_IMMUTABLE);
        int NotificationID = 0;
        String ChanelID = "Abata";
        String ChanelName = "AbataChannel";
        int Impoortance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(ChanelID, ChanelName, Impoortance);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder MBuilder = new NotificationCompat.Builder(context, ChanelID)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logobarusetianobg)
                .setContentTitle(judul)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(isi))
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(PI)
                .setContentText(isi);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(intent);
        notificationManager.notify(NotificationID++, MBuilder.build());

        Intent refresh = new Intent("refresh");
        sendBroadcast(refresh);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String judul = remoteMessage.getNotification().getTitle();
        String isi = remoteMessage.getNotification().getBody();
        String trx = remoteMessage.getNotification().getTag();
        String url = remoteMessage.getData().get("id");

        Log.v("iddanpesan", trx + url);

//        localBroadcastManager.sendBroadcast( new Intent("kirim"));
        Preference.setNilaiNotif(getApplicationContext(), 1 + Preference.getNilaiNotif(getApplicationContext()));
        createNotification(judul, isi, getApplicationContext(), new Intent(), trx, url);
//        "NOTIF"

    }


}

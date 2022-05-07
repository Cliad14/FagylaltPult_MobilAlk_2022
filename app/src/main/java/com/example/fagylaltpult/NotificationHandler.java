package com.example.fagylaltpult;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNEL_ID = "fagylalt_channel";
    private NotificationManager mManager;
    private Context context;
    private final int NOTIFICATION_ID = 0;


    public NotificationHandler(Context context) {
        this.context = context;
        this.mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "fagylalt notification", NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(android.R.color.holo_red_dark);
        channel.setDescription("Notification from FagylaltPult");
        this.mManager.createNotificationChannel(channel);
    }

    public void send(String message){
        Intent intent = new Intent(context, PultActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle("Fagyi app")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_exit)
                .setContentIntent(pendingIntent);
        this.mManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void cancel(){
        this.mManager.cancel(NOTIFICATION_ID);
    }

}


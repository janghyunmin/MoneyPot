package com.quantec.moneypot.util.FireBase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.quantec.moneypot.activity.TopTest;
import com.quantec.moneypot.R;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
    }

    @Override

    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Log.e("Message data payload", "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.e("Notification", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), "");

//            Log.e("노티로받은 데이터","데이터 : "+remoteMessage.getData().get("urlLink"));
        }else{

            Log.e("노티로받은 데이터111111","데이터111111 : "+remoteMessage.getData().get("urlLink"));
            sendNotification("백그라운드", "시작합니다.", remoteMessage.getData().get("urlLink"));
        }
    }



    private void sendNotification(String title, String message, String activityName) {


        Log.e("노티로받은 데이터22222","데이터2222 : "+activityName);
        Intent showIntent  = new Intent(this, TopTest.class);
        showIntent.putExtra("pushType", activityName);

//        String url ="activity://activityauthfidosingle";
//        Intent showIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        showIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, showIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /**
         * 오레오 버전부터는 Notification Channel이 없으면 푸시가 생성되지 않는 현상이 있습니다.
         * **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channel = "채널";
            String channel_nm = "채널명";

            NotificationManager notichannel = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm,
                    android.app.NotificationManager.IMPORTANCE_DEFAULT);
            channelMessage.setDescription("채널에 대한 설명.");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setShowBadge(false);
            channelMessage.setVibrationPattern(new long[]{100, 200, 100, 200});
            notichannel.createNotificationChannel(channelMessage);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channel)
                            .setSmallIcon(R.drawable.start_off)
                            .setColor(0xffff0000)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setWhen(System.currentTimeMillis())
                            .setChannelId(channel)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                            .setContentIntent(contentIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(9999, notificationBuilder.build());

        } else {

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, "")
                            .setSmallIcon(R.drawable.start_off)
//                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.img_app_icon) )
                            .setColor(0xffff0000)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setWhen(System.currentTimeMillis())
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                            .setContentIntent(contentIntent);
//
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(9999, notificationBuilder.build());

        }
    }

}

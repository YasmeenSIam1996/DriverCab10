package com.example.drivercab10.FirebaseUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.drivercab10.HomeActivity;
import com.example.drivercab10.Interfaces.ObjectClickListener;
import com.example.drivercab10.OrderCounterActivity;
import com.example.drivercab10.R;
import com.example.drivercab10.model.OrderNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "MyFirebaseMsgService";
    public static  int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private String date = "";
    private OrderNotification orderNotification;
    private static ObjectClickListener objectClickListener;


    public static void setOnItemClickListener(ObjectClickListener clickListener) {
        objectClickListener = clickListener;
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("MyFirebaseIIDService2", remoteMessage.getNotification().getTitle());
        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Gson gson = new Gson();
            orderNotification = gson.fromJson(object.toString(), OrderNotification.class);
            NOTIFICATION_ID = Integer.valueOf(orderNotification.getStatus_id());
            Log.e("JSON_OBJECT", object.toString());
            objectClickListener.onItemClickListener(orderNotification);
        } catch (Exception e) {
        }

        String title = "";
        if (remoteMessage.getNotification().getTitle() != null) {
            title = remoteMessage.getNotification().getTitle();
        }

        String message = "";
        if (remoteMessage.getNotification().getBody() != null) {
            message = remoteMessage.getNotification().getBody();
        }


        sendNotification(title, message, Integer.valueOf(orderNotification.getStatus_id()));

    }

    private void sendNotification(String msg, String body, int StatusId) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            mNotificationManager.cancelAll();

        } catch (Exception e) {

        }
        Intent intent;
        if (StatusId == 2) {

            intent = new Intent(getApplicationContext(), OrderCounterActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), HomeActivity.class);

        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        Bundle bundle = new Bundle();
        intent.putExtra("Status_id", Integer.valueOf(orderNotification.getStatus_id()));
        bundle.putSerializable("OrderNotification", orderNotification);
        intent.putExtras(bundle);

        PendingIntent contentIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                intent, 0);

        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.logo_launcher)
                        .setContentTitle(msg)
                        .setStyle(new Notification.BigTextStyle()
                                .bigText(body))
//                        .setVibrate(new long[]{100, 500})
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setAutoCancel(true)
                        .setContentText(body);

        mBuilder.setContentIntent(contentIntent);

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


    }

}

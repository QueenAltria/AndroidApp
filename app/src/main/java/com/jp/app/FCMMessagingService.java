package com.jp.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null) {
            sendNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        } else {
            sendNotification(getApplicationContext(), remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
        }

//        FirebaseInstanceId.getInstance()
//                .getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.e("FCMDemo", "getInstanceId failed", task.getException());
//                            return;
//                        }
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//                        Log.e("FCMDemo", "token: " + token);
//                    }
//                });

//        FirebaseInstanceId.getInstance()
//                .getInstanceId()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.e("FCMDemo", "getInstanceId failed", task.getException());
//                        return;
//                    }
//                    // Get new Instance ID token
//                    String token = task.getResult().getToken();
//                    Log.e("FCMDemo", "token: " + token);
//                });
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    private void sendNotification(Context iContext, String messageTitle, String messageBody) {

        NotificationManager notificationManager = (NotificationManager) iContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class); // 接收到通知后，点击通知，启动 MessageActivity

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long[] pattern = {500,500,500,500,500};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"-1")
                .setTicker(messageTitle)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("push 通知 标题")
                .setAutoCancel(true)
                .setContentText(messageBody)
                .setWhen(System.currentTimeMillis())
                .setVibrate(pattern)
                .setLights(Color.BLUE, 1, 1);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);

        builder.setContentIntent(pendingIntent);
//        builder.setFullScreenIntent(pendingIntent, true);//将一个Notification变成悬挂式Notification

        if (notificationManager != null) {
            notificationManager.notify(0, builder.build());
        }
    }
}
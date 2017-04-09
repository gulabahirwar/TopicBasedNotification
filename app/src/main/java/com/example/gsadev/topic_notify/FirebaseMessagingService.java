package com.example.gsadev.topic_notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.gsadev.topic_notify.MainActivity;
import com.example.gsadev.topic_notify.R;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by GSA Dev on 4/4/2017.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    Bitmap bitmap;
    String value;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData().size()>0){
            String imageUri=remoteMessage.getData().get("image");
            value=remoteMessage.getData().get("value");
            Log.e("image",""+imageUri);
            bitmap=getBitmapImage(imageUri);
            displayNotification("aa","sa",bitmap);
        }else{
            Log.e("Notification Data","null");
        }
        Log.e("message","Notification null"+remoteMessage.getNotification().getBody());
        if (remoteMessage.getNotification()!=null){
            displayNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),bitmap);
        }else{
            Log.e("error","Notification null");
        }

    }

    private Bitmap getBitmapImage(String imageUri) {
        try {
            URL url=new URL(imageUri);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            InputStream inputStream=connection.getInputStream();
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void displayNotification(String title, String desc, Bitmap bitmap){

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap))
                        .setContentTitle(title)
                        .setContentText(desc);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        if(value!=null){
        notificationIntent.putExtra("index",value);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(contentIntent);

        Random random=new Random(121);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(random.nextInt(), builder.build());



    }

}

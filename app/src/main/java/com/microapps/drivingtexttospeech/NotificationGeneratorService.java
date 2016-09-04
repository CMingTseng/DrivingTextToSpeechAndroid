package com.microapps.drivingtexttospeech;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Locale;

/**
 * {@link TextToSpeech} needs service context
 */
public class NotificationGeneratorService extends Service {

    private boolean mIsDriving = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        mIsDriving = intent.getBooleanExtra(Utils.KEY_EVENT, false);

        generateNotification();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void generateNotification() {
        String message = mIsDriving ? "You are currently driving" : "You are not driving at the moment";

        Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

        builder.setContentTitle(getApplicationContext().getString(R.string.app_name)).setContentText(message)
                .setSmallIcon(R.drawable.neura_sdk_notification_status_icon)
                .setLargeIcon(largeIcon)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))
                .setWhen(System.currentTimeMillis());

        if (mIsDriving)
            sayText(getApplicationContext(), message);

        Notification notification = builder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(0, notification);
    }

    private TextToSpeech mTextToSpeech;

    public void sayText(Context context, final String message) {

        mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                try {
                    if (mTextToSpeech != null && status == TextToSpeech.SUCCESS) {
                        mTextToSpeech.setLanguage(Locale.US);
                        mTextToSpeech.speak(message, TextToSpeech.QUEUE_ADD, null);
                    }
                } catch (Exception ex) {
                    System.out.print("Error handling TextToSpeech GCM notification " + ex.getMessage());
                }
            }
        });
    }
}

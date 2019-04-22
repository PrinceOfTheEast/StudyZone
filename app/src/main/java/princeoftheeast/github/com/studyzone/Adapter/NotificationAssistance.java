package princeoftheeast.github.com.studyzone.Adapter;
/*
https://www.youtube.com/watch?v=yrpimdBRk5Q&list=PLrnPJCHvNZuDR7-cBjRXssxYK0Y5EEKzr&index=13
The above YouTube tutorial was consulted in creating this adapter
***/

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import princeoftheeast.github.com.studyzone.R;
import princeoftheeast.github.com.studyzone.UI.SoundActivity;
import princeoftheeast.github.com.studyzone.UI.SoundMeterActivity;
import princeoftheeast.github.com.studyzone.UI.TimerActivity;


public class NotificationAssistance extends ContextWrapper {
    public static final String chID = "channelID";
    public static final String chlName = "Channel Name";

    private NotificationManager notificationManager;

    public NotificationAssistance(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(chID, chlName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        PendingIntent participateIntent = PendingIntent.getActivity(this, 0, new Intent(this, TimerActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent noiseLevelIntent = PendingIntent.getActivity(this, 0, new Intent(this, SoundMeterActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent soundSelectionIntent = PendingIntent.getActivity(this, 0, new Intent(this, SoundActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(getApplicationContext(), chID)
                .setContentTitle("Study Time!")
                .setContentText("A study session has started. Click to participate")
                .setSmallIcon(R.drawable.ic_timetable)
                .setContentIntent(participateIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_timetable, "Participate", participateIntent)
                .addAction(R.drawable.ic_timetable, "Noise level", noiseLevelIntent)
                .addAction(R.drawable.ic_timetable, "Sounds/Music", soundSelectionIntent);

        return  mBuilder;
    }
}

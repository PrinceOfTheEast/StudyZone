package princeoftheeast.github.com.studyzone.Adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationAssistance notificationAssistance = new NotificationAssistance(context);
        NotificationCompat.Builder notificationBuilder = notificationAssistance.getChannelNotification();
        notificationAssistance.getManager().notify(1, notificationBuilder.build());

    }
}


package com.czw.project.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by xiaowei on 2015/9/8.
 */
public class AppNotification {
    private NotificationManager manager;
    public AppNotification(Context context,String title,String content,int drawable){
        manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(drawable,title,System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent notificationIntent = new Intent();
        PendingIntent pedingIntent = PendingIntent.getActivity(context,0, notificationIntent, 0);
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.vibrate  = new long[]{0,100,200,300};
       // notification.setLatestEventInfo(context, title, content, pedingIntent);

        manager.notify(drawable,notification);
    }
}

package com.marklei.myarchitectu.base_api.clock;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.marklei.myarchitectu.R;

/**
 * Created by leihao on 2017/10/19.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context);
    }

    private void sendNotification(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.ic_launcher);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setLargeIcon(bitmap);
        mBuilder.setContentTitle("标题");
        mBuilder.setContentText("正文");
        mBuilder.setSubText("摘要");
        mBuilder.setAutoCancel(true);
        mBuilder.setContentInfo("Info");
        mBuilder.setNumber(2);
        mBuilder.setTicker("在状态栏显示");
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        mBuilder.setWhen(System.currentTimeMillis() - 3600000);
        mBuilder.setOngoing(true);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setVibrate(new long[]{0, 1000, 1000, 1000});
        Intent intent = new Intent(context, AlarmClockActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentIntent(pIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}

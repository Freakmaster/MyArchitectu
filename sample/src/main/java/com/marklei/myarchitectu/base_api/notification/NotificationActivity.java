package com.marklei.myarchitectu.base_api.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.marklei.myarchitectu.R;

/**
 * Created by leihao on 2017/10/18.
 */

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    public void sendNotification() {
        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
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
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}

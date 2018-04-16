package com.marklei.myarchitectu.base_api.clock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by leihao on 2017/10/19.
 */

public class AlarmService extends Service {

    String hour, minute;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        hour = intent.getStringExtra("Hour");
        minute = intent.getStringExtra("Minute");
        Log.d("Mark", "开启闹钟, " + hour + ":" + minute);
        startRemind();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startRemind() {
        Calendar mCalendar = Calendar.getInstance();
        long systemTime = System.currentTimeMillis();
        mCalendar.setTimeInMillis(systemTime);
        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
        mCalendar.set(Calendar.MINUTE, Integer.valueOf(minute));
        long selectTime = mCalendar.getTimeInMillis();

        if (systemTime > selectTime) {
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pi);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRemind();
    }

    private void stopRemind() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(pi);
    }
}

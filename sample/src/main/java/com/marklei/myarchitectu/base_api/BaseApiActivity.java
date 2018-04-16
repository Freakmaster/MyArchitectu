package com.marklei.myarchitectu.base_api;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marklei.myarchitectu.base_api.camera.CameraActivity;
import com.marklei.myarchitectu.base_api.clock.AlarmClockActivity;
import com.marklei.myarchitectu.base_api.notification.NotificationActivity;

/**
 * Created by leihao on 2018/3/16.
 */

public class BaseApiActivity extends ListActivity {

    public static final String[] options = {"Notification", "AlarmClock", "Camera2"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent;
        switch (position) {
            default:
            case 0:
                intent = new Intent(this, NotificationActivity.class);
                break;
            case 1:
                intent = new Intent(this, AlarmClockActivity.class);
                break;
            case 2:
                intent = new Intent(this, CameraActivity.class);
                break;
        }
        startActivity(intent);
    }
}

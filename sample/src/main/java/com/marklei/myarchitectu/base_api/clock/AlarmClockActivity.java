package com.marklei.myarchitectu.base_api.clock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.marklei.myarchitectu.R;


/**
 * Created by leihao on 2017/10/19.
 */

public class AlarmClockActivity extends AppCompatActivity {

    private Spinner sp_hour, sp_min;
    private Button btn_start, btn_stop;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);
        initView();
    }

    private void initView() {
        sp_hour = (Spinner) findViewById(R.id.spinner_hour);
        sp_min = (Spinner) findViewById(R.id.spinner_minute);
        intent = new Intent(this, AlarmService.class);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Hour", sp_hour.getSelectedItem().toString());
                intent.putExtra("Minute", sp_min.getSelectedItem().toString());
                startService(intent);
            }
        });
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
    }
}

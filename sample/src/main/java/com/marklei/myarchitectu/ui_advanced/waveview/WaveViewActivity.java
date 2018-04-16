package com.marklei.myarchitectu.ui_advanced.waveview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.marklei.myarchitectu.R;

/**
 * Created by leihao on 2017/9/26.
 */

public class WaveViewActivity extends AppCompatActivity {

    private WaveView mWaveView;
    private Button mReset;
    private Button mStartWave;
    private Button mStartShip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_view);
        initView();
    }

    private void initView() {
        mWaveView = (WaveView) findViewById(R.id.wave_view);
        mReset = (Button) findViewById(R.id.reset);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaveView.reset();
            }
        });
        mStartWave = (Button) findViewById(R.id.start_wave_animation);
        mStartWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaveView.startAnimWave();
            }
        });
        mStartShip = (Button) findViewById(R.id.start_ship_animation);
        mStartShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaveView.startAnimShip();
            }
        });
    }
}

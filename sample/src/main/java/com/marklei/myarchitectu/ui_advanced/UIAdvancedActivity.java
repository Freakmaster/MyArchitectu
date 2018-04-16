package com.marklei.myarchitectu.ui_advanced;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marklei.myarchitectu.ui_advanced.pulltorefresh_list.PullToRefreshListActivity;
import com.marklei.myarchitectu.ui_advanced.waveview.WaveViewActivity;

/**
 * Created by leihao on 2017/8/18.
 */

public class UIAdvancedActivity extends ListActivity {

    public static final String[] options = {"PullToRefresh", "WaveView"};

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
                intent = new Intent(this, PullToRefreshListActivity.class);
                break;
            case 1:
                intent = new Intent(this, WaveViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}

package com.marklei.myarchitectu;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marklei.myarchitectu.base_api.BaseApiActivity;
import com.marklei.myarchitectu.ui_advanced.UIAdvancedActivity;

public class MainActivity extends ListActivity {

    public static final String[] options = {"高级UI", "Base Api"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent;
        switch (position) {
            default:
            case 0:
                intent = new Intent(this, UIAdvancedActivity.class);
                break;
            case 1:
                intent = new Intent(this, BaseApiActivity.class);
                break;
        }
        startActivity(intent);
    }
}

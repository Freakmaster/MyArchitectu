package com.marklei.myarchitectu.ui_advanced.data.source;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by leihao on 2018/3/20.
 */

public interface DataSource {

    interface LoadDataCallback {

        void onDataLoaded(List<String> data);

        void onDataNotAvailable();
    }

    void getData(@NonNull LoadDataCallback callback);

    void refreshData();
}

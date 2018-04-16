package com.marklei.myarchitectu.ui_advanced.data.source;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.LinkedList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by leihao on 2018/3/20.
 */

public class DataRepository implements DataSource {

    private volatile static DataRepository INSTANCE = null;

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};
    private LinkedList<String> mCachedData;

    private boolean mCacheIsDirty = false;

    private DataRepository() {
        mCachedData = new LinkedList<>();
        mCachedData.addAll(Arrays.asList(mStrings));
    }

    public static DataRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getData(@NonNull LoadDataCallback callback) {
        checkNotNull(callback);
        if (mCachedData != null && !mCacheIsDirty) {
            callback.onDataLoaded(mCachedData);
            return;
        }
        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getDataFromRemoteDataSource(callback);
        } else {    //缓存数据为空
            // Query the local storage if available. If not, query the network.
            //TODO 查询数据库
        }
    }

    /**
     * 模拟获取远程数据
     * Note: {@link LoadDataCallback#onDataNotAvailable()} is never fired. In a real remote data
     * source implementation, this would be fired if the server can't be contacted or the server
     * returns an error.
     */
    private void getDataFromRemoteDataSource(final LoadDataCallback callback) {
        // Simulate network by delaying the execution.
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCachedData.addFirst("Added after refresh...");
                callback.onDataLoaded(mCachedData);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void refreshData() {
        mCacheIsDirty = true;
    }
}

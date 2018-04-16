package com.marklei.myarchitectu.ui_advanced.pulltorefresh_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.marklei.myarchitectu.R;
import com.marklei.myarchitectu.ui_advanced.mock.Injection;
import com.marklei.myarchitectu.util.EspressoIdlingResource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by leihao on 2017/8/18.
 */

public class PullToRefreshListActivity extends AppCompatActivity implements PullToRefreshListContract.View {

    private PullToRefreshListContract.Presenter mPresenter;
    private PullToRefreshListView mPullRefreshListView;
    private List<String> mListItems;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh_list);
        initView();

        // Create the presenter
        mPresenter = new PullToRefreshListPresenter(
                Injection.provideUseCaseHandler(),
                this,
                Injection.provideGetData(getApplicationContext()));
    }

    private void initView() {
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listView);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                mPresenter.refresh();
            }
        });

        ListView actualListView = mPullRefreshListView.getRefreshableView();
        mListItems = new LinkedList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mListItems);

        // You can also just use mPullRefreshListView.setAdapter(mAdapter)
        actualListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(PullToRefreshListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (!active) {
            mPullRefreshListView.onRefreshComplete();
        }
    }

    @Override
    public void showList(List<String> list) {
        mListItems.clear();
        mListItems.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingDataError() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}

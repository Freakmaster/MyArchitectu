package com.marklei.myarchitectu.ui_advanced.pulltorefresh_list;

import android.support.annotation.NonNull;

import com.marklei.myarchitectu.UseCase;
import com.marklei.myarchitectu.UseCaseHandler;
import com.marklei.myarchitectu.ui_advanced.pulltorefresh_list.usecase.GetData;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by leihao on 2018/3/17.
 */

public class PullToRefreshListPresenter implements PullToRefreshListContract.Presenter {

    private final UseCaseHandler mUseCaseHandler;
    private final PullToRefreshListContract.View mView;
    private final GetData mGetData;

    private boolean mFirstLoad = false;

    public PullToRefreshListPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull PullToRefreshListContract.View view, @NonNull GetData getData) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mView = checkNotNull(view, "ListView cannot be null!");
        mGetData = checkNotNull(getData, "getData cannot be null!");

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadList(false);
    }

    @Override
    public void loadList(boolean forceUpdate) {
        loadList(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadList(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mView.setLoadingIndicator(true);
        }

        GetData.RequestValues requestValue = new GetData.RequestValues(forceUpdate);

        mUseCaseHandler.execute(mGetData, requestValue, new UseCase.UseCaseCallback<GetData.ResponseValue>() {
            @Override
            public void onSuccess(GetData.ResponseValue response) {
                List<String> data = response.getData();
                // The view may not be able to handle UI updates anymore
                if (!mView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mView.setLoadingIndicator(false);
                }
                processData(data);
            }

            @Override
            public void onError() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showLoadingDataError();
            }
        });
    }

    private void processData(List<String> data) {
        if (data.isEmpty()) {
            mView.showNoData();
        } else {
            mView.showList(data);
        }
    }

    @Override
    public void refresh() {
        loadList(true);
    }

}

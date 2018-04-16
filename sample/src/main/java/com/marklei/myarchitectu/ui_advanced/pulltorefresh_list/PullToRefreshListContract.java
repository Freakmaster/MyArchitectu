package com.marklei.myarchitectu.ui_advanced.pulltorefresh_list;

import com.marklei.myarchitectu.BasePresenter;
import com.marklei.myarchitectu.BaseView;

import java.util.List;

/**
 * Created by leihao on 2017/8/18.
 */

public interface PullToRefreshListContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showList(List<String> list);

        void showLoadingDataError();

        void showNoData();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void loadList(boolean forceUpdate);

        void refresh();
    }

}

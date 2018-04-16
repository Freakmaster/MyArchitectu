package com.marklei.myarchitectu.ui_advanced.pulltorefresh_list.usecase;

import android.support.annotation.NonNull;

import com.marklei.myarchitectu.UseCase;
import com.marklei.myarchitectu.ui_advanced.data.source.DataRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by leihao on 2018/3/20.
 */

public class GetData extends UseCase<GetData.RequestValues, GetData.ResponseValue> {

    private final DataRepository mDataRepository;

    public GetData(@NonNull DataRepository dataRepository) {
        mDataRepository = checkNotNull(dataRepository, "dataRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        if (values.isForceUpdate()) {
            mDataRepository.refreshData();
        }

        mDataRepository.getData(new DataRepository.LoadDataCallback() {
            @Override
            public void onDataLoaded(List<String> data) {
                ResponseValue responseValue = new ResponseValue(data);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final boolean mForceUpdate;

        public RequestValues(boolean mForceUpdate) {
            this.mForceUpdate = mForceUpdate;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<String> mData;

        public ResponseValue(@NonNull List<String> data) {
            mData = checkNotNull(data, "data cannot be null!");
        }

        public List<String> getData() {
            return mData;
        }
    }
}

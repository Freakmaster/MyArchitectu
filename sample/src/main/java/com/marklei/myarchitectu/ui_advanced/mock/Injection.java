package com.marklei.myarchitectu.ui_advanced.mock;

import android.content.Context;
import android.support.annotation.NonNull;

import com.marklei.myarchitectu.UseCaseHandler;
import com.marklei.myarchitectu.ui_advanced.data.source.DataRepository;
import com.marklei.myarchitectu.ui_advanced.pulltorefresh_list.usecase.GetData;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by leihao on 2018/3/20.
 */

public class Injection {

    public static DataRepository provideDataRepository(@NonNull Context context) {
        checkNotNull(context);
        return DataRepository.getInstance();
    }

    public static GetData provideGetData(@NonNull Context context) {
        return new GetData(provideDataRepository(context));
    }

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }
}

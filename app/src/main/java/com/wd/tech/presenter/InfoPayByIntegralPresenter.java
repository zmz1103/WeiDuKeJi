package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/3/1
 * author:李阔(淡意衬优柔)
 * function:
 */
public class InfoPayByIntegralPresenter extends WDPresenter {

    private IRequest mIRequest;

    public InfoPayByIntegralPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetWorkManager.getInstance().create(IRequest.class);
        return mIRequest.infopaybyintegral((long) args[0],(String) args[1],(String) args[2],(int) args[3]);
    }
}

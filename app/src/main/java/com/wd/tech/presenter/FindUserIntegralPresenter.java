package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/19
 * author:李阔(淡意衬优柔)
 * function:
 */
public class FindUserIntegralPresenter extends WDPresenter{

    private IRequest mIRequest;

    public FindUserIntegralPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetWorkManager.getInstance().create(IRequest.class);
        return mIRequest.findUserIntegral((long)args[0],(String) args[1] );
    }
}
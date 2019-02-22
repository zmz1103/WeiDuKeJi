package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/21
 * author:李阔(淡意衬优柔)
 * function:
 */
public class InterestListPresenter extends WDPresenter {

    private IRequest mIRequest;

    public InterestListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        mIRequest = NetWorkManager.getInstance().create(IRequest.class);
        return mIRequest.showinterestlist();
    }
}

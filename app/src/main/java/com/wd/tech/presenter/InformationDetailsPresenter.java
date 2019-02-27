package com.wd.tech.presenter;

import com.wd.tech.activity.WDActivity;
import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/25
 * author:李阔(淡意衬优柔)
 * function:
 */
public class InformationDetailsPresenter extends WDPresenter {
    public InformationDetailsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.infordetails((long) args[0],(String) args[1],(String) args[2]);
    }
}

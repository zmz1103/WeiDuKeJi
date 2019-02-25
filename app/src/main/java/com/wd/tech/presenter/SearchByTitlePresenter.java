package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/24
 * author:李阔(淡意衬优柔)
 * function:
 */
public class SearchByTitlePresenter extends WDPresenter {
    public SearchByTitlePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.SearchByTitle((String) args[0],(int)args[1],(int)args[2]);
    }
}

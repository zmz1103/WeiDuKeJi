package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * function: 绑定人脸
 */
public class BindingFaceIdPresenter extends WDPresenter{
    public BindingFaceIdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.bindingFaceId((long)args[0],(String) args[1],(String) args[2] );
    }
}

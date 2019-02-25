package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * 作者: Wang on 2019/2/24 20:20
 * 寄语：加油！相信自己可以！！！
 */


public class PerFectUserInfoPresenter extends WDPresenter {
    public PerFectUserInfoPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.perfectUserInfo((long)args[0],(String)args[1],(String)args[2],(int)args[3],(String)args[4],(String)args[5],(String)args[6]);
    }
}

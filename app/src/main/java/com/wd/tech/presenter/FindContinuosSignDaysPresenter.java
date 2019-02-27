package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * 作者: Wang on 2019/2/26 11:34
 * 寄语：加油！相信自己可以！！！
 */


public class FindContinuosSignDaysPresenter extends WDPresenter {

    public FindContinuosSignDaysPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        return NetWorkManager.getInstance().create(IRequest.class).findContinuousSignDays((long)args[0],(String)args[1] );
    }
}

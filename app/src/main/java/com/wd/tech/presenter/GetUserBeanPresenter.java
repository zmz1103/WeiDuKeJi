package com.wd.tech.presenter;

import com.wd.tech.bean.GetUserBean;
import com.wd.tech.bean.Result;
import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * 作者: Wang on 2019/2/24 19:03
 * 寄语：加油！相信自己可以！！！
 */


public class GetUserBeanPresenter extends WDPresenter {
    public GetUserBeanPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);

        return iRequest.getUserInfoByUserId((long)args[0],(String) args[1]);
    }
}

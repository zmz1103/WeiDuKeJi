package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * 作者: Wang on 2019/2/26 11:34
 * 寄语：加油！相信自己可以！！！
 */


public class ModifyUserPwdPresenter extends WDPresenter {

    public ModifyUserPwdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        return NetWorkManager.getInstance().create(IRequest.class).modifyUserPwd((long) args[0], (String) args[1], (String) args[2], (String) args[3]);
    }
}

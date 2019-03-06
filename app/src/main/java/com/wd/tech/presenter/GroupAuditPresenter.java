package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/3/6 13:49
 * author:赵明珠(啊哈)
 * function:
 */
public class GroupAuditPresenter extends WDPresenter{

    public GroupAuditPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);

        return iRequest.groupaudit((int)args[0],(String)args[1],(int)args[2],(int)args[3]);
    }
}

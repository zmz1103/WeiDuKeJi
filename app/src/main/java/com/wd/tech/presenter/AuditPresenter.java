package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/27 19:59
 * author:赵明珠(啊哈)
 * function:
 */
public class AuditPresenter extends WDPresenter{

    private int page = 1;
    private int count = 5;
    private boolean isRefresh = true;

    public AuditPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        isRefresh = (boolean) args[2];

        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);


        return iRequest.audit((int)args[0],(String)args[1],page,count);
    }

    public boolean isResresh() {

        return isRefresh;
    }
}

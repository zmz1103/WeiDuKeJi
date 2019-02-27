package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/26 20:05
 * author:赵明珠(啊哈)
 * function:
 */
public class CreateGroupPresenter extends WDPresenter{
    public CreateGroupPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);

        return iRequest.creategroup((int)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }
}

package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/26 19:00
 * author:赵明珠(啊哈)
 * function:
 */
public class FindFriendPresenter extends WDPresenter{

    public FindFriendPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);

        return iRequest.phone((int)args[0],(String)args[1],(String)args[2]);
    }
}

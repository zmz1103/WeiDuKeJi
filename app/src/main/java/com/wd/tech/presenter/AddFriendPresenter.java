package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date: 2019/2/25.
 * Created 王思敏
 * function:添加好友
 */
public class AddFriendPresenter extends WDPresenter {
    public AddFriendPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.getAddFriend((int)args[0],(String) args[1],(int)args[2],(String) args[3]);
    }
}

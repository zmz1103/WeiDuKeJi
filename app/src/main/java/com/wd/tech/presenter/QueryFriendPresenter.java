package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date: 2019/2/25.
 * Created 王思敏
 * function:查询好友信息
 */
public class QueryFriendPresenter extends WDPresenter{

    public QueryFriendPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.getQueryFriend((int)args[0],(String) args[1],(int)args[2]);
    }
}

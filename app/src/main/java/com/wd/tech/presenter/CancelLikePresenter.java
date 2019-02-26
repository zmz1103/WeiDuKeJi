package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date: 2019/2/24.
 * Created 王思敏
 * function:社区展示页取消点赞
 */
public class CancelLikePresenter extends WDPresenter {
    public CancelLikePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.getcancelLike((int)args[0],(String) args[1],(int)args[2]);
    }
}

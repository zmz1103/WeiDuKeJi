package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date: 2019/3/5.
 * Created 王思敏
 * function:全部评论列表
 */
public class AllCommentPresenter extends WDPresenter{
    public AllCommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.getAllComment((int)args[0],(String) args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}

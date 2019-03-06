package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/3/6 11:42
 * author:赵明珠(啊哈)
 * function:
 */
public class GroupNoticePresenter extends WDPresenter{

    private int page = 1;
    private int count = 10;

    public GroupNoticePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);

        return iRequest.groupnotice((int)args[0],(String)args[1],page,count);
    }
}

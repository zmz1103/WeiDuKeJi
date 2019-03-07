package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date: 2019/3/6.
 * Created 王思敏
 * function:修改群简介
 */
public class ModifyGroupDescriptionPresenter extends WDPresenter{
    public ModifyGroupDescriptionPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
        return iRequest.getModifyGroupDescription((int)args[0],(String) args[1],(int)args[2],(String) args[3]);
    }
}

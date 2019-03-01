package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/28 19:46
 * author:赵明珠(啊哈)
 * function:审核
 */
public class VerifierPresenter extends WDPresenter{

    public VerifierPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {

        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);

        return iRequest.verifier((int)args[0],(String)args[1],(int)args[2],(int)args[3]);
    }
}

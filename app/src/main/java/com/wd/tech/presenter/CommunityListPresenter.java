package com.wd.tech.presenter;

import com.wd.tech.https.IRequest;
import com.wd.tech.https.NetWorkManager;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date: 2019/2/19.
 * Created 王思敏
 * function:社区列表
 */
public class CommunityListPresenter extends WDPresenter {
    private int page = 1;
    public CommunityListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.getInstance().create(IRequest.class);
//        boolean refresh = (boolean)args[2];
//        if (refresh){
//            page = 1;
//        }else{
//            page++;
//        }
        return iRequest.getCommunitylist((int)args[0],(String) args[1],(int)args[2],(int)args[3]);
    }
}

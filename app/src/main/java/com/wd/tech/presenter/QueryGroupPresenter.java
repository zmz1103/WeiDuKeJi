package com.wd.tech.presenter;

import com.wd.tech.view.DataCall;

import io.reactivex.Observable;

/**
 * date:2019/2/20 15:12
 * author:赵明珠(啊哈)
 * function:
 */
public class QueryGroupPresenter extends WDPresenter{
    public QueryGroupPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        return null;
    }
}

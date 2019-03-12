package com.wd.tech.presenter;

import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.Result;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.CustomException;
import com.wd.tech.exception.ResponseTransformer;
import com.wd.tech.view.DataCall;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * date:2019/2/18 20:18
 * author:赵明珠(啊哈)
 * function:presenter基类
 */
public abstract class WDPresenter<T> {
    private DataCall dataCall;

    boolean running;

    public WDPresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    protected abstract Observable observable(Object...args);
    public void reqeust(Object...args) {

        if (running ) {
            return;
        }

        running = true;

        observable(args)
                .compose(ResponseTransformer.handleResult())
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result)  {
                        running = false;
                        if (result.getStatus().equals("9999")) {
                            DaoSession daoSession = DaoMaster.newDevSession(WDApplication.getAppContext(), UserDao.TABLENAME);
                            daoSession.getUserDao().deleteAll();
                        }
                        dataCall.success(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        running = false;
                        dataCall.fail(CustomException.handleException(throwable));
                    }
                });

    }

    public boolean Running(){
        return running;
    }
}

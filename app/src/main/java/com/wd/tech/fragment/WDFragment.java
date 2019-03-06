package com.wd.tech.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wd.tech.activity.NoNetWorkActivity;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.UserDao;
import com.wd.tech.util.NetWorkUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * date:2019/2/19 10:31
 * author:赵明珠(啊哈)
 * function:
 */
public abstract class WDFragment extends Fragment {
    private Unbinder unbinder;
    public User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContent(), container, false);

        unbinder = ButterKnife.bind(this, view);


        List<User> users = WDApplication.getAppContext().getUserDao().loadAll();
        if (users.size() == 0) {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }else{
            user = users.get(0);
        }

        if (NetWorkUtils.isNetworkAvailable(WDApplication.getAppContext())) {

        } else {
            Toast.makeText(WDApplication.getAppContext(), "ffff请检查网络", Toast.LENGTH_SHORT).show();

        }
        initView(view);
        return view;
    }

    public abstract int getContent();

    public abstract void initView(View view);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (WDApplication.getAppContext().getUserDao().loadAll().size()>0) {
            user = WDApplication.getAppContext().getUserDao().loadAll().get(0);
        }
    }
}

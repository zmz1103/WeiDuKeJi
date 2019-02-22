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
    public UserDao userDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContent(), container, false);

        unbinder = ButterKnife.bind(this, view);

        DaoSession daoSession = DaoMaster.newDevSession(WDApplication.getAppContext(), UserDao.TABLENAME);
        userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        if (users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getSole() == 1) {
                    user = users.get(i);
                    break;
                }
            }
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
}

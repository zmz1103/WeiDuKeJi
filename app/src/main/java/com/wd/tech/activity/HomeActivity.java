package com.wd.tech.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.UserDao;
import com.wd.tech.fragment.CommunityFragment;
import com.wd.tech.fragment.HaveUserFragment;
import com.wd.tech.fragment.HomeFragment;
import com.wd.tech.fragment.MessageFragment;
import com.wd.tech.fragment.NoUserFragment;
import com.wd.tech.util.NetWorkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

/**
 * date:2019/2/19 10:52
 * author:赵明珠(啊哈)
 * function:
 */
public class HomeActivity extends AppCompatActivity implements CustomAdapt {

    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.home_radio)
    RadioGroup radioGroup;
    @BindView(R.id.Message)
    RadioButton message;
    @BindView(R.id.Information)
    RadioButton information;
    @BindView(R.id.community)
    RadioButton community;
    @BindView(R.id.c_frame)
    FrameLayout c_frame;

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private CommunityFragment communityFragment;
    private FragmentManager manager;
    private NoUserFragment noUserFragment;
    private HaveUserFragment haveUserFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        communityFragment = new CommunityFragment();

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame, homeFragment);
        transaction.commit();
        information.setTextColor(getResources().getColorStateList(R.color.color_Text));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Message:
                        message.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        community.setTextColor(getResources().getColorStateList(R.color.colorText));
                        information.setTextColor(getResources().getColorStateList(R.color.colorText));
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.replace(R.id.frame, messageFragment);
                        transaction1.commit();
                        break;
                    case R.id.Information:
                        message.setTextColor(getResources().getColorStateList(R.color.colorText));
                        community.setTextColor(getResources().getColorStateList(R.color.colorText));
                        information.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.replace(R.id.frame, homeFragment);
                        transaction2.commit();
                        break;
                    case R.id.community:
                        message.setTextColor(getResources().getColorStateList(R.color.colorText));
                        community.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        information.setTextColor(getResources().getColorStateList(R.color.colorText));
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        transaction3.replace(R.id.frame, communityFragment);
                        transaction3.commit();
                        break;
                }
            }
        });

        // 侧拉出的页面

        UserDao userDao = DaoMaster.newDevSession(this, UserDao.TABLENAME).getUserDao();
        List<User> users = userDao.loadAll();
        if (users.size() == 0) {
            noUserLogin();
        }else{
            haveUserLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
          UserDao userDao = DaoMaster.newDevSession(this, UserDao.TABLENAME).getUserDao();
        if (userDao.loadAll().size() == 0) {
            noUserLogin();
        }else{
            haveUserLogin();
        }

    }

    private void haveUserLogin() {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        haveUserFragment = new HaveUserFragment();
        fragmentTransaction.replace(R.id.c_frame,haveUserFragment);
        fragmentTransaction.commit();
    }

    private void noUserLogin() {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        noUserFragment = new NoUserFragment();
        fragmentTransaction.replace(R.id.c_frame,noUserFragment);
        fragmentTransaction.commit();

    }


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}

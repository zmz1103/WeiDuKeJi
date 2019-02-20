package com.wd.tech.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wd.tech.R;
import com.wd.tech.fragment.CommunityFragment;
import com.wd.tech.fragment.HomeFragment;
import com.wd.tech.fragment.MessageFragment;
import com.wd.tech.util.SlidingMenu;

import butterknife.BindView;

/**
 * date:2019/2/19 10:52
 * author:赵明珠(啊哈)
 * function:
 */
public class HomeActivity extends WDActivity{

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

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private CommunityFragment communityFragment;
    private FragmentManager manager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
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
                switch (checkedId){
                    case R.id.Message:
                        message.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        community.setTextColor(getResources().getColorStateList(R.color.colorText));
                        information.setTextColor(getResources().getColorStateList(R.color.colorText));
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.replace(R.id.frame,messageFragment);
                        transaction1.commit();
                        break;
                    case R.id.Information:
                        message.setTextColor(getResources().getColorStateList(R.color.colorText));
                        community.setTextColor(getResources().getColorStateList(R.color.colorText));
                        information.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.replace(R.id.frame,homeFragment);
                        transaction2.commit();
                        break;
                    case R.id.community:
                        message.setTextColor(getResources().getColorStateList(R.color.colorText));
                        community.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        information.setTextColor(getResources().getColorStateList(R.color.colorText));
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        transaction3.replace(R.id.frame,communityFragment);
                        transaction3.commit();
                        break;
                }
            }
        });

    }

    @Override
    protected void destoryData() {

    }
}

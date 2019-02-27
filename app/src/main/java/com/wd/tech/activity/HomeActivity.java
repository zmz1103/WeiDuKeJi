package com.wd.tech.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.wd.tech.R;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.UserDao;
import com.wd.tech.fragment.CommunityFragment;
import com.wd.tech.fragment.HaveUserFragment;
import com.wd.tech.fragment.HomeFragment;
import com.wd.tech.fragment.MessageFragment;
import com.wd.tech.fragment.NoUserFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date:2019/2/19 10:52
 * author:赵明珠(啊哈)
 * function:
 */
public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.frame)
    FrameLayout mFrameLayout;
    @BindView(R.id.home_radio)
    RadioGroup mRadioGroup;
    @BindView(R.id.Message)
    RadioButton mMessage;
    @BindView(R.id.Information)
    RadioButton mInformation;
    @BindView(R.id.community)
    RadioButton mCommunity;
    @BindView(R.id.c_frame)
    FrameLayout mCLayout;

    @BindView(R.id.draw)
    DrawerLayout mDraw;
    @BindView(R.id.reght)
    RelativeLayout mRight;
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private CommunityFragment mCommunityFragment;
    private FragmentManager mManager;
    private NoUserFragment mNoUserFragment;
    private HaveUserFragment mHaveUserFragment;
    private boolean mIsDrawer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mHomeFragment = new HomeFragment();
        mMessageFragment = new MessageFragment();
        mCommunityFragment = new CommunityFragment();

        mManager = getSupportFragmentManager();
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(R.id.frame, mHomeFragment);
        transaction.commit();
        mInformation.setTextColor(getResources().getColorStateList(R.color.color_Text));

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Message:
                        mMessage.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        mCommunity.setTextColor(getResources().getColorStateList(R.color.colorText));
                        mInformation.setTextColor(getResources().getColorStateList(R.color.colorText));
                        FragmentTransaction transaction1 = mManager.beginTransaction();
                        transaction1.replace(R.id.frame, mMessageFragment);
                        transaction1.commit();
                        break;
                    case R.id.Information:
                        mMessage.setTextColor(getResources().getColorStateList(R.color.colorText));
                        mCommunity.setTextColor(getResources().getColorStateList(R.color.colorText));
                        mInformation.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        FragmentTransaction transaction2 = mManager.beginTransaction();
                        transaction2.replace(R.id.frame, mHomeFragment);
                        transaction2.commit();
                        break;
                    case R.id.community:
                        mMessage.setTextColor(getResources().getColorStateList(R.color.colorText));
                        mCommunity.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        mInformation.setTextColor(getResources().getColorStateList(R.color.colorText));
                        FragmentTransaction transaction3 = mManager.beginTransaction();
                        transaction3.replace(R.id.frame, mCommunityFragment);
                        transaction3.commit();
                        break;
                    default:
                        break;
                }
            }
        });

        // 侧拉出的页面
        mDraw.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mIsDrawer = true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mRight.layout(mCLayout.getRight(), 0, mCLayout.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mIsDrawer = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        UserDao userDao = DaoMaster.newDevSession(this, UserDao.TABLENAME).getUserDao();
        List<User> users = userDao.loadAll();
        if (users.size() == 0) {
            noUserLogin();
        } else {
            haveUserLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDraw.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mIsDrawer = true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mRight.layout(mCLayout.getRight(), 0, mCLayout.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mIsDrawer = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        UserDao userDao = DaoMaster.newDevSession(this, UserDao.TABLENAME).getUserDao();
        if (userDao.loadAll().size() == 0) {
            noUserLogin();
        } else {
            haveUserLogin();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mDraw.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mIsDrawer = true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mRight.layout(mCLayout.getRight(), 0, mCLayout.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mIsDrawer = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void haveUserLogin() {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        mHaveUserFragment = new HaveUserFragment();
        fragmentTransaction.replace(R.id.c_frame, mHaveUserFragment);
        fragmentTransaction.commit();
    }

    private void noUserLogin() {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        mNoUserFragment = new NoUserFragment();
        fragmentTransaction.replace(R.id.c_frame, mNoUserFragment);
        fragmentTransaction.commit();
    }


}

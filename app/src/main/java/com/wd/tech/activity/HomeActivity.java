package com.wd.tech.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    private int show = 1;
    private UserDao userDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mManager = getSupportFragmentManager();
            mHomeFragment = (HomeFragment) mManager.findFragmentByTag("home");
            mMessageFragment = (MessageFragment) mManager.findFragmentByTag("message");
            mCommunityFragment = (CommunityFragment) mManager.findFragmentByTag("community");
            mNoUserFragment = (NoUserFragment) mManager.findFragmentByTag("nouser");
            mHaveUserFragment = (HaveUserFragment) mManager.findFragmentByTag("haveuser");
        }
        super.onCreate(savedInstanceState);
        if (mTime2 > 0) {
            finish();
        }
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mHomeFragment = new HomeFragment();
        mMessageFragment = new MessageFragment();
        mCommunityFragment = new CommunityFragment();

        mHaveUserFragment = new HaveUserFragment();
        mNoUserFragment = new NoUserFragment();
        if (mManager == null) {
            mManager = getSupportFragmentManager();
        }
        FragmentTransaction transaction = mManager.beginTransaction();
        show = 1;
        Intent intent = getIntent();
        show = intent.getExtras().getInt("show");
        if (show == 1) {
            transaction.add(R.id.frame, mHomeFragment, "home");
            transaction.commit();
        } else if (show == 2) {
            transaction.add(R.id.frame, mMessageFragment, "message");
            transaction.commit();
        } else if (show == 3) {
            transaction.add(R.id.frame, mCommunityFragment, "community");
            transaction.commit();
        }

        mInformation.setTextColor(getResources().getColorStateList(R.color.color_Text));
        UserDao userDao = DaoMaster.newDevSession(this, UserDao.TABLENAME).getUserDao();
        List<User> users = userDao.loadAll();
        if (users.size() == 0) {
            noUserLogin();
        } else {
            haveUserLogin();
        }
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
                        transaction1.replace(R.id.frame, mMessageFragment, "message");
                        transaction1.commit();
                        break;
                    case R.id.Information:
                        mMessage.setTextColor(getResources().getColorStateList(R.color.colorText));
                        mCommunity.setTextColor(getResources().getColorStateList(R.color.colorText));
                        mInformation.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        FragmentTransaction transaction2 = mManager.beginTransaction();
                        transaction2.replace(R.id.frame, mHomeFragment, "home");
                        transaction2.commit();
                        break;
                    case R.id.community:
                        mMessage.setTextColor(getResources().getColorStateList(R.color.colorText));
                        mCommunity.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        mInformation.setTextColor(getResources().getColorStateList(R.color.colorText));
                        FragmentTransaction transaction3 = mManager.beginTransaction();
                        transaction3.replace(R.id.frame, mCommunityFragment, "community");
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

                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mRight.layout(mCLayout.getRight(), 0, mCLayout.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mRight.layout(mCLayout.getRight(), 0, mCLayout.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        userDao = DaoMaster.newDevSession(this, UserDao.TABLENAME).getUserDao();
        if (userDao.loadAll().size() == 0) {
            noUserLogin();
        } else {
            haveUserLogin();
        }


    }

    private int mFlag = 0;
    private long mTime1, mTime2;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回按键监听
        if (keyCode == KeyEvent.KEYCODE_BACK && mFlag == 0) {
            mFlag = 1;
            //获取当前系统时间
            mTime1 = System.currentTimeMillis();

            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
        } else if (keyCode == KeyEvent.KEYCODE_BACK && mFlag == 1) {
            mTime2 = System.currentTimeMillis();
            if (mTime2 - mTime1 < 2500) {
                finish();
            } else {
            }
            mFlag = 0;
            mTime1 = 0;
            mTime2 = 0;

        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDao=null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mDraw.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mRight.layout(mCLayout.getRight(), 0, mCLayout.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mRight.layout(mCLayout.getRight(), 0, mCLayout.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void haveUserLogin() {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(R.id.c_frame, mHaveUserFragment, "haveuser");
        fragmentTransaction.commit();
    }

    private void noUserLogin() {
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(R.id.c_frame, mNoUserFragment, "nouser");
        fragmentTransaction.commit();
    }


}

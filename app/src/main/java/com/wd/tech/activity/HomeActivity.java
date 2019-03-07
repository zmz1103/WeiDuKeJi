package com.wd.tech.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.H;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.myactivity.MyAttentionActivity;
import com.wd.tech.activity.myactivity.MyCardActivity;
import com.wd.tech.activity.myactivity.MyCollectActivity;
import com.wd.tech.activity.myactivity.MyInteGralActivity;
import com.wd.tech.activity.myactivity.MyNoticeActivity;
import com.wd.tech.activity.myactivity.MySettingActivity;
import com.wd.tech.activity.myactivity.MyTaskActivity;
import com.wd.tech.activity.myactivity.MyUpdateUserMessageActivity;
import com.wd.tech.activity.myactivity.PublishSingActivity;
import com.wd.tech.activity.myactivity.SignActivity;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.GetUserBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.fragment.CommunityFragment;
import com.wd.tech.fragment.HaveUserFragment;
import com.wd.tech.fragment.HomeFragment;
import com.wd.tech.fragment.MessageFragment;
import com.wd.tech.fragment.NoUserFragment;
import com.wd.tech.presenter.GetUserBeanPresenter;
import com.wd.tech.util.NetWorkUtils;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * date:2019/2/19 10:52
 * author:赵明珠(啊哈)
 * function:
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

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
    LinearLayout mCLayout;

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
    private RelativeLayout mHave;
    private RelativeLayout mNo;
    private int mClickFragmentId;
    private int mCurrentFragmentId;
    private LinearLayout mGoLoginActivity;


    //头像 昵称 签到图标 签名
    @BindView(R.id.my_icon)
    SimpleDraweeView mMyIcon;
    @BindView(R.id.my_name)
    TextView mMyName;
    @BindView(R.id.my_image_sign)
    ImageView mMyImageSign;
    @BindView(R.id.my_signature)
    TextView mMySignAture;
    @BindView(R.id.my_image_vip)
    ImageView mImageVip;
    private GetUserBeanPresenter mGetUserBeanPresenter;
    private List<User> users;
    private boolean isDrawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.RECORD_AUDIO,android.Manifest.permission.CAMERA},1);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE
                }, 100);
            }
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
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
        mImageVip.setVisibility(View.GONE);

        mGetUserBeanPresenter = new GetUserBeanPresenter(new getUserById());

        mHomeFragment = new HomeFragment();
        mMessageFragment = new MessageFragment();
        mCommunityFragment = new CommunityFragment();

        mHaveUserFragment = new HaveUserFragment();
        mNoUserFragment = new NoUserFragment();
        if (mManager == null) {
            mManager = getSupportFragmentManager();
        }
        FragmentTransaction transaction = mManager.beginTransaction();
        mHave = findViewById(R.id.have_user);
        mNo = findViewById(R.id.no_user);

        findViewById(R.id.goLoginBtn).setOnClickListener(this);

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
        users = WDApplication.getAppContext().getUserDao().loadAll();
        if (users.size() == 0) {
            noUserLogin();
        } else {
            haveUserLogin();
        }



//        mDraw.setScrimColor(Color.TRANSPARENT);//去除阴影
        mCLayout.measure(0, 0);
        //获取布局宽度，并获得左移大小
        final float width = mCLayout.getMeasuredWidth() * 0.2f;
        //底布局左移
        mCLayout.setTranslationX(-width);
        // 侧拉出的页面
        mDraw.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer = true;

                mCLayout.setTranslationX(-width + width * slideOffset);
                //底布局跟着移动
                mRight.setTranslationX(drawerView.getMeasuredWidth() * slideOffset);
                //主界面布局移动，移动长度等于抽屉的移动长度

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;
                mCLayout.setTranslationX(0F);
                //底布局跟着移动
                mRight.setTranslationX(0F);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private class getUserById implements DataCall<Result<GetUserBean>> {
        @Override
        public void success(Result<GetUserBean> result) {
            if (result.getStatus().equals("0000")) {
                if (result.getResult().getWhetherVip() == 1) {
                    mImageVip.setVisibility(View.VISIBLE);
                } else {
                    mImageVip.setVisibility(View.GONE);
                }
                mMyIcon.setImageURI(result.getResult().getHeadPic());
                mMyName.setText(result.getResult().getNickName());
                if (result.getResult().getSignature().equals("")) {
                    mMySignAture.setText("发表个心情吧！");
                } else {
                    mMySignAture.setText(result.getResult().getSignature());
                }

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (WDApplication.getAppContext().getUserDao().loadAll().size() == 0) {
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
        mGetUserBeanPresenter.unBind();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        final float width = mCLayout.getMeasuredWidth() * 0.2f;//获取布局宽度，并获得左移大小
        mDraw.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
//                //获取屏幕的宽高
                isDrawer = true;

                mCLayout.setTranslationX(-width + width * slideOffset);
                //底布局跟着移动
                mRight.setTranslationX(drawerView.getMeasuredWidth() * slideOffset);
                //主界面布局移动，移动长度等于抽屉的移动长度
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer = false;
                mCLayout.setTranslationX(0F);
                //底布局跟着移动
                mRight.setTranslationX(0F);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void haveUserLogin() {

        List<User> users1 = DaoMaster.newDevSession(HomeActivity.this, UserDao.TABLENAME).getUserDao().loadAll();
        mGetUserBeanPresenter.reqeust(users1.get(0).getUserId(), users1.get(0).getSessionId());
        mHave.setVisibility(View.VISIBLE);
        mNo.setVisibility(View.GONE);
        if (mClickFragmentId == R.id.Message) {
            mRadioGroup.check(mClickFragmentId);
            mCurrentFragmentId = R.id.Message;
            mMessage.setTextColor(getResources().getColorStateList(R.color.color_Text));
            mCommunity.setTextColor(getResources().getColorStateList(R.color.colorText));
            mInformation.setTextColor(getResources().getColorStateList(R.color.colorText));
            FragmentTransaction transaction1 = mManager.beginTransaction();
            transaction1.replace(R.id.frame, mMessageFragment, "message");
            transaction1.commit();
            mClickFragmentId = 0;
        }
    }

    private void noUserLogin() {
        if (mClickFragmentId == R.id.Message) {
            if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
                mRadioGroup.check(mCurrentFragmentId);
                mCurrentFragmentId = R.id.Message;
                mMessage.setTextColor(getResources().getColorStateList(R.color.color_Text));
                mCommunity.setTextColor(getResources().getColorStateList(R.color.colorText));
                mInformation.setTextColor(getResources().getColorStateList(R.color.colorText));
                FragmentTransaction transaction1 = mManager.beginTransaction();
                transaction1.replace(R.id.frame, mMessageFragment, "message");
                transaction1.commit();
            } else {
                mClickFragmentId = R.id.Information;
                mRadioGroup.check(mClickFragmentId);
                mCurrentFragmentId = R.id.Information;
                mMessage.setTextColor(getResources().getColorStateList(R.color.colorText));
                mCommunity.setTextColor(getResources().getColorStateList(R.color.colorText));
                mInformation.setTextColor(getResources().getColorStateList(R.color.color_Text));
                FragmentTransaction transaction2 = mManager.beginTransaction();
                transaction2.replace(R.id.frame, mHomeFragment, "home");
                transaction2.commit();
            }
        }
        mClickFragmentId = 0;
        mHave.setVisibility(View.GONE);
        mNo.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.linear_my_attention,
            R.id.Message, R.id.Information, R.id.community,
            R.id.linear_my_card, R.id.linear_my_collect,
            R.id.linear_my_integral, R.id.linear_my_notice,
            R.id.linear_my_setting, R.id.linear_my_task,
            R.id.my_image_sign, R.id.qdtext, R.id.my_icon, R.id.my_signature})
    void dian(View view) {
        // 判断是否有网
        if (NetWorkUtils.isNetworkAvailable(WDApplication.getAppContext())) {
            switch (view.getId()) {
                case R.id.my_icon:
                    startActivity(new Intent(HomeActivity.this, MyUpdateUserMessageActivity.class));
                    break;
                case R.id.my_image_sign:
                    // 签到
                    startActivity(new Intent(HomeActivity.this, SignActivity.class));
                    break;
                case R.id.linear_my_attention:
                    // 关注
                    startActivity(new Intent(HomeActivity.this, MyAttentionActivity.class));
                    break;
                case R.id.linear_my_collect:
                    // 收藏
                    startActivity(new Intent(HomeActivity.this, MyCollectActivity.class));
                    break;
                case R.id.linear_my_card:
                    // 我的帖子
                    startActivity(new Intent(HomeActivity.this, MyCardActivity.class));
                    break;
                case R.id.linear_my_notice:
                    // 通知
                    startActivity(new Intent(HomeActivity.this, MyNoticeActivity.class));
                    break;
                case R.id.linear_my_integral:
                    // 我的积分
                    startActivity(new Intent(HomeActivity.this, MyInteGralActivity.class));
                    break;
                case R.id.linear_my_task:
                    // 任务
                    startActivity(new Intent(HomeActivity.this, MyTaskActivity.class));
                    break;
                case R.id.linear_my_setting:
                    // 设置
                    startActivity(new Intent(HomeActivity.this, MySettingActivity.class));
                    break;
                case R.id.my_signature:
                    // 更换签名
                    startActivity(new Intent(HomeActivity.this, PublishSingActivity.class));
                    break;
                default:
                    break;

                case R.id.Message:
                    if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
                        mCurrentFragmentId = R.id.Message;
                        mMessage.setTextColor(getResources().getColorStateList(R.color.color_Text));
                        mCommunity.setTextColor(getResources().getColorStateList(R.color.colorText));
                        mInformation.setTextColor(getResources().getColorStateList(R.color.colorText));
                        FragmentTransaction transaction1 = mManager.beginTransaction();
                        transaction1.replace(R.id.frame, mMessageFragment, "message");
                        transaction1.commit();
                    } else {
                        mClickFragmentId = R.id.Message;
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    break;
                case R.id.Information:
                    mCurrentFragmentId = R.id.Information;
                    mMessage.setTextColor(getResources().getColorStateList(R.color.colorText));
                    mCommunity.setTextColor(getResources().getColorStateList(R.color.colorText));
                    mInformation.setTextColor(getResources().getColorStateList(R.color.color_Text));
                    FragmentTransaction transaction2 = mManager.beginTransaction();
                    transaction2.replace(R.id.frame, mHomeFragment, "home");
                    transaction2.commit();
                    break;
                case R.id.community:
                    mCurrentFragmentId = R.id.community;
                    mMessage.setTextColor(getResources().getColorStateList(R.color.colorText));
                    mCommunity.setTextColor(getResources().getColorStateList(R.color.color_Text));
                    mInformation.setTextColor(getResources().getColorStateList(R.color.colorText));
                    FragmentTransaction transaction3 = mManager.beginTransaction();
                    transaction3.replace(R.id.frame, mCommunityFragment, "community");
                    transaction3.commit();
                    break;
            }
        } else {
            Toast.makeText(WDApplication.getAppContext(), "ffff请检查网络", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WDApplication.getAppContext(), NoNetWorkActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.goLoginBtn:
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                break;


            default:
                break;
        }

    }
}

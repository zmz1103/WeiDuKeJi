package com.wd.tech.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.LoginPresenter;
import com.wd.tech.util.RegUtils;
import com.wd.tech.util.RsaCoder;
import com.wd.tech.util.WeiXinUtil;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;




public class MainActivity extends WDActivity implements CustomAdapt {

    private LoginPresenter mLoginPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (userDao.loadAll().size()>0) {
            finish();
            return ;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userDao.loadAll().size()>0) {
            finish();
            return ;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userDao.loadAll().size()>0) {
            finish();
            return ;
        }
    }

    @Override
    protected int getLayoutId() {
        if (userDao.loadAll().size()>0) {
            finish();
            return R.layout.activity_main;
        }
        return R.layout.activity_main;
    }

    @BindView(R.id.et_pwd_number)
    EditText mEpwd;
    @BindView(R.id.et_tel_number)
    EditText mEtel;
    boolean isHide;

    @Override
    protected void initView() {
        // 设置密码不可见
        mEpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isHide = true;
        mLoginPresenter = new LoginPresenter(new loginCall());
    }

    @OnClick({R.id.eye, R.id.toReagist, R.id.weixinLogin, R.id.faseIdLogin, R.id.login_btn})
    void clivk(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                // 登录
                String s = mEtel.getText().toString();
                String s1 = mEpwd.getText().toString();
                boolean mobile = RegUtils.isMobile(s);
                boolean b = RegUtils.rexCheckPassword(s1);
                if (mobile) {

                    if (b && mEpwd.length() >= 8) {
                        try {
                            mLoginPresenter.reqeust(mEtel.getText().toString(), RsaCoder.encryptByPublicKey(mEpwd.getText().toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "密码必须大于8位", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "手机号不正确", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.eye:
                if (isHide) {
                    isHide = false;
                    //设置密码可见
                    mEpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isHide = true;
                    // 不可见
                    mEpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.toReagist:
                // 去注册页面
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.weixinLogin:
                // 微信登录
                if (!WeiXinUtil.success(this)) {
                    Toast.makeText(this, "请先安装应用", Toast.LENGTH_SHORT).show();
                } else {
                    //  验证
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    WeiXinUtil.reg(MainActivity.this).sendReq(req);
                }
                break;
            case R.id.faseIdLogin:
                // 人脸登录
                break;
            default:
                break;
        }
    }

    @Override
    protected void destoryData() {
        mLoginPresenter.unBind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class loginCall implements DataCall<Result<User>> {
        @Override
        public void success(Result<User> result) {
            Toast.makeText(MainActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                UserDao userDao = DaoMaster.newDevSession(MainActivity.this, UserDao.TABLENAME).getUserDao();
                userDao.deleteAll();
                User user = new User();
                user = result.getResult();
                user.setSole(1);
                userDao.insertOrReplace(user);

                finish();
                Log.v("数据库---",""+userDao.loadAll().get(0).toString());

                EMClient.getInstance().login(result.getResult().getUserName(),result.getResult().getPwd(),new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        finish();
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                    }
                });



            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

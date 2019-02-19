package com.wd.tech.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.LoginPresenter;
import com.wd.tech.util.RegUtils;
import com.wd.tech.util.RsaCoder;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends WDActivity {

    private LoginPresenter loginPresenter;

    @Override
    protected int getLayoutId() {
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
        loginPresenter = new LoginPresenter(new loginCall());
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
                if (b && mobile) {
                    try {
                        loginPresenter.reqeust(mEtel.getText().toString(), RsaCoder.encryptByPublicKey(mEpwd.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        loginPresenter.unBind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class loginCall implements DataCall<Result<User>> {
        @Override
        public void success(Result<User> result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(MainActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();

                userDao.deleteAll();
                User user = new User();
                user = result.getResult();
                user.setSole(1);
                userDao.insertOrReplace(user);
                List<User> users = userDao.loadAll();
                Toast.makeText(MainActivity.this, ""+users.size(), Toast.LENGTH_SHORT).show();
                finish();

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

package com.wd.tech.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.RegisterPresenter;
import com.wd.tech.util.RegUtils;
import com.wd.tech.util.RsaCoder;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: Wang on 2019/2/19 15:48
 * 寄语：加油！相信自己可以！！！
 */


public class RegisterActivity extends WDActivity   {

    private RegisterPresenter mRegisterPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @BindView(R.id.r_et_phone_number)
    EditText mEphone;
    @BindView(R.id.r_et_nickname_number)
    EditText mEname;
    @BindView(R.id.r_et_opwd_number)
    EditText mEpwd;

    @Override
    protected void initView() {
        mRegisterPresenter = new RegisterPresenter(new registerCall());
    }

    @OnClick(R.id.register_btn)
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                boolean mMobile = RegUtils.isMobile(mEphone.getText().toString());
                boolean mPwdText = RegUtils.rexCheckPassword(mEpwd.getText().toString());
                if ( mMobile) {
                    if (mPwdText&&mEpwd.length()>=8) {
                        try {
                            if (mEname.getText().toString().length()>=1) {
                                String s = RsaCoder.encryptByPublicKey(mEpwd.getText().toString());

                                mRegisterPresenter.reqeust(mEphone.getText().toString(),mEname.getText().toString(),s );
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(this, "密码必须大于8位", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "手机号不正确", Toast.LENGTH_SHORT).show();
                }

                break;
                default:
                    break;
        }
    }

    @Override
    protected void destoryData() {
        mRegisterPresenter=null;
    }



    private class registerCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(RegisterActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(RegisterActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }
}

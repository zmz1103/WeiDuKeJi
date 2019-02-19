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


public class RegisterActivity extends WDActivity {

    private RegisterPresenter registerPresenter;

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
        registerPresenter = new RegisterPresenter(new registerCall());
    }

    @OnClick(R.id.register_btn)
    void onclick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                boolean mobile = RegUtils.isMobile(mEphone.getText().toString());
                boolean b = RegUtils.rexCheckPassword(mEpwd.getText().toString());
                if (b && mobile && mEname.length() > 0) {
                    try {
                        String s = RsaCoder.encryptByPublicKey(mEpwd.getText().toString());

                        registerPresenter.reqeust(mEphone.getText().toString(), mEname.getText().toString(), s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    protected void destoryData() {
        registerPresenter.unBind();
    }

    private class registerCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(RegisterActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

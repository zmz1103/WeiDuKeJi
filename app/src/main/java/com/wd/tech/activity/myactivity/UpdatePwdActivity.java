package com.wd.tech.activity.myactivity;

import android.view.View;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.ModifyUserPwdPresenter;
import com.wd.tech.view.DataCall;

import butterknife.OnClick;

/**
 * 作者: Wang on 2019/2/28 19:49
 * 寄语：加油！相信自己可以！！！
 */

public class UpdatePwdActivity extends WDActivity {

    private ModifyUserPwdPresenter modifyUserPwdPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pwd;
    }


    @Override
    protected void initView() {
        modifyUserPwdPresenter = new ModifyUserPwdPresenter(new modifyPwd());
    }

    @Override
    protected void destoryData() {
        modifyUserPwdPresenter.unBind();
    }

    @OnClick(R.id.my_back_u_pwd)
    void dianGo(View view) {
        switch (view.getId()) {
            case R.id.my_back_u_pwd:
                finish();
                break;
            default:
                break;
        }
    }

    private class modifyPwd implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(UpdatePwdActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

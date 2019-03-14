package com.wd.tech.activity.myactivity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.HomeActivity;
import com.wd.tech.activity.MainActivity;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.ModifyUserPwdPresenter;
import com.wd.tech.util.RegUtils;
import com.wd.tech.util.RsaCoder;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
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

    @BindView(R.id.te_number)
    EditText mOldPwd;
    @BindView(R.id.pwd_01)
    EditText mPwd1;
    @BindView(R.id.pwd_02)
    EditText mPwd2;

    @Override
    protected void destoryData() {
        modifyUserPwdPresenter=null;
    }

    @OnClick({R.id.my_back_u_pwd, R.id.upw_btn})
    void dianGo(View view) {
        switch (view.getId()) {
            case R.id.my_back_u_pwd:
                finish();
                break;
            case R.id.upw_btn:
                String moldPwd = mOldPwd.getText().toString().trim();
                if (RegUtils.rexCheckPassword(moldPwd)) {
                    String mNewPwd01 = mPwd1.getText().toString().trim();
                    if (RegUtils.rexCheckPassword(mNewPwd01)) {
                        if (mNewPwd01.equals(mPwd2.getText().toString().trim())) {
                            try {
                                String s = RsaCoder.encryptByPublicKey(moldPwd);
                                modifyUserPwdPresenter.reqeust(user.getUserId(), user.getSessionId(), s, RsaCoder.encryptByPublicKey(mNewPwd01));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(this, "新密码不一致 请重新检查", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "原密码不对，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    private class modifyPwd implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(UpdatePwdActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                WDApplication.getAppContext().getUserDao().deleteAll();
                Intent intent = new Intent(UpdatePwdActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("show", 1);
                // 清空当前栈 ，并且创建新的栈
                startActivity(intent);
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

package com.wd.tech.activity.myactivity;

import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.UserInteGralListData;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindContinuosSignDaysPresenter;
import com.wd.tech.presenter.FindUserIntegralPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import me.jessyan.autosize.internal.CustomAdapt;

public class MyInteGralActivity extends WDActivity implements CustomAdapt {


    private FindUserIntegralPresenter findUserIntegralPresenter;
    private FindContinuosSignDaysPresenter findContinuosSignDaysPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_inte_gral;
    }

    @BindView(R.id.jf_user_sign_count)
    TextView mUserSignCount;
    @BindView(R.id.jf_count_textview)
    TextView mUserUseCount;

    @Override
    protected void initView() {
        findUserIntegralPresenter = new FindUserIntegralPresenter(new getIntegralCount());
        findContinuosSignDaysPresenter = new FindContinuosSignDaysPresenter(new getSignDays());
        if (user == null) {

        } else {
            findUserIntegralPresenter.reqeust(user.getUserId(), user.getSessionId());
            findContinuosSignDaysPresenter.reqeust(user.getUserId(),user.getSessionId());
        }
    }

    @Override
    protected void destoryData() {
        findUserIntegralPresenter.unBind();

    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class getIntegralCount implements DataCall<Result<UserInteGralListData>> {
        @Override
        public void success(Result<UserInteGralListData> result) {
            if (result.getStatus().equals("0000")) {
                mUserUseCount.setText(String.valueOf(result.getResult().getAmount()));
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class getSignDays implements DataCall<Result<Integer>> {
        @Override
        public void success(Result<Integer> result) {

            if (result.getStatus().equals("0000")) {
                mUserSignCount.setText("您已连续签到"+result.getResult()+"天");
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

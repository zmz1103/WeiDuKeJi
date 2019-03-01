package com.wd.tech.activity.myactivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.DoTheTastPresenter;
import com.wd.tech.presenter.FindUserSignPresenter;
import com.wd.tech.presenter.FindUserSignRecordingPresenter;
import com.wd.tech.presenter.UserSignPresenter;
import com.wd.tech.view.DataCall;
import com.wd.tech.view.SignCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignActivity extends WDActivity {


    private SignCalendar mCalendar;
    private String mDate;
    private TextView mTvMonth;
    private int mMonth;
    private int mYear;
    private RelativeLayout mRlGetGiftData;
    private TextView mTvGetSunValue;
    private ImageView mIvSun;
    private ImageView mIvSunBg;
    private RelativeLayout mRlQuedingBtn;
    private Button mRlBtnSign;
    private ImageView mSignBack;
    private UserSignPresenter mUserSignPresenter;
    private FindUserSignPresenter mFindUserSignPresenter;
    private FindUserSignRecordingPresenter mFindUserSignRecordingPresenter;
    private ImageView mBack;
    private DoTheTastPresenter doTheTastPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        if (user.getUserId() == 0) {
            return;
        } else {

            mFindUserSignRecordingPresenter = new FindUserSignRecordingPresenter(new FinduserRecordResult());

            // 做任务
            doTheTastPresenter = new DoTheTastPresenter(new doTheTask());
            // 用户签到
            mUserSignPresenter = new UserSignPresenter(new UserSignResult());
            mMonth = Calendar.getInstance().get(Calendar.MONTH);
            mYear = Calendar.getInstance().get(Calendar.YEAR);
            //当天签到状态
            mFindUserSignPresenter = new FindUserSignPresenter(new FindUserResult());
            mFindUserSignPresenter.reqeust(user.getUserId(), user.getSessionId());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            // 获取当前时间
            mDate = formatter.format(curDate);
            mCalendar = (SignCalendar) findViewById(R.id.sc_main);
            mTvMonth = (TextView) findViewById(R.id.tv_sign_year_month);
            mRlGetGiftData = (RelativeLayout) findViewById(R.id.rl_get_gift_view);
            mTvGetSunValue = (TextView) findViewById(R.id.tv_text_one);
            mIvSun = (ImageView) findViewById(R.id.iv_sun);
            mIvSunBg = (ImageView) findViewById(R.id.iv_sun_bg);
            mRlQuedingBtn = (RelativeLayout) findViewById(R.id.rl_queding_btn);
            mBack = (ImageView) findViewById(R.id.bacg_qiandao_im);
            mBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            mRlBtnSign = (Button) findViewById(R.id.rl_btn_sign);
            mCalendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
                @Override
                public void onCalendarDateChanged(int year, int month) {
                    mTvMonth.setText(year + "年" + (month) + "月");//设置日期
                }
            });
            mTvMonth.setText(mYear + "年" + (mMonth + 1) + "月");//设置日期
            Log.i("GT", "month：" + mMonth);


            mRlBtnSign.setBackgroundResource(R.drawable.click_btn_shapert);
            mRlBtnSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUserSignPresenter.reqeust(user.getUserId(), user.getSessionId());
                }
            });

            mRlQuedingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRlGetGiftData.setVisibility(View.GONE);
                    mRlBtnSign.setVisibility(View.VISIBLE);
                }
            });
            mCalendar.setCalendarDayBgColor(mDate, R.drawable.jintian);
            //查询用户当月所有签到的日期
            mFindUserSignRecordingPresenter.reqeust(user.getUserId(), user.getSessionId());
        }
    }

    @Override
    protected void destoryData() {

    }

    /**
     * 签到回调接口
     */
    private class UserSignResult implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                mCalendar.setCalendarDayBgColor(mDate, R.drawable.yiqiandao);
                mCalendar.textColor();
                mRlBtnSign.setText("已签到");
                mRlBtnSign.setClickable(false);
                doTheTastPresenter.reqeust(user.getUserId(),user.getSessionId(),1001);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 查看当天签到状态
     */
    private class FindUserResult implements DataCall<Result<Integer>> {


        @Override
        public void success(Result<Integer> result) {
            ;
            if (result.getStatus().equals("0000")) {
                if (result.getResult() == 1) {
                    mCalendar.setCalendarDayBgColor(mDate, R.drawable.yiqiandao);
                    mCalendar.textColor();
                    mRlBtnSign.setText("已签到");
                    mRlBtnSign.setClickable(false);


                } else {
                    mCalendar.textColor();
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 当月签到日
     */
    private class FinduserRecordResult implements DataCall<Result<List<String>>> {
        @Override
        public void success(Result<List<String>> result) {
            if (result.getStatus().equals("0000")) {
                List<String> SignCalendList =  result.getResult();
                for (int i = 0; i < SignCalendList.size(); i++) {
                    mCalendar.setCalendarDayBgColor(SignCalendList.get(i), R.drawable.yiqiandao);
                }
                mCalendar.textColor();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class doTheTask implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(SignActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

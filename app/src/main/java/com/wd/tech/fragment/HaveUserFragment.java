package com.wd.tech.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.NoNetWorkActivity;
import com.wd.tech.activity.myactivity.MyAttentionActivity;
import com.wd.tech.activity.myactivity.MyCardActivity;
import com.wd.tech.activity.myactivity.MyCollectActivity;
import com.wd.tech.activity.myactivity.MyInteGralActivity;
import com.wd.tech.activity.myactivity.MyNoticeActivity;
import com.wd.tech.activity.myactivity.MySettingActivity;
import com.wd.tech.activity.myactivity.MyTaskActivity;
import com.wd.tech.activity.myactivity.SignActivity;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.GetUserBean;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.GetUserBeanPresenter;
import com.wd.tech.util.NetWorkUtils;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 作者: Wang on 2019/2/20 14:53
 * 寄语：加油！相信自己可以！！！
 */


public class HaveUserFragment extends WDFragment {

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

    @Override
    public int getContent() {
        return R.layout.fragment_have_user;
    }

    @Override
    public void initView(View view) {
        mImageVip.setVisibility(View.GONE);
        mGetUserBeanPresenter = new GetUserBeanPresenter(new getUserById());
        mGetUserBeanPresenter.reqeust(user.getUserId(), user.getSessionId());
    }

    @Override
    public void onResume() {
        super.onResume();
        mGetUserBeanPresenter.reqeust(user.getUserId(), user.getSessionId());
    }

    @OnClick({R.id.linear_my_attention, R.id.linear_my_card, R.id.linear_my_collect, R.id.linear_my_integral, R.id.linear_my_notice, R.id.linear_my_setting, R.id.linear_my_task, R.id.my_image_sign, R.id.qdtext})
    void dian(View view) {
        // 判断是否有网
        if (NetWorkUtils.isNetworkAvailable(WDApplication.getAppContext())) {
            switch (view.getId()) {
                case R.id.my_image_sign:
                    // 签到
                    startActivity(new Intent(WDApplication.getAppContext(), SignActivity.class));
                    break;
                case R.id.linear_my_attention:
                    // 关注
                    startActivity(new Intent(WDApplication.getAppContext(), MyAttentionActivity.class));
                    break;
                case R.id.linear_my_collect:
                    // 收藏
                    startActivity(new Intent(WDApplication.getAppContext(), MyCollectActivity.class));
                    break;
                case R.id.linear_my_card:
                    // 我的帖子
                    startActivity(new Intent(WDApplication.getAppContext(), MyCardActivity.class));
                    break;
                case R.id.linear_my_notice:
                    // 通知
                    startActivity(new Intent(WDApplication.getAppContext(), MyNoticeActivity.class));
                    break;
                case R.id.linear_my_integral:
                    // 我的积分
                    startActivity(new Intent(WDApplication.getAppContext(), MyInteGralActivity.class));
                    break;
                case R.id.linear_my_task:
                    // 任务
                    startActivity(new Intent(WDApplication.getAppContext(), MyTaskActivity.class));
                    break;
                case R.id.linear_my_setting:
                    // 设置
                    startActivity(new Intent(WDApplication.getAppContext(), MySettingActivity.class));
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(WDApplication.getAppContext(), "ffff请检查网络", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WDApplication.getAppContext(), NoNetWorkActivity.class));
        }
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
                mMySignAture.setText(result.getResult().getSignature());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

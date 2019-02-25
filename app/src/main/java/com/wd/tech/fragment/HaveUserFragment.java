package com.wd.tech.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.GetUserBeanPresenter;
import com.wd.tech.util.NetWorkUtils;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;


/**
 * 作者: Wang on 2019/2/20 14:53
 * 寄语：加油！相信自己可以！！！
 */


public class HaveUserFragment extends WDFragment {

    //头像 昵称 签到图标 签名
    @BindView(R.id.my_icon)
    SimpleDraweeView my_icon;
    @BindView(R.id.my_name)
    TextView my_name;
    @BindView(R.id.my_image_sign)
    ImageView my_image_sign;
    @BindView(R.id.my_signature)
    TextView my_signature;

    private GetUserBeanPresenter getUserBeanPresenter;
    @Override
    public int getContent() {
        return R.layout.fragment_have_user;
    }

    @Override
    public void initView(View view) {
        getUserBeanPresenter = new GetUserBeanPresenter(new getUserById());
        getUserBeanPresenter.reqeust(user.getUserId(),user.getSessionId());
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
                case R.id.qdtext:
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
                my_icon.setImageURI(user.getHeadPic());
                my_name.setText(result.getResult().getNickName());
                my_signature.setText(result.getResult().getSignature());
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

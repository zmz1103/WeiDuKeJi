package com.wd.tech.fragment;

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
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.UserDao;

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

    @Override
    public int getContent() {
        return R.layout.fragment_have_user;
    }

    @Override
    public void initView(View view) {
        my_icon.setImageURI(user.getHeadPic());
        my_name.setText(user.getNickName());
        my_signature.setText("快来发表新签名吧！");
    }

    @OnClick({R.id.linear_my_attention, R.id.linear_my_card, R.id.linear_my_collect, R.id.linear_my_integral, R.id.linear_my_notice, R.id.linear_my_setting})
    void dian(View view) {
        switch (view.getId()) {
            case R.id.linear_my_attention:
                // 关注
                break;
            case R.id.linear_my_collect:
                // 收藏
                break;
            case R.id.linear_my_card:
                // 我的帖子
                break;
            case R.id.linear_my_notice:
                // 通知
                break;
            case R.id.linear_my_integral:
                // 我的积分
                break;
            case R.id.linear_my_task:
                // 任务
                break;
        }
    }

}

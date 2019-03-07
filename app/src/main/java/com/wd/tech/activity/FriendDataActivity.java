package com.wd.tech.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.wd.tech.R;
import com.wd.tech.activity.huanxin.IMActivity;
import com.wd.tech.bean.QueryFriendList;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.QueryFriendPresenter;
import com.wd.tech.util.DateUtils;
import com.wd.tech.view.DataCall;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/3/3 20:43
 * author:赵明珠(啊哈)
 * function:
 */
public class FriendDataActivity extends WDActivity{


    @BindView(R.id.setting)
    TextView mSetting;

    @BindView(R.id.friend_data_headpic)
    SimpleDraweeView mHeadPic;

    @BindView(R.id.friend_data_nickname)
    TextView mNickName;

    @BindView(R.id.friend_data_phone)
    TextView mPhone;

    @BindView(R.id.frient_data_email)
    TextView mEmail;

    @BindView(R.id.friend_data_whethervip)
    SimpleDraweeView mVip;

    @BindView(R.id.friend_data_signature)
    TextView mSign;

    @BindView(R.id.friend_data_integral)
    TextView mIntegral;

    @BindView(R.id.friend_data_sex)
    TextView mSex;

    @BindView(R.id.friend_data_birthday)
    TextView mBirth;

    private QueryFriendPresenter mQueryFriendPresenter;
    private QueryFriendList mResult;
    private String mUserName;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_data;
    }

    @Override
    protected void initView() {
        mQueryFriendPresenter = new QueryFriendPresenter(new QueryFriend());
        Intent intent = getIntent();
        String mid = intent.getStringExtra("mUserid");
        mUserName = intent.getStringExtra("mUserName");

        int userId = (int) user.getUserId();
        String sessionId = user.getSessionId();

        mQueryFriendPresenter.reqeust(userId,sessionId,Integer.parseInt(mid));
    }


    @OnClick({R.id.setting,R.id.send_message})
    public void onClick(View view){
        if (view.getId() == R.id.send_message){


            Intent intent = new Intent(FriendDataActivity.this, IMActivity.class);
            intent.putExtra(EaseConstant.EXTRA_USER_ID,mUserName);
            intent.putExtra("UserNames",mUserName);
            intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
            startActivity(intent);
        }
        if (view.getId() == R.id.setting){
            Intent intent = new Intent(FriendDataActivity.this, SettingActivity.class);
            intent.putExtra("mid",String.valueOf(mResult.getUserId()));
            intent.putExtra("name",mResult.getNickName());
            intent.putExtra("headpic",mResult.getHeadPic());
            startActivity(intent);
        }
    }

    //查询好友信息
    class QueryFriend implements DataCall<Result<QueryFriendList>> {

        @Override
        public void success(Result<QueryFriendList> result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(FriendDataActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                mResult = result.getResult();

                mHeadPic.setImageURI(Uri.parse(mResult.getHeadPic()));

                mNickName.setText(mResult.getNickName());

                int integral = mResult.getIntegral();
                mIntegral.setText("("+integral+"积分)");

                int whetherVip = mResult.getWhetherVip();

                mSign.setText(mResult.getSignature());

                int sex = mResult.getSex();

                if (whetherVip==1){
                    mVip.setImageURI(R.mipmap.frient_vip+"");
                }
                if (sex ==1){
                    mSex.setText("男");
                }else {
                    mSex.setText("女");
                }

                //转换成日期格式
                SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_PATTERN,Locale.getDefault());

                long birthday = mResult.getBirthday();
                String phone = mResult.getPhone();
                String email = mResult.getEmail();

                mBirth.setText(dateFormat.format(birthday));
                mPhone.setText(phone);
                mEmail.setText(email);
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(FriendDataActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void destoryData() {
        mQueryFriendPresenter.unBind();
    }
}

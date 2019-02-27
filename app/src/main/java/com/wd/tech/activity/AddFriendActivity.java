package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.QueryFriendList;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AddFriendPresenter;
import com.wd.tech.presenter.QueryFriendPresenter;
import com.wd.tech.util.DateUtils;
import com.wd.tech.view.DataCall;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;

public class AddFriendActivity extends WDActivity implements CustomAdapt {
    @BindView(R.id.friend_headpic)
    SimpleDraweeView friendHeadpic;
    @BindView(R.id.friend_nickname)
    TextView friendNickName;
    @BindView(R.id.friend_integral)
    TextView friendIntegral;
    @BindView(R.id.friend_whethervip)
    SimpleDraweeView friendWhetherVip;
    @BindView(R.id.friend_signature)
    TextView friendSignature;
    @BindView(R.id.frient_sex)
    TextView frientSex;
    @BindView(R.id.frient_birthday)
    TextView frientBirthday;
    @BindView(R.id.frient_phone)
    TextView frientPhone;
    @BindView(R.id.frient_email)
    TextView frientEmail;
    @BindView(R.id.frient_addfrient)
    Button frientAddfrient;
    private int mMUserid;
    private QueryFriendPresenter mQueryFriendPresenter;
    private String mNickName;
    private String mSignature;
    private String mHeadPic;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        //用户发布帖子页添加好友传递过来的id
        Intent intent = getIntent();
        mMUserid = intent.getExtras().getInt("mUserid");
        mQueryFriendPresenter = new QueryFriendPresenter(new QueryFriend());
        mQueryFriendPresenter.reqeust((int)user.getUserId(),user.getSessionId(),mMUserid);
        if ("".equals(mMUserid)){
            if (frientAddfrient.getVisibility() == View.VISIBLE){
                frientAddfrient.setVisibility(View.GONE);
            }
        }else {
            if (frientAddfrient.getVisibility() == View.GONE){
                frientAddfrient.setVisibility(View.VISIBLE);
            }
        }

    }

    //查询好友信息
    class QueryFriend implements DataCall<Result<QueryFriendList>> {



        @Override
        public void success(Result<QueryFriendList> result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(AddFriendActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                QueryFriendList friendList = result.getResult();
                mHeadPic = friendList.getHeadPic();
                mNickName = friendList.getNickName();
                int integral = friendList.getIntegral();
                int whetherVip = friendList.getWhetherVip();
                mSignature = friendList.getSignature();
                int sex = friendList.getSex();
                //转换成日期格式
                SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_PATTERN,Locale.getDefault());
                long birthday = friendList.getBirthday();
                String phone = friendList.getPhone();
                String email = friendList.getEmail();

                friendHeadpic.setImageURI(mHeadPic);
                friendNickName.setText(mNickName);
                friendIntegral.setText("("+integral+"积分)");
                if (whetherVip==1){
                    friendWhetherVip.setImageURI(R.mipmap.frient_vip+"");
                }
                friendSignature.setText(mSignature);
                if (sex ==1){
                    frientSex.setText("男");
                }else {
                    frientSex.setText("女");
                }
                frientBirthday.setText(dateFormat.format(birthday));
                frientPhone.setText(phone);
                frientEmail.setText(email);
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(AddFriendActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void destoryData() {

    }

    @OnClick(R.id.frient_addfrient)
    public void onViewClicked() {
        Intent intent = new Intent(AddFriendActivity.this,AddFriendsActivity.class);
        intent.putExtra("mUserid",mMUserid);
        intent.putExtra("mHeadPic",mHeadPic);
        intent.putExtra("mNickName",mNickName);
        intent.putExtra("mSignature",mSignature);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueryFriendPresenter=null;
    }


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

}

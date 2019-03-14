package com.wd.tech.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.AddFriendActivity;
import com.wd.tech.activity.GroupApplyActivity;
import com.wd.tech.bean.Flockformation;
import com.wd.tech.bean.FriendInformation;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindFlockPresenter;
import com.wd.tech.presenter.FindFriendPresenter;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/26 15:06
 * author:赵明珠(啊哈)
 * function:
 */
public class AddFlockFragment extends WDFragment {
    @BindView(R.id.find_search_friend)
    EditText mFindSearch;

    @BindView(R.id.show_friend)
    RelativeLayout mFriend;

    @BindView(R.id.friend_head)
    SimpleDraweeView mHeadPic;

    @BindView(R.id.search_friend_nickname)
    TextView mNickName;

    @BindView(R.id.fruitless)
    TextView mFruitless;

    FindFlockPresenter mFlockPresenter;
    private Flockformation mResult;

    @Override
    public int getContent() {
        return R.layout.activity_find_friend;
    }

    @Override
    public void initView(View view) {
        mFindSearch.setHint("请输入群id");
        mFriend.setVisibility(View.GONE);
        mFruitless.setVisibility(View.GONE);
        mFlockPresenter = new FindFlockPresenter(new FindCall());
        mFindSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = mFindSearch.getText().toString();

                if (!phone.isEmpty()) {
                    int userId = (int) user.getUserId();
                    String sessionId = user.getSessionId();

                    mFlockPresenter.reqeust(userId,sessionId,phone);
                }else {
                    mFriend.setVisibility(View.GONE);
                    mFruitless.setVisibility(View.GONE);
                }
            }
        });

    }

    @OnClick(R.id.show_friend)
    public void onClick() {
        Intent intent = new Intent(getActivity(),GroupApplyActivity.class);

        intent.putExtra("mGroupId",String.valueOf(mResult.getGroupId()));
        intent.putExtra("mGroupImage",mResult.getGroupImage());
        intent.putExtra("mGroupName",mResult.getGroupName());
        intent.putExtra("mCurrentCount",String.valueOf(mResult.getCurrentCount()));
        intent.putExtra("mDescription",mResult.getDescription());
        startActivity(intent);
    }
    class FindCall implements DataCall<Result<Flockformation>> {
        @Override
        public void success(Result<Flockformation> result) {

            if (result.getStatus().equals("0000")){

                mResult = result.getResult();

                mHeadPic.setImageURI(Uri.parse(mResult.getGroupImage()));
                mNickName.setText(mResult.getGroupName());

                mFriend.setVisibility(View.VISIBLE);
                mFruitless.setVisibility(View.GONE);

            }else {
                mFriend.setVisibility(View.GONE);
                mFruitless.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFlockPresenter=null;
    }
}

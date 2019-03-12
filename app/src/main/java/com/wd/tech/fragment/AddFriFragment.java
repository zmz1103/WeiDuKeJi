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
import com.wd.tech.activity.FriendDataActivity;
import com.wd.tech.activity.FriendsPostActivity;
import com.wd.tech.bean.FriendInformation;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindFriendPresenter;
import com.wd.tech.presenter.JudgePresenter;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/26 15:06
 * author:赵明珠(啊哈)
 * function:
 */
public class AddFriFragment extends WDFragment {

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

    FindFriendPresenter mFriendPresenter;
    private FriendInformation mResult;
    JudgePresenter mJudgePresenter;

    @Override
    public int getContent() {
        return R.layout.activity_find_friend;
    }

    @Override
    public void initView(View view) {
        mFriend.setVisibility(View.GONE);
        mFruitless.setVisibility(View.GONE);
        mFriendPresenter = new FindFriendPresenter(new FindCall());
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

                    mFriendPresenter.reqeust(userId, sessionId, phone);
                } else {
                    mFriend.setVisibility(View.GONE);
                    mFruitless.setVisibility(View.GONE);
                }
            }
        });

    }

    @OnClick(R.id.show_friend)
    public void onClick() {

        mJudgePresenter = new JudgePresenter(new JudgeCall());

        mJudgePresenter.reqeust((int)user.getUserId(),user.getSessionId(),mResult.getUserId());

    }

    class FindCall implements DataCall<Result<FriendInformation>> {
        @Override
        public void success(Result<FriendInformation> result) {

            if (result.getStatus().equals("0000")) {

                mResult = result.getResult();

                mHeadPic.setImageURI(Uri.parse(mResult.getHeadPic()));
                mNickName.setText(mResult.getNickName());
                mFriend.setVisibility(View.VISIBLE);
                mFruitless.setVisibility(View.GONE);
            } else {
                mFriend.setVisibility(View.GONE);
                mFruitless.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class JudgeCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                if (result.getFlag() == 2) {
                    Intent intent1 = new Intent(getContext(), AddFriendActivity.class);
                    intent1.putExtra("mUserid", mResult.getUserId());
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(getContext(), FriendDataActivity.class);
                    intent1.putExtra("mUserid", String.valueOf(mResult.getUserId()));
                    intent1.putExtra("mUserName", String.valueOf(mResult.getNickName()));
                    startActivity(intent1);
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFriendPresenter=null;
    }
}

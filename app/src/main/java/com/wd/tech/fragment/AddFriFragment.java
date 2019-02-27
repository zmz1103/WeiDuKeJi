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
import com.wd.tech.bean.FriendInformation;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindFriendPresenter;
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

                    mFriendPresenter.reqeust(userId,sessionId,phone);
                }else {
                    mFriend.setVisibility(View.GONE);
                    mFruitless.setVisibility(View.GONE);
                }
            }
        });

    }

    @OnClick(R.id.datum)
    public void onClick() {
        Intent intent = new Intent(getActivity(),AddFriendActivity.class);
        intent.putExtra("mUserid",mResult.getUserId());
        startActivity(intent);
    }

    class FindCall implements DataCall<Result<FriendInformation>> {
        @Override
        public void success(Result<FriendInformation> result) {

            if (result.getStatus().equals("0000")){

                mResult = result.getResult();

                mHeadPic.setImageURI(Uri.parse(mResult.getHeadPic()));
                mNickName.setText(mResult.getNickName());
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
}

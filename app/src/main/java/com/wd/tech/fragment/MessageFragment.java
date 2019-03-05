package com.wd.tech.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.wd.tech.R;
import com.wd.tech.activity.CreateGroupActivity;
import com.wd.tech.activity.FridendAddActivity;
import com.wd.tech.activity.HomeActivity;
import com.wd.tech.activity.MainActivity;
import com.wd.tech.activity.huanxin.IMActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/19 13:51
 * author:赵明珠(啊哈)
 * function:
 */
public class MessageFragment extends WDFragment {

    @BindView(R.id.message_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.message_radio)
    RadioGroup mRadioGroup;
    @BindView(R.id.my_message)
    RadioButton myMessage;
    @BindView(R.id.contacts)
    RadioButton mContacts;
    ContactsFragment contactsFragment;
    MyMessageFrament myMessageFrament;
    List<Fragment> mList;
    @BindView(R.id.add)
    ImageView mAdd;

    @Override
    public int getContent() {
        return R.layout.activity_message_fragment;
    }

    @Override
    public void initView(View view) {
        mList = new ArrayList<Fragment>();
        myMessageFrament = new MyMessageFrament();
        EaseConversationListFragment conversationListFragment = new ContactsFragment();
        mList.add(conversationListFragment);
        mList.add(myMessageFrament);



        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(getContext(), IMActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mList.get(i);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        mRadioGroup.check(R.id.my_message);
                        myMessage.setTextColor(getResources().getColorStateList(R.color.white));
                        mContacts.setTextColor(getResources().getColorStateList(R.color.blue_btn));
                        break;
                    case 1:
                        mRadioGroup.check(R.id.contacts);
                        myMessage.setTextColor(getResources().getColorStateList(R.color.blue_btn));
                        mContacts.setTextColor(getResources().getColorStateList(R.color.white));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.my_message:
                        mViewPager.setCurrentItem(0);
                        myMessage.setTextColor(getResources().getColorStateList(R.color.white));
                        mContacts.setTextColor(getResources().getColorStateList(R.color.blue_btn));
                        break;
                    case R.id.contacts:
                        mViewPager.setCurrentItem(1);
                        myMessage.setTextColor(getResources().getColorStateList(R.color.blue_btn));
                        mContacts.setTextColor(getResources().getColorStateList(R.color.white));
                        break;
                }
            }
        });
    }

    @OnClick(R.id.add)
    public void OnClick() {
        if (user != null){
            View inflate = View.inflate(getActivity(), R.layout.activity_pop_window, null);

            PopupWindow popWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true);

            popWindow.setTouchable(true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());

            popWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popWindow.showAsDropDown(mAdd);

            initPopClick(inflate);
        }else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPopClick(View view) {
        RelativeLayout mFriend = view.findViewById(R.id.add_friend);
        RelativeLayout mGroup= view.findViewById(R.id.add_group);
        mFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FridendAddActivity.class);
                startActivity(intent);
            }
        });
        mGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
                startActivity(intent);
            }
        });
    }


}

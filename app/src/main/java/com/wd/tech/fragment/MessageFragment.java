package com.wd.tech.fragment;

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

import com.wd.tech.R;

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

    @BindView(R.id.messageViewPager)
    ViewPager viewPager;
    @BindView(R.id.message_radio)
    RadioGroup radioGroup;
    @BindView(R.id.my_message)
    RadioButton message;
    @BindView(R.id.contacts)
    RadioButton contacts;
    ContactsFragment contactsFragment;
    MyMessageFrament myMessageFrament;
    List<Fragment> list;
    @BindView(R.id.add)
    ImageView add;

    @Override
    public int getContent() {
        return R.layout.activity_message_fragment;
    }

    @Override
    public void initView(View view) {
        list = new ArrayList<Fragment>();
        contactsFragment = new ContactsFragment();
        myMessageFrament = new MyMessageFrament();

        list.add(contactsFragment);
        list.add(myMessageFrament);


        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

        HashSet<Object> objects = new HashSet<>();

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        radioGroup.check(R.id.my_message);
                        message.setTextColor(getResources().getColorStateList(R.color.white));
                        contacts.setTextColor(getResources().getColorStateList(R.color.blue_btn));
                        break;
                    case 1:
                        radioGroup.check(R.id.contacts);
                        message.setTextColor(getResources().getColorStateList(R.color.blue_btn));
                        contacts.setTextColor(getResources().getColorStateList(R.color.white));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.my_message:
                        viewPager.setCurrentItem(0);
                        message.setTextColor(getResources().getColorStateList(R.color.white));
                        contacts.setTextColor(getResources().getColorStateList(R.color.blue_btn));
                        break;
                    case R.id.contacts:
                        viewPager.setCurrentItem(1);
                        message.setTextColor(getResources().getColorStateList(R.color.blue_btn));
                        contacts.setTextColor(getResources().getColorStateList(R.color.white));
                        break;
                }
            }
        });
    }

    @OnClick(R.id.add)
    public void OnClick() {
        View inflate = View.inflate(getActivity(), R.layout.activity_pop_window, null);

        PopupWindow popWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popWindow.setTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());

        popWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.showAsDropDown(add);

        initPopClick(inflate);
    }

    private void initPopClick(View view) {
        RelativeLayout add_friend = view.findViewById(R.id.add_friend);
        RelativeLayout add_group = view.findViewById(R.id.add_group);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

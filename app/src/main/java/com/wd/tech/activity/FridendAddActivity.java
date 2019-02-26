package com.wd.tech.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.fragment.AddFlockFragment;
import com.wd.tech.fragment.AddFriFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * date:2019/2/26 14:35
 * author:赵明珠(啊哈)
 * function:
 */
public class FridendAddActivity extends WDActivity {


    @BindView(R.id.find_friend)
    RelativeLayout mFindPeople;
    @BindView(R.id.add_flock)
    RelativeLayout mFindFlock;

    @BindView(R.id.add_flock_text)
    TextView mFlockText;
    @BindView(R.id.add_friend_text)
    TextView mFriendText;

    @BindView(R.id.add_view_pager)
    ViewPager mViewPager;

    List<Fragment> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_friend_flock;
    }

    @Override
    protected void initView() {
        mList.add(new AddFriFragment());
        mList.add(new AddFlockFragment());


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mList.get(i);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        mFindPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        mFindFlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
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
                        mFriendText.setBackground(getResources().getDrawable(R.color.TextTure));
                        mFlockText.setBackground(getResources().getDrawable(R.color.TextFalse));

                        break;
                    case 1:
                        mFriendText.setBackground(getResources().getDrawable(R.color.TextFalse));
                        mFlockText.setBackground(getResources().getDrawable(R.color.TextTure));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @OnClick(R.id.add_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void destoryData() {

    }
}

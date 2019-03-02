package com.wd.tech.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.adapter.MyImageAdapter;
import com.wd.tech.view.PhotoViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LookImageActivity extends WDActivity {

    @BindView(R.id.tv_image_count)
    TextView tvImageCount;
    @BindView(R.id.view_pager_photo)
    PhotoViewPager viewPagerPhoto;
    public static final String TAG = LookImageActivity.class.getSimpleName();
    private List<String> Urls;
    private int currentPosition;
    private MyImageAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_look_image;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();
    }
    private void initData() {

        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        Urls=intent.getStringArrayListExtra("image");
        adapter = new MyImageAdapter(Urls, this);
        viewPagerPhoto.setAdapter(adapter);
        viewPagerPhoto.setCurrentItem(currentPosition, false);
        tvImageCount.setText(currentPosition+1 + "/" + Urls.size());
        viewPagerPhoto.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                tvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
            }
        });
    }

    @Override
    protected void destoryData() {

    }
}

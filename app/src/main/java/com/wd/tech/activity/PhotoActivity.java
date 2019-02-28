package com.wd.tech.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.wd.tech.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {
    public List<Bitmap> bitmap = new ArrayList<Bitmap>();
    private ArrayList<View> listViews;
    private ViewPager pager;
    private MyPageAdapter adapter;
    private Button fanhui;
    private int index = 0;
    private PublishActivity activity_publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        fanhui = (Button) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setOnPageChangeListener(pageChangeListener);

        for (int i = 0; i < activity_publish.bmp.size(); i++) {
            initListViews(activity_publish.bmp.get(i));
        }
        adapter = new MyPageAdapter(listViews);// 构造adapter
        pager.setAdapter(adapter); //设置适配器
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(id);
    }

    private void initListViews(Bitmap bm) {

        if (listViews == null)
            listViews = new ArrayList<View>();
        final ImageView img = new ImageView(this);// 构造textView对象

        img.setImageBitmap(bm);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        // 添加view
        listViews.add(img);

    }
    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            // 页面选择响应函数
            index = i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;// content

        private int size;// 页数

        public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
            // 初始化viewpager的时候给的一个页面
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        @Override
        public int getCount() {
            if (listViews != null && listViews.size() > 0) {
                return listViews.size();
            } else {
                return 0;
            }
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            if (listViews.size() == 0) {

                finish();
            }
        }

        @Override
        public void finishUpdate(@NonNull ViewGroup container) {
            super.finishUpdate(container);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(listViews.get(position));
            return listViews.get(position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;

        }
    }
}

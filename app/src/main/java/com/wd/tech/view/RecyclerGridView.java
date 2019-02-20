package com.wd.tech.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * date: 2019/2/19.
 * Created 王思敏
 * function:自定义发布帖子的图片布局
 */
public class RecyclerGridView extends GridView {

    public RecyclerGridView(Context context) {
        super(context);
    }

    public RecyclerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}

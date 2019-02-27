package com.wd.tech.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.GridView;

public class RecyclerRecView extends RecyclerView {

    public RecyclerRecView(Context context) {
        super(context);
    }

    public RecyclerRecView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerRecView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}


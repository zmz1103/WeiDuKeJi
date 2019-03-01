package com.wd.tech.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * date:2019/3/1 10:01
 * author:赵明珠(啊哈)
 * function:
 */
public class CustomExpandableListView extends ExpandableListView {


    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

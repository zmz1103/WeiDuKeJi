package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.NoticeListDAta;
import com.wd.tech.util.DateUtils;
import com.wd.tech.util.ToDate;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Wang on 2019/2/25 19:20
 * 寄语：加油！相信自己可以！！！
 */


public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.Vh> {

    private List<NoticeListDAta> mListDAta;
    private Context mContext;

    public NoticeListAdapter(Context context) {
        this.mListDAta = new ArrayList<>();
        this.mContext = context;
    }

    public void setmListDAta(List<NoticeListDAta> mListDAta) {
        if (mListDAta != null) {
            this.mListDAta.addAll(mListDAta);
        }
    }


    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(View.inflate(mContext, R.layout.adapter_notice_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        vh.mText.setText(mListDAta.get(i).getContent());
        vh.mTiem.setText(DateUtils.stampToDate(mListDAta.get(i).getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return mListDAta.size();
    }

    public void clear() {
        mListDAta.clear();
    }

    public class Vh extends RecyclerView.ViewHolder{

        private final TextView mText, mTiem;

        public Vh(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.notice_item_text);
            mTiem = itemView.findViewById(R.id.notice_item_time);
        }
    }
}

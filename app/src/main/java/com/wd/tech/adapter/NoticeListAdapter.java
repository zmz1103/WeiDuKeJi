package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.NoticeListDAta;
import com.wd.tech.util.ToDate;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Wang on 2019/2/25 19:20
 * 寄语：加油！相信自己可以！！！
 */


public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.Vh> {

    private List<NoticeListDAta> listDAta;
    private Context context;

    public NoticeListAdapter(Context context) {
        this.listDAta = new ArrayList<>();
        this.context = context;
    }

    public void setListDAta(List<NoticeListDAta> listDAta) {
        this.listDAta = listDAta;
    }


    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(View.inflate(context, R.layout.adapter_notice_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        vh.text.setText(listDAta.get(i).getContent());
        vh.text.setText(ToDate.timedate(listDAta.get(i).getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return listDAta.size();
    }

    public void clear() {
        listDAta.clear();
    }

    public class Vh extends RecyclerView.ViewHolder{

        private final TextView text,tiem;

        public Vh(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.notice_item_text);
            tiem = itemView.findViewById(R.id.notice_item_time);
        }
    }
}

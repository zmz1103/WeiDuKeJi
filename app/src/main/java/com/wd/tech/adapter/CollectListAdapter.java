package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.CollectDataList;
import com.wd.tech.util.ToDate;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Wang on 2019/2/25 14:45
 * 寄语：加油！相信自己可以！！！
 */


public class CollectListAdapter extends RecyclerView.Adapter<CollectListAdapter.Vh> {
    private List<CollectDataList> lists;
    private Context context;

    public CollectListAdapter(Context context) {
        this.lists = new ArrayList<>();
        this.context = context;
    }

    public void setLists(List<CollectDataList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.adapter_collect_list, null);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        CollectDataList collectDataList = lists.get(i);
        String thumbnail = collectDataList.getThumbnail();
        String[] split = thumbnail.split("？");
        vh.sim.setImageURI(split[0]);
        
        vh.title.setText(collectDataList.getTitle());
        vh.zuozhe.setText(ToDate.timedate(collectDataList.getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void clear() {
        lists.clear();
    }

    public class Vh extends RecyclerView.ViewHolder {

        private final TextView title, zuozhe;
        private final SimpleDraweeView sim;

        public Vh(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.co_item_title);
            zuozhe = itemView.findViewById(R.id.co_item_zuozhe);
            sim = itemView.findViewById(R.id.collect_ite_simpleview);
        }
    }
}

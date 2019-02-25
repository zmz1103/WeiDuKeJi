package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.AttentionListData;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Wang on 2019/2/25 18:40
 * 寄语：加油！相信自己可以！！！
 */


public class AttentionListAdapter extends RecyclerView.Adapter<AttentionListAdapter.Vh> {

    private List<AttentionListData> listData;
    private Context context;

    public AttentionListAdapter(Context context) {
        this.listData = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(View.inflate(context,R.layout.adapter_atten_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        vh.qm.setText(listData.get(i).getSignature());
        vh.sim.setImageURI(listData.get(i).getHeadPic());
        vh.title.setText(listData.get(i).getNickName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setListData(List<AttentionListData> listData) {
        this.listData = listData;
    }

    public void clear() {
        listData.clear();
    }

    public class Vh extends RecyclerView.ViewHolder{

        private final SimpleDraweeView sim;
        private final TextView title,qm;

        public Vh(@NonNull View itemView) {
            super(itemView);
            sim = itemView.findViewById(R.id.atten_item_sim);
            title = itemView.findViewById(R.id.atten_item_title);
            qm = itemView.findViewById(R.id.atten_item_qm);
        }
    }
}

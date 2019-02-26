package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wd.tech.R;
import com.wd.tech.bean.CardListData;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Wang on 2019/2/25 19:57
 * 寄语：加油！相信自己可以！！！
 */


public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.Vh> {

    private List<CardListData> listData;
    private Context context;

    public CardListAdapter(Context context) {
        this.listData = new ArrayList<>();
        this.context = context;
    }

    public void setListData(List<CardListData> listData) {
        if (listData!= null) {
            this.listData.addAll(listData);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(View.inflate(context, R.layout.adapter_card_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void clear() {
        listData.clear();
    }

    public class Vh extends RecyclerView.ViewHolder{
        public Vh(@NonNull View itemView) {
            super(itemView);
        }
    }
}

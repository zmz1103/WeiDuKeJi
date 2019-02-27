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
    private List<CollectDataList> mLists;
    private Context mContext;

    public CollectListAdapter(Context context) {
        this.mLists = new ArrayList<>();
        this.mContext = context;
    }

    public void setmLists(List<CollectDataList> mLists) {
        if (mLists != null) {
            this.mLists.addAll(mLists);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.adapter_collect_list, null);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        CollectDataList collectDataList = mLists.get(i);
        String thumbnail = collectDataList.getThumbnail();
        String[] split = thumbnail.split("？");
        vh.mIcon.setImageURI(split[0]);

        vh.mTitle.setText(collectDataList.getTitle());
        vh.mAuther.setText(ToDate.timedate(collectDataList.getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public void clear() {
        mLists.clear();
    }

    public class Vh extends RecyclerView.ViewHolder {

        private final TextView mTitle, mAuther;
        private final SimpleDraweeView mIcon;

        public Vh(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.co_item_title);
            mAuther = itemView.findViewById(R.id.co_item_zuozhe);
            mIcon = itemView.findViewById(R.id.collect_ite_simpleview);
        }
    }
}

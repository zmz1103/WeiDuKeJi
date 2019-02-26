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

    private List<AttentionListData> mListData;
    private Context mContext;

    public AttentionListAdapter(Context context) {
        this.mListData = new ArrayList<>();
        this.mContext = context;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(View.inflate(mContext,R.layout.adapter_atten_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        vh.mSignsture.setText(mListData.get(i).getSignature());
        vh.mIcon.setImageURI(mListData.get(i).getHeadPic());
        vh.mTitle.setText(mListData.get(i).getNickName());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void setmListData(List<AttentionListData> mListData) {
        if (mListData != null) {
            this.mListData.addAll(mListData);
        }
    }

    public void clear() {
        mListData.clear();
    }

    public class Vh extends RecyclerView.ViewHolder{

        private final SimpleDraweeView mIcon;
        private final TextView mTitle, mSignsture;

        public Vh(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.atten_item_sim);
            mTitle = itemView.findViewById(R.id.atten_item_title);
            mSignsture = itemView.findViewById(R.id.atten_item_qm);
        }
    }
}

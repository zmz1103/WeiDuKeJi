package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
    public Vh vh;

    public CollectListAdapter(Context context) {
        this.mLists = new ArrayList<>();
        this.mContext = context;
    }

    public void setmLists(List<CollectDataList> mLists) {
        if (mLists != null) {
            this.mLists.addAll(mLists);
        }
    }

    StringBuilder stringBuilder = new StringBuilder();

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.adapter_collect_list, null);
        return new Vh(view);
    }

    String id = "";

    @Override
    public void onBindViewHolder(@NonNull final Vh vh, int i) {
        this.vh = vh;
        CollectDataList collectDataList = mLists.get(i);
        String thumbnail = collectDataList.getThumbnail();
        String[] split = thumbnail.split("？");
        vh.mIcon.setImageURI(split[0]);

        vh.mTitle.setText(collectDataList.getTitle());
        vh.mAuther.setText(ToDate.timedate(collectDataList.getCreateTime()));
        if (collectDataList.isFlag()) {
            vh.rb.setVisibility(View.VISIBLE);
        } else {
            vh.rb.setVisibility(View.GONE);
        }

        vh.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringBuilder.append(mLists.get(vh.getLayoutPosition()).getInfoId() + ",");
            }
        });
    }

    public String getId() {
        if (stringBuilder.toString().length()> 1) {
            StringBuilder st = this.stringBuilder.deleteCharAt(this.stringBuilder.toString().length() - 1);
            return st.toString();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public void clear() {
        mLists.clear();
    }

    public class Vh extends RecyclerView.ViewHolder {

        public final TextView mTitle, mAuther;
        public final SimpleDraweeView mIcon;
        public final CheckBox rb;

        public Vh(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.co_item_title);
            mAuther = itemView.findViewById(R.id.co_item_zuozhe);
            mIcon = itemView.findViewById(R.id.collect_ite_simpleview);
            rb = itemView.findViewById(R.id.rb);
        }
    }

}

package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.InformationDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/26
 * author:李阔(淡意衬优柔)
 * function:
 */
public class IfmDtlPlateAdapter extends RecyclerView.Adapter<IfmDtlPlateAdapter.ViewHolder> {
    Context mContext;
    List<InformationDetailsBean.PlateBean> mPlateBeans;


    public IfmDtlPlateAdapter(Context context) {
        mContext = context;
        mPlateBeans = new ArrayList<>();
    }

    public void reset(List<InformationDetailsBean.PlateBean> plate) {
        mPlateBeans.clear();
        mPlateBeans.addAll(plate);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.informationdetails_platelist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mPlateBeans.get(i).getName());


    }

    @Override
    public int getItemCount() {
        return mPlateBeans.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}

package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.InformationDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/26
 * author:李阔(淡意衬优柔)
 * function:
 */
public class IfmDtlRecommendedAdapter extends RecyclerView.Adapter<IfmDtlRecommendedAdapter.ViewHolder> {
    Context mContext;
    List<InformationDetailsBean.InformationListBean> mInformationListBeans;
    private String mThumbnail;
    private String[] mSplit;


    public IfmDtlRecommendedAdapter(Context context) {
        mContext = context;
        mInformationListBeans = new ArrayList<>();
    }

    public void reset(List<InformationDetailsBean.InformationListBean> informationList) {
        mInformationListBeans.clear();
        mInformationListBeans.addAll(informationList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.informationdetails_recommendedlist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        mThumbnail = mInformationListBeans.get(i).getThumbnail();
        mSplit = mThumbnail.split("\\?");
        viewHolder.simpleimage.setImageURI(mSplit[0]);
        viewHolder.title.setText(mInformationListBeans.get(i).getTitle());
    }

    @Override
    public int getItemCount() {
        return mInformationListBeans.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleimage;
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleimage = itemView.findViewById(R.id.simpleimage);
            title = itemView.findViewById(R.id.title);
        }
    }
}

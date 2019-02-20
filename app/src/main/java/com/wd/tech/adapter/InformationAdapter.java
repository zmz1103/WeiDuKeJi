package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.InformationListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/19
 * author:李阔(淡意衬优柔)
 * function:
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {

    Context context;
    List<InformationListBean> mInformationListBeans;
    private String mThumbnail;


    public InformationAdapter(Context context) {
        this.context = context;
        mInformationListBeans = new ArrayList<>();
    }

    public void reset(List<InformationListBean> result) {
        mInformationListBeans.clear();
        mInformationListBeans.addAll(result);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.informationlist1_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //mThumbnail = mInformationListBeans.get(i).getThumbnail();
        viewHolder.title.setText(mInformationListBeans.get(i).getTitle());
        viewHolder.details.setText(mInformationListBeans.get(i).getSummary());
        //viewHolder.simpleview.setImageURI(mInformationListBeans.get(i).getThumbnail());

    }

    @Override
    public int getItemCount() {
        return mInformationListBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleview;
        private TextView title;
        private TextView details;
        private TextView zuozhe;
        private TextView time;
        private ImageView shoucang;
        private ImageView fenxiang;
        private TextView fenxiangshu;
        private TextView shoucangshu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleview = itemView.findViewById(R.id.simpleview);
            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
            zuozhe = itemView.findViewById(R.id.zuozhe);
            time = itemView.findViewById(R.id.time);
            shoucang = itemView.findViewById(R.id.shoucang);
            fenxiang = itemView.findViewById(R.id.fenxiang);
            fenxiangshu = itemView.findViewById(R.id.fenxiangshu);
            shoucangshu = itemView.findViewById(R.id.shoucangshu);
        }
    }
}

package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.AllInfoCommentListBean;
import com.wd.tech.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/26
 * author:李阔(淡意衬优柔)
 * function:
 */
public class AllInfoCommentListAdapter extends RecyclerView.Adapter<AllInfoCommentListAdapter.ViewHolder> {
    Context mContext;
    List<AllInfoCommentListBean> mListBeans;
    private String mDate;


    public AllInfoCommentListAdapter(Context context) {
        mContext = context;
        mListBeans = new ArrayList<>();
    }

    public void reset(List<AllInfoCommentListBean> result) {

        mListBeans.addAll(result);
        notifyDataSetChanged();
    }

    public void clear() {
        mListBeans.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.allinfocommentlist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.infophoto.setImageURI(mListBeans.get(i).getHeadPic());
        mDate = DateUtils.stampToDate(mListBeans.get(i).getCommentTime());
        viewHolder.time.setText(mDate);
        viewHolder.infoname.setText(mListBeans.get(i).getNickName());
        viewHolder.infomessage.setText(mListBeans.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView infophoto;
        private TextView infoname;
        private TextView time;
        private TextView infomessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            infophoto = itemView.findViewById(R.id.infophoto);
            infoname = itemView.findViewById(R.id.infoname);
            time = itemView.findViewById(R.id.time);
            infomessage = itemView.findViewById(R.id.infomessage);
        }
    }
}

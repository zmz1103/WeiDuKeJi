package com.wd.tech.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.AllComment;
import com.wd.tech.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * date: 2019/3/5.
 * Created 王思敏
 * function:
 */
public class AllCommentAdapter extends RecyclerView.Adapter<AllCommentAdapter.ViewHolder> {
    private Context context;
    private List<AllComment> list;

    public AllCommentAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.allcomment_recy, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AllComment allComment = list.get(i);
        viewHolder.mSdvHead.setImageURI(Uri.parse(allComment.getHeadPic()));
        viewHolder.mTxtName.setText(allComment.getNickName());
        Date date = new Date();
        date.setTime(allComment.getCommentTime());
        viewHolder.mTxtTime.setText(DateUtils.getTimeFormatText(date));
        viewHolder.mTxtContent.setText(allComment.getContent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setAll(List<AllComment> result) {
        if (result !=null){
            list.addAll(result);
        }
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView mSdvHead;
        private final TextView mTxtName;
        private final TextView mTxtTime;
        private final TextView mTxtContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSdvHead = itemView.findViewById(R.id.sdv_head);
            mTxtName = itemView.findViewById(R.id.txt_name);
            mTxtTime = itemView.findViewById(R.id.txt_time);
            mTxtContent = itemView.findViewById(R.id.txt_content);
        }
    }
}

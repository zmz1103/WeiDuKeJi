package com.wd.tech.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.FriendInform;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/28 9:59
 * author:赵明珠(啊哈)
 * function:
 */
class ProcessedAdapter extends RecyclerView.Adapter<ProcessedAdapter.ProcessedHolder> {

    private Context context;
    private List<FriendInform> mList = new ArrayList<>();

    public ProcessedAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<FriendInform> mListProcessed) {
        mList.addAll(mListProcessed);
    }

    @NonNull
    @Override
    public ProcessedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_processed_friend_item, null);

        return new ProcessedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessedHolder holder, int i) {
        FriendInform friendInform = mList.get(i);
        Log.e("zmz","==审核过的=="+mList.size());
        holder.mSimple.setImageURI(Uri.parse(friendInform.getFromHeadPic()));
        holder.mName.setText(friendInform.getFromNickName());

        if (friendInform.getStatus() == 2){
            holder.mCircularize.setText("已同意");
        }
        if (friendInform.getStatus() == 3){
            holder.mCircularize.setText("已拒绝");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    class ProcessedHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView mSimple;
        TextView mName;
        TextView mCircularize;

        public ProcessedHolder(@NonNull View itemView) {
            super(itemView);
            mCircularize = itemView.findViewById(R.id.circularize);
            mName = itemView.findViewById(R.id.name_processed);
            mSimple = itemView.findViewById(R.id.headpic_processed);
        }
    }
}

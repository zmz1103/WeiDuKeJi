package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.SearchMyFriendActivity;
import com.wd.tech.bean.FuzzyQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/3/1 11:48
 * author:赵明珠(啊哈)
 * function:
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private Context context;

    List<FuzzyQuery> mList = new ArrayList<>();

    public FriendAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.activity_friend_child_id_item, null);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        FuzzyQuery fuzzyQuery = mList.get(i);
        Log.e("zmz","模糊查询");
        holder.head.setImageURI(fuzzyQuery.getHeadPic());
        holder.nickName.setText(fuzzyQuery.getNickName());
        holder.signature.setText("来自:"+fuzzyQuery.getBuddySource());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    public void addAll(List<FuzzyQuery> mResult) {
        mList.addAll(mResult);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView head;
        TextView nickName;
        TextView signature;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.friend_head);
            nickName = itemView.findViewById(R.id.friend_nickname);
            signature = itemView.findViewById(R.id.friend_signature);
        }
    }

}

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
import com.wd.tech.activity.MyGroupActivity;
import com.wd.tech.bean.MyGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/3/5 18:59
 * author:赵明珠(啊哈)
 * function:
 */
public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.ViewHolder>{
    private Context context;
    List<MyGroup> mList = new ArrayList<>();


    public MyGroupAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<MyGroup> mResult) {
        mList.addAll(mResult);
    }

    public void clear() {
        mList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_my_group_list_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MyGroup myGroup = mList.get(i);
        Log.e("zmz","我的群组");
        viewHolder.mSimple.setImageURI(Uri.parse(myGroup.getGroupImage()));
        viewHolder.name.setText(myGroup.getGroupName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(myGroup.getHxGroupId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimple;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimple = itemView.findViewById(R.id.my_group_headpic);
            name = itemView.findViewById(R.id.my_group_name);
        }
    }
    public OnClickListener onClickListener;
    public interface OnClickListener{
        void onClick(String name);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

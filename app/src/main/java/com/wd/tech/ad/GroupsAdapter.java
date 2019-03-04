package com.wd.tech.ad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.activity.GroupsActivity;
import com.wd.tech.bean.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/3/4 20:27
 * author:赵明珠(啊哈)
 * function:
 */
public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder>{

    private Context context;
    List<Group> mList = new ArrayList<>();

    public GroupsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_friend_groups_item, null);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Group group = mList.get(i);

        viewHolder.name.setText(group.getGroupName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onClickListener.onClick(group.getGroupId(),group.getGroupName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<Group> mResult) {
        mList.addAll(mResult);
    }

    public void clear() {
        mList.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.groups_name);
        }
    }
    public OnClickListener onClickListener;
    public interface OnClickListener{
        void onClick(int id,String name);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

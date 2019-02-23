package com.wd.tech.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import com.wd.tech.R;
import com.wd.tech.bean.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/21 19:43
 * author:赵明珠(啊哈)
 * function:
 */
public class GroupAdapter extends BaseAdapter {
    private Context context;
    List<Group> mList = new ArrayList<>();
    private ExpandableAdapter expandableAdapter;

    public GroupAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        switch (position) {
            case 0:
                ViewHolder holder;
                Log.e("zmz", "============");
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = View.inflate(context, R.layout.activity_search_item, null);
                    holder.editText = convertView.findViewById(R.id.search_friend);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                break;
            case 1:
                ClusterHolder clusterHolder;
                if (convertView == null) {
                    clusterHolder = new ClusterHolder();
                    convertView = View.inflate(context, R.layout.activity_new_friend_item, null);
                    clusterHolder.cluster = convertView.findViewById(R.id.cluster);
                    convertView.setTag(clusterHolder);
                } else {
                    clusterHolder = (ClusterHolder) convertView.getTag();
                }

                break;
            default:
                FriendHolder friendHolder;
                if (convertView == null) {
                    friendHolder = new FriendHolder();
                    convertView = View.inflate(context, R.layout.activity_friend_item, null);
                    friendHolder.expandableListView = convertView.findViewById(R.id.friend);
                    convertView.setTag(friendHolder);
                } else {
                    friendHolder = (FriendHolder) convertView.getTag();
                }
                expandableAdapter = new ExpandableAdapter(context);
                Group group = mList.get(position);
                expandableAdapter.addAll(group);
                friendHolder.expandableListView.setAdapter(expandableAdapter);
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getViewTypeCount() {
        return 3;
    }

    public void addAll(List<Group> groupList) {

        mList.addAll(groupList);
    }

    class ViewHolder {
        EditText editText;
    }

    class ClusterHolder {
        RelativeLayout cluster;
    }

    class FriendHolder {
        ExpandableListView expandableListView;
    }
}

package com.wd.tech.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.AuditFriendActivity;
import com.wd.tech.activity.SearchMyFriendActivity;
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
    List<Group> mGroupList = new ArrayList<>();
    private ExpandableAdapter expandableAdapter = null;

    public GroupAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        int size = mList.size();
        int num = size - 1;

        return num;
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

        int itemViewType = getItemViewType(position);

        switch (itemViewType) {
            case 0:
                ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = View.inflate(context, R.layout.activity_search_item, null);
                    holder.editText = convertView.findViewById(R.id.search_friend);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.editText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SearchMyFriendActivity.class);
                        context.startActivity(intent);
                    }
                });

                break;
            case 1:
                AddFriendHolder addFriendHolder;
                if (convertView == null) {
                    addFriendHolder = new AddFriendHolder();
                    convertView = View.inflate(context, R.layout.activity_add_friend_item, null);
                    addFriendHolder.addFriend = convertView.findViewById(R.id.add_fri);
                    convertView.setTag(addFriendHolder);
                } else {
                    addFriendHolder = (AddFriendHolder) convertView.getTag();
                }

                addFriendHolder.addFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AuditFriendActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case 2:
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

                if (expandableAdapter != null) {


                    friendHolder.expandableListView.setAdapter(expandableAdapter);
                } else {
                    expandableAdapter = new ExpandableAdapter(context);
                    friendHolder.expandableListView.setAdapter(expandableAdapter);
                }

                friendHolder.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                        int mid = mGroupList.get(groupPosition).getFriendInfoList().get(childPosition).getFriendUid();
                        onClickChildListenter.onClick(mid);

                        return true;
                    }
                });

                expandableAdapter.addAll(mGroupList);
                expandableAdapter.notifyDataSetChanged();
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {

        if (mList.get(position).getGroupName() != null) {
            return 3;
        }
        if (position == 1) {
            return 1;
        }
        if (position == 2) {
            return 2;
        }
        return 0;

    }


    @Override
    public int getViewTypeCount() {
        return 4;
    }

    public void addAll(List<Group> groupList, List<Group> mGroup) {


        mGroupList.addAll(mGroup);

        Group group = new Group();

        groupList.add(0, group);

        groupList.add(1, group);

        groupList.add(2, group);

        mList.addAll(groupList);
    }

    public void clear() {
        mList.clear();
        mGroupList.clear();
    }

    class ViewHolder {
        EditText editText;
    }

    class ClusterHolder {
        RelativeLayout cluster;
    }

    class AddFriendHolder {
        RelativeLayout addFriend;
    }

    class FriendHolder {
        ExpandableListView expandableListView;
    }
    private OnClickChildListenter onClickChildListenter;

    public interface OnClickChildListenter{
        void onClick(int mid);
    }

    public void setOnClickChildListenter(OnClickChildListenter onClickChildListenter) {
        this.onClickChildListenter = onClickChildListenter;
    }
}

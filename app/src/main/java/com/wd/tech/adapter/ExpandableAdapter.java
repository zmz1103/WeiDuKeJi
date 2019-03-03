package com.wd.tech.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.bean.Group;

import java.util.ArrayList;
import java.util.List;

import static com.wd.tech.util.UIUtils.getResources;

/**
 * date:2019/2/22 18:34
 * author:赵明珠(啊哈)
 * function:
 */
public class ExpandableAdapter extends BaseExpandableListAdapter{
    private Context context;

    List<Group> mList = new ArrayList<>();

    public ExpandableAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getFriendInfoList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getFriendInfoList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        DivideHolder holder=null;

        Log.e("zmz"+groupPosition,mList.size()+"二级groupPosition:"+mList.get(groupPosition).getGroupName());

        if (convertView == null) {
            holder = new DivideHolder();
            convertView = View.inflate(context, R.layout.activity_friend_id_item, null);
            holder.relativeLayout = convertView.findViewById(R.id.on_off);
            holder.off = convertView.findViewById(R.id.off);
            holder.gruppnamn = convertView.findViewById(R.id.gruppnamn);
            holder.number = convertView.findViewById(R.id.number);
            convertView.setTag(holder);
        } else {
            holder = (DivideHolder) convertView.getTag();
        }

        holder.gruppnamn.setText(mList.get(groupPosition).getGroupName());
        holder.number.setText("0/" + String.valueOf(mList.get(groupPosition).getCurrentNumber()));

        if (isExpanded){
            holder.relativeLayout.setBackground(getResources().getDrawable(R.color.TextTure));
            holder.off.setImageResource(R.drawable.caret_bottom);
        }else {
            holder.off.setImageResource(R.drawable.caret_left);
            holder.relativeLayout.setBackground(getResources().getDrawable(R.color.white));
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.e("zmz","childPosition:"+childPosition);

        FriendInfoList friendInfoList = mList.get(groupPosition).getFriendInfoList().get(childPosition);

        Log.e("zmz","==0=="+friendInfoList.getNickName());
        ChildHolder childHolder;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = View.inflate(context, R.layout.activity_friend_child_id_item, null);
            childHolder.head = convertView.findViewById(R.id.friend_head);
            childHolder.nickName = convertView.findViewById(R.id.friend_nickname);
            childHolder.signature = convertView.findViewById(R.id.friend_signature);
            convertView.setTag(childHolder);
        }else {
            childHolder = (ChildHolder) convertView.getTag();
        }


        childHolder.head.setImageURI(friendInfoList.getHeadPic());
        childHolder.nickName.setText(friendInfoList.getNickName());
        childHolder.signature.setText(friendInfoList.getSignature());




        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void addAll(List<Group> mGroupList) {
        mList.clear();
        if (mGroupList != null)
            mList.addAll(mGroupList);
    }

    class DivideHolder {
        RelativeLayout relativeLayout;
        ImageView off;
        TextView gruppnamn, number;
    }
    class ChildHolder {
        SimpleDraweeView head;
        TextView nickName;
        TextView signature;
    }

}

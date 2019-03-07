package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.GroupNoticeActivity;
import com.wd.tech.bean.FriendInfoList;
import com.wd.tech.bean.FriendInform;
import com.wd.tech.bean.MyGroup;
import com.wd.tech.util.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * date:2019/3/6 10:54
 * author:赵明珠(啊哈)
 * function:
 */
public class GroupNoticeAdapter extends RecyclerView.Adapter<GroupNoticeAdapter.ViewHolder>{
    private Context context;
    List<FriendInform> mList = new ArrayList<>();

    public GroupNoticeAdapter(Context context) {
        this.context = context;
    }
    public void addAll(List<FriendInform> mResult) {
        mList.addAll(mResult);
    }

    public void clear() {
        mList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.activity_group_notice_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final FriendInform friendInform = mList.get(i);

        try {
            viewHolder.mTime.setText(DateUtils.dateFormat(new Date(friendInform.getNoticeTime()),DateUtils.HOUR_MINUTE_ONLY_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.mSimple.setImageURI(friendInform.getHeadPic());
        viewHolder.mName.setText(friendInform.getNickName()+"");

        if (friendInform.getType() != 1){
            viewHolder.mMessage.setText(friendInform.getNickName()+"申请进群");
        }else {
            viewHolder.mMessage.setText(friendInform.getNickName()+"进入群组");
        }

        if (friendInform.getStatus() != 1){
            viewHolder.mConsent.setVisibility(View.GONE);
            viewHolder.mRefuse.setVisibility(View.GONE);
            if (friendInform.getStatus() == 2){
                viewHolder.mText.setText("已通过");
            }else {
                viewHolder.mText.setText("已拒绝");
            }
        }else {
            viewHolder.mText.setVisibility(View.GONE);
            viewHolder.mConsent.setVisibility(View.VISIBLE);
            viewHolder.mRefuse.setVisibility(View.VISIBLE);
        }

        viewHolder.mConsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(friendInform.getNoticeId(),1);
            }
        });

        viewHolder.mRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(friendInform.getNoticeId(),2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimple;
        TextView mName;
        TextView mMessage;
        TextView mConsent;
        TextView mRefuse;
        TextView mTime;
        TextView mText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.flag);
            mName = itemView.findViewById(R.id.group_notice_name);
            mSimple = itemView.findViewById(R.id.group_notice_headpic);
            mMessage = itemView.findViewById(R.id.group_notice_message);
            mConsent = itemView.findViewById(R.id.group_notice_consent);
            mRefuse = itemView.findViewById(R.id.group_notice_refuse);
            mTime = itemView.findViewById(R.id.group_notice_time);

        }
    }
    public OnClickListener onClickListener;
    public interface OnClickListener{
        void onClick(int id,int notice);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}

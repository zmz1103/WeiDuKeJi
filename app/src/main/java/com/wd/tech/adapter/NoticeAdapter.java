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
import com.nostra13.universalimageloader.utils.L;
import com.wd.tech.R;
import com.wd.tech.bean.FriendInform;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/28 10:40
 * author:赵明珠(啊哈)
 * function:
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeHolder> {

    private Context context;
    private List<FriendInform> mList = new ArrayList<>();
    int num = 2;
    int sum = 3;



    public NoticeAdapter(Context context) {
        this.context = context;
    }


    public void addAll(List<FriendInform> mListNotice) {
        mList.addAll(mListNotice);
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_notice_friend_item, null);

        return new NoticeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoticeHolder holder, final int i) {
        FriendInform friendInform = mList.get(i);

        holder.mSimple.setImageURI(Uri.parse(friendInform.getFromHeadPic()));
        holder.mName.setText(friendInform.getFromNickName());


        holder.mConsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = holder.getLayoutPosition();

                FriendInform friendInform = mList.get(layoutPosition);

                int noticeId = friendInform.getNoticeId();

                onItemClickListenter.onItemClick(noticeId, num);
            }
        });

        holder.mRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = holder.getLayoutPosition();

                FriendInform friendInform = mList.get(layoutPosition);

                int noticeId = friendInform.getNoticeId();

                onItemClickListenter.onItemClick(noticeId, sum);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    class NoticeHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mSimple;
        TextView mName;
        TextView mConsent, mRefuse;

        public NoticeHolder(@NonNull View itemView) {
            super(itemView);
            mSimple = itemView.findViewById(R.id.headpic_notice);
            mName = itemView.findViewById(R.id.name_notice);
            mConsent = itemView.findViewById(R.id.consent);
            mRefuse = itemView.findViewById(R.id.refuse);
        }
    }
    public OnItemClickListenter onItemClickListenter;


    public interface OnItemClickListenter {
        void onItemClick(int id, int state);
    }

    public void setOnItemClickListenter(OnItemClickListenter onItemClickListenter) {
        this.onItemClickListenter = onItemClickListenter;
    }
}
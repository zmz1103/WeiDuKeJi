package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.tech.R;
import com.wd.tech.activity.AuditFriendActivity;
import com.wd.tech.bean.FriendInform;
import com.wd.tech.util.DateUtils;
import com.wd.tech.util.SpaceItemDecoration;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wd.tech.util.UIUtils.getResources;
import static com.wd.tech.util.UIUtils.isRunInMainThread;

/**
 * date:2019/2/27 20:32
 * author:赵明珠(啊哈)
 * function:
 */
public class AuditAdapter extends RecyclerView.Adapter<AuditAdapter.CheckHolder> {

    private Context context;
    private List<FriendInform> mList = new ArrayList<>();
    boolean flag = false;
    boolean notice = false;
    private List<FriendInform> mListNotice = new ArrayList<>();
    private List<FriendInform> mListProcessed = new ArrayList<>();

    public AuditAdapter(Context context) {
        this.context = context;
    }

    public void clear() {
        mList.clear();
        mListNotice.clear();
        mListProcessed.clear();
    }

    public void addAll(List<FriendInform> mResult) {
        mList.addAll(mResult);


        for (int i = 0; i < mResult.size(); i++) {
            FriendInform friendInform = mResult.get(i);

            if (friendInform.getStatus() != 1) {
                mListProcessed.add(friendInform);


            } else {
                mListNotice.add(friendInform);
            }

        }
    }

    @NonNull
    @Override
    public CheckHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.activity_audit_friend_check_item, null);

        return new CheckHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckHolder checkHolder, int i) {
        Log.e("zmz" + "mList:" + mList.size(), "   mListNotice:" + mListNotice.size() + "    mListProcessed:" + mListProcessed.size());

        if (i == 1 && mListProcessed.size()>0){
                try {
                    checkHolder.mNoticetime.setVisibility(View.VISIBLE);
                    String dateTime = DateUtils.dateFormat(new Date(mList.get(i).getNoticeTime()), DateUtils.HOUR_MINUTE_ONLY_PATTERN);
                    checkHolder.mNoticetime.setText(dateTime);
                    notice = true;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                checkHolder.mAdapter.clear();

                checkHolder.mRecycleView.setAdapter(checkHolder.mAdapter);

                checkHolder.mAdapter.addAll(mListProcessed);

                checkHolder.mAdapter.notifyDataSetChanged();

            }else if (i == 0 && mListNotice.size() > 0) {
            try {
                checkHolder.mNoticetime.setVisibility(View.VISIBLE);
                String dateTime = DateUtils.dateFormat(new Date(mList.get(i).getNoticeTime()), DateUtils.HOUR_MINUTE_ONLY_PATTERN);
                checkHolder.mNoticetime.setText(dateTime);
                notice = true;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            checkHolder.mNoticeAdapter.clear();

            checkHolder.mRecycleView.setAdapter(checkHolder.mNoticeAdapter);

            checkHolder.mNoticeAdapter.addAll(mListNotice);


        } else {
            checkHolder.mNoticetime.setBackground(getResources().getDrawable(R.color.transparency));
            checkHolder.mNoticetime.setVisibility(View.GONE);
        }
        if (mListNotice.size() == 0 && mListProcessed.size() == 1){
            try {
                checkHolder.mNoticetime.setBackground(getResources().getDrawable(R.color.colorText99));
                checkHolder.mNoticetime.setVisibility(View.VISIBLE);
                String dateTime = DateUtils.dateFormat(new Date(mList.get(i).getNoticeTime()), DateUtils.HOUR_MINUTE_ONLY_PATTERN);
                checkHolder.mNoticetime.setText(dateTime);
                notice = true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            checkHolder.mAdapter.clear();

            checkHolder.mRecycleView.setAdapter(checkHolder.mAdapter);

            checkHolder.mAdapter.addAll(mListProcessed);


        }

        checkHolder.mNoticeAdapter.notifyDataSetChanged();

        checkHolder.mAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    class CheckHolder extends RecyclerView.ViewHolder {

        RecyclerView mRecycleView;
        TextView mNoticetime;
        ProcessedAdapter mAdapter;
        NoticeAdapter mNoticeAdapter;

        public CheckHolder(@NonNull View itemView) {
            super(itemView);
            mNoticetime = itemView.findViewById(R.id.notice_time);
            mRecycleView = itemView.findViewById(R.id.notice_item);
            mAdapter = new ProcessedAdapter(context);
            mNoticeAdapter = new NoticeAdapter(context);
            mRecycleView.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.VERTICAL, false));
            mRecycleView.addItemDecoration(new SpaceItemDecoration(20));

            mNoticeAdapter.setOnItemClickListenter(new NoticeAdapter.OnItemClickListenter() {
                @Override
                public void onItemClick(int id, int state) {
                    onClickListenter.onItemClick(id, state);
                }
            });

        }
    }

    public OnClickListenter onClickListenter;


    public interface OnClickListenter {
        void onItemClick(int id, int state);
    }

    public void setOnClickListenter(OnClickListenter onClickListenter) {
        this.onClickListenter = onClickListenter;
    }

}

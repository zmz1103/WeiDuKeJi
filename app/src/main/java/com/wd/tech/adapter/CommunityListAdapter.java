package com.wd.tech.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.AllCommentActivity;
import com.wd.tech.activity.StartActivity;
import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.communityCommentVoList;
import com.wd.tech.util.DateUtils;
import com.wd.tech.util.StringUtils;
import com.wd.tech.view.RecyclerGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jaydenxiao.com.expandabletextview.ExpandableTextView;

/**
 * date: 2019/2/19.
 * Created 王思敏
 * function:社区列表
 */
public class CommunityListAdapter extends RecyclerView.Adapter<CommunityListAdapter.ViewHolder> {
    private Context context;
    private List<CommunitylistData> list;
    private List<communityCommentVoList> mCommunitylist;
    private int mImageCount;
    private CommentAdapter mCommentAdapter;

    public CommunityListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void addAll(List<CommunitylistData> result) {
        if (result !=null){
            list.addAll(result);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    //接口回调
    public interface onCommunityListClickListener{
        //点击头像
        void onmHeadPicClick(int userid);
        //评论
        void onmCommentClick(int id,String name);
        //点赞
        void onmPraiseClick(int id,int whetherGreat);
    }

    public onCommunityListClickListener mOnCommunityListClickListener;

    public void setOnCommunityListClickListener(onCommunityListClickListener onCommunityListClickListener){
        mOnCommunityListClickListener = onCommunityListClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_recy_adapter, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final CommunitylistData data = list.get(i);
        viewHolder.mHeadPic.setImageURI(Uri.parse(data.getHeadPic()));
        viewHolder.mNickName.setText(data.getNickName());
        Date date = new Date();
        date.setTime(data.getPublishTime());
        viewHolder.mPublishTime.setText(DateUtils.getTimeFormatText(date));

        //转换成日期格式
//        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN,Locale.getDefault());
//        viewHolder.mPublishTime.setText(dateFormat.format(data.getPublishTime()));
        viewHolder.mSignature.setText(data.getSignature());
        viewHolder.mCommentNum.setText(""+data.getComment());
        viewHolder.mPraiseNum.setText(""+data.getPraise());
        viewHolder.mContent.setText(data.getContent());

        //登录用户是否点过赞
        if (list.get(i).getWhetherGreat()==1){
            viewHolder.mPraise.setImageResource(R.mipmap.common_icon_praise_s);
        } else {
            viewHolder.mPraise.setImageResource(R.mipmap.common_icon_prise_n);
        }

        //图片
        if (StringUtils.isEmpty(data.getFile())){
            viewHolder.mGridView.setVisibility(View.GONE);
        }else{
            viewHolder.mGridView.setVisibility(View.VISIBLE);
            String[] images = data.getFile().split(",");
            mImageCount = images.length;

            int colNum;//列数
            if (mImageCount == 1){
                colNum = 1;
            }else if (mImageCount == 2||mImageCount == 4){
                colNum = 2;
            }else {
                colNum = 3;
            }
            viewHolder.imageAdapter.clear();
            viewHolder.imageAdapter.addAll(Arrays.asList(images));
            viewHolder.mGridView.setNumColumns(colNum);
            viewHolder.imageAdapter.notifyDataSetChanged();
        }


        mCommunitylist = list.get(i).getCommunityCommentVoList();
        mCommentAdapter = new CommentAdapter(context,mCommunitylist);
        viewHolder.mCommentRecy.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.mCommentRecy.setAdapter(mCommentAdapter);
        mCommentAdapter.notifyDataSetChanged();


        //头像监听
        viewHolder.mHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnCommunityListClickListener !=null){
                    mOnCommunityListClickListener.onmHeadPicClick(data.getUserId());
                }
            }
        });
        //评论监听
        viewHolder.mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnCommunityListClickListener !=null){
                    mOnCommunityListClickListener.onmCommentClick(data.getId(),"@"+list.get(i).getNickName()+"回复：");
                }
            }
        });
        //点赞监听
        viewHolder.mPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int whetherGreat = list.get(i).getWhetherGreat();
                if (mOnCommunityListClickListener !=null){
                    mOnCommunityListClickListener.onmPraiseClick(data.getId(),whetherGreat);
                }
                if (list.get(i).getWhetherGreat()==2){
                    list.get(i).setWhetherGreat(1);
                    list.get(i).setPraise(list.get(i).getPraise()+1);
                    notifyItemChanged(i);
                }else {
                    list.get(i).setWhetherGreat(2);
                    list.get(i).setPraise(list.get(i).getPraise()-1);
                    notifyItemChanged(i);
                }
            }
        });
        if (list.get(i).getComment() > 0) {
            viewHolder.mCommentPl.setText("点击查看更多评论");
            viewHolder.mCommentPl.setTextColor(Color.parseColor("#20affa"));
            viewHolder.mCommentPl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,AllCommentActivity.class);
                    intent.putExtra("communityId",list.get(i).getId());
                    intent.putExtra("comment",list.get(i).getComment()+"");
                    intent.putExtra("headPic",list.get(i).getHeadPic());
                    intent.putExtra("nickName",list.get(i).getNickName());
                    context.startActivity(intent);
                }
            });

        }else {
            viewHolder.mCommentPl.setText("快来评论吧~");
            viewHolder.mCommentPl.setTextColor(Color.parseColor("#999999"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNickName;
        private final SimpleDraweeView mHeadPic;
        private final TextView mPublishTime;
        private final TextView mSignature;
        private final ExpandableTextView mContent;
        private final RecyclerGridView mGridView;
        private final ImageView mComment;
        private final TextView mCommentNum;
        private final ImageView mPraise;
        private final TextView mPraiseNum;
        private final ImageAdapter imageAdapter;
        private final RecyclerView mCommentRecy;
        private final TextView mCommentPl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadPic = itemView.findViewById(R.id.community_list_headpic);
            mNickName = itemView.findViewById(R.id.community_list_nickname);
            mPublishTime = itemView.findViewById(R.id.community_list_publishtime);
            mSignature = itemView.findViewById(R.id.community_list_signature);
            mContent = itemView.findViewById(R.id.community_list_content);
            mGridView = itemView.findViewById(R.id.community_list_grid_view);
            mComment = itemView.findViewById(R.id.community_list_comment);
            mCommentNum = itemView.findViewById(R.id.community_list_comment_num);
            mPraise = itemView.findViewById(R.id.community_list_praise);
            mPraiseNum = itemView.findViewById(R.id.community_list_praise_num);
            mCommentRecy = itemView.findViewById(R.id.comment_recy);
            mCommentPl = itemView.findViewById(R.id.comment_pl);
            imageAdapter = new ImageAdapter();
            int space = context.getResources().getDimensionPixelSize(R.dimen.dp_10);;//图片间距
            mGridView.setHorizontalSpacing(space);
            mGridView.setVerticalSpacing(space);
            mGridView.setAdapter(imageAdapter);
        }
    }


}

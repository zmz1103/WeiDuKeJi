package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.CommunityUserPostVoListBean;
import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.util.StringUtils;
import com.wd.tech.view.RecyclerGridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jaydenxiao.com.expandabletextview.ExpandableTextView;

/**
 * date: 2019/2/20.
 * Created 王思敏
 * function:好友帖子页
 */
public class FriendsPostAdapter extends RecyclerView.Adapter<FriendsPostAdapter.ViewHolder> {
    private Context context;
    private List<CommunityUserPostVoListBean> list;


    public FriendsPostAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setAll(List<CommunityUserPostVoListBean> postVoList) {
        if (postVoList !=null){
            list.addAll(postVoList);
        }
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public CommunityUserPostVoListBean getItem(int position) {
        return list.get(position);
    }

    public interface OnFriendsPostClickListener{
        //评论
        void onCommentClick(int id);
        //点赞
        void onPraiseClick(int id,int whetherGreat);
    }
    public OnFriendsPostClickListener mOnFriendsPostClickListener;
    public void setOnFriendsPostClickListener(OnFriendsPostClickListener onFriendsPostClickListener){
        mOnFriendsPostClickListener = onFriendsPostClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.friendspost_recy_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final CommunityUserPostVoListBean listBean = list.get(i);
        viewHolder.mFriendspostContent.setText(listBean.getContent());
        viewHolder.mFriendspostCommentNum.setText(""+listBean.getComment());
        viewHolder.mFriendspostPraiseNum.setText(""+listBean.getPraise());
        if (listBean.getWhetherGreat()==1){
            viewHolder.mFriendspostpraise.setImageResource(R.mipmap.common_icon_praise_s);
        } else {
            viewHolder.mFriendspostpraise.setImageResource(R.mipmap.common_icon_prise_n);
        }
        //图片
        if (StringUtils.isEmpty(listBean.getFile())){
            viewHolder.mFriendspostGridView.setVisibility(View.GONE);
        }else{
            viewHolder.mFriendspostGridView.setVisibility(View.VISIBLE);
            String[] images = listBean.getFile().split(",");
            int imageCount = images.length;

            int colNum;//列数
            if (imageCount == 1){
                colNum = 1;
            }else if (imageCount == 2||imageCount == 4){
                colNum = 2;
            }else {
                colNum = 3;
            }
            viewHolder.imageAdapter.clear();
            viewHolder.imageAdapter.addAll(Arrays.asList(images));
            viewHolder.mFriendspostGridView.setNumColumns(colNum);
            viewHolder.imageAdapter.notifyDataSetChanged();
        }

        //评论
        viewHolder.mFriendspostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnFriendsPostClickListener !=null){
                    mOnFriendsPostClickListener.onCommentClick(listBean.getId());
                }

            }
        });
        //点赞
        viewHolder.mFriendspostpraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int whetherGreat = list.get(i).getWhetherGreat();
                if (mOnFriendsPostClickListener !=null){
                    mOnFriendsPostClickListener.onPraiseClick(list.get(i).getId(),whetherGreat);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ExpandableTextView mFriendspostContent;
        private final RecyclerGridView mFriendspostGridView;
        private final ImageView mFriendspostComment;
        private final TextView mFriendspostCommentNum;
        private final ImageView mFriendspostpraise;
        private final TextView mFriendspostPraiseNum;
        private final ImageAdapter imageAdapter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFriendspostContent = itemView.findViewById(R.id.friendspost_content);
            mFriendspostGridView = itemView.findViewById(R.id.friendspost_grid_view);
            mFriendspostComment = itemView.findViewById(R.id.friendspost_comment);
            mFriendspostCommentNum = itemView.findViewById(R.id.friendspost_comment_num);
            mFriendspostpraise = itemView.findViewById(R.id.friendspost_praise);
            mFriendspostPraiseNum = itemView.findViewById(R.id.friendspost_praise_num);
            imageAdapter = new ImageAdapter();
            mFriendspostGridView.setVerticalSpacing(10);
            mFriendspostGridView.setAdapter(imageAdapter);
        }
    }
}

package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.communityCommentVoList;

import org.w3c.dom.Text;

import java.util.List;

/**
 * date: 2019/2/21.
 * Created 王思敏
 * function:社区评论
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<communityCommentVoList> list;

    public CommentAdapter(Context context, List<communityCommentVoList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_recy, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        communityCommentVoList commentVoList = list.get(i);
        viewHolder.mCommentName.setText(commentVoList.getNickName());
        viewHolder.mCommentName.setText(commentVoList.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mCommentName;
        private final TextView mCommentContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCommentName = itemView.findViewById(R.id.comment_txt_name);
            mCommentContent = itemView.findViewById(R.id.comment_txt_content);
        }
    }
}

package com.wd.tech.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.CardListData;
import com.wd.tech.util.StringUtils;
import com.wd.tech.util.ToDate;
import com.wd.tech.view.RecyclerRecView;
import com.wd.tech.view.SpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import jaydenxiao.com.expandabletextview.ExpandableTextView;

/**
 * 作者: Wang on 2019/2/25 19:57
 * 寄语：加油！相信自己可以！！！
 */


public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.Vh> {

    private List<CardListData> mListData;
    private FragmentActivity mContext;
    private int mLieNum = 1;
    public DeletePostInterface mDeletePostInterface;

    public void setmDeletePostInterface(DeletePostInterface mDeletePostInterface) {
        this.mDeletePostInterface = mDeletePostInterface;
    }

    public CardListAdapter(FragmentActivity context) {
        this.mListData = new ArrayList<>();
        this.mContext = context;
    }
    public interface DeletePostInterface {
        void onclick(int i );
    }
    public void setmListData(List<CardListData> mListData) {
        if (mListData != null) {
            this.mListData.addAll(mListData);
        }
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(View.inflate(mContext, R.layout.adapter_card_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, final int i) {
        final CardListData cardListData = mListData.get(i);
        vh.mContent.setText(cardListData.getContent());
        vh.mDzText.setText(String.valueOf(cardListData.getPraise()));
        vh.mPlText.setText(String.valueOf(cardListData.getComment()));
        vh.mTimeText.setText(ToDate.timedate(cardListData.getPublishTime()));


        // 图片
        // 图片
        if (StringUtils.isEmpty(cardListData.getFile())) {
            vh.mGird.setVisibility(View.GONE);
        } else {
            vh.mGird.setVisibility(View.VISIBLE);
            String[] split = cardListData.getFile().split(",");
            Log.v("帖子--图片",split.length+"");
            if (split.length == 1) {
                mLieNum = 1;
            } else if (split.length == 2 || split.length == 4) {
                mLieNum = 2;
            } else {
                mLieNum = 3;
            }
            vh.mGridLayoutManager.setSpanCount(mLieNum);// 设置列数
            final List<Object> list = new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                list.add(split[j]);
            }
            vh.mImageAdapter.clear();// 清空集合
            vh.mImageAdapter.addAll(list);// 添加图片
            vh.mImageAdapter.notifyDataSetChanged();



        }
        // 删除帖子
        vh.mScText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ""+cardListData.getId(), Toast.LENGTH_SHORT).show();
                mDeletePostInterface.onclick(cardListData.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void clear() {
        mListData.clear();
    }

    public class Vh extends RecyclerView.ViewHolder{


        private final TextView mPlText, mDzText,mTimeText, mScText;
        private final ImageView mDzImage,mPlImage;
        private final RecyclerRecView mGird;

        private ExpandableTextView mContent;

        GridLayoutManager mGridLayoutManager;
        ImageRecAdapter mImageAdapter;

        public Vh(@NonNull View itemView) {
            super(itemView);
            mPlText = itemView.findViewById(R.id.card_pl_text);
            mDzText = itemView.findViewById(R.id.card_dz_textview);
            mContent = itemView.findViewById(R.id.card_etv_textview);
            mTimeText = itemView.findViewById(R.id.card_time_textview);
            mScText = itemView.findViewById(R.id.card_sc_text);
            mGird = itemView.findViewById(R.id.card_rec_list);
            mDzImage = itemView.findViewById(R.id.card_dz_img);
            mPlImage = itemView.findViewById(R.id.card_dz_img);

            mImageAdapter = new ImageRecAdapter(mContext);// 适配器
            int size = mContext.getResources().getDimensionPixelSize(R.dimen.dp_10);// 条目间的间距
            mGird.addItemDecoration(new SpacingItemDecoration(size));// 添加间距
            mGridLayoutManager = new GridLayoutManager(mContext, mLieNum);//
            mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            mGird.setLayoutManager(mGridLayoutManager);
            mGird.setAdapter(mImageAdapter);//设置适配器

        }
    }
}

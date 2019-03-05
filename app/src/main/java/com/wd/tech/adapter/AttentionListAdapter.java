package com.wd.tech.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.FriendsPostActivity;
import com.wd.tech.bean.AttentionListData;
import com.wd.tech.util.RecyclerUtils;
import com.wd.tech.view.RecyclerItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Wang on 2019/2/25 18:40
 * 寄语：加油！相信自己可以！！！
 */


public class AttentionListAdapter extends RecyclerView.Adapter<AttentionListAdapter.Vh> implements RecyclerItemView.onSlidingButtonListener {

    private List<AttentionListData> mListData;
    private Context mContext;

    public AttentionListAdapter(Context context) {
        this.mListData = new ArrayList<>();
        this.mContext = context;
    }

    private onSlidingViewClickListener onSvcl;

    private RecyclerItemView recyclers;

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(View.inflate(mContext, R.layout.adapter_atten_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull final Vh vh,  int i) {
        vh.mSignsture.setText(mListData.get(i).getSignature());
        vh.mIcon.setImageURI(mListData.get(i).getHeadPic());
        vh.mTitle.setText(mListData.get(i).getNickName());

        vh.mLayoutLeft.getLayoutParams().width = RecyclerUtils.getScreenWidth(mContext);

        vh.mLayoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    //获得布局下标（点的哪一个）
                    int i1 = vh.getLayoutPosition();
                    Log.v("name++",mListData.get(i1).getNickName()+mListData.get(i1).getUserId());
                    Toast.makeText(mContext, ""+mListData.get(i1).getUserId(), Toast.LENGTH_SHORT).show();
                    onSvcl.onItemClick( mListData.get(i1).getFocusUid());
                }
            }
        });

        vh.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSvcl.onDeleteBtnCilck(mListData.get(vh.getLayoutPosition()).getFocusUid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void setmListData(List<AttentionListData> mListData) {
        if (mListData != null) {
            this.mListData.addAll(mListData);
        }
    }

    public void clear() {
        mListData.clear();
    }


    public class Vh extends RecyclerView.ViewHolder {

        private final SimpleDraweeView mIcon;
        private final TextView mTitle, mSignsture;
        private final LinearLayout mLayoutLeft;
        private final TextView mDelete ;

        public Vh(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.atten_item_sim);
            mTitle = itemView.findViewById(R.id.atten_item_title);
            mSignsture = itemView.findViewById(R.id.atten_item_qm);

            mLayoutLeft = itemView.findViewById(R.id.layout_left);
            mDelete = itemView.findViewById(R.id.delete);

            ((RecyclerItemView) itemView).setSlidingButtonListener(AttentionListAdapter.this);
        }
    }

    //设置在滑动侦听器上
    public void setOnSlidListener(onSlidingViewClickListener listener) {
        onSvcl = listener;
    }


    @Override
    public void onDownOrMove(RecyclerItemView recycler) {
        if (menuIsOpen()) {
            if (recyclers != recycler) {
                closeMenu();
            }
        }
    }

    //关闭菜单
    public void closeMenu() {
        recyclers.closeMenu();
        recyclers = null;
    }

    // 判断是否有菜单打开
    public Boolean menuIsOpen() {
        if (recyclers != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onMenuIsOpen(View view) {
        recyclers = (RecyclerItemView) view;
    }

    // 在滑动视图上单击侦听器
    public interface onSlidingViewClickListener {
        void onItemClick( int position);

        void onDeleteBtnCilck(int position);
    }

}

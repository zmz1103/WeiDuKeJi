package com.wd.tech.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * date: 2019/2/20.
 * Created 王思敏
 * function:发布帖子图片得适配器
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyHodler>{

    FragmentActivity content;

    public PictureAdapter(FragmentActivity content) {
        this.content = content;
    }

    private List<Object> mList = new ArrayList<>();

    public void addAll(List<Object> list) {
        mList.addAll(list);
    }


    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.picture_image_item, null);
        return new MyHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHodler myHodler, final int i) {
        if (mList.get(i) instanceof String) {
            String imageUrl = (String) mList.get(i);
            if (imageUrl.contains("http:")) {//加载http
                myHodler.image.setImageURI(Uri.parse(imageUrl));
            } else {//加载sd卡
                Uri uri = Uri.parse("file://" + imageUrl);
                myHodler.image.setImageURI(uri);
            }
        } else {//加载资源文件
            int id = (int) mList.get(i);
            Uri uri = Uri.parse("res://com.dingtao.rrmmp/" + id);
            myHodler.image.setImageURI(uri);
        }

        myHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i==0){
                    mOnImageClickListener.onImageClick();
                }
            }
        });
        myHodler.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (i!=0){
                    if (myHodler.mLongress.getVisibility()==View.GONE){
                        myHodler.mLongress.setVisibility(View.VISIBLE);
                        myHodler.mLongress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                notifyItemChanged(i);
                                del(i);
                                Toast.makeText(content, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else {
                    Log.i("相册选择","相机选择");
                }

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    public void add(Object image) {
        if (image != null) {
            mList.add(image);
        }
    }

    public void del(int position){
            mList.remove(position);
            notifyItemRemoved(position);
            notifyItemChanged(position);
    }
    public List<Object> getList() {
        return mList;
    }


    public class MyHodler extends RecyclerView.ViewHolder {
        private final ImageView mLongress;
        SimpleDraweeView image;

        public MyHodler(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.community_image);
            mLongress = itemView.findViewById(R.id.long_ress);
        }
    }
    public interface OnImageClickListener{
        void onImageClick();
    }
    public OnImageClickListener mOnImageClickListener;
    public void setOnImageClickListener(OnImageClickListener onImageClickListener){
        mOnImageClickListener = onImageClickListener;
    }
}


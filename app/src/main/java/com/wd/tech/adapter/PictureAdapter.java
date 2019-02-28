package com.wd.tech.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
    private int sign;//0:普通点击，1自定义

    public void addAll(List<Object> list) {
        mList.addAll(list);
    }

    public void setSign(int sign){
        this.sign = sign;
    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.circle_image_item, null);
        return new MyHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHodler myHodler, final int i) {
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

    }

    @Override
    public int getItemCount() {
        if(mList == null){
            return 0;
        }
        return mList.size() >= 9 ? 9 : mList.size();
    }

    public void add(Object image) {
        if (image != null) {
            mList.add(image);
        }
    }
    public List<Object> getList() {
        return mList;
    }


    public class MyHodler extends RecyclerView.ViewHolder {
        SimpleDraweeView image;

        public MyHodler(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.community_image);
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


package com.wd.tech.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;

import java.util.ArrayList;
import java.util.List;


public class ImageRecAdapter extends RecyclerView.Adapter<ImageRecAdapter.MyHodler> {

    private List<Object> mList = new ArrayList<>();
    private int sign;//0:普通点击，1自定义

    public void addAll(List<Object> list) {
        mList.addAll(list);
    }

    FragmentActivity context;

    public void setSign(int sign) {
        this.sign = sign;
    }

    public void add(Object image) {
        if (image != null) {
            mList.add(image);
        }
    }

    public List<Object> getList() {
        return mList;
    }

    public ImageRecAdapter(FragmentActivity context) {
        this.context = context;
    }

    //    @Override
//    public int getCount() {
//        return mList.size();
//    }

//    @Override
//    public Object getItem(int position) {
//        return mList.get(position);
//    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.mycircle_image_item, null);
        return new MyHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHodler myHodler, final int position) {
//        String o = (String) mList.get(position);
//        myHodler.image.setImageURI(Uri.parse(o));

        if (mList.get(position) instanceof String) {
            String imageUrl = (String) mList.get(position);
            if (imageUrl.contains("http:")) {//加载http
                myHodler.mImage.setImageURI(Uri.parse(imageUrl));
            } else {//加载sd卡
                Uri uri = Uri.parse("file://" + imageUrl);
                myHodler.mImage.setImageURI(uri);
            }
        } else {//加载资源文件
            int id = (int) mList.get(position);
            Uri uri = Uri.parse("res://com.dingtao.rrmmp/" + id);
            myHodler.mImage.setImageURI(uri);
        }

        myHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign == 1) {//自定义点击
                    if (position == 0) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        context.startActivityForResult(intent, WDActivity.PHOTO);
                    } else {
                        Toast.makeText(context, "点击了图片", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "点击了图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    class MyHodler extends RecyclerView.ViewHolder {
        SimpleDraweeView mImage;

        public MyHodler(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.mycircle_image);
        }
    }
}

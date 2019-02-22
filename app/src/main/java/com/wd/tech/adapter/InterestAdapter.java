package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.InterestListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/21
 * author:李阔(淡意衬优柔)
 * function:
 */
public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {

    Context context;
    List<InterestListBean> mListBeans;
    private InterestListBean mlist;


    public InterestAdapter(Context context) {
        this.context = context;
        mListBeans = new ArrayList<>();
    }

    public void reset(List<InterestListBean> result) {
        mListBeans.clear();
        mListBeans.addAll(result);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.interest_recycler_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        mlist = mListBeans.get(i);
        viewHolder.pic.setImageURI(mlist.getPic());
        viewHolder.text.setText(mlist.getName());
        if (mlist.getName().equals("电商消费")){
            viewHolder.textEnglish.setText("Electricity consumption");
        }else if (mlist.getName().equals("区块链")){
            viewHolder.textEnglish.setText("Block chain");
        }else if (mlist.getName().equals("AI世界")){
            viewHolder.textEnglish.setText("AI world");
        }else if (mlist.getName().equals("人工智能")){
            viewHolder.textEnglish.setText("Artificial intelligence");
        }else if (mlist.getName().equals("车与出行")){
            viewHolder.textEnglish.setText("Car and travel");
        }else if (mlist.getName().equals("智能终端")){
            viewHolder.textEnglish.setText("Intelligent terminal");
        }else if (mlist.getName().equals("金融地产")){
            viewHolder.textEnglish.setText("Financial property");
        }else if (mlist.getName().equals("大数据")){
            viewHolder.textEnglish.setText("Big Data");
        }else if (mlist.getName().equals("全球热点")){
            viewHolder.textEnglish.setText("Global hot spot");
        }else if (mlist.getName().equals("社交通讯")){
            viewHolder.textEnglish.setText("Social IM");
        }


    }

    @Override
    public int getItemCount() {
        return mListBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView pic;
        private TextView text;
        private TextView textEnglish;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            text = itemView.findViewById(R.id.text);
            textEnglish = itemView.findViewById(R.id.textEnglish);
        }
    }
}

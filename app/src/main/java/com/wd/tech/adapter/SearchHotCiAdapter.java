package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2019/2/25
 * author:李阔(淡意衬优柔)
 * function:
 */
public class SearchHotCiAdapter extends RecyclerView.Adapter<SearchHotCiAdapter.ViewHolder> {
    Context mContext;
    List<String> mList;


    public SearchHotCiAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void reset(List<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.searchresoulist_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mList.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFuzhi.hotci(mList.get(i));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
    public interface fuzhi{
        void hotci(String s);

    }
    private fuzhi mFuzhi;

    public void setFuzhi(fuzhi fuzhi) {
        mFuzhi = fuzhi;
    }
}

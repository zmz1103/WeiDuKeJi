package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wd.tech.R;
import com.wd.tech.bean.InformationSearchByTitleBean;
import com.wd.tech.util.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * date:2019/2/24
 * author:李阔(淡意衬优柔)
 * function:
 */
public class SearchByTitleAdapter extends RecyclerView.Adapter<SearchByTitleAdapter.ViewHolder> {
    Context mContext;
    List<InformationSearchByTitleBean> mSearchByTitleBeans;


    public SearchByTitleAdapter(Context context) {
        mContext = context;
        mSearchByTitleBeans = new ArrayList<>();
    }

    public void reset(List<InformationSearchByTitleBean> searchlist) {
        mSearchByTitleBeans.clear();
        mSearchByTitleBeans.addAll(searchlist);
        notifyDataSetChanged();

    }

    public void clear() {
        mSearchByTitleBeans.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.searchbytitle_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mSearchByTitleBeans.get(i).getTitle());
        viewHolder.zuozhe.setText(mSearchByTitleBeans.get(i).getSource());
        String date = DateUtils.stampToDate(mSearchByTitleBeans.get(i).getReleaseTime());
        viewHolder.time.setText(date);
    }




    @Override
    public int getItemCount() {
        return mSearchByTitleBeans.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView zuozhe;
        private TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            zuozhe = itemView.findViewById(R.id.zuozhe);
            time = itemView.findViewById(R.id.time);
        }
    }
}

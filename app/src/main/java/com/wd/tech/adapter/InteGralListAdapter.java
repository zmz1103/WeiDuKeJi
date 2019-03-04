package com.wd.tech.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.UserInteGralDataList2;
import com.wd.tech.bean.UserInteGralListData;
import com.wd.tech.util.ToDate;

import java.util.ArrayList;
import java.util.List;

import static com.wd.tech.util.UIUtils.getResources;

/**
 * 作者: Wang on 2019/2/28 10:35
 * 寄语：加油！相信自己可以！！！
 */


public class InteGralListAdapter extends RecyclerView.Adapter<InteGralListAdapter.Vh> {
    private List<UserInteGralDataList2> listData;
    private Context context;

    public void setListData(List<UserInteGralDataList2> listData) {
        if (listData != null) {
            this.listData.addAll(listData);
        }
    }

    public InteGralListAdapter(Context context) {
        this.listData = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Vh(View.inflate(context, R.layout.adapter_integral_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        UserInteGralDataList2 userInteGralDataList2 = listData.get(i);
        //1=签到  2=评论  3=分享 4=发帖  5=抽奖收入  6=付费资讯   type
        //7=抽奖支出  8=完善个人信息(单次奖励)  9=查看广告  10=绑定第三方
        vh.time.setText(ToDate.timedate(userInteGralDataList2.getCreateTime()));
        if (userInteGralDataList2.getDirection() == 1) {
            // 收入
            if (userInteGralDataList2.getType() == 1) {
                vh.title.setText("签到成功");
            } else if (userInteGralDataList2.getType() == 2) {
                vh.title.setText("评论成功");
            } else if (userInteGralDataList2.getType() == 3) {
                vh.title.setText("分享成功");
            } else if (userInteGralDataList2.getType() == 4) {
                vh.title.setText("发帖奖励");
            } else if (userInteGralDataList2.getType() == 5) {
                vh.title.setText("抽奖收入");
            } else if (userInteGralDataList2.getType() == 8) {
                vh.title.setText("完善信息奖励");
            } else if (userInteGralDataList2.getType() == 9) {
                vh.title.setText("查看广告奖励");
            } else if (userInteGralDataList2.getType() == 10) {
                vh.title.setText("绑定第三方奖励");
            }
            vh.jine.setTextColor(getResources().getColorStateList(R.color.jiacolor)
            );
            vh.jine.setText("+" + userInteGralDataList2.getAmount());
        } else {
            // 输出
            if (userInteGralDataList2.getType() == 6) {
                vh.title.setText("付费资讯");
            } else if (userInteGralDataList2.getType() == 7) {
                vh.title.setText("抽奖支出");
            }
            vh.jine.setTextColor(getResources().getColorStateList(R.color.jiancolor)
            );
            vh.jine.setText(userInteGralDataList2.getAmount() + "");
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void clear() {
        listData.clear();
    }

    public class Vh extends RecyclerView.ViewHolder {

        private final TextView jine, title, time;

        public Vh(@NonNull View itemView) {
            super(itemView);
            jine = itemView.findViewById(R.id.integral_item_jine);
            title = itemView.findViewById(R.id.integral_item_title);
            time = itemView.findViewById(R.id.integral_item_time);
        }
    }
}

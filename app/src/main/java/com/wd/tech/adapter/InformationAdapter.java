package com.wd.tech.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.bean.InformationListBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * date:2019/2/19
 * author:李阔(淡意衬优柔)
 * function:
 */
public class InformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<InformationListBean> mInformationListBeans;
    private String mThumbnail;
    private String[] mTu;

    public static final int TYPE_RIGHT_IMAGE = 1;
    public static final int TYPE_THREE_IMAGE = 2;
    private InformationListBean mListBean;
    private int mWhetherAdvertising;
    private String mPic;
    private String[] mPic2;
    private int mItemViewType;
    private SimpleDateFormat mSimpleDateFormat;


    public InformationAdapter(Context context) {
        this.context = context;
        mInformationListBeans = new ArrayList<>();
    }

    public void reset(List<InformationListBean> result) {
        mInformationListBeans.addAll(result);
        notifyDataSetChanged();
    }

    public void clear() {
        mInformationListBeans.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (TYPE_RIGHT_IMAGE == getItemViewType(i)) {
            View view = View.inflate(context, R.layout.informationlist1_item, null);
            return new InformationListMessage(view);
        } else {
            View view = View.inflate(context, R.layout.informationlist2_item, null);
            return new InformationListGuangGao(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        /*mItemViewType = getItemViewType(i);
        switch (mItemViewType){
            case TYPE_RIGHT_IMAGE:
                InformationListMessage holderA=(InformationListMessage)viewHolder;
                String[] split = mInformationListBeans.get(i).getThumbnail().split("\\?");
                holderA.simpleview.setImageURI(split[0]);
                holderA.title.setText(mInformationListBeans.get(i).getTitle());
                holderA.details.setText(mInformationListBeans.get(i).getSummary());
                holderA.zuozhe.setText(mInformationListBeans.get(i).getSource());
                break;




        }*/
        if (viewHolder instanceof InformationListMessage) {
            mThumbnail = mInformationListBeans.get(i).getThumbnail();
            mTu = mThumbnail.split("\\?");
            ((InformationListMessage)viewHolder).title.setText(mInformationListBeans.get(i).getTitle());
            ((InformationListMessage)viewHolder).details.setText(mInformationListBeans.get(i).getSummary());
            ((InformationListMessage)viewHolder).simpleview.setImageURI(mTu[0]);
            ((InformationListMessage)viewHolder).zuozhe.setText(mInformationListBeans.get(i).getSource());
            mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            ((InformationListMessage)viewHolder).time.setText(mSimpleDateFormat.format(mInformationListBeans.get(i).getReleaseTime()));
            if (mInformationListBeans.get(i).getWhetherCollection() == 1){
                ((InformationListMessage)viewHolder).shoucang.setImageResource(R.mipmap.common_icon_collect_s);
            }else{
                ((InformationListMessage)viewHolder).shoucang.setImageResource(R.mipmap.common_icon_collect_n);
            }
            //((InformationListMessage)viewHolder).fenxiangshu.setText(mInformationListBeans.get(i).getShare());
            //((InformationListMessage)viewHolder).shoucangshu.setText(mInformationListBeans.get(i).getCollection());


        }
        if (viewHolder instanceof InformationListGuangGao){
            /*if (mInformationListBeans.get(i).getInfoAdvertisingVo().getPic().equals("")){
                ((InformationListGuangGao)viewHolder).simpleview.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550659913819&di=3fb9f9de20a37a9bf1a101983656298f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201708%2F05%2F20170805134053_HzALE.png");
                ((InformationListGuangGao)viewHolder).textwenben.setText(mInformationListBeans.get(i).getInfoAdvertisingVo().getContent());
            }else {


                mPic = mInformationListBeans.get(i).getInfoAdvertisingVo().getPic();
                mPic2 = mPic.split("|");
                ((InformationListGuangGao)viewHolder).simpleview.setImageURI(mPic2[0]);
            }*/
        }


    }

    @Override
    public int getItemCount() {
        return mInformationListBeans.size();
    }

    @Override
    public int getItemViewType(int position) {

        mListBean = mInformationListBeans.get(position);
//返回条目类型，可以根据需要返回条目类型，我这就随意返回了两种，
        mWhetherAdvertising = mListBean.getWhetherAdvertising();
        if (mWhetherAdvertising == 2){
            return 1;
        }else {
            return 2;
        }

    }



    public class InformationListMessage extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleview;
        private TextView title;
        private TextView details;
        private TextView zuozhe;
        private TextView time;
        private ImageView shoucang;
        private TextView fenxiangshu;
        private TextView shoucangshu;

        public InformationListMessage(@NonNull View itemView) {
            super(itemView);
            simpleview = itemView.findViewById(R.id.simpleview);
            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
            zuozhe = itemView.findViewById(R.id.zuozhe);
            time = itemView.findViewById(R.id.time);
            shoucang = itemView.findViewById(R.id.shoucang);
            fenxiangshu = itemView.findViewById(R.id.fenxiangshu);
            shoucangshu = itemView.findViewById(R.id.shoucangshu);

        }
    }

    public class InformationListGuangGao extends RecyclerView.ViewHolder {
        private TextView textwenben;
        private SimpleDraweeView simpleview;

        public InformationListGuangGao(@NonNull View itemView) {
            super(itemView);
            textwenben = itemView.findViewById(R.id.textwenben);
            simpleview = itemView.findViewById(R.id.simpleview);
        }
    }
}

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
import com.wd.tech.util.TimeUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        if (i == TYPE_RIGHT_IMAGE) {
            View view = View.inflate(context, R.layout.informationlist1_item, null);
            return new InformationListMessage(view);
        } else {
            View view = View.inflate(context, R.layout.informationlist2_item, null);
            return new InformationListGuangGao(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof InformationListMessage) {
            mThumbnail = mInformationListBeans.get(i).getThumbnail();
            mTu = mThumbnail.split("\\?");
            ((InformationListMessage) viewHolder).title.setText(mInformationListBeans.get(i).getTitle());
            ((InformationListMessage) viewHolder).details.setText(mInformationListBeans.get(i).getSummary());
            ((InformationListMessage) viewHolder).simpleview.setImageURI(mTu[0]);
            ((InformationListMessage) viewHolder).zuozhe.setText(mInformationListBeans.get(i).getSource());
            if (mInformationListBeans.get(i).getWhetherPay() == 1){
                ((InformationListMessage) viewHolder).qiandai.setVisibility(View.VISIBLE);
            }else {
                ((InformationListMessage) viewHolder).qiandai.setVisibility(View.GONE);
            }


            Date date1 = new Date();
            date1.setTime(mInformationListBeans.get(i).getReleaseTime());
            ((InformationListMessage) viewHolder).time.setText(TimeUtil.getTimeFormatText(date1));

            if (mInformationListBeans.get(i).getWhetherCollection() == 1) {
                ((InformationListMessage) viewHolder).shoucang.setImageResource(R.mipmap.common_icon_collect_s);
            } else {
                ((InformationListMessage) viewHolder).shoucang.setImageResource(R.mipmap.common_icon_collect_n);
            }
            ((InformationListMessage) viewHolder).fenxiangshu.setText(mInformationListBeans.get(i).getShare() + "");
            ((InformationListMessage) viewHolder).shoucangshu.setText(mInformationListBeans.get(i).getCollection() + "");
            ((InformationListMessage) viewHolder).shoucang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int whetherCollection = mInformationListBeans.get(i).getWhetherCollection();
                    mAddcollection.addsuccess(mInformationListBeans.get(i).getId(), whetherCollection, i);

                    if (whetherCollection == 2) {
                        mInformationListBeans.get(i).setWhetherCollection(1);
                        mInformationListBeans.get(i).setCollection(mInformationListBeans.get(i).getCollection() + 1);

                    } else {
                        mInformationListBeans.get(i).setWhetherCollection(2);
                        mInformationListBeans.get(i).setCollection(mInformationListBeans.get(i).getCollection() - 1);

                    }

                }
            });
            ((InformationListMessage) viewHolder).fenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInformationListBeans.get(i).setShare(mInformationListBeans.get(i).getShare() + 1);
                    mSharefenxiang.sharessuccess(mInformationListBeans.get(i).getId(), i);
                }
            });

            ((InformationListMessage) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDetailstiao.detalssuccess(mInformationListBeans.get(i).getId(),mInformationListBeans.get(i).getTitle(),mInformationListBeans.get(i).getSummary(),mInformationListBeans.get(i).getSource(),mInformationListBeans.get(i).getThumbnail(),mInformationListBeans.get(i).getReleaseTime(),mInformationListBeans.get(i).getWhetherCollection(),mInformationListBeans.get(i).getCollection(),mInformationListBeans.get(i).getShare(),i);
                }
            });


        }


        if (viewHolder instanceof InformationListGuangGao) {
            if (mInformationListBeans.get(i).getInfoAdvertisingVo().getPic().equals("")) {
                ((InformationListGuangGao) viewHolder).simpleview.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550659913819&di=3fb9f9de20a37a9bf1a101983656298f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201708%2F05%2F20170805134053_HzALE.png");
                ((InformationListGuangGao) viewHolder).textwenben.setText(mInformationListBeans.get(i).getInfoAdvertisingVo().getContent());
            } else {
                mPic = mInformationListBeans.get(i).getInfoAdvertisingVo().getPic();
                mPic2 = mPic.split("\\?");
                ((InformationListGuangGao) viewHolder).simpleview.setImageURI(mPic2[0]);
                ((InformationListGuangGao) viewHolder).textwenben.setText(mInformationListBeans.get(i).getInfoAdvertisingVo().getContent());
                ((InformationListGuangGao) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGuangGaoClick.ggsuccess(mInformationListBeans.get(i).getInfoAdvertisingVo().getUrl());
                    }
                });

            }
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
        if (mWhetherAdvertising == 2) {
            return TYPE_RIGHT_IMAGE;
        } else {
            return TYPE_THREE_IMAGE;
        }

    }


    public class InformationListMessage extends RecyclerView.ViewHolder {
        private SimpleDraweeView simpleview;
        private TextView title;
        private TextView details;
        private TextView zuozhe;
        private TextView time;
        private ImageView shoucang;
        private ImageView fenxiang;
        private TextView fenxiangshu;
        private TextView shoucangshu;
        private ImageView qiandai;

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
            fenxiang = itemView.findViewById(R.id.fenxiang);
            qiandai = itemView.findViewById(R.id.qiandai);

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

    public interface GuangGaoClick {
        void ggsuccess(String url);
    }

    private GuangGaoClick mGuangGaoClick;

    public void setGuangGaoClick(GuangGaoClick guangGaoClick) {
        mGuangGaoClick = guangGaoClick;
    }


    public interface Addcollection {
        void addsuccess(int id, int whetherCollection, int i);
    }

    private Addcollection mAddcollection;

    public void setAddcollection(Addcollection addcollection) {
        mAddcollection = addcollection;
    }


    public interface Detailstiao {
        void detalssuccess(int id,String title,String neirong,String laiyuan,String tupian,long time,int shoucang,int shoucangshu,int shareshu,int i);
    }

    private Detailstiao mDetailstiao;

    public void setDetailstiao(Detailstiao detailstiao) {
        mDetailstiao = detailstiao;
    }


    public interface Sharefenxiang {

        void sharessuccess(int id, int i);
    }

    private Sharefenxiang mSharefenxiang;

    public void setSharefenxiang(Sharefenxiang sharefenxiang) {
        mSharefenxiang = sharefenxiang;
    }
}

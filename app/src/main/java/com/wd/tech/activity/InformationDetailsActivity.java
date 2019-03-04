package com.wd.tech.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wd.tech.R;
import com.wd.tech.adapter.AllInfoCommentListAdapter;
import com.wd.tech.adapter.IfmDtlPlateAdapter;
import com.wd.tech.adapter.IfmDtlRecommendedAdapter;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.AllInfoCommentListBean;
import com.wd.tech.bean.InformationDetailsBean;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.Transfer;
import com.wd.tech.bean.User;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.AddCollectionPresenter;
import com.wd.tech.presenter.AddGreatPresenter;
import com.wd.tech.presenter.AddInforCommentPresenter;
import com.wd.tech.presenter.AllInfoCommentListPresenter;
import com.wd.tech.presenter.CancelCollectionPresenter;
import com.wd.tech.presenter.CancelGreatPresenter;
import com.wd.tech.presenter.InformationDetailsPresenter;
import com.wd.tech.util.DateUtils;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 淡意衬优柔 （lk）
 * 资讯
 */
public class InformationDetailsActivity extends WDActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.zuozhe)
    TextView zuozhe;
    @BindView(R.id.simpletitle)
    SimpleDraweeView simpletitle;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.dd)
    RelativeLayout dd;
    @BindView(R.id.platelist)
    RecyclerView platelist;
    @BindView(R.id.recommendedlist)
    RecyclerView recommendedlist;
    @BindView(R.id.commentlist)
    RecyclerView commentlist;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.editcomment)
    EditText editcomment;
    @BindView(R.id.pinglun)
    ImageView pinglun;
    @BindView(R.id.great)
    ImageView great;
    @BindView(R.id.collect)
    ImageView collect;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.pinglunshu)
    TextView pinglunshu;
    @BindView(R.id.greatshu)
    TextView greatshu;
    @BindView(R.id.shareshu)
    TextView shareshu;
    @BindView(R.id.shop)
    Button shop;
    @BindView(R.id.shopgoods)
    RelativeLayout shopgoods;
    @BindView(R.id.message)
    RelativeLayout message;
    @BindView(R.id.recommend)
    RelativeLayout recommend;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Intent mIntent;
    private String mId;
    private InformationDetailsPresenter mInformationDetailsPresenter;
    private long mUserId;
    private String mSessionId;
    private IfmDtlPlateAdapter mIfmDtlPlateAdapter;
    private IfmDtlRecommendedAdapter mIfmDtlRecommendedAdapter;
    private AddCollectionPresenter mAddCollectionPresenter;
    private CancelCollectionPresenter mCancelCollectionPresenter;
    private InformationDetailsBean mResult;
    private AddGreatPresenter mAddGreatPresenter;
    private CancelGreatPresenter mCancelGreatPresenter;
    private AllInfoCommentListAdapter mAllInfoCommentListAdapter;
    private AllInfoCommentListPresenter mAllInfoCommentListPresenter;
    private String mComment;
    private AddInforCommentPresenter mAddInforCommentPresenter;
    private int page = 1;
    private Transfer mTransfer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_details;
    }

    @Override
    protected void initView() {
        shopgoods.setVisibility(View.GONE);
        ImageLoader imageLoader = ImageLoader.getInstance();//ImageLoader需要实例化
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        //收藏
        mAddCollectionPresenter = new AddCollectionPresenter(new AddCollectionCall());
        //取消收藏
        mCancelCollectionPresenter = new CancelCollectionPresenter(new CancelCollectionCall());

        //点赞
        mAddGreatPresenter = new AddGreatPresenter(new AddGreatCall());
        //取消点赞
        mCancelGreatPresenter = new CancelGreatPresenter(new CancelGreatCall());

        //查询评论
        mAllInfoCommentListPresenter = new AllInfoCommentListPresenter(new AllInfoCommentCall());

        //用户评论
        mAddInforCommentPresenter = new AddInforCommentPresenter(new AddInforCommentCall());

        mIntent = getIntent();
        mId = mIntent.getStringExtra("id");
        mTransfer = (Transfer)mIntent.getSerializableExtra("mTransfer");



        mInformationDetailsPresenter = new InformationDetailsPresenter(new InforDetailsCall());
        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            List<User> users = WDApplication.getAppContext().getUserDao().loadAll();
            mUserId = users.get(0).getUserId();
            mSessionId = users.get(0).getSessionId();
            mInformationDetailsPresenter.reqeust(mUserId, mSessionId, mId);
        } else {
            mInformationDetailsPresenter.reqeust(0L, "", mId);
        }

        platelist.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        mIfmDtlPlateAdapter = new IfmDtlPlateAdapter(this);
        platelist.setAdapter(mIfmDtlPlateAdapter);

        recommendedlist.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false));
        mIfmDtlRecommendedAdapter = new IfmDtlRecommendedAdapter(this);
        recommendedlist.setAdapter(mIfmDtlRecommendedAdapter);

        editcomment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editcomment.setFocusable(true);
                editcomment.setFocusableInTouchMode(true);
                return false;
            }
        });


        editcomment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //此处做逻辑处理
                    publish();

                    return true;
                }
                return false;
            }
        });

        refreshLayout.setRefreshFooter(new BallPulseFooter(InformationDetailsActivity.this).setSpinnerStyle(SpinnerStyle.Scale));
        commentlist.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mAllInfoCommentListAdapter = new AllInfoCommentListAdapter(this);
        commentlist.setAdapter(mAllInfoCommentListAdapter);
        requestt(page);
        /**
         * 启用刷新
         */
        refreshLayout.setEnableRefresh(true);

        /**
         * 启用加载
         */
        refreshLayout.setEnableLoadmore(true);
        requestt(page);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestt(page);
                mAllInfoCommentListAdapter.clear();
                refreshlayout.finishRefresh();

            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestt(page);
                mAllInfoCommentListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });




    }

    private void requestt(int page) {
        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            mAllInfoCommentListPresenter.reqeust(mUserId, mSessionId, mId, page, 10);
        } else {
            mAllInfoCommentListPresenter.reqeust(0L, "", mId, page, 10);
        }
    }

    private void publish() {
        mComment = editcomment.getText().toString().trim();
        if (mComment.equals("")) {
            Toast.makeText(InformationDetailsActivity.this, "请输入评论内容！", Toast.LENGTH_SHORT).show();
        } else {
            hideKeyboard(editcomment);
            if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
                mAddInforCommentPresenter.reqeust(mUserId, mSessionId, mComment, mId);


            } else {
                Toast.makeText(InformationDetailsActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void destoryData() {
        mInformationDetailsPresenter = null;
    }


    @OnClick({R.id.shop, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shop:
                goShop();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * 购买VIP
     */
    private void goShop() {
        if (WDApplication.getAppContext().getUserDao().loadAll().size() == 0) {

            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();

        } else {

            View popView = View.inflate(this, R.layout.shoppingvip, null);
            PopupWindow popWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popWindow.setTouchable(true);
            popWindow.setBackgroundDrawable(new BitmapDrawable());
            popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            initData(popView, popWindow);
            View inflate = View.inflate(this, R.layout.activity_information_details, null);
            popWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);




        }





    }

    private void initData(View popView, final PopupWindow popWindow) {
        RelativeLayout mIntegral = popView.findViewById(R.id.integral);
        RelativeLayout mVip = popView.findViewById(R.id.vip);
        ImageView mBackGb = popView.findViewById(R.id.backgb);
        mIntegral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationDetailsActivity.this,IntegralActivity.class);
                intent.putExtra("mTransfer",mTransfer);
                intent.putExtra("id",mId);
                startActivity(intent);
                popWindow.dismiss();
            }
        });
        mVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationDetailsActivity.this,OpenVipActivity.class);
                startActivity(intent);
                popWindow.dismiss();
            }
        });
        mBackGb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
    }


    /**
     * 请求详情数据
     */
    private class InforDetailsCall implements DataCall<Result<InformationDetailsBean>> {

        private String mThumbnail;
        private String[] mTu;

        @Override
        public void success(final Result<InformationDetailsBean> result) {
            if (result.getStatus().equals("0000")) {
                mResult = result.getResult();
                if (mResult.getReadPower() == 1) {
                    String content = result.getResult().getContent();
                    URLImageParser imageGetter = new URLImageParser(mContent);
                    mContent.setText(Html.fromHtml(content, imageGetter, null));
                    title.setText(result.getResult().getTitle());
                    String s = DateUtils.stampToDate(result.getResult().getReleaseTime());
                    time.setText(s);
                    zuozhe.setText(result.getResult().getSource());
                    mThumbnail = result.getResult().getThumbnail();
                    mTu = mThumbnail.split("\\?");
                    simpletitle.setImageURI(mTu[0]);
                    mIfmDtlPlateAdapter.reset(result.getResult().getPlate());
                    mIfmDtlRecommendedAdapter.reset(result.getResult().getInformationList());

                    shopgoods.setVisibility(View.GONE);


                } else {
                    shopgoods.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    recommend.setVisibility(View.GONE);

                    title.setText(result.getResult().getTitle());
                    String s = DateUtils.stampToDate(result.getResult().getReleaseTime());
                    time.setText(s);
                    zuozhe.setText(result.getResult().getSource());
                    Log.e("lk", "title" + result.getResult().getTitle());
                    Toast.makeText(InformationDetailsActivity.this, "您没有阅读权限！！！", Toast.LENGTH_SHORT).show();

                }

                if (result.getResult().getWhetherGreat() == 1) {
                    great.setImageResource(R.mipmap.common_icon_praise_s);
                } else {
                    great.setImageResource(R.mipmap.common_icon_prise_n);
                }
                if (result.getResult().getWhetherCollection() == 1) {
                    collect.setImageResource(R.mipmap.common_icon_collect_s);
                } else {
                    collect.setImageResource(R.mipmap.common_icon_collect_n);
                }
                pinglunshu.setText(result.getResult().getComment() + "");
                greatshu.setText(result.getResult().getPraise() + "");
                shareshu.setText(result.getResult().getShare() + "");
                collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
                            if (result.getResult().getWhetherCollection() == 2) {

                                mAddCollectionPresenter.reqeust(mUserId, mSessionId, result.getResult().getId());
                            } else {
                                String mid = String.valueOf(result.getResult().getId());
                                mCancelCollectionPresenter.reqeust(mUserId, mSessionId, mid);
                            }

                        } else {
                            Toast.makeText(InformationDetailsActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                great.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
                            if (result.getResult().getWhetherGreat() == 2) {
                                mAddGreatPresenter.reqeust(mUserId, mSessionId, result.getResult().getId());

                            } else {
                                String mid = String.valueOf(result.getResult().getId());
                                mCancelGreatPresenter.reqeust(mUserId, mSessionId, mid);

                            }

                        } else {
                            Toast.makeText(InformationDetailsActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    public class URLImageParser implements Html.ImageGetter {
        TextView mTextView;

        public URLImageParser(TextView textView) {
            this.mTextView = textView;
        }

        @Override
        public Drawable getDrawable(String source) {
            final URLDrawable urlDrawable = new URLDrawable();
            ImageLoader.getInstance().loadImage(source,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            urlDrawable.bitmap = loadedImage;
                            urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                            mTextView.invalidate();
                            mTextView.setText(mTextView.getText());
                        }
                    });
            return urlDrawable;
        }
    }

    public class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }

    /**
     * 关注
     */
    private class AddCollectionCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                mResult.setWhetherCollection(1);
                collect.setImageResource(R.mipmap.common_icon_collect_s);
                //mInformationDetailsPresenter.reqeust(mUserId, mSessionId, mId);
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    /**
     * 取消关注
     */
    private class CancelCollectionCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                mResult.setWhetherCollection(2);
                collect.setImageResource(R.mipmap.common_icon_collect_n);
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 点赞
     */
    private class AddGreatCall implements DataCall<Result> {

        private int mPraise;

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                mResult.setWhetherGreat(1);
                Log.e("lk", "点赞" + mResult.getPraise() + 1);
                great.setImageResource(R.mipmap.common_icon_praise_s);
                mPraise = mResult.getPraise();
                greatshu.setText(String.valueOf(mPraise+1));
                //mInformationDetailsPresenter.reqeust(mUserId, mSessionId, mId);
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    /**
     * 取消点赞
     */
    private class CancelGreatCall implements DataCall<Result> {

        private int mPraise;

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                mResult.setWhetherGreat(2);
                great.setImageResource(R.mipmap.common_icon_prise_n);
                mPraise = mResult.getPraise();
                greatshu.setText(mPraise + "");
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 隐藏软键盘
     *
     * @param view :一般为EditText
     */
    public static void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 查询评论列表
     */
    private class AllInfoCommentCall implements DataCall<Result<List<AllInfoCommentListBean>>> {

        private List<AllInfoCommentListBean> mResult;

        @Override
        public void success(Result<List<AllInfoCommentListBean>> result) {
            if (result.getStatus().equals("0000")) {
                mResult = result.getResult();
                mAllInfoCommentListAdapter.reset(mResult);

            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * 评论
     */
    private class AddInforCommentCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                editcomment.setText("");
                mAllInfoCommentListAdapter.clear();
                mAllInfoCommentListPresenter.reqeust(mUserId, mSessionId, mId, 1, 100);
            } else {
                Toast.makeText(InformationDetailsActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

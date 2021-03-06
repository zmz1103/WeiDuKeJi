package com.wd.tech.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.adapter.SearchByTitleAdapter;
import com.wd.tech.adapter.SearchHotCiAdapter;
import com.wd.tech.bean.InformationSearchByTitleBean;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.SearchByTitlePresenter;
import com.wd.tech.view.DataCall;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends WDActivity {


    @BindView(R.id.sou)
    ImageView sou;
    @BindView(R.id.search_text)
    EditText searchText;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.searchlist)
    RecyclerView searchlist;
    @BindView(R.id.resoucilist)
    RecyclerView resoucilist;
    @BindView(R.id.resou)
    RelativeLayout resou;
    @BindView(R.id.liebiao)
    RelativeLayout liebiao;
    @BindView(R.id.wu)
    RelativeLayout wu;
    private SearchByTitleAdapter mSearchByTitleAdapter;
    private SearchByTitlePresenter mSearchByTitlePresenter;
    private SearchHotCiAdapter mSearchHotCiAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        wu.setVisibility(View.GONE);
        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchText.setFocusable(true);
                searchText.setFocusableInTouchMode(true);
                return false;
            }
        });
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //此处做逻辑处理
                    sousuo();
                    return true;
                }
                return false;
            }
        });
        sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sousuo();
            }
        });



        resoucilist.setLayoutManager(new StaggeredGridLayoutManager(3,OrientationHelper.VERTICAL));
        mSearchHotCiAdapter = new SearchHotCiAdapter(this);
        resoucilist.setAdapter(mSearchHotCiAdapter);
        List<String> list = new ArrayList<>();
        list.add("区块链");
        list.add("中年危机");
        list.add("锤子科技");
        list.add("子弹短信");
        list.add("民营企业");
        list.add("特斯拉");
        list.add("支付宝");
        list.add("资本市场");
        list.add("电视剧");
        mSearchHotCiAdapter.reset(list);
        mSearchHotCiAdapter.setFuzhi(new SearchHotCiAdapter.fuzhi() {
            @Override
            public void hotci(String s) {
                searchText.setText(s);
                sousuo();
            }
        });

    }

    private void sousuo() {
        String text = searchText.getText().toString().trim();
        if (text.equals("")){
            Toast.makeText(SearchActivity.this, "请输入关键词！", Toast.LENGTH_SHORT).show();
            searchlist.setVisibility(View.GONE);
            resou.setVisibility(View.VISIBLE);
        }else {
            hideKeyboard(searchText);
            initData(text);
            resou.setVisibility(View.GONE);
            searchlist.setVisibility(View.VISIBLE);
        }
    }

    private void initData(String text) {
        Log.e("lk", "search" + text);
        searchlist.setLayoutManager(new LinearLayoutManager(SearchActivity.this, OrientationHelper.VERTICAL, false));
        mSearchByTitleAdapter = new SearchByTitleAdapter(this);
        searchlist.setAdapter(mSearchByTitleAdapter);
        mSearchByTitlePresenter = new SearchByTitlePresenter(new SearchCall());
        mSearchByTitlePresenter.reqeust(text, 1, 30);
        mSearchByTitleAdapter.setOncllk(new SearchByTitleAdapter.Oncllk() {
            @Override
            public void good(int id) {
                Intent intent = new Intent(SearchActivity.this,InformationDetailsActivity.class);
                intent.putExtra("id",id+"");
                startActivity(intent);
            }
        });
    }




    @Override
    protected void destoryData() {
        mSearchByTitlePresenter = null;
    }


    @OnClick({R.id.sou, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sou:
                break;
            case R.id.back:
                finish();
                break;
        }
    }




    private class SearchCall implements DataCall<Result<List<InformationSearchByTitleBean>>> {

        @Override
        public void success(Result<List<InformationSearchByTitleBean>> result) {
            if (result.getStatus().equals("0000")){

                List<InformationSearchByTitleBean> searchlist = result.getResult();
                if (searchlist.size()==0){
                    resou.setVisibility(View.VISIBLE);
                    wu.setVisibility(View.VISIBLE);
                }else {
                    wu.setVisibility(View.GONE);
                    mSearchByTitleAdapter.reset(searchlist);
                }

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

}

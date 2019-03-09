package com.wd.tech.activity.myactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.ModifySignPresenter;
import com.wd.tech.view.DataCall;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class PublishSingActivity extends WDActivity {

    private ModifySignPresenter modifySignPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_sing;
    }

    @BindView(R.id.activity_publish_content)
    EditText mCommunityPublishContent;
    @BindView(R.id.activity_publish_num)
    TextView mCommunityPublishNum;
    //限制的最大字数
    private int max = 100;

    @Override
    protected void initView() {

        modifySignPresenter = new ModifySignPresenter(new updateSign());

        //输入框字数限制
        InputFilter[] filters = {chineseFilter()};
        mCommunityPublishContent.addTextChangedListener(passwordListener());
    }

    private InputFilter chineseFilter() {
        return new InputFilter() {
            // unicode编码，判断是否为汉字
            String regEx = "[\\u4e00-\\u9fa5]";

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                float destCount = dest.toString().length()
                        + getChineseCount(dest.toString());
                float sourceCount = source.toString().length()
                        + getChineseCount(source.toString());
                if (destCount + sourceCount > 10) {
                    Log.e("log", "已经达到字数限制范围");
                    return "";

                } else {
                    return source;
                }
            }

            private float getChineseCount(String str) {
                float count = 0;
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(str);
                while (m.find()) {
                    for (int i = 0; i <= m.groupCount(); i++) {
                        count = count + 1;
                    }
                }
                return count;
            }
        };
    }

    private TextWatcher passwordListener() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                mCommunityPublishNum.setText(length + "/" + max);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

    }

    @Override
    protected void destoryData() {
        modifySignPresenter.unBind();
        mCommunityPublishContent = null;
        mCommunityPublishNum = null;
    }

    @OnClick({R.id.activity_txt_cancel, R.id.activity_txt_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_txt_cancel:
                finish();
                break;
            case R.id.activity_txt_publish:
                if (mCommunityPublishContent.getText().toString().length() <= 0) {
                    Toast.makeText(this, "不可发布空哦", Toast.LENGTH_SHORT).show();
                } else {
                    modifySignPresenter.reqeust((int) user.getUserId(), user.getSessionId(), mCommunityPublishContent.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }

    private class updateSign implements DataCall<Result> {
        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")) {
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

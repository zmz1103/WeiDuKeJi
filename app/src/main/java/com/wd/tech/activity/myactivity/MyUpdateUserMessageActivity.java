package com.wd.tech.activity.myactivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.DoTheTastPresenter;
import com.wd.tech.presenter.PerFectUserInfoPresenter;
import com.wd.tech.util.JavaUtils;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者: Wang on 2019/2/28 16:46
 * 寄语：加油！相信自己可以！！！
 */


public class MyUpdateUserMessageActivity extends WDActivity {

    private DoTheTastPresenter doTheTastPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_update_message;
    }

    @BindView(R.id.new_riqi)
    TextView mDateAndTimeLabel;
    private PerFectUserInfoPresenter mPerFectUserInfoPresenter;

    @Override
    protected void initView() {
        mPerFectUserInfoPresenter = new PerFectUserInfoPresenter(new perCall());
        doTheTastPresenter = new DoTheTastPresenter(new theTask());
        mDateAndTimeLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyUpdateUserMessageActivity.this, "....", Toast.LENGTH_SHORT).show();
                new DatePickerDialog(MyUpdateUserMessageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mDateAndTimeLabel.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, 2000, 1, 2).show();

            }
        });


    }

    @OnClick(R.id.saveUserData)
    void oN() {
        EditText newName = (EditText) findViewById(R.id.newname);
        EditText newBox = (EditText) findViewById(R.id.newbox);

        RadioButton man = (RadioButton) findViewById(R.id.man);
        RadioButton woman = (RadioButton) findViewById(R.id.woman);

        EditText qm = (EditText) findViewById(R.id.new_qianming_t);

        int sex = 0;
        if (man.isChecked()) {
            sex = 1;
        }
        if (woman.isChecked()) {
            sex = 2;
        }

        String name = newName.getText().toString().trim();

        String box = newBox.getText().toString().trim();

        if (JavaUtils.isEmail(box)) {
            if (name != null && name != "" && qm.getText().toString() != null && qm.getText().toString() != "") {
                mPerFectUserInfoPresenter.reqeust(user.getUserId(), user.getSessionId(), name, sex, qm.getText().toString(), mDateAndTimeLabel.getText().toString().trim(), box);
            }
        } else {
            Toast.makeText(this, "请检查邮箱格式", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void destoryData() {
        mPerFectUserInfoPresenter=null;
    }

    private class perCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(MyUpdateUserMessageActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                doTheTastPresenter.reqeust(user.getUserId(), user.getSessionId(), 1006);
            }
            finish();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class theTask implements DataCall<Result> {
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

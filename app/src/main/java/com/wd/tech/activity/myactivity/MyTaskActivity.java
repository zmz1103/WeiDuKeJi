package com.wd.tech.activity.myactivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.activity.HomeActivity;
import com.wd.tech.activity.PublishActivity;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.TaskListData;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.FindUserTaskListPresenter;
import com.wd.tech.view.DataCall;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyTaskActivity extends WDActivity {

    private FindUserTaskListPresenter findUserTaskListPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_task;
    }

    @BindView(R.id.task_btn_bd)
    Button mBtnBd;
    @BindView(R.id.task_btn_ck)
    Button mBtnCk;
    @BindView(R.id.task_btn_ft)
    Button mBtnFt;
    @BindView(R.id.task_btn_fx)
    Button mBtnFx;
    @BindView(R.id.task_btn_pl)
    Button mBtnPl;
    @BindView(R.id.task_btn_qd)
    Button mBtnQd;
    @BindView(R.id.task_btn_ws)
    Button mBtnWs;

    @Override
    protected void initView() {
        findUserTaskListPresenter = new FindUserTaskListPresenter(new findTaskList());
        if (user == null) {

        } else {
            findUserTaskListPresenter.reqeust(user.getUserId(), user.getSessionId());
        }
    }

    @Override
    protected void destoryData() {
        findUserTaskListPresenter=null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user == null) {

        } else {
            findUserTaskListPresenter.reqeust(user.getUserId(), user.getSessionId());
        }
    }


    @OnClick({R.id.task_btn_ws, R.id.task_btn_qd, R.id.task_btn_pl, R.id.task_btn_fx, R.id.task_btn_ft, R.id.task_btn_bd, R.id.task_btn_ck})
    void onDian(View v) {
        switch (v.getId()) {
            case R.id.task_btn_ws:
                // 完善
                if (mBtnWs.getText().toString().equals("去完善")) {
                    startActivity(new Intent(MyTaskActivity.this, MySettingActivity.class));
                    this.finish();
                }
                break;
            case R.id.task_btn_qd:
                // 签到
                if (mBtnQd.getText().toString().equals("去签到")) {
                    startActivity(new Intent(MyTaskActivity.this, SignActivity.class));
                    this.finish();
                }
                break;
            case R.id.task_btn_pl:
                // 评论
                if (mBtnPl.getText().toString().equals("去评论")) {
                    Intent intent = new Intent(MyTaskActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("show", 3);
                    startActivity(intent);
                    this.finish();
                }
                break;
            case R.id.task_btn_fx:
                // fx分享
                if (mBtnFx.getText().toString().equals("去分享")) {
                    Intent intent = new Intent(MyTaskActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("show", 1);
                    startActivity(intent);
                    this.finish();
                }
                break;
            case R.id.task_btn_ft:
                // ft发帖
                if (mBtnFt.getText().toString().equals("去发帖")) {
                    Intent intent = new Intent(MyTaskActivity.this, PublishActivity.class);
                    startActivity(intent);
                    this.finish();
                }
                break;
            case R.id.task_btn_bd:
                // 绑定
                if (mBtnBd.getText().toString().equals("去绑定")) {
                    Intent intent = new Intent(MyTaskActivity.this, MySettingActivity.class);
                    intent.putExtra("show", 3);
                    startActivity(intent);
                    this.finish();
                }
                break;
            case R.id.task_btn_ck:
                // 查看
                if (mBtnCk.getText().toString().equals("去查看")) {
                    Intent intent = new Intent(MyTaskActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("show", 1);
                    startActivity(intent);
                    this.finish();
                }
                break;
            default:
                break;
        }
    }

    private class findTaskList implements DataCall<Result<List<TaskListData>>> {
        @Override
        public void success(Result<List<TaskListData>> result) {
            for (int i = 0; i < result.getResult().size(); i++) {
                TaskListData taskListData = result.getResult().get(i);
                if (taskListData.getTaskType() == 1) {
                    if (taskListData.getTaskId() == 1001) {
                        if (taskListData.getStatus() == 1) {
                            mBtnQd.setBackgroundResource(R.drawable.task_qd_n);
                            mBtnQd.setTextColor(getResources().getColor(R.color.white));
                            mBtnQd.setText("已签到");
                        } else {
                            mBtnQd.setBackgroundResource(R.drawable.task_qd_s);
                            mBtnQd.setTextColor(getResources().getColor(R.color.jiancolor));
                            mBtnQd.setText("去签到");
                        }
                    } else if (taskListData.getTaskId() == 1002) {
                        if (taskListData.getStatus() == 1) {
                            mBtnPl.setBackgroundResource(R.drawable.task_qd_n);
                            mBtnPl.setTextColor(getResources().getColor(R.color.white));
                            mBtnPl.setText("已评论");
                        } else {
                            mBtnPl.setBackgroundResource(R.drawable.task_qd_s);
                            mBtnPl.setTextColor(getResources().getColor(R.color.jiancolor));
                            mBtnPl.setText("去评论");
                        }
                    } else if (taskListData.getTaskId() == 1003) {
                        if (taskListData.getStatus() == 1) {
                            mBtnFt.setBackgroundResource(R.drawable.task_qd_n);
                            mBtnFt.setTextColor(getResources().getColor(R.color.white));
                            mBtnFt.setText("已发帖");
                        } else {
                            mBtnFt.setBackgroundResource(R.drawable.task_qd_s);
                            mBtnFt.setTextColor(getResources().getColor(R.color.jiancolor));
                            mBtnFt.setText("去发帖");
                        }
                    } else if (taskListData.getTaskId() == 1004) {
                        if (taskListData.getStatus() == 1) {
                            mBtnFx.setBackgroundResource(R.drawable.task_qd_n);
                            mBtnFx.setTextColor(getResources().getColor(R.color.white));
                            mBtnFx.setText("已分享");
                        } else {
                            mBtnFx.setBackgroundResource(R.drawable.task_qd_s);
                            mBtnFx.setTextColor(getResources().getColor(R.color.jiancolor));
                            mBtnFx.setText("去分享");
                        }
                    } else if (taskListData.getTaskId() == 1005) {
                        if (taskListData.getStatus() == 1) {
                            mBtnCk.setBackgroundResource(R.drawable.task_qd_n);
                            mBtnCk.setTextColor(getResources().getColor(R.color.white));
                            mBtnCk.setText("已查看");
                        } else {
                            mBtnCk.setBackgroundResource(R.drawable.task_qd_s);
                            mBtnCk.setTextColor(getResources().getColor(R.color.jiancolor));
                            mBtnCk.setText("去查看");
                        }
                    }
                } else {
                    if (taskListData.getTaskId() == 1006) {
                        if (taskListData.getStatus() == 1) {
                            mBtnWs.setBackgroundResource(R.drawable.task_qd_n);
                            mBtnWs.setTextColor(getResources().getColor(R.color.white));
                            mBtnWs.setText("已完善");
                        } else {
                            mBtnWs.setBackgroundResource(R.drawable.task_qd_s);
                            mBtnWs.setTextColor(getResources().getColor(R.color.jiancolor));
                            mBtnWs.setText("去完善");
                        }
                    } else if (taskListData.getTaskId() == 1007) {
                        if (taskListData.getStatus() == 1) {
                            mBtnBd.setBackgroundResource(R.drawable.task_qd_n);
                            mBtnBd.setTextColor(getResources().getColor(R.color.white));
                            mBtnBd.setText("已绑定");
                        } else if (taskListData.getStatus() == 2) {
                            mBtnBd.setBackgroundResource(R.drawable.task_qd_s);
                            mBtnBd.setTextColor(getResources().getColor(R.color.jiancolor));
                            mBtnBd.setText("去绑定");
                        }
                    }
                }


            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

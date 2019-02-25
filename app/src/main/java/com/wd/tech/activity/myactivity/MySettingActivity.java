package com.wd.tech.activity.myactivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.bean.GetUserBean;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.GetUserBeanPresenter;
import com.wd.tech.presenter.PerFectUserInfoPresenter;
import com.wd.tech.util.FileUtils;
import com.wd.tech.util.JavaUtils;
import com.wd.tech.util.ToDate;
import com.wd.tech.view.DataCall;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class MySettingActivity extends WDActivity {

    private LinearLayout mPhoto;
    private LinearLayout mCancel;
    private LinearLayout mCamera;
    private Dialog mDialog;
    private GetUserBeanPresenter mGetUserBeanPresenter;

    @BindView(R.id.my_setting_icon)
    SimpleDraweeView my_setting_icon;
    @BindView(R.id.my_setting_u_sex)
    TextView my_setting_u_sex;
    @BindView(R.id.my_setting_u_nicheng)
    TextView my_setting_u_nicheng;
    @BindView(R.id.my_sr_t)
    TextView my_sr_t;
    @BindView(R.id.my_dh_t)
    TextView my_dh_t;
    @BindView(R.id.my_setting_email)
    TextView my_setting_email;
    @BindView(R.id.my_jf_t)
    TextView my_jf_t;
    @BindView(R.id.my_vip_t)
    TextView my_vip_t;
    @BindView(R.id.bind_faceId)
    TextView bind_faceId;
     TextView dateAndTimeLabel;
    private PerFectUserInfoPresenter perFectUserInfoPresenter;
    private View view1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_setting;
    }

    //获取日期格式器对象
    DateFormat fmtDateAndTime = DateFormat.getDateInstance(1);

    //获取一个日历对象
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    //当点击DatePickerDialog控件的设置按钮时，调用该方法
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //将页面TextView的显示更新为最新时间
            updateLabel();
        }
    };
    @Override
    protected void initView() {
        mGetUserBeanPresenter = new GetUserBeanPresenter(new getUserById());
        perFectUserInfoPresenter = new PerFectUserInfoPresenter(new perCall());
        if (user == null) {
            Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
        } else {
            mGetUserBeanPresenter.reqeust(user.getUserId(), user.getSessionId());
        }

        view1 = View.inflate(this, R.layout.activity_mine_dialog, null);

        dateAndTimeLabel  = view1.findViewById(R.id.new_riqi);

    }

    //更新页面TextView的方法
    private void updateLabel() {
        dateAndTimeLabel.setText(fmtDateAndTime.format(dateAndTime.getTime()));
    }

    @Override
    protected void destoryData() {
        mGetUserBeanPresenter.unBind();
        perFectUserInfoPresenter.unBind();
    }

    @OnClick({R.id.my_back_setting, R.id.my_tc_t, R.id.my_setting_icon, R.id.go_up_sign, R.id.bind_faceId, R.id.my_sr_t, R.id.my_setting_email})
    void dianJi(View view) {
        switch (view.getId()) {
            case R.id.my_back_setting:
                // 关闭这个页面
                finish();
                break;
            case R.id.my_tc_t:
                // 退出登录 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder builder = new AlertDialog.Builder(MySettingActivity.this);
                //    设置Title的内容
                builder.setTitle("温馨提示");
                //    设置Content来显示一个信息
                builder.setMessage("确定退出登录吗？");
                //    设置一个PositiveButton
                builder.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDao.deleteAll();
                        finish();
                    }
                });
                //    设置一个NegativeButton
                builder.setNegativeButton("暂不退出", null);
                //    显示出该对话框
                builder.show();
                break;
            case R.id.my_setting_icon:
                View inflate = View.inflate(MySettingActivity.this, R.layout.community_popwindow, null);

                mDialog = new Dialog(MySettingActivity.this, R.style.BottomDialog);
                mDialog.setContentView(inflate);
                mDialog.setCanceledOnTouchOutside(true);
                ViewGroup.LayoutParams layoutParamsthreefilmreview = inflate.getLayoutParams();
                layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
                inflate.setLayoutParams(layoutParamsthreefilmreview);
                mDialog.getWindow().setGravity(Gravity.BOTTOM);
                mDialog.show();

                mCamera = inflate.findViewById(R.id.open_camera);

                mPhoto = inflate.findViewById(R.id.open_album);

                mCancel = inflate.findViewById(R.id.open_cancel);
                initPop(inflate);
                break;
            case R.id.go_up_sign:
                //去发布签名页面
                break;
            case R.id.bind_faceId:
                //绑定Face Id
                if (bind_faceId.getText().toString().equals("点击绑定")) {

                }
                break;
            case R.id.my_sr_t:
                if (my_sr_t.getText().toString().equals("未设置生日")) {
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);


                    mBuilder.setTitle("完善信息");
                    mBuilder.setView(view1);
                    mBuilder.setPositiveButton("确定完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText newName = view1.findViewById(R.id.newname);
                            EditText newBox = view1.findViewById(R.id.newbox);

                            RadioButton man = view1.findViewById(R.id.man);
                            RadioButton woman = view1.findViewById(R.id.woman);

                            EditText qm = view1.findViewById(R.id.new_qianming_t);
                            dateAndTimeLabel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
                                    new DatePickerDialog(MySettingActivity.this,
                                            d,
                                            dateAndTime.get(Calendar.YEAR),
                                            dateAndTime.get(Calendar.MONTH),
                                            dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
                                    Toast.makeText(MySettingActivity.this, "aaa", Toast.LENGTH_SHORT).show();
                                }
                            });
                            int sex = 0;
                            if (man.isChecked()) {
                                sex = 1;
                            }
                            if (woman.isChecked()) {
                                sex = 2;
                            }

                            String name = newName.getText().toString().trim();

                            String box = newBox.getText().toString().trim();
                            String s1 = dateAndTimeLabel.getText().toString().replaceAll("年", "-");
                            String s2 = s1.replaceAll("月", "-");
                            String rs = s2.replace("日","  ");
                            if (JavaUtils.isEmail(box)) {
                                if (name != null && name != "" && qm.getText().toString() != null && qm.getText().toString() != "") {
                                    perFectUserInfoPresenter.reqeust(user.getUserId(), user.getSessionId(), name, sex, qm.getText().toString(), rs, box);
                                }
                            }

                        }
                    });
                    mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    mBuilder.show();
                }
                break;
            case R.id.my_setting_email:
                if (my_setting_email.getText().toString().equals("未设置邮箱")) {
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

                    mBuilder.setTitle("完善信息");
                    mBuilder.setView(view1);
                    mBuilder.setPositiveButton("确定完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText newName = view1.findViewById(R.id.newname);
                            EditText newBox = view1.findViewById(R.id.newbox);
                            EditText qm = view1.findViewById(R.id.new_qianming_t);

                            RadioButton man = view1.findViewById(R.id.man);
                            RadioButton woman = view1.findViewById(R.id.woman);

                            dateAndTimeLabel .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //生成一个DatePickerDialog对象，并显示。显示的DatePickerDialog控件可以选择年月日，并设置
                                    new DatePickerDialog(MySettingActivity.this,
                                            d,
                                            dateAndTime.get(Calendar.YEAR),
                                            dateAndTime.get(Calendar.MONTH),
                                            dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
                                }
                            });
                            int sex = 0;
                            if (man.isChecked()) {
                                sex = 1;
                            }
                            if (woman.isChecked()) {
                                sex = 2;
                            }
                            String s1 = dateAndTimeLabel .getText().toString().replaceAll("年", "-");
                            String s2 = s1.replaceAll("月", "-");
                            String rs = s2.replace("日","  ");
                            String name = newName.getText().toString().trim();
                            String box = newBox.getText().toString().trim();
                            if (JavaUtils.isEmail(box)) {
                                if (name != null && name != "" && qm.getText().toString() != null && qm.getText().toString() != "") {
                                    perFectUserInfoPresenter.reqeust(user.getUserId(), user.getSessionId(), name, sex, qm.getText().toString(),rs , box);
                                }
                            }
                        }
                    });
                    mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    mBuilder.show();
                }
                break;
            default:
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:


                mDialog.cancel();
                if (data == null || data.equals("")) {
                    return;
                }
                File imageFile = FileUtils.getImageFile();
                String path = imageFile.getPath();


                break;
            case 1:
                mDialog.cancel();
                if (data == null || data.equals("")) {
                    return;
                }
                Uri data1 = data.getData();

                String[] proj = {MediaStore.Images.Media.DATA};

                Cursor actualimagecursor = managedQuery(data1, proj, null, null, null);

                int actual_image_column_index = actualimagecursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                String img_path = actualimagecursor
                        .getString(actual_image_column_index);

                // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    actualimagecursor.close();
                }


                break;
            default:
                break;
        }
    }

    private void initPop(View popView) {

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消弹框
                mDialog.cancel();
            }
        });
        //相册
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MySettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(MySettingActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 122);
                } else {
                    Intent openAlbumIntent = new Intent(
                            Intent.ACTION_PICK);
                    openAlbumIntent.setType("image/*");
                    //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                    startActivityForResult(openAlbumIntent, 1);
                }
            }
        });
        //相机
        mCamera.setOnClickListener(new View.OnClickListener() {
            private Uri tempUri;

            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MySettingActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MySettingActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

                } else {
                    Intent openCameraIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);

                    tempUri = Uri.parse(FileUtils.getDir("/image/bimap") + "1.jpg");
                    Log.e("zmz", "=====" + tempUri);

                    //启动相机程序
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                    startActivityForResult(intent, 0);
                }

            }
        });
    }

    private class getUserById implements DataCall<Result<GetUserBean>> {
        @Override
        public void success(Result<GetUserBean> result) {
            Toast.makeText(MySettingActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {
                my_dh_t.setText(result.getResult().getPhone());
                my_jf_t.setText(result.getResult().getIntegral() + "");
                if (result.getResult().getEmail() == null || result.getResult().getEmail() == "") {
                    my_setting_email.setText("未设置邮箱");
                } else {
                    my_setting_email.setText(result.getResult().getEmail());
                }
                if (result.getResult().getBirthday() == 0) {
                    my_sr_t.setText("未设置生日");
                }else{
                    my_sr_t.setText(ToDate.timedate(result.getResult().getBirthday()));
                }
                my_setting_icon.setImageURI(result.getResult().getHeadPic());
                my_setting_u_nicheng.setText(result.getResult().getNickName());
                if (result.getResult().getSex() == 1) {
                    my_setting_u_sex.setText("男");
                } else {
                    my_setting_u_sex.setText("女");
                }
                if (result.getResult().getWhetherVip() == 1) {
                    my_vip_t.setText("是");
                } else {
                    my_vip_t.setText("否");
                }
                if (result.getResult().getWhetherFaceId() == 1) {
                    bind_faceId.setText("已绑定");
                } else {
                    bind_faceId.setTextColor(Color.RED);
                    bind_faceId.setText("点击绑定");
                }

            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class perCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Toast.makeText(MySettingActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

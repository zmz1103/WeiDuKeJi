package com.wd.tech.activity.myactivity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wd.tech.R;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.util.FileUtils;

import java.io.File;

import butterknife.OnClick;

public class MySettingActivity extends WDActivity {

    private View popView;
    private RelativeLayout photo;
    private RelativeLayout cancel;
    private RelativeLayout camera;
    private Dialog a;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_setting;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }

    @OnClick({R.id.my_back_setting, R.id.my_tc_t, R.id.my_setting_icon, R.id.my_setting_u_nicheng,
            R.id.my_setting_u_sex, R.id.go_up_sign})
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
                View inflate = View.inflate(MySettingActivity.this, R.layout.my_icon_update, null);

                a = new Dialog(MySettingActivity.this, R.style.BottomDialog);
                a.setContentView(inflate);
                a.setCanceledOnTouchOutside(true);
                ViewGroup.LayoutParams layoutParamsthreefilmreview = inflate.getLayoutParams();
                layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
                inflate.setLayoutParams(layoutParamsthreefilmreview);
                a.getWindow().setGravity(Gravity.BOTTOM);
                a.show();

                //  更换头像
//                popView = View.inflate(MySettingActivity.this, R.layout.my_icon_update, null);
//                final View inflate = LayoutInflater.from(MySettingActivity.this).inflate(R.layout.activity_my_setting, null);
//                popWindow = new Dialog(MySettingActivity.this, R.style.BottomDialog);
//                popWindow.setContentView(inflate);
//                popWindow.setCanceledOnTouchOutside(true);
//                ViewGroup.LayoutParams layoutParamsthreefilmreview = inflate.getLayoutParams();
//                layoutParamsthreefilmreview.width = getResources().getDisplayMetrics().widthPixels;
//                inflate.setLayoutParams(layoutParamsthreefilmreview);
//                popWindow.getWindow().setGravity(Gravity.BOTTOM);
//                popWindow.show();
                camera = inflate.findViewById(R.id.open_camera);

                photo = inflate.findViewById(R.id.open_album);

                cancel = inflate.findViewById(R.id.open_cancel);
                initPop(inflate);
                break;
            case R.id.my_setting_u_nicheng:
                // 更换昵称
                break;
            case R.id.my_setting_u_sex:
                // 更换性别
                break;
            case R.id.go_up_sign:
                //去发布签名页面
                break;
            case R.id.bind_faceId:
                //绑定Face Id
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


                a.cancel();
                if (data == null || data.equals("")) {
                    return;
                }
                File imageFile = FileUtils.getImageFile();
                String path = imageFile.getPath();


                break;
            case 1:
                a.cancel();
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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消弹框
                a.cancel();
            }
        });
        //相册
        photo.setOnClickListener(new View.OnClickListener() {
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
        camera.setOnClickListener(new View.OnClickListener() {
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
}

package com.wd.tech.activity.myactivity;

import android.Manifest;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
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
import com.wd.tech.activity.HomeActivity;
import com.wd.tech.activity.MainActivity;
import com.wd.tech.activity.RegisterActivity;
import com.wd.tech.activity.WDActivity;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.GetUserBean;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.face.PeopleActivity;
import com.wd.tech.presenter.GetUserBeanPresenter;
import com.wd.tech.presenter.ModifyHeadPicPresenter;
import com.wd.tech.presenter.PerFectUserInfoPresenter;
import com.wd.tech.presenter.UntiedFaceIdPresenter;
import com.wd.tech.util.JavaUtils;
import com.wd.tech.util.StringUtils;
import com.wd.tech.util.ToDate;
import com.wd.tech.view.DataCall;

import java.io.File;
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
    private static final int PERMISSIONS_REQUEST_OPEN_ALBUM = 1;
    private static int output_X = 100;
    private static int output_Y = 100;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    @BindView(R.id.my_setting_icon)
    SimpleDraweeView mSettingIcon;
    @BindView(R.id.my_setting_u_sex)
    TextView mSettingSex;
    @BindView(R.id.my_setting_u_nicheng)
    TextView mSettingNickName;
    @BindView(R.id.my_sr_t)
    TextView mBirthday;
    @BindView(R.id.my_dh_t)
    TextView mPhoneTextview;
    @BindView(R.id.my_setting_email)
    TextView MSettingEmail;
    @BindView(R.id.my_jf_t)
    TextView mJfTextView;
    @BindView(R.id.my_vip_t)
    TextView MWhereVip;
    @BindView(R.id.bind_faceId)
    TextView mBindFaceId;
    TextView mDateAndTimeLabel;
    private PerFectUserInfoPresenter mPerFectUserInfoPresenter;
    private View mView1;
    private ModifyHeadPicPresenter mModifyHeadPicPresenter;
    private final String TAG = this.getClass().toString();

    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_IMAGE_OP = 2;
    private static final int REQUEST_CODE_OP = 3;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_setting;
    }


    @Override
    protected void initView() {
        mModifyHeadPicPresenter = new ModifyHeadPicPresenter(new uImage());
        mGetUserBeanPresenter = new GetUserBeanPresenter(new getUserById());
        mPerFectUserInfoPresenter = new PerFectUserInfoPresenter(new perCall());
        if (user == null) {
            Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
        } else {
            mGetUserBeanPresenter.reqeust(user.getUserId(), user.getSessionId());
        }


    }

    @Override
    protected void destoryData() {
        mGetUserBeanPresenter.unBind();
        mPerFectUserInfoPresenter.unBind();
        mModifyHeadPicPresenter.unBind();
    }

    @OnClick({R.id.my_back_setting, R.id.my_tc_t, R.id.my_setting_icon, R.id.go_up_sign, R.id.bind_faceId, R.id.u_mpwd})
    void dianJi(View view) {
        switch (view.getId()) {
            case R.id.my_back_setting:
                // 关闭这个页面
                finish();
                break;
            case R.id.my_tc_t:
                // 退出登录 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                final AlertDialog.Builder builder = new AlertDialog.Builder(MySettingActivity.this);
                //    设置Title的内容
                builder.setTitle("温馨提示");
                //    设置Content来显示一个信息
                builder.setMessage("确定退出登录吗？");
                //    设置一个PositiveButton
                builder.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WDApplication.getAppContext().getUserDao().deleteAll();
                        Intent intent = new Intent(MySettingActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("show", 1);
                        startActivity(intent);
                        finish();
                    }
                });
                //设置一个NegativeButton
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

                mPhoto = inflate.findViewById(R.id.open_picture);

                mCancel = inflate.findViewById(R.id.btn_cancel);
                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消弹框
                        mDialog.dismiss();
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
                            choseHeadImageFromGallery();

                        }
                    }
                });
                //相机
                mCamera.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MySettingActivity.this, "xj", Toast.LENGTH_SHORT).show();
                        if (ContextCompat.checkSelfPermission(MySettingActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MySettingActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
                            Toast.makeText(MySettingActivity.this, "if", Toast.LENGTH_SHORT).show();

                        } else {
                            choseHeadImageFromCameraCapture();
                        }
                    }
                });
                break;
            case R.id.go_up_sign:
                //去发布签名页面
                startActivity(new Intent(MySettingActivity.this, PublishSingActivity.class));
                finish();
                break;
            case R.id.bind_faceId:
                //绑定Face Id
                if (mBindFaceId.getText().toString().equals("点击绑定")) {
                    new AlertDialog.Builder(this)
                            .setTitle("请选择注册方式")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setItems(new String[]{"打开图片", "拍摄照片"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 1:
                                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                            ContentValues values = new ContentValues(1);
                                            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                            ((WDApplication)(MySettingActivity.this.getApplicationContext())).setCaptureImage(uri);
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                            startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
                                            break;
                                        case 0:
                                            Intent getImageByalbum = new Intent(Intent.ACTION_GET_CONTENT);
                                            getImageByalbum.addCategory(Intent.CATEGORY_OPENABLE);
                                            getImageByalbum.setType("image/jpeg");
                                            startActivityForResult(getImageByalbum, REQUEST_CODE_IMAGE_OP);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            })
                            .show();
                }else if(mBindFaceId.getText().toString().equals("已绑定")){
                    UntiedFaceIdPresenter untiedFaceIdPresenter = new UntiedFaceIdPresenter(new und());
                    untiedFaceIdPresenter.reqeust(user.getUserId(),user.getSessionId());
                }
                break;
            case R.id.u_mpwd:
                startActivity(new Intent(MySettingActivity.this, UpdatePwdActivity.class));
                break;

            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_OP && resultCode == RESULT_OK) {
            Uri mPath = data.getData();
            String file = getPath(mPath);
            Bitmap bmp = WDApplication.decodeImage(file);
            if (bmp == null || bmp.getWidth() <= 0 || bmp.getHeight() <= 0 ) {
                Log.e(TAG, "error");
            } else {
                Log.i(TAG, "bmp [" + bmp.getWidth() + "," + bmp.getHeight());
            }
            startRegister(bmp, file);
        } else if (requestCode == REQUEST_CODE_OP) {
            Log.i(TAG, "RESULT =" + resultCode);
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            String path = bundle.getString("imagePath");
            Log.i(TAG, "path="+path);
        } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA && resultCode == RESULT_OK) {
            Uri mPath = ((WDApplication)(MySettingActivity.this.getApplicationContext())).getCaptureImage();
            String file = getPath(mPath);
            Bitmap bmp = WDApplication.decodeImage(file);
            startRegister(bmp, file);
        }


        //   ----
        mDialog.dismiss();
        mDialog.cancel();
        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);

                    cropRawPhoto(Uri.fromFile(tempFile));

                } else {
                    Toast.makeText(MySettingActivity.this, "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(data.getData());
                break;
            case CODE_RESULT_REQUEST:
                if (data != null) {
                    setImageToHeadView(data);
                }
                break;
            default:
                break;
        }
    }

    private class getUserById implements DataCall<Result<GetUserBean>> {
        @Override
        public void success(Result<GetUserBean> result) {

            if (result.getStatus().equals("0000")) {
                mPhoneTextview.setText(result.getResult().getPhone());
                mJfTextView.setText(result.getResult().getIntegral() + "");
                if (result.getResult().getEmail() == null || result.getResult().getEmail() == "") {
                    MSettingEmail.setText("未设置邮箱");
                } else {
                    MSettingEmail.setText(result.getResult().getEmail());
                }
                if (result.getResult().getBirthday() == 0) {
                    mBirthday.setText("未设置生日");
                } else {
                    mBirthday.setText(ToDate.timedate(result.getResult().getBirthday()));
                }
                mSettingIcon.setImageURI(result.getResult().getHeadPic());
                mSettingNickName.setText(result.getResult().getNickName());
                if (result.getResult().getSex() == 1) {
                    mSettingSex.setText("男");
                } else {
                    mSettingSex.setText("女");
                }
                if (result.getResult().getWhetherVip() == 1) {
                    MWhereVip.setText("是");
                } else {
                    MWhereVip.setText("否");
                }
                if (result.getResult().getWhetherFaceId() == 1) {
                    mBindFaceId.setText("已绑定");
                } else {
                    mBindFaceId.setTextColor(Color.RED);
                    mBindFaceId.setText("点击绑定");
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

            if (result.getStatus().equals("0000")) {
                onResume();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user == null) {
            Toast.makeText(this, "0", Toast.LENGTH_SHORT).show();
        } else {
            mGetUserBeanPresenter.reqeust(user.getUserId(), user.getSessionId());
        }
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型


        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_PICK);

        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));


        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }


    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        String path = StringUtils.getRealPathFromUri(MySettingActivity.this, uri);
        // 改头像
        mModifyHeadPicPresenter.reqeust(user.getUserId(), user.getSessionId(), path);
        Bitmap map = null;

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
        //  intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(LOGO_ZOOM_FILE_PATH));
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }


    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");

            mSettingIcon.setImageBitmap(photo);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    private class uImage implements DataCall<Result<String>> {
        @Override
        public void success(Result<String> result) {
            Toast.makeText(MySettingActivity.this, "" + result.getMessage() + result.getResult(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {

        }
    }


    private String getPath(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(this, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] {
                            split[1]
                    };

                    return getDataColumn(this, contentUri, selection, selectionArgs);
                }
            }
        }
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = this.getContentResolver().query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        String end = img_path.substring(img_path.length() - 4);
        if (0 != end.compareToIgnoreCase(".jpg") && 0 != end.compareToIgnoreCase(".png")) {
            return null;
        }
        return img_path;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param mBitmap
     */
    private void startRegister(Bitmap mBitmap, String file) {
        Intent it = new Intent(MySettingActivity.this, PeopleActivity .class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", file);
        it.putExtras(bundle);
        startActivityForResult(it, REQUEST_CODE_OP);
    }

    private class und implements DataCall<Result> {
        @Override
        public void success(Result result) {
            onResume();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

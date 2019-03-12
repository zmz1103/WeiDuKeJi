package com.wd.tech.activity;


import android.app.Application;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.wd.tech.R;
import com.wd.tech.activity.myactivity.MySettingActivity;
import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.FindConversationList;
import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.UserDao;
import com.wd.tech.exception.ApiException;
import com.wd.tech.face.DetecterActivity;
import com.wd.tech.face.PeopleActivity;
import com.wd.tech.presenter.LoginPresenter;
import com.wd.tech.util.DaoUtils;
import com.wd.tech.util.RegUtils;
import com.wd.tech.util.RsaCoder;
import com.wd.tech.util.WeiXinUtil;
import com.wd.tech.view.DataCall;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;


public class MainActivity extends WDActivity implements CustomAdapt {

    private LoginPresenter mLoginPresenter;

    private static final int REQUEST_CODE_OP = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            finish();
            return;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            finish();
            return;
        }
    }

    @Override
    protected int getLayoutId() {
        if (WDApplication.getAppContext().getUserDao().loadAll().size() > 0) {
            finish();
            return R.layout.activity_main;
        }
        return R.layout.activity_main;
    }

    @BindView(R.id.et_pwd_number)
    EditText mEpwd;
    @BindView(R.id.et_tel_number)
    EditText mEtel;
    boolean isHide;

    @Override
    protected void initView() {
        // 设置密码不可见
        mEpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        isHide = true;
        mLoginPresenter = new LoginPresenter(new loginCall());
    }
    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_IMAGE_OP = 2;
    @OnClick({R.id.eye, R.id.toReagist, R.id.weixinLogin, R.id.faseIdLogin, R.id.login_btn})
    void clivk(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                // 登录
                String s = mEtel.getText().toString();
                String s1 = mEpwd.getText().toString();
                boolean mobile = RegUtils.isMobile(s);
                boolean b = RegUtils.rexCheckPassword(s1);
                if (mobile) {

                    if (b && mEpwd.length() >= 8) {
                        try {
                            mLoginPresenter.reqeust(mEtel.getText().toString(), RsaCoder.encryptByPublicKey(mEpwd.getText().toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "密码必须大于8位", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "手机号不正确", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.eye:
                if (isHide) {
                    isHide = false;
                    //设置密码可见
                    mEpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    isHide = true;
                    // 不可见
                    mEpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.toReagist:
                // 去注册页面
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.weixinLogin:
                // 微信登录
                if (!WeiXinUtil.success(this)) {
                    Toast.makeText(this, "请先安装应用", Toast.LENGTH_SHORT).show();
                } else {
                    //  验证
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    WeiXinUtil.reg(MainActivity.this).sendReq(req);
                }
                break;
            case R.id.faseIdLogin:
                // 人脸登录
                if (((WDApplication) getApplicationContext()).mFaceDB.mRegister.isEmpty()) {
                    Toast.makeText(this, "没有注册人脸，请先注册！", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(this)
                            .setTitle("请选择注册方式")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setItems(new String[]{"打开图片", "拍摄照片"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 1:
                                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                            ContentValues values = new ContentValues(1);
                                            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                            ((WDApplication) (MainActivity.this.getApplicationContext())).setCaptureImage(uri);
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
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("请选择相机")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setItems(new String[]{"后置相机", "前置相机"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startDetector(which);
                                }
                            })
                            .show();
                }

                break;
            default:
                break;
        }
    }

    private void startDetector(int camera) {
        Intent it = new Intent(MainActivity.this, DetecterActivity.class);
        it.putExtra("Camera", camera);
        startActivity(it );
        finish();
    }

    @Override
    protected void destoryData() {
        mLoginPresenter=null;
    }

    private final String TAG = this.getClass().toString();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_OP && resultCode == RESULT_OK) {
            Uri mPath = data.getData();
            String file = getPath(mPath);
            Bitmap bmp = WDApplication.decodeImage(file);
            if (bmp == null || bmp.getWidth() <= 0 || bmp.getHeight() <= 0) {
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
            Log.i(TAG, "path=" + path);
        } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA && resultCode == RESULT_OK) {
            Uri mPath = ((WDApplication) (MainActivity.this.getApplicationContext())).getCaptureImage();
            String file = getPath(mPath);
            Bitmap bmp = WDApplication.decodeImage(file);
            startRegister(bmp, file);
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
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(this, contentUri, selection, selectionArgs);
                }
            }
        }
        String[] proj = {MediaStore.Images.Media.DATA};
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
    private void startRegister(Bitmap mBitmap, String file) {
        Intent it = new Intent(MainActivity.this, PeopleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", file);
        it.putExtras(bundle);
        startActivityForResult(it, REQUEST_CODE_OP);
    }
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
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }

    private class loginCall implements DataCall<Result<User>> {
        @Override
        public void success(Result<User> result) {
            Toast.makeText(MainActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
            if (result.getStatus().equals("0000")) {

                WDApplication.getAppContext().getUserDao();
                User user = new User();
                user = result.getResult();
                user.setSole(1);
                WDApplication.getAppContext().getUserDao().insertOrReplace(user);

//                new XToas

                FindConversationList conversation = new FindConversationList();
                conversation.setUserName(result.getResult().getUserName().toLowerCase());
                conversation.setHeadPic(result.getResult().getHeadPic());
                conversation.setNickName(result.getResult().getNickName());
                conversation.setUserId((int)result.getResult().getUserId());
                DaoUtils.getInstance().getConversationDao().insertOrReplaceInTx(conversation);

                EMClient.getInstance().login(result.getResult().getUserName(), result.getResult().getPwd(), new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        finish();
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                        finish();
                    }
                });


                finish();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}

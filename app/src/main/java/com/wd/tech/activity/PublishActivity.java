package com.wd.tech.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.adapter.PictureAdapter;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.CommunityPublishPresenter;
import com.wd.tech.presenter.DoTheTastPresenter;
import com.wd.tech.util.FileUtils;
import com.wd.tech.util.StringUtils;
import com.wd.tech.view.DataCall;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.internal.CustomAdapt;


/**
 * 王思敏
 * 2019/2/20
 * 发布帖子页
 */
public class PublishActivity extends WDActivity implements View.OnClickListener,CustomAdapt {

    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.txt_publish)
    TextView txtPublish;
    @BindView(R.id.community_publish_content)
    EditText communityPublishContent;
    @BindView(R.id.community_publish_num)
    TextView communityPublishNum;
    @BindView(R.id.community_image)
    RecyclerView communityImage;
    private CommunityPublishPresenter mCommunityPublishPresenter;
    private PictureAdapter mPictureAdapter;
    private int max= 300;//限制的最大字数
    private LinearLayout mBtnCancel;
    private LinearLayout mOpenPicture;
    private LinearLayout mOpenCamera;
    private PopupWindow popupWindow;
    String picturePath;
    private static final int TAKE_PICTURE = 1;
    private DoTheTastPresenter mDoTheTastPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mCommunityPublishPresenter = new CommunityPublishPresenter(new CommunityPublish());
        mDoTheTastPresenter = new DoTheTastPresenter(new doTheTask());
        mPictureAdapter = new PictureAdapter(this);
        mPictureAdapter.add(R.drawable.xc);
        communityImage.setLayoutManager(new GridLayoutManager(this, 3));
        communityImage.setAdapter(mPictureAdapter);
        mPictureAdapter.setOnImageClickListener(new PictureAdapter.OnImageClickListener() {
            @Override
            public void onImageClick() {
                View view1 = View.inflate(PublishActivity.this, R.layout.community_popwindow, null);
                popupWindow = new PopupWindow(view1);
                //设置充满父窗体
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
                //点击外部关闭弹框
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                popupWindow.showAtLocation(view1, Gravity.BOTTOM, 0, 0);
                mOpenCamera = view1.findViewById(R.id.open_camera);
                mOpenPicture = view1.findViewById(R.id.open_picture);
                mBtnCancel = view1.findViewById(R.id.btn_cancel);
                //相机
                mOpenCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String path = Environment.getExternalStorageDirectory().toString() + "/elife/img";
                        File path1 = new File(path);
                        if (!path1.exists()) {
                            path1.mkdirs();
                        }
                        File file = new File(path1, System.currentTimeMillis() + ".jpg");
                        Uri mOutPutFileUri = Uri.fromFile(file);
                        picturePath = file.getAbsolutePath();
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
                        startActivityForResult(intent, TAKE_PICTURE);
                    }
                });
                //相册
                mOpenPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        PublishActivity.this.startActivityForResult(intent, WDActivity.PHOTO);
                    }
                });
                //取消popupWindow的点击事件
                mBtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        //输入框字数限制
        InputFilter[] filters={chineseFilter()};
        communityPublishContent.addTextChangedListener(passwordListener());
    }

    @Override
    protected void destoryData() {


    }

    @Override
    public void onClick(View view) {

    }



    class CommunityPublish implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(PublishActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                mDoTheTastPresenter.reqeust(user.getUserId(),user.getSessionId(),1003);
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(PublishActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
        }
    }
    //做任务评论
    private class doTheTask implements DataCall<Result> {
        @Override
        public void success(Result result) {
            Log.i("success",result.getMessage());
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PICTURE:
                //相机图片添加适配器
                if (resultCode == RESULT_OK){
                    if (!StringUtils.isEmpty(picturePath)){
                        mPictureAdapter.add(picturePath);
                        mPictureAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case PHOTO:
                //相册图片添加适配器
                if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
                    String filePath = getFilePath(null, requestCode, data);
                    if (!StringUtils.isEmpty(filePath)) {
                        mPictureAdapter.add(filePath);
                        mPictureAdapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }

    }

    @OnClick({R.id.txt_cancel, R.id.txt_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                finish();
                break;
            case R.id.txt_publish:
                mCommunityPublishPresenter.reqeust((int)user.getUserId(), user.getSessionId(), communityPublishContent.getText().toString().trim(), mPictureAdapter.getList());
                break;
            default:
                break;
        }
    }


    /**
     * 输入框
     * @return
     */
    private InputFilter chineseFilter() {
        return new InputFilter() {
            String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字
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
                        count =count + 1;//
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
                communityPublishNum.setText(length + "/" + max);
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
    protected void onDestroy() {
        super.onDestroy();
        mCommunityPublishPresenter=null;
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 720;
    }
}

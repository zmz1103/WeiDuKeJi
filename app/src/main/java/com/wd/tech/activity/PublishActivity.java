package com.wd.tech.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.bean.Result;
import com.wd.tech.exception.ApiException;
import com.wd.tech.presenter.CommunityPublishPresenter;
import com.wd.tech.view.DataCall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
public class PublishActivity extends WDActivity implements CustomAdapt, AdapterView.OnItemClickListener {

    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.txt_publish)
    TextView txtPublish;
    @BindView(R.id.community_publish_content)
    EditText communityPublishContent;
    @BindView(R.id.community_publish_num)
    TextView communityPublishNum;
    @BindView(R.id.Scrollgridview)
    GridView gridview;
    private CommunityPublishPresenter mCommunityPublishPresenter;
    private GridAdapter adapter;

    private int max= 300;//限制的最大字数
    public static List<Bitmap> bmp = new ArrayList<Bitmap>();
    public static List<String> drr = new ArrayList<String>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initData();
        gridviewInit();
    }
    public void gridviewInit() {

        adapter = new GridAdapter(this, bmp);
        adapter.setSelectedPosition(0);
        gridview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        gridview.setOnItemClickListener(this);

    }

    private void initData() {
        //输入框字数限制
        InputFilter[] filters={chineseFilter()};
        communityPublishContent.addTextChangedListener(passwordListener());

        mCommunityPublishPresenter = new CommunityPublishPresenter(new CommunityPublish());



    }

    @Override
    protected void destoryData() {


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).

                hideSoftInputFromWindow(PublishActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (i == bmp.size()) {
            String sdcardState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                if (bmp.size() <5) {
                    new PopupWindows(PublishActivity.this, gridview);
                } else {
                    Toast.makeText(getApplicationContext(), "一次只能传5张", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "sdcard已拔出，不能选择照片", Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(PublishActivity.this, PhotoActivity.class);
            intent.putExtra("ID", i);
            startActivity(intent);
        }
    }





    @OnClick({R.id.txt_cancel, R.id.txt_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                finish();
                break;
            case R.id.txt_publish:
                mCommunityPublishPresenter.reqeust((int)user.getUserId(), user.getSessionId(), communityPublishContent.getText().toString().trim(),bmp);
                break;
            default:
                break;
        }
    }
    class CommunityPublish implements DataCall<Result>{

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Toast.makeText(PublishActivity.this, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(PublishActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
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
    private class GridAdapter extends BaseAdapter {
        private LayoutInflater listContainer;
        private int selectedPosition = -1;
        private List<Bitmap> bmps = new ArrayList<Bitmap>();

        public List<Bitmap> getList() {
            return bmps;
        }

        private class ViewHolder {
            public ImageView image;
        }

        private GridAdapter(Context context, List<Bitmap> bmp) {
            listContainer = LayoutInflater.from(context);
            bmps = bmp;
        }

        @Override
        public int getCount() {
            return bmps.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int sign = i;
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = listContainer.inflate(
                        R.layout.item_published_grida, null);
                holder.image = (ImageView) view
                        .findViewById(R.id.item_grida_image);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            if (i == bmps.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.xc));
                if (i == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(bmps.get(i));
            }
            return view;
        }
    }
    private class PopupWindows extends PopupWindow {

        private PopupWindows(Context mContext, View parent) {
            View view = View.inflate(PublishActivity.this, R.layout.community_popwindow, null);
            final PopupWindow popupWindow = new PopupWindow(view);
            //设置充满父窗体
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.StyleNetChangedDialog_Animation);
            //点击外部关闭弹框
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            update();

            LinearLayout bt1 = (LinearLayout) view.findViewById(R.id.open_camera);
            LinearLayout bt2 = (LinearLayout) view.findViewById(R.id.open_picture);

            LinearLayout bt3 = (LinearLayout) view.findViewById(R.id.btn_cancel);


            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    dismiss();
                }
            });

            bt3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        }
    }
    private static final int TAKE_PICTURE = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int UI = 2;
    private String path = "";
    private Uri mOutPutFileUri;
    String picturePath;

    private void photo() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //文件夹aaaa
        String path = Environment.getExternalStorageDirectory().toString() + "/elife/img";
        File path1 = new File(path);
        if (!path1.exists()) {
            path1.mkdirs();
        }
        File file = new File(path1, System.currentTimeMillis() + ".jpg");
        mOutPutFileUri = Uri.fromFile(file);
        picturePath = file.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 拍照
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom(picturePath);
                }
                break;
            // 相册
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] files = {MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(uri, files, null, null, null);
                    c.moveToFirst();
                    int ii = c.getColumnIndex(files[0]);
                    path = c.getString(ii);
                    c.close();
                    startPhotoZoom(path);
                }

                break;
            default:
                break;
        }
    }

    private Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startPhotoZoom(String uri) {
        drr.add(uri);
        Bitmap bitmap = getLoacalBitmap(drr.get(drr.size() - 1));
        bmp.add(bitmap);
        gridviewInit();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        bmp.clear();
        drr.clear();
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

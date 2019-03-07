package com.wd.tech.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

import com.wd.tech.application.WDApplication;
import com.wd.tech.bean.User;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.UserDao;
import com.wd.tech.util.NetWorkUtils;

import java.util.List;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * date:2019/2/18 19:55
 * author:赵明珠(啊哈)
 * function:
 */
public abstract class WDActivity extends SwipeBackActivity {
    // 相册选取
    public final static int PHOTO = 0;
    // 拍照
    public final static int CAMERA = 1;

    private static WDActivity mForegroundActivity = null;
    private SwipeBackLayout mSwipeBackLayout;
    public User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mForegroundActivity = this;
        ButterKnife.bind(this);
        List<User> users = WDApplication.getAppContext().getUserDao().loadAll();
        if (users != null && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getSole() == 1) {
                    user = users.get(i);
                    break;
                }
            }
        }

        if (NetWorkUtils.isNetworkAvailable(WDApplication.getAppContext())) {
        } else {
            Toast.makeText(WDApplication.getAppContext(), "ffff请检查网络", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WDActivity.this, NoNetWorkActivity.class));
        }
        initView();
        //  初始化右滑退出
        initSwipeBack();


    }



    private void initSwipeBack() {
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        // mSwipeBackLayout.setEdgeSize(200);
    }

    /**
     * 设置layoutId
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 清除数据
     */
    protected abstract void destoryData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mForegroundActivity = this;
    }

    public static WDActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * 得到图片的路径
     *
     * @param fileName
     * @param requestCode
     * @param data
     * @return
     */

    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == CAMERA) {
            return fileName;
        } else if (requestCode == PHOTO) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                actualimagecursor.close();
            }
            return img_path;
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<User> users = WDApplication.getAppContext().getUserDao().loadAll();
        if (users != null && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getSole() == 1) {
                    user = users.get(i);
                    break;
                }
            }
        }
    }
}

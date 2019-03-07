package com.wd.tech.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.wd.tech.R;
import com.wd.tech.application.WDApplication;
import com.wd.tech.face.PermissionAcitivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者: Wang on 2019/2/19 15:58
 * 寄语：加油！相信自己可以！！！
 */


public class StartActivity extends AppCompatActivity {

    public static int PERMISSION_REQ = 0x123456;

    private String[] mPermission = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private List<String> mRequestPermission = new ArrayList<String>();


    int mTime = 2;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (mTime <= 0) {
                    Intent intent = new Intent(StartActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("show", 1);
                    startActivity(intent);
                    finish();
                    return;
                }
                mTime--;
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };
    @BindView(R.id.s_t)
    TextView t;
    @BindView(R.id.s_tt)
    TextView tt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/ziti.ttf");
        t.setTypeface(typeFace);
        tt.setTypeface(typeFace);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            for (String one : mPermission) {
                if (PackageManager.PERMISSION_GRANTED != this.checkPermission(one, Process.myPid(), Process.myUid())) {
                    mRequestPermission.add(one);
                }
            }
            if (!mRequestPermission.isEmpty()) {
                this.requestPermissions(mRequestPermission.toArray(new String[mRequestPermission.size()]), PERMISSION_REQ);
                return;
            }
        }
        startActiviy();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQ) {
            if (resultCode == 0) {
                this.finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 版本兼容
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }
        if (requestCode == PERMISSION_REQ) {
            for (int i = 0; i < grantResults.length; i++) {
                for (String one : mPermission) {
                    if (permissions[i].equals(one) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mRequestPermission.remove(one);
                    }
                }
            }
            startActiviy();
        }
    }

    public void startActiviy() {
        if (mRequestPermission.isEmpty()) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("loading register data...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WDApplication app = (WDApplication) StartActivity.this.getApplicationContext();
                    app.mFaceDB.loadFaces();
                    StartActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.cancel();
                            mHandler.sendEmptyMessage(1);
                        }
                    });
                }
            }).start();
        } else {
            Toast.makeText(this, "PERMISSION DENIED!", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    StartActivity.this.finish();
                }
            }, 3000);
        }
    }
}

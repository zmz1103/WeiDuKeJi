package com.wd.tech.application;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.wd.tech.R;
import com.wd.tech.activity.huanxin.IMActivity;
import com.wd.tech.bean.FindConversationList;
import com.wd.tech.dao.DaoMaster;
import com.wd.tech.dao.DaoSession;
import com.wd.tech.dao.FindConversationListDao;
import com.wd.tech.dao.UserDao;
import com.wd.tech.face.FaceDB;
import com.wd.tech.util.DaoUtils;

import java.util.List;

/**
 * date:2019/2/18 18:32
 * author:赵明珠(啊哈)
 * function:
 */
public class WDApplication extends Application {
    private static WDApplication wdApplication;
    private final String TAG = this.getClass().toString();
    public FaceDB mFaceDB;
    Uri mImage;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;

    private UserDao userDao = null;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;


    @Override
    public void onCreate() {
        super.onCreate();
        wdApplication = this;
        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
        mImage = null;
        Fresco.initialize(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);

        userDao = daoSession.getUserDao();

        EaseUI.getInstance().init(this,null);

        EMClient.getInstance().setDebugMode(true);

        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                username = username.toLowerCase();
                EaseUser easeUser = new EaseUser(username);
                List<FindConversationList> aa = DaoUtils.getInstance().getConversationDao().loadAll();
                FindConversationList conversation = DaoUtils.getInstance().getConversationDao().queryBuilder().where(FindConversationListDao.Properties.UserName.eq(username)).build().unique();
                if (conversation!=null) {
                    easeUser.setNickname(conversation.getNickName());
                    easeUser.setAvatar(conversation.getHeadPic());
                }
                return easeUser;
            }
        });


        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                if (list != null){
                    for (int i = 0; i < list.size(); i++) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(wdApplication).setDefaults(Notification.DEFAULT_ALL);
                        Intent intent = new Intent(wdApplication, IMActivity.class);//将要跳转的界面
                        intent.putExtra(EaseConstant.EXTRA_USER_ID,list.get(i).getUserName());
                        intent.putExtra("UserNames",list.get(i).getUserName());
                        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                        //Intent intent = new Intent();//只显示通知，无页面跳转
                        builder.setAutoCancel(true);//点击后消失
                        builder.setSmallIcon(R.drawable.child_head);//设置通知栏消息标题的头像
                        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
                        builder.setTicker("状态栏显示的文字");
                        String from = list.get(i).getFrom();
                        String name = "";
                        List<FindConversationList> conversationLists = DaoUtils.getInstance().getConversationDao().loadAll();

                        for (int j = 0; j <conversationLists.size() ; j++) {
                           if (conversationLists.get(j).getUserName().equals(from)){
                               name = conversationLists.get(j).getNickName();
                               builder.setContentTitle(name);
                               break;
                           }
                        }


                        String message = list.get(i).getBody().toString().substring(5, list.get(i).getBody().toString().length() - 1);

                        builder.setContentText(message);
                        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
                        PendingIntent intentPend = PendingIntent.getActivity(wdApplication, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        builder.setContentIntent(intentPend);
                        NotificationManager manager = (NotificationManager) wdApplication.getSystemService(wdApplication.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });

    }

    public UserDao getUserDao() {
        return userDao;
    }

    public static WDApplication getAppContext() {
        return wdApplication;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }

    /**
     * @param path
     * @return
     */
    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
            Log.d("com.arcsoft", "check target Image:" + temp.getWidth() + "X" + temp.getHeight());

            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.demo.test;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.demo.test.hotfix.util.FixDexUtils;

public class MyApplication extends Application {

    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getMyWmParams() {
        return wmParams;
    }

    private WindowManager mWindowManager;
    private int windowWidth, windowHeight;

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
        windowWidth = dm.widthPixels;
        //窗口高度
        windowHeight = dm.heightPixels;
    }

    @Override
    protected void attachBaseContext(Context base) {
        // TODO Auto-generated method stub

        try {
            if (getBoolean(base)) {
                MultiDex.install(base);
                FixDexUtils.loadFixedDex(base);

            } else {
                FixDexUtils.deleteFixedDex(base);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        super.attachBaseContext(base);

    }

    public boolean setBoolean(Context context) throws PackageManager.NameNotFoundException {
        SharedPreferences sp = context.getSharedPreferences("upFix", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("version_" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode, true);
        return editor.commit();
    }

    private boolean getBoolean(Context context) throws PackageManager.NameNotFoundException {
        SharedPreferences sp = context.getSharedPreferences("upFix", Context.MODE_PRIVATE);
        return sp.getBoolean("version_" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode, false);
    }

}

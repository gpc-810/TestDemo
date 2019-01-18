package com.demo.test;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.view.WindowManager;

import com.demo.test.hotfix.util.FixDexUtils;

public class MyApplication extends Application {

    private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

    public WindowManager.LayoutParams getMyWmParams() {
        return wmParams;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
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

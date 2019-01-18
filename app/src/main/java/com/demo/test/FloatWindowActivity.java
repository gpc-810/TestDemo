package com.demo.test;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.demo.test.widget.view.DragFloatActionButton;

public class FloatWindowActivity extends AppCompatActivity {

    private WindowManager wm;
    private WindowManager.LayoutParams wmParams;
    private DragFloatActionButton floatButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);
        wm= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=getResources().getDisplayMetrics();
        int widthPixels=dm.widthPixels;
        int heightPixels=dm.heightPixels;
        wmParams=((MyApplication)getApplication()).getMyWmParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0+
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            wmParams.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        wmParams.format=PixelFormat.RGBA_8888;
        wmParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ;
        wmParams.gravity = Gravity.LEFT|Gravity.TOP;//
        wmParams.x = 0;//widthPixels-150;   //设置位置像素
        wmParams.y = 0;//heightPixels-110;
        wmParams.width=200; //设置图片大小
        wmParams.height=200;

        floatButton=new DragFloatActionButton(this);
        wm.addView(floatButton,wmParams);




    }
}

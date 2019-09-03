package com.demo.remoteViews.appWidget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.widget.RemoteViews;

import com.demo.test.R;
import com.demo.util.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class PunchRemoteViewBtn {

    private static final String TAG = "PunchRemoteViewBtn";

    private Context mContext;
    private RemoteViews punchView;
    @LayoutRes
    private int remoteViewId;

    private long time_ms;
    StringBuffer sb;
    private Calendar calendar;

    public static final int HH_MM = 1;
    public static final int HH_MM_SS = 2;

    private int format;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };


    private Timer mTimer;
    private TimerTask mTimerTask;

    private TimeListener mTimeListener;

    public PunchRemoteViewBtn(Context context) {
        this.mContext = context;
        remoteViewId = R.layout.punch_view;
        punchView = new RemoteViews(mContext.getPackageName(), remoteViewId);
        initView(mContext, remoteViewId);
    }

    private void initView(Context context, int remoteViewId) {
        calendar=Calendar.getInstance();
        sb=new StringBuffer();
        if (format == HH_MM) {
            sb.append(TimeUtil.getFormatStringDate("%tR", calendar.getTime()));
        } else {
            sb.append(TimeUtil.getFormatStringDate("%tT", calendar.getTime()));
        }
        if (mTimeListener != null) {
            mTimeListener.changeTimeListener(calendar);
        }
        setText(sb);
    }



    private void setText(StringBuffer sb) {
        Log.e(TAG, "setText: " + sb);
        punchView.setTextViewText(R.id.punch_btn_time, sb);
        Intent intent=new Intent();
        intent.setData(Uri.parse("vtime://clockin"));
        PendingIntent punchBtnPending= PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        punchView.setOnClickPendingIntent(R.id.punch_view_btn,punchBtnPending);
    }

    public RemoteViews getPunchView() {
        return punchView;
    }

    public interface TimeListener {
        void changeTimeListener(Calendar calendar);
    }
}

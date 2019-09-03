package com.demo.remoteViews.appWidget;

import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;

import com.demo.test.R;

import java.util.ArrayList;

public class PunchManager {

    private ArrayList<String> arrayList;

    private static class SingleHolder {
        private static final PunchManager INSTANC = new PunchManager();
    }

    private PunchManager() {
    }

    public static final PunchManager getInstance() {
        return SingleHolder.INSTANC;
    }

    public PunchManager setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        return this;
    }

    public RemoteViews initView(Context context) {
        if (arrayList == null) {
            throw new RuntimeException("数据是空的");
        }
        RemoteViews punchGroup = new RemoteViews(context.getPackageName(), R.layout.punch_group_widget);
        punchGroup.removeAllViews(R.id.punch_group);
        for (int i = 0; i < arrayList.size(); i++) {
            ItemPunchRemoteView views = new ItemPunchRemoteView(context, i == 1);
            RemoteViews remoteViews = views.getItemPunchView();
            if (i == 0) {//第一个
                remoteViews.setViewVisibility(R.id.top_view, View.GONE);
                remoteViews.setViewVisibility(R.id.textView_line, View.VISIBLE);
            } else if (i == arrayList.size() - 1) {//最后一个
                remoteViews.setViewVisibility(R.id.top_view, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.textView_line, View.GONE);
            } else {
                remoteViews.setViewVisibility(R.id.top_view, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.textView_line, View.VISIBLE);
            }
            punchGroup.addView(R.id.punch_group, remoteViews);
        }
        return punchGroup;
        //        appWidgetManager.updateAppWidget(appWidgetId, punchGroup);

    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}

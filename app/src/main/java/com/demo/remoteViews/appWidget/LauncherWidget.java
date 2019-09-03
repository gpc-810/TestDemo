package com.demo.remoteViews.appWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.demo.test.R;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class LauncherWidget extends AppWidgetProvider {
    private static final String TAG = "LauncherWidget";

    private ComponentName thisWidget;
    private RemoteViews remoteViews;

    public LauncherWidget() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive: action=" + intent.getAction());


    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Log.e(TAG, "updateAppWidget: " );
        // Construct the RemoteViews object
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");


        Intent intent = new Intent(context, ListViewRemoteService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putStringArrayListExtra("dataArray",arrayList);
        //设置适配器
        remoteViews.setRemoteAdapter(R.id.widget_list, intent);

//        Intent intent2 = new Intent();
//        //TODO
//        //intent2.setComponent(new ComponentName("包名", "类名"));
//        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        //拼接PendingIntent
//        remoteViews.setPendingIntentTemplate(R.id.widget_list, pendingIntentTemplate);
        //更新RemoteViews
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);

//        AppWidgetManager manager = AppWidgetManager.getInstance(context);
//        manager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.i(TAG, "onUpdate: appWidgetIds===" + appWidgetIds.length);
        thisWidget = new ComponentName(context, LauncherWidget.class);
        remoteViews = new RemoteViews(context.getPackageName(),	R.layout.list_view_widget);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


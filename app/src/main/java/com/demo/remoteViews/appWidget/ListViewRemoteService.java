package com.demo.remoteViews.appWidget;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.demo.test.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ListViewRemoteService extends RemoteViewsService {
    private static final String TAG = "ListViewRemoteService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsFactory {
        private static final String TAG = "ListRemoteViewsFactory";
        public final int ViewTypePunch = 0;
        public final int TimeClock = 1;

        private Context mContext;
        private ArrayList<String> mList;

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mList = (ArrayList<String>) intent.getSerializableExtra("dataArray");

            //            if (Looper.myLooper() == null) {
            //                Looper.prepare();
            //            }

        }

        @Override
        public void onCreate() {
            Log.e(TAG, "onCreate: ");

        }

        @Override
        public void onDataSetChanged() {
            Log.e(TAG, "onDataSetChanged: ");

        }

        @Override
        public void onDestroy() {
            Log.e(TAG, "onDestroy: ");
            mList.clear();
        }

        @Override
        public int getCount() {
            if (null == mList) {
                return 0;
            }
            Log.e(TAG, "getCount: " + mList.size());
            return 2;

        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.i(TAG, "getViewAt: " + position);
            if (position < 0 || position >= mList.size())
                return null;

            int currentType = getItemViewType(position);
            RemoteViews rv = null;
            switch (currentType) {
                case ViewTypePunch: {
                    PunchManager punchManager = PunchManager.getInstance();
                    punchManager.setArrayList(mList);
                    rv = punchManager.initView(mContext);
                }
                break;
                case TimeClock:
                    rv = new RemoteViews(getPackageName(), R.layout.widget_time_clock);
                    Calendar calendar = Calendar.getInstance();
                    String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
                    rv.setTextViewText(R.id.appwidget_text, time);
                    break;
            }
            //            String content = mList.get(position);
            //            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

            //            Intent intent = new Intent();
            //            //TODO
            //            //intent.setComponent(new ComponentName("包名", "类名"));
            //            //与CustomWidget中remoteViews.setPendingIntentTemplate配对使用
            //            rv.setOnClickFillInIntent(R.id.widget_list_item_layout, intent);
            //
            //            rv.setTextViewText(R.id.widget_list_item_tv, content);

            return rv;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        public int getItemViewType(int position) {
            return position;//对应type
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}

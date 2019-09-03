package com.demo.remoteViews.appWidget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.RemoteViews;

import com.demo.test.R;
import com.demo.util.TimeUtil;

public class ItemPunchRemoteView {
    private Context context;
    private RemoteViews itemPunchView;
    @LayoutRes
    private int remoteViewId;

    private RemoteViews punchView;

    public ItemPunchRemoteView(Context context, boolean isShowPunchView) {
        this.context = context;
        this.remoteViewId = R.layout.punch_item_widget;
        itemPunchView = new RemoteViews(context.getPackageName(), remoteViewId);
        initView(context, isShowPunchView);
    }

    private void initView(Context context, boolean isShowPunchView) {
        if (isShowPunchView) {
            PunchRemoteViewBtn punchBtnView = new PunchRemoteViewBtn(context);
            punchView = punchBtnView.getPunchView();
            addPunchView(R.id.punch_content_ll, punchView);
        }

    }

    public RemoteViews getItemPunchView() {
        return itemPunchView;
    }


    /**
     * 添加打卡按钮
     *
     * @param punchView
     */
    public void addPunchView(@IdRes int viewId, RemoteViews punchView) {
        if (punchView != null) {
            this.punchView = punchView;
            itemPunchView.addView(viewId, this.punchView);
        }
    }


    public void setDataView(int shift_type, int position, long time) {

        setLocationText("", false);
        setPunchFlagText(false);

    }

    /**
     * 设置地址文本
     *
     * @param str
     */
    private void setLocationText(String str, boolean isWifi) {

    }

    /**
     * 设置打卡状态
     */
    private void setPunchFlagText(boolean isUp) {


        setUpPunchViewVisible(View.VISIBLE);

        setAdminChangeVisible();
        setOutWorkVisible();
        setApplyStatus(/**flag*/);
    }

    private void setUpPunchViewVisible(int visible) {

        //        updatePunchTextView.setVisibility(visible);
    }

    private void setAdminChangeVisible() {
        //        if ((mBean != null && mBean.getClockType() == PunchConfig.ADMIN_CHANGE_TYPE && mBean.getClock_result() != PunchConfig.ClockResult
        // .INVALID)) {//管理员修改结果
        //            adminChangeText.setVisibility(VISIBLE);
        //            adminChangeText.setText(R.string.admin_change_result);
        //            applyLL.setVisibility(GONE);
        //            updatePunchTextView.setVisibility(GONE);
        //        } else {
        //        adminChangeText.setVisibility(View.GONE);
        //        }
    }

    private void setOutWorkVisible() {

    }

    /**
     * 设置申请状态
     */
    private void setApplyStatus() {

    }
}

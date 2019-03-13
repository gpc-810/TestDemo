package com.guo.project.ndk.glideprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ProgressImageView extends AppCompatImageView {
    private int progress = 0;//加载的进度
    private int diameter = 100;//圆形进度条的直径
    private Paint mPaint;

    public ProgressImageView(Context context) {
        super(context);
        init();
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (progress > 0 && progress < 360) {//如果图片加载完成，进度条不显示
            /**
             * 画进度圆圈，圆圈位置放在图片中间
             */
            mPaint.setAntiAlias(true);//取消锯齿
            mPaint.setStrokeWidth(10);//画笔宽度
            mPaint.setColor(Color.BLUE);//圆圈设置成蓝色
            mPaint.setStyle(Paint.Style.STROKE);//空心
            int ho = (getMeasuredWidth() - diameter) / 2;//水平
            int ve = (getMeasuredHeight() - diameter) / 2;//垂直
            RectF rectF = new RectF();
            rectF.left = ho;
            rectF.top = ve;
            rectF.right = ho + diameter;
            rectF.bottom = ve + diameter;
            canvas.drawArc(rectF, -90, progress, false, mPaint);

            /**
             * 画进度文字显示，位置在图片中间向左编译30px，向下移动10px
             */
            mPaint.setColor(Color.RED);//文字设置成红色
            mPaint.setStrokeWidth(1);//画笔宽度
            mPaint.setTextSize(25);//字体大小
            canvas.drawText(String.valueOf((int) (progress / 3.6)) + "%", getMeasuredWidth() / 2 - 30, getMeasuredHeight() / 2 + 10, mPaint);
        }
    }

    /**
     * 设置进度
     *
     * @param total    总的长度
     * @param progress 已加载的进度
     */
    public void setProgress(int total, int progress) {
        this.progress = progress * 360 / total;
        postInvalidate();
    }

}

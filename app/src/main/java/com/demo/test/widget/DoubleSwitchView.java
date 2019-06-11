package com.demo.test.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.demo.test.R;


/**
 * 上下(两层)布局切换View
 *
 * @作者 guo
 * @创建日期 2019/4/17 11:31
 */
public class DoubleSwitchView extends FrameLayout {

    private static final String TAG = "DoubleSwitchView";

    /**
     * 重叠
     */
    private static final int STATUS_OVERLAP = 0;
    /**
     * 展开
     */
    private static final int STATUS_EXPAND = 1;


    private View bottomView, topView;
    private int minMaiginTopViewHeight;

    private boolean isAnimating;
    private float topViewTranslateY;//默认在顶部 是0

    private int nowStatus;

    /**
     * 手速判断
     */
    protected VelocityTracker mVelocityTracker;
    protected int mMaximumVelocity;
    private int mTouchSlop;
    private float mDownY;

    private OnFinishInflateListener mFinishListener;


    public DoubleSwitchView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DoubleSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DoubleSwitchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DoubleSwitchView, defStyle, 0);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.

        a.recycle();
        mVelocityTracker = VelocityTracker.obtain();
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();


        // Set up a default TextPaint object

        // Update TextPaint and text measurements from attributes
        invalidateMeasurements();
    }

    private void invalidateMeasurements() {
        setMotionEventSplittingEnabled(false);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mFinishListener != null) {
                    Log.e(TAG, "onFinishInflate: ");
                    mFinishListener.onFinishInflate();
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("sub layouts must be 2 ");
        }
        bottomView = getChildAt(0);
        topView = getChildAt(1);


    }


    // TODO: 2019/4/17  事件拦截 不需要
    //    @Override
    //    public boolean onInterceptTouchEvent(MotionEvent ev) {
    //        if (isAnimating) {
    //            return true;
    //        }
    //        final int action = ev.getAction();
    //        float y = ev.getY();
    //        switch (action) {
    //            case MotionEvent.ACTION_DOWN:
    //                mDownY=y;
    //                break;
    //            case MotionEvent.ACTION_MOVE:
    //                float dy=y-mDownY;
    //                //
    //
    //
    //                if (dy>30){
    //                    return true;
    //                }
    //                break;
    //            case MotionEvent.ACTION_UP:
    //                break;
    //            case MotionEvent.ACTION_CANCEL:
    //                break;
    //        }
    //
    //        return super.onInterceptTouchEvent(ev);
    //    }

    /**
     * 下滑 top 下滑
     */
    private void downSlide() {

        if (isAnimating)
            return;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topView, "translationY", topView.getTranslationY(), topViewTranslateY);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (Float) animation.getAnimatedValue();
                float percent = currentValue * 1.0f / topViewTranslateY;
                topView.setTranslationY(topViewTranslateY * percent);
                isAnimating = true;
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
                nowStatus = STATUS_EXPAND;

            }
        });
        objectAnimator.start();
    }


    /**
     * top 上滑
     */
    private void upSlide() {
        if (isAnimating)
            return;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topView, "translationY", topView.getTranslationY(), 0f);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (Float) animation.getAnimatedValue();
                float percent = currentValue * 1.0f / topViewTranslateY;
                topView.setTranslationY(-topViewTranslateY * percent);
                isAnimating = true;
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimating = false;
                nowStatus = STATUS_EXPAND;

            }
        });
        objectAnimator.start();

    }

    public void showTopView() {
        upSlide();
    }

    public void showBottomView() {
        downSlide();
    }

    public void setTopViewTranslateY(float topViewTranslateY) {
        if (topViewTranslateY <= 0f || this.topViewTranslateY > 0) {//有过设置 不再设置
            return;
        }
        this.topViewTranslateY = topViewTranslateY;
    }

    public void setFinishListener(OnFinishInflateListener finishListener) {
        this.mFinishListener = finishListener;
    }

    public interface OnFinishInflateListener {
        void onFinishInflate();
    }
}

package com.demo.test.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.demo.test.R;
import com.demo.test.bean.NumberBean;
import com.demo.test.widget.adapter.ArrayWheelAdapter;
import com.guo.wheelview.listener.OnItemSelectedListener;
import com.guo.wheelview.view.WheelView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 日历选择控件
 */
public class CalendarSelectView extends FrameLayout {

    OnSelectListener mSelectListener;

    private View rootView;
    private FrameLayout mTitleRootView;
    private TitleView mTitleView;
    private String titleClass;

    private Calendar mCalendar;

    private List<NumberBean> list1;
    private List<NumberBean> list2;
    private List<NumberBean> list3;
    private List<NumberBean> list4;
    private List<NumberBean> list5;

    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;
    private WheelView wv_option4;
    private WheelView wv_option5;

    private Animation timeViewAnimationShow;
    private Animation timeViewAnimationHint;
    private View mTimeRootView;

    private int minYear, maxYear;


    public CalendarSelectView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CalendarSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CalendarSelectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        timeViewAnimationShow = AnimationUtils.loadAnimation(context, R.anim.pickerview_dialog_scale_in);
        timeViewAnimationHint = AnimationUtils.loadAnimation(context, R.anim.pickerview_dialog_scale_out);

        rootView = inflate(context, R.layout.calendar_select_view, this);
        mTitleRootView = rootView.findViewById(R.id.calendar_title_layout);
        mTimeRootView = rootView.findViewById(R.id.calendar_select_time_ll);
        wv_option1 = rootView.findViewById(R.id.calendar_select_position_1);
        wv_option2 = rootView.findViewById(R.id.calendar_select_position_2);
        wv_option3 = rootView.findViewById(R.id.calendar_select_position_3);
        wv_option4 = rootView.findViewById(R.id.calendar_select_position_4);
        wv_option5 = rootView.findViewById(R.id.calendar_select_position_5);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CalendarSelectView);
        minYear = array.getInteger(R.styleable.CalendarSelectView_min_year, 1900);
        maxYear = array.getInteger(R.styleable.CalendarSelectView_max_year, 2099);
        titleClass = array.getString(R.styleable.CalendarSelectView_layout_src);
        if (TextUtils.isEmpty(titleClass)) {
            mTitleView = new TitleView(context);
        } else {
            try {
                Class cls = Class.forName(titleClass);
                @SuppressWarnings("unchecked") Constructor constructor = cls.getConstructor(Context.class);
                mTitleView = (TitleView) constructor.newInstance(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long timeMillis = System.currentTimeMillis();
        mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(timeMillis);

        if (mTitleView != null) {
            mTitleRootView.addView(mTitleView);
        }

        getDataTimeList();
        wv_option1.setAdapter(new ArrayWheelAdapter(list1));
        wv_option2.setAdapter(new ArrayWheelAdapter(list2));
        wv_option3.setAdapter(new ArrayWheelAdapter(list3));
        wv_option4.setAdapter(new ArrayWheelAdapter(list4));
        wv_option5.setAdapter(new ArrayWheelAdapter(list5));

        wv_option1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                getSelectItemData();
            }
        });
        wv_option2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                getSelectItemData();
                int opt3Select = wv_option3.getCurrentItem();//上一个opt2的选中位置
                list3.clear();
                getDayOfMonthList(list1.get(wv_option1.getCurrentItem()).getNumber(), list2.get(index).getNumber());
                wv_option3.setAdapter(new ArrayWheelAdapter(list3));
                //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                opt3Select = opt3Select >= list3.size() - 1 ? list3.size() - 1 : opt3Select;
                wv_option3.setCurrentItem(opt3Select);


            }
        });

        wv_option3.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                getSelectItemData();
            }
        });
        wv_option4.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                getSelectItemData();
            }
        });
        wv_option5.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                getSelectItemData();
            }
        });

        selectTime(mCalendar);


    }

    /**
     * 设置选中的时间
     *
     * @param calendar
     */
    public void selectTime(Calendar calendar) {
        if (list1 == null || list1.size() <= 0) {
            return;
        }
        wv_option1.setCurrentItem(calendar.get(Calendar.YEAR) - minYear);
        if (list2 == null || list2.size() <= 0) {
            return;
        }
        wv_option2.setCurrentItem(calendar.get(Calendar.MONTH));
        if (list3 == null || list3.size() <= 0) {
            return;
        }
        wv_option3.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        if (list4 == null || list4.size() <= 0) {
            return;
        }
        wv_option4.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY));
        if (list5 == null || list5.size() <= 0) {
            return;
        }
        wv_option5.setCurrentItem(calendar.get(Calendar.MINUTE));

    }

    public void selectTime(long timeMillis) {
        mCalendar.setTimeInMillis(timeMillis);
        selectTime(mCalendar);

    }

    public void selectTime() {
        selectTime(System.currentTimeMillis());

    }


    public void getSelectItemData() {
        if (mSelectListener == null) {
            mSelectListener = new OnSelectListener() {
                @Override
                public void onSelectItemListener(String... optionStrings) {
                    mTitleView.setText(optionStrings);
                }
            };
        }
        String option1String = list1.get(wv_option1.getCurrentItem()).getPickerViewText();
        if (list2 == null || list2.size() <= 0) {
            mSelectListener.onSelectItemListener(option1String);
            return;
        }
        String option2String = list2.get(wv_option2.getCurrentItem()).getPickerViewText();
        if (list3 == null || list3.size() <= 0) {
            mSelectListener.onSelectItemListener(option1String, option2String);
            return;
        }
        String option3String = list3.get(wv_option3.getCurrentItem()).getPickerViewText();
        if (list4 == null || list4.size() <= 0) {
            mSelectListener.onSelectItemListener(option1String, option2String, option3String);
            return;
        }
        String option4String = list4.get(wv_option4.getCurrentItem()).getPickerViewText();
        if (list5 == null || list5.size() <= 0) {
            mSelectListener.onSelectItemListener(option1String, option2String, option3String, option4String);
            return;
        }
        String option5String = list5.get(wv_option5.getCurrentItem()).getPickerViewText();
        mSelectListener.onSelectItemListener(option1String, option2String, option3String, option4String, option5String);


    }

    public void setSelectListener(OnSelectListener selectListener) {
        this.mSelectListener = selectListener;
    }

    private void getDataTimeList() {
        getYearList(minYear, maxYear);
        getMonthList();
        getDayOfMonthList(list1.get(wv_option1.getCurrentItem()).getNumber(), list2.get(wv_option2.getCurrentItem()).getNumber());
        getHourList();
        getMinuteList();


    }

    private void getYearList(int min, int max) {
        if (list1 == null) {
            list1 = new ArrayList<>();
        }
        for (int i = min; i <= max; i++) {
            NumberBean bean = new NumberBean(i);
            list1.add(bean);
        }


    }

    private void getMonthList() {
        if (list2 == null) {
            list2 = new ArrayList<>();
        }
        for (int i = 1; i <= 12; i++) {
            NumberBean bean = new NumberBean(i);
            list2.add(bean);
        }

    }

    private void getDayOfMonthList(int year, int month) {
        int maxDay = 28;

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            maxDay = 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDay = 30;
        }

        if (month == 2) {
            if (year % 4 == 0) {//闰年 TODO 待定
                maxDay = 29;
            } else {
                maxDay = 28;
            }
        }
        if (list3 == null) {
            list3 = new ArrayList<>();
        }
        for (int i = 1; i <= maxDay; i++) {
            NumberBean bean = new NumberBean(i);
            list3.add(bean);
        }

    }

    private void getHourList() {
        if (list4 == null) {
            list4 = new ArrayList<>();
        }
        for (int i = 0; i < 24; i++) {
            NumberBean bean = new NumberBean(i);
            list4.add(bean);
        }
    }

    private void getMinuteList() {
        if (list5 == null) {
            list5 = new ArrayList<>();
        }
        for (int i = 0; i < 60; i++) {
            NumberBean bean = new NumberBean(i);
            list5.add(bean);
        }
    }


    public void showTimeView() {
        mTimeRootView.startAnimation(timeViewAnimationShow);
        mTimeRootView.setVisibility(VISIBLE);

    }

    public void hintTimeView() {
        mTimeRootView.startAnimation(timeViewAnimationHint);
        mTimeRootView.setVisibility(GONE);
    }

    public interface OnSelectListener {
        void onSelectItemListener(String... optionStrings);
    }

}

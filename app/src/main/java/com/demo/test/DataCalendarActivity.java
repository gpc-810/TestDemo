package com.demo.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.test.bean.CardBean;
import com.demo.test.widget.CalendarSelectView;
import com.demo.test.widget.builder.OptionsPickerBuilder;
import com.demo.test.widget.listener.CustomListener;
import com.demo.test.widget.listener.OnOptionsSelectListener;
import com.demo.test.widget.view.OptionsPickerView;

import java.util.ArrayList;

public class DataCalendarActivity extends Activity {
    private OptionsPickerView pvCustomOptions;

    private ArrayList<CardBean> cardItem = new ArrayList<>();

    private Button btn_CustomOptions;
    private CalendarSelectView mSelectTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_calendar);
        mSelectTimeView = (CalendarSelectView) findViewById(R.id.calendar_select_time_btn);

        btn_CustomOptions = findViewById(R.id.calendar_select_dialog_btn);
        btn_CustomOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvCustomOptions.show(); //弹出自定义条件选择器
            }
        });
        getCardData();
        initCustomOptionPicker();

        findViewById(R.id.calendar_select_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectTimeView.showTimeView();
            }
        });
        findViewById(R.id.calendar_select_hint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectTimeView.hintTimeView();
            }
        });

    }

    private void initCustomOptionPicker() {
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                btn_CustomOptions.setText(tx);
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.returnData();
                        pvCustomOptions.dismiss();
                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.dismiss();
                    }
                });

                tvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCardData();
                        pvCustomOptions.setPicker(cardItem);
                    }
                });

            }
        }).isDialog(true).setCyclic(true, false, false).build();

        pvCustomOptions.setPicker(cardItem);//添加数据
    }

    private void getCardData() {
        for (int i = 0; i < 5; i++) {
            cardItem.add(new CardBean(i, "No.ABC12345 " + i));
        }

        for (int i = 0; i < cardItem.size(); i++) {
            if (cardItem.get(i).getCardNo().length() > 6) {
                String str_item = cardItem.get(i).getCardNo().substring(0, 6) + "...";
                cardItem.get(i).setCardNo(str_item);
            }
        }
    }
}

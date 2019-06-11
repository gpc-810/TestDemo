package com.demo.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.test.widget.DoubleSwitchView;

public class DoubleSwitchViewActivity extends AppCompatActivity implements View.OnClickListener {

    private DoubleSwitchView mSwitchView;
    private TextView mView1;
    private TextView mView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_switch_view);
        findViewById(R.id.double_view_1).setOnClickListener(this);
        findViewById(R.id.double_view_2).setOnClickListener(this);
        mSwitchView = (DoubleSwitchView) findViewById(R.id.double_switch_view);
        mView1 = (TextView) findViewById(R.id.double_view_1);
        mView2 = (TextView) findViewById(R.id.double_view_2);
        mSwitchView.setFinishListener(new DoubleSwitchView.OnFinishInflateListener() {
            @Override
            public void onFinishInflate() {
                mSwitchView.setTopViewTranslateY(mSwitchView.getHeight() - mView2.getBottom() - mSwitchView.getPaddingBottom());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.double_view_1:
                mSwitchView.showBottomView();
                break;
            case R.id.double_view_2:
                mSwitchView.showTopView();
                break;
        }
    }
}

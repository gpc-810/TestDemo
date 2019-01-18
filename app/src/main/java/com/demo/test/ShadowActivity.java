package com.demo.test;

import android.app.Activity;
import android.os.Bundle;

/**
 * 阴影activity
 */
public class ShadowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow);
    }
}

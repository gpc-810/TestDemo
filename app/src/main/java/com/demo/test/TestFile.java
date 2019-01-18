package com.demo.test;

import android.content.Context;
import android.widget.Toast;

public class TestFile {
    int i = 10;
    int a = 0;

    public void testFix(Context context) {
        a = 5;

        Toast.makeText(context, "aaaaaa:count==" + i / a, Toast.LENGTH_LONG).show();

    }

}

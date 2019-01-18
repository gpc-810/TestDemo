package com.demo.test.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.test.R;

public class TitleView extends LinearLayout {

    TextView textView;

    public TitleView(Context context) {
        super(context);
        if ("com.demo.test.widget.TitleView".equals(getClass().getName())){
            LayoutInflater.from(context).inflate(R.layout.base_title_layout_view, this, true);

            textView=findViewById(R.id.title_view);
        }
    }

    public void setText(String... strings){

        StringBuffer sb=new StringBuffer();
        for (String s:strings){
            sb.append(s);
        }
        textView.setText(sb);

    }

}

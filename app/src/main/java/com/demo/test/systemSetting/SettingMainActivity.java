package com.demo.test.systemSetting;

import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingMainActivity extends AppCompatActivity {
    private ListView mSettingList;
    private ListAdapter mAdapter;
    private String[] settingName = {"应用信息","白名单"};
    private String[] settingAction = {"android.settings.APPLICATION_DETAILS_SETTINGS"};


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_main);
        mSettingList = (ListView) findViewById(R.id.setting_list);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, settingName);
        mSettingList.setAdapter(mAdapter);
        mSettingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(settingAction[position]);
                intent.setData(Uri.fromParts("package", SettingMainActivity.this.getPackageName(), null));
                startActivity(intent);

            }
        });
    }

    private void setListData() {


    }

}

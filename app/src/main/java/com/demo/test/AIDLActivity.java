package com.demo.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.test.service.ItineraryManagerService;
import com.vargo.vtime.IVTimeService;

import org.json.JSONException;
import org.json.JSONObject;

public class AIDLActivity extends AppCompatActivity {

    private static final String TAG = "AIDLActivity";

    private ServiceConnection mConnection;

    private EditText mMethod;
    private EditText mJson;
    private Button mCreateItinerary;
    private Button mSearchItinerary;
    private TextView mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        mMethod = (EditText) findViewById(R.id.aidl_method);
        mJson = (EditText) findViewById(R.id.aidl_json);
        mCreateItinerary = (Button) findViewById(R.id.aidl_create_itinerary);
        mSearchItinerary = (Button) findViewById(R.id.aidl_search_itinerary);
        mContent = (TextView) findViewById(R.id.aidl_content);


    }


    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.aidl_create_itinerary://新建行程
                createItinerary();
                break;
            case R.id.aidl_search_itinerary://查询行程
                break;
        }

    }

    /**
     * 创建行程
     */
    private void createItinerary() {
        if (mConnection != null) {
            unbindService(mConnection);
        }
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IVTimeService ivTimeService = IVTimeService.Stub.asInterface(service);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(mJson.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String ssss = ivTimeService.handler(mMethod.getText().toString(), jsonObject.toString(), null);
                    mContent.setText(ssss);
                    Log.e(TAG, "onServiceConnected: " + ssss);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        Intent intent = new Intent();
        intent.setPackage("com.vargo.vtime");
        intent.setAction("action.vtime.vtimeservice");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);


    }

    @Override
    protected void onDestroy() {
        if (mConnection != null) {
            unbindService(mConnection);
        }
        super.onDestroy();
    }
}

package com.demo.test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

public class AudioActivity extends AppCompatActivity {

    private Button mRecord;
    private Button mPlay;
    private Button mStop;
    private SeekBar mPlaySeekBar;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPlaySeekBar.setProgress(msg.what);
        }
    };

    // TODO: 2019/2/25 待完善




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        mRecord = (Button) findViewById(R.id.audio_activity_record);
        mPlay = (Button) findViewById(R.id.audio_activity_play);
        mStop = (Button) findViewById(R.id.audio_activity_stop);
        mPlaySeekBar = (SeekBar) findViewById(R.id.audio_activity_play_seek_bar);
    }
}

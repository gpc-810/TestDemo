package com.demo.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.CopyOnWriteArrayList;

public class ItineraryManagerService extends Service {

    private static final String TAG = "ItineraryManagerService";

    private CopyOnWriteArrayList<Object> list=new CopyOnWriteArrayList<>();



    public ItineraryManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

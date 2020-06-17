package com.jp.app;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyObserver implements LifecycleObserver {
    public static final String TAG="MyObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connectListener(){
        Log.d(TAG,"connectListener:------------------onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnectListener(){
        Log.d(TAG,"disconnectListener:------------------onPause");

    }

}

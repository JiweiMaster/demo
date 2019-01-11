package com.nrec.demo;

import android.app.Application;
import android.util.Log;

import com.nrec.commonwidget.ActivityLifeCyclerListener;

/**
 * Created by 18099 on 2019/1/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifeCyclerListener activityLifeCyclerListener = new ActivityLifeCyclerListener();
        registerActivityLifecycleCallbacks(activityLifeCyclerListener);
        activityLifeCyclerListener.setOnActivityListener(new ActivityLifeCyclerListener.ActivityListener() {
            @Override
            public void onBackGround() {
                Log.e(""+getPackageName(),"进入后台");
            }
        });
    }
}

package com.befiring.easytime.app;

import android.app.Application;

//import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/8/31.
 */
public class EasyTimeApplication extends Application{

    private static EasyTimeApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

//        SMSSDK.initSDK(this, "16b719b51084b", "460094dd229e6efca08119e46a8fe15b");
    }
    public static EasyTimeApplication getInstance(){
        return instance;
    }
}

package com.imooc.animation360.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.imooc.animation360.engine.FloatViewManager;

/**
 * Created by Administrator on 2016/8/5.
 */
public class MyFloatService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //启动服务就让小圆球显示在屏幕上
        FloatViewManager floatViewManager = FloatViewManager.getInstance(this);
        floatViewManager.showFloatCircleView();
        super.onCreate();
    }
}

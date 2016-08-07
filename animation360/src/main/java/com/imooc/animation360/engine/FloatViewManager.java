package com.imooc.animation360.engine;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.imooc.animation360.view.FloatCirecleView;
import com.imooc.animation360.view.FloatMenuView;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/8/5.
 * 控制浮动小球对象的操作类
 */
public class FloatViewManager {

    private static final String TAG = FloatViewManager.class.getName();
    private Context context;
    private WindowManager windowManager; //通过这个windowmanager来操控浮窗的显示和隐藏以及位置的改变
    /**
     * 浮动球对象
     */
    private static FloatCirecleView floatCirecleView;

    private FloatMenuView floatMenuView;

    private float startx;
    private float starty;
    private float x0;
    private float y0;

    private WindowManager.LayoutParams params = null;
    /**
     * 浮动框点击滑动事件
     */
    private View.OnTouchListener cirecleViewTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startx = event.getRawX();
                    x0 = event.getRawX();
                    y0 = event.getRawY();
                    starty = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = event.getRawX();
                    float y = event.getRawY();
                    float dx = x - startx;
                    float dy = y - starty;
                    params.x += dx;
                    params.y += dy;
                    floatCirecleView.setDragState(true);

                    // updateViewLayout方法刷新手机自定义控件的位置
                    windowManager.updateViewLayout(floatCirecleView, params);
                    //重新设置下次滑动的初始化位置
                    startx = x;
                    starty = y;
                    break;
                case MotionEvent.ACTION_UP:
                    float x1 = event.getRawX();
                    if (x1 > getScreenWidth() / 2) { //拖拽小球到屏幕的左边的时候贴近屏幕边缘,在右边的时候贴近屏幕右边边缘

                        params.x = getScreenWidth() - floatCirecleView.width;

                    } else {
                        params.x = 0;

                    }
                    //手指抬起的时候设置状态为非滑动
                    floatCirecleView.setDragState(false);
                    //重新刷新小球的位置
                    windowManager.updateViewLayout(floatCirecleView, params);
                    if (Math.abs(x1 - x0) > 6) { //这里是为了区分手指滑动和点击事件，如果手指抬起的时候滑动的距离比开始的距离位置大于6个像素则判断为滑动事件,
                        // 返回true,如果不是这样就返回false,走点击事件,这样来解决事件冲突

                        return true;
                    } else {

                        return false;

                    }

                default:
                    break;

            }

            return false;
        }
    };

    /**
     * 获取当前屏幕的宽度
     *
     * @return
     */
    public int getScreenWidth() {
        return windowManager.getDefaultDisplay().getWidth();


    }

    /**
     * 获取当前屏幕的高度
     *
     * @return
     */
    public int getScreenHeight() {
        return windowManager.getDefaultDisplay().getHeight();


    }

    /**
     * 获取当前状态栏的高度
     *
     * @return
     */
    public int getStatusHeight() {

        try {
            Class<?> c = Class.forName("com,android,internal.R$dimen");
            Object o = c.newInstance();
            Field file = c.getField("status_bar_height");
            int x = (Integer) file.get(o);
            Log.i(TAG, "getStatusHeight: x=" + x);
            return context.getResources().getDimensionPixelSize(x);

        } catch (Exception e) {
            return 0;
        }


    }


    private FloatViewManager(final Context context) {

        this.context = context;
        //通过上下文对象去获取窗口管理的服务
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        floatCirecleView = new FloatCirecleView(context);
        floatMenuView = new FloatMenuView(context);
        floatCirecleView.setOnTouchListener(cirecleViewTouchListener);
        floatCirecleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏cricleView 显示菜单栏  开启动画
                windowManager.removeView(floatCirecleView);
                showFloatMenuView();
                floatMenuView.startAnimation();


            }
        });
    }

    /**
     * 显示菜单
     */
    private void showFloatMenuView() {

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = getScreenWidth();

        params.height = getScreenHeight() - getStatusHeight();
        //设置显示位置在左上偏移的位置
        params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        params.type = WindowManager.LayoutParams.TYPE_PHONE; //这样设置之后我们的浮动窗会一直显示在手机的上面
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; //让浮窗球不与其他的应用抢焦点
        params.format = PixelFormat.RGB_888;
        windowManager.addView(floatMenuView, params);
    }


    private static FloatViewManager manager;


    public static FloatViewManager getInstance(Context context) {
        if (null == manager) {
            manager = new FloatViewManager(context);


        }

        return manager;


    }

    /**
     * 展示浮窗小球到窗口上
     */
    public void showFloatCircleView() {
            if(null == params) {
                params = new WindowManager.LayoutParams();
                params.width = floatCirecleView.width;
                params.height = floatCirecleView.height;
                //设置显示位置在左上偏移的位置
                params.gravity = Gravity.TOP | Gravity.LEFT;
                params.x = 0;
                params.y = 0;
                params.type = WindowManager.LayoutParams.TYPE_PHONE; //这样设置之后我们的浮动窗会一直显示在手机的上面
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; //让浮窗球不与其他的应用抢焦点
                params.format = PixelFormat.RGB_888;

            }

        windowManager.addView(floatCirecleView, params);


    }


    /**
     * 隐藏菜单方法
     */
    public void hideFloatMenuView() {

        windowManager.removeView(floatMenuView);

    }
}

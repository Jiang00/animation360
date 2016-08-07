package com.imooc.animation360.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.imooc.animation360.R;
import com.imooc.animation360.engine.FloatViewManager;

/**
 * Created by Administrator on 2016/8/7.
 * 浮动菜单界面
 */
public class FloatMenuView extends LinearLayout {

    /**
     * 移动时的动画
     **/
    private TranslateAnimation animation;

    public FloatMenuView(Context context) {
        super(context);
        View root = View.inflate(getContext(), R.layout.float_menu_view, null);
        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.ll);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(500);
        animation.setFillAfter(true);
        linearLayout.setAnimation(animation);
        root.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //隐藏菜单栏
                FloatViewManager floatViewManager  =  FloatViewManager.getInstance(getContext());
                floatViewManager.hideFloatMenuView(); //隐藏菜单
                floatViewManager.showFloatCircleView(); //显示浮窗小球

                return false;
            }
        });

        addView(root);

    }

    public FloatMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 开始显示菜单,包含动画效果
     */
    public void startAnimation() {

        animation.start();

    }


}

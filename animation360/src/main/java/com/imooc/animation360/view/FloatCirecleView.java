package com.imooc.animation360.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.imooc.animation360.R;

/**
 * Created by Administrator on 2016/8/5.
 * 绘制浮窗小球
 */
public class FloatCirecleView extends View {


    //设定浮窗小球的宽高
    public int width = 200;
    public int height = 200;
    /**
     * 绘制圆形的画笔
     */
    private Paint criclePaint;
    /**
     * 绘制文字的画笔
     */
    private Paint textPaint;
    private String text = "50%";
    /**
     * 浮动球当前是否被拖拽
     */
    private boolean drag = false;
    /**
     * 滑动浮动球时候替换的图片
     */
    private Bitmap bitmap;

    public FloatCirecleView(Context context) {
        super(context);
        initPaints();

    }

    public FloatCirecleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatCirecleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaints() {

        criclePaint = new Paint();
        criclePaint.setColor(Color.GRAY);
        criclePaint.setAntiAlias(true);


        textPaint = new Paint();
        textPaint.setTextSize(25);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
        bitmap = Bitmap.createScaledBitmap(srcBitmap, width, height, true); //用Bitmap的静态方法缩放图片到我们设置的宽高


    }

    public FloatCirecleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (drag) {
            canvas.drawBitmap(bitmap, 0, 0, null); //绘制拖动时候的小图图片


        } else {

            canvas.drawCircle(width / 2, height / 2, width / 2, criclePaint);
            //通过画笔测量出文本内容的宽度
            float textWidth = textPaint.measureText(text);
            //绘制文字的基线的x值为圆形控件的一半减去文字内容宽度的一半
            float x = width / 2 - textWidth / 2;

            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float dy = -(fontMetrics.descent + fontMetrics.ascent) / 2;
            float y = height / 2 + dy;
            canvas.drawText(text, x, y, textPaint);

        }
    }

    public void setDragState(boolean dragState) {

        this.drag = dragState;
        invalidate(); //刷新当前的View，去onDraw方法里重新绘制要展示的内容
    }
}


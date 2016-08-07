package com.imooc.animation360.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 底部加速球
 * Created by Administrator on 2016/8/6.
 */
public class MyProgressView extends View {
    private static final String TAG = MyProgressView.class.getName();
    /**
     * 固定的宽高
     **/
    private int width = 300;
    private int height = 300;
    private Bitmap bitmap;
    /**
     * 画布
     **/
    private Canvas bitmapCanvas;

    /**
     * 绘制圆形的画笔
     */
    private Paint circlePaint;
    /**
     * 绘制文字的画笔
     */
    private Paint textPain;

    /**
     * 绘制进度条的画笔
     */
    private Paint progressPaint;

    public Path path = new Path();
    /**
     * 控件要达到的目标进度
     **/
    private int progress = 50; //当前的进度
    /**
     * 最大值
     **/
    private int max = 100;  //最大值
    /**
     * 当前的进度
     **/
    private int currentProgress = 0;

    /**
     * 手势监听对象
     */
    private GestureDetector detector = null;

    private int count = 50;
    /**
     * 是否是单击
     **/
    private boolean isSingleTap = false;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    public MyProgressView(Context context) {
        super(context);
        initPaints();

    }

    private void initPaints() {

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true); //抗锯齿
        circlePaint.setColor(Color.argb(0xff, 0x3a, 0x8c, 0x6c)); //画笔颜色

        //画进度的画笔
        progressPaint = new Paint();
        circlePaint.setAntiAlias(true); //抗锯齿
        progressPaint.setColor(Color.argb(0xff, 0x4e, 0xc9, 0x63));
        progressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        textPain = new Paint();
        textPain.setAntiAlias(true);
        textPain.setColor(Color.WHITE);
        textPain.setTextSize(25);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //创建画布
        bitmapCanvas = new Canvas(bitmap);


        detector = new GestureDetector(new GestureDetectorListener());
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return detector.onTouchEvent(motionEvent);
            }
        });
        setClickable(true);  //这样才可以接收到控件的双击和单机事件


    }

    /**
     * 自己创建一个手势捕捉类
     */
    class GestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(getContext(), "双击啦", Toast.LENGTH_SHORT).show();
            startDoubleTapAnimation();
            return super.onDoubleTap(e);
        }

        private void startDoubleTapAnimation() {
            handler.postDelayed(doubleTapRunnbale, 50);

        }

        private DoubleTapRunnable doubleTapRunnbale = new DoubleTapRunnable();

        class DoubleTapRunnable implements Runnable {


            @Override
            public void run() {
                currentProgress++;
                if (currentProgress <= progress) {
                    invalidate(); //更新控件
                    handler.postDelayed(doubleTapRunnbale, 50);
                } else {
                    handler.removeCallbacks(doubleTapRunnbale);
                    currentProgress = 0;


                }

            }
        }


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            Toast.makeText(getContext(), "单击啦", Toast.LENGTH_SHORT).show();
            isSingleTap = true;
            currentProgress = progress; //当前的进度等于目标进度
            startSingleTapAnimation();
            return super.onSingleTapConfirmed(e);
        }

        private void startSingleTapAnimation() {
            handler.postDelayed(singleTapRunnable, 200);


        }

        private SingleTapRunnable singleTapRunnable = new SingleTapRunnable();

        class SingleTapRunnable implements Runnable {


            @Override
            public void run() {

                count--;
                if (count >= 0) { //就代表50次还没有绘制完成
                    invalidate();
                    handler.postDelayed(singleTapRunnable, 200);


                } else {
                    //绘制任务执行完毕
                    handler.removeCallbacks(singleTapRunnable);
                    count = 50;
                }
            }
        }
    }


    public MyProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public MyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        bitmapCanvas.drawCircle(width / 2, height / 2, width / 2, circlePaint);
        path.reset(); //每次绘制的时候都需要将其reset一下
        float y = (1 - (float) currentProgress / max) * height;
        path.moveTo(width, y);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.lineTo(0, y);
        if (!isSingleTap) {

            float d = (1 - ((float) currentProgress / progress)) * 10;
            //绘制贝塞尔曲线
            for (int i = 0; i < 9; i++) {
                // Log.i(TAG, "onDraw: d"+d);
                path.rQuadTo(10, -d, 20, 0);
                path.rQuadTo(10, d, 20, 0);

            }

        } else {
            //这里是为了实现水面波纹效果
            float d = (float) count / 50 * 10; //这个d作为曲线的整幅应该是递减的

            if (count % 2 == 0) {

                for (int i = 0; i < 5; i++) {

                    path.rQuadTo(20, -d, 40, 0);
                    path.rQuadTo(20, d, 40, 0);

                }

            } else { //将上下的位置颠倒一下

                for (int i = 0; i < 5; i++) {
                    path.rQuadTo(20, d, 40, 0);
                    path.rQuadTo(20, -d, 40, 0);


                }


            }

        }

        path.close();
        bitmapCanvas.drawPath(path, progressPaint);
        String text = (int) (((float) currentProgress / max) * 100) + "%";
        float textWidth = textPain.measureText(text);
        Paint.FontMetrics fontMetrics = textPain.getFontMetrics();
        float baseLine = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;
        bitmapCanvas.drawText(text, width / 2 - textWidth / 2, baseLine, textPain);
        canvas.drawBitmap(bitmap, 0, 0, null);
        super.onDraw(canvas);
    }
}

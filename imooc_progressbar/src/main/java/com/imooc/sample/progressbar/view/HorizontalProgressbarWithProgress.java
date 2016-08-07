package com.imooc.sample.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.imooc.sample.progressbar.R;

/**
 * Created by Administrator on 2016/5/23.
 */
public class HorizontalProgressbarWithProgress extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 10;//SP

    private static final int DEFAULT_TEXT_COLOR = 0xFFC00D1;

    private static final int DEFAULT_COLOR_UNREACH = 0xFFD3D6DA;

    private static final int DEFAULT_HEIGHT_UNREACH = 2;//SP

    private static final int DEFAULT_COLOR_REACH = DEFAULT_COLOR_UNREACH;//SP

    private static final int DEFAULT_HEIGHT_REACH = 2;//SP

    /**
     * 默认的文字位移的高度
     **/
    private static final int DEFAULT_TEXT_OFFSET = 10;//SP


    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mUnReachColor = DEFAULT_COLOR_UNREACH;
    private int mUnReachHeight = dp2px(DEFAULT_HEIGHT_UNREACH);
    private int mReachColor = DEFAULT_COLOR_REACH;
    private int mReachHeight = dp2px(DEFAULT_HEIGHT_REACH);
    private int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    private Paint mPaint = new Paint();
    /**
     * 当前控件的宽度减去控件的padding值，获得的真实的宽度
     **/
    private int mRealWidth;


    public HorizontalProgressbarWithProgress(Context context) {
        this(context, null);
    }

    public HorizontalProgressbarWithProgress(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public HorizontalProgressbarWithProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        obtainStyledAttrs(attrs);
    }


    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressbarWithProgress);
        //获取定义的结果,获取不到则使用原先初始化的默认值
        mTextSize = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_text_size, mTextSize);

        mTextColor = ta.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_text_color, mTextColor);
        mTextOffset = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_text_offset, mTextOffset);

        mUnReachColor = ta.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_unreach_color, mUnReachColor);

        mUnReachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_unreach_height, mUnReachHeight);

        mReachColor = ta.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_reach_color, mReachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_reach_height, mReachHeight);



        ta.recycle();

        mPaint.setTextSize(mTextSize);


    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 获取测量模式 我们这里需要的是用户传递的一个宽度的确定的值
        //int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取宽度值
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);

        int height = measureHeight(heightMeasureSpec);

        setMeasuredDimension(widthVal, height);

        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();


    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) //如果用户给的精确值则使用精确值
        {
            result = size;

        } else {
            //获得文字的高度
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            result = getPaddingTop() + getPaddingBottom() + Math.max(Math.max(mReachHeight, mUnReachHeight), Math.abs(textHeight));

            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);

            }

        }

        return 0;


    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //思路先绘制前两个部分,然后根据前两部分的宽度确定需不需要绘制第三部分.锁定画布
        canvas.save();
        //移动原点
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        boolean noNeedUnRech = false;

        String text = getProgress() + "%";
        //占用的百分比
        float radio = getProgress() * 1.0f / getMax();
        //获取文本的宽度
        int textWidth = (int) mPaint.measureText(text);
        float progressX = radio * mRealWidth;
        //占用的进度的宽度加上文字的宽度已经大于整个进度的宽度就可以再增加了
        if (progressX + textWidth > mRealWidth) {
            progressX = mRealWidth - textWidth;
            noNeedUnRech = true;

        }

        float endX = radio * mRealWidth - mTextOffset / 2;
        if (endX > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            //绘出这条线
            canvas.drawLine(0, 0, endX, 0, mPaint);


        }

        //draw text
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent() + mPaint.ascent()) / 2);
        canvas.drawText(text,progressX,y,mPaint);

        //draw unReach bar
        if(!noNeedUnRech){
            float start = progressX +mTextOffset/2 + textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);

        }

        //绘制完成之后执行一下.
        canvas.restore();


    }

    private int dp2px(int dpVal) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());


    }


    private int sp2px(int spVal) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());


    }


}

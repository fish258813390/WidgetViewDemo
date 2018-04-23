//package com.neil.demo.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.View;
//
//import com.neil.demo.R;
//
//import java.util.HashSet;
//import java.util.Random;
//import java.util.Set;
//
///**
// * 自定义View系列
// * 详解系列
// * Created by neil on 2018/4/23 0023.
// */
//public class CustomTitleView extends View {
//
//    private String mTitleText; // 文本
//    private int mTitleTextColor; // 文本的颜色
//    private int mTitleTextSize; // 文本的大小
//    private Paint mPaint;
//    private Rect mBound;
//
//    public CustomTitleView(Context context) {
//        this(context, null);
//    }
//
//    public CustomTitleView(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CustomTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
//        for (int i = 0; i < a.length(); i++) {
//            int attr = a.getIndex(i);
//            switch (attr) {
//                case R.styleable.CustomTitleView_titleText:
//                    mTitleText = a.getString(attr);
//                    break;
//
//                case R.styleable.CustomTitleView_titleTextColor:
//                    mTitleTextColor = a.getColor(attr, Color.BLACK); // 默认颜色设置为黑色
//                    break;
//
//                case R.styleable.CustomTitleView_titleTextSize:
//                    // 默认设置为16sp，TypeValue也可以把sp转化为px
//                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
//                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
//                    break;
//            }
//        }
//        a.recycle();
//        mPaint = new Paint();
//        mPaint.setTextSize(mTitleTextSize);
//
//        mBound = new Rect();
//        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);   // 获取文字的宽、高
//
//        Log.d("CustomTitleView", "mBounds.width()--->" + mBound.width());
//        Log.d("CustomTitleView", "mBounds.height()--->" + mBound.width());
//        Log.d("CustomTitleView", "------------------------------");
//        init();
//    }
//
//    private void init() {
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mTitleText = randomText();
//                postInvalidate();
//            }
//        });
//    }
//
//    private String randomText() {
//        Random random = new Random();
//        Set<Integer> set = new HashSet<>();
//        while (set.size() < 4) {
//            int randomInet = random.nextInt(10);
//            set.add(randomInet);
//        }
//        StringBuffer sb = new StringBuffer();
//        for (Integer i : set) {
//            sb.append(i + "");
//        }
//        return sb.toString();
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width;
//        int height;
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = widthSize;
//        } else {
//            mPaint.setTextSize(mTitleTextSize);
//            // 计算了描绘字体需要的范围
//            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound); // 获取文字的宽、高
//            float textWidth = mBound.width();
//            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
//            width = desired;
//        }
//
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        } else {
//            mPaint.setTextSize(mTitleTextSize);
//            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
//            float textHeight = mBound.height();
//            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
//            height = desired;
//        }
//        setMeasuredDimension(width, height);
//        Log.d("CustomTitleView", "widthMode:" + widthMode);
//        Log.d("CustomTitleView", "widthSize:" + widthSize);
//        Log.d("CustomTitleView", "heightMode:" + heightMode);
//        Log.d("CustomTitleView", "heightSize:" + heightSize);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
//        mPaint.setColor(mTitleTextColor);
//        /**
//         * 参数一: 要绘制的文字
//         * 参数二：绘制原点x坐标
//         * 参数三：绘制原点y坐标  y所代表的是基线的位置！
//         *  paint: 用来做画的画笔
//         */
//        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
//        Log.d("CustomTitleView", "getWidth()--->" + getWidth());
//        Log.d("CustomTitleView", "getHeight()--->" + getHeight());
//    }
//}

package com.neil.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.neil.demo.R;

/**
 * Created by neil on 2018/4/9 0009.
 */
public class CircleView extends View {

    private Paint mPaint1;
    private int mColor;

    public CircleView(Context context) {
        this(context, null);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 加载自定义属性集合CircleView  typedArray
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);

        // 解析集合中的属性circle_color属性
        // 该属性的id为：R.styleable.Circle_circle_color
        // 将解析的属性传入到画圆的画笔 颜色变量中(本质上是自定义画圆画笔的颜色)
        // 第二个参数是默认设置颜色(没有指定circle_color情况下使用)
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED);

        // 解析后 释放资源
        typedArray.recycle();
        init();
    }

    /**
     * 画笔初始化
     */
    private void init() {
        mPaint1 = new Paint();
        mPaint1.setColor(mColor); // 传入颜色
        mPaint1.setStrokeWidth(5f);
        mPaint1.setStyle(Paint.Style.FILL);
    }

    // 复写onDraw()进行绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();


        // 获取控件的
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        // 设置圆的半径 = 宽/高最小值的1/2
        int r = Math.min(width, height) / 2;
        // 画出圆(蓝色)
        // 圆心 = 控件中央
        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, r, mPaint1);
    }
}

package com.neil.demo.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.neil.demo.entity.PieData;

import java.util.ArrayList;

/**
 * <p> 1.构造函数 初始化画笔paint
 * <p> 2. onMeasure() 测量View的大小
 * <p> 3. onSizeChanged 确定View大小(记录当前View的宽高)
 * <p> 4.onLayout() 确定子View布局(无子View,不关心)
 * <p> 5.onDraw() 实际绘制内容(本例中 绘制饼状图)
 * <p> 6.提供外界接口 设置数据
 * Created by neil on 2017/11/21 0021.
 */

public class PieView extends View {

    // 颜色表
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private ArrayList<PieData> mData;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public PieView(Context context) {
        super(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

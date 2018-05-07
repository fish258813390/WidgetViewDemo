package com.neil.demo.widget.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * 【绘图篇】 基本几何图形
 * <p>
 * void drawPoint (float x, float y, Paint paint) 点
 * 参数：float X: 点的X坐标，float Y：点的Y坐标
 * </p>
 * <p>
 * RectF 与 Rect 矩形工具类
 * RectF(float left, float top, float right, float bottom)
 * 根据矩形的4个点画出矩形
 * </p>
 * <p>
 * Created by neil on 2018/4/27 0027.
 */
public class MyView extends View {

    private Context mContext;


    public MyView(Context context) {
        super(context);
        mContext = context;
    }

    // 重写onDraw()函数，在每次重绘时自主实现绘图
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置画笔基本属性
        Paint paint = new Paint();

        // 圆
//        paint.setAntiAlias(true); // 抗锯齿功能
//        paint.setColor(Color.RED); // 画笔颜色
//        paint.setStyle(Paint.Style.FILL_AND_STROKE); // 填充样式 Paint.Style.STROKE 、 Paint.Style.FILL 、Paint.Style.FILL_AND_STROKE
//        paint.setStrokeWidth(5); // 设置画笔宽度
//        paint.setShadowLayer(10, 15, 15, Color.GREEN); // 设置阴影
//        canvas.drawRGB(255, 255, 255); // 设置画布背景
//        canvas.drawCircle(190, 200, 150, paint); // 画圆

        // 点
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);
//        canvas.drawPoint(20, 100, paint);

        // 矩形
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawRect(10, 10, 100, 100, paint);

        RectF rect = new RectF(120, 10, 210, 100);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, paint);
        Rect rect2 = new Rect(230, 10, 320, 100);
        canvas.drawRect(rect2, paint);


    }
}

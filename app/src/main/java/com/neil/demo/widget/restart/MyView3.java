package com.neil.demo.widget.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * 【绘图篇】 drawText()详解
 * 1.drawText中的参数Y是基线的位置
 * 2.一定要清楚的是，只要X坐标、基线位置、文字大小确定以后，文字的位置就是确定的了。
 * <p>
 * paint.setTextAlign(Paint.Align.Left); 相对位置是根据所要绘制文字所在矩形来计算的
 * <p>
 * <p>
 * Created by neil on 2018/4/28
 */
public class MyView3 extends View {


    public MyView3(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        int baseLineX = 0;
//        int baseLineY = 300;
//        String str = "我是张三";
//
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//
//        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);
//
//        paint.setColor(Color.GREEN);
//        paint.setTextSize(100);
//        /**
//         * text：要绘制的文字
//         * x:绘制原点x坐标
//         * y:绘制原点y坐标 基线的位置
//         * paint:画笔
//         */
//        paint.setTextAlign(Paint.Align.LEFT);
//        canvas.drawText(str, baseLineX, baseLineY, paint);

        // [最小矩形]
        String text = "harvic\'s blog";
        int baseLineY = 200;
        int baseLineX = 0;

        Paint paint = new Paint();
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);

        // 字符串所占高度和宽度:bottom线所在位置的Y坐标减去top线所在位置的Y坐标就是字符串所占的高度
        // 画text所占的区域
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int top = baseLineY + fm.top;
        int bottom = baseLineY + fm.bottom;
        // 一、所在高度
        int height = bottom - top;
        // 画top线
//        paint.setColor(Color.YELLOW);
//        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        // 二、宽度
        int width = (int) paint.measureText(text);
        System.out.println("height:" + height + ",width:" + width);
        // text所占的区域
        Rect rect = new Rect(baseLineX, top, baseLineX + width, bottom);
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect, paint);

        // 三、最小矩形
        Rect minRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), minRect);
        minRect.top = baseLineY + minRect.top;
        minRect.bottom = baseLineY + minRect.bottom;
        paint.setColor(Color.RED);
        System.out.println("最小矩形：" + minRect.toShortString());
        canvas.drawRect(minRect, paint);

        // 写文字
        paint.setColor(Color.BLACK);
        canvas.drawText(text, baseLineX, baseLineY, paint);
    }


}

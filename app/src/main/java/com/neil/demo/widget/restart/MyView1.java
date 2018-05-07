package com.neil.demo.widget.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * 【绘图篇】 路径及文字
 * <p>
 * void drawPath (Path path, Paint paint)
 * 直线路径
 * </p>
 * void addRect (RectF rect, Path.Direction dir)
 * 矩形路径
 * </p>
 * Created by neil on 2018/4/27
 */
public class MyView1 extends View {

    Context mContext;

    public MyView1(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5); // 设置画笔宽度
        paint.setStyle(Paint.Style.STROKE); // 设置为描边
        // 1.直线路径
//        Path path = new Path();
//        path.moveTo(20, 20);
//        path.lineTo(20, 100);
//        path.lineTo(300, 100);
//        path.lineTo(500, 100);
//        path.close(); // 闭环
//        canvas.drawPath(path, paint);

        // 2.矩形路径
//        Path CWRectpath = new Path();
//        RectF rect2 = new RectF(50, 50, 240, 200);
//        CWRectpath.addRect(rect2, Path.Direction.CW);
//        canvas.drawPath(CWRectpath, paint);
//        // 根据路径写出文字
//        String text = "hello world!";
//        paint.setColor(Color.GRAY);
//        paint.setTextSize(30);
//        // hOffset 文字开始的位置
//        // vOffset  The distance above(-) or below(+) the path to position the text
//        canvas.drawTextOnPath(text, CWRectpath, 10, -18, paint);

        // 3.圆角矩形路径

        // 4.圆形路径
//        Path path = new Path();
//        path.addCircle(300, 300, 100, Path.Direction.CW);
//        canvas.drawPath(path, paint);

        // 5.椭圆路径
//        Path path = new Path();
//        RectF rect = new RectF(50, 50, 240, 100);
//        path.addOval(rect, Path.Direction.CW);
//        canvas.drawPath(path, paint);

        // 绘制文字
        // Paint 相关设置
        paint.setStrokeWidth(5); // 设置画笔宽度
        paint.setAntiAlias(true); // 指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(70);

        // 1. 绘制样式，设置为填充
        paint.setStyle(Paint.Style.FILL);
        paint.setStrikeThruText(true); // 设置带有删除线效果
        canvas.drawText("Hello World!", 10, 100, paint);
        //绘图样式设置为描边
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText("Hello World!", 10, 200, paint);
        //绘图样式设置为填充且描边
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setUnderlineText(true); // 设置下划线
        paint.setTextSkewX((float) -0.25); // 文字倾斜程度
        paint.setFakeBoldText(true); // 设置是否粗体
        canvas.drawText("Hello World!", 10, 300, paint);


    }
}

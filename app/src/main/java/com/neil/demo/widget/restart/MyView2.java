package com.neil.demo.widget.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * 【绘图篇】canvas变换与操作
 * canvas中有一个函数translate()是用来实现画布平移的，画布的原状是以左上角为原点，向右是X轴正反向，向下是Y轴正方向
 * 总结：
 * 1.每次调用canvas.drawXXX系列函数来进行绘图，都会产生一个全新的Canvas画布
 * 2.如果在DrawXXX前，调用平移、旋转等函数来对Canvas进行了操作，那么这个操作时不可逆的，
 * 每次产生的画布的最新位置都是这些操作后的位置。(save()、Restore()可以对画布进行保存和恢复)
 * 3.在Canvas与屏幕合成时，超出屏幕范围的图片是不会显示出来的。
 * <p>
 * save()：每次调用Save()函数都会把当前的画布的状态进行保存，然后放入特定的栈中;
 * restore()：没当调用Restore()函数，就会把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布，并在这个画布上做画
 * <p>
 * Created by neil on 2018/4/27
 */
public class MyView2 extends View {

    public MyView2(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 3);
//        Paint paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 3);
//
//        // 构造一个矩形
//        Rect rect1 = new Rect(300, 50, 400, 200);
//        // 在画布平移前用绿色画下边框
//        canvas.drawRect(rect1, paint_green);
////        canvas.translate(150, 150);
//        canvas.rotate(45); // 顺时针旋转画布
//        canvas.drawRect(rect1, paint_red); // 画布平移后，用红色画边框


        // save()、restore()
//        canvas.drawColor(Color.RED);
//        canvas.save(); // 保存当前画布大小即整屏
//        canvas.clipRect(new Rect(100, 100, 800, 800));
//        canvas.drawColor(Color.GREEN);
//        canvas.restore(); // 恢复整屏画布
//        canvas.drawColor(Color.BLUE);

        //



    }

    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }
}

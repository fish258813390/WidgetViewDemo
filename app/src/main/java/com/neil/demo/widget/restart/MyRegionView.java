package com.neil.demo.widget.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.view.View;

/**
 * 【绘图篇】区域(Range)
 *  https://blog.csdn.net/harvic880925/article/details/39056701
 * Created by neil on 2018/4/27 0027.
 */
public class MyRegionView extends View {

    public MyRegionView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        Region rgn = new Region(10, 10, 100, 100);

        rgn.set(100, 100, 200, 200);
        drawRegion(canvas,rgn,paint);
    }

    //这个函数不懂没关系，下面会细讲
    private void drawRegion(Canvas canvas, Region rgn, Paint paint) {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();

        while (iter.next(r)) {
            canvas.drawRect(r, paint);
        }
    }

}

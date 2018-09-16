package com.neil.demo.widget.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yujin on 2018/9/10 0010
 */

public class CustomView1 extends View {

    private Paint mPaint;
    private String str = "hello Word!";
    private Rect bounds;
    private int offsetX = 100;
    private int offsetY = 100;

    public CustomView1(Context context) {
        super(context);
        init();
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        bounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(str, offsetX, offsetY, mPaint);

        mPaint.getTextBounds(str, 0, str.length(), bounds);
        bounds.left += offsetX;
        bounds.top += offsetY;
        bounds.right += offsetX;
        bounds.bottom += offsetY;

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, mPaint);
    }
}

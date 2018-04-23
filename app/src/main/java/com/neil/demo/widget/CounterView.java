package com.neil.demo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自动计数 View
 * Created by neil on 2018/4/23 0023.
 */

public class CounterView extends View implements View.OnClickListener {

    private Paint mPaint;
    private Rect mBounds;
    private int mCount; // 记数值，每点击一次控件，值加1
    private int mWidth; // 空间宽度
    private int mHeight; // 控件高度

    public CounterView(Context context) {
        super(context);
        init();
    }

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(Color.BLUE);
        mBounds = new Rect();
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        Log.d("CounterView","mWidth--->" + mWidth);
        Log.d("CounterView","mHeight--->" + mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(50);
        String text = String.valueOf(mCount);
        // 获取文字的宽、高
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();
        canvas.drawText(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 2, mPaint);
        Log.d("CounterView","getWidth()--->" + getWidth());
        Log.d("CounterView","getHeight()--->" + getHeight());
        Log.d("CounterView","mBounds--->" + mBounds.toString().replace("-",""));
        Log.d("CounterView","mBounds.width()--->" + textWidth);
        Log.d("CounterView","mBounds.height()--->" + textHeight);
    }

    @Override
    public void onClick(View v) {
        mCount ++;
        // 重绘
        invalidate();
    }
}

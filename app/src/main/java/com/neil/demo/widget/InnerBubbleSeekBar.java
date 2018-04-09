package com.neil.demo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.neil.demo.R;

/**
 * Seekbar上显示滑动值
 * Created by neil on 2018/4/9 0009.
 */
public class InnerBubbleSeekBar extends android.support.v7.widget.AppCompatSeekBar {

    private TextPaint mTextPaint; // 文本画笔
    private String mText; // 文本内容
    private Rect mTextBound; // 文本所在的矩阵
    private int mWidth; // 空间宽度
    private int mHeight; // 控件高度

    public InnerBubbleSeekBar(Context context) {
        this(context, null);
    }

    public InnerBubbleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerBubbleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mTextPaint = new TextPaint(Paint.FAKE_BOLD_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#333333"));
        mTextPaint.setTextSize(20);
        mTextBound = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mText = getProgress() + "%";
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

        Rect bounds = this.getProgressDrawable().getBounds();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.button_slide, opts);
        int width = mBitmap.getWidth();

//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(getResources(), R.mipmap.button_slide, opts);
//        opts.inSampleSize = 1;
//        opts.inJustDecodeBounds = false;
//        Bitmap mBitmap =BitmapFactory.decodeResource(getResources(), R.mipmap.button_slide, opts);
//        int width = mBitmap.getWidth();

        float xText = (bounds.width() - width / 2) * getProgress() / getMax() + width / 2 - mTextBound.width() / 2 - 15;
        float yText = mHeight / 2 + mTextBound.height() / 3;
        canvas.drawText(mText, xText, yText, mTextPaint);
    }
}

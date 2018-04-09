package com.neil.demo.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;

import com.neil.demo.R;

/**
 * 带气泡显示的 seekbar
 * Created by neil on 2018/4/9 0009.
 */
public class BubbleSeekBar extends AppCompatSeekBar {
    private final String TAG = BubbleSeekBar.class.getName();
    private Resources res;
    private Bitmap mBitmap; // 气泡背景图片
    private float mImgWidth; // 宽度
    private float mImgHeight; // 高度
    private Paint mPaint; // 画笔 在气泡背景下显示文字
    private String mText; // 文字内容
    private float mTextWidth; // 文字内容宽度
    private int textSize = 35; // 文字大小
    private int oldPaddingTop = 0;
    private int oldPaddingBottom = 0;
    private int oldPaddingLeft = 50;
    private int oldPaddingRight = 50;
    private boolean isMysetPadding = true;

    private int textpaddingleft;
    private int textpaddingtop;
    private int imagepaddingleft;
    private int imagepaddingtop;

    private Drawable mThumbDrawable; // seekBar bar

    public BubbleSeekBar(Context context) {
        this(context, null);
        init();
    }

    public BubbleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public BubbleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        res = getResources();
        // 加载气泡背景
        mBitmap = BitmapFactory.decodeResource(res, R.mipmap.icon_slid);
        if (mBitmap != null) {
            mImgWidth = mBitmap.getWidth();
            mImgHeight = mBitmap.getHeight();
        } else {
            mImgWidth = 0;
            mImgHeight = 0;
        }

        // 初始化paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(textSize);
        mPaint.setColor(res.getColor(android.R.color.white));

        // 设置边距
        setPadding();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mText = getProgress() + "%";
        mTextWidth = mPaint.measureText(mText);
        Rect bounds = this.getProgressDrawable().getBounds();
        Log.d("TAG", bounds.toString());
        float xImg = bounds.width() * getProgress() / getMax() - oldPaddingLeft + 10;
        float yImg = imagepaddingtop + oldPaddingTop;
        float xText = bounds.width() * getProgress() / getMax() + mImgWidth / 2
                - mTextWidth / 2 + textpaddingleft - oldPaddingLeft + 10;
        float yText = yImg + textpaddingtop + mImgHeight / 2 + getTextHeight() / 4 - 5;
        canvas.drawBitmap(mBitmap, xImg, yImg, mPaint);
        canvas.drawText(mText, xText, yText, mPaint);
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumbDrawable = thumb;
    }

    private void setPadding() {
        int top = getBitmapHeight() + oldPaddingTop;
        int left = oldPaddingLeft;
        int right = oldPaddingRight;
        int bottom = oldPaddingBottom;
        isMysetPadding = true;
        setPadding(left, top, right, bottom);
        isMysetPadding = false;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (isMysetPadding) {
            super.setPadding(left, top, right, bottom);
        }
    }

    private float getTextHeight() {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.top) + 2;
    }

    /**
     * 获取背景图的高度 取整
     *
     * @return
     */
    private int getBitmapHeight() {
        return (int) Math.ceil(mImgHeight);
    }

}

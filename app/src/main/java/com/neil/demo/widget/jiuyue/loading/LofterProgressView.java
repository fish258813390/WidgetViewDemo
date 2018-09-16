package com.neil.demo.widget.jiuyue.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.neil.demo.R;

/**
 * 加载进度框
 * 1.背景:一个圆角矩形; 2.进度条：一条圆弧; 3.百分比：纯文字
 * Created by yujin on 2018/9/16
 */
public class LofterProgressView extends View {

    private int mBgWidth; // 背景圆角矩形的宽度
    private int mBgColor; // 背景圆角矩形的颜色
    private int mBgCornerRadius; // 圆角矩形的圆角半径

    private int mInnerRadius; // 内圆的半径
    private int mInnerCorlor; // 内圆的颜色
    private int mEdgeRadius; // 最外层的半径(内圆半径+进度条的宽度)
    private int mEdgeColor; // 最外层的颜色
    private int mRingWidth; // 圆环(会动的进度条的宽度)
    private int mRingColor; // 圆环的颜色(进度条的颜色)

    private int mPercentColor; // 百分比的颜色
    private int mPercentSize; // 百分比的大小

    private Paint mInnerPaint; // 内圆画笔
    private Paint mRingPaint; // 圆环(进度条)画笔
    private Paint mEdgePaint; // 最外层画笔
    private Paint mPercentPaint; // 百分数文字画笔
    private Paint mBgPaint; // 背景(圆角矩形)画笔

    private float mTextWidth; // 百分比文字的宽度
    private float mTextHeight; // 百分比文字的高度

    private int centerX; // 空间中心的X轴位置
    private int centerY; // 控制中心的Y轴位置

    private RectF mBgRect; // 背景圆角矩形
    private RectF mOval; // 圆弧外切的矩形

    private int mCurrentProgress = 0; // 当前进度
    private int mTotalProgress = 1; // 总进度
    private int mPercent; // 百分比(外部直接设置百分比)


    public LofterProgressView(Context context) {
        this(context, null);
    }

    public LofterProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LofterProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intAttrs(context, attrs);
        initProSetting();
    }

    /**
     * 初始化自定义属性的配置
     *
     * @param context
     * @param attrs
     */
    private void intAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        mBgWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressView_bgWidth, 210);
        mBgColor = typedArray.getColor(R.styleable.ProgressView_bgColor, Color.WHITE);
        mBgCornerRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressView_bgCornerRadius, 28);
        mInnerCorlor = typedArray.getColor(R.styleable.ProgressView_innerColor, Color.WHITE);
        mInnerRadius = typedArray.getDimensionPixelSize(R.styleable.ProgressView_innerRadius, 50);

        mEdgeColor = typedArray.getColor(R.styleable.ProgressView_edgeColor, getResources().getColor(R.color.default_edge_color));
        mRingWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressView_ringWidth, 5);
        mRingColor = typedArray.getColor(R.styleable.ProgressView_ringColor, getResources().getColor(R.color.default_ring_color));

        mPercentColor = typedArray.getColor(R.styleable.ProgressView_ringColor, Color.GRAY);
        mPercentSize = typedArray.getDimensionPixelSize(R.styleable.ProgressView_percentSize, 30);

        typedArray.recycle();
    }

    /**
     * 初始化配置
     */
    private void initProSetting() {
        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setColor(mInnerCorlor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND); // 设置笔触为圆形
        mInnerPaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mRingPaint.setStrokeWidth(mRingWidth);
        mRingPaint.setStyle(Paint.Style.STROKE);

        mEdgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEdgePaint.setColor(mEdgeColor);
        mEdgePaint.setStrokeCap(Paint.Cap.ROUND);
        mEdgePaint.setStrokeWidth(mRingWidth);
        mEdgePaint.setStyle(Paint.Style.STROKE);

        // 字体的画笔
        mPercentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPercentPaint.setColor(mPercentColor);
        mPercentPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPercentPaint.setTextSize(mPercentSize);

        // 计算字体的高度
        Paint.FontMetrics fm = mPercentPaint.getFontMetrics();
        mTextHeight = (int) Math.ceil(fm.descent - fm.ascent);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);

        mOval = new RectF();
        mBgRect = new RectF();

        mEdgeRadius = mInnerRadius + mRingWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;

        // 计算背景bg圆角矩形的位置
        mBgRect.set(centerX - (mBgWidth / 2), centerY - (mBgWidth / 2), centerX + (mBgWidth / 2), centerY + (mBgWidth / 2));
        // 计算进度条圆形的 外切矩形的位置
        mOval.left = centerX - mInnerRadius;
        mOval.top = centerY - mInnerRadius;
        mOval.right = centerY + mInnerRadius;
        mOval.bottom = centerY + mInnerRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画背景
        canvas.drawRoundRect(mBgRect, mBgCornerRadius, mBgCornerRadius, mBgPaint);

        // 画内圆
        canvas.drawCircle(centerX, centerY, mInnerRadius, mInnerPaint);
        // 画外层的圆环,相当于进度为0时时候的显示
        canvas.drawCircle(centerX, centerY, mInnerRadius, mEdgePaint);

        float progress;
        String percentText;
        if (mPercent != 0) {
            progress = (float) mPercent / 100 * 360;
            percentText = mPercent + "%";
        } else {
            progress = mCurrentProgress / mTotalProgress * 360;
            percentText = (mCurrentProgress * 1.0f / mTotalProgress * 100) + "%";
        }

        // 画进度条圆环
        canvas.drawArc(mOval, -90, progress, false, mRingPaint);
        // 画比分比
        mTextWidth = mPercentPaint.measureText(percentText, 0, percentText.length());
        canvas.drawText(percentText, centerX - mTextWidth / 2, centerY + mTextHeight / 4, mPercentPaint);
    }

    /*******  设置外部接口  *********/

    /**
     * 设置当前进度
     *
     * @param progress 进度
     */
    public synchronized void setProgress(int progress) {
        this.mCurrentProgress = progress;
        postInvalidate();
    }

    /**
     * 设置总进度
     *
     * @param totalProgress 总进度
     */
    public synchronized void setTotalProgress(int totalProgress) {
        this.mTotalProgress = totalProgress;
    }

    /**
     * 直接从外部设置百分比
     *
     * @param percent 百分比
     */
    public synchronized void setPercent(int percent) {
        this.mPercent = percent;
        postInvalidate();
    }

}

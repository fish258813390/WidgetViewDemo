package com.neil.demo.widget;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.neil.demo.R;

/**
 * 自定义加载View
 * https://www.jianshu.com/p/67b69fc8b63b
 * Created by neil on 2018/4/10 0010.
 */
public class Kawaii_LoadingView extends View {

    // 固定方块 & 移动方块变量
    private FixedBlock[] mFixedBlock;
    private MoveBlock mMoveBlock;

    private Integer lineNumber; // 方块行数量
    private float half_BlockWidth; //半个方块的宽度
    private float blockInterval; // 方块间隔宽度
    private float moveBlock_Angle; //移动方块的圆角半径
    private float fixBlock_Angle; // 固定方块的圆角半径

    private Paint mPaint;
    private int blockColor;// 方块颜色
    private int initPosition; // 移动方块的初始位置(空白位置)
    private boolean isClock_Wise; // 动画方向是否 = 顺时针
    private int moveSpeed; // 移动方块的移动速度
    // 动画插值器（默认 = 线性）
    private Interpolator move_Interpolator;
    private AnimatorSet mAnimatorSet;
    private int mCurrEmptyPosition;


    public Kawaii_LoadingView(Context context) {
        this(context, null);
    }

    public Kawaii_LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Kawaii_LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 步骤1：初始化动画属性
        initAttrs(context, attrs);

        // 步骤2：初始化方块对象以及之间的关系
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        // 控件资源名称
        TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.Kawaii_LoadingView);

        // 方块行数量(最少3行)
        lineNumber = typedArray.getInteger(R.styleable.Kawaii_LoadingView_lineNumber, 3);
        if (lineNumber < 3) {
            lineNumber = 3;
        }
        // 半个方块的宽度 (dp)
        half_BlockWidth = typedArray.getDimension(R.styleable.Kawaii_LoadingView_half_BlockWidth, 30);
        // 方块间隔宽度
        blockInterval = typedArray.getDimension(R.styleable.Kawaii_LoadingView_blockInterval, 10);

        // 移动方块的圆角半径
        moveBlock_Angle = typedArray.getFloat(R.styleable.Kawaii_LoadingView_moveBlock_Angle, 10);
        // 固定方块的圆角半径
        fixBlock_Angle = typedArray.getFloat(R.styleable.Kawaii_LoadingView_fixBlock_Angle, 30);

        // 方块颜色
        int defaultColor = context.getResources().getColor(R.color.colorAccent); // 默认颜色
        blockColor = typedArray.getColor(R.styleable.Kawaii_LoadingView_blockColor, defaultColor);

        // 移动方块的初始位置(即空白位置)
        initPosition = typedArray.getInteger(R.styleable.Kawaii_LoadingView_initPosition, 0);

        // 由于移动方块只能是外部方块，所以这里需要判断方块是否属于外部方块 关注1
        if (isInsideTheRect(initPosition, lineNumber)) {
            initPosition = 0;
        }

        // 动画方向是否 = 顺时针
        isClock_Wise = typedArray.getBoolean(R.styleable.Kawaii_LoadingView_isClock_Wise, true);

        // 移动方块的移动速度
        // 注：不建议将速度设置过快,因为会导致ValueAnimator 动画对象频繁重复的创建，存在内存抖动。
        moveSpeed = typedArray.getInteger(R.styleable.Kawaii_LoadingView_moveSpeed, 250);

        // 设置移动方块动画的差值器
        int move_InterpolatorResId = typedArray.getResourceId(R.styleable.Kawaii_LoadingView_move_interpolator, android.R.anim.linear_interpolator);
        move_Interpolator = AnimationUtils.loadInterpolator(context, move_InterpolatorResId);

        // 当方块移动后，需要实时更新的空白方块的位置
        mCurrEmptyPosition = initPosition;

        // 释放资源
        typedArray.recycle();
    }

    /**
     * 初始化方块对象 及 之间的关系
     */
    private void init() {
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(blockColor);
        // 初始化方块对象 & 关系 --->
        initBlocks(initPosition);
    }

    /**
     * 初始化方块对象、之间的关系
     * 参数说明:initPosition = 移动方块的初始位置
     *
     * @param initPosition
     */
    private void initBlocks(int initPosition) {
        // 1.创建总方块的数量(固定方法) = lineNumber * lineNumber
        // lineNumber : 方块的行数；fixedBlock：固定方法 --> 关注2
        mFixedBlock = new FixedBlock[lineNumber * lineNumber];

        // 2.创建方块
        for (int i = 0; i < mFixedBlock.length; i++) {
            // 创建固定方块 并保存到数组中
            mFixedBlock[i] = new FixedBlock();
            // 对固定方块对象里的变量进行赋值
            mFixedBlock[i].index = i;
            // 对方块是否显示进行判断
            // 若方块的位置 = 移动方块的初始位置，则隐藏；否则显示
            mFixedBlock[i].isShow = initPosition == i ? false : true;
            mFixedBlock[i].rectF = new RectF();
        }
        // 3.创建移动的方块(1) ---> 关注3
        mMoveBlock = new MoveBlock();
        mMoveBlock.rectF = new RectF();
        mMoveBlock.isShow = false;

        // 4.关联外部方块的位置  ---> 关注四
        // 因为外部的方块序号 # 0、1、2...排列，通过next变量(指定其下一个)，一个接一个连接 外部方块 成圈
        relate_OuterBlock(mFixedBlock, isClock_Wise);
    }

    /**
     * 关注4: 将外部方块的位置关联起来
     * 算法思想：按照第1行，最后1行，第1列 & 最后1列的顺序，分别让每个外部方块的next属性 == 下一个外部方块的位置
     * 最终对整个外部方块的位置进行关联，注：需要考虑移动方向变量isClockWise(顺 or 逆时针)
     *
     * @param fixedBlocks
     * @param isClockwise
     */
    private void relate_OuterBlock(FixedBlock[] fixedBlocks, boolean isClockwise) {

    }


    /**
     * 判断方块是否在内部
     *
     * @param pos
     * @param lineCount
     * @return
     */
    private boolean isInsideTheRect(int pos, int lineCount) {
        // 判断方块是否在第1行
        if (pos < lineCount) {
            return false;
            // 是否在最后1行
        } else if (pos > (lineCount * lineCount - 1 - lineCount)) {
            return false;
            // 是否在最后1行
        } else if ((pos + 1) % lineCount == 0) {
            return false;
            // 是否在第1行
        } else if (pos % lineCount == 0) {
            return false;
        }
        // 若不在4边，则在内部
        return true;
    }

    // 固定方块 类
    private class FixedBlock {
        // 存储方块的坐标位置参数
        RectF rectF;

        // 方块对应序号
        int index;

        // 标志位：判断是否需要绘制
        boolean isShow;

        // 指向下一个需要移动的位置
        // 外部的方块序号 # 0、1、2....排列 通过next变量(指定其下一个) 一个接一个连接 外部方块 成圈
        FixedBlock next;

    }

    // 移动方块 类
    private class MoveBlock {
        // 存储方块的坐标位置参数
        RectF rectF;

        // 方块对应序号
        int index;

        // 标志位：判断是否需要绘制
        boolean isShow;

        // 旋转中心坐标
        // 移动时的旋转中心(X,Y)
        float cx;
        float cy;
    }
}

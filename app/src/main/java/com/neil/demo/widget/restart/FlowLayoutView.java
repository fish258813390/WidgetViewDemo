package com.neil.demo.widget.restart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 一.提取margin 与 onMeasure()重写
 * 重写generateLayoutParams
 * 重写onMeasure()--- 计算当前Container(即FlowLayout)所占的宽高
 * 思考几个问题：
 * (1)何时换行
 * Flow的布局是一行一行的，如果当前已经放不下下一个控件了，那就把这个控件移到下一行显示，
 * 所以需要有个变量计算当前行已经占据的宽度，以判断剩下的空间是否还能容得下下一个控件。
 * <p>
 * (2)如何得到FlowLayout的宽度
 * FlowLayout的宽度是所有行宽度的最大值，所以要记录下每一行所占据的宽度值，进而找到所有值中的最大值
 * <p>
 * (3)如何得到FlowLayout的高度
 * 每一行高度的综合，而每一行的高度则是取改行中所有控件高度的最大值。
 * <p>
 * Created by neil on 2018/4/29 .
 */
public class FlowLayoutView extends ViewGroup {

    public FlowLayoutView(@NonNull Context context) {
        super(context);
    }

    public FlowLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取系统建议的数值模式
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 计算FlowLayout所占用的空间大小
        int lineWidth = 0; // 记录每一行的宽度
        int lineHeight = 0; // 记录每一行的高度
        int height = 0; // 记录整个FlowLayout所占高度
        int width = 0; // 记录整个FlowLayout所占宽度

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin; // view的宽度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin; // view的高度
            // 这里注意的是，在调用child.getMeasuredWidth()、child.getMeasureHeight()之前，一定更要调用measureChild(child,widthMeasureSpec,heightMeasureSpec)
            // 在onMeasure()之后才能调用getMeasuredWidth()获得值;同样 只有调用onLayout()后，getWidth()才能获取值
            if (lineWidth + childWidth > measureWidth) {
                // 需要换行
                width = Math.max(lineWidth, childWidth);
                height += lineHeight;
                // 因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化为lineHeight、lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;
            } else {
                // 否则累加值lineWidth,lineHeight取最大值
                lineHeight = Math.max(lineHeight, childHeight);
                lineWidth += childWidth;
            }

            // 最后一行是不会超出width范围的，所以要单独处理
            if (i == childCount -1 ) {
                height += lineHeight;
                width = Math.max(width, lineWidth);
            }
        }
        // 当属性是MeasureSpec.EXACTLY时，那么它的高度就是确定的，
        // 只有当是wrap_content时，根据内部空间的大小确定它的大小时，大小是不确定的，属性是AT_MOST，此时就需要我们自己计算它的应当大小，并设置进去。
        setMeasuredDimension(measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : width,
                measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : height);
    }

    // 布局所有子控件，onLayout()中就是一个个布局子控件，由于控件要后移和换行，所以我们要标记当前控件的left坐标和top坐标
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //
        int count = getChildCount();
        int lineWidth = 0; // 累加当前行的行宽
        int lineHeight = 0; // 累加当前行的行高
        int top = 0; // 当前坐标的top坐标
        int left = 0; // 当前坐标的left坐标
        // 计算每个控件的top坐标和left 坐标，然后调用layout(int left,int top,int right,int bottom)来布局每个控件
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (childWidth + lineWidth > getMeasuredWidth()) {
                // 如果换行,当前控件将跑到下一行，从最左边开始，所以left就是0，那top则需要加上上一行的行高，才是这个控件的top点
                top += lineHeight;
                left = 0;
                // 初始化lineHeight和lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;
            } else {
                lineHeight = Math.max(lineHeight, childHeight);
                lineWidth += childWidth;
            }
            // 计算childView的left，top，right，bottom
            int lc = left + lp.leftMargin;
            int tc = top + lp.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();
            child.layout(lc, tc, rc, bc);
            // 将left置为下一子控件的起始点
            left += childWidth;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
}

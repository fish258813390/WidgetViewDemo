package com.neil.demo.widget.nested;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 嵌套滑动机制 父View
 * 1.在onStartNestedScroll() 中判断参数target是哪一个子View以及滚动的方向，然后决定是否要配合其进行嵌套滑动
 * 2.在onNestedPreScroll() 中获取需要滚动的距离，根据情况决定自己是否要进行滚动，最后还要将自己滚动消费掉的距离存储在consumed
 *   数组中回传给child
 *
 * Created by neil on 2018/4/11 0011.
 */
public class MyNestedScrollParent extends LinearLayout implements NestedScrollingParent {

    private ImageView img;
    private TextView tv;
    private MyNestedScrollChild myNestedScrollChild;
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private int imgHeight;
    private int tvHeight;

    public MyNestedScrollParent(Context context) {
        super(context);
    }

    public MyNestedScrollParent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

    }

    /**
     * 加载完布局后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        img = (ImageView) getChildAt(0);
        tv = (TextView) getChildAt(1);
        myNestedScrollChild = (MyNestedScrollChild) getChildAt(2);
        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (imgHeight <= 0) {
                    imgHeight = img.getMeasuredHeight();
                }
            }
        });
        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (tvHeight <= 0) {
                    tvHeight = tv.getMeasuredHeight();
                }
            }
        });
    }

    /**
     * 判断参数target是哪一个子View以及滚动的方向，然后决定是否要配合其进行嵌套滚动
     *
     * @param child
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (target instanceof MyNestedScrollChild) {
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
    }

    /**
     * 先于child滚动
     * 前3个为输入参数，最后一个是输出参数
     *
     * @param target
     * @param dx
     * @param dy
     * @param consumed 长度为2，第一个元素是父View消费的x方向的滚动距离，第二个元素是父View消费的y方向的滚动距离
     *                 如果这两个值不为0，则子View需要对滚动的量进行一些修正
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (showImg(dy) || hideImg(dy)) { // 如果需要显示或隐藏图片，即需要自己(parent)滚动
            scrollBy(0, -dy); // 滚动
            consumed[1] = dy; // 告诉child 我消费了多少
            System.out.println("Parent----> (" + dx + "--" + dy + "),我消费了" + consumed[1]);
        }
    }

    // 后于child滚动
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    // 返回值：是否消费了fling
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    // 返回值：是否消费了fling
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    /**
     * 下拉的时候是否向下滚动以显示图片
     *
     * @param dy
     * @return
     */
    public boolean showImg(int dy) {
        if (dy > 0) {
            if (getScrollY() > 0 && myNestedScrollChild.getScrollY() == 0) {
                System.out.println("-----showImg-----------" + dy);
                return true;
            }
        }
        return false;
    }

    /**
     * 上拉的时候，是否要向上滚动，隐藏图片
     *
     * @param dy
     * @return
     */
    public boolean hideImg(int dy) {
        if (dy < 0) {
            if (getScrollY() < imgHeight) {
                System.out.println("-----hideImg-----------" + dy);
                return true;
            }
        }
        return false;
    }

    /**
     * scrollBy内部会调用scrollTo
     *
     * @param x
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > imgHeight) {
            y = imgHeight;
        }
        super.scrollTo(x, y);
    }
}

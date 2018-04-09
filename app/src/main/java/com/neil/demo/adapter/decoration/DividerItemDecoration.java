package com.neil.demo.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * itemDecoration 装饰类
 * Created by neil on 2018/4/9 0009.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;

    public DividerItemDecoration() {
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    /**
     * 重写getItemOffsets() 方法
     * 作用：设置矩形OutRect 与 Item 的间隔区域
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 获得每个Item的位置
        int itemPostion = parent.getChildAdapterPosition(view);
        if (itemPostion != 0) {
            // 设置间隔区域为10px，即onDraw()可绘制的区域为10px
            outRect.set(0, 0, 0, 10);
        }
    }

    /**
     * 重写onDraw()
     * 作用：在间隔区域里绘制一个矩形，即分割线
     *
     * @param canvas
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        // 获取RecyclerView的Child view的个数
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            // 根据childCount总数量，获取角标的每一个View，再根据View每个Item的位置 【可现实出来的View】
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            // 第一个Item不需要绘制
//            if (index == 0 || i == childCount - 1) {
//                continue;
//            }
            // 获取布局参数
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 设置矩形(分割线)的宽度为10px
            int mDivider = 10;
            // 根据子视图的位置 & 区域间隔，设置矩形(分割线)的2个顶点坐标(左上 & 右下)
            // 矩形左上顶点 = ItemView的左边界，ItemView的下边界
            // ItemView的左边界 = RecyclerView的左边界 + paddingLeft距离 后的位置；
            int left = parent.getPaddingLeft();
            // ItemView的下边界：ItemView的bottom坐标 + 距离RecyclerView底部距离 + translationY ; 【后面2个相加不是很理解?】
            int top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            Log.d("sss", "[ " + i + "  ]" + "child.getBottom()" + child.getBottom() + ",params.bottomMargin"
                    + params.bottomMargin + "," + Math.round(ViewCompat.getTranslationY(child)));

            // 矩形右下顶点 = ItemView的右边界，矩形的下边界
            // ItemView的右边界 = RecyclerView的右边界 减去 paddingRight距离 后的坐标位置
            int right = parent.getWidth() - parent.getPaddingRight();
            // 绘制分割线的下边界 = ItemView的下边界 + 分割线的高度
            int bottom = top + mDivider;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }


}

package com.neil.demo.adapter.decoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.neil.demo.R;

/**
 * 在RecyclerView特定的ItemView上绘制内容，如蒙层、重叠内容等等
 * 添加一个小图标
 * Created by neil on 2018/4/9 0009.
 */
public class DividerItemDecoration1 extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Bitmap mIcon;

    public DividerItemDecoration1(Context context) {
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        // 获取图片资源
        mIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }

    /**
     * 重写onDrawOver()
     * 将图片资源绘制到ItemView上
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(childView);

            int left = parent.getWidth() / 2;
            int top = childView.getTop();

            if (index == 0) {
                continue;
            }
            c.drawBitmap(mIcon, left, top, mPaint);
        }
    }
}

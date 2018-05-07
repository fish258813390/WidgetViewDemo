package com.neil.demo.widget.restart;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by neil on 2018/5/2 0002.
 */

public class MoveView extends View {

    private int mLastX;
    private int mLastY;

    public MoveView(Context context) {
        super(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获得手指当前的坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.d("滑动距离", "move,deltaX:" + deltaX + ",deltaY:" + deltaY);
                scrollTo(deltaX, deltaY);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }
}

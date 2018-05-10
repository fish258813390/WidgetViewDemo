package com.neil.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 父布局
 * Created by neil on 2018/5/10 0010.
 */
public class RTLayout extends LinearLayout {

    public RTLayout(Context context) {
        super(context);
    }

    public RTLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("RTLayout---dispatchTouchEvent---DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("RTLayout---dispatchTouchEvent---MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("RTLayout---dispatchTouchEvent---UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("RTLayout---onInterceptTouchEvent---DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("RTLayout---onInterceptTouchEvent---MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("RTLayout---onInterceptTouchEvent---UP");
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("RTLayout---onTouchEvent---DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("RTLayout---onTouchEvent---MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("RTLayout---onTouchEvent---UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}

package com.neil.demo.widget.jiuyue.loading;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yujin on 2018/9/17 0017
 */
public class HackyViewPager extends ViewPager {

    private boolean isLocked;

    private float preX = 0;

    public HackyViewPager(Context context) {
        super(context);
        isLocked = false;
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLocked = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isLocked) {
            try {
                boolean res = super.onInterceptTouchEvent(ev);
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    preX = ev.getX();
                } else {
                    if (Math.abs(ev.getX() - preX) > 1) {
                        return true;
                    } else {
                        preX = ev.getX();
                    }
                }
                return res;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !isLocked && super.onTouchEvent(event);
    }

    public void toggleLock() {
        isLocked = !isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }

}

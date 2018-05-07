package com.neil.demo.widget.restart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 【绘图】手指轨迹
 * 核心思想：
 * 在自定义中拦截OnTouchEvent，然后根据手指的移动轨迹来绘制Path即可。
 * onTouchEvent() 方法执行在主线程中
 * <p>
 * Created by neil on 2018/4/28
 */
public class MyView4 extends View {

    private Path mPath = new Path();

    public MyView4(Context context) {
        super(context);
    }

    public MyView4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                //  MotionEvent.ACTION_DOWN返回true，表示当前控件已经消费了下按动作，之后的ACTION_MOVE、ACTION_UP也会继续传递到当前控件中
                //  如果ACTION_DOWN时返回false,那么后续的ACTION_MOVE、ACTION_UP动作就不会再传递到这个控件来了。主要是事件拦截
                return true;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                // 重绘控件invalidate(),也可以使用postInvalidate()
                // 区别：invalidate()一定要是在主线程中执行，如果不是在UI线程就会报错
                //       postInvalidate()可以在任意线程中执行，其内部是利用handler给主线程发送刷新界面的消息来实现的。
                invalidate();
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawPath(mPath, paint);
    }

    public void reset(){
        mPath.reset();
        invalidate();
    }
}

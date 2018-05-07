package com.neil.demo.widget.restart;

import android.content.Context;
import android.view.View;

/**
 * 【绘图篇】 Canvas
 * 一、如何获得一个Canvas对象
 * 方法一：自定义view，重写onDraw()、dispatchDraw()方法
 * onDraw()和dispatchDraw()的传入的参数中都有一个canvas对象，这个canvas对象是View中Canvas对象，
 * 利用这个对象绘制，效果会直接反应在View中
 * 区别：onDraw()是绘制视图自身
 * dispatchDraw()是绘制子视图
 * 调用顺序：无论是view还是viewGroup,调用顺序都是onDraw()>dispatchDraw()
 * 方法二：使用Bitmap创建
 * <p>
 * Created by neil on 2018/4/28
 */
public class MyView5 extends View {

    public MyView5(Context context) {
        super(context);
    }
}

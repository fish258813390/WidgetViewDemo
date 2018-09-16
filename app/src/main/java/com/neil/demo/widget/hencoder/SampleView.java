package com.neil.demo.widget.hencoder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Layout内部布局的自定义
 * 定义view的尺寸
 * 1.重写onMeasure()计算内部布局(子View的尺寸以及自己的尺寸)
 *   1-1.计算子View的尺寸
 *       a.关键：宽度和高度的MeasureSpec来计算
 *       b.结合开发者的要求(layout_xxx)和自己的可用空间(自己的尺寸上限-已用尺寸)
 *   1-2 计算子View的位置并保存子View的位置和尺寸
 *   1-3 计算自己的尺寸并保存 setMeasuredDimension()
 * 2.重写onLayout()来摆放子view
 *
 * 测量阶段：从上到下递归地调用每个View或者ViewGroup的measure()方法，测量他们的尺寸并计算他们的位置；
 * 布局阶段：从上到下递归地调用每个View或者ViewGroup的layout()方法，把测得的他们的尺寸和位置赋值给他们。
 *
 * Created by neil on 2018/9/9
 */
public class SampleView extends ViewGroup {

    public SampleView(Context context) {
        super(context);
    }


    /**
     * onMeasure()的重写
     * 1.调用每个子View的onMeasure(),让子View自我测量;
     * 2.根据子View给出的尺寸，得出子View的位置，并保存他们的位置和尺寸；
     * 3.根据子View的位置和尺寸计算出自己的尺寸，并用setMeasuredDimension()保存子View的位置(在onLayout中获取保存的值，来进行必要的使用)
     *   备注：关于保存子View位置的说明：（1）不是所有的layout都需要保存子View的位置（因为有的Layout可以在布局阶段实时推导出子View的位置，如：LinearLayout）
     *                                 （2）有时候对某些子View需要重复测量两次或者多次才能得到正确的尺寸和位置
     *
     * <p>
     * 开发者的要求 和 可用空间
     * layout_width = 48dp mode: Extrly ,size:48dp对应的px值
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        for (int i = 0; i < getChildCount(); i++) {
            int childWithSpec; // 尺寸限制
            int childHeightSpec;
            int usedWidth = 0;
            int usedHeight = 0;
            View childView = getChildAt(i);
            LayoutParams lp = childView.getLayoutParams();
            int selfWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int selfWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

            int selfHeightSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int selfHeightSpecSize = MeasureSpec.getSize(widthMeasureSpec);

            // lp.width 对应 layout_width , lp.height 对应 layout_height
            // wrap_content ==> WRAP_CONTENT,match_parent ==> MATCH_PARENT，xxdp/xxsp 对应具体的像素值
            // 结合自己的可用空间来计算

            /**
             * 根据自己的MeasureSpec 的mode的不同，判断可用 空间
             * 1.EXACTLY/AT_MOST
             *   可用空间：MeasureSpec中的size
             * 2.UNSPECIFIED
             *   可用空间：无限大
             */
            switch (lp.width) {
                case MATCH_PARENT:
                    // 子view填满父控件
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                        childWithSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize - usedWidth, MeasureSpec.EXACTLY);
                    } else {
                        // UNSPECIFIED 来说 size是没有用的
                        childWithSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    }
                    break;

                case WRAP_CONTENT:
                    // 让子View自己测量，自适应，隐藏的限制条件：不能超过父View的边界，即在父类的可用空间内（有上限和无上限）
                    if (selfWidthSpecMode == MeasureSpec.EXACTLY || selfWidthSpecMode == MeasureSpec.AT_MOST) {
                        childWithSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize - usedWidth, MeasureSpec.AT_MOST);
                    } else {
                        childWithSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    }
                    break;

                default:
                    // 精确尺寸
                    childWithSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY); // 压缩后的尺寸限制

            }

            switch (lp.height) {
                case MATCH_PARENT:
                    // 子view填满父控件
                    if (selfHeightSpecMode == MeasureSpec.EXACTLY || selfHeightSpecMode == MeasureSpec.AT_MOST) {
                        childHeightSpec = MeasureSpec.makeMeasureSpec(selfHeightSpecSize - usedHeight, MeasureSpec.EXACTLY);
                    } else {
                        // UNSPECIFIED 来说 size是没有用的
                        childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    }
                    break;

                case WRAP_CONTENT:
                    // 让子View自己测量，自适应，隐藏的限制条件：不能超过父View的边界，即在父类的可用空间内（有上限和无上限）
                    if (selfHeightSpecMode == MeasureSpec.EXACTLY || selfHeightSpecMode == MeasureSpec.AT_MOST) {
                        childHeightSpec = MeasureSpec.makeMeasureSpec(selfHeightSpecSize - usedHeight, MeasureSpec.AT_MOST);
                    } else {
                        childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    }
                    break;

                default:
                    // 精确尺寸
                    childHeightSpec = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY); // 压缩后的尺寸限制

            }
            setMeasuredDimension(childWithSpec,childHeightSpec);
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

}

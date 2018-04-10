package com.neil.demo.adapter.decoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.neil.demo.R;
import com.neil.demo.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 事件轴的装饰类
 * <p>
 * 可以将数据源从adpter 中获取，实现动态化
 * 可以扩展 将轴点改成图标
 *
 * 参考  https://www.jianshu.com/p/655ea359e3db
 * Created by neil on 2018/4/10 0010.
 */
public class DividerItemDecoration2 extends RecyclerView.ItemDecoration {

    // 写右边字的画笔
    private Paint mPaint; //  具体信息

    // 写在左边的画笔
    private Paint mPaint1; // 时间
    private Paint mPaint2; // 日期
    private Paint mPaint3; // 已签收

    private int itemView_leftinterval; // 左偏移长度
    private int itemView_topinterval; // 上偏移长度

    // 轴点半径
    private int cicle_radius;

    // 图标
    private Bitmap mIcon;

    public DividerItemDecoration2(Context context) {
        // 绘制初始化,设置画笔属性等
        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        // 左边时间文本画笔(蓝色)
        // 此处设置了 时分 & 年月
        mPaint1 = new Paint();
        mPaint1.setColor(Color.BLACK);
        mPaint1.setTextSize(30);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.BLACK);
        mPaint2.setTextSize(20);

        mPaint3 = new Paint();
        mPaint3.setColor(Color.RED);
        mPaint3.setTextSize(28);

        itemView_leftinterval = 250;
        itemView_topinterval = 50;

        cicle_radius = 10;

        mIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 设置ItemView的  左 & 上偏移长度
        outRect.set(itemView_leftinterval, itemView_topinterval, 0, 0);
    }

    /**
     * 重写onDraw()
     * 作用：在间隔区域里绘制时光轴线 & 时间文本
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // 获取RecyclerView的Child View的个数
        int childCount = parent.getChildCount();
        MyAdapter adapter = (MyAdapter) parent.getAdapter();
        ArrayList<HashMap<String, Object>> data = adapter.getData();
        // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
        for (int i = 0; i < data.size(); i++) {
            HashMap<String, Object> itemData = data.get(i);
            View child = parent.getChildAt(i);

            /**
             * 绘制轴点
             */
            // 轴点 = 圆 = 圆心(x,y)
            float centerx = child.getLeft() - itemView_leftinterval / 3;
            float centery = child.getTop() - itemView_topinterval + (itemView_topinterval + child.getHeight()) / 2;
            // 绘制轴点圆
            c.drawCircle(centerx, centery, cicle_radius, mPaint);
//            c.drawBitmap(mIcon, centerx - cicle_radius, centery - cicle_radius, mPaint);

            /**
             * 绘制上半轴线
             */
            // 上端坐标(x,y)
            float upLine_up_x = centerx;
            float upLine_up_y = child.getTop() - itemView_topinterval;

            // 下端坐标(x,y)
            float upLine_bottom_x = centerx;
            float upLine_bottom_y = centery - cicle_radius;

            /**
             * 绘制下半轴线
             */
            // 上端坐标
            float bottomLine_up_x = centerx;
            float bottomLine_up_y = centery + cicle_radius;

            // 下端坐标
            float bottomLine_bottom_x = centerx;
            float bottomLine_bottom_y = child.getBottom();

            /**
             * 绘制左边时间文本
             */
            // 获取每个Item的位置
//            int index = parent.getChildAdapterPosition(child);
            int index = (int) itemData.get("index");

            // 设置文本起始坐标
            float text_x = child.getLeft() - itemView_leftinterval * 5 / 6;
            float text_y = upLine_bottom_y;

            // 绘制上半轴线
            // 第一个节点不绘制顶部
//            if (index != 0) {
//                c.drawLine(upLine_up_x, upLine_up_y, upLine_bottom_x, upLine_bottom_y, mPaint1);
//            }
//
//            // 绘制下半轴线
//            // 最后一个节点不绘制底部
//            if (index != childCount - 1) {
//                c.drawLine(bottomLine_up_x, bottomLine_up_y, bottomLine_bottom_x, bottomLine_bottom_y, mPaint2);
//            }

            // 根据item位置设置时间文本

//            switch (index) {
//                case (0):
//                    c.drawText("13:40", text_x, text_y, mPaint1);
//                    c.drawText("2018.04.03", text_x + 5, text_y + 25, mPaint2);
//                    break;
//
//                case (1):
//                    c.drawText("17:33", text_x, text_y, mPaint1);
//                    c.drawText("2018.04.03", text_x + 5, text_y + 25, mPaint2);
//                    break;
//
//                case (2):
//                    c.drawText("20:13", text_x, text_y, mPaint1);
//                    c.drawText("2018.04.03", text_x + 5, text_y + 25, mPaint2);
//                    break;
//
//                case (3):
//                    c.drawText("13:13", text_x, text_y, mPaint1);
//                    c.drawText("2018.04.04", text_x + 5, text_y + 25, mPaint2);
//                    break;
//
//                case (4):
//                    c.drawText("13:25", text_x, text_y, mPaint1);
//                    c.drawText("2018.04.03", text_x + 5, text_y + 25, mPaint2);
//                    break;
//
//                case (5):
//                    c.drawText("22:25", text_x, text_y, mPaint1);
//                    c.drawText("2018.04.03", text_x + 5, text_y + 25, mPaint2);
//                    break;
//                default:
//                    c.drawText("已签收", text_x, text_y, mPaint1);
//            }

            c.drawText((String) itemData.get("ItemData"), text_x, text_y, mPaint1);
            c.drawText((String) itemData.get("ItemTime"), text_x + 5, text_y + 25, mPaint2);
            if (index == 0) {
                c.drawLine(bottomLine_up_x, bottomLine_up_y, bottomLine_bottom_x, bottomLine_bottom_y, mPaint2);
            } else if (index == data.size() - 1) {
                c.drawLine(upLine_up_x, upLine_up_y, upLine_bottom_x, upLine_bottom_y, mPaint1);
                c.drawText("(已签收)", text_x, text_y + 50, mPaint3);
            } else {
                c.drawLine(upLine_up_x, upLine_up_y, upLine_bottom_x, upLine_bottom_y, mPaint1);
                c.drawLine(bottomLine_up_x, bottomLine_up_y, bottomLine_bottom_x, bottomLine_bottom_y, mPaint2);
            }

        }

    }
}

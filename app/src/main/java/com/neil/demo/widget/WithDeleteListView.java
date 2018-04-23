package com.neil.demo.widget;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.neil.demo.R;

/**
 * 带滑动删除的ListView
 * Created by neil on 2018/4/23 0023.
 */
public class WithDeleteListView extends ListView implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private GestureDetector mGestureDetector; // 手势动作探测器

    private OnDeleteListener mOnDeleteListener;

    private View mDeleteBtn; // 删除按钮
    private ViewGroup mItemLayout; // 列表项布局
    private int mSelectedItem; // 选择的列表项
    private boolean isDeleteShown; // 当前按钮是否显示出来

    public interface OnDeleteListener {
        void onDelete(int position);
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener){
        this.mOnDeleteListener = onDeleteListener;
    }


    public WithDeleteListView(Context context) {
        super(context);
        init();
    }

    public WithDeleteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WithDeleteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), this); // 创建手势监听器对象
        setOnTouchListener(this); // 监听touch事件
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isDeleteShown) {
            hideDelete();
            return false;
        }
        return mGestureDetector.onTouchEvent(event);
    }

    // 隐藏删除按钮
    public void hideDelete() {
        mItemLayout.removeView(mDeleteBtn);
        mDeleteBtn = null;
        isDeleteShown = false;
    }

    public boolean isDeleteShown() {
        return isDeleteShown;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (!isDeleteShown) {
            mSelectedItem = pointToPosition((int) e.getX(), (int) e.getY());
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // 如果当前删除按钮没有显示出来，并且x方向滑动的速度大于y方向的滑动速度
        if (!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)) {
            mDeleteBtn = LayoutInflater.from(getContext()).inflate(R.layout.delete_btn, null);
            mDeleteBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemLayout.removeView(mDeleteBtn);
                    mDeleteBtn = null;
                    isDeleteShown = false;
                    if (mOnDeleteListener != null) {
                        mOnDeleteListener.onDelete(mSelectedItem);
                    }
                }
            });
            mItemLayout = (ViewGroup) getChildAt(mSelectedItem - getFirstVisiblePosition());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            mItemLayout.addView(mDeleteBtn, params);
            isDeleteShown = true;
        }
        return false;
    }


    /**
     * 后面方法没有用到
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }


}

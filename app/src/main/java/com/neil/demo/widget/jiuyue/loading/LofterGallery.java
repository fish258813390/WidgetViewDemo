package com.neil.demo.widget.jiuyue.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 多图预览控件
 * Created by yujin on 2018/9/17
 */
public class LofterGallery extends RelativeLayout {

    private Context mContext;
    private View view;
    private HackyViewPager mViewPager;
    private RelativeLayout mRootLayout;

    private LinearLayout indicator; // 小圆点指示器
    private LofterPagerAdapter mLofterPagerAdapter;
    private int currentPagePosition = 0;
    private List<ImageView> mIndicators = new ArrayList<>(); // 保存小圆点指示器


    public LofterGallery(Context context) {
        this(context, null);
    }

    public LofterGallery(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LofterGallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

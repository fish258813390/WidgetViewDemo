package com.neil.demo.widget.jiuyue.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.neil.demo.R;

/**
 * 带进度条加载的imageview
 * Created by yujin on 2018/9/16
 */
public class LofterImageView extends RelativeLayout {

    private Context mContext;
    private LofterProgressView mProgressView; // 进度框
    private PhotoView mPhotoView; // 可伸缩的imageView
    private LinearLayout mErrorLayout; // 加载失败的占位图
    private RelativeLayout mRootLayout;
    private boolean isLoadSuccess = false;
    private String mImageUrl;

    public LofterImageView(Context context) {
        this(context, null);
    }

    public LofterImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LofterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.lofter_progress_view, this, true);
        mRootLayout = (RelativeLayout) mView.findViewById(R.id.root);
        mProgressView = (LofterProgressView) mView.findViewById(R.id.pv);
        mPhotoView = (PhotoView) mView.findViewById(R.id.photo_view);
        mErrorLayout = (LinearLayout) mView.findViewById(R.id.layout_load_error);
    }


}

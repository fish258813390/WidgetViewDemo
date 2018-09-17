package com.neil.demo.widget.jiuyue.loading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.neil.demo.R;

import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;

/**
 * 带进度条加载的imageview
 * Created by yujin on 2018/9/16
 */
public class LofterImageView extends RelativeLayout {

    private static final String TAG = LofterImageView.class.getSimpleName();

    private Context mContext;
    private LofterProgressView mProgressView; // 进度框
    private PhotoView mPhotoView; // 可伸缩的imageView
    private LinearLayout mErrorLayout; // 加载失败的占位图
    private RelativeLayout mRootLayout;
    private boolean isLoadSuccess = false; // 是否加载成功
    private String mImageUrl; // 图片url

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

    private void initListener() {
        ProgressManager.getInstance().addResponseListener(mImageUrl, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                int percent = progressInfo.getPercent();
                mProgressView.setVisibility(VISIBLE);
                mProgressView.setPercent(percent);
                if (!isLoadSuccess && (progressInfo.isFinish() || percent == 100)) {
                    // 加载完成
                    Log.d(TAG, "Glide ----> finish");
                }
            }

            @Override
            public void onError(long id, Exception e) {
                Log.d(TAG, "Glide ----> onError" + e.getMessage());
                mErrorLayout.setVisibility(VISIBLE);
            }
        });
    }

    /**
     * 加载图片
     *
     * @param imageUrl
     */
    public void load(String imageUrl) {
        this.mImageUrl = imageUrl;
        initListener();
        mErrorLayout.setVisibility(GONE);
        Glide.with(mContext)
                .load(mImageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e(TAG, "load ----> onException" + e.getMessage());
                        mProgressView.setVisibility(GONE);
                        mErrorLayout.setVisibility(VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (!isFromMemoryCache) {
                            mProgressView.startAnimation(getDefaultExitAnimation());
                        }
                        isLoadSuccess = true;
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(mPhotoView);
    }

    /**
     * 获取加载进度达到百分百后，进度条消失的动画
     *
     * @return
     */
    private Animation getDefaultExitAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0F, 0.0F);
        alphaAnimation.setStartOffset(600L);
        alphaAnimation.setDuration(400L);
        alphaAnimation.setFillEnabled(true);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, "lofterProgressView exit animation start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "lofterProgressView gone");
                mProgressView.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return alphaAnimation;
    }

    public PhotoView getPhotoView() {
        return mPhotoView;
    }

    /**
     * 资源回收和销毁
     */
    public void destroy() {
        if (mPhotoView != null) {
            mPhotoView.setImageBitmap(null);
            releaseImageViewResouce(mPhotoView);
        }
    }

    /**
     * 释放图片资源
     *
     * @param imageView imageView
     */
    public static void releaseImageViewResouce(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
    }

}

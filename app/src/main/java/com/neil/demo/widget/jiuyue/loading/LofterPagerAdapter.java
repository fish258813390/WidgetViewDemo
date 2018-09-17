package com.neil.demo.widget.jiuyue.loading;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * 多图预览 PageAdapter
 * Created by yujin on 2018/9/17 0017
 */
public class LofterPagerAdapter extends PagerAdapter {

    private static final String TAG = LofterPagerAdapter.class.getSimpleName();
    private Context mContext;
    private List<String> mImages;
    private static ArrayList<LofterImageView> mPhotoViewPool;
    private static final int mPhotoViewPoolSize = 9;

    public LofterPagerAdapter(Context context, List<String> images) {
        this.mContext = context;
        if (images != null && images.size() > 0) {
            mImages = new ArrayList<>(images);
        } else {
            mImages = new ArrayList<>();
        }
        mPhotoViewPool = new ArrayList<>();
        buildLofterImageViewPool();
    }

    private void buildLofterImageViewPool() {
        for (int i = 0; i < mPhotoViewPoolSize; i++) {
            LofterImageView lofterImageView = new LofterImageView(mContext);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lofterImageView.setLayoutParams(layoutParams);
            mPhotoViewPool.add(lofterImageView);
        }
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LofterImageView image = mPhotoViewPool.get(position);
        if (image == null) {
            image = new LofterImageView(mContext);
            image.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        image.getPhotoView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick((PhotoView) v);
                }
            }
        });
        image.load(mImages.get(position));
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnItemClickListener {
        void onItemClick(PhotoView imageView);
    }

    /**
     * 回调数据监听
     */
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 更换图片
     *
     * @param images
     */
    public void updateImages(List<String> images) {
        if (images != null && images.size() > 0) {
            this.mImages.clear();
            this.mImages.addAll(images);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空图片池
     */
    public void destroy() {
        for (LofterImageView lofterImageView : mPhotoViewPool) {
            lofterImageView.destroy();
        }
        mPhotoViewPool.clear();
        mPhotoViewPool = null;
    }

}

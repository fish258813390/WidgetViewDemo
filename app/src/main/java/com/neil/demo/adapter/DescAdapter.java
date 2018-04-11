package com.neil.demo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.neil.demo.R;

import java.util.List;

/**
 * Created by neil on 2018/4/11 0011.
 */

public class DescAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DescAdapter(@Nullable List<String> data) {
        super(R.layout.rv_des_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String desc) {
        holder.setText(R.id.tv_title, desc);
    }
}

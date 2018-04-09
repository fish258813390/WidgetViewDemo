package com.neil.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neil.demo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by neil on 2018/4/9 0009.
 */
public class MyAdapter extends RecyclerView.Adapter {

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, Object>> listItem;

    public MyAdapter(Context context, ArrayList<HashMap<String, Object>> listItem) {
        layoutInflater = LayoutInflater.from(context);
        this.listItem = listItem;
    }

    // 绑定Viewholder布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Viewholder(layoutInflater.inflate(R.layout.list_cell, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Viewholder vh = (Viewholder) holder;
        // 绑定数据到viewholder里面
        vh.mTitle.setText((String) listItem.get(position).get("ItemTitle"));
        vh.mText.setText((String) listItem.get(position).get("ItemText"));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    // 定义Viewholder
    class Viewholder extends RecyclerView.ViewHolder {
        private TextView mTitle, mText;

        public Viewholder(View root) {
            super(root);
            mTitle = root.findViewById(R.id.tv_itemtitle);
            mText = root.findViewById(R.id.tv_itemtext);
        }

        public TextView getTitle() {
            return mTitle;
        }

        public TextView getText() {
            return mText;
        }

    }
}

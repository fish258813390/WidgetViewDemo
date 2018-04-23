package com.neil.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.neil.demo.widget.WithDeleteListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neil on 2018/4/23 0023.
 */
public class DeleteListViewActivity extends AppCompatActivity {

    // 自定义Lv
    private WithDeleteListView withDeleteListView;
    // 自定义适配器
    private CustomListViewAdapter mAdapter;
    // 内容列表
    private List<String> contentList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_delete_listview);
        initContentList();

        withDeleteListView = findViewById(R.id.wd_listview);
        withDeleteListView.setOnDeleteListener(new WithDeleteListView.OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                contentList.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter = new CustomListViewAdapter(this, 0, contentList);
        withDeleteListView.setAdapter(mAdapter);
    }

    // 初始化内容列表
    private void initContentList() {
        for (int i = 0; i < 20; i++) {
            contentList.add("内容项" + i);
        }
    }


    static class CustomListViewAdapter extends ArrayAdapter<String> {

        public CustomListViewAdapter(@NonNull Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.with_delete_listview_item, null);

            } else {
                view = convertView;
            }
            TextView contentTv = view.findViewById(R.id.content_tv);
            contentTv.setText(getItem(position));
            return view;
        }
    }

    @Override
    public void onBackPressed() {
        if (withDeleteListView.isDeleteShown()) {
            withDeleteListView.hideDelete();
            return;
        }
        super.onBackPressed();
    }
}

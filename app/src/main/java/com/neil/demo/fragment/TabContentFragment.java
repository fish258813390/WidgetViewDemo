package com.neil.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.neil.demo.R;
import com.neil.demo.adapter.DescAdapter;

import java.util.ArrayList;

/**
 * Created by neil on 2018/4/11 0011.
 */
public class TabContentFragment extends Fragment {

    private static final String EXTRA_CONTENT = "content";

    private RecyclerView mRecyclerView;

    private DescAdapter mDescAdapter;

    public static TabContentFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabContentFragment tabContentFragment = new TabContentFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_tab_content, null);
        mRecyclerView = contentView.findViewById(R.id.recycler);

        initData();
        return contentView;
    }

    private void initData() {
        ArrayList<String> mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDatas.add("哈哈,你好--->" + (i + 1));
        }
        mDescAdapter = new DescAdapter(mDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mDescAdapter);

    }

}

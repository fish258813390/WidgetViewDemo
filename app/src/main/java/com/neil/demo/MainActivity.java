package com.neil.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neil.demo.adapter.decoration.DividerGridItemDecoration;
import com.neil.demo.adapter.decoration.DividerItemDecoration;
import com.neil.demo.adapter.MyAdapter;
import com.neil.demo.adapter.decoration.DividerItemDecoration1;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ArrayList<HashMap<String, Object>> mDatas = new ArrayList<>();

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_main);

//        final View tvTest = (View) findViewById(R.id.tv_test);
//        tvTest.post(new Runnable() {
//            @Override
//            public void run() {
//                TextView tv = (TextView) tvTest;
//                tv.setText("11111111111111111");
//            }
//        });

        mRecyclerView = findViewById(R.id.my_recycler_view);
        for (int i = 0; i < 30; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemTitle", "这是第" + i + "行");
            map.put("ItemText", "第" + i + "行");
            mDatas.add(map);
        }
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

//        mRecyclerView.addItemDecoration(new DividerItemDecoration());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration1(this));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        myAdapter = new MyAdapter(this,mDatas);
        mRecyclerView.setAdapter(myAdapter);

    }
}

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
import com.neil.demo.adapter.decoration.DividerItemDecoration2;

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
//        for (int i = 0; i < 30; i++) {
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("ItemTitle", "这是第" + i + "行");
//            map.put("ItemText", "第" + i + "行");
//            mDatas.add(map);
//        }

        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

//        mRecyclerView.addItemDecoration(new DividerItemDecoration());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration1(this));
//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        mRecyclerView.addItemDecoration(new DividerItemDecoration2(this));


        myAdapter = new MyAdapter(this, mDatas);
        mRecyclerView.setAdapter(myAdapter);

    }


    public void initData() {
        mDatas = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/

        HashMap<String, Object> map1 = new HashMap<String, Object>();
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        HashMap<String, Object> map6 = new HashMap<String, Object>();

        map1.put("ItemTitle", "美国谷歌公司已发出");
        map1.put("ItemText", "发件人:谷歌 CEO Sundar Pichai");
        map1.put("ItemData", "13:40");
        map1.put("index", 0);
        map1.put("ItemTime", "2018.04.03");
        mDatas.add(map1);

        map2.put("ItemTitle", "国际顺丰已收入");
        map2.put("ItemText", "等待中转");
        map2.put("ItemData", "17:33");
        map2.put("index", 1);
        map2.put("ItemTime", "2018.04.03");
        mDatas.add(map2);

        map3.put("ItemTitle", "国际顺丰转件中");
        map3.put("ItemText", "下一站中国");
        map3.put("index", 2);
        map3.put("ItemData", "21:23");
        map3.put("ItemTime", "2018.04.03");
        mDatas.add(map3);

        map4.put("ItemTitle", "中国顺丰已收入");
        map4.put("ItemText", "下一站上海开发部");
        map4.put("ItemData", "03:33");
        map4.put("index", 3);
        map4.put("ItemTime", "2018.04.04");
        mDatas.add(map4);

        map5.put("ItemTitle", "中国顺丰派件中");
        map5.put("ItemText", "等待派件");
        map5.put("ItemData", "08:33");
        map5.put("index", 4);
        map5.put("ItemTime", "2018.04.04");
        mDatas.add(map5);

        map6.put("ItemTitle", "技术部门已签收");
        map6.put("ItemText", "收件人:neil");
        map6.put("index", mDatas.size());
        map6.put("ItemData", "12:13");
        map6.put("ItemTime", "2018.04.04");
        mDatas.add(map6);
    }
}

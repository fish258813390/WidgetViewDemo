package com.neil.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * 协调布局
 * CoordinatorLayout 包含三个子View：
 * AppbarLayout
 * NestedScrollVew
 * FloatingActionBar
 * <p>
 * <p>
 * <CoordinatorLayout>
 *      <AppbarLayout/>
 *      <scrollableView/>
 *      <FloatingActionButton/>
 * </CoordinatorLayout>
 * Created by neil on 2018/4/11
 */
public class CoordinatorLayoutActivity extends Activity {

    private AppBarLayout mAppBarLayout;
    private FloatingActionButton mActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        mAppBarLayout = findViewById(R.id.main_appbar);
        mActionButton = findViewById(R.id.float_action_button);

        // AppBarLayout 添加滑动偏移监听事件
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                System.out.println("appBarLayout:" + verticalOffset + ",最大偏移量是：" + appBarLayout.getTotalScrollRange());
            }
        });


        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CoordinatorLayoutActivity.this,"1111",Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.float_action_button), "Show The Snackbar", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

package com.neil.demo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.neil.demo.widget.restart.MoveView;
import com.neil.demo.widget.restart.MyRegionView;
import com.neil.demo.widget.restart.MyView;
import com.neil.demo.widget.restart.MyView1;
import com.neil.demo.widget.restart.MyView2;
import com.neil.demo.widget.restart.MyView3;
import com.neil.demo.widget.restart.MyView4;

/**
 * 自定义view start
 * part1
 * Created by neil on 2018/4/27.
 */
public class WidgetNew1Activity extends AppCompatActivity {

    final int startX = 0;
    final int deltaX = 100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_new1);

//        FrameLayout root = findViewById(R.id.root);
//        final MoveView myView4 = new MoveView(this);
//        root.addView(myView4);
//
        final Button button = findViewById(R.id.btn_test);
//        ObjectAnimator.ofFloat(button,"translationX",0,300).setDuration(100).start();
        final ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                button.scrollTo((int) (startX + (deltaX * fraction)), 0);
            }
        });


        //
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
//        params.width += 100;
//        params.leftMargin += 200;
//        button.setLayoutParams(params);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WidgetNew1Activity.this, "aaa", Toast.LENGTH_SHORT).show();
//                ObjectAnimator.ofFloat(button,"translationX",0,100).setDuration(100).start();
                animator.start();
            }
        });

    }
}

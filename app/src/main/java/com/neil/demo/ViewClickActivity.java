package com.neil.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.neil.demo.greendao.Meizi;
import com.neil.demo.greendao.MeiziDaoUtils;
import com.neil.demo.view.RTButton;
import com.neil.demo.view.RTLayout;

/**
 * Created by neil on 2018/5/10 0010.
 */

public class ViewClickActivity extends AppCompatActivity {

    private RTLayout mRTLayout;
    private RTButton mRTButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rt_layout_test);
        initView();
    }


    protected void initView() {
        mRTLayout = findViewById(R.id.myLayout);
        mRTButton = findViewById(R.id.myButton);
//        mRTButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        System.out.println("RTButton---onTouch---DOWN");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        System.out.println("RTButton---onTouch---MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        System.out.println("RTButton---onTouch---UP");
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
//
        mRTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewClickActivity.this,"111",Toast.LENGTH_SHORT).show();
                System.out.println("RTButton --  onClick!");
                Meizi meizi = new Meizi();
                meizi.set_id(1002l);
                meizi.setUrl("http://baidu.jpg");
                new MeiziDaoUtils(ViewClickActivity.this).insertMeizi(meizi);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Activity---dispatchTouchEvent---DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Activity---dispatchTouchEvent---MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("Activity---dispatchTouchEvent---UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("Activity---onTouchEvent---DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Activity---onTouchEvent---MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("Activity---onTouchEvent---UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}

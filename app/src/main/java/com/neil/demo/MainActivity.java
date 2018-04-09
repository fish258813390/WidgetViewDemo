package com.neil.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View tvTest = (View) findViewById(R.id.tv_test);
        tvTest.post(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) tvTest;
                tv.setText("11111111111111111");
            }
        });
    }
}

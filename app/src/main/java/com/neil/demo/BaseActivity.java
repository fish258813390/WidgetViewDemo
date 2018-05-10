package com.neil.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;

/**
 * 基类
 * Created by neil on 2018/5/9 0009.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private InputMethodManager imm;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        // 初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        // 初始化数据
        initData();
        // View与数据绑定
        initView();

        // 设置监听
        setListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;
        if (mImmersionBar != null) {
            // 销毁,防止内存泄漏，不调用改方法，如果界面bar发生改变，在不关闭app的情况下，
            // 退出此界面再进入将记忆最后一次bar改变的状态
            mImmersionBar.destroy();
        }
    }

    protected abstract int setLayoutId();

    protected void initImmersionBar() {
        // 在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void setListener() {
    }

    /**
     * 是否可以使用沉浸式
     *
     * @return
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }
}

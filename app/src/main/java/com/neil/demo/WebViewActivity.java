package com.neil.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by neil on 2018/4/10 0010.
 */
public class WebViewActivity extends Activity {

    private WebView mWebView;
    private Button mBtnTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = findViewById(R.id.webview);

        mBtnTest = findViewById(R.id.btn_test);
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

//        mWebView.loadUrl("https://www.jianshu.com/p/3c94ae673e2a");
        mWebView.loadUrl("file:///android_asset/javascript.html");

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("WebViewActivity", "onPageStarted");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 在回调方法里 拦截url并进行解析，事先和html约定好协议
                // 根据协议的参数，判断是否是所需要的url
                // 一般根据scheme(协议格式) & authority(协议名) 判断(前2个参数)
                // 假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("js")) {

                    if (uri.getAuthority().contains("webview")) {

                        System.out.println("js调用了Android的方法");
                        Set<String> queryParameterNames = uri.getQueryParameterNames();
                        Iterator<String> iterator = queryParameterNames.iterator();
                        while (iterator.hasNext()) {
                            System.out.println("参数:" + iterator.next());
                        }
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebViewActivity", "onPageFinished");
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });

        mWebView.addJavascriptInterface(new AndroidtoJs(), "test");

        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过Handler发送消息
                final int version = Build.VERSION.SDK_INT;
                mWebView.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        // 注意调用的JS方法名要对应上
                        // 调用javascript的callJS()方法
                        if (version < 18) {
                            mWebView.loadUrl("javascript.html:callJS()");
                        } else {
                            mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                    //此处为 js 返回的结果
                                    System.out.println("JS返回的结果" + value);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        @JavascriptInterface
        public void hello(String msg) {
            System.out.println("JS调用了Android的hello方法");
            AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
            b.setTitle("Alert");
            b.setMessage("JS调用了Android的hello方法");
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            b.setCancelable(false);
            b.create().show();
        }
    }
}

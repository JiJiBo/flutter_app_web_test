package com.iallchain.flutter_app_web_test

import android.app.Activity
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.iallchain.flutter_app_web_test.WebViewPack.WebViewRegistrant

import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)
        var wv = WebView(this);
        wv.getSettings().setJavaScriptEnabled(true);
        //访问百度首页
        //设置在当前WebView继续加载网页
        wv.setWebViewClient(object :WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (request != null) {
                    val isCan = request.url.toString().startsWith("http://") || request.url.toString().startsWith("https://")
                    if (!isCan) {
                        return true
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed();//接受信任所有网站的证书
            }
        });
        WebViewRegistrant.registerWith(this,this, wv)
    }
}

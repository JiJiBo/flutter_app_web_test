package com.iallchain.flutter_app_web_test.WebViewPack;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class WebViewFactory extends PlatformViewFactory {
    private WebView wv;

    public WebViewFactory(MessageCodec<Object> createArgsCodec, WebView wv) {
        super(createArgsCodec);
        this.wv = wv;
    }
    @Override
    public PlatformView create(Context context, int i, Object o) {
        return new PlatformView() {
            @Override
            public View getView() {
                return wv;
            }

            @Override
            public void dispose() {

            }
        };
    }
}

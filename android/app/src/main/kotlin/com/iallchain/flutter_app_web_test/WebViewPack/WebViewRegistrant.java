package com.iallchain.flutter_app_web_test.WebViewPack;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDrawableHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;

public class WebViewRegistrant implements MethodChannel.MethodCallHandler {
    private PluginRegistry.Registrar registrar;
    private WebView webView;
    private Activity activity;

    private WebViewRegistrant(PluginRegistry.Registrar registrar, WebView webView, Activity activity) {
        this.registrar = registrar;
        this.webView = webView;
        this.activity = activity;
    }

    public static void registerWith(Activity activity, PluginRegistry registry, WebView webView) {
        final String key = WebViewRegistrant.class.getCanonicalName();
        if (registry.hasPlugin(key)) return;
        PluginRegistry.Registrar registrar = registry.registrarFor(key);
        registrar.platformViewRegistry().registerViewFactory("MyWebView", new WebViewFactory(new StandardMessageCodec(), webView));
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "com.iallchain/webview");
        Log.e("1123", "jijiji ");
        channel.setMethodCallHandler(new WebViewRegistrant(registrar, webView, activity));
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        Log.e("1123", methodCall.method);
        if (methodCall.method.equals("cutWeb")) {
            if (activity != null) {
                verifyStoragePermissions(activity, result);
            }
        } else if (methodCall.method.equals("loadurl")) {
            String url = methodCall.argument("url");
            String type = methodCall.argument("type");
            loadUrl(url, type);
        } else {
            result.notImplemented();
        }
    }

    private void loadUrl(String url, String type) {
        if (type.equals("computer")) {
            webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
        } else {
            webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36");
        }
        webView.loadUrl(url);
    }

    public static final String ROOT_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + File.separator + "iallchain";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void verifyStoragePermissions(Activity activity, MethodChannel.Result result) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the u
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        } else {
            cutWeb(result);
        }
    }

    private void cutWeb(MethodChannel.Result result) {
        Bitmap bitmap = getPic();
        File file = new File(ROOT_FILE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File imgFile = new File(file, fileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            Log.e("1123", "ccc");
            outputStream.close();
            result.success(imgFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getPic() {
        int height = (int) (webView.getContentHeight() * webView.getScale());
        int width = webView.getWidth();
        int pH = webView.getHeight();
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bm);
        int top = height;
        while (top > 0) {
            if (top < pH) {
                top = 0;
            } else {
                top -= pH;
            }
            canvas.save();
            canvas.clipRect(0, top, width, top + pH);
            webView.scrollTo(0, top);
            webView.draw(canvas);
            canvas.restore();
        }
        return bm;
    }

}

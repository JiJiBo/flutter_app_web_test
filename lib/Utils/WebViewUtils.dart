import 'dart:io';

import 'package:flutter/services.dart';

class WebViewUtils {
  ///私有构造方法
  WebViewUtils._();

  static String TYPE_COMPUTER = "computer";
  static String TYPE_MOBILE = "mpbile";

  ///其中com.example/share用于唯一识别通道名称，
  /// 这个名字后边会在Android端使用到，必须跟Android端对应调用方法中的通道名称保持一致，否则无法调用
  static const MethodChannel _channel =
      const MethodChannel("com.iallchain/webview");

  ///Flutter调用三方登录，需要Android端返回是否登录成功，以及用户信息。
  ///onResult中存储了是否调用成功，以及成功之后获取到的用户信息
  static Future<dynamic> cutWeb(int platform,
      {Function onResult(String filePath)}) async {
    String result = await _channel.invokeMethod('cutWeb', {});
    if (onResult != null) onResult(result);
    return result;
  }

  static Future<dynamic> loadurl(
    String url,
    String type,
  ) async {
    String result =
        await _channel.invokeMethod('loadurl', {"url": url, "type": type});
    return result;
  }
}

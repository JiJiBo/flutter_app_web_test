import 'dart:io';

import 'package:flutter/material.dart';

import 'Utils/WebViewUtils.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return layout();
  }
}

class layout extends StatefulWidget {
  @override
  view createState() {
    return view();
  }
}

class view extends State<layout> {
  cutWeb() {
    WebViewUtils.loadurl(
        "https://blog.csdn.net/S43565442/article/details/86016442",
        WebViewUtils.TYPE_COMPUTER);
  }

  var img = Image.asset("images/mipmap/home_icon_cunzheng_def.png");

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: "wv",
      home: new Scaffold(
        appBar: AppBar(
          title: Text("1212"),
        ),
        body: Container(
          child: Stack(
            children: <Widget>[
              new AndroidView(viewType: 'MyWebView'),
              Positioned(
                  bottom: 10,
                  child: FlatButton(
                      onPressed: cutWeb,
                      child: Text(
                        "截屏  zx ",
                        style: TextStyle(fontSize: 40, color: Colors.red),
                      ))),
              img,
            ],
          ),
        ),
      ),
    );
  }
}

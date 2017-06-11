# AndroidChartsDemo

Show case of embedding web components into Android application via DukeScript presenters. See [tutorial](https://dukescript.com/best/practices/2017/06/11/AndroidStudio.html) for more verbose intro.

![Android app with Charts.js](http://dukescript.com/assets/as/charts-7.jpg)

## Usage

Make sure you have an Android device connected or an emulator started and then you can:
```bash
$ ANDROID_HOME=/your/sdk/path/ ./gradlew installDebug
```
Application called **Embedded DukeScript Charts** shall appear on your device. The application is composed from
two native buttons at the top and a native text area at bottom. Most of the space is however occupied by a **WebView**
[configured to allow easy Java access](https://dukescript.com/javadoc/presenters/com/dukescript/presenters/Android.html#configure-java.lang.String-android.webkit.WebView-java.lang.String-java.lang.Boolean-) to [Charts.js Java API](https://dukescript.com/javadoc/charts/net/java/html/charts/package-summary.html#package.description). Click the buttons, click the graph and see the
native to web and web to native interaction works.

## Open in IDE

Use [Android Studio 2.3.1](https://developer.android.com/studio/index.html) to see and work with the sources. Edit, re-design,
execute, debug the Java code.

## How this is done?

Check the [associated tutorial](https://dukescript.com/best/practices/2017/06/11/AndroidStudio.html) or check the source directly:
- one simple [HTML layout page](https://github.com/dukescript/AndroidChartsDemo/blob/master/app/src/main/assets/chart.html)
- manipulated by [main activity](https://github.com/dukescript/AndroidChartsDemo/blob/master/app/src/main/java/com/dukescript/example/androidchartsdemo/MainActivity.java#L32) Java code
- with just [two additional dependencies](https://github.com/dukescript/AndroidChartsDemo/blob/master/app/build.gradle#L35)

## Where to go next?

Check the [Android.configure](https://dukescript.com/javadoc/presenters/com/dukescript/presenters/Android.html#configure-java.lang.String-android.webkit.WebView-java.lang.String-java.lang.Boolean-)
documentation to understand possible embedding alternatives.

Check the [overview javadoc page](https://dukescript.com/javadoc/) to see available Java APIs that
have already been prepared and documented by the [DukeScript documentation](https://dukescript.com/documentation.html)
project. Choose from charts, maps, jquery, knockout, canvas, SVG, and more...

Talk back. Contact us via
[forums](https://groups.google.com/forum/#!forum/dukescript),
[twitter](https://twitter.com/DukeScript) or
[support line](https://dukescript.com/index.html#support).

package com.dukescript.example.androidchartsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.dukescript.example.webui.WebChart;
import com.dukescript.presenters.Android;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    private WebChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView view = (WebView) findViewById(R.id.view);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Executor withView = Android.configure("GPLv3", view, "file:///android_asset/chart.html", null);
        chart = new AndroidWebChart(withView);
    }



    public void plusRed(View v) {
        chart.plusRed();
    }

    public void plusBlue(View v) {
        chart.plusBlue();
    }

    private final class AndroidWebChart extends WebChart {
        public AndroidWebChart(Executor withView) {
            super(withView);
        }

        @Override
        protected void setText(String msg) {
            TextView text = (TextView) findViewById(R.id.text);
            text.setText(msg);
        }

        @Override
        protected void runOnUiThread(Runnable command) {
            MainActivity.this.runOnUiThread(command);
        }
    }
}

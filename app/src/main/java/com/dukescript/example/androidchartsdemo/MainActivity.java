package com.dukescript.example.androidchartsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import net.java.html.charts.Chart;
import net.java.html.charts.ChartEvent;
import net.java.html.charts.ChartListener;
import net.java.html.charts.Color;
import net.java.html.charts.Config;
import net.java.html.charts.Segment;

import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements ChartListener {
    private Executor withView;
    private Chart<Segment, Config> pie;
    private int redAmount = 1;
    private int blueAmount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView view = (WebView) findViewById(R.id.view);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        withView = com.dukescript.presenters.Android.configure("GPLv3", view, "file:///android_asset/chart.html", null);
        withView.execute(new Runnable() {
            @Override
            public void run() {
                pie = Chart.createPie();
                pie.addChartListener(MainActivity.this);
                pie.applyTo("chart");
                updatePie();
            }
        });
    }

    void updatePie() {
        withView.execute(new Runnable() {
            @Override
            public void run() {
                Segment red = new Segment("Red", redAmount, Color.valueOf("red"), Color.valueOf("magenta"));
                Segment blue = new Segment("Blue", blueAmount, Color.valueOf("blue"), Color.valueOf("cyan"));
                List<Segment> data = pie.getData();
                if (data.isEmpty()) {
                    data.add(red);
                    data.add(blue);
                } else {
                    data.set(0, red);
                    data.set(1, blue);
                }
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = (TextView) findViewById(R.id.text);
                text.setText("What is the score? Click the graph!");
            }
        });
    }

    public void plusRed(View v) {
        redAmount++;
        updatePie();
    }

    public void plusBlue(View v) {
        blueAmount++;
        updatePie();
    }

    @Override
    public void chartClick(ChartEvent chartEvent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = (TextView) findViewById(R.id.text);
                text.setText("Blue " + blueAmount + " vs. " + redAmount + " Red");
            }
        });
    }
}

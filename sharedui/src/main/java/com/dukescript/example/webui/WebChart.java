package com.dukescript.example.webui;

import net.java.html.charts.Chart;
import net.java.html.charts.ChartEvent;
import net.java.html.charts.ChartListener;
import net.java.html.charts.Color;
import net.java.html.charts.Config;
import net.java.html.charts.Segment;

import java.util.List;
import java.util.concurrent.Executor;

public abstract class WebChart implements ChartListener {
    private final Executor withView;
    private Chart<Segment, Config> pie;
    private int redAmount = 1;
    private int blueAmount = 1;

    public WebChart(Executor withView) {
        this.withView = withView;
        this.withView.execute(new Runnable() {
            @Override
            public void run() {
                pie = Chart.createPie();
                pie.addChartListener(WebChart.this);
                pie.applyTo("chart");
                updatePie();
            }
        });
    }

    final void updatePie() {
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
                String msg = "What is the score? Click the graph!";
                setText(msg);
            }
        });
    }

    public final void plusRed() {
        redAmount++;
        updatePie();
    }

    public final void plusBlue() {
        blueAmount++;
        updatePie();
    }

    @Override
    public final void chartClick(ChartEvent chartEvent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setText("Blue " + blueAmount + " vs. " + redAmount + " Red");
            }
        });
    }


    protected abstract void setText(String text);
    protected abstract void runOnUiThread(Runnable command);
}

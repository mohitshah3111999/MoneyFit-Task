package com.example.moneyfitio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ShowGraphActivity extends AppCompatActivity {
    LineChart chart;
    List<String> xLabels;
    List<Integer> yLabel_invest, y_Label_current;
    LineDataSet investLineDataSet, currentLineDataSet;
    public static String X_LABEL = "xLabels", Y_LABEL_INVEST = "yLabel_invest", Y_LABEL_CURRENT = "yLabel_current";
    CheckBox investCheck, currentCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_graph);

        @SuppressLint("UseCompatLoadingForDrawables") final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=#2B2B2B>" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chart = findViewById(R.id.graph);
        investCheck = findViewById(R.id.investCheckbox);
        currentCheck = findViewById(R.id.currentCheckbox);

        if (getIntent() != null) {
            xLabels = getIntent().getStringArrayListExtra(X_LABEL);
            yLabel_invest = getIntent().getIntegerArrayListExtra(Y_LABEL_INVEST);
            y_Label_current = getIntent().getIntegerArrayListExtra(Y_LABEL_CURRENT);
            chart.setVisibility(View.VISIBLE);
            buildGraph();
        } else {
            Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show();
            chart.setVisibility(View.GONE);
        }

        investCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            investLineDataSet.setVisible(isChecked);
            chart.invalidate();
        });

        currentCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentLineDataSet.setVisible(isChecked);
            chart.invalidate();
        });
    }

    private void buildGraph() {

        chart.setBackgroundColor(Color.parseColor("#222222"));
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        XAxis xAxis;
        xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#FFFF00"));
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setTextSize(10f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                super.getFormattedValue(value);
                return xLabels.get((int) value - 1);
            }
        });

        YAxis yAxis;
        yAxis = chart.getAxisLeft();
        yAxis.setTextColor(Color.parseColor("#FFFF00"));
        chart.getAxisRight().setEnabled(false);
        yAxis.setTextSize(10f);
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setAxisMaximum(1500f);
        yAxis.setAxisMinimum(700f);

        setData();

        chart.animateX(1500);
        Legend l = chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.parseColor("#FFFFFF"));
        l.setTextSize(15f);
    }

    private void setData() {
        ArrayList<Entry> investValues = new ArrayList<>();
        ArrayList<Entry> currentValues = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            investValues.add(new Entry(i + 1, yLabel_invest.get(i)));
            currentValues.add(new Entry(i + 1, y_Label_current.get(i)));
        }

        investLineDataSet = getSet(investValues, "Invest", Color.parseColor("#FFFF00"));
        currentLineDataSet = getSet(currentValues, "Current", Color.parseColor("#00FF00"));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(investLineDataSet);
        dataSets.add(currentLineDataSet);

        LineData data = new LineData(dataSets);

        chart.setData(data);
    }

    private LineDataSet getSet(ArrayList<Entry> values, String lineLabel, int color) {
        LineDataSet set = new LineDataSet(values, lineLabel);

        set.setDrawIcons(false);
        set.setColor(color);
        set.setCircleColor(color);

        set.setLineWidth(2f);
        set.setCircleRadius(5f);

        set.setDrawCircleHole(false);

        set.setFormLineWidth(1f);
        set.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set.setFormSize(15.f);

        set.setValueTextSize(0f);

        set.setDrawFilled(true);
        set.setFillFormatter((dataSet, dataProvider) -> chart.getAxisLeft().getAxisMinimum());

        set.setFillColor(Color.parseColor("#B6B6B6"));

        return set;
    }
}
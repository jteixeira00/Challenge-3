
package com.example.challenge3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;


import java.util.ArrayList;
import java.util.List;

/**
 * Example of a heavily customized {@link LineChart} with limit lines, custom line shapes, etc.
 *
 * @since 1.7.4
 * @version 3.1.0
 */
public class LineChartActivity1 extends AppCompatActivity implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private LineChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    private LineDataSet set1, set2;
    Button btnData;
    LineData data;
    ToggleButton tempBtn, humBtn, btnLed;
    boolean tempBool, humBool;
    int lastTemp, lastHum;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart);

        setTitle("LineChartActivity1");
        btnData = findViewById(R.id.btnAdd);
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set1.addEntry(new Entry(6, 70));
                data.notifyDataChanged();
                chart.notifyDataSetChanged();
                chart.invalidate();
                Log.d("myTag", "btn clicked");
            }
        });
        tempBool = true;
        humBool = true;
        tempBtn = findViewById(R.id.tempBtn);
        humBtn = findViewById(R.id.humBtn);

        tempBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(tempBtn.isChecked()){
                    //Button is ON
                    // Do Something
                    data.removeDataSet(set1);
                    data.notifyDataChanged();
                    chart.notifyDataSetChanged();
                    chart.invalidate();
                    tempBool = false;

                }
                else{
                    data.addDataSet(set1);
                    data.notifyDataChanged();
                    chart.notifyDataSetChanged();
                    chart.invalidate();
                    tempBool = true;
                }
            }
        });
        humBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(humBtn.isChecked()){
                    //Button is ON
                    // Do Something
                    data.removeDataSet(set2);
                    data.notifyDataChanged();
                    chart.notifyDataSetChanged();
                    chart.invalidate();
                    humBool =  false;
                }
                else{
                    data.addDataSet(set2);
                    data.notifyDataChanged();
                    chart.notifyDataSetChanged();
                    chart.invalidate();
                    humBool = true;
                }

            }
        });
        btnLed = findViewById(R.id.ledBtn);
        btnLed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(tempBtn.isChecked()){
                }
                else{
                }
            }
        });


        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarX.setMax(100);
        seekBarX.setMin(-20);
        seekBarX.setOnSeekBarChangeListener(this);

        seekBarY = findViewById(R.id.seekBar2);
        seekBarY.setMax(100);
        seekBarY.setMin(1);
        seekBarY.setOnSeekBarChangeListener(this);

        // // Chart Style // //
        {
            chart = findViewById(R.id.chart1);
            // background color
            chart.setBackgroundColor(Color.WHITE);
            // disable description text
            chart.getDescription().setEnabled(false);
            // enable touch gestures
            chart.setTouchEnabled(true);
            // set listeners
            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(false);
            // create marker to display box when values are selected
            //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
            // Set the marker to the chart
            //mv.setChartView(chart);
            //chart.setMarker(mv);
            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);
            // force pinch zoom along both axis
            chart.setPinchZoom(true);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();
            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(100f);
            yAxis.setAxisMinimum(-40f);
        }

        // add data
        seekBarX.setProgress(20);
        seekBarY.setProgress(40);
        setData(5, 100);

        // draw points over time
        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(LegendForm.LINE);
    }

    //private
    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) - 30;
            values.add(new Entry(i, val));
        }

        ArrayList<Entry> values2 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range) - 30;
            values2.add(new Entry(i, val));
        }


        // create a dataset and give it a type
        set1 = new LineDataSet(values, "Temperature (ºC)");
        set1.setDrawIcons(false);
        // draw dashed line
        set1.enableDashedLine(10f, 5f, 0f);
        // black lines and points
        set1.setColor(Color.parseColor("#B84748"));
        set1.setCircleColor(Color.parseColor("#B84748"));
        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        // draw points as solid circles
        set1.setDrawCircleHole(false);
        // customize legend entry
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        // text size of values
        set1.setValueTextSize(9f);

        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f);

        // set the filled area
        set1.setDrawFilled(false);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();
            }
        });


        //SET 2
        // create a dataset and give it a type
        set2 = new LineDataSet(values2, "Humidity (%)");
        set2.setDrawIcons(false);
        // draw dashed line
        set2.enableDashedLine(10f, 5f, 0f);
        // black lines and points
        set2.setColor(Color.parseColor("#47B8B7"));
        set2.setCircleColor(Color.parseColor("#47B8B7"));
        // line thickness and point size
        set2.setLineWidth(1f);
        set2.setCircleRadius(3f);
        // draw points as solid circles
        set2.setDrawCircleHole(false);
        // customize legend entry
        set2.setFormLineWidth(1f);
        set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set2.setFormSize(15.f);

        // text size of values
        set2.setValueTextSize(9f);

        // draw selection line as dashed
        set2.enableDashedHighlightLine(10f, 5f, 0f);

        // set the filled area
        set2.setDrawFilled(false);
        set2.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();
            }
        });

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets
        dataSets.add(set2);


        // create a data object with the data sets
        data = new LineData(dataSets);

        // set data
        chart.setData(data);
//        }

    }

    public void newEntry(int timestamp, int temp, int hum){
        if (tempBool == true){

        }

        if (humBool == true)


    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOW HIGH", "low: " + chart.getLowestVisibleX() + ", high: " + chart.getHighestVisibleX());
        Log.i("MIN MAX", "xMin: " + chart.getXChartMin() + ", xMax: " + chart.getXChartMax() + ", yMin: " + chart.getYChartMin() + ", yMax: " + chart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


}

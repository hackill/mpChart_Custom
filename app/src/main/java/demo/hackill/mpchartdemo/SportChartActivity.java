package demo.hackill.mpchartdemo;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.BarChart2Renderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.XAxisRendererBar2Chart;
import com.github.mikephil.charting.renderer.XAxisRendererBarChart;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import demo.hackill.view.MarkerBgShape;
import demo.hackill.view.MyYAxisValueFormatter;

/**
 * Created by hackill on 16/4/19.
 */
public class SportChartActivity extends FragmentActivity implements OnChartValueSelectedListener, View.OnClickListener {

    public final static String TAG = SportChartActivity.class.getSimpleName();

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };
    protected String[] mWeeks = new String[]{
            "MON", "TUE", "WEB", "THR", "FRI", "SAT", "SUN"
    };

    protected String[] Hours = new String[]{
            "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "23:00", "23:59"
    };
    protected String[] DAY = new String[]{
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"
            , "25", "26", "27", "28", "29", "30", "31"
    };

    protected BarChart mChart;
    @Bind(R.id.day)
    Button day;
    @Bind(R.id.week)
    Button week;
    @Bind(R.id.month)
    Button month;
    private Typeface mTf;
    private SeekBar mSeekBarX;

    private int type = 1;

    private int max = 300;
    private int xCount = 24;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sport);
        ButterKnife.bind(this);

        mChart = (BarChart) findViewById(R.id.energy_chart);
        mChart.setOnChartValueSelectedListener(this);

        day.setOnClickListener(this);
        week.setOnClickListener(this);
        month.setOnClickListener(this);


        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");


        MarkerView mv = new SportMarkerView(this);

        // set the marker to the chart
        mChart.setMarkerView(mv);


        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDragEnabled(true);
        mChart.setTouchEnabled(true);


        mChart.setScaleEnabled(false);


        mChart.setDrawGridBackground(true);

//        mChart.setViewPortOffsets(0,0,0,0);
        mChart.setExtraTopOffset(25);

//        mChart.setExtraRightOffset(35);

        mChart.setHighlightPerDragEnabled(true);

        mChart.setGridBackgroundColor(getResources().getColor(R.color.transparent));

        BarChart2Renderer cbcr = new BarChart2Renderer(mChart, mChart.getAnimator(), mChart.getViewPortHandler());
//        cbcr.setShader(getResources().getColor(R.color.step_top), getResources().getColor(R.color.step_bottom));
        mChart.setRenderer(cbcr);

        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(getResources().getColor(R.color.white));


        YAxisValueFormatter custom = new MyYAxisValueFormatter();


        LimitLine ll1 = new LimitLine(max / 3);
        ll1.setLineWidth(0.6f);
        ll1.enableDashedLine(8f, 8f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setLineColor(getResources().getColor(R.color.white));
        ll1.setTypeface(mTf);

        LimitLine ll2 = new LimitLine(max / 3 * 2);
        ll2.setLineWidth(0.6f);
        // 绘制虚线 长度, 宽度 ,起始位置
        ll2.enableDashedLine(8f, 8f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setLineColor(getResources().getColor(R.color.white));
        ll2.setTextSize(10f);
        ll2.setTypeface(mTf);

        LimitLine ll3 = new LimitLine(max);
        ll3.setLineWidth(0.6f);
        // 绘制虚线 长度, 宽度 ,起始位置
        ll3.enableDashedLine(8f, 8f, 0f);
//        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll3.setLineColor(getResources().getColor(R.color.white));
        ll3.setTextSize(10f);
        ll3.setTypeface(mTf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTypeface(mTf);
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);
        leftAxis.setTextColor(getResources().getColor(R.color.white));
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(4, true);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextSize(8f);
        leftAxis.setYOffset(8f);
//        leftAxis.setXOffset(5f);

        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaxValue(max);
        leftAxis.setDrawAxisLine(false);
        leftAxis.getAxisDependency();

//        leftAxis.setDrawTopYLabelEntry(false);

        leftAxis.setShowOnlyMinMax(true);


        YAxis rightAxis = mChart.getAxisRight();

        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setEnabled(false);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        setData(xCount, max, type);
        updateLeftAxis(type);

        mSeekBarX.setProgress(24);
        mSeekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setData(mSeekBarX.getProgress() + 1, max, type);
                mChart.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void updateLeftAxis(int type) {

        LimitLine ll1 = new LimitLine(max / 3);
        ll1.setLineWidth(0.6f);
        ll1.enableDashedLine(8f, 8f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setLineColor(getResources().getColor(R.color.white));
        ll1.setTypeface(mTf);

        LimitLine ll2 = new LimitLine(max / 3 * 2);
        ll2.setLineWidth(0.6f);
        // 绘制虚线 长度, 宽度 ,起始位置
        ll2.enableDashedLine(8f, 8f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setLineColor(getResources().getColor(R.color.white));
        ll2.setTextSize(10f);
        ll2.setTypeface(mTf);

        LimitLine ll3 = new LimitLine(max);
        ll3.setLineWidth(0.6f);
        // 绘制虚线 长度, 宽度 ,起始位置
        ll3.enableDashedLine(8f, 8f, 0f);
//        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll3.setLineColor(getResources().getColor(R.color.white));
        ll3.setTextSize(10f);
        ll3.setTypeface(mTf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMaxValue(max);
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);

        XAxis xAxis = mChart.getXAxis();

        XAxisRenderer xAxisRenderer = null;
        if (type == 1) {
            xAxis.resetLabelsToSkip();
            xAxisRenderer = new XAxisRendererBar2Chart(mChart.getViewPortHandler(), mChart.getXAxis(), mChart.getTransformer(YAxis.AxisDependency.LEFT), mChart);
        } else if (type == 2) {
            xAxis.setLabelsToSkip(0);
            xAxisRenderer = new XAxisRendererBarChart(mChart.getViewPortHandler(), mChart.getXAxis(), mChart.getTransformer(YAxis.AxisDependency.LEFT), mChart);
        } else if (type == 3) {
            xAxis.setLabelsToSkip(1);
            xAxisRenderer = new XAxisRendererBarChart(mChart.getViewPortHandler(), mChart.getXAxis(), mChart.getTransformer(YAxis.AxisDependency.LEFT), mChart);
        }
        mChart.setXAxisRenderer(xAxisRenderer);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.day:
                type = 1;
                xCount = 24;
                max = 300;
                break;
            case R.id.week:
                type = 2;
                xCount = 7;
                max = 900;
                break;
            case R.id.month:
                type = 3;
                xCount = 30;
                max = 1200;
                break;
        }
        updateLeftAxis(type);
        setData(xCount, max, type);
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.d(TAG, "onValueSelected() called with: " + "e = [" + e + "], dataSetIndex = [" + dataSetIndex + "], h = [" + h + "]");

    }

    @Override
    public void onNothingSelected() {
        Log.d(TAG, "onNothingSelected() called with: " + "");

    }


    private void setData(int count, float range, int type) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            if (type == 1) {
                xVals.add(Hours[i % 24]);
            } else if (type == 2) {
                xVals.add(mWeeks[i % 7]);
            } else {
                xVals.add(DAY[i % 31]);
            }
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
//        ArrayList<BarEntry> yVals2 = new ArrayList<>();


        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            if (i == 3) {
                val = 1;
            } else if (i == count - 1) {
                val = 100;
            }

//            if (i > count - 6 && i < count - 3) {
//                continue;
//            }
            yVals1.add(new BarEntry(val, i));
        }

//
//        float mult = (range + 1);
//        float val = (float) (Math.random() * mult);
//        yVals2.add(new BarEntry(val, count - 2));
//        val = (float) (Math.random() * mult);
//        yVals2.add(new BarEntry(val, count - 1));

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);
        set1.setColor(getResources().getColor(R.color.white));
        set1.setHighLightColor(getResources().getColor(R.color.item_selected));

//        BarDataSet set2 = new BarDataSet(yVals2, "dataSet2");
//        set2.setBarSpacePercent(35f);


        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
//        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);

        mChart.setData(data);
    }


    public class SportMarkerView extends MarkerView {


        @Bind(R.id.tv_steps)
        TextView tvSteps;
        @Bind(R.id.bg)
        LinearLayout bg;

        /**
         * Constructor. Sets up the MarkerView with a custom layout resource.
         *
         * @param context
         */
        public SportMarkerView(Context context) {
            super(context, R.layout.mark_view_linechart);
            ButterKnife.bind(this);
            bg.setBackground(new ShapeDrawable(new MarkerBgShape(1, getResources().getColor(R.color.white))));
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            highlight.getXIndex();
            tvSteps.setText("步数:" + highlight.getXIndex() + "步");
        }

        @Override
        public int getXOffset(float translateX, int pWidth) {
            Log.d(TAG, "getXOffset() called with: " + "translateX = [" + translateX + "], pWidth = [" + pWidth + "]");
            if (translateX + getWidth() > pWidth) {
                Log.i(TAG, "getXOffset: ......");
                bg.setBackground(new ShapeDrawable(new MarkerBgShape(0, getResources().getColor(R.color.white))));
                return -getWidth();
            } else {
                bg.setBackground(new ShapeDrawable(new MarkerBgShape(1, getResources().getColor(R.color.white))));
                return 0;
            }

        }

        @Override
        public int getYOffset(float translateY, int markViewHeight) {
            Log.d(TAG, "getYOffset() called with: " + "translateY = [" + translateY + "], pHeight = [" + markViewHeight + "]");
            return (int) -translateY - markViewHeight / 4;
        }

        @Override
        public int getTipYOffSet(float translateY, int markViewHeight, float containerTop) {
            Log.d(TAG, "getTipYOffSet() called with: " + "translateY = [" + translateY + "], markViewHeight = [" + markViewHeight + "], containerTop = [" + containerTop + "]");
            return (int) (-translateY - markViewHeight / 2 + containerTop);
        }
    }



}

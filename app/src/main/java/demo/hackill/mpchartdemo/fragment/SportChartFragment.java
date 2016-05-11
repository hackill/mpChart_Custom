package demo.hackill.mpchartdemo.fragment;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.XAxisRendererBarChart;
import com.github.mikephil.charting.renderer.custom.BarChartSportRenderer;
import com.github.mikephil.charting.renderer.custom.YAxisSportRenderer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import demo.hackill.mpchartdemo.R;
import demo.hackill.view.MarkerBgShape;
import demo.hackill.view.MyYAxisValueFormatter;

/**
 * 初始化控件层
 * Created by hackill on 16/4/19.
 */
public class SportChartFragment extends SportChartBaseFragment implements OnChartValueSelectedListener, OnChartGestureListener {

    public final static String TAG = SportChartFragment.class.getSimpleName();

    protected String[] mWeeks = new String[]{
            "MON", "TUE", "WEB", "THR", "FRI", "SAT", "SUN"
    };
    protected String[] Hours = new String[]{
            "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "23:00", "23:59"
    };
    protected String[] MONTH = new String[]{
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"
            , "25", "26", "27", "28", "29", "30", "31"
    };


    protected int mMaxYAxis = 300;
    protected int mXAxisCount = 24;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBarData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    protected void initBarData() {
        mMaxYAxis = 300;
        mXAxisCount = 24;
    }

    private BarChart mBarChart;

    protected void initBarChart(final BarChart mChart, final SeekBar mSeekBarX) {
        mBarChart = mChart;
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        mChart.setNoDataTextDescription("no data");
        mChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.d(TAG, "onChartGestureStart() called with: " + "me = [" + me + "], lastPerformedGesture = [" + lastPerformedGesture + "]");
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.d(TAG, "onChartGestureEnd() called with: " + "me = [" + me + "], lastPerformedGesture = [" + lastPerformedGesture + "]");
                mChart.highlightValue(null);
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

                Log.d(TAG, "onChartLongPressed() called with: " + "me = [" + me + "]");
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                Log.d(TAG, "onChartDoubleTapped() called with: " + "me = [" + me + "]");

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                Log.d(TAG, "onChartSingleTapped() called with: " + "me = [" + me + "]");

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
                Log.d(TAG, "onChartFling() called with: " + "me1 = [" + me1 + "], me2 = [" + me2 + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                Log.d(TAG, "onChartScale() called with: " + "me = [" + me + "], scaleX = [" + scaleX + "], scaleY = [" + scaleY + "]");

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                Log.d(TAG, "onChartTranslate() called with: " + "me = [" + me + "], dX = [" + dX + "], dY = [" + dY + "]");

            }
        });

        MarkerView mv = new SportMarkerView(getActivity());

        // set the marker to the chart
        mChart.setMarkerView(mv);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDragEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);

        // 自定义的 Y 轴绘制规则
        mChart.setRendererLeftYAxis(new YAxisSportRenderer(mChart.getViewPortHandler(), mChart.getAxisLeft(), mChart.getTransformer(YAxis.AxisDependency.LEFT)));
        //设置整个空间的边距
        //mChart.setViewPortOffsets(0,0,0,0);
        mChart.setHighlightPerDragEnabled(true);

        //自定义绘制数轴的Renderer
        BarChartSportRenderer cbcr = new BarChartSportRenderer(mChart, mChart.getAnimator(), mChart.getViewPortHandler());
        mChart.setRenderer(cbcr);
        //x轴绘制
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(getResources().getColor(R.color.white));

        // Y 左侧轴
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(4, true);
        leftAxis.setTextColor(getResources().getColor(R.color.white));
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setTextSize(8f);
        leftAxis.setYOffset(8f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaxValue(mMaxYAxis);
        leftAxis.setDrawAxisLine(false);
//        leftAxis.setShowOnlyMinMax(true);

        // Y 右侧轴 禁用
        mChart.getAxisRight().setEnabled(false);
        // 数据源 禁用
        mChart.getLegend().setEnabled(false);

        setData(mChart, mXAxisCount, mMaxYAxis);
        updateLeftAxis(mChart);

        mSeekBarX.setProgress(24);
        mSeekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setData(mChart, mSeekBarX.getProgress() + 1, mMaxYAxis);
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

    protected void updateLeftAxis(BarChart mChart) {

        LimitLine ll1 = new LimitLine(mMaxYAxis / 3);
        ll1.setLineWidth(0.6f);
        ll1.enableDashedLine(8f, 8f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setLineColor(getResources().getColor(R.color.white));

        LimitLine ll2 = new LimitLine(mMaxYAxis / 3 * 2);
        ll2.setLineWidth(0.6f);
        // 绘制虚线 长度, 宽度 ,起始位置
        ll2.enableDashedLine(8f, 8f, 0f);
//        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setLineColor(getResources().getColor(R.color.white));
        ll2.setTextSize(10f);
//        ll2.setTypeface(mTf);

        LimitLine ll3 = new LimitLine(mMaxYAxis);
        ll3.setLineWidth(0.6f);
        // 绘制虚线 长度, 宽度 ,起始位置
        ll3.enableDashedLine(8f, 8f, 0f);
//        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll3.setLineColor(getResources().getColor(R.color.white));
        ll3.setTextSize(10f);
//        ll3.setTypeface(mTf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMaxValue(mMaxYAxis);
        leftAxis.setAxisMinValue(0);
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setLabelsToSkip(0);
        mChart.setXAxisRenderer(new XAxisRendererBarChart(mChart.getViewPortHandler(), mChart.getXAxis(), mChart.getTransformer(YAxis.AxisDependency.LEFT), mChart));
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
    }

    @Override
    public void onNothingSelected() {
    }


    protected void setData(BarChart mChart, int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(MONTH[i % 31]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            if (i == 3 || i == 4 || i == 6) {
                val = 0;
            } else if (i == count - 1) {
                val = 100;
            }

            yVals1.add(new BarEntry(val, i));
        }


        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);
        set1.setColor(getResources().getColor(R.color.white));
        set1.setHighLightColor(getResources().getColor(R.color.item_selected));
        set1.setHighLightAlpha(255);
        set1.setDrawValues(false);

//        BarDataSet set2 = new BarDataSet(yVals2, "dataSet2");
//        set2.setBarSpacePercent(35f);


        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
//        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        mChart.setData(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * pop window
     */
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
            tvSteps.setText("步数:" + (int) e.getVal() + "步");
        }

        @Override
        public int getXOffset(float translateX, int pWidth) {
            if (translateX + getWidth() > pWidth) {
                bg.setBackground(new ShapeDrawable(new MarkerBgShape(0, getResources().getColor(R.color.white))));
                return -getWidth();
            } else {
                bg.setBackground(new ShapeDrawable(new MarkerBgShape(1, getResources().getColor(R.color.white))));
                return 0;
            }
        }

        @Override
        public int getYOffset(float translateY, int markViewHeight) {
            return (int) -translateY - markViewHeight / 4;
        }

        @Override
        public int getTipYOffSet(float translateY, int markViewHeight, float containerTop) {
            return (int) (-translateY - markViewHeight / 2 + containerTop);
        }
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        Log.d(TAG, "onChartGestureStart() called with: " + "me = [" + me + "], lastPerformedGesture = [" + lastPerformedGesture + "]");
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.d(TAG, "onChartGestureEnd() called with: " + "me = [" + me + "], lastPerformedGesture = [" + lastPerformedGesture + "]");
        mBarChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.d(TAG, "onChartLongPressed() called with: " + "me = [" + me + "]");

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.d(TAG, "onChartDoubleTapped() called with: " + "me = [" + me + "]");

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.d(TAG, "onChartSingleTapped() called with: " + "me = [" + me + "]");

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.d(TAG, "onChartFling() called with: " + "me1 = [" + me1 + "], me2 = [" + me2 + "], velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

        Log.d(TAG, "onChartScale() called with: " + "me = [" + me + "], scaleX = [" + scaleX + "], scaleY = [" + scaleY + "]");
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.d(TAG, "onChartTranslate() called with: " + "me = [" + me + "], dX = [" + dX + "], dY = [" + dY + "]");

    }

}

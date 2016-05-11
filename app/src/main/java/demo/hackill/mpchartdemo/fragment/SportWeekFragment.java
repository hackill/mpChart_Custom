package demo.hackill.mpchartdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.renderer.XAxisRendererBarChart;

import java.util.ArrayList;

import demo.hackill.mpchartdemo.R;

/**
 * Created by hackill on 16/5/10.
 */
public class SportWeekFragment extends SportChartFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initBarData() {
        mMaxYAxis = 900;
        mXAxisCount = 7;
    }

    @Override
    protected void updateLeftAxis(BarChart mChart) {

        LimitLine ll1 = new LimitLine(mMaxYAxis / 3);
        ll1.setLineWidth(0.6f);
        ll1.enableDashedLine(8f, 8f, 0f);
        ll1.setTextSize(10f);
        ll1.setLineColor(getResources().getColor(R.color.white));

        LimitLine ll2 = new LimitLine(mMaxYAxis / 3 * 2);
        ll2.setLineWidth(0.6f);
        // 绘制虚线 长度, 宽度 ,起始位置
        ll2.enableDashedLine(8f, 8f, 0f);
        ll2.setLineColor(getResources().getColor(R.color.white));
        ll2.setTextSize(10f);

        LimitLine ll3 = new LimitLine(mMaxYAxis);
        ll3.setLineWidth(0.6f);
        // 绘制虚线 长度, 宽度 ,起始位置
        ll3.enableDashedLine(8f, 8f, 0f);
        ll3.setLineColor(getResources().getColor(R.color.white));
        ll3.setTextSize(10f);

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
    protected void setData(BarChart mChart, int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(mWeeks[i % 7]);
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

}

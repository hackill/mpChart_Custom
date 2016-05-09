package com.github.mikephil.charting.renderer.custom;

import android.graphics.Canvas;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.renderer.XAxisRendererBarChart;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by hackill on 16/5/9.
 */
public class XAxisRendererSportBarChart extends XAxisRendererBarChart {

    public XAxisRendererSportBarChart(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans, BarChart chart) {
        super(viewPortHandler, xAxis, trans, chart);
    }

    @Override
    protected void drawLabels(Canvas c, float pos) {

        // pre allocate to save performance (dont allocate in loop)
        float[] position = new float[]{
                0f, 0f
        };

        BarData bd = mChart.getData();
        int step = bd.getDataSetCount();

        for (int i = mMinX; i < mMaxX; i++) {

            if (i == mMinX) {
                //起始点
            } else if (i == (mMinX + mMaxX) / 2) {
                // 中间点
                position[0] = i * step + i * bd.getGroupSpace()
                        + bd.getGroupSpace() / 2f;
                // consider groups (center label for each group)
                if (step > 1) {
                    position[0] += ((float) step - 1f) / 2f;
                }
            } else if (i == mMaxX - 1) {
            } else {
                continue;
            }
            position[0] = i * step + i * bd.getGroupSpace()
                    + bd.getGroupSpace() / 2f;
            // consider groups (center label for each group)
            if (step > 1) {
                position[0] += ((float) step - 1f) / 2f;
            }
            mTrans.pointValuesToPixel(position);
            String label = mXAxis.getValues().get(i);
            drawLabel(c, label, i, position[0], pos);
        }
    }
}

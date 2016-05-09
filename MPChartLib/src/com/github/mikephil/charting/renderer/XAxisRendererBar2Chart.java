package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class XAxisRendererBar2Chart extends XAxisRenderer {

    public final static String TAG = XAxisRendererBar2Chart.class.getSimpleName();

    protected BarChart mChart;

    public XAxisRendererBar2Chart(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans,
                                  BarChart chart) {
        super(viewPortHandler, xAxis, trans);

        this.mChart = chart;
    }

    /**
     * draws the x-labels on the specified y-position
     *
     * @param pos
     */
    @Override
    protected void drawLabels(Canvas c, float pos) {

        // pre allocate to save performance (dont allocate in loop)
        float[] position = new float[]{
                0f, 0f
        };

        BarData bd = mChart.getData();
        int step = bd.getDataSetCount();
        Log.i(TAG, "drawLabels: mMinx = " + mMinX + ", mMax = " + mMaxX + " modulus= " + mXAxis.mAxisLabelModulus);

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


            Log.i(TAG, "drawLabels: " + "postion[0]" + position[0] + ", isInboudnsX = " + mViewPortHandler.isInBoundsX(position[0]) + ", i = " + i);

            String label = mXAxis.getValues().get(i);

//            if (mXAxis.isAvoidFirstLastClippingEnabled()) {
//
//                // avoid clipping of the last
//                if (i == mXAxis.getValues().size() - 1) {
//                    float width = Utils.calcTextWidth(mAxisLabelPaint, label);
//
//                    if (width > mViewPortHandler.offsetRight() * 2
//                            && position[0] + width > mViewPortHandler.getChartWidth())
//                        position[0] -= width / 2;
//
//                    // avoid clipping of the first
//                } else if (i == 0) {
//
//                    float width = Utils.calcTextWidth(mAxisLabelPaint, label);
//                    position[0] += width / 2;
//                }
//            }

            drawLabel(c, label, i, position[0], pos);

        }
    }

    @Override
    public void renderGridLines(Canvas c) {
        if (!mXAxis.isDrawGridLinesEnabled() || !mXAxis.isEnabled())
            return;

        float[] position = new float[]{
                0f, 0f
        };

        mGridPaint.setColor(mXAxis.getGridColor());
        mGridPaint.setStrokeWidth(mXAxis.getGridLineWidth());

        BarData bd = mChart.getData();
        int step = bd.getDataSetCount();

        for (int i = mMinX; i < mMaxX; i += mXAxis.mAxisLabelModulus) {

            position[0] = i * step + i * bd.getGroupSpace() - 0.5f;

            mTrans.pointValuesToPixel(position);

            if (mViewPortHandler.isInBoundsX(position[0])) {

                c.drawLine(position[0], mViewPortHandler.offsetTop(), position[0],
                        mViewPortHandler.contentBottom(), mGridPaint);
            }
        }
    }
}

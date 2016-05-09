package demo.hackill.mpchartdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * @author rqg
 * @date 2/16/16.
 */
public class CustomBarChartRender extends BarChartRenderer {
    public final static String TAG = CustomBarChartRender.class.getSimpleName();

    protected RectF mTmpRect = new RectF();
    protected Shader mShader = null;

    protected int mTopColor, mBottomColor;

    protected BarChart smBarChart;

    public CustomBarChartRender(BarChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        smBarChart = chart;
//        Log.d(TAG, "CustomBarChartRender  " + viewPortHandler.getContentRect());
    }


    protected void drawBar(Canvas c, float left, float top, float right, float bottom) {
        mTmpRect.set(left, top, right, bottom);
        if (mTmpRect.width() > 30f) {
            float middle = (mTmpRect.left + mTmpRect.right) / 2f;
            mTmpRect.left = middle - 15f;
            mTmpRect.right = middle + 15f;
        }
        float rc = Math.min(mTmpRect.width(), mTmpRect.height()) / 2f;
        c.drawRoundRect(mTmpRect, rc, rc, mRenderPaint);
    }


    float mRadius = 15;

    protected void drawHighlightBar(Canvas c, float x) {
        mHighlightPaint.setColor(Color.RED);
        mHighlightPaint.setStrokeWidth(2);
        mHighlightPaint.setStrokeCap(Paint.Cap.ROUND);
        c.drawLine(x, mViewPortHandler.contentTop(), x, smBarChart.getHeight(), mHighlightPaint);
        c.drawCircle(x, mViewPortHandler.contentTop() - mRadius - 10, mRadius, mHighlightPaint);
    }


    @Override
    protected void drawDataSet(Canvas c, BarDataSet dataSet, int index) {
        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mShadowPaint.setColor(dataSet.getBarShadowColor());

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        List<BarEntry> entries = dataSet.getYVals();

        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setBarSpace(dataSet.getBarSpace());
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));

        buffer.feed(entries);

        trans.pointValuesToPixel(buffer.buffer);


//            mRenderPaint.setColor(dataSet.getColor());
        if (mShader == null) {
            mShader = getShader();
        }
        mRenderPaint.setShader(mShader);

        for (int j = 0; j < buffer.size(); j += 4) {

            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2]))
                continue;

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break;

            if (mChart.isDrawBarShadowEnabled()) {
                c.drawRect(buffer.buffer[j], mViewPortHandler.contentTop(),
                        buffer.buffer[j + 2],
                        mViewPortHandler.contentBottom(), mShadowPaint);
            }
//
//                c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
//                        buffer.buffer[j + 3], mRenderPaint);

            drawBar(c, buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                    buffer.buffer[j + 3]);
        }
    }


    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
        for (int i = 0; i < indices.length; i++) {

            BarDataSet set = mChart.getBarData().getDataSetByIndex(indices[i]
                    .getDataSetIndex());

            if (set == null || !set.isHighlightEnabled())
                continue;

            int xIndex = indices[i].getXIndex(); // get the
            // x-position

            if (xIndex > mChart.getXChartMax() * mAnimator.getPhaseX())
                continue;

            final float yVal = set.getYValForXIndex(xIndex);
            if (yVal == Float.NaN)
                continue;

            float y = yVal * mAnimator.getPhaseY(); // get
            // the
            // y-position

            float[] pts = new float[]{
                    xIndex, y
            };

            mChart.getTransformer(set.getAxisDependency()).pointValuesToPixel(pts);

            // draw the lines
            drawHighlightBar(c, pts[0]);
        }

    }


    /**
     * use for draw bar
     *
     * @param top    top color
     * @param bottom bottom color
     */
    public void setShader(int top, int bottom) {
        mTopColor = top;
        mBottomColor = bottom;
        if (mShader != null) {
            mShader = getShader();
        }
    }


    private Shader getShader() {
        RectF contentRect = mViewPortHandler.getContentRect();
        return new LinearGradient
                (contentRect.left, contentRect.top, contentRect.left, contentRect.bottom
                        , mTopColor, mBottomColor
                        , Shader.TileMode.MIRROR);
    }
}

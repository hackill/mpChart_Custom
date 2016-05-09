package com.github.mikephil.charting.renderer.custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.interfaces.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BarChartSportRenderer extends BarChartRenderer {

    protected RectF mTmpRect = new RectF();
    protected RectF mTmpRectBottom = new RectF();
    protected RectF mTmpRectTop = new RectF();

    public BarChartSportRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    protected void drawBar(Canvas c, float left, float top, float right, float bottom) {
        mTmpRect.set(left, top, right, bottom);
        float rc = Math.min(mTmpRect.width(), mTmpRect.height()) / 2f;

        mTmpRectTop.set(left, top, right, top + 2 * rc);

        mTmpRectBottom.set(left, top + rc, right, bottom);

        c.drawRoundRect(mTmpRectTop, rc, rc, mRenderPaint);

        c.drawRect(mTmpRectBottom, mRenderPaint);

    }

    protected void drawHighlightBar(Canvas c) {
        float rc = Math.min(mBarRect.width(), mBarRect.height()) / 2f;

        mTmpRectTop.set(mBarRect.left, mBarRect.top, mBarRect.right, mBarRect.top + 2 * rc);

        mTmpRectBottom.set(mBarRect.left, mBarRect.top + rc, mBarRect.right, mBarRect.bottom);

        c.drawRoundRect(mTmpRectTop, rc, rc, mHighlightPaint);

        c.drawRect(mTmpRectBottom, mHighlightPaint);

        mHighlightPaint.setColor(Color.WHITE);
        mHighlightPaint.setStrokeWidth(2);
        mHighlightPaint.setStrokeCap(Paint.Cap.ROUND);

        c.drawLine((mBarRect.left + mBarRect.right) / 2, mViewPortHandler.contentTop(), (mBarRect.left + mBarRect.right) / 2, mViewPortHandler.contentBottom(), mHighlightPaint);
    }

}

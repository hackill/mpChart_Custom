package com.github.mikephil.charting.renderer.custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.interfaces.BarDataProvider;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BarChartSportRenderer extends BarChartRenderer {

    protected RectF mTmpRect = new RectF();
    protected RectF mTmpRectBottom = new RectF();
    protected RectF mTmpRectTop = new RectF();
    protected RectF mRectBottom = new RectF();

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


        float pos = Utils.convertDpToPixel(1);

        mRectBottom.set(left, bottom - pos, right, bottom);

        c.drawRect(mRectBottom, mRenderPaint);

    }


    protected void drawHighlightBar(Canvas c) {

        float rc = Math.min(mBarRect.width(), mBarRect.height()) / 2f;

        mTmpRectTop.set(mBarRect.left, mBarRect.top, mBarRect.right, mBarRect.top + 2 * rc);

        mTmpRectBottom.set(mBarRect.left, mBarRect.top + rc, mBarRect.right, mBarRect.bottom);

        float pos = Utils.convertDpToPixel(1);

        mRectBottom.set(mBarRect.left, mBarRect.bottom - pos, mBarRect.right, mBarRect.bottom);

        c.drawRoundRect(mTmpRectTop, rc, rc, mHighlightPaint);

        c.drawRect(mTmpRectBottom, mHighlightPaint);

        mHighlightPaint.setColor(Color.WHITE);

        c.drawRect(mRectBottom, mHighlightPaint);

        mHighlightPaint.setStrokeWidth(2);

        c.drawLine((mBarRect.left + mBarRect.right) / 2, mViewPortHandler.contentTop(), (mBarRect.left + mBarRect.right) / 2, mBarRect.bottom, mHighlightPaint);
    }

}

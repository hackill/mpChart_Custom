package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.interfaces.BarDataProvider;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by hackill on 16/4/28.
 */
public class BarChartExtend extends BarChart2Renderer {

    public BarChartExtend(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    protected void drawBar(Canvas c, float left, float top, float right, float bottom) {
        super.drawBar(c, left, top, right, bottom);

        mTmpRect.set(left, top, right, bottom);
        Log.i(TAG, "drawBar: " + mTmpRect.width() + " height = " + mTmpRect.height());
        float rc = Math.min(mTmpRect.width(), mTmpRect.height()) / 2f;

//
//        mTmpRect2.set(left, top + rc, right, bottom);
//
//        c.drawRoundRect(mTmpRect, rc, rc, mRenderPaint);
//
//
////
//        mRenderPaint.setColor(Color.rgb(153, 215, 34));
//        c.drawRect(mTmpRect2, mRenderPaint);
//
//        c.drawRect(left, top, right, bottom, mRenderPaint);
    }
}

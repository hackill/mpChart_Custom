package demo.hackill.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import demo.hackill.mpchartdemo.R;


/**
 * @author hackill
 */
public class MarkerBgView extends LinearLayout {

    public static final String TAG = MarkerBgView.class.getSimpleName();

    private Paint mBgPaint;

    public MarkerBgView(Context context) {
        this(context, null);
    }

    public MarkerBgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarkerBgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int viewWidth = 0;
    private int viewHeight = 0;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        viewHeight = getDefaultSize(getSuggestedMinimumWidth(), heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(getContext().getResources().getColor(R.color.white));
        mBgPaint.setStyle(Paint.Style.STROKE);

        setMeasuredDimension(viewWidth, viewHeight);

    }


    TextView tvSteps;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvSteps = (TextView) findViewById(R.id.tv_steps);
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: ");
        // 绘制背景
//        updateRect();
//        canvas.drawRect(mProgressRect, mRectPaint);
        drawBg(canvas, BgType.LEFT);
        super.onDraw(canvas);
    }

    private Rect bgRect = new Rect();

    private void drawBg(Canvas canvas, BgType type) {
        if (type == BgType.LEFT) {
            canvas.drawCircle(viewHeight / 2, viewHeight / 2, viewHeight / 2, mBgPaint);
            renderRect(type);
            canvas.drawRect(bgRect, mBgPaint);
            Path path = new Path();
            path.reset();
            path.moveTo(viewWidth - viewHeight, 0);
            path.lineTo(viewWidth, viewHeight / 2);
            path.lineTo(viewWidth - viewHeight, viewHeight);
            path.close();
            canvas.drawPath(path, mBgPaint);

        }

    }

    private void renderRect(BgType type) {
        if (type == BgType.LEFT) {
            bgRect.left = viewHeight / 2;
            bgRect.top = 0;
            bgRect.right = viewWidth - 2 * viewHeight;
            bgRect.bottom = viewHeight;
        }
    }


    private AnimatorSet mAnimatorSet = new AnimatorSet();

    private void smoothRefreshReset() {

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 1).setDuration(500);

        animator.setInterpolator(new AccelerateInterpolator());

        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(animator);
        mAnimatorSet.start();
    }

    enum BgType {
        LEFT, RIGHT
    }
}

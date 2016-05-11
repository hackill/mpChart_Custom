package demo.hackill.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 长按时不能滑动 事件直接传递到下层
 */
public class NoLongPressViewPager extends ViewPager {

    private boolean isLongPressed = false;
    private static final long LONG_PRESS_TIME = 100;
    private float mLastMotionX, mLastMotionY;
    private boolean isMoved;
    private Runnable mLongPressRunnable;
    private static final int TOUCH_SLOP = 20;


    public NoLongPressViewPager(Context context) {
        this(context, null);
    }

    public NoLongPressViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        mLongPressRunnable = new Runnable() {

            @Override
            public void run() {
                isLongPressed = true;
            }
        };
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                isLongPressed = false;
                mLastMotionX = x;
                mLastMotionY = y;
                isMoved = false;
                postDelayed(mLongPressRunnable, LONG_PRESS_TIME);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoved)
                    break;
                if (Math.abs(mLastMotionX - x) > TOUCH_SLOP
                        || Math.abs(mLastMotionY - y) > TOUCH_SLOP) {
                    //移动超过阈值，则表示移动了
                    isMoved = true;
                    removeCallbacks(mLongPressRunnable);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                removeCallbacks(mLongPressRunnable);
                isLongPressed = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return !isLongPressed && super.onInterceptTouchEvent(ev);
    }
}

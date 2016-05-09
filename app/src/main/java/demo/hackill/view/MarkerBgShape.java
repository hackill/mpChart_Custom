package demo.hackill.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.shapes.Shape;

/**
 * marker view bg
 * Created by hackill on 16/5/9.
 */
public class MarkerBgShape extends Shape {


    private Rect bgRect = new Rect();

    private Path trianglePath = new Path();

    private int viewWidth = 0, viewHeight = 0;

    private int backgroundColor;
    private int type = 0;


    public MarkerBgShape(int type, int bgColor) {
        this.type = type;
        backgroundColor = bgColor;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {

        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        int mathLength = (int) (Math.sin(Math.PI / 4) * viewHeight);
        if (type == 0) {
            canvas.drawCircle(viewHeight / 2, viewHeight / 2, viewHeight / 2, paint);
            bgRect.left = viewHeight / 2;
            bgRect.top = 0;
            bgRect.right = viewWidth - mathLength;
            bgRect.bottom = viewHeight;
            canvas.drawRect(bgRect, paint);
            trianglePath.reset();
            trianglePath.moveTo(viewWidth - mathLength, 0);
            trianglePath.lineTo(viewWidth, viewHeight / 2);
            trianglePath.lineTo(viewWidth - mathLength, viewHeight);
            trianglePath.close();
            canvas.drawPath(trianglePath, paint);
        } else {
            canvas.drawCircle(viewWidth - viewHeight / 2, viewHeight / 2, viewHeight / 2, paint);
            bgRect.left = mathLength;
            bgRect.top = 0;
            bgRect.right = viewWidth - viewHeight / 2;
            bgRect.bottom = viewHeight;
            canvas.drawRect(bgRect, paint);

            trianglePath.reset();
            trianglePath.moveTo(mathLength, 0);
            trianglePath.lineTo(0, viewHeight / 2);
            trianglePath.lineTo(mathLength, viewHeight);
            trianglePath.close();
            canvas.drawPath(trianglePath, paint);
        }
    }


    @Override
    protected void onResize(float width, float height) {
        viewWidth = (int) width;
        viewHeight = (int) height;
    }
}
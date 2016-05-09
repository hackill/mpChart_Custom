package demo.hackill.mpchartdemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import demo.hackill.view.MarkerBgShape;


public class BgActivity extends AppCompatActivity {


    public final static String TAG = BgActivity.class.getSimpleName();

    private LinearLayout contentView;
    int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bg);
        setTitle("MPAndroidChart Example");

        type = 1;

        contentView = (LinearLayout) findViewById(R.id.bg);

        contentView.setBackground(new ShapeDrawable(new MarkerBgShape(type, getResources().getColor(R.color.white))));

        findViewById(R.id.ddd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    type = 0;
                } else {
                    type = 1;
                }
                contentView.setBackground(new ShapeDrawable(new MarkerBgShape(type, getResources().getColor(R.color.white))));
            }
        });
    }



}

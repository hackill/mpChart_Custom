package demo.hackill.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by hackill on 16/1/19.
 */
public class ScalePageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        final float normalizedposition = Math.abs(Math.abs(position) - 1);
        page.setScaleX(normalizedposition / 2 + 0.5f);
        page.setScaleY(normalizedposition / 2 + 0.5f);
    }
}

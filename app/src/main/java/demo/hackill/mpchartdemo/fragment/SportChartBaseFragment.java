package demo.hackill.mpchartdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.BarChart;

import butterknife.Bind;
import butterknife.ButterKnife;
import demo.hackill.mpchartdemo.R;
import demo.hackill.view.NoLongPressViewPager;

/**
 * Created by hackill on 16/4/19.
 */
public abstract class SportChartBaseFragment extends Fragment {

    public final static String TAG = SportChartBaseFragment.class.getSimpleName();

    @Bind(R.id.view_pager)
    NoLongPressViewPager viewPager;

    SportChartPagerAdapter sportChartPagerAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contactsLayout = inflater.inflate(R.layout.activity_sport_viewpager,
                container, false);
        ButterKnife.bind(this, contactsLayout);
        initView();
        return contactsLayout;
    }

    private void initView() {
        sportChartPagerAdapter = new SportChartPagerAdapter();
        viewPager.setAdapter(sportChartPagerAdapter);
        viewPager.setCurrentItem(sportChartPagerAdapter.getCount());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    abstract void initBarChart(BarChart barChart, SeekBar seekBar);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called with: " + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public class SportChartPagerAdapter extends PagerAdapter {

        public static final int MAX_ITEMS = 10;

        public static final int ITEM_SIZE = 4;

        View[] items = new View[ITEM_SIZE];

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Log.i(TAG, "instantiateItem: .....");

            View v = getItem(container.getContext(), position);

            if (container.indexOfChild(v) != -1) {
                //View 已添加则重新创建
                items[position % ITEM_SIZE] = null;
                v = getItem(container.getContext(), position);
            }

            container.addView(v);

            BarChart barChart = (BarChart) v.findViewById(R.id.energy_chart);
            SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekBar1);
            initBarChart(barChart, seekBar);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.d(TAG, "destroyItem() called with: " + "container = [" + container + "], position = [" + position + "], object = [" + object + "]");
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public void finishUpdate(ViewGroup container) {
        }

        @Override
        public int getCount() {
            return MAX_ITEMS;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        private View getItem(Context context, int position) {
            int internalPos = position % ITEM_SIZE;
            Log.i(TAG, "getItem: 获取item ");
            if (items[internalPos] == null) {

                Log.d(TAG, "getItem() called with: " + "context = [" + context + "], position = [" + position + "]");
                final LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.activity_sport, null);

                items[internalPos] = view;
            }

            return items[internalPos];
        }
    }


}

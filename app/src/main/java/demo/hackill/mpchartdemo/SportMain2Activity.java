package demo.hackill.mpchartdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import demo.hackill.SportType;
import demo.hackill.mpchartdemo.fragment.SportDayFragment;
import demo.hackill.mpchartdemo.fragment.SportMonthFragment;
import demo.hackill.mpchartdemo.fragment.SportWeekFragment;
import demo.hackill.view.PagerCheckTabStrip;


public class SportMain2Activity extends AppCompatActivity {


    public final static String TAG = SportMain2Activity.class.getSimpleName();

    @Bind(R.id.tabs)
    PagerCheckTabStrip tabs;
//    @Bind(R.id.headerViewPager)
//    ViewPager headerViewPager;


    private ViewPagerAdapter headerViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_main);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        headerViewPagerAdapter = new ViewPagerAdapter(fragmentManager);


        headerViewPagerAdapter.addFragment(new SportDayFragment());
        headerViewPagerAdapter.addFragment(new SportWeekFragment());
        headerViewPagerAdapter.addFragment(new SportMonthFragment());
//        headerViewPager.setPageTransformer(true, new ScalePageTransformer());
//        headerViewPager.setAdapter(headerViewPagerAdapter);

        tabs.setItems(new ArrayList<String>(Arrays.asList("天", "周", "月")) {
        });

        tabs.setCheckListener(new PagerCheckTabStrip.OnItemCheckListener() {
            @Override
            public void onCheck(int index) {
//                headerViewPager.setCurrentItem(index, false);
            }
        });
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {


        private ArrayList<Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        public void addFragment(Fragment fragmentToAdd) {
            fragments.add(fragmentToAdd);
            notifyDataSetChanged();
        }

        public void setCurrentItem(int index, SportType type) {
            getItem(index);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}

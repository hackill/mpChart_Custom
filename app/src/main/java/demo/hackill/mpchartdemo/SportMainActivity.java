package demo.hackill.mpchartdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import demo.hackill.mpchartdemo.fragment.SportChartFragment;
import demo.hackill.mpchartdemo.fragment.SportDayFragment;
import demo.hackill.mpchartdemo.fragment.SportMonthFragment;
import demo.hackill.mpchartdemo.fragment.SportWeekFragment;
import demo.hackill.view.PagerCheckTabStrip;


public class SportMainActivity extends AppCompatActivity {


    public final static String TAG = SportMainActivity.class.getSimpleName();

    @Bind(R.id.tabs)
    PagerCheckTabStrip tabs;

    SportChartFragment fragment;
    int curIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_main);
        ButterKnife.bind(this);

        tabs.setItems(new ArrayList<String>(Arrays.asList("天", "周", "月")) {
        });

        tabs.setCheckListener(new PagerCheckTabStrip.OnItemCheckListener() {
            @Override
            public void onCheck(int index) {
                if (curIndex != index) {
                    setCurrentFragment(index);
                }
            }
        });

        setCurrentFragment(0);
    }

    private void setCurrentFragment(int index) {
        curIndex = index;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (index == 0) {
            fragment = new SportDayFragment();
        } else if (index == 1) {
            fragment = new SportWeekFragment();
        } else {
            fragment = new SportMonthFragment();
        }
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}

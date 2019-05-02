package com.febers.iuestc.home.view;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.Constants;
import com.febers.iuestc.entity.EventTheme;
import com.febers.iuestc.util.SPUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.ISupportFragment;

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    private static final String TAG = "HomeActivity";

    private Boolean themeChange = false;
    private BottomNavigationBar bottomNavigationBar;
    private List<ISupportFragment> fragmentList = new ArrayList<>();

    @Override
    protected int setView() {
        return R.layout.activity_home;
    }

    @Override
    protected Boolean registerEventBus() {
        return true;
    }

    @Override
    protected void findViewById() {
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
    }

    @Override
    protected void initView() {
        int openPosition = 0;
        if (!SPUtil.INSTANCE().get(Constants.IS_LOGIN, false)) {
            openPosition = 2;
        }

        ISupportFragment firstFragment = findFragment(HomeFirstContainer.class);
        if (firstFragment == null) {
            fragmentList.add(0, new HomeFirstContainer());
            fragmentList.add(1, new HomeSecondContainer());
            fragmentList.add(2, new HomeThirdContainer());
            loadMultipleRootFragment(R.id.home_fragment_layout, openPosition,
                    fragmentList.get(0), fragmentList.get(1), fragmentList.get(2));
        } else {
            fragmentList.add(0, firstFragment);
            fragmentList.add(1, findFragment(HomeSecondContainer.class));
            fragmentList.add(2, findFragment(HomeThirdContainer.class));
        }
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setAutoHideEnabled(true);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_course_bottom_gray,
                        getString(R.string.home_course_table)))
                .addItem(new BottomNavigationItem(R.drawable.ic_all_pink,
                        getString(R.string.home_library)))
                .addItem(new BottomNavigationItem(R.drawable.ic_more_bottom_gray,
                        getString(R.string.home_more)))
                .setFirstSelectedPosition(openPosition)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(int position) {
        showHideFragment(fragmentList.get(position));
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.THEME_CHANGED, themeChange);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            themeChange = savedInstanceState.getBoolean(Constants.THEME_CHANGED, false);
            if (themeChange) {
                bottomNavigationBar.selectTab(2);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void themeChange(EventTheme eventTheme) {
        if (eventTheme.getThemeChanged()) {
            themeChange = true;
            recreate();
        }
    }
}

package com.febers.iuestc.module.grade.view;

import android.view.Menu;
import android.view.MenuItem;

import com.febers.iuestc.R;
import com.febers.iuestc.view.adapter.AdapterGradeViewPager;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.view.custom.CustomViewPager;
import com.febers.iuestc.view.manager.GradeFragmentManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

public class GradeActivity extends BaseSwipeActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "GradeActivity";
    private TabLayout tlGrade;
    private CustomViewPager vpGrade;
    private AdapterGradeViewPager adapterGradeViewPager;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected int setView() {
        return R.layout.activity_grade;
    }

    @Override
    protected int setToolbar() {
        return R.id.tb_grade;
    }

    @Override
    protected String setToolbarTitle() {
        return "我的成绩";
    }

    @Override
    protected void findViewById() {
        vpGrade = findViewById(R.id.vp_grade);
        tlGrade = findViewById(R.id.tl_grade);
        navigationView = findViewById(R.id.nv_grade);
    }

    @Override
    protected void initView() {
        vpGrade.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vpGrade.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpGrade.setOffscreenPageLimit(2);
        adapterGradeViewPager = new AdapterGradeViewPager(getSupportFragmentManager());
        vpGrade.setAdapter(adapterGradeViewPager);
        tlGrade.setupWithViewPager(vpGrade);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.item_grade_all);
        drawerLayout = findViewById(R.id.dl_grade);
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        GradeListFragment listFragment = (GradeListFragment) GradeFragmentManager.getInstance(0);
        switch (item.getItemId()) {
            case R.id.item_grade_13140:
                listFragment.showGradeByTime("2013-2014 1");
                break;
            case R.id.item_grade_13141:
                listFragment.showGradeByTime("2013-2014 2");
                break;
            case R.id.item_grade_14150:
                listFragment.showGradeByTime("2014-2015 1");
                break;
            case R.id.item_grade_14151:
                listFragment.showGradeByTime("2014-2015 2");
                break;
            case R.id.item_grade_15160:
                listFragment.showGradeByTime("2015-2016 1");
                break;
            case R.id.item_grade_15161:
                listFragment.showGradeByTime("2015-2016 2");
                break;
            case R.id.item_grade_16170:
                listFragment.showGradeByTime("2016-2017 1");
                break;
            case R.id.item_grade_16171:
                listFragment.showGradeByTime("2016-2017 2");
                break;
            case R.id.item_grade_17180:
                listFragment.showGradeByTime("2017-2018 1");
                break;
            case R.id.item_grade_17181:
                listFragment.showGradeByTime("2017-2018 2");
                break;
            case R.id.item_grade_all:
                listFragment.showGradeByTime("ic_all_pink");
                break;
            case R.id.item_grade_exit:
                finish();
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grade_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_grade_refresh:
                navigationView.setCheckedItem(R.id.item_grade_all);
                GradeListFragment listFragment = (GradeListFragment) GradeFragmentManager.getInstance(0);
                listFragment.dataRequest(true);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

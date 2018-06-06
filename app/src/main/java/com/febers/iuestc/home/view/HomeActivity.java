package com.febers.iuestc.home.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.module.service.view.ServiceActivity;
import com.febers.iuestc.utils.CustomSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener
        , NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "HomeActivity";

    private BottomNavigationBar mBottomNavigationBar;
    private Fragment mLibraryFragment;
    private Fragment mUserFragment;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private Fragment mCourseFragment;
    private Fragment mECardFragment;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected int setView() {
        return R.layout.activity_home;
    }

    @Override
    protected void findViewById() {
        mDrawerLayout = findViewById(R.id.dl_home);
        mNavigationView = findViewById(R.id.nv_home);
    }

    @Override
    protected void initView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        int openPosition = 0;
        if (!CustomSharedPreferences.getInstance().get("is_login", false)) {
            openPosition = 3;
        }
        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setAutoHideEnabled(true);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_course_bottom_gray, "课程表"))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_bottom_color, "图书馆"))
                .addItem(new BottomNavigationItem(R.drawable.ic_cards_bottom_black, "卡务"))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_bottom_black, "我的"))
                .setFirstSelectedPosition(openPosition)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);

        mLibraryFragment  = HomeFragmentManager.getInstance(1);
        mUserFragment  = HomeFragmentManager.getInstance(2);
        mECardFragment = HomeFragmentManager.getInstance(3);
        mCourseFragment = HomeFragmentManager.getInstance(-1);

        android.support.v4.app.FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_layout, mLibraryFragment);
        mFragmentTransaction.add(R.id.fragment_layout, mUserFragment);
        mFragmentTransaction.add(R.id.fragment_layout, mECardFragment);
        mFragmentTransaction.add(R.id.fragment_layout, mCourseFragment);
        mFragmentTransaction.commit();

        mFragmentList.add(mCourseFragment);
        mFragmentList.add(mLibraryFragment);
        mFragmentList.add(mECardFragment);
        mFragmentList.add(mUserFragment);

        //如果没登录，显示userFragment
        if (!CustomSharedPreferences.getInstance().get("is_login", false)) {
            showFragment(3);
            mBottomNavigationBar.setFirstSelectedPosition(3);
        } else {
            showFragment(0);
        }
    }

    /**
     * BottomNavigationBar的点击监听
     * @param position 点击的位置
     */
    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                showFragment(0);
                break;
            case 1:
                //图书馆
                showFragment(1);
                break;
            case 2:
                //一卡通
                showFragment(2);
                break;
            case 3:
                //我的
                showFragment(3);
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void showFragment(int position) {
        android.support.v4.app.FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        for (Fragment f : mFragmentList) {
            mFragmentTransaction.hide(f);
        }
        mFragmentTransaction.show(mFragmentList.get(position));
        mFragmentTransaction.commit();
        //此时关闭启动画面
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Boolean fromLib = intent.getBooleanExtra("lib_activity", false);
        Boolean fromUser = intent.getBooleanExtra("user_activity", false);

        //如果是从lib_activity跳转过来，显示libraryFragment
        if (fromLib) {
            showFragment(1);
            mBottomNavigationBar.selectTab(1);
        }
        if (fromUser) {
            showFragment(3);
            mBottomNavigationBar.selectTab(3);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(Gravity.END)) {
            mDrawerLayout.closeDrawer(Gravity.END);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(HomeActivity.this, ServiceActivity.class);
        switch (item.getItemId()) {
            case R.id.item_nav_query_classroom:
                intent.putExtra("position", 0);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_today_class:
                intent.putExtra("position", 1);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_all_class:
                intent.putExtra("position", 2);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_teacher:
                intent.putExtra("position", 3);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_lxfs:
                intent.putExtra("position", 9);
                startActivity(intent);
                mDrawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_exit:
                finish();
            default:
                break;
        }
        return true;
    }
}

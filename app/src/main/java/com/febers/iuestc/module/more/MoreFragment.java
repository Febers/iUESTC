/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-4 下午9:17
 *
 */

package com.febers.iuestc.module.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterSetting;
import com.febers.iuestc.base.BaseFragment2;
import com.febers.iuestc.entity.BeanSetting;
import com.febers.iuestc.module.ecard.view.ECardActivity;
import com.febers.iuestc.module.exam.view.ExamActivity;
import com.febers.iuestc.module.grade.view.GradeActivity;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.module.news.view.NewsActivity;
import com.febers.iuestc.module.service.view.BusActivity;
import com.febers.iuestc.module.service.view.CalActivity;
import com.febers.iuestc.module.service.view.ServiceActivity;
import com.febers.iuestc.module.user.view.UserActivity;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.util.LogoutUtil;
import com.febers.iuestc.view.custom.CustomGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoreFragment extends BaseFragment2 {

    private List<BeanSetting> beanSettingList = new ArrayList<>();
    private ListView mListView;
    private TextView tvName;
    private TextView tvId;
    private CardView mCardView;
    private CustomGridView mGridView;
    private SimpleAdapter mAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_more;
    }

    @Override
    protected int setMenu() {
        return R.menu.more_menu;
    }

    @Override
    protected void initView() {
        Toolbar mToolbar = findViewById(R.id.user_toolbar);
        mToolbar.setTitle("i成电");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        tvName = findViewById(R.id.tv_fragment_user_name);
        tvId = findViewById(R.id.tv_fragment_user_id);
        mListView = findViewById(R.id.lv_user_setting);
        AdapterSetting adapterSetting = new AdapterSetting(getActivity(),
                R.layout.item_more_setting, beanSettingList);
        mListView.setAdapter(adapterSetting);
        mListView.setOnItemClickListener( (adapterView, view, i, l) ->{
            onClickListViewItem(i);
        });

        mCardView = findViewById(R.id.cv_user);
        mCardView.setOnClickListener(v ->  {
            if (!CustomSharedPreferences.getInstance().get(getString(R.string.sp_is_login), false)) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            } else {
                startActivity(new Intent(getActivity(), UserActivity.class));
            }
        });

        String[] from = {"image", "title"};
        int[] to = {R.id.user_grid_image, R.id.user_grid_text};
        mGridView = findViewById(R.id.grid_view_user);
        mAdapter = new SimpleAdapter(getActivity(), getGridList(), R.layout.item_user_grid, from, to);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener( (adapterView, view, i, l) -> {
            onClickGridViewItem(i);
        });
        initSettingList();
        dateRequest(true);
    }

    /**
     * GridView显示
     */
    private List<Map<String, Object>> getGridList() {
        List<Map<String, Object>> gridList= new ArrayList<>();

        String[] titles = {getString(R.string.user_exam), getString(R.string.user_grade),
                getString(R.string.user_detail), getString(R.string.user_ecard)};
        int[] images = {R.mipmap.ic_pen_yellow,  R.mipmap.ic_tend_pink,
                R.mipmap.ic_id_card_blue, R.mipmap.ic_money_green};
        for (int i = 0; i < titles.length; i++) {
            Map<String , Object> map = new ArrayMap<>();
            map.put("image", images[i]);
            map.put("title", titles[i]);
            gridList.add(map);
        }
        return gridList;
    }

    private void onClickGridViewItem(int position) {
        if (!CustomSharedPreferences.getInstance().get(getString(R.string.sp_is_login), false)) {
            return;
        }
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ExamActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), GradeActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), UserActivity.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(), ECardActivity.class));
                break;
            default:
                break;
        }
    }

    private void onClickListViewItem(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                intent.putExtra("position", 0);
                startActivity(intent);
                break;
            case 1:
                Intent i1 = new Intent(getActivity(), NewsActivity.class);
                i1.putExtra("type", 0);
                startActivity(i1);
                break;
//            case 2:
//                Intent i2 = new Intent(getActivity(), NewsActivity.class);
//                i2.putExtra("type", 1);
//                startActivity(i2);
//                HomeFragmentManager.clearFragment(99);
//                break;
            case 3 - 1:
                startActivity(new Intent(getActivity(), BusActivity.class));
                break;
            case 4 - 1:
                startActivity(new Intent(getActivity(), CalActivity.class));
                break;
            case 5 - 1:
                startActivity(new Intent(getActivity(), ThemeActivity.class));
                break;
            case 6 - 1:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            default:
                break;
        }
    }

    private void initSettingList() {
        BeanSetting stService = new BeanSetting("快捷查询", R.mipmap.ic_book_blue);
        BeanSetting stUnderJW = new BeanSetting("本科教务", R.mipmap.ic_news_red);
        BeanSetting stPostJW = new BeanSetting("研究生教务", R.mipmap.ic_news_purple);
        BeanSetting stBus = new BeanSetting("校车服务", R.mipmap.ic_bus_blue2);
        BeanSetting stCalendar = new BeanSetting("校历", R.mipmap.ic_cal_green2);
        BeanSetting stTheme = new BeanSetting("主题风格", R.mipmap.ic_theme_red);
        BeanSetting stAbout = new BeanSetting("关于", R.mipmap.ic_about_blue2);

        beanSettingList.add(stService);
        beanSettingList.add(stUnderJW);
//        beanSettingList.add(stPostJW);
        beanSettingList.add(stBus);
        beanSettingList.add(stCalendar);
        beanSettingList.add(stTheme);
        beanSettingList.add(stAbout);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        if (CustomSharedPreferences.getInstance()
                .get(getContext().getString(R.string.sp_is_login),false)) {
            String userName = CustomSharedPreferences.getInstance()
                    .get(getContext().getString(R.string.sp_user_name), "");
            String userId = CustomSharedPreferences.getInstance()
                    .get(getContext().getString(R.string.sp_user_id), "");
            if (userName.trim().isEmpty()) {
                userName = "已登录";
            }
            if (userName.trim().isEmpty()) {
                userName = "";
            }
            tvName.setText(userName);
            tvId.setText(userId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_menu_logout:
                if (!CustomSharedPreferences.getInstance()
                        .get(getContext().getString(R.string.sp_is_login),false)) {
                    break;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("您要注销信息门户登录吗?")
                        .setPositiveButton("注销",  (dialogInterface, i) -> {
                            logout();
                        })
                        .setNegativeButton("取消", (dialogInterface, i) -> {
                        });
                builder.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        dateRequest(true);
    }

    private void logout() {
        LogoutUtil.logoutSchool();
        tvName.setText("未登录");
        tvId.setText("");
    }

    public static MoreFragment newInstance(String param1) {
        Bundle args = new Bundle();
        args.putString(PARAMTER_1, param1);
        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

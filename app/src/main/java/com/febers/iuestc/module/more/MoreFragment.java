/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 上午11:29
 *
 */

package com.febers.iuestc.module.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.ecard.view.ECardActivity;
import com.febers.iuestc.module.exam.view.ExamActivity;
import com.febers.iuestc.module.grade.view.GradeActivity;
import com.febers.iuestc.entity.BeanSetting;
import com.febers.iuestc.adapter.AdapterSetting;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.module.news.view.NewsActivity;
import com.febers.iuestc.module.service.view.BusActivity;
import com.febers.iuestc.module.service.view.CalActivity;
import com.febers.iuestc.module.service.view.ServiceActivity;
import com.febers.iuestc.module.user.view.UserActivity;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.util.LogoutUtil;
import com.febers.iuestc.view.manager.HomeFragmentManager;
import com.febers.iuestc.view.custom.CustomGridView;
import com.febers.iuestc.view.custom.CustomLoginDialog;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Febers on img_2018/2/3.
 */

public class MoreFragment extends BaseFragment {

    private static final String TAG = "MoreFragment";

    private Toolbar mToolbar;
    private List<BeanSetting> beanSettingList = new ArrayList<>();
    private ListView mListView;
    private TextView tvName;
    private TextView tvId;
    private CardView mCardView;
    private CustomLoginDialog customLoginDialog;
    private TextInputEditText tieId;
    private String stId;
    private String stPw;
    private CustomGridView mGridView;
    private SimpleAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_more;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView() {
        setHasOptionsMenu(true);
        mToolbar = findViewById(R.id.user_toolbar);
        mToolbar.setTitle("i成电");
        mToolbar.inflateMenu(R.menu.more_menu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        tvName = findViewById(R.id.tv_user_name);
        tvId = findViewById(R.id.tv_user_id);

        mListView = findViewById(R.id.lv_user_setting);
        AdapterSetting adapterSetting = new AdapterSetting(getActivity(),
                R.layout.item_user_setting, beanSettingList);
        mListView.setAdapter(adapterSetting);
        mListView.setOnItemClickListener( (adapterView, view, i, l) ->{
            onClickListViewItem(i);
        });

        mCardView = findViewById(R.id.cv_user);
        mCardView.setOnClickListener(v ->  {
            if (!CustomSharedPreferences.getInstance().get(getContext().getString(R.string.sp_is_login), false)) {
//                showLoginDialog();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            } else {
                //显示个人详情页
            }
        });

        String[] from = {"image", "title"};
        int[] to = {R.id.user_grid_image, R.id.user_grid_text};
        mGridView = findViewById(R.id.grid_view_user);
        adapter = new SimpleAdapter(getActivity(), getGridList(), R.layout.item_user_grid, from, to);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener( (adapterView, view, i, l) -> {
                onClickGridViewItem(i);
        });

        initSettingList();
    }



    /**
     * GridView显示
     */
    private List<Map<String, Object>> getGridList() {
        List<Map<String, Object>> gridList= new ArrayList<>();

        String[] titles = {getString(R.string.user_exam), getString(R.string.user_grade),
                getString(R.string.user_detail), getString(R.string.user_ecard)};
        int[] images = {R.drawable.ic_exam_user_color,  R.drawable.ic_grade_trend_color,
                R.drawable.ic_detail_color, R.drawable.ic_money_color};
        for (int i = 0; i < titles.length; i++) {
            Map<String , Object> map = new ArrayMap<>();
            map.put("image", images[i]);
            map.put("title", titles[i]);
            gridList.add(map);
        }
        return gridList;
    }

    /**
     * 重写此方法并注释super调用，确保activity被回收之后fragment被销毁
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    private void onClickGridViewItem(int position) {
        switch (position) {
            case 0:
                if (!CustomSharedPreferences.getInstance().get(getContext().getString(R.string.sp_is_login),false)) {
                    break;
                }
                startActivity(new Intent(getActivity(), ExamActivity.class));
                HomeFragmentManager.clearFragment(99);
                break;
            case 1:
                if (!CustomSharedPreferences.getInstance().get(getContext().getString(R.string.sp_is_login),false)) {
                    break;
                }
                startActivity(new Intent(getActivity(), GradeActivity.class));
                HomeFragmentManager.clearFragment(99);
                break;
            case 2:
                startActivity(new Intent(getActivity(), UserActivity.class));
                HomeFragmentManager.clearFragment(99);
                break;
            case 3:
                startActivity(new Intent(getActivity(), ECardActivity.class));
                HomeFragmentManager.clearFragment(99);
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
                HomeFragmentManager.clearFragment(99);
                break;
            case 1:
                Intent i1 = new Intent(getActivity(), NewsActivity.class);
                i1.putExtra("type", 0);
                startActivity(i1);
                HomeFragmentManager.clearFragment(99);
                break;
//            case 2:
//                Intent i2 = new Intent(getActivity(), NewsActivity.class);
//                i2.putExtra("type", 1);
//                startActivity(i2);
//                HomeFragmentManager.clearFragment(99);
//                break;
            case 3-1:
                startActivity(new Intent(getActivity(), BusActivity.class));
                HomeFragmentManager.clearFragment(99);
                break;
            case 4-1:
                startActivity(new Intent(getActivity(), CalActivity.class));
                HomeFragmentManager.clearFragment(99);
                break;
            case 5-1:
                startActivity(new Intent(getActivity(), ThemeActivity.class));
                HomeFragmentManager.clearFragment(99);
                getActivity().finish();
                break;
            case 6-1:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                HomeFragmentManager.clearFragment(99);
                break;
            default:
                break;
        }
    }

    private void initSettingList() {
        BeanSetting stService = new BeanSetting("快捷查询", R.drawable.ic_contact_user_color);
        BeanSetting stUnderJW = new BeanSetting("本科教务", R.drawable.ic_undergraduate_user_color);
        BeanSetting stPostJW = new BeanSetting("研究生教务", R.drawable.ic_postgraduate_user_color);
        BeanSetting stBus = new BeanSetting("校车服务", R.drawable.ic_bus_user_color);
        BeanSetting stCalendar = new BeanSetting("校历", R.drawable.ic_cal_user_color);
        BeanSetting stTheme = new BeanSetting("主题风格", R.drawable.ic_theme_color);
        BeanSetting stAbout = new BeanSetting("关于", R.drawable.ic_bulb_color);

        beanSettingList.add(stService);
        beanSettingList.add(stUnderJW);
//        beanSettingList.add(stPostJW);
        beanSettingList.add(stBus);
        beanSettingList.add(stCalendar);
        beanSettingList.add(stTheme);
        beanSettingList.add(stAbout);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Fragment生命周期问题，如果切换Activity时不关闭Fragment
        //ListView会重复加载
        HomeFragmentManager.clearFragment(99);
    }

    public void showLoginDialog() {
        if (customLoginDialog == null) {
            customLoginDialog = new CustomLoginDialog(getContext());
            customLoginDialog.setShowTitle(true);
            tieId = customLoginDialog.getTieUserId();
            customLoginDialog.setOnLoginListener(v -> {
                switch (v.getId()) {
                    case R.id.bt_dialog_login_enter:
                        if (!BaseApplication.checkNetConnecting()) {
                            Toast.makeText(getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        stId = customLoginDialog.getStId();
                        stPw = customLoginDialog.getStPw();
                        if (stId.equals("") || stPw.equals("")) {
                            Toast.makeText(getContext(), "请输入正确的格式", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        customLoginDialog.hide();
                        showProgressDialog();
                        break;
                    case R.id.bt_dialog_login_cancel:
                        customLoginDialog.dismiss();
                        break;
                    default:
                        break;
                }
            });
        }
        customLoginDialog.show();
    }

    private void logout() {
        LogoutUtil.logoutSchool();
        HomeFragmentManager.clearFragment(-1);
        tvName.setText("未登录");
        tvId.setText("");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.more_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_menu_logout:
                if (!CustomSharedPreferences.getInstance().get(getContext().getString(R.string.sp_is_login),false)) {
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
}

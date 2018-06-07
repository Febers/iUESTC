/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 下午12:57
 *
 */

package com.febers.iuestc.home.view;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 废弃的Fragment
 * Created by Febers on img_2018/2/3.
 */

public class HomeFragment extends BaseFragment{

    private static final String TAG = "HomeFragment";

    private GridView mGridView;
    private SimpleAdapter adapter;
    private Toolbar mToolbar;

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.home_toolbar);
        mToolbar.setTitle("i成电");

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        String[] from = {"image", "title"};
        int[] to = {R.id.home_grid_image, R.id.home_grid_text};
        mGridView = findViewById(R.id.home_grid_view);
        adapter = new SimpleAdapter(getActivity(), getGridList(), R.layout.item_home_grid, from, to);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    /**
     * 主界面GridView显示
     */
    private List<Map<String, Object>> getGridList() {
        List<Map<String, Object>> gridList= new ArrayList<>();

        String[] titles = {"课表", "成绩", "一卡通", "邮箱", "教务处", "考试", "校历", "校车"};
        int[] images = {R.drawable.ic_all_pink, R.drawable.ic_all_pink, R.drawable.ic_all_pink,R.drawable.ic_all_pink,
                R.drawable.ic_all_pink, R.drawable.ic_all_pink, R.drawable.ic_all_pink, R.drawable.ic_all_pink};

        for (int i = 0; i < titles.length; i++) {
            Map<String , Object> map = new ArrayMap<>();
            map.put("image", images[i]);
            map.put("title", titles[i]);
            gridList.add(map);
        }
        return gridList;
    }
}

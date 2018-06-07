/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 下午12:58
 *
 */

package com.febers.iuestc.module.library.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.library.model.BeanBook;
import com.febers.iuestc.module.library.contract.ILibraryPresenter;
import com.febers.iuestc.module.library.contract.LibraryPresenterImp;
import com.febers.iuestc.utils.CustomSharedPreferences;
import com.febers.iuestc.home.view.HomeFragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Febers on img_2018/2/3.
 */

public class LibraryFragment extends BaseFragment {

    private static final String TAG = "LibraryFragment";
    private Context context = BaseApplication.getContext();
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private Spinner typeSpiner, locationSpiner;
    private List<String> typeList, locationList;
    private ArrayAdapter<String> typeAdapter, locationAdapter;
    private String type;
    private String location;

    @Override
    protected int setContentView() {
        return R.layout.fragment_library;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView() {
        initSpiner();
        mToolbar = getActivity().findViewById(R.id.library_toolbar);
        mToolbar.inflateMenu(R.menu.library_menu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mSearchView = getActivity().findViewById(R.id.library_search);
        mSearchView.setIconifiedByDefault(false);  //初始展开搜索
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setFocusableInTouchMode(true);  //解决按返回键无效问题
        mSearchView.setFocusable(true);      //禁止弹出输入法
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), LibQueryActivity.class);
                intent.putExtra("keyword", query);
                intent.putExtra("type", type);
                intent.putExtra("position", location);
                intent.putExtra("status", -1);
                startActivity(intent);
                HomeFragmentManager.clearFragment(99);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
            if (!CustomSharedPreferences.getInstance().get(BaseApplication.getContext()
                    .getString(R.string.sp_library_school_net), false)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("图书馆查询功能目前只支持校园网");
                builder.setPositiveButton("知道了", (DialogInterface dialogInterface, int i) -> { });
                builder.show();
                CustomSharedPreferences.getInstance().put(BaseApplication.getContext()
                        .getString(R.string.sp_library_school_net), true);
            }
        }
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.library_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_library_history:
                if (!CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_is_login), false)) {
                    Toast.makeText(getContext(), "尚未登录信息门户", Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent = new Intent(getContext(), LibQueryActivity.class);
                intent.putExtra("history", true);
                startActivity(intent);
                HomeFragmentManager.clearFragment(99);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSpiner() {
        typeSpiner = getActivity().findViewById(R.id.sp_type);
        locationSpiner = getActivity().findViewById(R.id.sp_location);
        typeList = new ArrayList<>();
        //typeList.add("题名");
        typeList.add("关键字");
        //typeList.add("著者");
        locationList = new ArrayList<>();
        //locationList.add("默认");
        locationList.add("清水河");
        locationList.add("沙河");
        typeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        locationAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, locationList);
        locationAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpiner.setAdapter(typeAdapter);
        locationSpiner.setAdapter(locationAdapter);
        typeSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        type = "t";
                        break;
                    case 1:
                        type = "X";
                        break;
                    case 2:
                        type = "a";
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        locationSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        location = "3";
                        break;
                    case 1:
                        location = "2";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

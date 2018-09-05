/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-5 下午7:40
 *
 */

package com.febers.iuestc.module.library.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;

public class LibraryFragment extends BaseFragment implements EditText.OnEditorActionListener {

    private EditText etLibFragment;
    private RadioGroup rgLibFragment;
    private Button btnQuery;
    private int mType;

    @Override
    protected int setContentView() {
        return R.layout.fragment_library;
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        String keyword = etLibFragment.getText().toString();
        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }
        Intent intent = new Intent(getActivity(), LibQueryActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("type", mType);
        startActivity(intent);
        hideSoftInput();
//        ((InputMethodManager) etLibFragment.getContext().getSystemService(
//                getContext().INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                getActivity().getCurrentFocus().getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void initView() {
        Toolbar mToolbar = findViewById(R.id.library_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        etLibFragment = findViewById(R.id.et_library_fragemnt);
        etLibFragment.setOnEditorActionListener(this);
        rgLibFragment = findViewById(R.id.rg_library_fragment);
        rgLibFragment.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
                    switch (checkedId) {
                        case R.id.rb_lib_keyword:
                            mType = 0;
                            break;
                        case R.id.rb_lib_author:
                            mType = 1;
                            break;
                        case R.id.rb_lib_title:
                            mType = 2;
                            break;
                        case R.id.rb_lib_theme:
                            mType = 3;
                            break;
                        case R.id.rb_lib_isbn:
                            mType = 4;
                            break;
                        default:
                            break;
                    }
                }
        );
        btnQuery = findViewById(R.id.btn_lib_query);
        btnQuery.setOnClickListener((View v) -> {
            dateRequest(true);
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            dateRequest(true);
            return true;
        }
        return false;
    }

    public static LibraryFragment newInstance(String param1) {
        Bundle args = new Bundle();
        args.putString(PARAMTER_1, param1);
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

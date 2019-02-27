/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-5 下午7:40
 *
 */

package com.febers.iuestc.module.library.view;

import android.content.Intent;
import android.net.Uri;
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
    private int mType;

    @Override
    protected int setContentView() {
        return R.layout.fragment_library;
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
        String keyword = etLibFragment.getText().toString();
        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }
        Intent intent = new Intent(getActivity(), LibQueryActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("type", mType);
        startActivity(intent);
        hideSoftInput();
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.library_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        etLibFragment = findViewById(R.id.et_library_fragemnt);
        etLibFragment.setOnEditorActionListener(this);
        RadioGroup rgLibFragment = findViewById(R.id.rg_library_fragment);
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
        Button btnQuery = findViewById(R.id.btn_lib_query);
        btnQuery.setOnClickListener((View v) -> {
            dataRequest(true);
        });
        Button btnHistory = findViewById(R.id.btn_lib_history);
        btnHistory.setOnClickListener( v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://222.197.165.97:8080/sms/opac/search/showSearch.action?xc=6"));
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
            //startActivity(new Intent(getActivity(), LibHistoryActivity.class));
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            dataRequest(true);
            return true;
        }
        return false;
    }

    public static LibraryFragment newInstance(String param1) {
        Bundle args = new Bundle();
        args.putString(PARAMETER, param1);
        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

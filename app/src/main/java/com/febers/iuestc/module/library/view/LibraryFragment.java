/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-7 下午4:53
 *
 */

package com.febers.iuestc.module.library.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.view.manager.HomeFragmentManager;

public class LibraryFragment extends BaseFragment implements EditText.OnEditorActionListener {

    private static final String TAG = "LibraryFragment";
    private EditText etLibFragment;
    private RadioGroup rgLibFragment;
    private Button btnQuery;
    private int mType;

    @Override
    protected int setContentView() {
        return R.layout.fragment_library;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView() {
        Toolbar mToolbar = getActivity().findViewById(R.id.library_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        etLibFragment = findViewById(R.id.et_library_fragemnt);
        etLibFragment.setOnEditorActionListener(this);
        rgLibFragment = findViewById(R.id.rg_library_fragment);
        rgLibFragment.setOnCheckedChangeListener( (RadioGroup group, int checkedId) -> {
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
        btnQuery.setOnClickListener( (View v) -> {
            queryQuest();
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            queryQuest();
            return true;
        }
        return false;
    }

    private void queryQuest() {
        String keyword = etLibFragment.getText().toString();
        if (keyword ==null || keyword.trim().isEmpty()) {
            return;
        }
        Intent intent = new Intent(getContext(), LibQueryActivity.class);
        intent.putExtra("keyword", keyword);
        intent.putExtra("type", mType);
        startActivity(intent);
        getActivity().finish();
        ((InputMethodManager) etLibFragment.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

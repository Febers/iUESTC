package com.febers.iuestc.module.library.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LibraryFragment extends BaseFragment implements EditText.OnEditorActionListener {

    private EditText etLibFragment;
    private int type;

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
        intent.putExtra("type", type);
        startActivity(intent);
        hideSoftInput();
    }

    @Override
    protected int setToolbar() {
        return R.id.library_toolbar;
    }

    @Override
    protected void initView() {
        etLibFragment = findViewById(R.id.et_library_fragemnt);
        etLibFragment.setOnEditorActionListener(this);
        RadioGroup rgLibFragment = findViewById(R.id.rg_library_fragment);
        rgLibFragment.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
                    switch (checkedId) {
                        case R.id.rb_lib_keyword:
                            type = 0;
                            break;
                        case R.id.rb_lib_author:
                            type = 1;
                            break;
                        case R.id.rb_lib_title:
                            type = 2;
                            break;
                        case R.id.rb_lib_theme:
                            type = 3;
                            break;
                        case R.id.rb_lib_isbn:
                            type = 4;
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

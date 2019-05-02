package com.febers.iuestc.home.view;

import android.os.Bundle;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.course.view.CourseFragment;

import androidx.annotation.Nullable;

public class HomeFirstContainer extends BaseFragment {

    @Override
    protected int setContentView() {
        return R.layout.container_home_first;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(CourseFragment.class) == null) {
            loadRootFragment(R.id.first_container_layout, CourseFragment.newInstance(""));
        }
    }
}

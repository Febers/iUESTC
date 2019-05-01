/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-4 下午6:18
 *
 */

package com.febers.iuestc.home.view;

import android.os.Bundle;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.more.MoreFragment;

import androidx.annotation.Nullable;

public class HomeThirdContainer extends BaseFragment {

    @Override
    protected int setContentView() {
        return R.layout.container_home_third;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(MoreFragment.class) == null) {
            loadRootFragment(R.id.third_container_layout, MoreFragment.newInstance(""));
        }
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-4 下午6:17
 *
 */

package com.febers.iuestc.home.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.library.view.LibraryFragment;

public class HomeSecondContainer extends BaseFragment {
    @Override
    protected int setContentView() {
        return R.layout.container_home_second;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (findChildFragment(LibraryFragment.class) == null) {
            loadRootFragment(R.id.second_container_layout, LibraryFragment.newInstance(""));
        }
    }
}

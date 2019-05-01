/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.view.manager;

import com.febers.iuestc.module.grade.view.GradeImgFragment;
import com.febers.iuestc.module.grade.view.GradeListFragment;

import androidx.fragment.app.Fragment;

public class GradeFragmentManager {
    private static final String TAG = "GradeFragmentManager";
    private static GradeListFragment listFragment;
    private static GradeImgFragment imgFragment;

    public static Fragment getInstance(int position) {
        if (position == 0) {
            if (listFragment == null) {
                listFragment = new GradeListFragment();
            }
            return listFragment;
        } else if (position == 1) {
            if (imgFragment == null) {
                imgFragment = new GradeImgFragment();
            }
            return imgFragment;
        }
        return new GradeListFragment();
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:02
 *
 */

package com.febers.iuestc.module.grade.view;

import android.support.v4.app.Fragment;

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

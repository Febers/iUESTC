/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.view.manager;

import android.support.v4.app.Fragment;

import com.febers.iuestc.module.course.view.CourseFragment;
import com.febers.iuestc.module.library.view.LibraryFragment;
import com.febers.iuestc.module.more.MoreFragment;

public class HomeFragmentManager {

    private static final String TAG = "HomeFragmentManager";

    private static LibraryFragment sLibraryFragment;
    private static MoreFragment sMoreFragment;
    private static CourseFragment sCourseFragment;

    public static Fragment getInstance(int position) {
        switch (position) {
            case 0:
                if (sCourseFragment == null) {
                    sCourseFragment = new CourseFragment();
                }
                return sCourseFragment;
            case 1:
                if (sLibraryFragment == null) {
                    sLibraryFragment = new LibraryFragment();
                }
                return sLibraryFragment;
            case 2:
                if (sMoreFragment == null) {
                    sMoreFragment = new MoreFragment();
                }
                return sMoreFragment;
            default:
                break;
        }
        return new Fragment();
    }

    public static void clearFragment(int position) {
        switch (position) {
            case 0:
                sCourseFragment = null;
                break;
            case 1:
                sLibraryFragment = null;
                break;
            case 2:
                sMoreFragment = null;
                break;
            case 99:
                sCourseFragment = null;
                sLibraryFragment = null;
                sMoreFragment = null;
                break;
            default:
                break;
        }
    }
}

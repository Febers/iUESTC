/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.view.manager;

import android.support.v4.app.Fragment;

import com.febers.iuestc.home.view.HomeFragment;
import com.febers.iuestc.mvp.view.CourseFragment;
import com.febers.iuestc.mvp.view.ECardFragment;
import com.febers.iuestc.mvp.view.LibraryFragment;
import com.febers.iuestc.mvp.view.UserFragment;

public class HomeFragmentManager {

    private static final String TAG = "HomeFragmentManager";

    private static HomeFragment sHomeFragment;
    private static LibraryFragment sLibraryFragment;
    private static UserFragment sUserFragment;
    private static CourseFragment sCourseFragment;
    private static ECardFragment sECardFragment;

    public static Fragment getInstance(int position) {
        switch (position) {
            //阶段一先以课程表为第一界面
            case -1:
                if (sCourseFragment == null) {
                    sCourseFragment = new CourseFragment();
                }
                return sCourseFragment;
            case 0:
                if (sHomeFragment == null) {
                    sHomeFragment = new HomeFragment();
                }
                return sHomeFragment;
            case 1:
                if (sLibraryFragment == null) {
                    sLibraryFragment = new LibraryFragment();
                }
                return sLibraryFragment;
            case 2:
                if (sUserFragment == null) {
                    sUserFragment = new UserFragment();
                }
                return sUserFragment;
            case 3:
                if (sECardFragment == null) {
                    sECardFragment = new ECardFragment();
                }
                return sECardFragment;
            default:
                break;
        }
        return new Fragment();
    }

    public static void clearFragment(int position) {
        switch (position) {
            case -1:
                sCourseFragment = null;
                break;
            case 0:
                sHomeFragment = null;
                break;
            case 1:
                sLibraryFragment = null;
                break;
            case 2:
                sUserFragment = null;
                break;
            case 3:
                sECardFragment = null;
                break;
            case 99:
                sCourseFragment = null;
                sHomeFragment = null;
                sECardFragment = null;
                sLibraryFragment = null;
                sUserFragment = null;
                break;
            default:
                break;
        }
    }
}

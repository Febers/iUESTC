/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-4 下午10:08
 *
 */

package com.febers.iuestc.util;

import com.febers.iuestc.R;

public class ThemeUtil {
    public static int getTheme(int themeCode) {
        switch (themeCode) {
            case 0:
                return R.style.DefaultTheme;
            case 1:
                return R.style.BlackTheme;
            case 2:
                return R.style.GreenTheme;
            case 3:
                return R.style.RedTheme;
            case 4:
                return R.style.PurpleTheme;
            case 5:
                return R.style.OrangeTheme;
            case 6:
                return R.style.GreyTheme;
            case 7:
                return R.style.TealTheme;
            case 8:
                return R.style.PinkTheme;
            case 9:
                return R.style.BlueTheme;
            default:
                return R.style.DefaultTheme;
        }
    }
}

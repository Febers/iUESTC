/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-7 下午5:41
 *
 */

package com.febers.iuestc.util;

import android.content.Context;
import android.util.TypedValue;

public class AttrUtil {
    public static int getColor(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }
}

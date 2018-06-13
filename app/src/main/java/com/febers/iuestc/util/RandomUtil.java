/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:04
 *
 */

package com.febers.iuestc.util;

import java.util.Random;

/**
 * 获取随机整数，范围为[0,end)
 */

public class RandomUtil {
    public static int getRandomFrom0(int end) {
        Random random = new Random();
        return random.nextInt(end);
    }
}

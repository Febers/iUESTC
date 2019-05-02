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

package com.febers.iuestc.utils;

import java.util.Random;

/**
 * 获取随机整数，范围为[0,end)
 * Created by 23033 on 2018/3/31.
 */

public class RandomUtil {
    public static int getRandomFrom0(int end) {
        Random random = new Random();
        return random.nextInt(end);
    }
}

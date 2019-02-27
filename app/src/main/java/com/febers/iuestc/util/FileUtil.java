package com.febers.iuestc.util;

import com.febers.iuestc.MyApplication;

public class FileUtil {

    public static final String FILE_PROVIDER_AUTHORITY = "com.febers.iuestc.fileProvider";

    public static final String appDataDir = MyApplication.getContext().getExternalFilesDir("data").getAbsolutePath();

}

package com.febers.iuestc.util;

import com.febers.iuestc.MyApp;

public class FileUtil {

    public static final String FILE_PROVIDER_AUTHORITY = "com.febers.iuestc.fileProvider";

    public static final String appDataDir = MyApp.getContext().getExternalFilesDir("data").getAbsolutePath();

}

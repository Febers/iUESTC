#将放置在MainDex的类的混淆规则写在这里

# for Bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}
-keep class androidx.**{*;}

# iUESTC

<img src="https://github.com/Febers/iUESTC/blob/master/picture/app_icon.png" align=center />

## 关于

i成电 (iUESTC) 是一个为电子科技大学学生提供生活和学习帮助的 Android 客户端。由 *@Febers* 独立开发，目前只支持本科生使用

## 功能

- [x] 查看课表、成绩、当前学期考试情况
- [x] 图书馆图书检索功能
- [x] 查看校历、校车时刻表
- [x] 本科教学新闻查看
- [ ] ......

## 项目实现

### 登录

在早些版本中，数据的获取是通过模拟登录实现的：首先对教务系统的登录过程进行抓包，然后通过 OKHttp 登录，核心代码中的请求体为

```java
        FormBody formBody = new FormBody.Builder()
                .add("username",id)
                .add("password",pw)
                .add("lt",lt)
                .add("_eventId","submit")
                .add("dllt","userNamePasswordLogin")
                .add("execution",exec)
                .add("rmShown","1")
                .build();
```

上面的很多参数都隐藏在信息门户首页的 HTML 源代码中，解决方法是通过 Jsoup 解析 DOM 文档获取

之后教务系统将隐藏的验证码常规化，需要添加一个参数`captchaResponse`，同样可以通过抓包的方式获取

这种方式已经废弃，原因有下：

- 模拟登录的状态保持是通过持久化 Cookie 实现的，但是 Cookie 的有效时间很短。一旦过期，登录状态将丢失，需要重新登录，所以每次请求都要判断登录状态是否有效，否则无法正确获取比如课表、考试情况等最新的信息
- 登录或重新登录的过程非常繁琐，Java 代码模拟浏览器需要复杂的逻辑，并且需要本地保存用户账号密码
- 教务系统的登录方式很有可能再次发生变化，旧的模拟登录方式将失效，届时应用将瘫痪

> 早期 git commit 写法不规范，很难找到以前的代码，可以在 Release 界面下载 Beta3.0 的源码，查看`BeforeLoginModel.java`文件



现在采取的方式为：通过 WebView 打开教务系统的登录界面，由用户手动登录，之后通过 WebView 的`addJavascriptInterface`方法拦截返回的代码判断是否登录成功——这其中牵扯到 Java 代码和 JavaScript 代码的交互，不再赘述

之后课表、考试信息的获取仍然是通过模拟浏览器的 POST 请求。最关键的一点在于：自定义 [CookiesManager](https://github.com/Febers/iUESTC/blob/master/app/src/main/java/com/febers/iuestc/net/CustomCookiesManager.java) ，使得 OkHttpClient 可以共享 WebView 的 Cookie，这是一切的前提

### 其他

整体采用MVP的设计架构，网络请求框架为 OkHttp，UI则采用了开源框架 Fragmentation

整个项目的功能位于 module，每个功能模块按照 MVP 分层实现。其中 Model 层负责数据的获取和存取，View 层则负责显示数据，Presenter 充当上述两者的桥梁。


## 四、使用截图

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen3.png" />

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen1.png" />

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen2.png" />

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen4.png" />

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen5.png" />

## 五、开源相关
[okhttp](https://github.com/square/okhttp)、[RecyclerViewAdapter](https://github.com/SheHuan/RecyclerViewAdapter)、[Fragmentation](https://github.com/YoKeyword/Fragmentation)、[ListItemView](https://github.com/lurbas/ListItemView)、
[multiline-collapsingtoolbar](https://github.com/opacapp/multiline-collapsingtoolbar)、[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)、[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)、
[Android-PickerView](https://github.com/Bigkoo/Android-PickerView)、[BottomNavigation](https://github.com/Ashok-Varma/BottomNavigation)、[jsoup](https://github.com/jhy/jsoup)、[gson](https://github.com/google/gson)


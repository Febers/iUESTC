# iUESTC

<img src="https://github.com/Febers/iUESTC/blob/master/picture/app_icon.png" align=center />

## 一、关于

i成电 (iUESTC) 是一个为电子科技大学的学生提供生活和学习帮助的Android客户端。由 *@Febers* 独立开发，目前只支持本科生使用。

## 二、功能

- [x] 登录信息门户的情况下，可以查看课表、查看成绩、一卡通、当前学期考试情况。
- [x] 图书馆图书检索功能。
- [x] 查看校历、校车时刻表。
- [x] 空闲教室、当日课程、全校课程以及教师信息的查询。
- [x] 本科教学新闻查看。
- [x] 查看学校各学院教务处联系方式。
- [ ] 研究生和博士生功能的实现。
- [ ] 应用内查看图书借阅记录。
- [ ] ......

## 使用截图

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen3.png" />

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen1.png" />

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen2.png" />

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen4.png" />

<img src="https://github.com/Febers/iUESTC/blob/master/picture/sreen5.png" />


## 四、项目实现

+ 整体采用MVP的设计架构，网络请求框架为OkHttp，UI则采用了开源框架Fragmentation。

+ 整个项目的功能位于module，每个功能模块按照MVP分层实现。其中Model层负责数据的获取和存取，View层则负责显示数据，Presenter充当上述两者的桥梁。

+ 数据的获取通过OkHttp模拟登录教务系统，拿到网页源码之后进行本地解析。其中的难点在于状态的保持。项目通过持久化Cookie(见\net包)以及二次登录实现。

+ 网页登录、一卡通和图书馆检索图书的实现采用了解析WebView的网页源码的方案，WebView的Cookie也经过了本地持久化。

+ 软件更新和Crash统计采用腾讯Bugly方案。

+ “快捷查询”功能使用 *@Vizard* 提供的api：[uestc-api](https://github.com/Vizards/uestc-api)。

## 五、开源相关
+ [okhttp](https://github.com/square/okhttp)、[RecyclerViewAdapter](https://github.com/SheHuan/RecyclerViewAdapter)、[Fragmentation](https://github.com/YoKeyword/Fragmentation)、[ListItemView](https://github.com/lurbas/ListItemView)、
[multiline-collapsingtoolbar](https://github.com/opacapp/multiline-collapsingtoolbar)、[MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)、[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)、
[Android-PickerView](https://github.com/Bigkoo/Android-PickerView)、[BottomNavigation](https://github.com/Ashok-Varma/BottomNavigation)、[jsoup](https://github.com/jhy/jsoup)、[gson](https://github.com/google/gson)


*查看更多可访问 [主页](http://app.febers.tech)*

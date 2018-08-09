# 电子科技大学生活与学习Android服务平台

### 下载页面

![image](https://github.com/Febers/iUESTC/blob/master/app/src/main/ic_launcher-web.png)

## 实现功能：

- 登录信息门户的情况下，查看课表、成绩、考试情况、个人信息、一卡通状态。
- 图书馆图书检索功能。
- 查看教学新闻、校历、校车情况。
- 快捷查询功能，包括空闲教室、当日课程、全校课程、教师信息以及教务联系方式的查询。

## 实现原理：

- 通过WebView与Okhttp的Cookie共享，在WebView端登录之后并解析登录结果之后，通过Okhttp后台发起资源页面请求，使用Jsoup进行本地解析。
- 由于一卡通页面的复杂性，采用JavaScript代码与WebView的交互解析其载入的html源码。
- 项目采用mvp架构，模块隔离，保证耦合性。


# 简介
《欧几里》是一款一比一模仿《青藤之恋》的社交交友软件开源项目，主打高学历优质人群交友，功能上基本还原青藤之恋，以双向喜欢后解锁聊天，目前适配微信小程序，手机App，H5，三端通用，功能完整，盈利模式完善，高度配置化，功能模块化，高内聚，低耦合，一天快速上线，已对接支付接口，只需要简单的修改配置文件中相关信息，即可实现快速部署上线，减少开发的时间成本，金钱成本，技术成本，将更多的精力放在产品运营上。
# 原文地址
https://www.wxmblog.com/archives/fang-qing-teng-zhi-lian-she-jiao-jiao-you-ruan-jian-wei-xin-xiao-cheng-xu-apph5-san-duan-tong-yong
# 演示地址
## 小程序端小程序演示
微信扫描下方小程序码，可添加作者微信（MMRWXM）交流<br>
![gh_01e98cf10e5a_430](https://www.wxmblog.com/upload/2024/11/gh_01e98cf10e5a_430.jpg)
## H5演示地址
https://www.wxmblog.com/oujili
## 安卓APK
下载地址：
https://www.wxmblog.com/devminio/oujili/2024/09/22/2c73cd3d-ae8b-4987-9133-16dc75fec051.apk
## 后台管理演示地址
http://1.14.110.117:8089<br>
账号：demo<br>
密码：123456
#  合作洽谈
专业后端，资深UI，前端，运维，产品，测试，项目管理俱全，项目经验丰富，如果你有软件开发需求可与我联系，微信：MMRWXM（请注明来意）
# 项目地址
## github
https://github.com/378526425/oujili
## gitee
https://gitee.com/wxmr0/oujili
# 关于授权
✅允许
- 允许用于个人学习、毕业设计、教学案例、公益事业、商业使用（商用建议使用旗舰版）
- 禁止将本项目的部分或全部代码以及基于此项目二次开发后的产品和资源进行任何形式的开源再发行（尤其上传GitHub,Gitee,插件市场等互联网公开场所）
- 禁止将本项目的代码和资源以及基于此项目二次开发后的产品和资源进行任何形式的二次出售，产生的一切任何后果责任由侵权者自负
- 推荐Watch、Star项目，获取项目第一时间更新，同时也是对项目最好的支持 <br>

# 项目清单
完整项目清单包含如下<br>
1 客户端(uni-client)<br>
2 服务端(oujili-server)<br>
3 后台管理端(oujili-manage)<br>
# 项目结构
## 客户端
```
platform
|--api api接口请求
|--components 公共组件
|--pages tab页面
|--request restful接口请求封装
|--static 静态资源
|--subpackages 分包页面
|--uni_modules 引用组件包
|--utils 工具包
|--......
```
## 服务端
```
platform
|--authority 权限认证
|--common 公共工具类
|--controller 控制层
|--dao 数据访问层 
|--entity 实体类
|--schedules 定时器
|--service 业务层
|--......
```
## 后台管理端
```
platform
|--mock 公共相关js
|--public 静态资源
|--src 核心业务
|--......
```
# 技术架构
## 客户端
开发框架 uniapp<br>
客户端使用uniapp开发，一次开发可垮三端平台，目前支持发布到微信小程序,手机APP（安卓和IOS）,H5端等。
## 服务端
开发语言： java<br>
开发框架： springboot,spring cloud alibaba 两种版本，设计时按微服务设计开发，鉴于微服务的复杂性，为降低维护成本，改造了一个单体springboot版本，功能完全一样，可根据自己需求选择合适的版本<br>
数据库：mysql<br>
中间件：redis,nginx<br>
文件存储:  minio<br>
支付：微信支付<br>
其他第三方sdk:高德定位，腾讯定位
## 即时通讯IM
项目中聊天im未使用第三方插件，由自己实现，减少预算成本和依赖，im系统通过netty+websocket+redis 实现，理论上单体可同时超过百万人数在线，可集群部署，连接稳定，消息通过心跳和应答机制，保证每条消息可达性，即时通讯系统已历经多个其他项目生产检验和迭代，已具备商用的稳定和条件。
## 后台管理端
开发框架： vue
# 功能模块
## 客户端
- 用户资料推荐
- 用户匹配
- 在线聊天IM
- 个人中心
- 好友列表
- 社区朋友圈
- 好友动态列表
- 评论点赞互动
- 消息通知
- 我喜欢和喜欢我列表
- 学历认证
- 实名认证
- 修改个人资料
- 金币充值
- 微信好友 朋友圈分享
- 注销账户
- 注册
-  ......
## 后台管理端
- 资料审核
- 学历审核
- 实名审核
- 前台用户管理
- 用户统计
- ......

# 页面展示
## 客户端
![image](https://www.wxmblog.com/upload/2024/01/image.png)

![image-1704095985738](https://www.wxmblog.com/upload/2024/01/image-1704095985738.png)

![image-1720543710239](https://www.wxmblog.com/upload/2024/07/image-1720543710239.png)

![image-1720543768089](https://www.wxmblog.com/upload/2024/07/image-1720543768089.png)

![image-1704096286155](https://www.wxmblog.com/upload/2024/01/image-1704096286155.png)

![image-1704096379510](https://www.wxmblog.com/upload/2024/01/image-1704096379510.png)

![image-1704096488039](https://www.wxmblog.com/upload/2024/01/image-1704096488039.png)

![image-1704096557001](https://www.wxmblog.com/upload/2024/01/image-1704096557001.png)

![image-1704096901008](https://www.wxmblog.com/upload/2024/01/image-1704096901008.png)

![image-1704096851604](https://www.wxmblog.com/upload/2024/01/image-1704096851604.png)

![image-1704096994556](https://www.wxmblog.com/upload/2024/01/image-1704096994556.png)

![image-1704097048206](https://www.wxmblog.com/upload/2024/01/image-1704097048206.png)

![image-1704097967350](https://www.wxmblog.com/upload/2024/01/image-1704097967350.png)

![image-1720543671736](https://www.wxmblog.com/upload/2024/07/image-1720543671736.png)

![image-1704097243586](https://www.wxmblog.com/upload/2024/01/image-1704097243586.png)

![image-1704097666584](https://www.wxmblog.com/upload/2024/01/image-1704097666584.png)

## 后台管理端
![image-1703080008972](https://www.wxmblog.com/upload/2023/12/image-1703080008972.png)

![image-1703080026969](https://www.wxmblog.com/upload/2023/12/image-1703080026969.png)

![image-1703080054976](https://www.wxmblog.com/upload/2023/12/image-1703080054976.png)

![image-1703080089429](https://www.wxmblog.com/upload/2023/12/image-1703080089429.png)

![image-1703080104100](https://www.wxmblog.com/upload/2023/12/image-1703080104100.png)
# 联系我们
## 作者微信
MMRWXM
## QQ交流群
546414613
# 其他优秀开源项目推荐
https://www.wxmblog.com/categories/open
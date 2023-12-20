# 简介
《欧几里》是一款一比一模仿《青藤之恋》的社交交友软件开源项目，主打高学历优质人群交友，功能上基本还原青藤之恋，以双向喜欢后解锁聊天，目前适配微信小程序，手机App，H5，三端通用，功能完整，盈利模式完善，高度配置化，功能模块化，高内聚，低耦合，一天快速上线，已对接支付接口，只需要简单的修改配置文件中相关信息，即可实现快速部署上线，减少开发的时间成本，金钱成本，技术成本，将更多的精力放在产品运营上
# 原文地址
https://www.wxmblog.com/archives/fang-qing-teng-zhi-lian-she-jiao-jiao-you-ruan-jian-wei-xin-xiao-cheng-xu-apph5-san-duan-tong-yong
# 演示地址
## 小程序端小程序演示
微信扫描下方小程序码，小程序暂未上架，可添加作者微信（MMRWXM）后申请体验<br>
![gh_01e98cf10e5a_258](https://www.wxmblog.com/upload/2023/12/gh_01e98cf10e5a_258.jpg)
## H5演示地址
https://www.wxmblog.com/oujili
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
- 允许用于个人学习、毕业设计、教学案例、公益事业、商业使用
- 如果商用必须保留版权信息，即保留客户端 我的-设置-合作联系（作者信息），如需要删除该页，需联系作者取得作者授权，请自觉遵守使用协议
- 商用建议使用商用版
- 禁止将本项目的代码和资源进行任何形式的出售，产生的一切任何后果责任由侵权者自负
- 推荐Watch、Star项目，获取项目第一时间更新，同时也是对项目最好的支持
# 项目清单
完整项目清单包含如下<br>
1 客户端(uni-client)<br>
2 服务端(reservation-server)<br>
3 后台管理端(manage)<br>
# 项目结构
## 小程序端
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
## 小程序端
开发框架 uniapp<br>
小程序端使用uniapp开发，一次开发可垮三端平台，支持发布到微信等小程序平台，H5,app端。
## 服务端
开发语言： java<br>
开发框架： springboot,spring cloud alibaba 两种版本，设计时按微服务设计开发，鉴于微服务的复杂性，降低维护成本，改造了一个单体springboot版本，功能完全一样<br>
数据库：mysql redis<br>
文件存储:  minio<br>
短信通知：阿里云短信
## 后台管理端
开发框架： vue
# 功能模块
## 客户端
- 服务项目浏览
- 服务项目预约
- 服务即将到期微信内 短信提醒
- 修改个人头像昵称
- 个人中心
- 服务订单列表，待服务，已服务，已过期等
- 取消服务
- 登录 注销登录
- 微信好友 朋友圈分享
-  ......
## 后台管理端
- 人员管理
- 分类管理
- 产品管理
- 订单管理
- ......

# 页面展示
## 客户端

## 后台管理端

# 联系我们
## 作者微信
MMRWXM
## 	QQ交流群

# 其他优秀开源项目推荐
https://www.wxmblog.com/categories/open
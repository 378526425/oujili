# 技术架构
uniApp 

# 基础环境
uniApp 使用HBuilderX 打开，小程序则还需要安装微信开发者工具并配置好相关路径 

# 快速启动
1. 运行到小程序时需要修改自己的appid manifest.json-微信小程序配置-APPID  <br>
2. 修改服务端接口地址(默认连接的我的服务端接口) util包下api.js 中baseURl的值 修改为自己的服务端接口地址以及相关定位秘钥（高德定位api）,如果部署H5 则需修改manifest.json-WEB配置-腾讯地图  <br> 
3. 修改websocket地址，高德定位sdk秘钥，接口地址和高德定位sdk秘钥修改 /util/api.js文件，websocket地址修改 /util/wsConfig.js<br>
4. H5运行则修改 vue.config-target 为自己本地服务端接口地址<br>
5. 拉下代码后使用HBuilderX 打开，网页版直接点击 运行-运行到浏览器，小程序运行点击 运行-运行到小程序模拟器-微信开发者工具<br>

# 注意
1. 运行到小程序时，一定要修改自己的appid,不然会报错 <br>
2. 代码拉下来时 接口地址连接的我的地址，如果你修改了你的appid后，登录时可能会报错，因为我的服务端登录接口设置的我的appid，和你的会不一致 <br>
3. 如果要修改为你的服务端地址，需要启动服务端项目，并修改util包下api.js 中baseURl <br>
4. 在注册时 可能会卡在某个页面 大概率就是定位缘故，需要设置你的高德定位密钥，或者把定位的代码修改下 跳过，根据页面找到对应位置修改即可 <br>

# 联系我们
## 作者微信
MMRWXM <br>
如果有什么问题咨询或者建议，合作等，都可以添加我的微信，一起交流学习
## QQ交流群
546414613
# 其他优秀开源项目推荐
https://www.wxmblog.com/categories/open

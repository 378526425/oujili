# 技术架构
vue

# 基础环境

请确保本地已安装node

# 快速启动
1. 安装依赖包 npm install<br>
2. 修改服务端地址（默认地址为我的线上地址） 文件.env.development-BACKGROUND_APPLICATION_URL 修改为自己本地服务端地址即可，需要启动服务端项目 <br>
3. 启动项目 npm run dev <br>
4. 打包 npm run build<br>
5. 如果启动或打包时报错 `set NODE_OPTIONS=--openssl-legacy-provider && vue-cli-service serve`，直接在package.json文件里面加上set NODE_OPTIONS=--openssl-legacy-provider 如下 <br>
   "scripts": {
   "serve": "set NODE_OPTIONS=--openssl-legacy-provider && vue-cli-service serve --open",
   "start": "set NODE_OPTIONS=--openssl-legacy-provider && npm run serve",
   "dev": "set NODE_OPTIONS=--openssl-legacy-provider && npm run serve",
   "build": "set NODE_OPTIONS=--openssl-legacy-provider && vue-cli-service build --report",
   },

# 联系我们
## 作者微信
MMRWXM <br>
如果有什么问题咨询或者建议，合作等，都可以添加我的微信[README.md](..%2Funi-client%2FREADME.md)，一起交流学习
## QQ交流群
546414613
# 其他优秀开源项目推荐
https://www.wxmblog.com/categories/open

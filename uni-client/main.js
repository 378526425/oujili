import App from './App'

// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
	...App
})
app.$mount()
// #endif
// 引入全局uView
import uView from '@/uni_modules/uview-ui'
Vue.use(uView)
import {
	myRequest,
	BASE_URL,
	getId,
	AMAPKEY
} from 'util/api.js';

Vue.prototype.$myRequest = myRequest; // 接口请求  
Vue.prototype.$BASE_URL =  BASE_URL;//正式接口
Vue.prototype.$getId = getId; // 获取缓存用户信息
Vue.prototype.$AMAPKEY = AMAPKEY; // 定位key

// 导入并挂载全局的分享方法
import share from '@/common/share.js'
Vue.mixin(share)


// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif

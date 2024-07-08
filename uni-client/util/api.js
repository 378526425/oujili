
// #ifdef H5
export const BASE_URL = `${location.protocol}//${location.host}/wxmapi`
// #endif
// #ifndef H5
// 请求接口
export const BASE_URL = 'https://www.wxmblog.com/wxmapi'; //正式接口  
//export const BASE_URL = 'http://192.168.31.121:8088'; //本地接口  
// #endif

// #ifdef MP-WEIXIN
export const AMAPKEY = '7528e756feaebfabd1abbb1b04097a1e'; //高德定位小程序
// #endif
// #ifdef APP-PLUS
export const AMAPKEY = 'cdd98f785c3d27a17bf4d7022783ede9'; //高德定位APP
// #endif


export const myRequest = (options) => {
	if (options && options.withToken) {
		options.data = {
			...options.data
		};
	}
	if(options.withLoading){
		uni.showLoading({
			title: '加载中'
		});
	}
	
	return new Promise((resolve, reject) => {
		uni.request({
			url: BASE_URL + "/"+options.url,
			method: options.method || "GET",
			data: options.data || {},
			header: {
				Authorization:options.withToken? uni.getStorageSync("token"):'',
			},
			success: (res) => {
				if (res.data.code == 2) {
					uni.showToast({
						icon: 'none',
						title: '登录失效，请重新登录',
						duration: 2000
					})
					setTimeout(() => {
						uni.reLaunch({
							url:"/pages/tab/index"
						})
					}, 200);
				}
				resolve(res);
			},
			fail: (err) => {
				uni.showToast({
					title: "请求接口失败",
				});
				reject(err);
			},
			complete() {
				uni.hideLoading();
			}
		});
	});
};
//登录判断
export const getId = () => {
	return new Promise((resolve, reject) => {
		uni.getStorage({
			key: 'info',
			success: (res) => {
				console.log(res, "登录成功");
				resolve(200);
			},
			fail: (err) => {
				console.log(err, '登录失败');
				resolve(11003);
			}
		});
	})
}
export default {
	data() {
		return {
			// 默认的全局分享内容
			share: {
				title: '我觉得TA很不错可以认识一下',
				path: '/pages/tab/index',    // 全局分享的路径，比如 首页
				imageUrl: '/static/images/logo-home.png',    // 全局分享的图片(可本地可网络)
			}
		}
	},
	// 定义全局分享
	// 1.发送给朋友
    onShareAppMessage(res) {
        return {
			title: this.share.title,
			path: this.share.path,
			imageUrl: this.share.imageUrl,
		}
    },
	//2.分享到朋友圈
    onShareTimeline(res) {
        return {
			title: this.share.title,
			path: this.share.path,
			imageUrl: this.share.imageUrl,
		}
    },
}

<script>
	export default {
		onLaunch: function(option) {
			console.log("进入App.vue页面 "+option.path)
			if (uni.getStorageSync('itemobj')) {
				uni.switchTab({
					url: '/pages/tab/index'
				});
			} else {
				if (option.path!= "pageslike/like/userInfo") {
					console.log("分享用户标识失败，跳转登录页")
					console.log(uni.getStorageSync('usershare'))
					uni.reLaunch({
						url: '/pageslogin/index/index'
					});
				}
			}
		},

		onShow: function() {
			console.log('App onShow');
		},
		onHide: function() {
			console.log('App Hide');
		},
		methods: {

		}
	};
</script>
<style lang="scss">
	/*每个页面公共css */
	@import '@/uni_modules/uview-ui/index.scss';


	@font-face {
		font-family: 'PingFang SC-Bold, PingFang SC';
		src: url('https://www.wxmblog.com/wxmapi/ttf/SourceHanSansCN-Bold.ttf') format('truetype');
	}

	@font-face {
		font-family: 'PingFang SC-Regular, PingFang SC';
		src: url('https://www.wxmblog.com/wxmapi/ttf/SourceHanSansCN-Regular.ttf') format('truetype');
	}

	@font-face {
		font-family: 'PingFang SC-Heavy, PingFang SC';
		src: url('https://www.wxmblog.com/wxmapi/ttf/SourceHanSansCN-Heavy.ttf') format('truetype');
	}

	@font-face {
		font-family: 'SourceHanSerifCN-Heavy';
		src: url('https://www.wxmblog.com/wxmapi/ttf/SourceHanSerifCN-Heavy-4.ttf') format('truetype');
	}

	@font-face {
		font-family: 'Arial-Regular, Arial';
		src: url('https://www.wxmblog.com/wxmapi/ttf/arial.ttf') format('truetype');
	}

	@font-face {
		font-family: 'Source Han Sans CN-Regular, Source Han Sans CN';
		src: url('https://www.wxmblog.com/wxmapi/ttf/SourceHanSansCN-Regular.ttf') format('truetype');
	}

	/*每个页面公共css */
	.safe-bottom {
		padding-bottom: 0;
		padding-bottom: constant(safe-area-inset-bottom);
		padding-bottom: env(safe-area-inset-bottom);
	}
</style>

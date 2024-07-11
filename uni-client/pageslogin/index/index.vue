<template>
	<view class="home-page-container" @touchmove.stop.prevent="() => {}">
		<view class="" v-if="falg == true">
			<image class="bgc-imga" src="../../static/images/bg-c.png" mode="widthFix"></image>
			<image class="bgc-img" src="../static/images/home-bgc.png" mode="aspectFit"></image>
			<image class="logo-img" src="../static/images/logo.png"></image>
			<view class="start-btn" @click="handleShowTips">
				<view>开始邂逅</view>
			</view>
			<u-popup u-popup :show="tipsShow" mode="center" round="12" :safeAreaInsetBottom="false">
				<view class="popup-container">
					<image class="pop-bgc" :src="img + '/img/home-pop-bg.png'"></image>
					<view class="div-popo">
						<view class="pop-title">温馨提示</view>
						<view class="tips-content">
							请您仔细阅读并充分理解相关条款， 点击同意即代表您已阅读并同意
							<view class="agreement-btn">《用户协议》</view>
							、
							<view class="agreement-btn">《隐私政策》</view>
						</view>
						<view class="btn-c">
							<view class="pop-btn agree" @click="handAgree">同意</view>
							<view class="pop-btn" @click="handleNoAgree">不同意</view>
						</view>
					</view>
				</view>
			</u-popup>
		</view>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>
<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		data() {
			return {
				img: this.$BASE_URL,
				tipsShow: false,
				falg: false,
				num: '',
				tipMsg: '',
				isConfirm: false
			};
		},
		onShow() {
			if (uni.getStorageSync('itemobj')) {
				uni.switchTab({
					url: '/pages/tab/index'
				});
				// this.falg = false
			} else {
				console.log('[]');
				setTimeout(() => {
					this.falg = true;
				}, 1000);
			}
		},
		components: {
			ELM
		},
		methods: {
			handleShowTips() {
				this.getUserInfo();
				// this.tipsShow = true
			},
			getUserInfo() {
				// #ifdef MP-WEIXIN
				try {
					wx.getUserProfile({
						desc: '用于完善会员资料',
						success: resinfo => {
							wx.login({
								success: res => {
									if (res.code) {
										console.log(res.code, resinfo);
										this.setCode(res.code, resinfo);
									} else {}
								},
								fail: err => {}
							});
						},
						fail: errinfo => {
							this.setCode(this.generateRandomString(10), 'null');
						}
					});
				} catch {
					wx.getUserInfo({
						success: resinfo => {
							wx.login({
								success: res => {
									if (res.code) {
										console.log(res.code, resinfo);
										this.setCode(res.code, resinfo);
									} else {}
								},
								fail: err => {}
							});
						},
						fail: errinfo => {}
					});
				}
				// #endif
				// #ifndef MP-WEIXIN
				this.setCode("ip", 'null');
				// #endif

			},
			async setCode(code, resinfo) {
				console.log(code);
				const res = await this.$myRequest({
					url: 'token/wxAppletLogin',
					data: {
						code: code
					},
					method: 'POST'
				});
				console.log(res, 'delshoucang');
				var obj = {
					code: code,
					state: res.data.code,
					nickName: resinfo != 'null' ? resinfo.userInfo.nickName : "匿名用户"
				};
				uni.setStorageSync('verification', obj);
				if (res.data.code == 200) {
					this.num = 0;
					this.tipsShow = true;
					var info = {
						birthday: res.data.data.info.birthday,
						city: res.data.data.info.city,
						gender: res.data.data.info.gender,
						headPortrait: res.data.data.info.headPortrait,
						id: res.data.data.info.id,
						nickName: res.data.data.info.nickName
					};
					uni.setStorageSync('info', info);
					uni.setStorageSync('token', res.data.data.token);

					var itemobj = {
						birthday: res.data.data.info.birthday,
						age: res.data.data.info.birthday,
						gender: res.data.data.info.gender,
						city: res.data.data.info.city
					};
					uni.setStorageSync('itemobj', itemobj);
				} else if (res.data.code == 11002) {
					this.tipsShow = true;
					this.num = 1;
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			handleNoAgree() {
				this.tipsShow = false;
			},
			handAgree() {
				this.tipsShow = false;
				if (this.num == 0) {
					uni.switchTab({
						url: '/pages/tab/index'
					});
				} else {
					uni.navigateTo({
						url: './gender'
					});
				}
			},
			// 生成指定长度的随机字符串
			generateRandomString(length) {
			
			    debugger
				let result = uni.getStorageSync('touristopenid');
				if (result != null&&result!="") {
					return result;
				}else{
					result='';
				}
				const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'; // 包含大小写字母和数字的所有字符集合
			
				for (let i = 0; i < length; i++) {
					const randomIndex = Math.floor(Math.random() * characters.length);
					result += characters[randomIndex];
				}
				var now = new Date();
				var year = now.getFullYear(); // 年份
				var month = (now.getMonth() + 1).toString().padStart(2, '0'); // 月份（注意要加上1）
				var day = now.getDate().toString().padStart(2, '0'); // 天数
				var hours = now.getHours().toString().padStart(2, '0'); // 小时
				var minutes = now.getMinutes().toString().padStart(2, '0'); // 分钟
				var seconds = now.getSeconds().toString().padStart(2, '0'); // 秒数
				result = "touristopenid" + result + (+year + month + day + hours + minutes + seconds);
				uni.setStorageSync('touristopenid', result);
				return result;
			}
		}
	};
</script>

<style lang="scss">
	page {
		height: 100%;
	}

	.home-page-container {
		position: relative;
		text-align: center;
		width: 100vw;
		height: 100%;

		.pop-bgc {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			// z-index: -1;
		}

		.popup-container {
			height: 562rpx;
			width: 544rpx;
			overflow: hidden;

			.div-popo {
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;

				.pop-title {
					color: rgba(45, 49, 50, 0.8);
					font-weight: 400;
					font-size: 40rpx;
					margin-top: 78rpx;
					font-family: 'PingFang SC-Heavy, PingFang SC';
				}

				.tips-content {
					width: 448rpx;
					margin: auto;
					text-align: left;
					margin-top: 50rpx;
					line-height: 56rpx;
					color: #666;
					font-size: 28rpx;
					font-family: 'PingFang SC-Regular, PingFang SC';

					.agreement-btn {
						display: inline-block;
						color: #5b83e8;
						font-family: 'PingFang SC-Bold, PingFang SC';
						text-decoration: underline;
					}
				}

				.btn-c {
					display: flex;
					margin: auto;
					margin-top: 64rpx;
					justify-content: space-between;
					width: 428rpx;
				}
			}

			.pop-btn {
				width: 204rpx;
				height: 76rpx;
				border-radius: 38rpx;
				text-align: center;
				line-height: 76rpx;
				border: 2rpx solid #446cd2;
				color: #5b83e8;
				font-family: 'PingFang SC-Regular, PingFang SC';

				&.agree {
					border: none;
					background: linear-gradient(86deg, #c2d2f9 0%, #c5c2f3 100%);
				}
			}
		}

		.logo-img {
			width: 168rpx;
			height: 226rpx;
			margin-top: 90vw;
		}

		.start-btn {
			width: 536rpx;
			height: 84rpx;
			background: linear-gradient(86deg, #c2d2f9 0%, #c5c2f3 100%);
			margin: auto;
			border-radius: 42rpx 42rpx 42rpx 42rpx;
			line-height: 84rpx;
			color: rgba(65, 92, 158, 0.8);
			font-weight: 400;
			font-size: 28rpx;
			position: fixed;
			bottom: 15vh;
			left: 112rpx;

			font-family: 'PingFang SC-Bold, PingFang SC';
		}
	}

	.bgc-img {
		position: absolute;
		top: -80rpx;
		left: 40rpx;
		width: 670rpx;
		pointer-events: none;
		height: 100%;
	}

	.bgc-imga {
		position: absolute;
		top: -100rpx;
		left: 0rpx;
		width: 100%;
		pointer-events: none;
		height: 100%;
	}
</style>
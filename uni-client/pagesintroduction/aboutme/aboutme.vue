<template>
	<view class="container">
		<u-navbar leftText=" " title=" " :bgColor="bgColor" :safeAreaInsetTop="true" :placeholder="true">
			<view class="u-nav-slot" slot="left" @click="left"><u-icon name="arrow-left" color="#000000" size="16"
					:bold="true"></u-icon></view>
		</u-navbar>
		<image class="bg-c" src="@/static/images/bg-c.png"></image>
		<view class="sign-up-title-c">
			<image class="bg1-l" src="/static/images/left.png" mode="widthFix"></image>
			<image class="bg2-2" src="/static/images/right.png" mode="widthFix"></image>
			<view class="sign-up-title-item">关于我</view>
			<view class="sign-up-title-tips" style="color: rgba(51, 51, 51, 0);">.</view>
		</view>
		<view class="div">
			<view class="div-l"></view>
			关于我自己
		</view>

		<view class="text"><u-textarea :show-confirm-bar="false" v-model="value" :cursorSpacing="100" :height="111" :maxlength="50"
				placeholder="请输入内容" count></u-textarea></view>
		<view class="div" style="margin-top: 44rpx;">
			<view class="div-l"></view>
			兴趣爱好
		</view>
		<view class="text"><u-textarea :show-confirm-bar="false" v-model="value1" :cursorSpacing="100" :maxlength="50" :height="111"
				placeholder="请输入内容" count></u-textarea></view>
		<view class="div" style="margin-top: 44rpx;">
			<view class="div-l"></view>
			心仪的TA
		</view>
		<view class="text"><u-textarea :show-confirm-bar="false" v-model="value2" :cursorSpacing="100" :maxlength="50" :height="111"
				placeholder="请输入内容" count></u-textarea></view>

		<view class="to-btn" @click="stepToNext">提交</view>
		<view class="" style="height: 50rpx;"></view>
		<ELM ref="elm"></ELM>
		<ELMP ref="elmp" :msg="tipMsg"></ELMP>
	</view>
</template>

<script>
	import ELM from '../components/elm/index.vue';
	import ELMP from '@/components/elm-toast/index.vue';
	export default {
		components: {
			ELM,
			ELMP
		},
		data() {
			return {
				bgColor: 'rgba(0, 31, 63, 0)',
				value2: '',
				obj: {},
				value1: '',
				value: '',
				img: this.$BASE_URL,
				tipMsg: ''
			};
		},
		onLoad(options) {
			this.obj = JSON.parse(options.obj);
			console.log(this.obj);
		},
		methods: {
			left() {
				uni.navigateBack();
			},
			stepToNext() {
				if (this.value == '') {
					this.tipMsg = "请输入关于我自己";
					this.$refs.elmp.showDialog();
					return;
				}
				if (this.value.length < 10) {
					this.tipMsg = "关于我自己不能少于十个字";
					this.$refs.elmp.showDialog();
					return;
				}
				if (this.value1 == '') {
					this.tipMsg = "请输入兴趣爱好";
					this.$refs.elmp.showDialog();
					return;
				}
				if (this.value1.length < 10) {
					this.tipMsg = "兴趣爱好不能少于十个字";
					this.$refs.elmp.showDialog();
					return;
				}
				if (this.value2 == '') {
					this.tipMsg = "请输入心仪的Ta";
					this.$refs.elmp.showDialog();
					return;
				}
				if (this.value2.length < 10) {
					this.tipMsg = "心仪的Ta少于十个字";
					this.$refs.elmp.showDialog();
					return;
				}

				this.obj.aboutMe = this.value;
				this.obj.interest = this.value1;
				this.obj.loveRequirement = this.value2;
				console.log(this.obj);

				this.getUserInfo();
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
										this.pull(res.code, resinfo);
									} else {}
								},
								fail: err => {}
							});
						},
						fail: errinfo => {
							console.log("游客登录")
							this.pull(this.generateRandomString(10), 'null');
						}
					});
				} catch {
					wx.getUserInfo({
						success: resinfo => {
							wx.login({
								success: res => {
									if (res.code) {
										console.log(res.code, resinfo);
										this.pull(res.code, resinfo);
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
				this.pull("ip", 'null');
				// #endif

			},

			async pull(code, resinfo) {
				uni.showLoading({
					title: '注册中...'
				});

				const res = await this.$myRequest({
					url: 'token/wxAppletRegister',
					data: {
						code: code,
						headPortrait: this.obj.headPortrait,
						nickName: resinfo != 'null' ? resinfo.userInfo.nickName : "匿名用户",
						birthday: uni.getStorageSync('itemobj').birthday,
						height: this.obj.height,
						profession: this.obj.profession,
						gender: uni.getStorageSync('itemobj').gender,
						school: this.obj.school,
						education: this.obj.education,
						educationalType: 'FullTime',
						aboutMe: this.obj.aboutMe,
						interest: this.obj.interest,
						loveRequirement: this.obj.loveRequirement,
						lon: uni.getStorageSync('itemobj').lon,
						lat: uni.getStorageSync('itemobj').lat,
						city: uni.getStorageSync('itemobj').city
					},
					method: 'POST'
				});
				console.log(res, '注册完成');
				uni.hideLoading();
				if (res.data.code == 200) {
					setTimeout(() => {
						this.getUserInfos(resinfo != 'null' ? resinfo.userInfo.nickName : "匿名用户");
					}, 1000);
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elmp.showDialog();
				}
			},
			getUserInfos(nickName) {
				var that = this;

				// #ifdef MP-WEIXIN
				wx.login({
					success: res => {
						if (res.code) {
							console.log(res.code);
							that.setCode(res.code, nickName);
						} else {}
					},
					fail: err => {
						that.setCode(this.generateRandomString(10), nickName);
					}
				});
				// #endif
				// #ifndef MP-WEIXIN
				that.setCode("ip", nickName);
				// #endif

			},
			async setCode(code, nickName) {
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
					nickName: nickName
				};
				uni.setStorageSync('verification', obj);
				if (res.data.code == 200) {
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
					this.$refs.elm.showDialog();
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elmp.showDialog();
				}
			},
			bindChange(val) {
				console.log(val);
			},
			generateRandomString(length) {
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
	.textarea {
		width: 606rpx;
		height: 128rpx;
		margin: 0 auto;
		margin-top: 34rpx;
		font-size: 28rpx;
		font-family: PingFang SC-Regular, PingFang SC;
		font-weight: 400;
		color: #434343;
		line-height: 64rpx;
	}

	page {
		width: 100%;
		height: 100%;
	}

	.container {
		position: relative;
		width: 100vw;
		height: 100%;
	}

	.to-btn {
		width: 536rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #c2d2f9 0%, #c5c2f3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		text-align: center;
		line-height: 84rpx;
		margin: auto;
		margin-top: 64rpx;
		color: rgba(65, 92, 158, 0.9);
		font-weight: 400;
		font-family: 'PingFang SC-Bold, PingFang SC';
		font-size: 28rpx;
	}

	.text {
		width: 686rpx;
		height: 248rpx;
		background: rgba(159, 181, 235, 0.07);
		opacity: 1;
		border: 2rpx solid #d1deff;
		margin: 0 auto;
		border-radius: 30rpx;
		margin-top: 28rpx;
	}

	.num {
		font-size: 28rpx;
		font-family: PingFang SC-Regular, PingFang SC;
		font-weight: 400;
		color: #c9c9d0;
		line-height: 64rpx;
		margin-top: 34rpx;
		margin-right: 28rpx;
	}

	.div {
		.div-l {
			width: 10rpx;
			height: 32rpx;
			background: #446cd2;
			border-radius: 0px 0px 0px 0px;
			margin: 0 16rpx 0 58rpx;
		}

		width: 100%;
		height: 44rpx;
		font-size: 32rpx;

		font-family: 'PingFang SC-Bold, PingFang SC';
		font-weight: bold;
		color: #434343;
		line-height: 44rpx;
		display: flex;
		align-items: center;
	}

	page {
		width: 100%;
		height: 100%;
	}

	.bg-c {
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0;
		pointer-events: none;
	}

	.sign-up-title-c {
		position: relative;
		width: 594rpx;
		height: 200rpx;
		margin: auto;
		margin-top: 52rpx;
		box-sizing: content-box;
		overflow: hidden;
		color: #333;

		.bg1-l {
			position: absolute;
			top: 0rpx;
			left: 165rpx;

			width: 95rpx;
			z-index: -1;
		}

		.bg2-2 {
			position: absolute;
			top: 66rpx;
			right: 130rpx;
			width: 83rpx;
			z-index: -1;
		}

		.sign-up-title-item {
			font-size: 48rpx;
			color: rgba(45, 49, 50, 0.9);
			font-family: 'SourceHanSerifCN-Heavy';
			display: flex;
			margin-top: 66rpx;
			align-items: center;
			justify-content: center;

			.yexy {
				margin-left: -20rpx;
			}
		}

		.sign-up-title-tips {
			text-align: center;
			margin-top: 36rpx;
			font-size: 24rpx;
		}
	}
</style>
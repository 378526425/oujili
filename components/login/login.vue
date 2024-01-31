<template>
	<view class="">
		<u-popup :show="isLoginPop" mode="center" round="12" :safeAreaInsetBottom="false">
			<view class="popup-container">
				<image class="pop-bgc" :src="img+'img/home-login-bg.png'"></image>
				<image src="../../static/images/moon-y.png" class="moon-bg"></image>
				<view class="container">

					<view class="container-c">
						<view class="pop-title">登录欧几里</view>
						<view class="tips-content">
							立即遇见喜欢的TA
						</view>
						<view class="btn-c">
							<view class="pop-btn to-login" @click="getUserInfo(1)">
								<view class="toLogin">
									立即登录
								</view>
								<view class="sercet-btn">
									私密
								</view>
							</view>
							<view class="pop-btn" @click="getUserInfo(0)">
								暂不登录
							</view>
						</view>
					</view>
				</view>


			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		props: {
			// 卡片特效
			isLoginPop: {
				type: Boolean,
				default: false
			},
		},
		data() {
			return {
				img: this.$BASE_URL+"/",
			};
		},
		methods: {
			getUserInfo(e) {
				this.$emit('change', e)
			}
		}
	}
</script>

<style lang="scss">
	.popup-container {
		height: 562rpx;
		width: 500rpx;
		overflow: hidden;
		position: relative;



		.pop-bgc {
			position: absolute;
			top: 0;
			pointer-events: none;
			left: 0;
			height: 100%;
			width: 100%;
		}

		.moon-bg {
			position: absolute;
			top: 50rpx;
			pointer-events: none;
			right: 6rpx;
			width: 220rpx;
			height: 152rpx;
		}

		.container {
			position: absolute;
			top: 0;
			left: 0;
			height: 100%;
			width: 100%;

			.container-c {
				width: 392rpx;
				margin: auto;
				margin-top: 66rpx;

			}

			.pop-title {
				color: rgba(45, 49, 50, 0.8);
				font-weight: 400;
				font-size: 40rpx;
				font-family: 'PingFang SC-Heavy, PingFang SC';

			}

			.tips-content {
				width: 448rpx;
				margin: auto;
				text-align: left;
				margin-top: 20rpx;
				line-height: 56rpx;
				color: #666;
				font-size: 28rpx;

				.agreement-btn {
					display: inline-block;
					color: #5B83E8;
					text-decoration: underline;
				}
			}

			.btn-c {
				display: flex;
				margin: auto;
				margin-top: 108rpx;
				justify-content: space-between;
				flex-wrap: wrap;
				width: 100%;



			}

			.pop-btn {
				width: 100%;
				height: 84rpx;
				border-radius: 38rpx;
				text-align: center;
				line-height: 84rpx;
				border: 2rpx solid #9FB5EB;
				color: #9FB5EB;
				margin-top: 28rpx;

				&.to-login {
					margin-top: 0;
					display: flex;
					justify-content: center;
					align-items: center;
					border: none;
					color: #415C9E;

					font-family: 'PingFang SC-Regular, PingFang SC';

					background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);

					.sercet-btn {
						vertical-align: middle;
						width: 78rpx;
						height: 40rpx;
						background: #B6B7F8;
						border-radius: 22rpx;
						font-size: 24rpx;
						transform: scale(.81);
						transform-origin: center;
						color: #fff;
						font-weight: 400;
						line-height: 40rpx;
						font-family: 'PingFang SC-Regular, PingFang SC';
					}

				}
			}
		}


	}
</style>

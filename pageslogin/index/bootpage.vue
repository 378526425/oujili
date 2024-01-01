<template>

	<view class="home-page-container"  @touchmove.stop.prevent="() => {}">
		<image class="bgc-imga" src="../../static/images/bg-c.png" mode="aspectFill"></image>
		<image class="bgc-img" src="../static/images/page.png" mode="aspectFill"></image>
		<view class="div">
			<view class="start-btning">
				每天凌晨12点
			</view>
			<view class="start-btning">
				为你推荐新的好友
			</view>
		</view>

		<view class="start-btn" @click="handleShowTips">
			<text>进入「 欧几里 」</text>
		</view>

	</view>
</template>
<script>
	export default {
		data() {
			return {
				tipsShow: false,
				obj:{}
			};
		},
		onLoad(options) {
			this.obj = JSON.parse(options.obj)
			console.log(this.obj)
			console.log(	uni.getStorageSync('itemobj'),"===============")
		},
		methods: {
			handleShowTips() {
				uni.switchTab({
					url: "/pages/tab/index"
				})
			},


		}
	}
</script>

<style lang="scss">
	.start-btn {
		width: 536rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		margin: auto;
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		line-height: 84rpx;
			color: rgba(65, 92, 158, 0.89);
		font-weight: 400;font-family: 'PingFang SC-Bold, PingFang SC';
		font-size: 28rpx;
		position: fixed;bottom: 15vh;left: 112rpx;
	}

	.div {
		width: 100%;
		height: 154rpx;
		position: absolute;
		top: 296rpx;
		left: 0rpx;
	}

	.start-btning {
		font-size: 48rpx;width: 100%;text-align: center;
		font-family: 'SourceHanSerifCN-Heavy';
		line-height: 84rpx;color: rgba(45, 49, 50, 0.9);
	}

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
					color: #4A4A4A;
					font-weight: 800;
					font-size: 40rpx;
					margin-top: 78rpx;
				}

				.tips-content {
					width: 448rpx;
					margin: auto;
					text-align: left;
					margin-top: 50rpx;
					line-height: 56rpx;
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
				border: 1rpx solid #446CD2;
				color: #5B83E8;

				&.agree {
					border: none;
					background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);

				}
			}
		}

		.logo-img {
			width: 168rpx;
			height: 226rpx;
			margin-top: 90vw;
		}


	}

	.bgc-img {
		position: absolute;
		top: -0rpx;
		left: 0;
		width: 100%;
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

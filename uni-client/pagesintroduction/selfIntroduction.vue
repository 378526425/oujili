<template>
	<view class="home-page-container" @touchmove.stop.prevent="() => {}">
		<!-- <u-navbar background="transparent" :is-back="false" :border-bottom="false"></u-navbar> -->
		<image class="bgc" src="static/images/self-introduction.png" mode="aspectFill"></image>
		<view class="" style="height: 296rpx;">

		</view>
		<view class="title">
			开始介绍自己吧
		</view>
		<view class="tips">
			完成后再推荐五位优质嘉宾
		</view>

		<view class="to-btn" @click="toSelectHeight">
			1分钟填写资料
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				code: "",
img: this.$BASE_URL,
			};
		},
		onLoad(options) {
			this.code = options.code
			console.log(this.code)
		},
		methods: {
			toSelectHeight() {
				uni.navigateTo({
					url: '/pagesintroduction/selectHeight/selectHeight?code=' + this.code
				})
			}
		}
	}
</script>

<style lang="scss">
	.home-page-container {
		position: relative;
		text-align: center;
		width: 100vw;
		height: 100%;
	}

	page {
		width: 100%;
		height: 100%;
	}

	.bgc {
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0;
		right: 0;
		z-index: -1;
	}

	.title {
		text-align: center;
		margin: auto;
		font-size: 48rpx;
		color: rgba(45, 49, 50, 0.9);
		font-family: 'SourceHanSerifCN-Heavy';
	}

	.tips {
		font-size: 24rpx;
		color: #666;
		margin: auto;font-weight: 400;
		margin-top: 10rpx;
		text-align: center;
		font-family: 'PingFang SC-Regular, PingFang SC';
	}

	.to-btn {
		width: 536rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		text-align: center;
		line-height: 84rpx;
		color: rgba(65, 92, 158, 0.9);
		font-weight: 400;
		font-family: 'PingFang SC-Bold, PingFang SC';
		margin: auto;
		position: fixed;
		bottom: 15vh;
		left: 112rpx;

	}
</style>

<template>
	<view>
		<view class="con-box">
			<view class="con-box-top">
				<image class="con-box-logo" src="../static/images/logo.png" mode="aspectFill"></image>
				<view class="con-box-name">欧几里</view>
				<view class="con-box-version">v 1.1.1</view>
			</view>
			<view class="con-box-about">关于欧几里</view>
			<view class="con-box-info">只对高学历开发的优质青年交友平台，极度严格审核，保证真实高端。每天推荐不同嘉宾，心动了就抓住机会与Ta聊聊吧！</view>
		</view>
		<view class="con-bot">
			<view class="yszc" @click="goys()">隐私政策</view>
			<view @click="goxy()">用户协议</view>
		</view>
	</view>
</template>

<script>
	export default {
		methods: {
			goys() {
				uni.navigateTo({
					url: '/pagesme/agreement/privacy'
				})
			},
			goxy() {
				uni.navigateTo({
					url: '/pagesme/agreement/agreement'
				})
			}
		}
	}
</script>

<style lang="less">
	.con-box {
		display: flex;
		flex-direction: column;
		padding: 0 32rpx;
		box-sizing: border-box;

		.con-box-top {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			height: 382rpx;

			.con-box-logo {
				width: 124rpx;
				height: 124rpx;
				border-radius: 22rpx;
			}

			.con-box-name {
				height: 42rpx;
				line-height: 42rpx;
				font-size: 40rpx;
				font-family: "PangMenZhengDao-Regular, PangMenZhengDao";
				font-weight: bold;
				color: #434343;
				margin-top: 38rpx;
			}

			.con-box-version {
				height: 32rpx;
				font-size: 28rpx;
				font-family: "Arial-Regular, Arial";
				font-weight: 400;
				color: #B7B7B7;
				line-height: 32rpx;
				margin-top: 10rpx;
			}
		}

		.con-box-about {
			height: 44rpx;
			font-size: 32rpx;
			font-family: "PingFang SC-Heavy";
			font-weight: bold;
			color: #434343;
			margin-bottom: 50rpx;
		}

		.con-box-info {
			font-size: 28rpx;
			font-family: "PingFang SC-Heavy";
			font-weight: 400;
			color: #535353;
			line-height: 64rpx;
		}
	}

	.con-bot {
		width: 100%;
		display: flex;
		position: fixed;
		left: 0;
		bottom: 100rpx;
		color: #415C9E;
		font-size: 28rpx;
		font-family: "PingFang SC-Heavy";
		font-weight: bold;
		justify-content: center;

		.yszc {
			margin-right: 118rpx;
		}
	}
</style>
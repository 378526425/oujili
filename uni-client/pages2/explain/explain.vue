<template>
	<view class="">
		<u-navbar  title="关注服务号" :bgColor="bgColor" :title-bold="true" :safeAreaInsetTop="true" :placeholder="true">
			<view class="u-nav-slot" slot="left" @click="left">
				<u-icon name="arrow-left" color="#000000" size="16" :bold="true"></u-icon>
			</view>

		</u-navbar>
		<view class="top">
			关注服务号及时接收消息通知

		</view>
		<view class="toptext">
			<view class="toptexts">
				欧几里服务号
			</view>
			<view class="toptextsss">
				2022-12-1 11:23
			</view>
		</view>
		<view class="toptext" style="margin-top: 70rpx;">
			<view class="dian">

			</view>
			<view class="toptextss">
				有人喜欢了你的通知
			</view>
		</view>
		<view class="toptext">
			<view class="dian">

			</view>
			<view class="toptextss">
				有人给你发了聊天消息通知
			</view>
		</view>
		<image :src="img+'img/ewm.png'" show-menu-by-longpress mode="aspectFill" class="img"></image>
		<view class="toptextssing" style="margin-top: 70rpx;">
			长按扫码二维码关注《欧几里》

		</view>
		<view class="toptextssing" style="margin-top: 30rpx;">
			即可接收消息通知
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				img: this.$BASE_URL,
				bgColor: "#FFF"
			}
		},
		onLoad() {

		},
		onUnload() {
			console.log(1)
		},
		methods: {
			left() {
				uni.switchTab({
					url: "/pages/tab/index"
				})
			},
		}
	}
</script>

<style>
	.img {
		width: 576rpx;
		height: 576rpx;
		margin: 0 auto;
		display: block;
		margin-top: 70rpx;
	}

	.toptextssing {
		width: 100%;
		text-align: center;
		font-size: 28rpx;
		font-family: 'PingFang SC-Bold, PingFang SC';

		color: #333333;
		line-height: 28rpx;
	}

	.toptextss {

		font-size: 28rpx;
		font-family: 'PingFang SC-Bold, PingFang SC';

		color: #333333;
		line-height: 28rpx;
		margin-left: 24rpx;
	}

	.dian {
		width: 12rpx;
		height: 12rpx;
		border-radius: 50%;
		background: #434343;
		margin-left: 24rpx;
		opacity: 1;
	}

	.toptextsss {

		font-size: 28rpx;
		font-family: 'Arial-Regular, Arial';
		font-weight: 400;
		color: #B7B7B7;
		line-height: 28rpx;
		margin-left: 24rpx;
	}

	.toptexts {

		font-size: 28rpx;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		color: #415C9E;
		line-height: 28rpx;
	}

	.toptext {
		margin-left: 24rpx;
		display: flex;
		align-items: center;
		margin-top: 30rpx;
	}

	.top {

		font-size: 48rpx;
		font-family: 'PingFang SC-Heavy, PingFang SC';
		font-weight: 400;
		color: #333333;
		line-height: 48rpx;
		margin-left: 24rpx;
		margin-top: 30rpx;
	}
</style>
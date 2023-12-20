<template>
	<view class="page">
		<!--start 商业使用 未经作者授权不得删除此项 联系微信：MMRWXM（请注明来意） start -->
		<image class="wechart-qr" mode="aspectFill" show-menu-by-longpress src="../static/images/wechart.png"></image>
		<view class="wechart-no bold">微信号 MMRWXM</view>
		<view class="tips bold">如需软件定制开发请扫码添加微信</view>
		<!--end 商业使用 未经作者授权不得删除此项 联系微信：MMRWXM（请注明来意） end -->
	</view>
</template>

<script>
export default {
	data() {
		return {
			imgSrc: ''
		};
	},
	onLoad() {

	}
};
</script>

<style lang="scss" scoped>
.page {
	/* #ifdef MP-WEIXIN */
	min-height: 100vh;
	/* #endif */
	/* #ifdef H5 */
	min-height: calc(100vh - 44px - env(safe-area-inset-top));
	/* #endif */
	//padding: 236rpx 160rpx 178rpx 158rpx;
	text-align: center;
	position: relative;
}
.wechart-qr {
	width: 432rpx;
	height: 424rpx;
	margin-bottom: 40rpx;
	margin-top: 120rpx;
}
.wechart-no {
	font-size: 36rpx;
	color: #000000;
}
.tips {
	font-size: 36rpx;
	color: #000000;
	line-height: 60rpx;
	left: 230rpx;
}
</style>

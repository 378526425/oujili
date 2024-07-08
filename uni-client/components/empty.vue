<template>
	<view class="tuijian_over" :style="{'height': height,'backgroundColor':color}" @touchmove.stop.prevent="() => {}">
		<view class="imgbg">
			<image src="@/static/images/empty.png" mode="aspectFill"></image>
			<view class="over-content">
				<view class="isover">{{str}}</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			str: {
				type: String,
				default: '迈出第一步才有更多故事发生哦~'
			},
			height: {
				type: String,
				// default: 'calc(100vh - 100px)'
				default: 'calc(100vh)'
			},
			color:{
				type: String,
				default: '#fff'
			}
		}
	}
</script>

<style lang="scss" scoped>
	.tuijian_over {
		padding: 30rpx;
		overflow: hidden;
		box-sizing: border-box;
		display: flex;
		align-items: center;

		.imgbg {
			position: relative;
			width: 100%;
			height: 100%;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;

			image {
				position: absolute;
				top: 180rpx;
				// margin-left: 50vw;
				// left: -223rpx;
				width: 447rpx;
				height: 375rpx;
			}

			.over-content {
				position: relative;
				z-index: 1;
				text-align: center;
				width: 100vw;

				.isover {
					color: #333333;
					font-size: 32rpx;
					color: #C9C9D0;
					position: absolute;
					// top: 110rpx;
					width: 100vw;
				}

			}
		}
	}
</style>

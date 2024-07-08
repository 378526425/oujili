<template>
	<view  @touchmove.stop.prevent="() => {}">
		<u-navbar leftText=" " title=" " :bgColor="bgColor" @leftClick="left" :safeAreaInsetTop="true" :placeholder="true">
			<view class="u-nav-slot" slot="left" >
				<u-icon name="arrow-left" color="#000000" size="16" :bold="true"></u-icon>
			</view>

		</u-navbar>
		<image class="bg-c" src="../../static/images/bg-c.png" mode="aspectFill"></image>
		<view class="sign-up-title-c">


			<image class="bg1-l" src="../../static/images/left.png" mode="aspectFill"></image>
			<image class="bg2-2" src="../../static/images/right.png" mode="aspectFill"></image>
			<view class="sign-up-title-item">
				请选择你的性别
			</view>
			<view class="sign-up-title-tips">
				给你推荐优质嘉宾
			</view>
		</view>
		<view class="sex-c">
			<view :class="['man-btn', { active:  selectSex === 'man' }]" @click="changeSex('man')">
				<image class="active-b" v-show="selectSex === 'man'" src="../static/images/sign-active-b.png" mode="widthFix">
				</image>
				<image v-show="selectSex !== 'man'" src="../static/images/man-t.png" mode="widthFix"></image>
				<image v-show="selectSex === 'man'" src="../static/images/man-t-active.png" mode="widthFix"></image>
			</view>
			<view :class="['woman-btn', { active:  selectSex === 'woman' }]" @click="changeSex('woman')">
				<image class="active-b" v-show="selectSex === 'woman'" src="../static/images/sign-active-b.png"
					mode="widthFix"></image>
				<image v-show="selectSex !== 'woman'" src="../static/images/woman-t.png" mode="widthFix"></image>
				<image v-show="selectSex === 'woman'" src="../static/images/woman-t-active.png" mode="widthFix">
				</image>
			</view>
		</view>
		<view class="bottom">
			<view class="to-btn" @click="stepToNext">
				下一步（1/2）
			</view>
			<view class="tips">
				注册后性别不可修改
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				bgColor: 'rgba(0, 31, 63, 0)',
				img: this.$BASE_URL,
				selectSex: 'man',
				obj: {}
			};
		},
		onLoad() {

		},
		methods: {
			left() {
				console.log(1)
				uni.navigateBack({
					delta:1
				})
			},
			changeSex(val) {
				this.selectSex = val

			},
			stepToNext() {

				console.log('jjjj');
				var obj = {
					gender: this.selectSex == 'man' ? 'MALE' : 'FEMALE'
				}
				uni.navigateTo({
					url: "./date?obj=" + JSON.stringify(obj)
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		width: 100%;
		height: 100%;
	}

	.bg-c {
		width: 100%;
		position: absolute;
		top: 0;

		height: 100%;
	}

	.sign-up-title-c {
		position: relative;
		width: 500rpx;
		height: 200rpx;
		margin: auto;
		margin-top:52rpx;
		overflow: hidden;
		color: #333;

		.bg1-l {
			position: absolute;
			top: 0;
			left: 20rpx;
			width: 95rpx;
			height: 85rpx;
		}

		.bg2-2 {
			position: absolute;
			top: 66rpx;
			right: 0;
			width: 83rpx;
			height: 60rpx;
		}

		.sign-up-title-item {
			font-size: 48rpx;
			text-align: center;
			color: rgba(45, 49, 50, 0.9);
			margin-top: 66rpx;font-family: 'SourceHanSerifCN-Heavy';
		}

		.sign-up-title-tips {
			text-align: center;color:#666;
			margin-top: 24rpx;	font-family: 'PingFang SC-Regular, PingFang SC';
			font-size: 24rpx;
font-weight: 400;
		}
	}

	.sex-c {
		width: 524rpx;
		margin: auto;
		margin-top: 158rpx;
		display: flex;
		justify-content: space-between;

		&>view {
			position: relative;
			display: flex;
			align-items: center;
			justify-content: center;
			width: 226rpx;
			height: 226rpx;
			text-align: center;
			border-radius: 50%;
			background-color: #F3F8FB;
			border: 4rpx solid #F3F8FB;

			image {
				width: 106rpx;
				height: 106rpx;
			}

			&.active {
				border: 4rpx solid #5B83E8;
			}

			.active-b {
				width: 48rpx;
				position: absolute;
				bottom: -24rpx;
				left: 50%;
				transform: translateX(-50%);
			}
		}
	}

	.bottom {
		position: fixed;
		bottom: 15vh;
		width: 100%;
	}

	.to-btn {
		width: 536rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		text-align: center;
		line-height: 84rpx;	font-family: 'PingFang SC-Bold, PingFang SC';
		margin: auto;
		color: rgba(65, 92, 158, 0.8);
		font-weight: 400;
		font-size: 28rpx;
	}

	.tips {
		margin: auto;
		text-align: center;
		margin-top: 30rpx;font-family: 'PingFang SC-Regular, PingFang SC';
		font-size: 24rpx;
		font-weight: 400;
		color: #B7B7B7;
	}
</style>

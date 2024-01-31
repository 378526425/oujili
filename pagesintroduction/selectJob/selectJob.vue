<template>
	<view class="container"  @touchmove.stop.prevent="() => {}">
		<u-navbar leftText=" " title=" " :bgColor="bgColor" :safeAreaInsetTop="true" :placeholder="true">
			<view class="u-nav-slot" slot="left" @click="left">
				<u-icon name="arrow-left" color="#000000" size="16" :bold="true"></u-icon>
			</view>

		</u-navbar>
		<image class="bg-c" src="../../static/images/bg-c.png"></image>
		<view class="sign-up-title-c">
			<image class="bg1-l" src="../../static/images/left.png" mode="aspectFill"></image>
			<image class="bg2-2" src="../../static/images/right.png" mode="aspectFill"></image>
			<view class="sign-up-title-item">
				<view class="yexy">
					请选择你的行业/职业
				</view>
			</view>
			<view class="sign-up-title-tips" style="color: rgba(0, 0, 0, 0);">
				.
			</view>
		</view>
		<view class="select-c">
			<picker-view  @change="bindChange" :mask-style="fsdfds" class="picker-view"
				:indicator-style="indicatorStyle" indicator-class="indicator-class">
				<picker-view-column>
					<view class="item" v-for="(item,index) in heightArr" :key="index" :class="val==item?'lldl':''">
						{{item}}
					</view>
				</picker-view-column>
			</picker-view>
		</view>
		<view class="to-btn" @click="stepToNext">
			下一步（2/6）
		</view>
	</view>
</template>

<script>
	export default {
		components: {},
		data() {
			return {
				heightArr: ['学生', 'IT互联网', '教育/科研', '建筑/房地产', '金融/保险'],
				bgColor: 'rgba(0, 31, 63, 0)',
				val: "学生",
				indicatorStyle: `height: 41px;color: rgba(51, 51, 51, 0.2);`,
				fsdfds: `opacity: 0.1;background: rgba(238, 245, 254, 0);`,
				obj: {},
				img: this.$BASE_URL,
			};
		},
		onLoad(options) {
			this.obj = JSON.parse(options.obj)
			console.log(this.obj)
		},
		methods: {
			left() {
				uni.navigateBack()
			},
			stepToNext() {
				console.log(this.val);
				this.obj.profession = this.val

				uni.navigateTo({
					url: '/pagesintroduction/selectSchool/selectSchool?obj=' + JSON.stringify(this.obj)
				})
			},
			bindChange(val) {
				this.val = this.heightArr[val.detail.value[0]]
				console.log(val, this.val);
			}
		}
	}
</script>

<style lang="scss" >
	.bg-c {
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0;
		pointer-events: none;
	}page {
		width: 100%;
		height: 100%;
	}
.container{
		position: relative;
		width: 100vw;
		height: 100%;
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
			top: 0;
			left: 0rpx;

			width: 95rpx;
			height: 85rpx;
		}

		.bg2-2 {
			position: absolute;
			top: 66rpx;
			right: -10rpx;
			width: 83rpx;
			height: 60rpx;
		}

		.sign-up-title-item {
			font-size: 48rpx;
			display: flex;margin-top: 66rpx;
			align-items: center;
			justify-content: center;

			.yexy {
				margin-left: -20rpx;font-family: 'SourceHanSerifCN-Heavy';color: rgba(45, 49, 50, 0.9);
			}
		}

		.sign-up-title-tips {
			text-align: center;
			margin-top: 24rpx;
			font-size: 24rpx
		}
	}


	.select-c {
		position: relative;
		padding: 20rpx 0;



		.picker-view {
			height: 450rpx;
			margin: auto;

			color: #434343;
		}

		picker-view-column {
			.item {
				width: 100%;
				text-align: center;
				line-height: 41px;
				height: 41px;
font-weight: 400;
				color: #B8B8B8;
				font-family: 'PingFang SC-Regular, PingFang SC';
			}

			.lldl {
				color: rgba(67, 67, 67, 0.9);font-weight: 400;
				font-family: 'PingFang SC-Bold, PingFang SC';

			}
		}
	}

	/deep/ .indicator-class {
		background: #EEF5FE;
		z-index: -1;
	}

	.to-btn {
		width: 536rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		text-align: center;
		line-height: 84rpx;
		margin: auto;
		font-family: 'PingFang SC-Bold, PingFang SC';
		position: fixed;
		bottom: 15vh;
		left: 112rpx;color: rgba(65, 92, 158, 0.9);
		font-weight: 400;
		font-size: 28rpx;
	}

	.tips {
		margin: auto;
		text-align: center;
		margin-top: 30rpx;
		font-size: 24rpx;
		font-weight: 400;
		color: #B7B7B7;
	}
</style>

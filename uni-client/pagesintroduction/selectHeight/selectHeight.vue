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
				请选择你的身高
			</view>
			<view class="sign-up-title-tips">
				给你推荐年龄适配的Ta
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
			下一步（1/6）
		</view>
	</view>
</template>

<script>
	export default {
		components: {},
		data() {
			return {
				img: this.$BASE_URL,
				heightArr: [],
				bgColor: 'rgba(0, 31, 63, 0)',
				val: "",
				indicatorStyle: `height: 41px;color: rgba(51, 51, 51, 0.2);`,
				fsdfds: `opacity: 0.5;background: rgba(238, 245, 254, 0);`,
				code: ""
			};
		},
		onLoad(options) {
			this.code = options.code
			console.log(this.code)
			this.getHeight()
		},
		onShow() {

		},
		methods: {
			left() {
				uni.navigateBack()
			},
			getHeight() {
				for (let index = 140; index < 200; index++) {
					this.heightArr.push(index)

				}
				console.log(this.heightArr)
				this.val = this.heightArr[0]
			},

			stepToNext() {
				console.log(this.val);
				let obj = {
					height: this.val,
					code: this.code
				}

				uni.navigateTo({
					url: "/pagesintroduction/selectJob/selectJob?obj=" + JSON.stringify(obj)
				})
			},
			handleDateChange(year, month, day) {
				this.year = year;
				this.month = month;
				this.day = day;
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
	}
.container{
		position: relative;
		width: 100vw;
		height: 100%;
	}
	.sign-up-title-c {
		position: relative;
		width: 500rpx;
		height: 200rpx;
		margin: auto;
		margin-top: 52rpx;
		box-sizing: content-box;
		overflow: hidden;
		color: #333;

		.bg1-l {
			position: absolute;
			top: 0rpx;
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
			top: 66rpx;
			font-weight: 800;
			font-family: 'SourceHanSerifCN-Heavy';
			margin-top: 66rpx;
			text-align: center;color: rgba(45, 49, 50, 0.9);
		}

		.sign-up-title-tips {
			text-align: center;
			font-family: 'PingFang SC-Regular, PingFang SC';
			margin-top: 24rpx;color:#666;
			font-size: 24rpx;font-weight: 400;
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
				color: #B8B8B8;
				font-weight: 400;
				font-family: 'PingFang SC-Regular, PingFang SC';
			}

			.lldl {
				font-family: 'PingFang SC-Bold, PingFang SC';
				color: rgba(67, 67, 67, 0.9);font-weight: 400;


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
		color: rgba(65, 92, 158, 0.9);
		font-weight: 400;
		position: fixed;
		bottom: 15vh;
		left: 112rpx;
		font-size: 28rpx;
		font-family: 'PingFang SC-Bold, PingFang SC';
	}

	.tips {
		margin: auto;
		text-align: center;
		margin-top: 30rpx;
		font-size: 24rpx;
		font-weight: 400;
		color: #B7B7B7;
	}
	page {
		width: 100%;
		height: 100%;
	}
	
</style>

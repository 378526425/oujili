<template>
	<view class="" @touchmove.stop.prevent="() => {}">
		
		<view class="" style="height: 56rpx;"></view>

		<view class="" v-if="falg">
			<view class="boot">
				<view class="xuan">
					<view class="div" v-for="(item, index) in list" :key="index" @click="clicks(index)">
						<view class="name">{{ item.regionName2 }}</view>
						<view class="" v-if="index == 0"><u-icon name="arrow-down" color="#434343" size="16"></u-icon></view>
						<view class="" v-else><u-icon name="close" color="#434343" size="16"></u-icon></view>
						<image src="../../static/select.png" v-if="index == 0" class="img" mode="aspectFill"></image>
					</view>

					<view class="div" @click="add"><view class="name" style="margin-left: 0rpx;">添加</view></view>
				</view>
			</view>
			<view class="tabs">
				<view class="ewrew">
					年龄:
					<view>{{ minValue }} - {{ maxValue }}</view>
				</view>

				<s-region-slider
					:minValue="minValue"
					:maxValue="maxValue"
					:fillMinValue="16"
					:fillValue="60"
					:step="1"
					@up="up"
					@down="down"
					@move="move"
					:parentCode="parentCode"
					:regionCode="regionCode"
					:regionName="regionName"
				/>
			</view>
		</view>
		<view class="adding" @click="addcity">确定</view>

		<level-linkage ref="levelLinkage" :pickerValueDefault="pickerValueDefault" :allData="treeData" @take="take" themeColor="#007AFF"></level-linkage>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>
<script>
import levelLinkage from '../components/three-level-linkage/linkage.vue';
import sRegionSlider from '../components/s-region-slider/s-region-slider.vue';
import ELM from '@/components/elm-toast/index.vue';
export default {
	components: {
		sRegionSlider,
		levelLinkage,
		ELM
	},
	data() {
		return {
			falg: false,
			minValue: 1,
			maxValue: 100,
			pickerValueDefault: [0, 0],
			pickerText: '',
			list: [
				// 	{
				// 	regionName2: "重庆"
				// }, {
				// 	regionName2: "成都"
				// },
			],
			parentCode: '',
			regionCode: '',
			regionName2: '',
			tipMsg: '',
			isConfirm: false
		};
	},
	onShow() {
		this.configInfo();
	},
	onLoad() {},
	methods: {
		async configInfo() {
			const res = await this.$myRequest({
				url: 'nostalgia/fruser/user/configInfo',
				withToken: true,
				method: 'GET'
			});
			console.log(res, 'delshoucang');
			if (res.data.code == 200) {
				this.maxValue = res.data.data.maxAge;
				this.minValue = res.data.data.minAge;
				this.list = [];
				if (res.data.data.city) {
					res.data.data.city.forEach((item, index) => {
						var obj = {
							regionName2: item
						};
						this.list.push(obj);
					});
				}
			} else {
				this.tipMsg = res.data.msg;
				this.$refs.elm.showDialog();
			}
			this.falg = true;
		},

		async addcity() {
			uni.showLoading({
				title: '设置中...'
			});
			let citydata = [];
			this.list.forEach((item, index) => {
				citydata.push(item.regionName2);
			});
			console.log(citydata);
			const res = await this.$myRequest({
				url: 'nostalgia/fruser/update/configInfo',
				data: {
					city: citydata,
					maxAge: this.maxValue,
					minAge: this.minValue
				},
				withToken: true,
				method: 'PUT'
			});
			console.log(res, 'delshoucang');
			uni.hideLoading();
			if (res.data.code == 200) {
				this.isConfirm = true;
				this.tipMsg = "设置成功";
				this.$refs.elm.showDialog();
			} else {
				this.tipMsg = res.data.msg;
				this.$refs.elm.showDialog();
			}
		},
		clicks(e) {
			if (e == 0) {
				this.$refs.levelLinkage.open(1);
			} else {
				this.list.splice(e, 1);
			}
		},

		take(e) {
			console.log(e, '[[[[]]]]');
			if (e.num == 1) {
				this.list.forEach((item, index) => {
					if (item.regionName2 == e.regionName2) {
						this.list.splice(index, 1);
					}
				});
				this.list.splice(e.index, 1);
				this.list.unshift(e);
			} else {
				const index = this.list.findIndex(function(val) {
					return val.regionName2 === e.regionName2;
				});
				// 如果有多个满足，返回第一个的index
				console.log(index, '[[[[[[[[[[[]]]]]]]]]]]');
				if (index == 0) {
					this.tipMsg = "您已添加过该城市";
					this.$refs.elm.showDialog();
				} else {
					this.list.push(e);
				}
			}
		},
		chage(minValue, maxValue) {
			this.minValue = minValue;
			this.maxValue = maxValue;
		},
		add() {
			this.$refs.levelLinkage.open(2);
		},

		up(e) {
			console.log(e); //// e中包含了原有的e信息, 并添加了custom对象
			this.minValue = e.custom.minValue;
			this.maxValue = e.custom.maxValue;
		},
		left() {
			uni.navigateBack();
		},
		down(e) {},
		move(e) {},
		confirm() {
			this.isConfirm = false;
			uni.navigateBack();
		}
	}
};
</script>

<style lang="scss">
.adding {
	width: 614rpx;
	height: 84rpx;
	background: linear-gradient(86deg, #c2d2f9 0%, #c5c2f3 100%);
	border-radius: 42rpx 42rpx 42rpx 42rpx;
	font-size: 28rpx;
	font-family: 'PingFang SC-Regular, PingFang SC';
	font-weight: 400;
	color: #415c9e;
	line-height: 84rpx;
	position: fixed;
	bottom: 54rpx;
	left: 68rpx;
	text-align: center;
}

.xuan {
	width: 100%;
	display: flex;
	align-items: center;
	flex-wrap: wrap;

	.div {
		// width: 170rpx;
		height: 72rpx;
		background: rgba(81, 81, 81, 0.08);
		border-radius: 16rpx;
		margin-bottom: 40rpx;
		margin-right: 20rpx;
		position: relative;
		display: flex;
		align-items: center;
		justify-content: center;
		padding: 0 32rpx;

		.name {
			font-size: 28rpx;
			font-family: 'PingFang SC-Medium, PingFang SC';
			font-weight: 500;
			color: #434343;
			line-height: 28rpx;
			margin-right: 10rpx;
		}

		.img {
			width: 60rpx;
			height: 60rpx;
			position: absolute;
			right: 0rpx;
			top: 0rpx;
		}
	}
}

.boot {
	width: 686rpx;
	border-bottom: 2rpx solid #ececec;
	margin: 0 auto;
}

.u-nav-slot {
	display: flex;
	align-items: center;

	.text {
		height: 44rpx;
		font-size: 32rpx;
		font-family: 'PingFang SC-Bold, PingFang SC';
		font-weight: bold;
		color: #000000;
		margin-left: 14rpx;
		line-height: 44rpx;
	}

	.img {
		width: 22rpx;
		height: 28rpx;
		margin-left: 12rpx;
	}
}

page {
	width: 100%;
	height: 100%;
}

.ewrew {
	display: flex;

	height: 50rpx;
	font-size: 36rpx;
	font-family: 'PingFang SC-Regular, PingFang SC';
	font-weight: 400;
	color: #333333;
	line-height: 50rpx;
	align-items: center;
	margin-top: 50rpx;
	margin-bottom: 42rpx;

	view {
		height: 50rpx;
		font-size: 32rpx;
		font-family: 'PingFang SC-Bold, PingFang SC';
		font-weight: bold;
		color: #446cd2;
		line-height: 50rpx;
		margin-left: 24rpx;
	}
}

.tabs {
	// position: fixed;
	width: 686rpx;
	margin: 0 auto;
	height: 300px;
}

.btn {
	margin: 10px 0;
	background-color: #f0ad4e;
	width: 200px;
}
</style>

<template>
	<view class="container" @touchmove.stop.prevent="() => {}">
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
				请选择你的生日
			</view>
			<view class="sign-up-title-tips">
				给你推荐年龄适配的Ta
			</view>
		</view>
		<view class="date-c">

			<my-select @valChange="handleDateChange" @curTime="curTime" :default-time="`${1997}-${12}-${1}`" mode="time"
				class="my-select">
			</my-select>
		</view>
		<view class="bottom">
			<view class="to-btn" @click="getLocation">
				下一步（2/2）
			</view>
			<view class="tips">
				注册后生日不可修改
			</view>
		</view>
		<ELM ref="elm" :msg="tipMsg"></ELM>
	</view>
</template>

<script>
	var amapFile = require('@/util/amap-wx.130.js');
	import {
		AMAPKEY
	} from '../../util/api.js';
	var amapPlugin = new amapFile.AMapWX({
		key: AMAPKEY
	});
	let util = require('@/util/means.js');
	import MySelect from '@/components/dateXZ.vue'
	import ELM from '../../components/elm-toast/index.vue';
	export default {

		components: {
			MySelect,
			ELM
		},
		data() {
			return {
				selectSex: 'man',
				bgColor: 'rgba(0, 31, 63, 0)',
				isShow: false,
				value1: Number(new Date()),
				year: 1997,
				month: 12,
				day: 1,
				img: this.$BASE_URL,
				obj: {

				},
				tipMsg: '',
				locationerr: false
			};
		},
		onLoad(options) {
			this.obj = JSON.parse(options.obj)
			console.log(this.obj)
			this.getCurrentTime()
		},
		methods: {
			left() {
				uni.navigateBack()
			},

			getCurrentTime() {
				var date = new Date(); //当前时间
				var year = date.getFullYear() //返回指定日期的年份
				var month = this.repair(date.getMonth() + 1); //月
				var day = this.repair(date.getDate()); //日
				//当前时间 
				var curTime = year + "-" + month + "-" + day + " " + '00' + ":" + '00' + ":" + '00';
				console.log(curTime)
				this.obj.birthday = curTime
			},
			curTime(e) {
				console.log(e)
				this.obj.birthday = e
			},
			//补0
			repair(i) {
				if (i >= 0 && i <= 9) {
					return "0" + i;
				} else {
					return i;
				}
			},

			getLocation() {
				console.log("获取位置信息")
				if (this.obj.birthday) {
					let age = util.mymethod(this.obj.birthday);
					if (age < 16) {
						this.tipMsg = "年龄小于16周岁";
						this.$refs.elm.showDialog();
						return;
					}
				}

				let that = this

				// 有权限则直接获取
				console.log("开始获取位置信息")
				amapPlugin.getRegeo({
					success: (data) => {
						console.log("高德地图获取位置信息成功")
						console.log(data)
						console.log('当前位置的经度：' + data[0].longitude);
						console.log('当前位置的纬度：' + data[0].latitude);
						let addresslocation = "";
						if (data[0].regeocodeData.addressComponent.city.length == 0) {
							addresslocation = data[0].regeocodeData.addressComponent.province;
						} else {
							addresslocation = data[0].regeocodeData.addressComponent.city;
						}
						console.log("获取用户位置信息：" + addresslocation)
						//修改用户位置       
						that.obj.city = addresslocation;
						that.obj.lon = data[0].longitude;
						that.obj.lat = data[0].latitude;
						console.log(that.obj)

						uni.setStorageSync('itemobj', that.obj);
						setTimeout(() => {
							uni.navigateTo({
								url: "/pageslogin/index/bootpage?obj=" + JSON
									.stringify(that.obj)
							})
						}, 1000)
					},
					fail: (err) => {
						if (this.locationerr == true) {
							that.obj.city = "重庆市";
							that.obj.lon = "106.55";
							that.obj.lat = "29.56";
							console.log(that.obj)

							uni.setStorageSync('itemobj', that.obj);
							setTimeout(() => {
								uni.navigateTo({
									url: "/pageslogin/index/bootpage?obj=" + JSON
										.stringify(that.obj)
								})
							}, 1000)
						} else {
							console.log('获取失败引导用户授权', err)
							this.locationerr = true;
							// #ifdef MP-WEIXIN
							uni.authorize({
								scope: 'scope.userLocation',
								success() {
									console.log("用户授权成功")
									//点击允许后--就一直会进入成功授权的回调 就可以使用获取的方法了
									amapPlugin.getRegeo({
										success: function(data) {
											console.log("用户授权成功后高德地图获取位置成功")
											console.log(data)
											console.log('当前位置的经度：' + data[0].longitude);
											console.log('当前位置的纬度：' + data[0].latitude);
											let addresslocation = "";
											if (data[0].regeocodeData.addressComponent.city
												.length == 0) {
												addresslocation = data[0].regeocodeData
													.addressComponent.province;
											} else {
												addresslocation = data[0].regeocodeData
													.addressComponent.city;
											}
											console.log("获取用户位置信息：" + addresslocation)
											//修改用户位置       
											that.obj.city = addresslocation;
											that.obj.lon = data[0].longitude;
											that.obj.lat = data[0].latitude;
											console.log(that.obj)

											uni.setStorageSync('itemobj', that.obj);
											setTimeout(() => {
												uni.navigateTo({
													url: "/pageslogin/index/bootpage?obj=" +
														JSON
														.stringify(that
															.obj)
												})
											}, 1000)
										},
										fail: (errorl) => {
											console.log('用户授权成功后高德地图获取位置失败', errorl)
											this.tipMsg = "手机定位未开启，请开启定位后继续使用！";
											this.$refs.elm.showDialog();
										}
									})
								},
								fail(error) {
									//点击了拒绝授权后--就一直会进入失败回调函数--此时就可以在这里重新拉起授权窗口
									console.log('拒绝授权', error)

									uni.showModal({
										title: '提示',
										content: '若点击不授权，将无法使用位置功能',
										cancelText: '不授权',
										cancelColor: '#999',
										confirmText: '授权',
										confirmColor: '#f94218',
										success(res) {
											console.log(res)
											if (res.confirm) {
												// 选择弹框内授权
												uni.openSetting({
													success(res) {
														console.log(res.authSetting)
													}
												})
											} else if (res.cancel) {
												// 选择弹框内 不授权
												console.log('用户点击不授权')
											}
										}
									})
								}
							})
							// #endif
							// #ifndef MP-WEIXIN
							//引导用户
							uni.getLocation();
							// #endif
						}
					}
				});

				/* 	//修改用户位置
					that.obj.city = "重庆市";
					that.obj.lon = "106.498771";
					that.obj.lat = "29.614222";
					console.log(that.obj)
									   
					uni.setStorageSync('itemobj', that.obj);
					setTimeout(() => {
						uni.navigateTo({
							url: "/pageslogin/index/bootpage?obj=" + JSON
								.stringify(that.obj)
						})
					}, 1000) */


			},

			changeSex(val) {
				this.selectSex = val
			},

			handleDateChange(year, month, day) {
				this.year = year;
				this.month = month;
				this.day = day;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.bg-c {
		width: 100%;
		height: 80vh;
		position: absolute;
		top: 0;
		z-index: -1;
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
			font-family: 'SourceHanSerifCN-Heavy';
			text-align: center;
			margin-top: 66rpx;
			color: rgba(45, 49, 50, 0.9);
		}

		.sign-up-title-tips {
			text-align: center;
			font-weight: 400;
			margin-top: 24rpx;
			color: #666;
			font-family: 'PingFang SC-Regular, PingFang SC';
			font-size: 24rpx
		}
	}


	.date-c {
		margin-top: 160rpx;

		.my-select {
			margin: auto;
			margin-top: 20rpx;
		}

		.select-date {
			width: 150rpx;
			height: 60rpx;
			line-height: 60rpx;
			text-align: center;
			border-radius: 30rpx;
			color: #415C9E;
			font-size: 24rpx;
			background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
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
		font-family: 'PingFang SC-Bold, PingFang SC';
		line-height: 84rpx;
		margin: auto;
		font-weight: 400;
		color: rgba(65, 92, 158, 0.89);
		font-size: 28rpx;
	}

	.tips {
		margin: auto;
		text-align: center;
		margin-top: 30rpx;
		font-size: 24rpx;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		color: #B7B7B7;
	}
</style>
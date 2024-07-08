<template>
	<view>
		<view class="title-txt">上传身份证照片</view>
		<view class="con-txt">根据监管要求身份证照片仅用于实名认证</view>
		<image class="zheng-image" :src="zhengimageUrl" @click="chooseImg('z')" mode="aspectFill"></image>
		<image class="fan-image" :src="fanimageUrl" @click="chooseImg('f')" mode="aspectFill"></image>
		<view class="to-btn" @click="authadd">
			提交
		</view>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		data() {
			return {
				zhengimageUrl: "../../static/images/me/zheng.png",
				fanimageUrl: "../../static/images/me/fan.png",
				updatezImg: "",
				updatefImg: "",
				newImageSrc: "",
				type: 0,
				tipMsg: '',
				isConfirm: false
			}
		},
		components: {
			ELM
		},
		onLoad() {
			var that = this;
			that.loadPage();
		},
		methods: {
			async loadPage() {
				var that = this;
				const res = await that.$myRequest({
					url: '/nostalgia/fruserauth/info',
					withToken: true,
					method: 'GET',
				});
				console.log(res);
				if (res.data.code == 200) {
					if (res.data.data.identityImgList != null) {
						if (res.data.data.identityImgList.length > 0) {
							that.zhengimageUrl = res.data.data.identityImgList[0]
						}
						if (res.data.data.identityImgList.length > 1) {
							that.fanimageUrl = res.data.data.identityImgList[1]
						}

					}

				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			chooseImg(type) {
				console.log(type);

				uni.chooseImage({
					count: 1,
					success: (res) => {
						uni.showLoading({
							title: "上传中"
						})
						res.tempFilePaths.forEach((i) => {

							uni.uploadFile({
								url: this.$BASE_URL + '/file/upload',
								filePath: i,
								name: 'file',
								formData: {
									'file': 'file'
								},
								success: (res) => {
									uni.hideLoading();
									let dataing = JSON.parse(res.data)
									console.log(dataing)
									this.newImageSrc = dataing.data.url
									console.log(this.newImageSrc)
									this.updateImage(type);
								},
								fail: (loginErr) => {
									this.tipMsg = "上传失败，请重试";
									this.$refs.elm.showDialog();						
								}
							});
						});
					}
				});
			},
			updateImage(type) {
				var that = this;
				if (type == 'z') {
					that.updatezImg = that.newImageSrc;
					that.zhengimageUrl = that.newImageSrc;
				} else if (type == 'f') {
					that.updatefImg = that.newImageSrc;
					that.fanimageUrl = that.newImageSrc;
				}
			},
			async authadd() {
				var that = this;
				var imgList = [];
				if (!that.updatezImg) {
					this.tipMsg = "请上传正面照片";
					this.$refs.elm.showDialog();
					return false;
				} else {
					imgList.push(that.updatezImg)
				}
				if (!that.updatefImg) {
					this.tipMsg = "请上传反面照片";
					this.$refs.elm.showDialog();
					return false;
				} else {
					imgList.push(that.updatefImg)
				}
				var data = {
					"authType": "IdentityAuth",
					"imgList": imgList
				}
				uni.showLoading({
					title: "提交中"
				})
				const res = await that.$myRequest({
					url: '/nostalgia/fruserauth/add',
					withToken: true,
					data: data,
					method: 'POST',
				});
				uni.hideLoading();
				if (res.data.code == 200) {
					this.isConfirm=true;
					this.tipMsg = "提交成功";
					this.$refs.elm.showDialog();
				
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			confirm() {
				this.isConfirm=false;
				uni.navigateBack();
			}
		}
	}
</script>

<style lang="scss">
	page {

		background-color: #ffffff;
	}

	.title-txt {
		width: 100%;
		height: 44rpx;
		box-sizing: border-box;
		padding: 0 62rpx;
		margin: 70rpx 0 18rpx 0;
		line-height: 44rpx;
		font-size: 32rpx;
		// font-family: 'PingFang SC-Bold, PingFang SC';
		font-weight: 600;
	}

	.con-txt {
		width: 100%;
		height: 40rpx;
		box-sizing: border-box;
		padding: 0 62rpx;
		line-height: 40rpx;
		color: #B7B7B7;
		font-size: 28rpx;
		// font-family: 'PingFang SC-Bold, PingFang SC';
		// font-weight: 400;
	}

	.zheng-image {
		width: 686rpx;
		height: 442rpx;
		margin: 42rpx 32rpx;
		border-radius: 24rpx;
	}

	.fan-image {
		width: 686rpx;
		height: 442rpx;
		margin: 0 32rpx;
		border-radius: 24rpx;
	}

	.to-btn {
		width: 686rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		text-align: center;
		line-height: 84rpx;
		margin: auto;
		margin-top: 150rpx;
		margin-bottom: 50rpx;
		bottom: 6vh;
		left: 32rpx;
		color: rgba(65, 92, 158, 0.9);
		font-weight: 400;
		font-size: 28rpx;
	}
</style>
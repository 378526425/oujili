<template>
	<view class="content">
		<image class="show-image" @click="showSingleNoPopup(index)" v-for="(item,index) in showList" :key="index"
			:src="item" mode="aspectFill"></image>
		<view class=" tc br24 blc upload-img" style="color: #D5D5D5; background-color: #EBEBEB;"
			@click="chooseImg('add')">
			+
		</view>
		<view class="to-btn" @click="authadd">
			确定
		</view>
		<!-- 单列不滚动 -->
		<u-popup :show="singleNoShow" @close="singleNoClose" mode="bottom">
			<view class="singleNo" style="border-radius: 24rpx 24rpx 0px 0px;">
				<view class="singleNo-item" @click="singleNoItem(1)">替换</view>
				<view class="singleNo-item" @click="singleNoItem(2)">删除</view>
				<view class="linkage-close" @click="singleNoClose">
					取消
				</view>
			</view>
		</u-popup>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		data() {
			return {
				newImageSrc: "",
				showList: [],
				type: 0,
				singleIndex: 0,
				singleNoShow: false,
				tipMsg: '',
				isConfirm: false
			}
		},
		components: {
			ELM
		},
		onLoad(option) {
			var that = this;
			that.loadPage();
		},
		methods: {
			singleNoClose() {
				this.singleNoShow = false;
				// userInfo.waitApprovedImg
			},
			showSingleNoPopup(Index) {

				this.singleIndex = Index;
				console.log(Index);
				this.singleNoShow = true;
			},
			singleNoItem(type) {

				if (type == 1) {
					this.chooseImg("edit")
				} else if (type == 2) {
					this.updateImage("delete")
				}
			},
			async loadPage() {
				var that = this;
				const res = await that.$myRequest({
					url: '/nostalgia/fruserauth/info',
					withToken: true,
					method: 'GET',
				});
				console.log(res);
				if (res.data.code == 200) {
					if (res.data.data.educationImgList) {
						that.showList = res.data.data.educationImgList
					}
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			chooseImg(type) {
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
									uni.hideLoading();
									this.tipMsg = "上传失败，请重试";
									this.$refs.elm.showDialog();
								}
							});
						});
					}
				});
			},
			updateImage(type) {
				debugger
				var that = this;
				console.log(that.showList);
				console.log(type);
				if (type == 'add') {
					that.showList.push(that.newImageSrc);
				} else if (type == 'edit') {
					that.showList.splice(that.singleIndex, 1, that.newImageSrc)
					that.singleNoShow = false;
				} else if (type == 'delete') {
					that.showList.splice(that.singleIndex, 1)
					that.singleNoShow = false;
				}

				console.log(that.showList);
			},
			async authadd() {
				var that = this;
				if (that.showList.length == 0) {
					this.tipMsg = "请认证照片";
					this.$refs.elm.showDialog();
					return false;
				}
				var data = {
					"authType": "EducationAuth",
					"imgList": that.showList
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
					this.isConfirm = true;
					this.tipMsg = "提交成功";
					this.$refs.elm.showDialog();

				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			confirm() {
				this.isConfirm = false;
				uni.navigateBack();
			}
		}
	}
</script>

<style lang="scss">
	page {

		background-color: #ffffff;
	}

	.br24 {
		border-radius: 24rpx;
	}

	.blc {
		background: #eee;
	}

	.tc {
		text-align: center;
	}

	.content {
		width: 100%;
		box-sizing: border-box;
		padding: 0 32rpx;
	}

	.show-image {
		width: 324rpx;
		height: 324rpx;
		margin-right: 38rpx;
		margin-bottom: 25rpx;
		margin-top: 25rpx;
		float: left;
		border-radius: 24rpx;
	}

	.show-image:nth-child(2n) {
		margin-right: 0rpx;
	}

	.upload-img {
		width: 324rpx;
		height: 324rpx;
		margin-top: 25rpx;
		margin-bottom: 25rpx;
		float: left;
		font-size: 200rpx;
	}

	.to-btn {
		width: 686rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		text-align: center;
		line-height: 84rpx;
		margin: auto;
		font-family: 'PingFang SC-Bold, PingFang SC';
		position: fixed;
		bottom: 6vh;
		left: 32rpx;
		color: rgba(65, 92, 158, 0.9);
		font-weight: 400;
		font-size: 28rpx;
	}

	// 单列不滚动
	.singleNo {
		width: 100%;
		height: auto;
		background-color: #F7F7F7;

		.singleNo-item {
			width: 100%;
			height: 108rpx;
			background-color: #fff;
			line-height: 108rpx;
			text-align: center;
			font-weight: 400;
			color: #000000;
			font-size: 32rpx;
			border-bottom: 2rpx solid #f7f7f7;
			// -webkit-background-clip: text;
		}

		.linkage-close {
			width: 750rpx;
			height: 108rpx;
			text-align: center;
			line-height: 108rpx;
			font-size: 32rpx;
			font-weight: 400;
			background-color: #fff;
			margin-top: 16rpx;
		}
	}
</style>
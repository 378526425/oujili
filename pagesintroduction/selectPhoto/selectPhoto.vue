<template>
	<view class="container"  @touchmove.stop.prevent="() => {}">
	<u-navbar leftText=" " title=" " :bgColor="bgColor":safeAreaInsetTop="true" :placeholder="true">
		<view class="u-nav-slot" slot="left" @click="left">
			<u-icon name="arrow-left" color="#000000" size="16" :bold="true"></u-icon>
		</view>
		
	</u-navbar>
		<image class="bg-c" src="@/static/images/bg-c.png"></image>
		<view class="sign-up-title-c">

			<image class="bg1-l" src="../../static/images/left.png" mode="widthFix"></image>
			<image class="bg2-2" src="../../static/images/right.png"  mode="widthFix"></image>
			<view class="sign-up-title-item">
				上传头像/封面 
			</view>
			<view class="sign-up-title-tips">
				请上传个人真实照片
			</view>
		</view>
		<view class="" style="height: 36rpx;">

		</view>
		<view class="select-c">
			<view class="phone-btn">
				<view class="choose-photo" @click="chooseImg">
					<image src="../static/images/add-photo.png"  mode="widthFix" class="image" v-if="falg"></image>
					<image :src="src" v-else class="images" mode="aspectFill"></image>
				</view>
			</view>
		</view>
		<view class="to-btn" @click="stepToNext">
			下一步（5/6）
		</view>
		<ELM ref="elm" :msg="tipMsg"></ELM>
	</view>
</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		components: {
			ELM
		},
		data() {
			return {
				bgColor: 'rgba(0, 31, 63, 0)',
				obj: {},
				src: "",
				falg: true,
				img: this.$BASE_URL,
				tipMsg: ''
			};
		},
		onLoad(options) {
			this.obj = JSON.parse(options.obj)
			console.log(this.obj)
		},
		methods: {
			stepToNext() {
				if(this.src == ''){
					this.tipMsg = "请上传头像";
					this.$refs.elm.showDialog();
					return
				}
				console.log(this.val);
				this.obj.headPortrait = this.src

				uni.navigateTo({
					url: "/pagesintroduction/aboutme/aboutme?obj=" + JSON.stringify(this.obj)
				})
			},
			bindChange(val) {
				console.log(val);
			},left(){
				uni.navigateBack() 
			},
			chooseImg() {
				uni.setStorageSync('falg', 1);
				uni.chooseImage({
					count: 1,
					success: (res) => {
						res.tempFilePaths.forEach((i) => {
							uni.uploadFile({
								url: this.$BASE_URL + '/file/upload',
								filePath: i,
								name: 'file',
								formData: {
									'file': 'file'
								},
								success: (res) => {

									let dataing = JSON.parse(res.data)
									console.log(dataing)
									this.falg = false
									this.src = dataing.data.url
									console.log(this.src)

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
			top: 0rpx;
			left: 0;

			width: 95rpx;
			z-index: -1;
		}

		.bg2-2 {
			position: absolute;
			top: 66rpx;
			right: -10rpx;
			width: 83rpx;
			z-index: -1;
		}

		.sign-up-title-item {
			font-size: 48rpx;
			display: flex;
			align-items: center;
			margin-top: 66rpx;
			justify-content: center;font-family: 'SourceHanSerifCN-Heavy';

			.yexy {
				margin-left: -20rpx;color: rgba(45, 49, 50, 0.9);
			}
		}

		.sign-up-title-tips {
			text-align: center;
			margin-top: 30rpx;font-family: 'PingFang SC-Regular, PingFang SC';
			font-size: 24rpx;font-weight: 400;color: #666;
		}
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
		bottom: 15vh;color: rgba(65, 92, 158, 0.9);
		font-weight: 400;
		font-size: 28rpx;left: 112rpx;
	}

	.tips {
		margin: auto;
		text-align: center;
		margin-top: 30rpx;
		font-size: 24rpx;
		font-weight: 400;
		color: #B7B7B7;
	}

	.phone-btn {
		width: 560rpx;
		height: 560rpx;
		margin: auto;

		image {
			width: 560rpx;
		}
	}

	.select-c {

		height: 600rpx;
	}

	.choose-photo {
		width: 560rpx;
		height: 560rpx;
		margin: auto;
		background: #FFFFFF;
		border-radius: 32px;
		opacity: 1;
		border: 6px solid #9FB5EB;
		display: flex;
		overflow: hidden;

		.image {
			width: 92rpx;
			margin: auto;
		}

		.images {
			width: 560rpx;
			height: 560rpx;
		}
	}
</style>

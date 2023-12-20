<template>
	<view class="dialog_box" v-if="show">
		<view class="bg"></view>
		<view class="dialog_content">
			<view class="bag">
				<image class="pop-bgc" :src="img + '/img/home-pop-bg.png'"></image>
				<view class="divss">
					<view class="namne">资料审核已提交</view>
					<view class="namnes">为了保证交友环境安全，我们 需要对你的资料进行审核</view>
					<view class="button" @click="gzh">审核通过后通知我</view>
				</view>
			</view>
			<view class="dialog_foot" @click="hideDialog()"><image src="/static/images/closeing.png" mode="aspectFill" style="width: 64rpx;height: 64rpx;;"></image></view>
		</view>
	</view>
</template>

<script>
export default {
	props: ['dataInfo'],
	data() {
		return {
			img: this.$BASE_URL,
			show: false,
			info: this.dataInfo
		};
	},
	onLoad() {},
	methods: {
		showDialog() {
			this.show = true;
		},
		hideDialog() {
			this.show = false;
			uni.switchTab({
				url: '/pages/tab/index'
			});
		},
		gzh() {
			this.show = false;
			uni.navigateTo({
				url: '/pages2/explain/explain'
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.dialog_box {
	position: fixed;
	left: 0;
	top: 0;
	right: 0;
	bottom: 0;
	z-index: 99;

	.bg {
		position: fixed;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		z-index: 1;
		background-color: rgba(0, 0, 0, 0.5);
	}

	.dialog_content {
		position: fixed;
		left: 0;
		top: 0;
		width: 100%;
		z-index: 10;
		text-align: center;

		.title {
			font-size: 50rpx;
			color: #fff;
			margin: 40rpx auto;
		}

		.bag {
			position: relative;
			width: 500rpx;
			height: 562rpx;
			background: #ffffff;
			border-radius: 24rpx 24rpx 24rpx 24rpx;
			opacity: 1;
			overflow: hidden;
			margin-left: 125rpx;
			margin-top: calc(100% - 333rpx);
			.divss {
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;
				.namne {
					width: 100%;
					height: 56rpx;
					font-size: 40rpx;
					font-family: 'PingFang SC-Heavy, PingFang SC';
					font-weight: 400;
					color: #4a4a4a;
					line-height: 56rpx;
					margin-top: 156rpx;
					text-align: center;
				}
				.button {
					width: 392rpx;
					height: 84rpx;
					background: linear-gradient(86deg, #c2d2f9 0%, #c5c2f3 100%);
					border-radius: 42rpx;

					font-size: 28rpx;
					font-family: 'PingFang SC-Regular, PingFang SC';
					font-weight: 400;
					color: #415c9e;
					line-height: 84rpx;
					text-align: center;
					margin: 0 auto;
					margin-top: 64rpx;
				}
				.namnes {
					width: 364rpx;
					height: 96rpx;
					font-size: 28rpx;
					font-family: 'PingFang SC-Regular, PingFang SC';
					font-weight: 400;
					color: #4a4a4a;
					line-height: 48rpx;
					margin: 0 auto;
					margin-top: 40rpx;
					text-align: center;
				}
			}
			.pop-bgc {
				position: absolute;
				top: 0;
				left: 0;
				width: 100%;
				height: 100%;
				// z-index: -1;
			}

			.bag_body {
				width: 100%;
				height: 500rpx;
				margin: auto;
				background-color: #ff2c21;
				border-bottom-left-radius: 50rpx;
				border-bottom-right-radius: 50rpx;
				margin-top: -200rpx;

				.ul {
					margin: -100rpx 20rpx 20rpx;
					border-radius: 32rpx;
					background-color: #ffffff;
					width: calc(100% - 40rpx);
					height: calc(100% - 20rpx);

					.li {
						position: relative;
						padding: 20rpx;
						font-size: 0;
						border-bottom: 1px solid #eee;

						.lred,
						.rred {
							position: absolute;
							bottom: -20rpx;
							width: 40rpx;
							height: 40rpx;
							border-radius: 50%;
							background-color: #ff2c21;
						}

						.lred {
							left: -20rpx;
						}

						.rred {
							right: -20rpx;
						}

						&:last-child {
							.lred,
							.rred {
								display: none;
							}
						}

						.img,
						.txt,
						.money {
							vertical-align: middle;
							display: inline-block;
						}

						.img {
							width: 80rpx;
						}

						.txt {
							text-align: left;
							margin-left: 20rpx;
							width: calc(80% - 100rpx);

							.name {
								font-size: 40rpx;
								color: #000;
								overflow: hidden;
								text-overflow: ellipsis;
								white-space: nowrap;
							}

							.tip {
								font-size: 20rpx;
								color: #bbbbbb;
							}
						}

						.money {
							font-size: 40rpx;
							color: #ca6143;
							font-weight: 700;
							width: 20%;

							label {
								font-size: 20rpx;
							}
						}
					}
				}
			}

			.bag_foot {
				position: absolute;
				left: 0;
				right: 0;
				bottom: 0;
				height: 100rpx;

				&::before {
					content: '';
					position: absolute;
					left: -10%;
					top: -100rpx;
					width: 120%;
					height: 100rpx;
					border-radius: 50%;
					box-shadow: 0 50rpx 0 0 #f4c35d;
				}

				&::after {
					content: '';
					position: absolute;
					left: 0;
					bottom: 0;
					width: 100%;
					height: 100%;
					background-image: linear-gradient(#f4c35d, #fb3e2a);
				}

				.txt {
					position: absolute;
					left: 0;
					top: 0;
					z-index: 10;
					width: 100%;
					height: 100rpx;
					line-height: 100rpx;
					font-size: 30rpx;
					color: #fff;
					text-align: center;
				}
			}
		}
	}

	.dialog_foot {
		margin-top: 40rpx;
		text-align: center;
	}
}

.sasas {
	width: 530rpx;
	height: 260rpx;
	background: #ffffff;
	border-radius: 20rpx;
}

.sasasa {
	width: 460rpx;
	margin: 0 auto 0rpx;
	padding-top: 100rpx;
	height: 120rpx;
	font-size: 28rpx;
	font-family: PingFangSC-Medium, PingFang SC;
	font-weight: 500;
	color: #666666;
	line-height: 40rpx;
}
</style>

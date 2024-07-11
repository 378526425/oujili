<template>
	<view :class="{item:true,bor:!isState}">
		<view class="left">
			<view class="l" v-if="isState">
				<view class="box">
					<view class="day">
						{{info.createTime.substring(8,10)*1}}
					</view>
					<view class="str">
						/
					</view>
					<view class="month">
						{{info.createTime.substring(5,7)*1}}月
					</view>
				</view>
				<view class="line">

				</view>
			</view>
			<image v-else @click="goPage(info)" :src="info.headPortrait" mode="aspectFill"></image>
		</view>
		<view class="center">
			<template v-if="!isState">
				<view class="name">
					<text @click="goPage(info)">{{info.nickName}}</text>
					<image v-if="info.gender!='MALE'" class="gender" src="../../static/images/like/famale.png" mode="">
					</image>
					<image v-else class="gender" src="../../static/images/like/male.png" mode=""></image>
				</view>
				<view class="info">
					<text decode="true" style="color: #434343;">{{info.information}}</text>
				</view>
			</template>
			<view v-else style="height: 100rpx;">

			</view>
			<view class="content">
				<template v-if="info.content">
					<u-read-more ref="uReadMore" :shadowStyle="shadowStyle" :toggle="true" color="#415C9E"
						:textIndent="0" showHeight="126rpx">
						<view @click="goDetail" style="font-size: 28rpx;color: #333333;">
							<!-- <u-parse :tagStyle="tagStyle" :content="info.content" @load="load"></u-parse> -->
							<!-- <rich-text :nodes="info.content"></rich-text> -->
							<view class="" style="line-height: 1.5;">
								{{info.content}}
							</view>
						</view>
					</u-read-more>
				</template>

				<!-- <xzj-readMore hideLineNum="4" :showHeight="40">
					{{info.content}}
				</xzj-readMore> -->
			</view>
			<view class="contentImg">
				<imageAdaptation :imgList="imgs" :imgLayout1="imgLayout1" :imgLayout2="imgLayout2"
					:imgLayout3="imgLayout3" @previewImg="preview"></imageAdaptation>

				<q-previewImage ref="previewImage" :urls="imgs" @onLongpress="onLongpress" @open="open"
					@close="close"></q-previewImage>
			</view>
			<view class="time">
				{{info.time}}
			</view>
			<view class="btns">
				<view class="btn">
					<u-button open-type="share" @click="select" :customStyle="{background: none,
  color: inherit,
  border: 0,
  padding: 0,
  font: inherit,
  cursor: pointer,
  outline: inherit,
  height:'40rpx'
  }">
						<image src="../../static/images/103.png" mode=""></image>
						<text>分享</text>
					</u-button>

				</view>
				<view class="btn" @click="goDetail">
					<image src="../../static/images/104.png" mode=""></image>
					<text>{{info.commentCount}}</text>
				</view>
				<view class="btn" @click="praise(info)">
					<image v-if="!info.isLike" src="../../static/images/105.png" mode=""></image>
					<image v-else src="../../static/images/105-no.png" mode=""></image>
					<text>{{info.likeCount}}</text>
				</view>
			</view>

		</view>
		<view class="right">
			<view v-if="info.isDel" @click="showSelect(info)">
				<text></text>
				<text></text>
				<text></text>
			</view>

		</view>
		<u-action-sheet :actions="list" :closeOnClickOverlay="true" :safeAreaInsetBottom="true" cancelText="取消"
			@select="selectClick" @close="show = false" :show="show"></u-action-sheet>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>

<script>
	import imageAdaptation from '@/components/images-adaptation/imageAdaptation.vue'
	import xzjReadMore from "@/components/xzj-readMore/xzj-readMore.vue"
	import ELM from '@/components/elm-toast/index.vue';
	export default {

		options: {
			styleIsolation: 'shared'
		},
		components: {
			imageAdaptation,
			xzjReadMore,
			ELM
		},
		props: {
			isState: {
				type: Boolean,
				default: false
			},
			info: {
				type: Object,
				default: () => {}
			}
		},
		watch: {
			info: {
				handler(newValue) {
					if (newValue?.img?.length) {
						this.imgs = newValue.img
					}
				},
				deep: true,
				immediate: true
			}
		},
		mounted() {
			if (this.$refs.uReadMore) {
				setTimeout(() => {
					this.$nextTick(() => {
						this.$refs.uReadMore.init();
					})
				}, 100)
			}

		},
		data() {
			return {
				tagStyle: {
					text: 'line-height:1em'
				},
				imgs: [],
				imgLayout1: [450, 450],
				imgLayout2: [220, 220],
				imgLayout3: [160, 160],
				show: false,
				list: [{
						name: '删除',
					},
					{
						name: "举报"
					}
				],
				shadowStyle: {
					'backgroundImage': 'linear-gradient(-180deg, rgba(255, 255, 255, 0) 50%, #fff 100%)',
					// paddingTop: "100px",
					// marginTop: "-100px",
					justifyContent: 'flex-end'
				},
				tipMsg: '',
				isConfirm: false
			}
		},
		methods: {
			load() {
				this.$refs.uReadMore.init();
			},
			select() {
				this.$emit("selectShare", this.info)
			},
			goDetail() {
				this.tipMsg = "开源版暂未开放，敬请期待！如需旗舰版，可联系作者微信（MMRWXM）咨询";
				this.$refs.elm.showDialog();
			},
			goPage(item) {
				uni.navigateTo({
					url: '/pageslike/like/userInfo?id=' + item.userId
				})
			},
			// 点赞
			praise(value) {
				this.$emit("praise", value)
			},
			// 删除
			selectClick(item) {
				if (item.name == "删除") {
					this.$emit("delete", this.info)
				} else if (item.name == "举报") {
					this.tipMsg = "尽情期待";
					this.$refs.elm.showDialog();
				}
			},
			preview({
				url,
				index
			}) {
				uni.previewImage({
					current: index,
					urls: this.imgs,
					longPressActions: {
						// itemList: ['发送给朋友', '保存图片', '收藏'],
						success: function(data) {
							console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
						},
						fail: function(err) {
							console.log(err.errMsg);
						}
					}
				});
				// // #ifdef MP-WEIXIN
				// this.$nextTick(() => {
				// 	this.$refs.previewImage.open(url); // 传入当前选中的图片地址(小程序必须添加$nextTick，解决组件首次加载无图)
				// })
				// // #endif

				// // #ifndef MP-WEIXIN
				// this.$refs.previewImage.open(url); // 传入当前选中的图片地址
				// // #endif
			},
			onLongpress(e) { //长按事件
				console.log('当前长按的图片是' + e);
				uni.showActionSheet({
					itemList: ['转发给朋友', '保存到手机'],
					success: function(res) {
						console.log('选中了第' + (res.tapIndex + 1) + '个按钮');
					},
					fail: function(res) {
						console.log(res.errMsg);
					}
				});
			},

			/* open和close方法一般用不到，但是在一些特殊场景会用到，
			 * 比如预览图片时你需要覆盖 NavigationBar和 TabBar，
			 * 或者在app中需要预览图片时覆盖住原生组件，比如video或者map等，
			 * 你可以根据open和close去做一些操作，例如隐藏导航栏或者隐藏一些原生组件等
			 */
			open() { //监听组件显示 （隐藏TabBar和NavigationBar，隐藏video原生组件）
				uni.hideTabBar()
				uni.setNavigationBarColor({
					frontColor: '#000000', // 设置前景色为黑色
					backgroundColor: '#000000', // 设置背景色为黑色
				})
				// this.videoShow = false 
				this.$emit("open")
			},
			close() { //监听组件隐藏 （显示TabBar和NavigationBar，显示video原生组件）
				uni.showTabBar()
				uni.setNavigationBarColor({
					frontColor: '#ffffff', // 设置前景色为白色
					backgroundColor: '#000000', // 设置背景色为黑色
				})
				// this.videoShow = true
				this.$emit("close")
			},
			showSelect(info) {
				if (info.isDelete != true) {
					const index = this.list.findIndex(i => i.name === '删除');
					if (index !== -1) {
						this.list.splice(index, 1);
					}
				}

				this.show = true;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.bor {
		border-top: 2rpx solid #F5F5F5;
	}

	.item {
		display: flex;
		justify-content: space-between;
		padding: 40rpx 0 0rpx 0;
		// padding-top: 40rpx;
		margin-top: 34rpx;
		margin-left: 32rpx;
		margin-right: 32rpx;

		.left {
			image {
				width: 68rpx;
				height: 68rpx;
				border-radius: 50%;
			}

			.l {
				height: 100%;
				margin-right: 10rpx;

				.box {
					font-weight: 600;
					font-size: 24rpx;
					color: #111827;

					.str {
						font-size: 30rpx;
					}

					.month {
						font-size: 20rpx;
					}
				}
			}

			.line {
				margin-left: 10rpx;
				width: 0;
				height: calc(100% - 80rpx);
				border: 1rpx solid #E6E6E6;
				margin-top: 20rpx;
			}
		}

		.center {
			margin: 0 20rpx;
			flex: 1;

			.name {
				text {

					font-weight: 600;
					font-size: 32rpx;
					color: #333333;
				}

				.gender {
					width: 36rpx;
					height: 36rpx;
					margin-left: 16rpx;
					vertical-align: middle;
				}
			}

			.info {
				font-weight: 400;
				font-size: 24rpx;
				color: #434343;
				margin: 12rpx 0 24rpx 0;
			}

			.content {
				font-weight: 400;
				font-size: 28rpx;
				color: #333333;
				margin-bottom: 16rpx;

				::v-deep .u-read-more__toggle {
					justify-content: flex-end !important;
				}
			}

			.contentImg {
				// max-height: 450rpx;
			}

			.time {
				font-weight: 400;
				font-size: 24rpx;
				color: #939393;
				margin: 22rpx 0 34rpx 0;
			}

			.btns {
				display: flex;
				justify-content: space-between;

				.btn {
					image {
						width: 28rpx;
						height: 28rpx;
						margin-right: 8rpx;
						vertical-align: middle;
					}

					text {
						font-weight: 400;
						font-size: 24rpx;
						color: #434343;
					}
				}
			}
		}

		.right {
			view {
				display: flex;
				height: 30rpx;
				padding-top: 14rpx;

				text {
					width: 8rpx;
					height: 8rpx;
					background-color: #333;
					border-radius: 50%;
					margin-right: 6rpx;
				}
			}

		}
	}
</style>
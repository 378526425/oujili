<template>
	<view>
		<view class="allImage">
			<view class="images" v-for="(item,index) in imgList" :key="index">
				<image @click="previewImg(item,index)" class="oneimg" :src="item" mode="aspectFill"
					:style="{width:imgWidth+'rpx',height:imgHeight+'rpx'}"></image>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			imgList: {
				type: Array
			},
			imgLayout1: {
				type: Array
			},
			imgLayout2: {
				type: Array
			},
			imgLayout3: {
				type: Array
			}
		},
		data() {
			return {
				imgWidth: 450,
				imgHeight: 450,
				imgDisplay: 'flex',
			}
		},
		mounted() {
			this.judgeImg();
		},
		methods: {
			// 预览图片
			previewImg(url,index) {
				this.$emit("previewImg", {url,index})
			},
			// 自适应判断
			judgeImg() {
				let that = this
				if (this.imgList == null) {
					this.imgDisplay = 'none';
				} else if (this.imgList.length == 1) {
					if (this.imgLayout1 != null) {
						this.imgWidth = this.imgLayout1[0];
						this.imgHeight = this.imgLayout1[1];
					} else {
						this.imgHeight = 450;
						this.imgWidth = 450;
					}

				} else if (this.imgList.length == 2 || this.imgList.length == 4) {
					if (this.imgLayout2 != null) {
						this.imgWidth = this.imgLayout2[0];
						this.imgHeight = this.imgLayout2[1];
					} else {
						this.imgWidth = 220;
						this.imgHeight = 220;
					}

				} else {
					if (that.imgLayout3 != null) {
						that.imgWidth = that.imgLayout3[0];
						that.imgHeight = that.imgLayout3[1];
					} else {
						this.imgWidth = 160;
						this.imgHeight = 160;
					}

				}
			}
		}
	}
</script>

<style>
	/* 想法图片排列样式 */

	.allImage {
		display: flex;
		margin-top: 10rpx;
		flex-wrap: wrap;
		justify-content: flex-start;
	}

	.images:not(:nth-child(3n)) {
		/* margin-right: 10rpx; */
	}

	.oneimg {
		margin-right: 12rpx;
		border: 1rpx solid #707070;
		border-radius: 12rpx;
	}
</style>
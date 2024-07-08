<template>
	<view class="wrap">
		<swiper
			class="swiper"
			:style="{ height: height * 2 + 'rpx' }"
			:autoplay="autoplay"
			:interval="interval"
			:duration="duration"
			:circular="loop"
			@change="change"
			:previous-margin="previousMargin + 'rpx'"
			:next-margin="nextMargin + 'rpx'"
		>
			<!-- <swiper-item v-for="(item, index) in list" :key="index" @click="$emit('clickItem', item)"> -->
			<swiper-item v-for="(item, index) in list" :key="index" @click="handLookImg(index, list)">
				<view v-if="list && list.length > 0" class="item" :class="[!crown ? '' : current == index ? 'crown-active' : 'crown']">
					<image v-if="!slots" class="item-img" :class="[imgShadow ? 'imgShadow' : '']" :src="item" :style="{ borderRadius: imgRadius + 'px', width: imgWidth }" mode="aspectFill"></image>
					<slot v-else :data="item"></slot>
				</view>
			</swiper-item>
		</swiper>
		<view class="dots flex" :style="{ bottom: bottom * 2 + 'rpx' }" v-if="dots">
			<view class="dot" :class="[current == i ? 'curr-dot' : '']" v-for="(d, i) in list" :key="i"></view>
		</view>
	</view>
</template>

<script>
export default {
	props: {
		list: {
			type: Array,
			default: () => []
		},
		// 轮播图片key
		imgKey: {
			type: String,
			required: true
		},
		// 高度
		height: {
			type: Number,
			default: 340
		},
		// 图片圆角
		imgRadius: {
			type: Number,
			default: 0
		},
		// 图片阴影
		imgShadow: {
			type: Boolean,
			default: false
		},
		// 前边距
		previousMargin: {
			type: Number,
			default: 0
		},
		// 后边距
		nextMargin: {
			type: Number,
			default: 0
		},
		// 图片宽度
		imgWidth: {
			type: String,
			default: '100%'
		},
		// 是否循环
		loop: {
			type: Boolean,
			default: false
		},
		// 自动播放
		autoplay: {
			type: Boolean,
			default: false
		},
		// 播放时间间隔
		interval: {
			type: Number,
			default: 2000
		},
		// 滑动速度
		duration: {
			type: Number,
			default: 1200
		},
		// 显示指示点
		dots: {
			type: Boolean,
			default: false
		},
		// 轮播点下边距
		bottom: {
			type: Number,
			default: 19
		},
		// 卡片特效
		crown: {
			type: Boolean,
			default: false
		}
	},
	data() {
		return {
			current: 0,
			slots: false
		};
	},
	watch: {
		// 判断异步数据源，是否使用插槽自定义样式
		list: {
			handler(val) {
				if (val.length > 0 && this.$slots.default) {
					this.slots = true;
				}
			},
			immediate: true
		}
	},
	methods: {
		change(event) {
			let current = event.detail.current;
			this.current = current;
			this.$emit('change', this.list[current]);
		},
		// 查看图片
		handLookImg(index, list) {
			console.log(index);
			console.log(list);
			// 预览图片
			uni.previewImage({
				current: index,
				urls: list,
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
		}
	}
};
</script>

<style lang="scss" scoped>
.wrap {
	position: relative;

	.crown {
		transform: scale(0.93, 0.85);
	}

	.item {
		height: 100%;
		transition: 1.2s;
	}

	.item-img {
		width: 100%;
		height: 100%;
	}

	.imgShadow {
		height: calc(100% - 10px);
		margin-bottom: 10px;
		box-shadow: 0 6px 6px rgba(0, 0, 0, 0.15);
	}

	.crown-active {
		transform: scale(1);
	}

	.dots {
		display: flex;
		position: absolute;
		left: 50%;
		transform: translateX(-50%);
		height: 12rpx;
		align-items: center;

		.dot {
			border-radius: 50%;
			width: 12rpx;
			height: 12rpx;
			background: rgba(255, 255, 255, 0.53);
			margin-right: 14rpx;
		}

		.curr-dot {
			width: 36rpx;
			height: 8rpx;
			background: #ffffff;
			border-radius: 4rpx;
		}
	}
}
</style>

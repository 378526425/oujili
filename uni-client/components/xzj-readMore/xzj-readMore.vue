<template>
	<view class="qaBox">
		<view class="">
			<view :class="[{ watchMoreContent: isWatchMore  }, {contentt:isLongContent} ,'xzj']" :style="" ><slot></slot></view>
			<view class="watchMore" v-if="isLongContent" @click.stop="watchMore">{{ isWatchMore ? '收起' : '查看更多' }}</view>
		</view>
	</view>
</template>
<script>
export default {
	data() {
		return {
			isWatchMore: false,
			isLongContent: false,
			zxy:{
				// '-webkit-line-clamp':'', /* 行数*/
				// lineClamp: '', /*行数*/
			}
		};
	},
	props: {
		// 默认的显示占位高度，单位为rpx
		showHeight: { //文本本身高度超出   showHeight  才会显示 查看更多
			type: [Number, String],
			default: 74,
			// 这里是rpx
		},
		// hideLineNum:{
		// 	type:[Number,String],
		// 	default:4,
		// 	// 4行
		// }
	},
	mounted() {
		this.$nextTick(function() {
			this.init();
		});
	},
	created(){
		for (let key in this.zxy) {
			// this.zxy[key]=this.hideLineNum
		}
	}
	,
	methods: {
		watchMore() {
			this.isWatchMore = !this.isWatchMore;
		},
		init() {
			this.getHeight('.xzj').then(res => {
				console.log(res.height,this.showHeight,uni.upx2px(28),"res.height");
				// 判断高度，如果真实内容高度大于占位高度，则显示收起与展开的控制按钮
				if (res.height > uni.upx2px(this.showHeight)) {
					this.isLongContent = true;
				}
			});
		},
		getHeight(selector, all) {
			return new Promise(resolve => {
				uni.createSelectorQuery()
					.in(this)
					[all ? 'selectAll' : 'select'](selector)
					.boundingClientRect(rect => {
						if (all && Array.isArray(rect) && rect.length) {
							resolve(rect);
						}
						if (!all && rect) {
							resolve(rect);
						}
					})
					.exec();
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.qaBox {
	text-align: right;
	font-size: 24rpx;
	font-weight: 400;
	margin-top: 14rpx;
	color: #00c15d;
	display: flex;
}
.contentt {
	overflow: hidden;
	text-overflow: ellipsis; /*超出则...代替*/
	display: -webkit-box;
	// -webkit-line-clamp: 4; /* 行数*/
	// line-clamp: 4; /*行数*/
	-webkit-box-orient: vertical;
	// line-height: 56rpx;
}
.xzj{
	color: #333333;
	font-size: 28rpx;
	// text-align;
	text-align: left;
	word-break: break-all;
	font-family: PingFang HK, PingFang HK;
}
.watchMoreContent {
	display: inline-block ;
	width: 100%;
}

.watchMore {
	color: #415C9E;
	margin-top: 20rpx;
}
</style>

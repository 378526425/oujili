<template>
	<view>
		<view class="slider-box">
			<view class="flex"></view>
			<movable-area class="sliderBar" :style="{width: (100 - minScore)+'%'}">
				<view class="gone" :style="{width: x +'px'}"></view>
			<!-- 	<movable-view class="slider" :x="x" :y="y" direction="all" @change="onChange">
					
				</movable-view> -->
				<view class="slider">
					
				</view>
				<!-- <text>{{ score }}</text> -->
			</movable-area>
			<view :style="{width: (100 - maxScore) +'%'}"></view>
			
		</view>
	</view>
</template>

<script>
	export default {
		props:['min','max','num'],
		data() {
			return {
			
				slideBarWidth:440,
				minScore: this.min?this.min:0,
				maxScore: this.max?this.max:100,
				x: (this.num*10)|| 0,
				y: 0,
				score: this.min?this.min:0,
				first:true
			};
		},
		// created() {
		// 	var that = this;
		// 	this.$nextTick(()=>{
		// 		uni.createSelectorQuery().select(".slider-box").boundingClientRect(function(res) {
		// 			that.slideBarWidth = res.width
		// 		}).exec();
		// 	})
		// },
		mounted() {
			
			this.x= this.num/10*220
			this.score=this.num
			setTimeout(() => {
			  this.first=false
			}, 300)
			
		},
		
		methods:{
			onChange(e) {
				
				return false
				this.x = e.detail.x
				
				this.score = parseInt(e.detail.x/this.slideBarWidth*100) + parseInt(this.minScore)
			
				this.$emit('change',this.score)
			}
		}
	}
</script>

<style lang="scss">
	$uni-color-primary: #F63D59;
	.slider-box {
		display: flex;
		width: 440rpx;
		height: 52rpx;
		
		position: relative;
		display: flex;
		align-items: center;
		&::before{
			content: '';
			position: absolute;
			height: 24rpx;
			border-radius: 100rpx;
			background-color: #F5F5F5;
			
			left: 0;
			
			width: 440rpx;
		}
		.flex {
			flex: 1;
			height: 8rpx;
			border-radius: 8rpx 0 0 8rpx;
			background-color: $uni-color-primary;
			
			position: relative;
			z-index: 1;
		}

		.sliderBar {
			height: 100%;
			border-radius: 100rpx;
			width: 440rpx;
			display: flex;
			align-items: center;
			.gone {
				background-color: #FF91C5;
				height: 100%;
				position: absolute;
				left: 0;
				height: 24rpx;
				
				max-width: 440rpx;
				z-index: 1;
				border-radius: 100rpx;
			}
			.slider {
				width: 0;
				height: 100%;
				position: relative;
				z-index: 2;
				display: flex;
				align-items: center;
				&::after {
					content: '';
					position: absolute;
					border-radius: 16rpx;
					box-shadow: 0px 0px 12px 2px rgba(161,161,161,0.16);
					background-color: #FFFFFF;
					width: 32rpx;
					height: 32rpx;
					transform: translatex(-50%);
				}
				text {
					position: absolute;
					width: 60rpx;
					color: white;
					border-radius: 14rpx;
					top: -140%;
					left: 50%;
					text-align: center;
					transform: translateX(-50%);
					background-color: $uni-color-primary;
					&::after {
						content: '';
						position: absolute;
						border: 6rpx solid transparent;
						border-top-color: $uni-color-primary;
						top: 99%;
						left: 50%;
						transform: translateX(-50%);
					}
				}
			}
		}
	}
</style>

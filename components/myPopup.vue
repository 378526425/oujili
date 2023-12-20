<template>
	<view class="">
		
	<u-popup u-popup :show="tipsShow" mode="center" round="12" :safeAreaInsetBottom="false">
		<view class="popup-container" :style="{'height':height}">
			<image class="pop-bgc" :src="img + '/img/home-pop-bg.png'" mode="widthFix"></image>					
			<view class="div-popo">
				<view class="pop-title">温馨提示</view>
				<view class="tips-content">
					{{content}}
				</view>
				<view class="btn-c">
					<view class="pop-btn agree" @click="handAgree">{{confirmText}}</view>
					<view class="pop-btn" @click="handleNoAgree">{{cancelText}}</view>
				</view>
			</view>
		</view>
	</u-popup>
	</view>
</template>

<script>
	export default{
		props:{
			// 文字内容区高度
			height:{
				type:String,
				default:''
			},
			// 内容区文字
			content:{
				type:String,
				default:''
			},
			// 确定按钮文字
			confirmText:{
				type:String,
				default:'确定'
			},
			// 取消按钮文字
			cancelText:{
				type:String,
				default:'取消'
			}
		},
		data(){
			return{
				img: this.$BASE_URL,
				tipsShow:true
			}
		},
		methods:{
			handAgree(){
				this.$emit('confirm')
			},
			handleNoAgree(){
				this.$emit('cancel')
			}
		}
	}
</script>

<style lang="scss" scoped>
	.pop-bgc {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		overflow: hidden;
		// z-index: -1;
	}
	
	.popup-container {
		min-height: 422rpx;
		width: 544rpx;
		overflow: hidden;
		text-align: center;
		position: relative;
		border-radius: 12px;
	
		.div-popo {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
	
			.pop-title {
				color: rgba(45, 49, 50, 0.8);
				font-weight: 400;
				font-size: 40rpx;
				margin-top: 78rpx;
				font-family: 'PingFang SC-Heavy, PingFang SC';
			}
	
			.tips-content {
				width: 448rpx;
				margin: auto;
				text-align: left;
				margin-top: 50rpx;
				line-height: 56rpx;
				color: #666;
				font-size: 28rpx;
				font-family: 'PingFang SC-Regular, PingFang SC';
				text-align: center;
	
				.agreement-btn {
					display: inline-block;
					color: #5b83e8;
					font-family: 'PingFang SC-Bold, PingFang SC';
					text-decoration: underline;
				}
			}
	
			.btn-c {
				display: flex;
				margin: auto;
				margin-top: 64rpx;
				justify-content: space-between;
				width: 400rpx;
			}
		}
	
		.pop-btn {
			// width: 204rpx;
			height: 76rpx;
			padding: 0 50rpx;
			border-radius: 38rpx;
			text-align: center;
			line-height: 76rpx;
			border: 2rpx solid #446cd2;
			color: #5b83e8;
			font-family: 'PingFang SC-Regular, PingFang SC';
	
			&.agree {
				border: none;
				background: linear-gradient(86deg, #c2d2f9 0%, #c5c2f3 100%);
			}
		}
	}
</style>
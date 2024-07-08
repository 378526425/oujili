<template>
	<view class="item">
		<view class="one">
			<view class="l">
				<image @click="goPage2(info.senderId)" :src="info.senderHeadPortrait" mode="aspectFill"></image>
				<view class="info">
					<view class="name" @click="goPage2(info.senderId)">
						{{info.senderNickName}}
					</view>
					<view class="m" v-if="info.category=='COMMENT'">
						评论了您的动态
					</view>
					
					<view class="m" v-else-if="info.category=='LIKE_ARTICLE'">
						点赞了您的动态
					</view>
					<view class="m" v-else-if="info.category=='LIKE_COMMENT'">
						点赞了您的评论
					</view>
					<view class="m" v-else-if="info.category=='LIKE_REPLY'">
						点赞了您的回复
					</view>
					<view class="time" v-else>
						{{info.createTimeDesc}} {{info.senderIp}}
					</view>
				</view>

			</view>
			<view class="r">
				<view class="time" v-if="isLike">
					{{info.createTimeDesc}} {{info.senderIp}}
				</view>
				<view class="itemBen" @click="reply" v-if="!isLike">
					回复
				</view>

			</view>
		</view>
		<view class="two">
			<view class="m1" v-if="info.category=='REPLY'">
				回复
				<text @click="goPage2(info.articleUserId)">@{{info.replyNickName}}</text>
				：{{info.content}}
			</view>
			<view class="m1" style="padding-left: 25rpx;" v-else-if="info.category!='LIKE_ARTICLE'">
			   {{info.content}}
			</view>
			
			<view class="coo" @click="goPage">
				<view class="txt">
					{{info.articleUserNickName}}：{{info.articleContent}}
				</view>
				<view :class="['imgs',info.img.length==2?'twoTemp':'']" v-if="info.img!==null&&info.img.length">
					<image v-for="item in info.img.splice(0,3)" :src="item" mode="aspectFill"></image>
				</view>
				<text class="el-popper__arrow"></text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			isLike: {
				type: Boolean
			},
			info: {
				type: Object,
				default: () => {}
			}
		},
		methods: {
			reply() {
				this.$emit("reply")
			},
			goPage() {
				uni.navigateTo({
					url: "/pagescommunity/dynamicDetail/dynamicDetail?id=" + this.info.articleId
				})
			},
			goPage2(id) {
				uni.navigateTo({
					url: '/pageslike/like/userInfo?id=' + id
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.item {
		margin-bottom: 48rpx;

		.one {
			display: flex;
			justify-content: space-between;
			align-items: center;

			.l {
				display: flex;

				image {
					width: 92rpx;
					height: 92rpx;
					border-radius: 50%;
					margin-right: 28rpx;
				}

				.info {
					display: flex;
					flex-direction: column;
					justify-content: space-between;

					.name {
						font-size: 32rpx;
						color: #111827;
					}

					.time {
						margin-top: 10rpx;
						font-weight: 400;
						font-size: 24rpx;
						color: #8F97A4;
					}

					.m {
						font-weight: 400;
						font-size: 28rpx;
						color: #434343;
					}
				}
			}

			.r {
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				align-items: flex-end;

				.time {
					font-weight: 400;
					font-size: 24rpx;
					color: #8F97A4;
					margin-bottom: 14rpx;
				}

				.itemBen {
					width: 106rpx;
					height: 52rpx;
					background: rgba(255, 255, 255, 0.44);
					border-radius: 34rpx 34rpx 34rpx 34rpx;
					border: 2rpx solid #EFF5F5;
					font-size: 24rpx;
					color: #8F97A4;
					text-align: center;
					line-height: 52rpx;
				}

			}
		}

		.two {
			.m1 {
				font-weight: 400;
				font-size: 28rpx;
				color: #434343;
				margin-top: 22rpx;
			}

			.coo {
				background: #F9FAFB;
				border-radius: 24rpx 24rpx 24rpx 24rpx;
				padding: 36rpx 44rpx;
				margin-top: 22rpx;
				position: relative;

				.txt {
					font-weight: 400;
					font-size: 28rpx;
					color: #333333;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap
				}
				.twoTemp{
					&:after{
						 width: calc(33% - 10rpx);
						 content: '';		
					}
					image{
						&:last-child{
							margin-left: 10rpx;
						}
					}
				}

				.imgs {
					display: flex;
					justify-content: space-between;
					margin-top: 24rpx;

					image {
						width: calc(33% - 10rpx);
						height: 198rpx;
						// border: 1rpx solid #707070;
						border-radius: 12rpx;
						// &:first-child{
						// 	border-bottom-left-radius: 12rpx;
						// 	border-top-left-radius:12rpx;
						// }
						// &:last-child{
						// 	border-bottom-right-radius: 12rpx;
						// 	border-top-right-radius:12rpx;
						// }
					}
				}

				.el-popper__arrow {
					position: absolute;
					left: 25rpx;
					top: -8rpx;
					width: 16rpx;
					height: 16rpx;
					z-index: -1;

					&::before {
						border-bottom-color: #F9FAFB ;
						border-right-color: #F9FAFB ;
						border-top-left-radius: 2rpx;
						border: 2rpx solid #F9FAFB;
						background: #F9FAFB;
						right: 0;
						position: absolute;
						width: 16rpx;
						height: 16rpx;
						z-index: -1;
						content: " ";
						transform: rotate(45deg);
						box-sizing: border-box;
					}
				}

			}
		}
	}
</style>
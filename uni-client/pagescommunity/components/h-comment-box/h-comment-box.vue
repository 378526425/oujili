<template>
	<view>
		<template v-if="commentList.length">
			<view class="h_comment" v-for="(item, index) in  commentList " :key="item.id">
				<!-- 一级评论 => 列表 => -->
				<view class="h_left">
					<u-avatar size="64rpx" :src="item.url" mode="aspectFill"></u-avatar>
				</view>

				<view class="h_right">
					<!-- 用户信息 -->
					<view class="h_info">
						<view class="name" :style="{ color: '#111827' }">{{ item.name }}</view>
						<view class="like" :style="{ color: item.isLike ? themeColor : '#9a9a9a' }">
							<view class="num" :style="{ color: item.isLike ? themeColor : '#9a9a9a' }">
								{{ item.likeNum }}
							</view>
							<u-icon v-if="!item.isLike" name="thumb-up" size="40rpx" color="#9a9a9a"
								@click="getLike(index,undefined,true)"></u-icon>
							<u-icon v-if="item.isLike" name="thumb-up-fill" :color="themeColor" size="40rpx"
								@click="getLike(index,undefined,false)"></u-icon>
						</view>
					</view>

					<!-- 一级评论 => 文本 -->
					<view class="h_content" @tap="showRevertPop(index, item, null, null)">{{ item.contentText }}</view>

					<!-- 一级评论 => 日期、回复 -->
					<view class="h_date">
						{{ item.date }}
						<!-- <view class="h_revert" :style="{ color: themeColor }" @tap="showRevertPop(index, item, null, null)">
						回复
					</view> -->
						<view class="h_remove" v-if="item.isMyComment"
							@tap="removeRevertModal(index, item, null, null)">
							删除
						</view>
					</view>

					<!-- 二级评论 -->
					<view class="h_revert_list">

						<!-- 二级评论 => 列表 -->
						<view class="item_s" v-for="( item_s, j ) in  item.replyList " :key="item_s.index">
							<view class="user-info">
								<u-avatar size="40rpx" :src="item_s.url" mode="aspectFill"></u-avatar>
								<view class="username">
									<view class="left-name">
										{{ item_s.name }}
									</view>
									<template v-if="item_s.to_user_id">
										<!-- <u-icon name="play-right-fill" color="#868686"
										size="10"></u-icon> -->

										<view class="right-name">
											<text>回复@</text>
											{{ item_s.to_user_name }}:
										</view>
									</template>

								</view>
								<view class="like" :style="{ color: item_s.isLike ? themeColor : '#9a9a9a' }">
									<view class="num" :style="{ color: item_s.isLike ? themeColor : '#9a9a9a' }">{{
                                    item_s.likeNum }}</view>
									<u-icon v-if="!item_s.isLike" name="thumb-up" size="40rpx" color="#9a9a9a"
										@click="getLike(index, j,true)"></u-icon>
									<u-icon v-if="item_s.isLike" name="thumb-up-fill" :color="themeColor" size="40rpx"
										@click="getLike(index, j,false)"></u-icon>
								</view>
							</view>
							<view class="text" @tap="showRevertPop(index, item, j, item_s)">{{ item_s.contentStr }}
							</view>
							<view class="date">
								{{ item_s.date }}
								<!-- <view class="revert" :style="{ color: themeColor }"
								@tap="showRevertPop(index, item, j, item_s)">回复</view> -->
								<view class="h_remove" v-if="item_s.user_isMyComment"
									@tap="removeRevertModal(index, item, j, item_s)">
									删除
								</view>
							</view>
						</view>
						<text class="el-popper__arrow"></text>

						<!-- loading -->
						<u-loading-icon class="p-t-20" :show="item.isLoading" color="#a4a4a4" textColor="#ccc"
							text="加载中" :duration="1500">
						</u-loading-icon>

						<!-- 展开更多二级评论 -->
						<view class="reply-more" @tap="showAllReply(index, item)"
							v-if="item.allReply && Number(item.allReply) - item.replyList.length">
							<view class="left-line" />
							展开
							<u-icon class="more" name="arrow-down"  :size="20"></u-icon>
						</view>

						<!-- 收起二级评论 -->
						<view class="reply-more flex-row-center" @tap="closetoAllReply(index)"
							v-if="item.allReply && Number(item.allReply) === item.replyList.length&&item.allReply!=1">
							<view class="left-line" />
							收起回复
							<u-icon class="more" name="arrow-up" :size="20"></u-icon>
						</view>


					</view>
				</view>
			</view>
		</template>

		<!-- 评论弹框 -->
		<!-- <u-popup  :show="showPopup" type="bottom" class="comment-pop" @close="close"> -->
		<view class="empty" v-else>
			<image style="width: 302rpx;height: 248rpx;" :src="img+'/images/article/1.png'" mode=""></image>
			<view class="emptyTxt">
				暂时还没有评论哦～
			</view>
		</view>
		
		<view class="comment-pop-content" :style="{'bottom':heightFn}">
			<textarea placeholder-style="color:#B7B7B7" :auto-height="true" :show-confirm-bar="false" :placeholder="placeholder" :adjust-position="false" confirm-type="send"
				v-model="inputVal" :focus="comtIptFocusStatus" :cursor-spacing="120" @focus="focusFn"
				@keyboardheightchange="keyboardheightchange" @blur="blurFn" @input="onChangeIput" @confirm="iptConfirm" class="myIpt"></textarea>
				<view class="btns">
								<view class="btn">
									<u-button open-type="share" :customStyle="{background: none,
				color: inherit,
				border: 0,
				padding: 0,
				font: inherit,
				cursor: pointer,
				outline: inherit,
				height:'40rpx'
				}">
										<image src="@/static/images/103.png" mode=""></image>
										<text>分享</text>
									</u-button>
				
								</view>
								<view class="btn" @click="pl">
									<image src="@/static/images/104.png" mode=""></image>
									<text>{{commentCount}}</text>
								</view>
								<view class="btn" @click="bigLike">
									<image v-if="!isLike" src="@/static/images/105.png" mode=""></image>
									<image v-else src="@/static/images/105-no.png" mode=""></image>
									<text>{{likeCount}}</text>
								</view>
							</view>
			<!-- <view class="send" :style="{ background: themeColor }" @click="sendComment">发送</view>
			<view class="like2" @click="bigLike">
				<image v-if="!isLike" src="@/static/images/105.png" mode=""></image>
				<image v-else src="@/static/images/105-no.png" mode=""></image>
				<text>点赞</text>
			</view> -->
		</view>
		<!-- </u-popup> -->
		<myPopup v-if="isShow" :content="msg" @confirm="confirm" @cancel="isShow = false"></myPopup>
	</view>
</template>

<script>
	import myPopup from '@/components/myPopup.vue'
	export default {
		components: {
			myPopup
		},
		props: {
			isLike: {
				type: Boolean,
				default: false
			},
			likeCount:{
				type: Number,
				default: 0
			},
			commentCount:{
				type: Number,
				default: 0
			},
			list: {
				type: Array,
				default: () => []
			},
			modelValue: {
				type: String,
				default: ''
			},
			keyNames: {
				type: Object,
				default: () => {
					return {
						// 一级评论相关
						id: 'id', // 评论id
						user_id: 'userId', // 用户id
						user_name: 'nickName', // 用户名
						user_avatar: 'headPortrait', // 用户头像
						user_content: 'content', // 用户评论内容
						user_date: 'time', // 用户评论时间
						user_is_like: 'isLike', // 用户是否点赞
						user_like_num: 'likeCount', // 用户点赞数
						isLoading: 'isLoading', // 是否显示加载中
						allReply: 'replyCount', // 评论总数
						isMyComment: 'deleteBtn', // 是否是自己的评论
						// 二级评论相关
						user_reply_list: 'user_reply_list', // 回复列表
						user_reply_id: 'id', // 回复人id
						user_reply_name: 'nickName', // 回复人名字
						user_reply_avatar: 'headPortrait', // 回复人头像
						user_reply_content: 'content', // 回复 内容
						user_reply_date: 'time', // 回复 时间
						user_reply_is_like: 'isLike', // 回复人是否点赞
						user_reply_like_num: 'likeCount', // 回复人点赞数
						pid: 'pid',
						user_isMyComment: 'deleteBtn', // 是否是自己的评论
						to_user_name: 'replyUserName', // 被回复人名字
						to_user_id: 'replyUserId', // 被回复人id
					}
				}
			}
		},
		data() {
			return {
				img:this.$BASE_URL,
				isShow: false,
				msg: '',
				themeColor: '#86A6F3',

				// 输入框的placeholde
				placeholder: '走心，说点好听的…',
				// 输入框的value
				inputVal: '',

				comtIptFocusStatus: false, // 评论输入框聚焦状态


				isComtIndex: 0, // 回复：当前点击的一级评论索引
				isComtRow: {}, // 回复：点击的 一级评论信息
				isComtIndex_s: 0, // 回复：点击的 二级评论索引
				isComtRow_s: {}, // 回复：点击的 二级评论信息
				commentList: [],
				temp: {},
				h:0
			}
		},
		// created() {
		// 	this.commentList = this.list.map((item) => {
		// 		return {
		// 			id: item[this.keyNames.id],
		// 			name: item[this.keyNames.user_name],
		// 			url: item[this.keyNames.user_avatar],
		// 			contentText: item[this.keyNames.user_content],
		// 			date: item[this.keyNames.user_date],
		// 			isLike: item[this.keyNames.user_is_like],
		// 			likeNum: item[this.keyNames.user_like_num],
		// 			isLoading: item[this.keyNames.isLoading],
		// 			allReply: item[this.keyNames.allReply],
		// 			isMyComment: item[this.keyNames.isMyComment],
		// 			replyList: item[this.keyNames.user_reply_list].map((item_s) => {
		// 				return {
		// 					id: item_s[this.keyNames.user_reply_id],
		// 					pid: item_s[this.keyNames.pid],
		// 					name: item_s[this.keyNames.user_reply_name],
		// 					url: item_s[this.keyNames.user_reply_avatar],
		// 					contentStr: item_s[this.keyNames.user_reply_content],
		// 					date: item_s[this.keyNames.user_reply_date],
		// 					isLike: item_s[this.keyNames.user_reply_is_like],
		// 					likeNum: item_s[this.keyNames.user_reply_like_num],
		// 					to_user_name: item_s[this.keyNames.to_user_name],
		// 					to_user_id: item_s[this.keyNames.to_user_id],
		// 					user_isMyComment: item_s[this.keyNames.user_isMyComment],
		// 				}
		// 			})
		// 		}
		// 	})
		// },
		watch: {
			list: {
				handler(newValue) {

					this.commentList = this.commentList = newValue.map((item) => {
						return {
							id: item[this.keyNames.id],
							name: item[this.keyNames.user_name],
							url: item[this.keyNames.user_avatar],
							contentText: item[this.keyNames.user_content],
							date: item[this.keyNames.user_date],
							isLike: item[this.keyNames.user_is_like],
							likeNum: item[this.keyNames.user_like_num],
							isLoading: item[this.keyNames.isLoading],
							allReply: item[this.keyNames.allReply],
							isMyComment: item[this.keyNames.isMyComment],
							replyList: item[this.keyNames.user_reply_list].map((item_s) => {
								return {
									id: item_s[this.keyNames.user_reply_id],
									pid: item_s[this.keyNames.pid],
									name: item_s[this.keyNames.user_reply_name],
									url: item_s[this.keyNames.user_reply_avatar],
									contentStr: item_s[this.keyNames.user_reply_content],
									date: item_s[this.keyNames.user_reply_date],
									isLike: item_s[this.keyNames.user_reply_is_like],
									likeNum: item_s[this.keyNames.user_reply_like_num],
									to_user_name: item_s[this.keyNames.to_user_name],
									to_user_id: item_s[this.keyNames.to_user_id],
									user_isMyComment: item_s[this.keyNames.user_isMyComment],
								}
							})
						}
					})
				},
				deep: true
			}
		},
		computed:{
			heightFn(){
				return `${this.h}px`
			}
		},
		methods: {
			pl(){
				this.close()
				this.comtIptFocusStatus = true
			},
			bigLike() {
				this.$emit("bigLike")
			},
			iptConfirm(){
				this.sendComment()
			},
			sendComment() {
				/**
				 * @argument index 一级评论索引
				 * @argument row 一级评论信息
				 * @argument index_s 二级评论索引
				 * @argument row_s 二级评论信息
				 * @callback ()=>{} 发送评论接口成功之后 调用sendComment的第二个形参 该形参为方法，更新组件内的评论列表
				 */
				this.$emit('sendComment', {
					index: this.isComtIndex,
					row: this.isComtRow,
					index_s: this.isComtIndex_s,
					row_s: this.isComtRow_s,
				}, (data) => {
					if (this.isComtIndex_s !== null) {
						this.commentList[this.isComtIndex].replyList.unshift({
							...data,
							isMyComment: true
						})

					} else {
						this.commentList.unshift({
							...data,
							isMyComment: true
						})
					}
					this.close()
				})
			},
			blurFn() {
				this.close()
				this.comtIptFocusStatus = false
				this.h = 0
			},
			focusFn(e){
				this.h = e.detail.height
			},
			keyboardheightchange(e){
				// this.h = e.detail.height
			},
			showRevertPop(index, row, j, row_s) {
				this.isComtIndex = index // 保存当前评论,发送评论时使用
				this.isComtRow = { // 保存当前评论,发送评论时使用
					id: row.id,
					name: row.name,
					url: row.url,
					contentText: row.contentText,
					date: row.date,
					isLike: row.isLike,
					likeNum: row.likeNum,
					allReply: row.allReply,
					isMyComment: row.isMyComment,
				}
				if (j !== null) this.isComtIndex_s = j // 保存当前评论,发送评论时使用
				if (row_s !== null) {
					if (row_s === undefined) {
						let obj = row.replyList[j]
						this.isComtRow_s = {
							id: obj.id,
							pid: obj.pid,
							name: obj.name,
							url: obj.url,
							contentStr: obj.contentStr,
							date: obj.date,
							isLike: obj.isLike,
							likeNum: obj.likeNum,
							user_isMyComment: obj.user_isMyComment,
						}
					} else {
						this.isComtRow_s = {
							id: row_s.id,
							pid: row_s.pid,
							name: row_s.name,
							url: row_s.url,
							contentStr: row_s.contentStr,
							date: row_s.date,
							isLike: row_s.isLike,
							likeNum: row_s.likeNum,
							user_isMyComment: row_s.user_isMyComment,
						}
					}

				}

				this.placeholder = `回复 @${j !== null ? this.isComtRow_s.name : row.name}`
				this.comtIptFocusStatus = true
			},
			confirm() {
				const {
					index,
					row,
					j,
					row_s
				} = this.temp
				this.$emit('remove', {
					index,
					row,
					j,
					row_s
				}, () => {
					if (j !== null) {
						let id = this.commentList[index].replyList[j].id
						this.$myRequest({
							url: `/nostalgia/article/reply/${id}`,
							withToken: true,
							method: 'DELETE',
						}).then(res => {
							this.isShow = false
							this.$emit("refresh")
						})
					} else {
						let id = this.commentList[index].id
						this.$myRequest({
							url: `/nostalgia/article/comment/${id}`,
							withToken: true,
							method: 'DELETE',
						}).then(res => {
							this.isShow = false
							this.$emit("refresh")
						})
					}
				})
			},
			removeRevertModal(index, row, j, row_s) {
				this.temp = {
					index,
					row,
					j,
					row_s
				}
				this.msg = j === null ? '确定删除该评论吗？' : '确定删除该回复吗？'
				this.isShow = true
			},
			close(msg) {
				this.inputVal = ''
				this.isComtIndex = undefined;
				this.isComtRow = {};
				this.isComtIndex_s = undefined;
				this.isComtRow_s = {}
				this.placeholder = "走心，说点好听的…"
				if (msg) uni.showToast({
					title: msg,
					icon: 'none'
				})
			},
			closetoAllReply(index) {
				this.commentList[index].replyList = [this.commentList[index].replyList[0]]
			},
			showAllReply(index, row) {
				this.commentList[index].isLoading = true // 显示加载中
				this.$emit('getMore', {
					index,
					row
				}, (data) => {
					let newData = null
					if (Array.isArray(data) && data.length) {
						newData = data.map((item) => {
							return {
								id: item[this.keyNames.user_reply_id],
								pid: item[this.keyNames.pid],
								name: item[this.keyNames.user_reply_name],
								url: item[this.keyNames.user_reply_avatar],
								contentStr: item[this.keyNames.user_reply_content],
								date: item[this.keyNames.user_reply_date],
								isLike: item[this.keyNames.user_reply_is_like],
								likeNum: item[this.keyNames.user_reply_like_num],
								to_user_id: item[this.keyNames.to_user_id],
								to_user_name: item[this.keyNames.to_user_name],
								user_isMyComment: item[this.keyNames.user_isMyComment],
							}
						})

						this.commentList[index].replyList.push(...newData)

					} else {
						newData = {
							id: item[this.keyNames.user_reply_id],
							pid: item[this.keyNames.pid],
							name: item[this.keyNames.user_reply_name],
							url: item[this.keyNames.user_reply_avatar],
							contentStr: item[this.keyNames.user_reply_content],
							date: item[this.keyNames.user_reply_date],
							isLike: item[this.keyNames.user_reply_is_like],
							likeNum: item[this.keyNames.user_reply_like_num],
							to_user_id: item[this.keyNames.to_user_id],
							to_user_name: item[this.keyNames.to_user_name],
							user_isMyComment: item[this.keyNames.user_isMyComment],
						}

						this.commentList[index].replyList.push(newData)

					}

					this.commentList[index].isLoading = false // 关闭加载中

				})
			},
			getLike(index, j, isLike) {
				/**
				 * index 一级评论索引
				 * j 二级评论索引
				 * ()=>{} 点在接口成功之后 调用getLike的第二个形参 该形参为方法，更新组件内的点赞样式
				 */
				let obj = {
					row_id: this.commentList[index].id,
					rows_id: j !== undefined ? this.commentList[index].replyList[j].id : undefined,
					isLike
				}
				this.$emit('getLike', {
					...obj
				}, () => {
					if (j !== undefined) {
						this.commentList[index].replyList[j].isLike = !this.commentList[index].replyList[j]
							.isLike
						if (this.commentList[index].replyList[j].isLike == true) {
							this.commentList[index].replyList[j].likeNum++;
						} else {
							this.commentList[index].replyList[j].likeNum--;
						}

					} else {
						this.commentList[index].isLike = !this.commentList[index].isLike;
						if (this.commentList[index].isLike == true) {
							this.commentList[index].likeNum++;
						} else {
							this.commentList[index].likeNum--;
						}
					}
				})
			},
			onChangeIput(e) {
				
				this.$emit('changeIpt', e.detail.value)
			}
		}
	}
</script>

<style lang="scss" scoped>
	* {
		font-family: PingFang HK, PingFang HK;
		box-sizing: border-box;
	}


	.h_comment {
		display: flex;
		padding: 30rpx 32rpx 0 32rpx;
		background-color: #fff;

		.h_left {
			margin-right: 10rpx;

			image {
				width: 64rpx;
				height: 64rpx;
				border-radius: 50%;
				background-color: #f2f2f2;
			}
		}

		.h_right {
			flex: 1;
			// padding-left: 20rpx;
			padding-top: 6rpx;
			font-size: 30rpx;


			.h_info {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 10rpx;



				.like {
					display: flex;
					align-items: center;
					font-size: 26rpx;

					.num {
						margin-right: 4rpx;
					}
				}


			}

			.h_content {
				margin-bottom: 10rpx;
				font-size: 28rpx;
				color: #000011;
			}


			.h_date {
				display: flex;
				font-size: 24rpx;
				color: #9a9a9a;


				.h_revert {
					margin-left: 10rpx;
				}

				.h_remove {
					color: #868686;
					margin-left: 20rpx;

				}
			}



			.h_revert_list {
				background-color: rgb(251, 251, 251);
				border-radius: 12rpx;
				margin-top: 20rpx;
				padding: 0 20rpx;
				position: relative;
				.item_s {
					padding-top: 20rpx;


					.user-info {
						display: flex;
						align-items: center;


						.username {
							flex: 1;
							display: flex;
							align-items: center;
							font-size: 24rpx;
							// color: #999999;
							margin-left: 10rpx;

							.left-name {
								margin-right: 10rpx;
							}

							.right-name {
								color: #999999;
								margin-left: 10rpx;
							}

						}


						.like {
							display: flex;
							align-items: center;
							color: #9a9a9a;
							font-size: 26rpx;

							.num {
								margin-right: 4rpx;
								color: #9a9a9a;
							}
						}


					}

					.text {
						padding: 8rpx 0;
					}

					.date {
						display: flex;
						font-size: 24rpx;
						color: #9a9a9a;


						.revert {
							margin-left: 10rpx;
						}

						.h_remove {
							color: #868686;
							margin-left: 20rpx;
						}
					}
				}
				.el-popper__arrow {
					position: absolute;
					left: 25rpx;
					top: -8rpx;
					width: 16rpx;
					height: 16rpx;
					z-index: 0;
				
					&::before {
						border-bottom-color: #fbfbfb ;
						border-right-color: #fbfbfb ;
						border-top-left-radius: 2rpx;
						border: 2rpx solid #fbfbfb;
						background: #fbfbfb;
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


				.reply-more {
					width: 100%;
					padding-top: 20rpx;
					display: flex;
					color: #868686;
					align-items: center;
					font-size: 24rpx;
					padding-bottom: 20rpx;


					.left-line {
						width: 50rpx;
						height: 2rpx;
						background-color: #eee;
						margin-right: 10rpx;
					}


					.more {
						margin-left: 6rpx;
					}
				}



			}
		}
	}


	::v-deep .vue-ref {
		padding-bottom: 0 !important;
	}


	::v-deep .u-input {
		width: 70% !important;
	}

	.empty {
		height: 590rpx;

		image {
			display: block;
			margin: 0 auto;
			margin-top: 94rpx;
			margin-bottom: 38rpx;
		}

		view {
			font-family: PingFang SC, PingFang SC;
			font-weight: 400;
			font-size: 28rpx;
			color: #C9C9D0;
			text-align: center;
		}
	}

	

	.comment-pop-content {
		box-sizing: border-box;
		min-height: 136rpx;
		width: 100vw;
		background-color: #fff;
		display: flex;
		align-items: center;
		box-shadow: 0rpx 0rpx 24rpx 2rpx rgba(175, 175, 175, 0.16);
		padding: 10rpx 32rpx 0 32rpx; 
		position: fixed;
		left: 0;
		bottom: 0;
		z-index: 9999;
		// bottom: constant(safe-area-inset-bottom);
		// /*兼容 IOS<11.2*/
		// bottom: env(safe-area-inset-bottom);
		// /*兼容 IOS>11.2*/
		padding-bottom: constant(safe-area-inset-bottom);
		padding-bottom: env(safe-area-inset-bottom);
		textarea{
			width: 334rpx;
			height: 60rpx;
			background: #F9FAFB;
			border-radius: 30rpx 30rpx 30rpx 30rpx;
			padding: 8rpx 20rpx;
			font-size: 24rpx;
		}
		.btns {
			display: flex;
			justify-content: space-between;
			align-items: center;
			flex: 1;
			margin-left: 30rpx;
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
		.send {
			width: 100rpx;
			height: 50rpx;
			border-radius: 60rpx;
			font-size: 28rpx;
			font-weight: 500;
			color: #ffffff;
			margin: 0 0rpx 0 30rpx;
			text-align: center;
			line-height: 50rpx;
			font-size: 28rpx;

		}

		.like2 {
			margin: 0 20rpx 0 20rpx;
			font-size: 24rpx;
			color: #434343;
			width: 100rpx;
			height: 47rpx;
			border-radius: 60rpx;
			border: 2rpx solid #415C9E;
			display: flex;
			align-items: center;
			justify-content: center;

			image {
				width: 28rpx;
				height: 28rpx;
				margin-right: 5rpx;
			}
		}
	}

	.p-t-20 {
		padding-top: 20rpx;
	}


	.flex-row-center {
		justify-content: center;
	}
</style>
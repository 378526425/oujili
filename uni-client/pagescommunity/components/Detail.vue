<template>
	<view class="">
		<view class="item">
			<view class="left">
				<image :src="info.headPortrait" mode="aspectFill"></image>
			</view>
			<view class="center">
				<template>
					<view class="name">
						<text>{{info.nickName}}</text>
						<image v-if="info.gender!='MALE'" class="gender" src="../../static/images/like/famale.png"
							mode="">
						</image>
						<image v-else class="gender" src="../../static/images/like/male.png" mode=""></image>
					</view>
					<view class="info">
						<text decode="true">{{info.information}}</text>
					</view>
				</template>
				<view class="content" v-html="info.content">
				</view>
				<view class="contentImg" v-if="imgs.length">
					<imageAdaptation :imgList="imgs" :imgLayout1="imgLayout1" :imgLayout2="imgLayout2"
						:imgLayout3="imgLayout3" @previewImg="preview"></imageAdaptation>

					<q-previewImage ref="previewImage" :urls="imgs" @onLongpress="onLongpress" @open="open"
						@close="close"></q-previewImage>
				</view>
				<view class="time">
					{{info.time}}
				</view>
				<!-- <view class="btns">
				<view class="btn">
					<image src="../../static/images/103.png" mode=""></image>
					<text>分享</text>
				</view>
				<view class="btn">
					<image src="../../static/images/104.png" mode=""></image>
					<text>{{info.commentCount}}</text>
				</view>
				<view class="btn" @click="praise(info)">
					<image v-if="!info.isLike" src="../../static/images/105.png" mode=""></image>
					<image v-else src="../../static/images/105-no.png" mode=""></image>
					<text>{{info.likeCount}}</text>
				</view>
			</view> -->
			</view>
			<view class="right">
				<!-- <view class="focus">
					关注
				</view> -->
				<view class="dot" v-if="info.isDel" @click="show = true">
					<text></text>
					<text></text>
					<text></text>
				</view>

			</view>
		</view>

		<view class="pl">
			<view class="l">
				评论区
			</view>
			<view class="r">
				<view class="ll">
					点赞 {{info.likeCount}}
				</view>
				<view class="line">

				</view>
				<view class="ll">
					评论 {{info.commentCount}}
				</view>
			</view>
		</view>
		<h-comment-box :isLike="info.isLike" :likeCount="info.likeCount" :commentCount="info.commentCount"
			@refresh2="refresh2" :list="comment" @getLike="getLike" @sendComment="sendComment" v-model="inputVal"
			@changeIpt="changeIpt" @getMore="getMore" @refresh="refresh" ref="commontRef" @remove="remove"
			@bigLike="bigLike"></h-comment-box>
		<u-action-sheet :actions="list" :closeOnClickOverlay="true" :safeAreaInsetBottom="true" cancelText="取消"
			@select="selectClick" @close="show = false" :show="show"></u-action-sheet>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
		<login @change="getUserInfo" :isLoginPop="isLoginPop&&tabIndex!=1" class="my-select"></login>
	</view>
</template>

<script>
	import imageAdaptation from '@/components/images-adaptation/imageAdaptation.vue'
	import hCommentBox from "./h-comment-box/h-comment-box.vue"
	import login from '@/components/login/login.vue';
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		components: {
			imageAdaptation,
			hCommentBox,
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
			},
			comment: {
				type: Array,
				default: () => []
			},
			articleId: {
				type: String,
				default: ''
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
		data() {
			return {
				imgs: [],
				imgLayout1: [450, 450],
				imgLayout2: [220, 220],
				imgLayout3: [160, 160],
				show: false,
				list: [{
					name: '删除',
				}],
				inputVal: '',
				userInfo: {
					id: 999,
					name: 'h_comment',
					url: "https://cdn.uviewui.com/uview/template/SmilingDog.jpg",
					contentStr: '',
					isLike: false,
					likeNum: 0,
					date: "2023-06-01 12:00"
				},
				isLoginPop: false,
				tipMsg: '',
				isConfirm: false
			}
		},
		methods: {
			refresh() {
				this.$emit("refresh")
			},
			changeIpt(e) {
				this.inputVal = e
			},
			/**
			 * 点赞事件
			 * @param data {index: number, j: number}  index 一级评论的索引 j 回复的索引
			 * @param callback  调用点赞接口成功后，调用该方法更新点赞状态
			 */
			getLike(data, callback) {
				const {
					row_id,
					rows_id,
					isLike
				} = data
				// j 是给回复点赞，给一级评论点赞时，j 为undefined
				let praiseType, id, Api;
				if (typeof rows_id === 'number') {
					praiseType = 'REPLY'
					id = rows_id
				} else {
					praiseType = 'COMMENT'
					id = row_id
				}
				Api = isLike ? '/nostalgia/article/praise' : '/nostalgia/article/cancel/praise'
				this.$myRequest({
					url: Api,
					withToken: true,
					method: 'put',
					data: {
						id,
						praiseType
					}
				}).then(res => {
					if (res.data.code == "200") {
						callback();
					} else if (res.data.code == "10006") {
						console.log("报错了：" + res.data.code)
						this.isLoginPop = true;
					}else {
						this.tipMsg = res.data.msg;
						this.$refs.elm.showDialog();
					}
					
				})
			},
			refresh2(id) {
				this.$emit("refresh2", id)
			},
			/**
			 * 发送评论事件
			 * @param data {index: number, index_s: number, row: {一级评论的信息}, row_s: { 点击当前回复的信息} }
			 * @param callback
			 */
			sendComment(data, callback) {
				// 给一级评论回复时，row_s 为空对象 {}
				// 判断是否有回复的人，有的话，就是回复的回复，没有就是回复的一级评论
				if (typeof data.row_s.id == 'number') {
					this.$myRequest({
						url: `/nostalgia/article/reply/add`,
						withToken: true,
						method: 'post',
						data: {
							articleCommentId: data.row.id,
							commentReplyId: data.row_s.id,
							content: this.inputVal
						}
					}).then(res => {
						if (res.data.code == "200") {
							this.$refs.commontRef.close()
							this.inputVal = ''
							// this.$emit("refresh2",data.row.id)
							this.$emit("refresh")
						} else if (res.data.code == "10006") {
							console.log("报错了：" + res.data.code)
							this.isLoginPop = true;
						}else {
							this.tipMsg = res.data.msg;
							this.$refs.elm.showDialog();
						}
						
					})
				} else if (typeof data.row.id == 'number') {
					this.$myRequest({
						url: `/nostalgia/article/reply/add`,
						withToken: true,
						method: 'post',
						data: {
							articleCommentId: data.row.id,
							content: this.inputVal
						}
					}).then(res => {
						if (res.data.code == "200") {
							this.$refs.commontRef.close()
							this.inputVal = ''
							// this.$emit("refresh2",data.row.id)
							this.$emit("refresh")
						} else if (res.data.code == "10006") {
							console.log("报错了：" + res.data.code)
							this.isLoginPop = true;
						}else {
							this.tipMsg = res.data.msg;
							this.$refs.elm.showDialog();
						}
						
					})
				} else {
					this.$myRequest({
						url: `/nostalgia/article/comment/add`,
						withToken: true,
						method: 'post',
						data: {
							articleId: this.articleId,
							content: this.inputVal
						}
					}).then(res => {
						if (res.data.code == "200") {
							this.$refs.commontRef.close()
							this.inputVal = ''
							this.$emit("refresh")
						} else if (res.data.code == "10006") {
							console.log("报错了：" + res.data.code)
							this.isLoginPop = true;
						}else {
							this.tipMsg = res.data.msg;
							this.$refs.elm.showDialog();
						}
						
					})
				}
				return
				// 评论接口发送成功后，把当前评论的信息传给 callback(新的评论信息，接口返回) 用于更新评论列表
				callback(params)

				// 关闭评论框
				this.$refs.commontRef.close();
			},
			/**
			 * 获取更多评论
			 * @param data {index: number, row: {一级评论的信息}}
			 * @param callback 调用获取更多回复接口成功后，调用该方法更新回复列表，会自动关闭加载动画
			 */
			getMore(data, callback) {
				// 调用 获取更多评论接口，成功后调用 callback(【新的评论信息，接口返回，可是对象】) 用于更新评论列表
				let pageSize = 3
				let pageIndex = 1
				let temp = 0
				if (data.row.replyList.length >= 1) {
					temp = (data.row.replyList.length - 1) / 3;
				}
				pageIndex = Math.floor(temp) + 1
				this.$myRequest({
					url: `/nostalgia/article/reply/page/${data.row.id}`,
					withToken: true,
					method: 'GET',
					data: {
						pageIndex,
						pageSize
					}
				}).then(res => {
					let arr = res.data.data.rows.map(item => {
						return {
							...item,
							pid: data.row.id
						}
					})
					callback(arr);
				})
			},
			/**
			 * 删除评论
			 */
			remove(data, callback) {
				// 调用删除接口

				// 调用删除接口成功后，调用 callback() 用于更新评论列表
				callback()

			},





			goDetail() {
				uni.navigateTo({
					url: "/pagescommunity/dynamicDetail/dynamicDetail?id=" + this.info.id
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
						success: function(data) {},
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
				uni.showActionSheet({
					itemList: ['转发给朋友', '保存到手机'],
					success: function(res) {},
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
				// uni.hideTabBar()
				// uni.setNavigationBarColor({
				//   frontColor: '#000000', // 设置前景色为黑色
				//   backgroundColor: '#000000', // 设置背景色为黑色
				// })
				// this.videoShow = false 
			},
			close() { //监听组件隐藏 （显示TabBar和NavigationBar，显示video原生组件）
				// uni.showTabBar()
				// uni.setNavigationBarColor({
				//   frontColor: '#ffffff', // 设置前景色为白色
				//   backgroundColor: '#000000', // 设置背景色为黑色
				// })
				// this.videoShow = true
			},
			bigLike() {
				this.$emit("bigLike")
			},
			getUserInfo(e) {
				if (e == 0) {
					this.isLoginPop = false;
					return;
				}
				// #ifdef MP-WEIXIN
				try {
					wx.getUserProfile({
						desc: '用于完善会员资料',
						success: resinfo => {
							wx.login({
								success: res => {
									if (res.code) {
										console.log(res.code, resinfo);
										this.setCode(res.code, resinfo);
									} else {}
								},
								fail: err => {}
							});
						},
						fail: errinfo => {
							this.setCode(this.generateRandomString(10), 'null');
						}
					});
				} catch {

					wx.getUserInfo({
						success: resinfo => {
							wx.login({
								success: res => {
									if (res.code) {
										console.log(res.code, resinfo);
										this.setCode(res.code, resinfo);
									} else {}
								},
								fail: err => {}
							});
						},
						fail: errinfo => {}
					});
				}
				// #endif
				// #ifndef MP-WEIXIN
				this.setCode("ip", 'null');
				// #endif
			},
			async setCode(code, resinfo) {
				const res = await this.$myRequest({
					url: 'token/wxAppletLogin',
					data: {
						code: code
					},
					method: 'POST'
				});
				console.log(res, 'delshoucang');
				var obj = {
					code: code,
					state: res.data.code,
					nickName: resinfo != 'null' ? resinfo.userInfo.nickName : "匿名用户"
				};
				uni.setStorageSync('verification', obj);
				if (res.data.code == 200) {
					this.isLoginPop = false;
					this.isConfirm = true;
					this.tipMsg = "登录成功";
					this.$refs.elm.showDialog();

					var info = {
						birthday: res.data.data.info.birthday,
						city: res.data.data.info.city,
						gender: res.data.data.info.gender,
						headPortrait: res.data.data.info.headPortrait,
						id: res.data.data.info.id,
						nickName: res.data.data.info.nickName
					};
					uni.setStorageSync('info', info);
					uni.setStorageSync('token', res.data.data.token);
				} else if (res.data.code == 11002) {
					this.isLoginPop = false;
					uni.reLaunch({
						url: '/pagesintroduction/selfIntroduction?code=' + code
					});
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			generateRandomString(length) {
				let result = uni.getStorageSync('touristopenid');
				if (result != null&&result!="") {
					return result;
				}else{
					result='';
				}
				const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'; // 包含大小写字母和数字的所有字符集合
				
				for (let i = 0; i < length; i++) {
					const randomIndex = Math.floor(Math.random() * characters.length);
					result += characters[randomIndex];
				}
				var now = new Date();
				var year = now.getFullYear(); // 年份
				var month = (now.getMonth() + 1).toString().padStart(2, '0'); // 月份（注意要加上1）
				var day = now.getDate().toString().padStart(2, '0'); // 天数
				var hours = now.getHours().toString().padStart(2, '0'); // 小时
				var minutes = now.getMinutes().toString().padStart(2, '0'); // 分钟
				var seconds = now.getSeconds().toString().padStart(2, '0'); // 秒数
				result = "touristopenid" + result + (+year + month + day + hours + minutes + seconds);
				uni.setStorageSync('touristopenid', result);
				return result;
			},
			confirm() {
				this.isConfirm = false;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.item {
		display: flex;
		justify-content: space-between;
		margin-top: 40rpx;
		padding: 32rpx;

		.left {
			image {
				width: 68rpx;
				height: 70rpx;
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
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			align-items: flex-end;
			padding-bottom: 48rpx;

			.focus {
				width: 120rpx;
				height: 58rpx;
				border-radius: 42rpx 42rpx 42rpx 42rpx;
				border: 1rpx solid #9FB5EB;
				font-family: PingFang HK, PingFang HK;
				font-weight: 600;
				font-size: 28rpx;
				color: #86A6F3;
				line-height: 58rpx;
				text-align: center;
			}

			.dot {
				display: flex;
				padding: 15rpx 0;

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

	.pl {
		display: flex;
		padding: 0 32rpx;
		font-family: PingFang HK, PingFang HK;
		font-size: 28rpx;
		color: #434343;

		.r {
			margin-left: auto;
			display: flex;
			align-items: center;
			font-size: 28rpx;
			color: #8F97A4;

			.line {
				width: 2rpx;
				height: 18rpx;
				background: #EEEEEE;
				margin: 0 20rpx;
			}
		}
	}
</style>
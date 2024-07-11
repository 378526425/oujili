<template>
	<view class="">
		<view class="home-page-container" style="height: 100vh;" v-if="nologin==true" @touchmove.stop.prevent="() => {}">
			<image class="nologi-bgc-imga" src="../../static/images/bg-c.png" mode="aspectFill"></image>
			<u-navbar class="myNav" :placeholder="true">
				<view class="u-nav-slot" slot="left">
					<view class="text">消息</view>
				</view>
			</u-navbar>
		
			<view class="nologi-noimg">
				<image src="../../static/images/nologin.png" style="width: 100%;height: 100%;" mode="aspectFill"></image>
			</view>
			<view class="nologi-text"> 登录后才能查看消息哦 </view>
			<view class="nologi-nobutton" @click="getUserInfo"> 点此登录 </view>
		</view>
		<view v-if="nologin==false">
			<view class="box" v-if="!isShow">
				<u-navbar :bgColor="bgColor" :placeholder="true">
					<view class="u-nav-slot" slot="left">
						<view class="text">消息</view>
					</view>
				</u-navbar>
				<view class="msgs">
					<u-swipe-action :autoClose="true" :key="key">
						<u-swipe-action-item :show="isOpened" :auto-close="false" :options="option" v-for="(i,index) in list" :key="i.sendUserId"
							@click="delClick">
							<view class="item" @click="actionClick(i)">
								<image class="ava" :src="i.headPortrait"  mode="aspectFill"></image>
								<u-badge customStyle="margin-left:11%;margin-bottom: 10%;" style="margin-bottom: ;" v-if="i.unreadCount!==null" max="99" :value="i.unreadCount" :absolute="true">
								</u-badge>
							<!-- 	<view class="ava" :style="{backgroundImage: 'url(' + i.headPortrait + ')'}">
									<u-badge v-if="i.unreadCount!==null" max="99" :value="i.unreadCount" :absolute="true">
									</u-badge>
								</view> -->
			
								<view class="r">
									<view class="title">
										<text class="name">{{i.nickName}}</text>
										<text class="time">{{i.latelyTime.slice(10,16)}}</text>
										
									</view>
									<view class="msg">
										<view v-if="i.messageDescribeFormat=='text'">
											<rich-text :nodes="i.messageDescribe"></rich-text>
										</view>
										<view v-if="i.messageDescribeFormat=='emoji'">
											[动画表情]
										</view>
										<!-- 图片消息 -->
										<view v-if="i.messageDescribeFormat=='image'">
											[图片]
										</view>
									</view>
									
								</view>
							</view>
						</u-swipe-action-item>
					</u-swipe-action>
				</view>
			</view>
			<template v-if="isShow">
				<u-navbar bgColor="#fff" :placeholder="true">
					<view class="u-nav-slot" slot="left">
						<view class="text">消息</view>
					</view>
				</u-navbar>
				<empty height="calc(100vh - 100px)" />
			</template>
		</view>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	import empty from '@/components/empty.vue'
	import {
		WS
	} from '@/util/wsConfig.js'
	export default {
		components: {
			empty,
			ELM
		},
		data() {
			return {
				key:1,
				bgColor: '#F4F5F9',
				option: [{
					text: '删除',
					style: {
						backgroundColor: '#FF8080'
					}
				}],
				list:[],
				total: 0,
				params: {
					pageIndex: 1,
					pageSize: 10
				},
				count: 0,
				isShow: false,
				ownerId: null,
				timeoutObj: null,
				reConnect: true,
				socketTask: null,
				nologin: '',
				tipMsg: '',
				isConfirm: false
			}
		},
		onShow() {
			if (uni.getStorageSync('info')) {
				this.nologin = false
				this.ownerId = uni.getStorageSync('info') != null ? uni.getStorageSync('info').id : null;
				this.getMsgList(true);
				this.connectSocketInit();
			} else {
				this.nologin = true
			}
			
		},
		// 关闭websocket【必须在实例销毁之前关闭,否则会是underfined错误】
		onHide () {
			this.reConnect=true;
			this.closeSocket();
			clearInterval(this.timeoutObj); //销毁定时器
		},
		onReachBottom() {
			let {
				pageIndex,
				pageSize
			} = this.params
			if (pageIndex * pageSize < this.total) {
				this.params.pageIndex += 1;
				this.getMsgList()
			}
		},
		methods: {
			async getUnRead() {
				const res = await this.$myRequest({
					url: `/message/un/read`,
					withToken: true,
					method: 'GET'
				});
				this.count = res.data.data
				uni.setTabBarBadge({ //显示数字
					index: 1, //tabbar下标
					text: `${res.data.data}` //数字
				})
			},
			delClick({
				index,
				name
			}) {
				console.log(1111);
				console.log(index);
				this.$myRequest({
					url: `/message/list/delete/${this.list[index].sendUserId}`,
					withToken: true,
					method: 'DELETE'
				}).then(res => {
					this.referesh()
					this.status = 'close';
				})

			},
			actionClick(i) {
				this.tipMsg = "开源版暂未开放，敬请期待！如需旗舰版，可联系作者微信（MMRWXM）咨询";
				this.$refs.elm.showDialog();
			},
			async getMsgList(isReload) {
				let page= this.params;
				if(isReload){
					page.pageIndex=1;
					if(this.list!=null&&this.list.length>page.pageSize)
					{
						page.pageSize=this.list.length;
					}
					
				}
				const res = await this.$myRequest({
					url: `/message/list`,
					withToken: true,
					data: page,
					method: 'GET'
				});
				if (isReload) {
					this.list = res.data.data.rows;
				} else {
					this.list = this.list.concat(
						res.data.data.rows
					);
				}
				this.total = res.data.data.totalCount
				if (this.total <= 0) {
					this.isShow = true
				} else {
					this.isShow = false
				}
				this.key+=1
				this.$nextTick(()=>{
					this.$forceUpdate()
				})
				this.getUnRead();
			},
			connectSocketInit: function() {
				let self = this;
				if (self.reConnect) {
					console.log("连接socket");
					self.socketTask = uni.connectSocket({
						url: WS,
						success(data) {
							console.log("连接socket成功");
						},
						fail(data) {
							console.log("连接socket失败");
						}
					});
					uni.onSocketOpen(function(res) {
						//监听WebSocket连接打开事件
						let msgData = {
							messageType: "CONNECT",
							info: self.ownerId
						};
						console.log("发送连接请求");
						self.websocketSendMessage(msgData);
					});
				}

			},
			// 关闭websocket【离开这个页面的时候执行关闭】
			closeSocket() {
				let self = this;
				uni.closeSocket({
					success(res) {
						console.log("关闭成功", res);
					},
					fail(err) {
						console.log("关闭失败", err);
					},
				});

			},
			websocketSendMessage(msgData) {
				let self = this;
				uni.sendSocketMessage({
					data: JSON.stringify(msgData),
					success() {
						if (msgData.messageType == 'CONNECT') {
							console.log("建立连接成功 启动监听" + JSON.stringify(msgData));
							self.websocketOnMessage();
						} else {
							console.log("消息发送成功" + JSON.stringify(msgData));
						}

					},
					fail() {
						console.log("发送失败，重新建立连接" + JSON.stringify(msgData));
						setTimeout(() => {
							self.websocketSendMessage(msgData);
						}, 1000)

					}
				});
			},
			websocketOnMessage() {
				let self = this;
				uni.onSocketMessage((res) => {
					console.log("监听到socket信息：", res);
					if ("CONNECT_SUCCESS" == res.data) {
						//建立连接成功后开始心跳
						this.reset(); // 检测心跳reset,防止长时间连接导致连接关闭
					} else {

						//收到消息
						let msgData = JSON.parse(res.data);
						console.log("收到消息：", JSON.stringify(msgData));
						//消息应答
						this.answerMessage(msgData.msgNo);
						this.referesh();
					}
				});
			},
			// 启动心跳 start
			start() {
				let self = this;
				self.timeoutObj = setInterval(function() {
					uni.sendSocketMessage({
						data: '{"messageType":"ALIVE"}',
						success() {
							console.log("消息列表心跳");
							self.reConnect = false;
						},
						fail() {
							console.log("心跳失败重新连接");
							setTimeout(() => {
								self.reConnect = true;
								self.connectSocketInit();
							}, 1000)
						}
					});
				}, 1000);
			},
			// 检测心跳reset
			reset() {
				clearInterval(this.timeoutObj);
				this.start(); // 启动心跳
			},
			referesh() {
				// 刷新消息列表
				
				this.getMsgList(true);
			},
			answerMessage(MsgNo) {
				let self = this;
				uni.sendSocketMessage({
					data: '{"messageType":"ANSWER","info":"' + MsgNo + '"}',
					success() {
						console.log("消息应答：" + MsgNo);
					},
					fail() {
						console.log("消息应答失败");
					}
				});
			},
			//登录
			getUserInfo() {
			// #ifdef MP-WEIXIN
			   try {
			   	wx.getUserProfile({
			   		desc: '用于完善会员资料',
			   		success: (resinfo) => {
			   			wx.login({
			   				success: (res) => {
			   					if (res.code) {
			   						console.log(res.code, resinfo);
			   						this.setCode(res.code, resinfo);
			   					} else {}
			   				},
			   				fail: (err) => {}
			   			})
			   		},
			   		fail: (errinfo) => {
						this.setCode(this.generateRandomString(10), 'null');
					}
			   	})
			   } catch {
			   	wx.getUserInfo({
			   		success: (resinfo) => {
			   			wx.login({
			   				success: (res) => {
			   					if (res.code) {
			   						console.log(res.code, resinfo);
			   						this.setCode(res.code, resinfo);
			   					} else {}
			   				},
			   				fail: (err) => {}
			   			})
			   		},
			   		fail: (errinfo) => {}
			   	})
			   }
			// #endif
			// #ifndef MP-WEIXIN
			this.setCode("ip", 'null');
			// #endif
				
			},
			async setCode(code, resinfo) {
			
				console.log(code)
				const res = await this.$myRequest({
					url: 'token/wxAppletLogin',
					data: {
						code: code
					},
					method: 'POST',
				});
				console.log(res, 'delshoucang');
				var obj = {
					code: code,
					state: res.data.code,
					nickName: resinfo!='null'?resinfo.userInfo.nickName:"匿名用户"
				}
				uni.setStorageSync('verification', obj);
				if (res.data.code == 200) {
					this.tipMsg = "登录成功";
					this.$refs.elm.showDialog();
					
					var info = {
						birthday: res.data.data.info.birthday,
						city: res.data.data.info.city,
						gender: res.data.data.info.gender,
						headPortrait: res.data.data.info.headPortrait,
						id: res.data.data.info.id,
						nickName: res.data.data.info.nickName,
					}
					uni.setStorageSync('info', info);
					uni.setStorageSync('token', res.data.data.token);
					this.ownerId = res.data.data.info.id;
					this.getMsgList(true);
					this.connectSocketInit();
				} else if (res.data.code == 11002) {
			
					uni.reLaunch({
						url: "/pagesintroduction/selfIntroduction?code=" + code
					})
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
			}
		}
	}
</script>

<style lang="scss" scoped>
	view {
		box-sizing: border-box;
	}

	.u-nav-slot {
		display: flex;
		align-items: center;

		.text {
			height: 44rpx;
			text-align: center;
			font-size: 32rpx;
			font-family: PingFang SC-Bold, PingFang SC;
			font-weight: bold;
			color: #000000;
			margin-left: 315rpx;
			line-height: 44rpx;
		}
	}

	.box {
		padding: 0 32rpx;
		min-height: 100vh;
		background-color: #F4F5F9;

		.u-nav-slot {
			display: flex;
			align-items: center;

			.text {
				height: 44rpx;
				text-align: center;
				font-size: 32rpx;
				font-family: PingFang SC-Bold, PingFang SC;
				font-weight: bold;
				color: #000000;
				margin-left: 315rpx;
				line-height: 44rpx;
			}
		}

		.msgs {
			/deep/ .u-swipe-action-item {
				border-radius: 24rpx;
				margin: 20rpx 0;

				.item {
					height: 160rpx;
					background: #FFFFFF;
					padding: 30rpx;
					display: flex;
					justify-content: space-between;
					align-items: center;
					border-radius: 24rpx;
					overflow: hidden;
					.ava {
						width: 102rpx;
						height: 102rpx;
						border-radius: 50%;
						//background-image: url('../../static/logo.png');
						background-size: 100% 100%;

						.u-badge {
							left: 95rpx;
						}
					}

					.r {
						width: 498rpx;

						.title {
							display: flex;
							justify-content: space-between;
							align-items: flex-end;

							.name {
								width: 460rpx;
								overflow: hidden; //超出隐藏
								white-space: nowrap; //不折行
								text-overflow: ellipsis; //溢出显示省略号
								font-size: 32rpx;
								font-family: PingFang SC-Bold, PingFang SC;
								font-weight: bold;
								color: #333333;
							}

							.time {
								font-size: 24rpx;
								font-family: PingFang SC-Regular, PingFang SC;
								font-weight: 400;
								color: #C9C9D0;
							}
							
						}

						.msg {
							width: 100%;
							margin-top: 20rpx;
							font-size: 28rpx;
							font-family: PingFang SC-Regular, PingFang SC;
							font-weight: 400;
							color: #434343;
							overflow: hidden; //超出隐藏
							white-space: nowrap; //不折行
							text-overflow: ellipsis; //溢出显示省略号
						}
					}
				}
			}

		}
	}
	
	.nologi-bgc-imga {
		position: absolute;
		top: -120rpx;
		left: 0rpx;
		width: 100%;
		height: 100%;
		pointer-events: none;
	}
	.nologi-noimg {
		width: 338rpx;
		height: 338rpx;
		margin: 0 auto;
		padding-top: 200rpx;
		box-sizing: content-box;
	}
	.nologi-text {
		width: 100%;
		height: 44rpx;
		font-size: 32rpx;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		color: #C9C9D0;
		line-height: 44rpx;
		text-align: center;
		margin-top: 104rpx;
	}
	
	.nologi-nobutton {
		width: 392rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 28rpx;
		margin: 0 auto;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		color: #415C9E;
		margin-top: 40rpx;
	
	}
	.home-page-container{
		::v-deep .u-navbar__content{
			background: none!important;
			background-color: none!important;
		}
		::v-deep .u-status-bar{
			background: none!important;
			background-color: none!important;
		}
	}
	.msgbadge{
		margin-left: 50rpx;
	}
</style>

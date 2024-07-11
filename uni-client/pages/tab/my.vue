<template>
	<view class="" style="background: #F4F5F9;">
		<view class="home-page-container" style="min-height: 100vh;" @touchmove.stop.prevent="() => {}"
			v-if="nologin==true">
			<image class="bgc-imga" src="../../static/images/bg-c.png" mode="aspectFill"></image>
			<u-navbar leftText=" " title=" " :bgColor="bgColor" :fixed="false" :safeAreaInsetTop="true"
				:placeholder="true">
				<view class="u-nav-slot" slot="left">
					<view class="text"> </view>
				</view>
				<view class="u-nav-slot" slot="center">
					<view class="texts"> 我的 </view>
				</view>
			</u-navbar>

			<view class="noimg">
				<image src="../../static/images/nologin.png" style="width: 100%;height: 100%;" mode="aspectFill">
				</image>
			</view>
			<view class="text"> 登录后才能查看我的哦 </view>
			<view class="nobutton" @click="getUserInfo"> 点此登录 </view>
		</view>
		<view class="" v-if="nologin==false && flag == true">
			<image class="bgc-imga" src="../../static/images/my2.png" mode="aspectFill"></image>
			<view class="logion">

				<image class="bgc-imgas" src="../../static/images/my22.png" mode="aspectFill"></image>
				<view class="sss">

					<view class="logo">
						<image :src="info.headPortrait" style="width: 100%;height: 100%;border-radius: 50%;"
							mode="aspectFill">
						</image>
						<view class="logola">
							<image src="../../static/images/myrz.png" class="cuo" mode="aspectFill"
								v-if="info.waitApprovedStatus=='EXAMINE' || info.waitApprovedStatus=='REFUSE'"></image>
							<image src="../../static/images/yrz.png" class="cuo" mode="aspectFill" v-else></image>
							<view class="shenhe">
								{{info.waitApprovedStatus=='EXAMINE' ? "审核中":info.waitApprovedStatus=='PASS'?"已认证":info.waitApprovedStatus=='REFUSE'?"已拒绝":''}}
							</view>
						</view>
					</view>
					<view class="names">
						<text v-if="info.nickName">{{info.nickName}}</text><text v-else>0</text>
					</view>
					<view class="div">
						<view class="diving" @click="gotolike(1)">
							<view class="divtast">
								<text v-if="info.ilike">{{info.ilike}}</text><text v-else>0</text>
							</view>
							<view class="divingname">
								我喜欢的
							</view>
						</view>
						<view class="diving" style="margin: 0 136rpx;" @click="gotolike(2)">

							<view class="divtast" v-if="info.likeMe==0">
								<text v-if="info.likeMe">{{info.likeMe}}</text><text v-else>0</text>
							</view>
							<view class="vimg" v-else>
								<image :src="info.likeMeheadPortrait" class="simg" mode="aspectFill"></image>
							</view>

							<view class="divingname">
								喜欢我的
							</view>
						</view>
						<view class="diving" @click="gotolike(3)">
							<view class="divtast">
								<text v-if="info.lovers">{{info.lovers}}</text><text v-else>0</text>
							</view>

							<view class="divingname">
								互相喜欢
							</view>
						</view>
					</view>
				</view>
			</view>




			<view class="box" v-if="dataflag">
				<view class="boxsing">
					<image src="../../static/images/indexlable.png"
						style="width: 42rpx;height: 42rpx;margin-left: 42rpx;;" mode="aspectFill">
					</image>
					<view class="boxsname">
						完成以下任务即可获得7日优先推荐
					</view>
				</view>
				<view class="boxsing" style="height: 52rpx;" v-if="numtwo<2">
					<view class="boxsname" style="margin-left: 42rpx;">
						双重认证
					</view>
					<view class="boxsnames" style="width: 88rpx;margin-left: 0rpx;">
						（{{numtwo}}/2）
					</view>
					<view class="boxsnames" style="margin-left: 0rpx;">
						配对成功率提升200%
					</view>
					<view class="boxsbutton" @click="gotomation">
						立即认证
					</view>
				</view>
				<view class="boxsing" style="height: 52rpx;" v-if="info.completionRatio < 80">
					<view class="boxsname" style="margin-left: 42rpx;">
						个人资料
					</view>
					<view class="boxsnames">
						资料完善80%以上双倍曝光
					</view>
					<view class="boxsbutton" @click="improveInformation">
						立即完善
					</view>
				</view>
				<view class="" style="height: 52rpx;">
				</view>
			</view>
			<view class="box" @click="goCommunity" v-if="(info.userArticleViewResponse.articleImg!==null&&info.userArticleViewResponse.articleImg.length)||info.userArticleViewResponse.articleContent!==null">
				<view class="boxsing">
					<image src="../../static/images/106.svg" style="width: 42rpx;height: 42rpx;margin-left: 42rpx;;"
						mode="aspectFill">
					</image>
					<view class="boxsname">
						我的动态
					</view>
				</view>
				<view :class="['imgs',info.userArticleViewResponse.articleImg.length==2?'twoTemp':'']" v-if="info.userArticleViewResponse.articleImg!==null&&info.userArticleViewResponse.articleImg.length">
					<image v-for="item in info.userArticleViewResponse.articleImg" :src="item" mode="aspectFill"></image>
				</view>
				<view class="strs" v-if="info.userArticleViewResponse.articleContent!==null">
					{{info.userArticleViewResponse.articleContent}}
				</view>
			</view>


			<view class="box" style="">
				<view class="box-bottom" v-for="(item,index) in button" :key="index" @click="whether(index)">
					<view class="box-bottom-left">
						<image :src="item.img" class="box-bottom-img" mode="aspectFill"></image>
						<view class="box-bottom-text">
							{{item.name}}
						</view>
						<view class="box-bottom-text-la" v-if="index == 0">
							{{info.goldBalance}}
						</view>
					</view>
					<view class="box-bottom-right">
						<view class="box-bottom-right-name" v-if="index != 2">
							{{item.la}}
						</view>
						<view class="box-bottom-right-name" v-else="index != 2">
							<u-button open-type="share" :hairline="false" :customStyle="{
								'font-size': '28rpx',
								'font-family': 'PingFang SC-Regular, PingFang SC',
								'font-weight': '400',
								'color': '#B7B7B7',
								'border':'none',
								
							}">
								{{item.la}}
							</u-button>

						</view>

						<image src="../../static/right.png" class="box-bottom-right-img" mode="aspectFill"></image>
					</view>
				</view>
			</view>
		</view>
		<ELM ref="elm" :msg="tipMsg"></ELM>
	</view>




</template>
<script>
	import {
		WS
	} from '@/util/wsConfig.js'
	import ELM from '../../components/elm-toast/index.vue';
	export default {
		components: {
			ELM
		},
		data() {
			return {
				flag: false,
				dataflag: true,
				nologin: '',
				img: this.$BASE_URL,
				bgColor: "rgba(255, 255, 255, 0)",
				button: [{
					name: "欧几币",
					num: 0,
					la: "立即充值",
					img: "../../static/images/mymoney.png",
				}, {
					name: "个人资料",
					num: '',
					la: "",
					img: "../../static/images/myren.png",
				}, {
					name: "推荐给好友",
					num: '',
					la: "免费获得欧几币",
					img: "../../static/images/myshare.png",
				}, {
					name: "设置",
					num: '',
					la: "",
					img: "../../static/images/myset.png",
				}],
				info: {
					userArticleViewResponse:{
						articleImg:null,
						articleContent:null
					}
				},
				numtwo: 0,
				ownerId: null,
				timeoutObj: null,
				reConnect: true,
				socketTask: null,
				tipMsg: ""
			};
		},
		onShow() {
			if (uni.getStorageSync('token')) {
				this.getUnRead()
				this.ownerId = uni.getStorageSync('info') != null ? uni.getStorageSync('info').id : null;
				this.connectSocketInit();
			}
			if (uni.getStorageSync('info')) {
				this.nologin = false
				this.personalCenter()
			} else {
				this.nologin = true
			}
		},
		onHide() {
			this.reConnect = true;
			this.closeSocket();
			clearInterval(this.timeoutObj); //销毁定时器
		},
		methods: {
			gotomation() {
				uni.navigateTo({
					url: "/pagesme/me/doubleauth"
				})
			},
			async getUnRead() {
				const res = await this.$myRequest({
					url: `/message/un/read`,
					withToken: true,
					method: 'GET'
				});
				uni.setTabBarBadge({ //显示数字
					index: 1, //tabbar下标
					text: `${res.data.data}` //数字
				})
			},
			gotolike(type) { //喜欢三个列表页面
				console.log("进来了")
				this.tipMsg = "开源版暂未开放，敬请期待！如需旗舰版，可联系作者微信（MMRWXM）咨询";
				this.$refs.elm.showDialog();

			},
			whether(i) {

				if (i == 0) {
					this.tipMsg = "开源版暂未开放，敬请期待！如需旗舰版，可联系作者微信（MMRWXM）咨询";
					this.$refs.elm.showDialog();
				} else if (i == 1) {
					this.improveInformation();
				} else if (i == 3) {
					uni.navigateTo({
						url: "/pagesme/set/set"
					})
				} else if (i == 2) {

				}
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
			improveInformation() { //完善信息
				uni.navigateTo({
					url: "/pagesme/me/improveInformation"
				})
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
					nickName: resinfo != 'null' ? resinfo.userInfo.nickName : "匿名用户"
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
					this.personalCenter()
					this.getUnRead()
					this.ownerId = res.data.data.info.id;
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

			async personalCenter() {
				const res = await this.$myRequest({
					url: '/nostalgia/fruser/personalCenter',
					withToken: true,
					method: 'GET',
				});
				if (res.data.code == 200) {
					this.info = res.data.data
					if(this.info.userArticleViewResponse.articleImg.length>3){
						this.info.userArticleViewResponse.articleImg = this.info.userArticleViewResponse.articleImg.splice(0,3)
					}
					this.flag = true
					var num = this.info.educationAuth == null || this.info.educationAuth == 'EXAMINE' || this.info
						.educationAuth == 'REFUSE' ? 0 : 1

					var nums = this.info.identityAuth == null || this.info.identityAuth == 'EXAMINE' || this.info
						.identityAuth == 'REFUSE' ? 0 : 1
					this.numtwo = num + nums
					console.log(num + nums)
					if (this.numtwo == 2 && this.info.completionRatio >= 80) {
						this.dataflag = false
					} else {
						this.dataflag = true
					}
					this.nologin = false
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();

				}
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
						this.getUnRead();
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
							console.log("个人中心心跳");
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
			goCommunity(){
				this.tipMsg = "开源版暂未开放，敬请期待！如需旗舰版，可联系作者微信（MMRWXM）咨询";
				this.$refs.elm.showDialog();
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
	.logion {
		width: 100%;
		height: 750rpx;
		position: relative;
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
	.imgs{
		display: flex;
		justify-content: space-between;
		padding: 30rpx 42rpx;
		image{
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
	.strs{
		padding: 30rpx 42rpx;
		font-size: 28rpx;
		color: #333333;
	}

	.box-bottom-text-la {

		font-size: 36rpx;
		font-family: 'PingFang SC-Heavy, PingFang SC';
		font-weight: 400;
		color: #5B83E8;
		line-height: 36rpx;
		margin-left: 20rpx;
	}

	.box-bottom-right-img {
		width: 11rpx;
		height: 20rpx;
		margin: 0 38rpx 0 14rpx;
	}

	.box-bottom-right-name {
		font-size: 28rpx;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		color: #B7B7B7;
		line-height: 28rpx;
	}

	.box-bottom-right {
		display: flex;
		align-items: center;
		justify-content: flex-end;
	}

	.box-bottom-text {
		font-size: 28rpx;
		// font-family: 'PingFang SC-Heavy, PingFang SC';
		// font-weight: 400;
		font-family: 'PingFang SC-Heavy';
		font-weight: bolder;
		color: rgba(45, 49, 50, 0.9);
		line-height: 28rpx;
		margin-left: 24rpx;
	}

	.box-bottom-img {
		height: 36rpx;
		width: 36rpx;
		margin-left: 42rpx;
	}

	.box-bottom-left {
		display: flex;
		align-items: center;
	}

	.box-bottom {
		width: 100%;
		height: 36rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 66rpx;
	}

	.box-bottom:first-child {
		padding-top: 30rpx;
	}

	.box-bottom:last-child {
		padding-bottom: 40rpx;
	}

	.boxsbutton {
		width: 152rpx;
		height: 52rpx;
		background: #E7EDFC;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 24rpx;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		color: #446CD2;
		line-height: 52rpx;
		margin-left: auto;
		border-radius: 33rpx;
		margin-right: 42rpx;
	}

	.boxsnames {
		height: 34rpx;
		font-size: 24rpx;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		line-height: 34rpx;
		margin-left: 18rpx;
		color: rgba(45, 49, 50, 0.9);

	}

	.boxsname {
		height: 40rpx;
		font-size: 28rpx;
		font-family: 'PingFang SC-Heavy';
		font-weight: bolder;
		color: rgba(45, 49, 50, 0.9);
		line-height: 40rpx;
		margin-left: 14rpx;
	}

	// .boxsname {
	// 	height: 42rpx;
	// 	font-size: 28rpx;
	// 	font-family: 'PingFang SC-Heavy, PingFang SC';
	// 	color: rgba(45, 49, 50, 0.9);
	// 	font-weight: 400;
	// 	line-height: 42rpx;
	// 	margin-left: 14rpx;
	// }

	.boxsing {
		display: flex;
		align-items: center;
		height: 42rpx;
		padding-top: 42rpx;
	}

	.box {
		width: 686rpx;
		background: #FFFFFF;
		border-radius: 24rpx;
		margin: 0 auto;
		margin-top: 32rpx;
		position: relative;

	}

	.divingname {
		width: 96rpx;
		height: 24rpx;
		font-size: 24rpx;
		font-family: 'Source Han Sans CN-Regular, Source Han Sans CN';
		font-weight: 400;
		color: #B1B4C5;
		line-height: 24rpx;
		margin-top: 30rpx;
	}

	.simg {
		width: 100%;
		height: 100%;
		border-radius: 50%;
		filter: blur(4rpx);
		transform: translate3d(0, 0, 0);

	}

	.vimg {
		border: 5rpx solid #CCD9EF;
		width: 96rpx;
		height: 96rpx;
		margin-top: 30rpx;
		border-radius: 50%;
		box-sizing: border-box;
	}

	.divtast {
		width: 96rpx;
		height: 96rpx;
		margin-top: 30rpx;
		display: flex;

		font-size: 40rpx;
		font-family: 'Arial-Regular, Arial';
		font-weight: 400;
		color: #000000;
		line-height: 40rpx;
		justify-content: center;
		align-items: center;
	}

	.diving {
		width: 102rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
	}

	.div {
		width: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-top: 50rpx;

	}

	.sss {
		position: absolute;
		width: 100%;
		height: 100%;
		left: 0rpx;
	}

	.names {
		width: 100%;
		text-align: center;
		height: 44rpx;
		font-size: 32rpx;
		// font-weight: bold;
		// font-family: 'PingFang SC-Bold, PingFang SC';
		font-family: 'Arial-Regular, Arial';
		font-weight: 600;
		color: #333333;
		line-height: 44rpx;
		margin-top: 28rpx;
	}

	.shenhe {
		height: 28rpx;
		font-size: 20rpx;
		font-family: 'PingFang SC-Bold, PingFang SC';
		font-weight: bold;
		color: #86A6F3;
		margin-left: 8rpx;
		line-height: 28rpx;
	}

	.cuo {
		width: 20rpx;
		height: 20rpx;
	}

	.logo {
		width: 184rpx;
		height: 184rpx;
		border: 6rpx solid #CCD9EF;
		margin: 0 auto;
		border-radius: 50%;
		margin-top: 186rpx;
		position: relative;

		.logola {
			width: 126rpx;
			height: 36rpx;
			background: rgba(222, 231, 252, 1);
			border-radius: 23rpx;
			border: 2rpx solid #86A6F3;
			position: absolute;
			left: 30rpx;
			bottom: -18rpx;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}

	.texts {

		height: 50rpx;
		font-size: 32rpx;
		font-family: 'PingFang SC-Bold, PingFang SC';
		// font-weight: bold;
		color: #000000;
		line-height: 50rpx;
	}

	.nobutton {
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

	.text {
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

	.noimg {
		width: 338rpx;
		height: 338rpx;
		margin: 0 auto;
		padding-top: 200rpx;
	}

	page {
		height: 100%;
		background: linear-gradient(180deg, #F4F5F9 0%, #F4F5F9 100%);
	}


	.bgc-img {
		position: absolute;
		top: -80rpx;
		left: 40rpx;
		width: 670rpx;
		pointer-events: none;
		height: 100%;
	}

	.bgc-imga {
		position: absolute;
		top: -100rpx;
		left: 0rpx;
		width: 100%;
		height: 100%;
		pointer-events: none;
	}

	.bgc-imgas {
		position: absolute;
		top: 0rpx;
		left: 0rpx;
		width: 100%;
		height: 100%;
	}
</style>
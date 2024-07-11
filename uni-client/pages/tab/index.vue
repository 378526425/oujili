<template>
	<view :class="{ popUpShow: showLikePop }">
		<view class="pageanima" v-if="isInit" :class="{ pagePop: showAnima }">
			<!-- <view class="div" style="height: 100%;background: linear-gradient(to bottom, #F4F5F9, #FFFFFF);"> -->
			<view class="div" style="height: 100%;background: #F4F5F9;">
				<!-- <image class="bgc-imga" src="../../static/images/home/home.png" mode="aspectFill"></image> -->
				<!-- <u-navbar leftText=" " title=" " :bgColor="bgColor" :fixed="false" :safeAreaInsetTop="true" :placeholder="true"> -->
				<u-navbar :bgColor="bgColor" :placeholder="true">
					<view class="u-nav-slot" slot="left" @click="screen">
						<image src="../../static/images/screen.png" class="img" mode="aspectFill"></image>
						<view class="text">筛选</view>
					</view>
				</u-navbar>

				<view class="">
					<view class="box">
						<view class="swiper">
							<ls-swiper :list="userData.imgList" imgKey="" :loop="true" :dots="true" :autoplay="true"
								:height="340" />
							<view class="hint" v-if="userData.userAuthStatus!=null">
								<image src="../../static/images/auth.png" mode="aspectFill"></image>
								<view class="t">
									{{
											userData.userAuthStatus == 'IdentityAuth'
												? '实名认证'
												: userData.userAuthStatus == 'EducationAuth'
												? '学历认证'
												: userData.userAuthStatus == 'DOUBLE'
												? '双重认证'
												: '已认证'
										}}
								</view>
							</view>

						</view>

						<view class="box-jj">
							<view class="box-jj-ta">
								<view class="box-jj-ta-left">
									<image src="../../static/images/yrz.png" class="img" mode="aspectFill"></image>
									<view class="te">{{ userData.school ? userData.school : '' }}</view>
									<view class="xian"></view>
									<view class="te">
										{{
											userData.education == 'Specialty'
												? '专科'
												: userData.education == 'Undergraduate'
												? '本科'
												: userData.education == 'Master'
												? '硕士'
												: userData.education == 'Doctor'
												? '博士'
												: ''
										}}
									</view>
								</view>
								<view class="text2">·
									{{ userData.educationalType == 'FullTime' ? '全日制' : userData.education == 'NOFullTime' ? '非全日制' : '' }}
								</view>
							</view>
						</view>

						<view class="box-name">
							<view class="name">{{ userData.nickName ? userData.nickName : '' }}</view>
							<image src="../../static/xiugai/nv.png" mode="aspectFill" v-if="userData.gender=='FEMALE'">
							</image>
							<image src="../../static/images/like/male.png" mode="aspectFill" v-else></image>
						</view>
						<view class="box-bq">
							<view class="">
								{{ userData.age ? userData.age : '' }}岁 · {{ userData.height ? userData.height : '' }}cm
								· {{ userData.constellation ? userData.constellation : '' }}
							</view>
							<view class="xian"></view>
							<view class="">{{ userData.city ? userData.city : '' }}</view>
						</view>
						<view class="ling" v-if="userData.emotional">
							<image src="../../static/images/indexlable.png" class="img" mode="aspectFill"></image>
							<view class="text">{{
								userData.emotional == 'Single'
									? '正在寻觅'
									: userData.emotional == 'Love'
									? '恋爱中'
									: ''
							}}</view>
						</view>

						<view class="line">
							<view class="sm-line" v-for="(item, index) in 12" :key="item"></view>
						</view>
						<view class="circle"></view>
						<view class="circles"></view>

						<view class="lable">
							<view class="lable-div" style="margin-right: 20rpx;">
								职业：{{ userData.profession ? userData.profession : '' }}</view>
							<view class="lable-div" style="background-color: #FFE3DE;" v-if="userData.annualSalary">
								年薪：{{
									userData.annualSalary == 'Less_Ten'
										? '小于10w'
										: userData.annualSalary == 'Ten_Two'
										? '10w到20w'
										: userData.annualSalary == 'Two_Three'
										? '20w到30w'
										: userData.annualSalary == 'Three_Five'
										? '30w到50w'
										: userData.annualSalary == 'Five_Hundred'
										? '50w到100w'
										: userData.annualSalary == 'Greater_Hundred'
										? '大于100w'
										: userData.annualSalary == 'Secrecy'
										? '保密'
										: ''
								}}
							</view>
						</view>
						<view class="lable" style="margin-top: 24rpx;" v-if="userData.marriage">
							<view class="lable-div" style="background-color: #F2E8F9;">
								婚姻状况：{{ userData.marriage == 'Unmarried' ? '未婚' : userData.Divorce == 'Ten_Two' ? '离异' : userData.Widow == 'Two_Three' ? '丧偶' : '' }}
							</view>
						</view>
						<view class="" style="height: 44rpx;"></view>
					</view>
					<view class="safe-bottom">
						<view class="card" style="height: auto;" @click="goDynamicState"
							v-if="(userData.userArticleViewResponse.articleImg!==null&&userData.userArticleViewResponse.articleImg.length)||userData.userArticleViewResponse.articleContent!==null">
							<view class="content">
								<view class="have_image">
									<image src="@/static/images/106.svg"
										mode="aspectFill">
									</image>
									<view class="title" style="font-family: SC-Bold;">
										{{userData.gender!='MALE'?'她':'他'}}的动态
									</view>
								</view>
								<view :class="['imgs',userData.userArticleViewResponse.articleImg.length==2?'twoTemp':'']" v-if="userData.userArticleViewResponse.articleImg!==null&&userData.userArticleViewResponse.articleImg.length">
									<image v-for="item in userData.userArticleViewResponse.articleImg" :src="item"
										mode="aspectFill">
									</image>
								</view>
								<view class="strs" v-if="userData.userArticleViewResponse.articleContent!==null">
									{{userData.userArticleViewResponse.articleContent}}
								</view>
							</view>
						</view>
						<view class="card">
							<view class="content">
								<view class="have_image">
									<image src="../../static/images/indexabout.png" mode="aspectFill"></image>
									<view class="title" style="font-family: SC-Bold;">关于我自己</view>
								</view>
								<view class="one">{{ userData.aboutMe }}</view>
								<view class="one"></view>
							</view>
						</view>
						<view class="card">
							<view class="content">
								<view class="have_image">
									<image src="../../static/images/indexlove.png" mode="aspectFill"></image>
									<view class="title" style="font-family: SC-Bold;">兴趣爱好</view>
								</view>
								<view class="one">{{ userData.interest }}</view>
								<view class="one"></view>
							</view>
						</view>
						<view class="card">
							<view class="content">
								<view class="have_image">
									<image src="../../static/images/indexcharacter.png" mode="aspectFill"></image>
									<view class="title" style="font-family: SC-Bold;">心仪的Ta</view>
								</view>
								<view class="one">{{ userData.loveRequirement }}</view>
								<view class="one"></view>
							</view>
						</view>
						<view class="bottom" v-if="false">
							<view class="Personality_type">
								<view class="content">
									<view class="have_image">
										<image src="../../static/images/indexinterest.png" mode="aspectFill"></image>
										<view class="title">性格类型</view>
									</view>
									<view class="center">{{ userData.characterType.name }}</view>
								</view>
							</view>
							<view class="line">
								<view class="sm-line" v-for="(item, index) in 12" :key="item"></view>
							</view>
							<view class="circle"></view>
							<view class="circles"></view>
							<!-- <view class="Personality_content" style="height: auto;">
								<view class="item" v-for="(item, index) in userData.characterType.character"
									:key="index">
									<view class="head_text">{{ item.name }}（E）</view>
									<view class="center">
										<view class="inner1"></view>
									</view>
									<view class="tail_text">
										{{ item.name == '外向' ? '内向（I）' : item.name == '想象' ? '实际（S）' : item.name == '感性' ? '理性（T）' : item.name == '计划' ? '随性（P）' : '' }}
									</view>
								</view>

								<view class="" style="height: 76rpx;"></view>
							</view> -->
						</view>
						<!-- <view class="bo" @click="shareUser">
							<image src="../../static/images/share.png" class="simg" mode="aspectFill"></image>
							<u-button class="name" open-type="share">将{{userData.gender!='MALE'?'她':'他'}}推荐给好友</u-button>
							<view class="name" open-type="share">将{{userData.gender!='MALE'?'她':'他'}}推荐给好友</view>
						</view> -->
						<view class="bo" style="'border-radius':'24rpx'">
							<u-button class="name" open-type="share" :hairline="false" :customStyle="{
								'font-size': '28rpx',
								'font-family': 'PingFang SC-Heavy, PingFang SC',
								'font-weight': '800',
								'color': '#434343',
								'border':'none',
								
							}">
								<image src="../../static/images/share.png" class="simg" mode="aspectFill"></image>
								将{{userData.gender!='MALE'?'她':'他'}}推荐给好友
							</u-button>
							<!-- <view class="name">将{{userData.gender!='MALE'?'她':'他'}}推荐给好友</view> -->
						</view>
						<view class="fe">
							<view class="" style="height: 40rpx;display: flex;align-items: center;margin-top: 26rpx;">
								<view class="line"></view>
								<view class="text">IP属地：{{ userData.city!=null?userData.city:city }}</view>
								<view class="line"></view>
							</view>
						</view>
					</view>
				</view>
			</view>
			<!-- <view class="footer-fixed" :class="{ 'safe-bottom': !showTabbar }"> -->
			<view class="footer-fixed">
				<view class="left" @click="logion(userData.id, false, '../../static/images/clo.png')">
					<image src="../../static/images/clo.png" style="width: 100%;height: 100%;;" mode="aspectFill">
					</image>
				</view>
				<view class="right" @click="logion(userData.id, true, '../../static/images/con1.png')">
					<image src="../../static/images/con1.png" style="width: 100%;height: 100%;;" mode="aspectFill">
					</image>
				</view>
			</view>
			<!-- v-show="showAnima" -->
			<image v-if="showAnima" :src="animUrl" mode="aspectFill" class="animate-middel-top"></image>
			<image v-show="showArrowDown" src="../../static/images/home/arrow_down_sm.png" mode="aspectFill"
				class="fixed_down"></image>
			<!-- 相关喜欢的弹窗 -->
			<u-popup :show="showLikePop" mode="center" round="12" :closeOnClickOverlay="false" @close="closeLikePop"
				@open="openLikePop" :safeAreaInsetBottom="false">
				<view class="mutual_like">
					<image class="likepopbg" :src="img + '/img/likepopbg.png'" mode="aspectFill"></image>
					<image class="trbg" src="../../static/images/like/likebg.png" mode="aspectFill"></image>
					<view class="like-pop-content">
						<view class="title">互相喜欢</view>
						<view class="toname">
							你已和
							<text class="toname_to">{{ mutualInfo.otherNickName }}</text>
							已互相喜欢
						</view>
						<view class="avatar_anmi">
							<view class="avatar avat_left_anmi">
								<image :src="mutualInfo.selfHeadPortrait" mode="aspectFill"></image>
							</view>
							<image class="liketo-icon" src="../../static/images/like/liketo.png" mode="aspectFill">
							</image>
							<view class="avatar avat_right_anmi">
								<image :src="mutualInfo.otherHeadPortrait" mode="aspectFill"></image>
							</view>
						</view>
						<view class="pt52">
							相遇是最浪漫的事
							<br />
							相守是最感动的话
						</view>
						<view class="like-pop-btn qdzh" @click="goSedMsg">去打招呼</view>
						<view class="like-pop-btn shzl" @click.stop="closeLikePop">稍后再聊</view>
					</view>
				</view>
			</u-popup>
			<login @change="getUserInfo" :isLoginPop="isLoginPop" class="my-select"></login>
		</view>
		<!-- 推荐结束 -->
		<view v-else class="tuijian_over" @touchmove.stop.prevent="() => {}">
			<view class="imgbg">
				<image src="../../static/images/like/nowover.png" mode="aspectFill"></image>
				<view class="over-content">
					<view class="isover">本次推荐已结束</view>
					<view class="overtime">下次推荐明天中午12点整</view>
					<view class="nxet-tuijian" @click="goTips">下次推荐提醒我</view>
				</view>
			</view>
		</view>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirma"></ELM>
	</view>
</template>

<script>
	let util = require('@/util/means.js');
	import ELM from '@/components/elm-toast/index.vue';
	import LsSwiper from '../../components/ls-swiper/index.vue';
	import login from '../../components/login/login.vue';
	import {
		WS
	} from '@/util/wsConfig.js'
	export default {
		components: {
			ELM,
			login,
			LsSwiper
		},
		data() {
			return {
				tipMsg: '',
				isConfirm: false,
				mutualInfo: {},
				showLikePop: false,
				animUrl: '',
				showAnima: false,
				showBtn: true,
				showArrowDown: true,
				isInit: true,
				showTabbar: true,
				scrollTop: 0,
				unfold: false,
				isLoginPop: false,
				age: uni.getStorageSync('itemobj').birthday,
				gender: uni.getStorageSync('itemobj').gender,
				city: uni.getStorageSync('itemobj').city,
				userData: {
					userArticleViewResponse: {
						articleContent: null,
						articleImg: []
					}
				},
				img: this.$BASE_URL,
				// bgColor: 'linear-gradient(to bottom, #e8eef6, #eef5f6)',
				bgColor: '#F4F5F9',
				base_lsit: [

				],
				data: [],
				count: 0,
				ownerId: null,
				timeoutObj: null,
				reConnect: true,
				socketTask: null,
				chooseimg: false
			};
		},
		onHide() {
			if (!this.chooseimg) {
				this.reConnect = true;
				this.closeSocket();
				clearInterval(this.timeoutObj); //销毁定时器
			}

		},
		// 自定义此页面的转发给好友(已经有全局的分享方法，此处会覆盖全局)
		onShareAppMessage(res) {
			return {
				title: this.userData.age + '岁 ' + this.userData.height + 'cm ' + this.userData.profession + ' ' + (this
					.userData.city != null ? this.userData.city : this.city) + ' ' + this.userData.school,
				path: '/pageslike/like/userInfo?share=true&id=' + this.userData.id,
				imageUrl: this.userData.imgList[0]
			}
		},
		// 自定义页面的分享到朋友圈
		onShareTimeline(res) {
			return {
				title: this.userData.age + '岁 ' + this.userData.height + 'cm ' + this.userData.profession + ' ' + (this
					.userData.city != null ? this.userData.city : this.city) + ' ' + this.userData.school,
				path: '/pageslike/like/userInfo?share=true&id=' + this.userData.id,
				imageUrl: this.userData.imgList[0]
			}
		},
		onShow() {
			this.chooseimg = false;
			this.showAnima = false;
			this.getRecUserInfo();
			if (uni.getStorageSync('token')) {
				this.getUnRead();
				this.ownerId = uni.getStorageSync('info') != null ? uni.getStorageSync('info').id : null;
				this.connectSocketInit();
			}
		},
		onReachBottom(e) {
			this.bottom_offset = true;
		},
		onPageScroll(e) {
			if (!this.isInit) {
				return;
			}
			const _this = this;
			if (e.scrollTop <= 0) {
				this.showArrowDown = true;
				return;
			}
			this.showArrowDown = false;
			if (this.scrollTop - e.scrollTop > 50) {
				this.bottom_offset = false;
			}
			if (this.bottom_offset) {
				return;
			}
			if (!_this.flag) {
				setTimeout(() => {
					if (e.scrollTop > _this.scrollTop&&_this.showTabbar) {
						uni.hideTabBar({
							animation: true,
							success() {
								_this.showTabbar = false;
							}
						});
					} else if (e.scrollTop < _this.scrollTop&&!_this.showTabbar) {
						uni.showTabBar({
							animation: true,
							success() {
								_this.showTabbar = true;
							}
						});
					}
					_this.scrollTop = e.scrollTop;
					_this.flag = false;
				}, 200);
			}
			_this.flag = true;
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
			goTips() {
				uni.navigateTo({
					url: '/pages2/explain/explain'
				});
			},
			goSedMsg() {
				const {
					otherId,
					otherHeadPortrait,
					otherNickName
				} = this.mutualInfo
				uni.navigateTo({
					url: `/pages2/sedMsg/sedMsg?sendUserId=${otherId}&nickName=${otherNickName}`
				})
			},
			openLikePop() {
				this.showLikePop = true;
			},
			closeLikePop() {
				this.showLikePop = false;
				this.getRecUserInfo();
			},
			async logion(id, type, url) {
				this.animUrl = url;
				const res = await this.$getId();
				if (res == 11003) {
					this.isLoginPop = true;
				} else {
					// 滑动到顶部
					if (!this.showBtn) {
						console.log('防止连续点击');
						return;
					}
					uni.pageScrollTo({
						scrollTop: 0,
						duration: 1000
					});
					this.userIsLike(id, type);
				}
			},
			//type 喜欢 true 还是 取消 false
			userIsLike(userId, type) {
				console.log("开始匹配：" + userId)
				this.showBtn = false;

				// 点击 取消 喜欢  根据返回结果展示
				this.$myRequest({
					url: 'nostalgia/usermatching/match',
					data: {
						otherUser: userId,
						result: type
					},
					withToken: true,
					method: 'POST'
				}).then(res => {
					console.log("匹配结束1：" + userId)
					if (res.data.code == 200) {
						this.showAnima = true;
						setTimeout(() => {
							this.showAnima = false;
						}, 1000);

						// 显示 相互 弹窗		this.showLikePop = true;
						if (res.data.data && res.data.data.result) {
							this.showLikePop = true;
							this.mutualInfo = res.data.data;
						} else {
							console.log("匹配结束2：" + userId)
							this.getRecUserInfo();
						}

					} else {
						this.tipMsg = res.data.msg;
						this.$refs.elm.showDialog();
						this.showBtn = true;
					}
				})

			},
			async screen() {
				const res = await this.$getId();
				console.log(res);
				if (res == 11003) {
					this.isLoginPop = true;
				} else {
					uni.navigateTo({
						url: '/pagespreference/preference/preference'
					});
				}
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
								fail: err => {
									
								}
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
			getRecUserInfo() {
				console.log("开始获取用户信息")
				// 获取推荐用户信息
				let age = 20;
				if (this.age) {
					age = util.mymethod(uni.getStorageSync('itemobj').birthday);
				}
				let loginType;
				uni.getStorageSync('token') ? (loginType = true) : (loginType = false);

				this.$myRequest({
					url: 'nostalgia/fruser/recommendUserInfo',
					withToken: loginType,
					data: {
						age,
						gender: this.gender == undefined ? "FEMALE" : this.gender
					},
					method: 'GET'
				}).then(res => {
					console.log("获取用户信息完成")
					if (res.data.code == 200) {
						if (res.data.data == null) {
							this.showArrowDown = false;
							this.isInit = false;
							// this.data = [];
							this.userData = {};
							this.nextData = {};
							uni.setTabBarBadge({
								index: 0,
								text: '0'
							});
						} else {
							this.showArrowDown = true;
							this.isInit = true;
							this.userData = res.data.data;
							if(this.userData.userArticleViewResponse.articleImg.length>3){
								this.userData.userArticleViewResponse.articleImg = this.userData.userArticleViewResponse.articleImg.splice(0,3)
							}
							uni.setTabBarBadge({
								index: 0,
								text: `${res.data.data.surplusNum}`
							});

							this.showBtn = true;
							console.log(
								`console.log("获取用户信息完成") 用户id${res.data.data.id}****剩余次数${res.data.data.surplusNum}`
							);
						}
					} else {
						this.tipMsg = res.data.msg;
						this.$refs.elm.showDialog();
					}
				})
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
							console.log("首页心跳");
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
			shareUser() {
				uni.navigateTo({
					url: '/pageslike/like/userInfo?share=true&id=' + this.userData.id
				})
			},
			goDynamicState(){
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
		},
		confirma() {
			this.isConfirm = false;
		}
	};
</script>

<style lang="scss" scoped>
	.popUpShow {
		overflow: hidden;
		height: 100vh;
	}

	.tuijian_over {
		padding: 30rpx;
		overflow: hidden;
		height: 100vh;
		box-sizing: border-box;
		display: flex;
		align-items: center;

		.imgbg {
			position: relative;
			width: 690rpx;
			height: 957rpx;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;

			image {
				position: absolute;
				top: 0;
				left: 0;
				width: 690rpx;
				height: 957rpx;
			}

			.over-content {
				position: relative;
				z-index: 1;
				text-align: center;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;

				.isover {
					color: #333333;
					font-size: 32rpx;
					font-weight: 800;
				}

				.overtime {
					margin-top: 50rpx;
					color: #333333;
					font-size: 48rpx;
					font-weight: 800;
				}

				.nxet-tuijian {
					margin-top: 70rpx;
					width: 372rpx;
					height: 84rpx;
					line-height: 84rpx;
					background: linear-gradient(to right, #c2d2f9, #c5c2f3);
					border-radius: 84rpx;
					color: #415c9e;
					font-size: 28rpx;
					font-weight: 400;
				}
			}
		}
	}

	.mutual_like {
		position: relative;
		width: 582rpx;
		height: 831rpx;
		box-sizing: border-box;

		.likepopbg {
			position: absolute;
			top: 0;
			left: 0;
			width: 582rpx;
			height: 831rpx;
			box-sizing: border-box;
		}

		.trbg {
			box-sizing: border-box;
			position: absolute;
			top: 44rpx;
			right: 13rpx;
			width: 168rpx;
			height: 110rpx;
		}

		.like-pop-content {
			box-sizing: border-box;
			position: absolute;
			top: 0;
			left: 0;
			width: 582rpx;
			height: 831rpx;
			padding: 64rpx 54rpx;
			text-align: center;

			.title {
				box-sizing: border-box;
				text-align: center;
				font-size: 40rpx;
				font-weight: bold;
			}

			.toname {
				padding-top: 18rpx;
				color: #4a4a4a;
				font-size: 28rpx;
				text-align: center;

				.toname_to {
					color: #9fb5eb;
				}
			}

			.avatar_anmi {
				padding-top: 45rpx;
				display: flex;
				height: 124rpx;
				flex-direction: row;
				align-items: center;
				justify-content: center;
				position: relative;

				.avatar {
					width: 124rpx;
					height: 124rpx;
					border-radius: 50%;
					overflow: hidden;
					transition: all 0.3s;

					image {
						width: 100%;
						height: 100%;
					}

					&.avat_left_anmi {
						position: relative;
						left: 0;
						width: 124rpx;
						height: 124rpx;
						animation: leftMove 1s;
					}

					&.avat_right_anmi {
						position: relative;
						right: 0;
						width: 124rpx;
						height: 124rpx;
						animation: rightMove 1s;
					}
				}

				.liketo-icon {
					width: 55rpx;
					height: 41rpx;
				}
			}

			.pt52 {
				padding-top: 52rpx;
				font-size: 28rpx;
				color: #4a4a4a;
			}

			.like-pop-btn {
				width: 474rpx;
				height: 84rpx;
				line-height: 84rpx;
				text-align: center;
				font-size: 28rpx;
				border-radius: 42rpx;

				&.qdzh {
					width: 100%;
					margin-top: 62rpx;
					background: linear-gradient(to right, #c2d2f9, #c5c2f3);
					color: #415c9e;
				}

				&.shzl {
					line-height: 80rpx;
					width: 100%;
					box-sizing: border-box;
					margin-top: 28rpx;
					border: 2px solid #9fb5eb;
					color: #9fb5eb;
				}
			}
		}
	}

	@keyframes leftMove {
		0% {
			left: -130rpx;
		}

		100% {
			left: 0;
		}
	}

	@keyframes rightMove {
		0% {
			right: -130rpx;
		}

		100% {
			right: 0;
		}
	}

	.unfold {
		width: 39rpx;
		height: 40rpx;
		margin: 30rpx auto;
	}

	.animate-middel-top {
		position: fixed;
		top: 50%;
		left: 50%;
		z-index: 111;
		width: 0;
		height: 0;
		// transition: all 0.5s linear;
		transform: translate(-50%, -50%);
		animation: topHide 1s linear;
	}

	@keyframes topHide {
		0% {
			top: 50%;
			width: 0;
			height: 0;
		}

		70% {
			top: 18%;
			width: 100rpx;
			height: 100rpx;
		}

		80% {
			top: 18%;
			width: 100rpx;
			height: 100rpx;
		}

		90% {
			top: 20%;
			width: 80rpx;
			height: 80rpx;
		}

		100% {
			top: 20%;
			wixdth: 0;
			height: 0;
		}
	}

	.footer-fixed {
		position: fixed;
		bottom: calc(var(--window-bottom) + 32rpx);
		left: 146rpx;
		right: 146rpx;
		z-index: 111;
		transition: all 0.3s linear;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		transition: all 1s linear;

		.right {
			width: 108rpx;
			height: 108rpx;
		}

		.left {
			width: 108rpx;
			height: 108rpx;
		}
	}

	.fixed_down {
		position: fixed;
		z-index: 12;
		bottom: 30rpx;
		left: 50%;
		transform: translateX(-50%);
		width: 40rpx;
		height: 40rpx;
		transition: all 0.3s linears;
	}

	.fe {
		width: 100%;
		height: 186rpx;
		position: relative;
		display: flex;
		justify-content: center;

		.text {
			height: 40rpx;
			font-size: 28rpx;
			font-family: PingFang SC-Regular, PingFang SC;
			font-weight: 400;
			color: #bbbbbb;
			line-height: 40rpx;
			margin: 0 18rpx;
		}

		.line {
			width: 50rpx;
			height: 2rpx;
			background-color: #bbbbbb;
		}
	}

	.bo {
		.simg {
			width: 32rpx;
			height: 32rpx;
		}

		.name {
			height: 40rpx;
			font-size: 28rpx;
			font-family: PingFang SC-Heavy, PingFang SC;
			font-weight: 800;
			color: #434343;
			line-height: 40rpx;
			margin-left: 16rpx;
		}

		width: 686rpx;
		height: 98rpx;
		margin: 0 auto;
		background: #ffffff;
		margin-top: 32rpx;
		border-radius: 24rpx 24rpx 24rpx 24rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.reco_card {
		border-radius: 24rpx;
		background-color: white;
		width: 680rpx;
		height: 98rpx;
		margin: 32rpx auto;
		display: flex;
		align-items: center;
		justify-content: center;

		image {
			width: 32rpx;
			height: 32rpx;
		}

		view {
			margin-left: 16rpx;
			font-size: 28rpx;
			font-weight: bold;
		}
	}

	.Personality_type {
		background-color: white;
		width: 680rpx;
		height: 198rpx;
		margin: 0 auto;
		// margin-top: 32rpx;
		border-radius: 24rpx 24rpx 0 0;

		.content {
			margin-left: 40rpx;
			padding-top: 32rpx;
			display: flex;
			flex-direction: column;

			.have_image {
				display: flex;
				align-items: center;
				font-size: 28rpx;
				font-weight: bold;

				image {
					width: 28rpx;
					height: 28rpx;
				}

				.title {
					padding-left: 12rpx;
				}
			}
		}

		.center {
			margin: 0 auto;
			font-size: 36rpx;
			font-weight: bold;
			margin-top: 20rpx;
		}
	}

	.Personality_content {
		border-radius: 0 0 24rpx 24rpx;

		.item {
			display: flex;
			align-items: center;
			padding-top: 48rpx;
			margin-left: 36rpx;

			.head_text {
				width: 130rpx;
				white-space: nowrap;
			}

			.center {
				background-color: #f5f5f5;
				width: 296rpx;
				border-radius: 16rpx;
				height: 24rpx;
				position: relative;
				margin: 0 26rpx;

				.inner1,
				.inner2,
				.inner3,
				.inner4 {
					border-radius: 16rpx;
					height: 24rpx;
					width: 186rpx;
					position: absolute;

					background-color: #ff91c5;
				}
			}

			.tail_text {
				// width: 130rpx;
			}
		}

		height: 378rpx;

		width: 680rpx;
		background-color: white;
		margin: 0 auto;
	}

	.bottom {
		background-color: #fff;
		position: relative;
		width: 686rpx;
		margin: 0 auto;
		border-radius: 24rpx 24rpx 24rpx 24rpx;
		overflow: hidden;
		margin-top: 32rpx;
	}

	.line {
		width: 636rpx;
		margin: 0 auto;
		overflow: hidden;
		display: flex;
		flex-direction: row;
		align-items: center;

		.sm-line {
			width: 50rpx;
			margin: 0 10rpx;
			height: 1rpx;
			background-color: #c8d3e2;
		}
	}

	.circles {
		background: #f5f6fa;
		width: 44rpx;
		height: 44rpx;
		border-radius: 50%;
		position: absolute;
		right: -22rpx;
		top: 174rpx;
	}

	.circle {
		background: #f5f6fa;
		width: 44rpx;
		height: 44rpx;
		border-radius: 50%;
		position: absolute;
		left: -22rpx;
		top: 174rpx;
	}

	page {
		width: 100%;
		height: 100vh;
	}

	.card {
		background-color: white;
		width: 680rpx;
		height: 250rpx;
		margin: 0 auto;
		margin-top: 32rpx;
		border-radius: 24rpx;

		.content {
			margin-left: 40rpx;
			padding-top: 32rpx;
			display: flex;
			flex-direction: column;

			.have_image {
				display: flex;
				align-items: center;
				font-size: 28rpx;
				font-weight: bold;

				image {
					width: 28rpx;
					height: 28rpx;
				}

				.title {
					padding-left: 12rpx;
				}
			}

			.one {
				font-size: 28rpx;
				margin-top: 26rpx;
			}
		}
	}

	.box {
		.swiper {
			position: relative;

			.hint {
				position: absolute;
				top: 46rpx;
				right: 34rpx;
				width: 202rpx;
				height: 62rpx;
				background: rgba(255, 255, 255, 0.2);
				border-radius: 34rpx;
				display: flex;
				justify-content: center;
				align-items: center;

				image {
					width: 24rpx;
					height: 28rpx;
				}

				.t {
					font-size: 28rpx;
					font-weight: 400;
					color: #333333;
					margin-left: 14rpx;
				}
			}
		}

		.lable {
			display: flex;
			align-items: center;

			font-size: 28rpx;
			font-family: PingFang SC-Regular, PingFang SC;
			font-weight: 400;
			color: #434343;
			margin-left: 50rpx;
			margin-top: 48rpx;

			.lable-div {
				height: 54rpx;
				background: #e5f4ff;
				border-radius: 28rpx;
				line-height: 54rpx;
				padding: 0 38rpx;
			}
		}

		.line {
			width: 636rpx;
			// height: 2rpx;
			// background-color: #c8d3e2;
			// border-bottom: 2rpx dashed #c8d3e2;
			margin: 0 auto;
			margin-top: 44rpx;
		}

		.circles {
			background: #f5f6fa;
			width: 44rpx;
			height: 44rpx;
			border-radius: 50%;
			position: absolute;
			right: -22rpx;
			top: 1002rpx;
		}

		.circle {
			background: #f5f6fa;
			width: 44rpx;
			height: 44rpx;
			border-radius: 50%;
			position: absolute;
			left: -22rpx;
			top: 1002rpx;
		}

		width: 686rpx;
		margin: 0 auto;
		border-radius: 42rpx 42rpx 24rpx 24rpx;
		overflow: hidden;
		z-index: 10;
		position: relative;
		background: #fff;

		.ling {
			height: 42rpx;
			display: flex;
			align-items: center;
			margin-left: 58rpx;
			margin-top: 24rpx;

			.img {
				height: 42rpx;
				width: 42rpx;
				margin-right: 8rpx;
			}

			.text {
				height: 34rpx;
				font-size: 24rpx;
				font-family: PingFang SC-Regular, PingFang SC;
				font-weight: 400;
				color: #333333;
				line-height: 34rpx;
			}
		}

		.box-bq {
			height: 40rpx;
			font-size: 28rpx;
			font-family: PingFang SC-Bold, PingFang SC;
			font-weight: bold;
			color: #333333;
			margin-left: 58rpx;
			margin-top: 24rpx;
			line-height: 40rpx;
			display: flex;
			align-items: center;

			.xian {
				width: 2rpx;
				height: 20rpx;
				background: #e0e2e8;
				margin: 0 32rpx;
			}
		}

		.box-name {
			.simg {
				width: 48rpx;
				height: 48rpx;
				margin-left: 20rpx;
			}

			height: 56rpx;
			font-size: 40rpx;
			font-family: PingFang SC-Heavy,
			PingFang SC;
			font-weight: 800;
			color: #4a4a4a;
			line-height: 56rpx;
			margin-left: 58rpx;
			margin-top: 24rpx;
			display: flex;
			align-items: center;

			image {
				width: 48rpx;
				height: 48rpx;
				margin-left: 20rpx;
				margin-top: 5rpx;
			}
		}

		.box-jj {
			.box-jj-ta {
				width: 618rpx;
				height: 58rpx;
				background: #f5f6fa;
				border-radius: 30rpx 30rpx 30rpx 30rpx;
				margin: 0 auto;
				margin-top: 32rpx;
				display: flex;
				align-items: center;
				justify-content: space-between;

				.text2 {
					height: 34rpx;
					font-size: 24rpx;
					font-family: PingFang SC-Regular, PingFang SC;
					font-weight: 400;
					color: #333333;
					line-height: 34rpx;
					margin-right: 28rpx;
				}

				.box-jj-ta-left {
					display: flex;
					align-items: center;

					.xian {
						width: 2rpx;
						height: 20rpx;
						background: #e0e2e8;
						margin: 0 18rpx;
					}

					.te {
						height: 34rpx;
						font-size: 24rpx;
						font-family: PingFang SC-Regular, PingFang SC;
						font-weight: 400;
						color: #333333;
						line-height: 34rpx;
					}

					.img {
						width: 24rpx;
						height: 24rpx;
						margin: 0 18rpx 0 26rpx;
					}
				}
			}

			width: 100%;
			height: auto;
			background-color: #fff;
			position: relative;
		}
	}

	.u-nav-slot {
		display: flex;
		align-items: center;

		.text {
			height: 44rpx;
			font-size: 32rpx;
			font-family: PingFang SC-Bold, PingFang SC;
			font-weight: bold;
			color: #000000;
			margin-left: 14rpx;
			line-height: 44rpx;
		}

		.img {
			width: 22rpx;
			height: 28rpx;
			margin-left: 32rpx;
		}
	}

	.pageanima {
		position: relative;
	}

	.pagePop {
		animation-name: pagePop; // 动画名称
		animation-timing-function: linear; // 动画执行方式，linear：匀速；ease：先慢再快后慢；ease-in：由慢速开始；ease-out：由慢速结束；ease-in-out：由慢速开始和结束；
		// animation-delay: 1s; // 动画延迟时间
		animation-duration: 0.5s; // 动画完成时间
	}

	@keyframes pagePop {
		0% {
			top: 100rpx;
			opacity: 0.5;
		}

		100% {
			top: 0;
			opacity: 1;
		}
	}

	.bgc-imga {
		position: absolute;
		top: -100rpx;
		left: 0rpx;
		width: 100%;
		pointer-events: none;
		height: 100%;
	}

	page {
		width: 100%;
		height: 100%;
	}

	.popup-container {
		height: 562rpx;
		width: 500rpx;
		overflow: hidden;
		position: relative;

		.pop-bgc {
			position: absolute;
			top: 0;
			pointer-events: none;
			left: 0;
			height: 100%;
			width: 100%;
		}

		.moon-bg {
			position: absolute;
			top: 50rpx;
			pointer-events: none;
			right: 6rpx;
			width: 220rpx;
			height: 152rpx;
		}

		.container {
			position: absolute;
			top: 0;
			left: 0;
			height: 100%;
			width: 100%;

			.container-c {
				width: 392rpx;
				margin: auto;
				margin-top: 66rpx;
			}

			.pop-title {
				color: #4a4a4a;
				font-weight: 800;
				font-size: 40rpx;
			}

			.tips-content {
				width: 448rpx;
				margin: auto;
				text-align: left;
				margin-top: 20rpx;
				line-height: 56rpx;
				font-size: 28rpx;

				.agreement-btn {
					display: inline-block;
					color: #5b83e8;
					text-decoration: underline;
				}
			}

			.btn-c {
				display: flex;
				margin: auto;
				margin-top: 108rpx;
				justify-content: space-between;
				flex-wrap: wrap;
				width: 100%;
			}

			.pop-btn {
				width: 100%;
				height: 84rpx;
				border-radius: 38rpx;
				text-align: center;
				line-height: 84rpx;
				border: 2rpx solid #9fb5eb;
				color: #9fb5eb;
				margin-top: 28rpx;

				&.to-login {
					margin-top: 0;
					display: flex;
					justify-content: center;
					align-items: center;
					border: none;
					color: #415c9e;

					background: linear-gradient(86deg, #c2d2f9 0%, #c5c2f3 100%);

					.sercet-btn {
						vertical-align: middle;
						width: 78rpx;
						height: 40rpx;
						background: #b6b7f8;
						border-radius: 22rpx;
						font-size: 24rpx;
						transform: scale(0.81);
						transform-origin: center;
						color: #fff;
						line-height: 40rpx;
					}
				}
			}
		}
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
		padding: 30rpx 42rpx 30rpx 0rpx;
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
	
</style>
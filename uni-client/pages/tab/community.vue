<template>
	<!-- 使用z-paging-swiper为根节点可以免计算高度 -->
	<z-paging-swiper>
		<view :class="['add',isOpen?'add2':'']">
			<image @click='goPage("/pagescommunity/newsRelease/newsRelease")' src="../../static/images/102.png" mode="">
			</image>
		</view>
		<!-- 需要固定在顶部不滚动的view放在slot="top"的view中 -->
		<!-- 注意！此处的z-tabs为独立的组件，可替换为第三方的tabs，若需要使用z-tabs，请在插件市场搜索z-tabs并引入，否则会报插件找不到的错误 -->
		<template #top>
			<view class="">
				<u-navbar title="个人中心" :placeholder="true">
					<template slot="left">
						<view style="position: relative;" @click="goMessage">
							<image class="left" @click="goMessage" src="../../static/images/101.png" mode=""></image>
							<u-badge style="font-weight: 600;" @click="goMessage" :value="num" bgColor="#FA5151"
								:max="99" :offset="[-6,-8]" :absolute="true"></u-badge>
						</view>
					</template>
					<template slot="center">
						<u-tabs :list="list" @click="tabClick" :current="current" lineColor="#86A6F3" :activeStyle="{
						            color: '#303133',
						            fontWeight: 'bold',
						            transform: 'scale(1.2)'
						        }" :inactiveStyle="{
						            color: '#606266',
						            transform: 'scale(1)'
						        }" itemStyle="padding-left: 15px; padding-right: 15px; height: 34px;">
						</u-tabs>
					</template>
				</u-navbar>
			</view>
		</template>
		<!-- swiper必须设置height:100%，因为swiper有默认的高度，只有设置高度100%才可以铺满页面  -->
		<swiper class="swiper" :current="current" @animationfinish="swiperAnimationfinish">
			<swiper-item class="swiper-item" v-for="(i,index) in list" :key="index">
				<!-- 这里的swiper-list-item为demo中为演示用定义的组件，列表及分页代码在swiper-list-item组件内 -->
				<!-- 请注意，swiper-list-item非z-paging内置组件，在自己的项目中必须自己创建，若未创建则会报组件不存在的错误 -->
				<!-- <ItemVue v-for="item in arr" :key="item.id" @delete="deleteFn" :info="item"
					" @praise="praise" @close="isOpen = false" @open="isOpen = true" /> -->
				<ListVue ref="listItem" @selectShare="selectShare" :tabIndex="index" :userInfo="userInfo"
					:currentIndex="current" @changeOpen="changeOpen"></ListVue>
			</swiper-item>
		</swiper>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
		<login @change="getUserInfo" :isLoginPop="isLoginPop&&tabIndex!=1" class="my-select"></login>
	</z-paging-swiper>
</template>

<script>
	import ItemVue from '../components/Item.vue';
	import empty from '@/components/empty.vue'
	import ListVue from '../components/List.vue';
	import login from '../../components/login/login.vue';
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		// 复制下面这行 options 代码
		options: {
			styleIsolation: 'shared'
		},
		components: {
			ItemVue,
			empty,
			ListVue,
			ELM
		},
		data() {
			return {
				list: [{
						name: '关注'
					},
					{
						name: '推荐',
					},
					{
						name: '同城',
					},
				],
				params: {
					pageIndex: 1,
					pageSize: 4,
					tabType: 'RECOMMEND'
				},
				current: 1, // tabs组件的current值，表示当前活动的tab选项
				arr: [],
				total: 0,
				userInfo: {},
				selectInfo: {},
				isOpen: false,
				num: 0,
				isLoginPop: false,
				tipMsg: '',
				isConfirm: false
			};
		},
		onLoad() {
			if (uni.getStorageSync('info') != '') {
				this.userInfo = uni.getStorageSync('info');
			} else {
				this.userInfo = {
					id: 0
				}
			}

			// this.getList(true)
		},
		onShow() {
			this.getNum()
			if (uni.getStorageSync("isRefresh")) {
				this.current = 1
				this.$refs.listItem[1].reload()
				uni.setStorageSync("isRefresh", false)
			}
		},
		// 自定义此页面的转发给好友(已经有全局的分享方法，此处会覆盖全局)
		onShareAppMessage(res) {
			if (res.from == "button") {
				return {
					title: this.selectInfo.content,
					path: '/pagescommunity/dynamicDetail/dynamicDetail?id=' + this.selectInfo.id,
					imageUrl: this.selectInfo.img[0]
				}

			} else {
				return {
					title: "动态列表",
					path: '/pages/tab/community',
				}
			}

		},
		// 自定义页面的分享到朋友圈
		onShareTimeline(res) {
			return {
				title: "动态列表",
				path: '/pages/tab/community',
			}
		},
		methods: {
			changeOpen(value) {
				this.isOpen = value
			},
			selectShare(value) {
				this.selectInfo = value
			},
			// swiper滑动结束
			swiperAnimationfinish(e) {
				this.current = e.detail.current;
			},
			// 获取未读消息数
			getNum() {
				this.$myRequest({
					url: `/nostalgia/articleMessage/unread`,
					withToken: true,
					method: 'GET',
				}).then(res => {
					this.num = res.data != null ? res.data.data : 0;
				})
			},
			// 删除
			deleteFn(value) {
				this.$myRequest({
					url: `nostalgia/article/article/${value.id}`,
					withToken: true,
					method: 'DELETE',
				}).then(res => {
					if (res.data.code == 200) {
						let idx = this.arr.findIndex(item => item.id == value.id)
						this.arr.splice(idx, 1)
						this.total -= 1
					} else {
						this.tipMsg = res.data.msg;
						this.$refs.elm.showDialog();
					}

				})
			},
			// 点赞
			praise(value) {
				console.log("进来了")
				let obj = {
					...value,

				}
				let url = ''
				if (obj.isLike) {
					url = 'nostalgia/article/cancel/praise'
					obj.likeCount -= 1
				} else {
					url = 'nostalgia/article/praise'
					obj.likeCount += 1
				}
				this.$myRequest({
					url,
					data: {
						id: value.id,
						praiseType: "ARTICLE"
					},
					withToken: true,
					method: 'PUT',
				}).then(res => {
					if (res.data.code == "200") {
						obj.isLike = !value.isLike
						let idx = this.arr.findIndex(item => item.id == obj.id)
						this.arr.splice(idx, 1, obj)
					} else {
						console.log(res.data.msg)
						this.tipMsg = res.data.msg;
						this.$refs.elm.showDialog();
						return;
					}

				})
			},
			onRefresh() {
				this.getList(true)
				// 告知z-paging下拉刷新结束，这样才可以开始下一次的下拉刷新
				setTimeout(() => {
					this.$refs.paging.complete();
				}, 1000)
			},
			tabClick({
				name
			}) {
				if (name == "关注") {
					this.params.tabType = "FOLLOW"
					this.current = 0
				} else if (name == "推荐") {
					this.params.tabType = "RECOMMEND"
					this.current = 1
				} else {
					this.params.tabType = "CITY"
					this.current = 2
				}
				// this.params.pageIndex = 1
				// this.getList(true)
			},
			goPage(path) {
				if (uni.getStorageSync('info') != '') {
					uni.navigateTo({
						url: path
					})
				} else {
					this.isLoginPop = true
				}

			},
			getList(isFirst) {
				if (isFirst) this.params.pageIndex = 1
				this.$myRequest({
					url: `/nostalgia/article/page`,
					data: this.params,
					withToken: true,
					method: 'GET',
				}).then(res => {
					this.total = res.data.data.totalCount
					if (isFirst) this.arr = []
					this.arr = [...this.arr, ...res.data.data.rows].map(item => {
						return {
							...item,
							isDel: item.userId == this.userInfo.id
						}
					})
				})
			},
			goMessage() {
				if (uni.getStorageSync('info') != '') {
					this.tipMsg = "开源版暂未开放，敬请期待！如需旗舰版，可联系作者微信（MMRWXM）咨询";
					this.$refs.elm.showDialog();
				} else {
					this.isLoginPop = true
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
			confirm() {
				this.isConfirm = false;
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
	* {
		box-sizing: border-box;
		margin: 0;
		padding: 0;
		font-family: PingFang HK, PingFang HK;
	}

	.u-navbar .left {
		width: 28rpx;
		height: 32rpx;
	}

	.add {
		width: 90rpx;
		height: 90rpx;
		position: fixed;
		bottom: 100rpx;
		right: 56rpx;
		z-index: 99;
		box-shadow: 0rpx 0rpx 24rpx 2rpx rgba(181, 202, 252, 0.84);
		background: #86A6F3;
		border-radius: 50%;
		display: block;

		image {
			width: 90rpx;
			height: 90rpx;
		}
	}

	.add2 {
		z-index: 0;
	}

	.box {
		padding: 0 32rpx 30rpx 32rpx;

		::v-deep item-vue:first-child {
			.item {
				border: none;
				margin-top: 0;
			}
		}
	}

	.swiper {
		height: 100%;
	}

	::v-deep .itemm:first-child .item {
		border: none !important;
		margin-top: 0;
	}

	::v-deep .u-badge {
		font-weight: 600;
	}
</style>
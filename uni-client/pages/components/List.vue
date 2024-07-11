<template>
	<view class="content">
		<z-paging v-if="firstLoaded || isCurrentPage" ref="paging" empty-view-text="迈出第一步才有更多故事发生哦"
			:empty-view-title-style="{
				'font-size': '32rpx',
				'color': '#C9C9D0',
				'position': 'absolute',
				'width': '100vw',
			}" empty-view-img="/static/images/empty.png" :empty-view-img-style="{
				'position': 'absolute',
				'top': '210rpx',
				'width': '447rpx',
				'height': '375rpx'
			}" v-model="dataList" @query="queryList" :fixed="false">
			<!-- 如果希望其他view跟着页面滚动，可以放在z-paging标签内 -->
			<!-- <template v-if="dataList.length"> -->
			<view class="itemm" v-for="(item,index) in dataList" :key="item.id" @click="itemClick(item)">
				<ItemVue @delete="deleteFn" :info="item" @selectShare="selectShare(item)" @praise="praise"
					@close="close" @open="open" />
			</view>
			<!-- </template> -->
			<!-- <empty v-if="firstLoaded&&!dataList.length"></empty> -->

		</z-paging>
		<ELM ref="elm" :msg="tipMsg"  :isConfirm="isConfirm" @confirm="confirm"></ELM>
		<login @change="getUserInfo" :isLoginPop="(isLoginPop&&tabIndex!=1)||chilkLog" class="my-select"></login>
	</view>
</template>

<script>
	import ItemVue from './Item.vue'
	import empty from '@/components/empty.vue'
	import login from '../../components/login/login.vue'
	import ELM from '../../components/elm-toast/index.vue';
	export default {
		components: {
			ItemVue,
			empty,
			ELM,
			login
		},
		data() {
			return {
				// v-model绑定的这个变量不要在分页请求结束中自己赋值！！！
				dataList: [],
				// 当前组件是否已经加载过了
				firstLoaded: false,
				// 是否滚动到当前页
				isCurrentPage: false,
				isLoginPop: false,
				chilkLog:false,
				tipMsg: '',
				isConfirm: false
			}
		},
		props: {
			// 当前组件的index，也就是当前组件是swiper中的第几个
			tabIndex: {
				type: Number,
				default: function() {
					return 0
				}
			},
			// 当前swiper切换到第几个index
			currentIndex: {
				type: Number,
				default: function() {
					return 0
				}
			},
			userInfo: {
				type: Object,
				default: function() {
					return {}
				}
			}
		},
		watch: {
			currentIndex: {
				handler(newVal) {
					console.log("切换到了第：" + newVal + " pop:" + this.isLoginPop)
					if (newVal === this.tabIndex) {
						// 懒加载，当滑动到当前的item时，才去加载
						if (!this.firstLoaded) {
							// 这里需要延迟渲染z-paging的原因是为了避免在一些平台上立即渲染可能引发的底层报错问题
							this.$nextTick(() => {
								setTimeout(() => {
									this.isCurrentPage = true;
								}, 100);
							})
						}

					}
					if (newVal == 0 || newVal == 2) {
						console.log("进入判断第" + newVal + "个")
						if (uni.getStorageSync('info') == '') {

							this.isLoginPop = true;
						}
					}
				},
				immediate: true
			},
		},
		methods: {
			close() {
				this.$emit("changeOpen", false)
			},
			open() {
				this.$emit("changeOpen", true)
			},
			selectShare(item) {
				this.$emit("selectShare", item)
			},
			// 删除
			deleteFn(value) {
				this.$myRequest({
					url: `nostalgia/article/article/${value.id}`,
					withToken: true,
					method: 'DELETE',
				}).then(res => {
					
					if (res.data.code == "200") {
						let idx = this.dataList.findIndex(item => item.id == value.id)
						this.dataList.splice(idx, 1)
					}
					else{
						this.tipMsg = res.data.msg;
						this.$refs.elm.showDialog();
					}
					
				})
			},
			// 点赞
			praise(value) {
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
						let idx = this.dataList.findIndex(item => item.id == obj.id)
						this.dataList.splice(idx, 1, obj)
					} else if(res.data.code == "10006"){
						console.log("报错了："+res.data.code)
						this.chilkLog=true;
					}
					else{
						this.tipMsg = res.data.msg;
						this.$refs.elm.showDialog();
					}

				})
			},
			// 接收父组件传过来的刷新列表要求
			reload() {
				this.$nextTick(() => {
					// 刷新列表数据(如果不希望列表pageNo被重置可以用refresh代替reload方法)
					this.$refs.paging && this.$refs.paging.reload();
				})
			},
			queryList(pageNo, pageSize) {
				// 组件加载时会自动触发此方法，因此默认页面加载时会自动触发，无需手动调用
				// 这里的pageNo和pageSize会自动计算好，直接传给服务器即可
				// 模拟请求服务器获取分页数据，请替换成自己的网络请求
				const params = {
					pageIndex: pageNo,
					pageSize: pageSize,
					type: this.tabIndex
				}
				if (this.tabIndex == 0) {
					params.tabType = "FOLLOW"
				} else if (this.tabIndex == 1) {
					params.tabType = "RECOMMEND"
				} else {
					params.tabType = "CITY"
				}
				this.$myRequest({
					url: `/nostalgia/article/page`,
					data: params,
					withToken: true,
					method: 'GET',
				}).then(res => {
					if (res.data.code == 200) {
						let arr = res.data.data.rows.map(item => {
							return {
								...item,
								isDel: item.userId == this.userInfo != null ? this.userInfo.id : 0
							}
						})
						this.$refs.paging.complete(arr);
						setTimeout(() => {
							this.firstLoaded = true;
						}, 100)
					} else if (res.data.code == 10006) {
						let rows = [];
						let arr = rows.map(item => {
							return {
								...item,
								isDel: item.userId == this.userInfo != null ? this.userInfo.id : 0
							}
						})
						this.$refs.paging.complete(arr);
						setTimeout(() => {
							this.firstLoaded = true;
						}, 100)

					} else {
						let rows = [];
						let arr = rows.map(item => {
							return {
								...item,
								isDel: item.userId == this.userInfo != null ? this.userInfo.id : 0
							}
						})
						this.$refs.paging.complete(arr);
						setTimeout(() => {
							this.firstLoaded = true;
						}, 100)
						console.log("请求失败")
					}
				}).catch(res => {
					debugger
					// 如果请求失败写this.$refs.paging.complete(false);
					// 注意，每次都需要在catch中写这句话很麻烦，z-paging提供了方案可以全局统一处理
					// 在底层的网络请求抛出异常时，写uni.$emit('z-paging-error-emit');即可
					console.log("请求接口失败了")
					this.$refs.paging.complete(false);
				})
			},
			itemClick(item) {
				console.log('点击了', item.title);
			},
			getUserInfo(e) {
				if (e == 0) {
					this.isLoginPop = false;
					this.chilkLog=false;
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

<style scoped>
	.content {
		height: 100%;
		/* padding: 0 32rpx 30rpx 32rpx; */
	}
</style>
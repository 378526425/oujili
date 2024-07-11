<template>
	<view :class="['bigBox']">

		<view class="box">
			<textarea style="width: 100%;height: 260rpx;" v-model="form.content" :maxlength="-1" :auto-height="false"
				:showConfirmBar="false" :adjust-position="false" @focus="focusFn" :focus="focus" @blur="blurFn"
				border="none" placeholder="分享生活、表达思考、提出困惑..."></textarea>
			<view class="upload">
				<u-upload :fileList="fileList" accept="image" uploadIcon="plus" :capture="['album', 'camera']"
					@afterRead="afterRead" @delete="deletePic" width="200rpx" height="200rpx" multiple :maxCount="9"
					:previewFullImage="true"></u-upload>
			</view>
			<view class="bot" :style="{'bottom':heightFn}">
				<view class="btn">
					<view class="l">
						<!-- 	<image src="../../static/images/107.png" mode=""></image>
					<image src="../../static/images/108.png" mode=""></image>
					<u-checkbox label="匿名发布" name="匿名发布">
					</u-checkbox> -->
					</view>
					<view class="r" @click="submit">
						发布
					</view>
				</view>
			</view>
			<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
			<login @change="getUserInfo" :isLoginPop="isLoginPop&&tabIndex!=1" class="my-select"></login>
		</view>
	</view>
</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	import login from '../../components/login/login.vue';
	export default {
		components: {
			ELM
		},
		data() {
			return {
				form: {
					content: '',
					img: []
				},
				fileList: [],
				tipMsg: '',
				isConfirm: false,
				h: 0,
				focus: false,
				isLoginPop: false
			}
		},
		computed: {
			heightFn() {
				return `${this.h}px`
			}
		},
		methods: {
			submit() {
				this.form.img = this.fileList.map(item => {
					return item.url
				})
				if (!this.form.content && !this.fileList.length) {
					this.tipMsg = '请输入内容或上传图片后再点击发布'
					this.$refs.elm.showDialog();
					return;
				}
				this.$myRequest({
					url: 'nostalgia/article/add',
					data: this.form,
					withToken: true,
					withLoading: true,
					method: 'post',
				}).then(res => {
					if (res.data.code == "200") {
						uni.setStorageSync("isRefresh", true)
						uni.navigateBack({
							delta: 1
						})
					} else if (res.data.code == "10006") {
                           this.isLoginPop=true
					} else {
						this.tipMsg = res.data.msg
						this.$refs.elm.showDialog();
						return;
					}

				})
			},
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				console.log(lists, "lists");
				let fileListLen = this.fileList.length
				lists.map((item) => {
					this.fileList.push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				console.log(lists, "lists");
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i])
					let item = this.fileList[fileListLen]
					this.fileList.splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
				}
			},
			uploadFilePromise(file) {
				let self = this
				return new Promise((resolve, reject) => {
					let arr = file.url.split('.')
					uni.uploadFile({
						url: self.$BASE_URL + '/file/lasting/upload',
						filePath: file.url,
						header: {
							Authorization: uni.getStorageSync("token"),
						},
						name: 'file',
						success: (uploadFileRes) => {
							let data = JSON.parse(uploadFileRes.data)
							resolve(data.data.url)
						},
					});
				})
			},
			confirm() {
				this.isConfirm = false;
			},
			focusFn(e) {
				this.h = e.detail.height
				this.focus = true
			},
			blurFn(e) {
				this.h = 0
				this.focus = false
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
			}
		}
	}
</script>

<style lang="scss" scoped>
	* {
		box-sizing: border-box;
		padding: 0;
		font-family: PingFang HK, PingFang HK;
		margin: 0;

	}

	.bigBox {
		height: 100vh;
	}

	.temp {
		background-color: #000;
	}

	.box {
		padding: 32rpx;
		padding-bottom: 0;
		position: relative;
		height: 100vh;
		box-sizing: border-box;
		background-color: #fff;
		width: 100vw;
		overflow-x: hidden;
		overflow-y: auto;

		.upload {
			margin-top: 50rpx;
		}

		.bot {
			height: 128rpx;
			width: 100%;
			position: absolute;
			left: 0;
			bottom: 0;
			z-index: 99999;

			.btn {
				height: 128rpx;
				width: 100vw;
				position: absolute;
				bottom: 0;
				left: 0;
				padding: 32rpx;
				padding-bottom: constant(safe-area-inset-bottom);
				padding-bottom: env(safe-area-inset-bottom);
				display: flex;
				justify-content: space-between;
				align-items: center;
				box-sizing: border-box;

				.l {
					display: flex;
					align-items: center;

					image {
						width: 36rpx;
						height: 28rpx;
						margin-right: 22rpx;
					}
				}

				.r {
					width: 178rpx;
					height: 64rpx;
					background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
					border-radius: 42rpx 42rpx 42rpx 42rpx;
					font-weight: 600;
					font-size: 28rpx;
					text-align: center;
					color: #415C9E;
					line-height: 64rpx;
				}
			}
		}
	}

	::v-deep .u-upload__button {
		border-radius: 18rpx !important;
	}

	::v-deep .u-upload__wrap__preview {
		border-radius: 18rpx !important;
	}

	::v-deep .u-upload__success {
		display: none !important;
	}
</style>
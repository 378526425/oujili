<template>
	<view class="content">
		<view class="text">
			<u-textarea v-model="value" :cursorSpacing="100" :height="165" :show-confirm-bar="false" :maxlength="50" placeholder="请输入内容"
				count></u-textarea>
		</view>
		<view class="adding" @click="saveAbout">
			保存
		</view>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>
<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		data() {
			return {
				value: '',
				type: 0,
				tipMsg: '',
				isConfirm: false
			}
		},
		components: {
			ELM
		},
		onLoad(option) {
			var that = this;
			console.log(option);
			that.type = option.type;
			if (option.type == 1) {
				uni.setNavigationBarTitle({
					title: "关于自己"
				})
			} else if (option.type == 2) {
				uni.setNavigationBarTitle({
					title: "兴趣爱好"
				})
			} else if (option.type == 3) {
				uni.setNavigationBarTitle({
					title: "心仪的Ta"
				})
			}
			that.value = uni.getStorageSync("editText")

		},
		onUnload() {
			uni.removeStorageSync('editText')
		},
		onPageScroll(e) {
			console.log(e);
			if (e.scrollTop > 50) {} else {}


		},
		onReachBottom() {
			var that = this;
		},
		onPullDownRefresh() {},
		methods: {
			async saveAbout() {
				var that = this;
				if (that.value.length < 10) {
					this.tipMsg = "内容不能少于10个字";
					this.$refs.elm.showDialog();
					return false;
				}
				if (that.type == 1) {

					var data = {
						aboutMe: that.value
					};
				} else if (that.type == 2) {
					var data = {
						interest: that.value
					};
				} else if (that.type == 3) {
					var data = {
						loveRequirement: that.value
					};
				}
				uni.showLoading({
					title: '保存中...'
				})
				const res = await this.$myRequest({
					url: 'nostalgia/fruser/baseInfo/edit',
					data: data,
					withToken: true,
					method: 'PUT',
				});
				uni.hideLoading();
				if (res.data.code == 200) {
					this.isConfirm = true;
					this.tipMsg = "编辑成功";
					this.$refs.elm.showDialog();
					if (that.type == 1) {
						uni.$emit('updateAboutMe', {
							data: that.value
						});
					} else if (that.type == 2) {
						uni.$emit('updateInterest', {
							data: that.value
						});
					} else if (that.type == 3) {
						uni.$emit('updateLoveRequirement', {
							data: that.value
						});
					}
					/* 		setTimeout(() => {
								var pages = getCurrentPages();
								var prevPage = pages[pages.length - 2]; //上一个页面
								prevPage.$vm.BackPage({'type':that.type,"value":that.value}); //重点$vm
								uni.navigateBack({
									delta: 1
								})
								uni.hideLoading()
							}, 1000) */
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			confirm() {
				this.isConfirm = false;
				uni.navigateBack({
					delta: 1
				})
			},
		}
	}
</script>

<style lang="less">
	page {

		background-color: #F4F5F9;
	}

	.content {
		width: 100%;
		height: 100%;
	}

	.text {
		width: 100%;
		height: 365rpx;
		background: #ffffff;
		border-top: 1rpx solid #eee;
	}

	.adding {
		width: 614rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		font-size: 28rpx;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		color: #415C9E;
		line-height: 84rpx;
		position: fixed;
		bottom: 54rpx;
		left: 68rpx;
		text-align: center;
	}
</style>
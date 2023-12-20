<template>
	<view class="">
		<view class="list-box">
			<view class="list-item" v-for="(item,index) in list" @click="gotoPage(item)">
				<view class="list-item-text">
					{{item.name}}
				</view>
				<image class="list-item-icon" src="../../static/right.png" mode="aspectFill"></image>
			</view>
		</view>
		<myPopup v-if="isShow" content="确定要注销吗？" @confirm="confirm" @cancel="cancelbtn"></myPopup>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>

<script>
	import myPopup from '@/components/myPopup.vue';
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		components: {
			myPopup,
			ELM
		},
		data() {
			return {
				list: [{
					name: "推荐设置",
					url: "/pagespreference/preference/preference"
				}, {
					name: "用户协议",
					url: "/pagesme/agreement/agreement"
				}, {
					name: "隐私政策",
					url: "/pagesme/agreement/privacy"
				}, {
					name: "关于我们",
					url: "/pagesme/set/aboutme"
				},
				//<!--start 商业使用 未经作者授权不得删除此项 联系微信：MMRWXM（请注明来意） start -->
				{
					name: "合作联系",
					url: "/pagesme/set/contact"
				}
				//<!--end 商业使用 未经作者授权不得删除此项 联系微信：MMRWXM（请注明来意） end -->
				, {
					name: "注销账户",
					url: ""
				}],
				isShow: false,
				tipMsg: '',
				isConfirm: false
			}
		},
		methods: {
			gotoPage(item) {
				debugger
				if (item.name == "注销账户") {
					this.isShow = true;
				} else {
					uni.navigateTo({
						url: item.url
					})
				}

			},
			confirm() {

				this.$myRequest({
					url: '/nostalgia/fruser/delete/fruser',
					withToken: true,
					method: 'GET'
				}).then(res => {
					this.isShow = false;
					if (res.data.code == 200) {
						try {
							uni.removeStorageSync('userLocation');
							uni.removeStorageSync('itemobj');
							uni.removeStorageSync('info');
							uni.removeStorageSync('token');
							uni.removeStorageSync('verification');
						} catch (e) {
							// error
						}
						uni.switchTab({
							url: '/pageslogin/index/index'
						});
						/* uni.reLaunch({
							url: '/pageslogin/index/index'
						}); */
					} else {
						this.tipMsg = res.data.msg;
						this.$refs.elm.showDialog();
					}
				})
			},
			cancelbtn() {
				this.isShow = false
			},
		}
	}
</script>

<style lang="less">
	.list-box {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		padding: 0 32rpx;
		margin-top: 20rpx;

		.list-item {
			display: flex;
			height: 96rpx;
			align-items: center;

			.list-item-text {
				font-family: "PingFang SC-Heavy";
				font-weight: bold;
				color: #333333;
				font-size: 32rpx;
			}

			.list-item-icon {
				width: 14rpx;
				height: 28rpx;
				margin-left: auto;
			}
		}
	}
</style>

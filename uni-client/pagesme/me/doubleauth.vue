<template>
	<view class="content">
		<view class="auth-item">
			<image class="auth-img" src="../../static/images/me/sfauth.png"></image>
			<view class="auth-con">
				<view class="auth-con-1">身份认证</view>
				<view class="auth-con-2" v-if="userInfo.identityAuth=='EXAMINE'">审核中</view>
				<view class="auth-con-2" v-if="userInfo.identityAuth=='PASS'">已认证</view>
				<view class="auth-con-2" v-if="userInfo.identityAuth=='REFUSE'">已拒绝</view>
				<view class="auth-con-2" v-if="userInfo.identityAuth==null">未认证</view>
			</view>
			<view class="auth-btn" @click="gosf()" v-if="userInfo.identityAuth=='EXAMINE'">修改认证</view>
			<view class="auth-btn" @click="gosf()" v-if="userInfo.identityAuth=='PASS'">重新认证</view>
			<view class="auth-btn" @click="gosf()" v-if="userInfo.identityAuth=='REFUSE'">修改认证</view>
			<view class="auth-btn" @click="gosf()" v-if="userInfo.identityAuth==null">立即认证</view>
		</view>
		<view class="auth-item">
			<image class="auth-img" src="../../static/images/me/xlauth.png"></image>
			<view class="auth-con">
				<view class="auth-con-1">学历认证</view>
				<view class="auth-con-2" v-if="userInfo.educationAuth=='EXAMINE'">审核中</view>
				<view class="auth-con-2" v-if="userInfo.educationAuth=='PASS'">已认证</view>
				<view class="auth-con-2" v-if="userInfo.educationAuth=='REFUSE'">已拒绝</view>
				<view class="auth-con-2" v-if="userInfo.educationAuth==null">未认证</view>
			</view>
			<view class="auth-btn" @click="goli()" v-if="userInfo.educationAuth=='EXAMINE'">修改认证</view>
			<view class="auth-btn" @click="goli()" v-if="userInfo.educationAuth=='PASS'">重新认证</view>
			<view class="auth-btn" @click="goli()" v-if="userInfo.educationAuth=='REFUSE'">修改认证</view>
			<view class="auth-btn" @click="goli()" v-if="userInfo.educationAuth==null">立即认证</view>
		</view>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		data() {
			return {
				userInfo: {},
				tipMsg: '',
				isConfirm: false
			}
		},
		components: {
			ELM
		},
		onShow() {
			var that = this;
			this.getUserIno();
		},
		methods: {
			gosf() {
				uni.navigateTo({
					url: "/pagesme/me/identityauthentication"
				})
			},
			goli() {
				uni.navigateTo({
					url: "/pagesme/me/educationcertification"
				})
			},
			async getUserIno() {
				const res = await this.$myRequest({
					url: '/nostalgia/fruser/doubleAuth',
					withToken: true,
					method: 'GET',
				});
				if (res.data.code == 200) {
					this.userInfo = res.data.data
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
		}
	}
</script>

<style lang="scss">
	.content {
		width: 100%;
		box-sizing: border-box;
		padding: 0 32rpx;
	}

	page {
		background-color: #ffffff;
	}

	.auth-item {
		width: 100%;
		height: 182rpx;
		background-color: #ffffff;
		box-shadow: 0px 0px 24px 2px rgba(187, 203, 243, 0.21);
		border-radius: 24rpx;
		box-sizing: border-box;
		padding: 32rpx 36rpx;
		margin-top: 32rpx;

		.auth-img {
			width: 118rpx;
			height: 118rpx;
			float: left;
		}

		.auth-con {
			width: 346rpx;
			height: 118rpx;
			float: left;

			.auth-con-1 {
				width: 100%;
				height: 59rpx;
				line-height: 59rpx;
				box-sizing: border-box;
				padding: 0rpx 32rpx;
				font-size: 32rpx;
				color: #434343;
				font-weight: 500;
			}

			.auth-con-2 {
				width: 100%;
				height: 59rpx;
				line-height: 59rpx;
				box-sizing: border-box;
				padding: 0rpx 32rpx;
				font-size: 28rpx;
				color: #434343;
			}
		}

		.auth-btn {
			width: 150rpx;
			height: 59rpx;
			line-height: 59rpx;
			border-radius: 59rpx;
			float: left;
			background-color: #E7EDFC;
			color: #446CD2;
			font-size: 24rpx;
			font-weight: 400;
			text-align: center;
		}

	}
</style>
<template>
	<view class="container"  @touchmove.stop.prevent="() => {}">
		<u-navbar leftText=" " title=" " :bgColor="bgColor" :safeAreaInsetTop="true" :placeholder="true">
			<view class="u-nav-slot" slot="left" @click="left">
				<u-icon name="arrow-left" color="#000000" size="16" :bold="true"></u-icon>
			</view>

		</u-navbar>
		<image class="bg-c" src="@/static/images/bg-c.png"></image>
		<view class="sign-up-title-c">
			<image class="bg1-l" src="../../static/images/left.png" mode="widthFix"></image>
			<image class="bg2-2" src="../../static/images/right.png" mode="widthFix"></image>
			<view class="sign-up-title-item">
				就读/毕业学校
			</view>
			<view class="sign-up-title-tips">
				仅支持大专以上学历
			</view>
		</view>
		<view class="" style="height: 88rpx;">

		</view>
		<view class="select-c">
			<view class="select-c-div">
				<input type="text" placeholder="请输入内容" focus="" class="u-input-item" v-model="searchValue"
					@input="searchChange" @confirm="confirm">
				<image src="../../static/images/close.png" class="img" @click="close" mode="aspectFill"></image>
			</view>

			<view class="search-res-c" v-if="isShowSearchList">
				<view class="search-res-item" v-for="(item,index) in searchResList" @click="() => selectSearch(item)"
					:key="item.index">
					<text>{{item.name}}</text>
				</view>
			</view>
		</view>
		<view class="to-btn" @click="stepToNext">
			下一步（3/6）
		</view>
		<ELM ref="elm" :msg="tipMsg"></ELM>
	</view>
</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		components: {
			ELM
		},
		data() {
			return {
				bgColor: 'rgba(0, 31, 63, 0)',
				searchValue: '',
				searchResList: [],
				img: this.$BASE_URL,
				isShowSearchList: false,
				obj: {},
				img: this.$BASE_URL,
				id: "",
				tipMsg: ''
			};
		},
		onLoad(options) {
			this.obj = JSON.parse(options.obj)
			console.log(this.obj)
		},
		methods: {
			confirm() {
				this.nameSelect()
			},
			close() {
				this.searchValue = ''
				this.isShowSearchList = false
				this.searchResList = []
				this.id = ''
			},

			async nameSelect() {
				const res = await this.$myRequest({
					url: 'nostalgia/university/nameSelect',
					data: {
						name: this.searchValue
					},
					method: 'GET',
				});
				console.log(res, 'delshoucang');
				if (res.data.code == 200) {
					this.searchResList = res.data.data
					if (res.data.data.length > 0) {
						this.isShowSearchList = true
					} else {
						this.isShowSearchList = false
						
					}

				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			left() {
				uni.navigateBack()
			},
			searchChange(val) {
				if (val.detail.value == '') {
					this.id = ''
					this.close()
				}else{
					this.nameSelect()
				}
				console.log(val.detail.value)
			},
			stepToNext() {
				if (this.searchValue == '') {
					this.tipMsg = "请选择就读/毕业学校";
					this.$refs.elm.showDialog();
					return
				}
				console.log(this.searchValue);
				this.obj.school = this.searchValue



				uni.navigateTo({
					url: '/pagesintroduction/selectEducation/selectEducation?obj=' + JSON.stringify(this.obj)
				})
			},
			selectSearch(item) {
				this.searchValue = item.name
				this.id = item.id
				this.isShowSearchList = false
			}
		}
	}
</script>

<style lang="scss" >
	.container{
		position: relative;
		width: 100vw;
		height: 100%;
	}
	.bg-c {
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0;
		pointer-events: none;
	}
page {
		width: 100%;
		height: 100%;
	}
	.sign-up-title-c {
		position: relative;
		width: 594rpx;
		height: 200rpx;
		margin: auto;
		margin-top: 52rpx;
		box-sizing: content-box;
		overflow: hidden;
		color: #333;

		.bg1-l {
			position: absolute;
			top: 0rpx;
			left: 0;

			width: 95rpx;
			z-index: -1;
		}

		.bg2-2 {
			position: absolute;
			top: 66rpx;
			right: -10rpx;
			width: 83rpx;
			z-index: -1;
		}

		.sign-up-title-item {
			font-size: 48rpx;
			font-family: 'SourceHanSerifCN-Heavy';
			display: flex;
			align-items: center;
			justify-content: center;
			margin-top: 66rpx;

			.yexy {
				margin-left: -20rpx;color: rgba(45, 49, 50, 0.9);
				font-family: 'SourceHanSerifCN-Heavy';
			}
		}

		.sign-up-title-tips {
			text-align: center;
			margin-top: 30rpx;
			font-family: 'PingFang SC-Regular, PingFang SC';
			font-size: 24rpx;font-weight: 400;color: #666;
		}
	}




	/deep/ .indicator-class {
		background: #EEF5FE;
		z-index: -1;
	}

	.to-btn {
		width: 536rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		text-align: center;
		line-height: 84rpx;
		margin: auto;
		
		font-family: 'PingFang SC-Bold, PingFang SC';
		position: fixed;
		bottom: 15vh;
		left: 112rpx;
		color: rgba(65, 92, 158, 0.9);
		font-weight: 400;
		font-size: 28rpx;
	}

	.tips {
		margin: auto;
		text-align: center;
		margin-top: 30rpx;
		font-size: 24rpx;
		font-weight: 400;
		color: #B7B7B7;
	}

	.select-c {
		width: 536rpx;
		margin: auto;
		height: 508rpx;


		.search-res-c {
			width: 450rpx;
			margin: auto;
			max-height: 418rpx;
			font-size: 28rpx;
			overflow-y: auto;
			padding: 0 22rpx;
			box-sizing: border-box;
			background: rgba(255, 255, 255, 0.78);
			box-shadow: 0px 10px 12px 2px rgba(134, 166, 243, 0.19);

			.search-res-item {
				height: 70rpx;
				font-family: 'PingFang SC-Regular, PingFang SC';
				line-height: 70rpx;
color: rgba(45, 49, 50, 0.9);
			}
		}

	}

	.select-c-div {
		width: 536rpx;
		height: 90rpx;
		background: rgba(191, 209, 255, 0.34);
		display: flex;
		align-items: center;
		justify-content: space-between;
		border-radius: 46rpx 46rpx 46rpx 46rpx;
		border: 2rpx solid #5B83E8;

		.u-input-item {


			width: 70%;
			margin-left: 32rpx;

			font-size: 28rpx;

			font-family: 'PingFang SC-Regular, PingFang SC';
			font-weight: 400;
			color: rgba(45, 49, 50, 0.9);
			line-height: 90rpx;

		}

		.img {
			width: 32rpx;
			height: 32rpx;
			margin-right: 32rpx
		}
	}
</style>

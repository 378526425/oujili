<template>
	<view class="container"  @touchmove.stop.prevent="() => {}">
		<view class="select-c">
			<view class="select-c-div">
				<input type="text" placeholder="请输入内容" focus="" class="u-input-item" v-model="searchValue"
					@input="searchChange" @confirm="confirm">
				<image src="/static/images/close.png" class="img" @click="close" mode="aspectFill"></image>
			</view>

			<view class="search-res-c" v-if="isShowSearchList">
				<view class="search-res-item" v-for="(item,index) in searchResList" @click="() => selectSearch(item)"
					:key="item.index">
					<text>{{item.name}}</text>
				</view>
			</view>
		</view>
		<view class="to-btn" @click="stepToNext">
			确定
		</view>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>

<script>
	// import MySelect from './components/dateSelect.vue'
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
				tipMsg: '',
				isConfirm: false
			};
		},
		onLoad(options) {
			if(options.obj){
				this.obj = JSON.parse(options.obj)
				console.log(this.obj)
			}
			if(options.val){
				this.searchValue = options.val
			}
			
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

				var pages = getCurrentPages();
				var prevPage = pages[pages.length - 2]; //上一个页面
				prevPage.$vm.BackPage(this.searchValue); //重点$vm
				 uni.navigateBack({
				        delta: 1
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
page {
		width: 100%;
		height: 100%;
	}

	/deep/ .indicator-class {
		background: #EEF5FE;
		z-index: -1;
	}

	.to-btn {
		width: 686rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx 42rpx 42rpx 42rpx;
		text-align: center;
		line-height: 84rpx;
		margin: auto;
		font-family: 'PingFang SC-Bold, PingFang SC';
		position: fixed;
		bottom: 6vh;
		left: 32rpx;
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
		width: 686rpx;
		margin: auto;
		height: 708rpx;
		// width: 536rpx;
		// height: 508rpx;

		.search-res-c {
			width: 600rpx;
			margin: auto;
			max-height: 618rpx;
			font-size: 28rpx;
			overflow-y: auto;
			padding: 0 22rpx;
			box-sizing: border-box;
			background: rgba(255, 255, 255, 0.78);
			// box-shadow: 0px 10px 12px 2px rgba(134, 166, 243, 0.19);
			.search-res-item {
				height: 70rpx;
				font-family: 'PingFang SC-Regular, PingFang SC';
				line-height: 70rpx;
color: rgba(45, 49, 50, 0.9);
			}
		}

	}

	.select-c-div {
		width: 686rpx;
		height: 90rpx;
		background: #F8F8F8;
		display: flex;
		align-items: center;
		justify-content: space-between;
		border-radius: 46rpx 46rpx 46rpx 46rpx;
		// border: 2rpx solid #5B83E8;

		.u-input-item {


			width: 86%;
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

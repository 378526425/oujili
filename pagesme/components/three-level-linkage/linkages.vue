<template>
	<view class="">
		<view class="dsds" v-if="showPopup" @click="showPopup = false"> </view>
		<view class="linkage" v-if="showPopup" @touchmove.stop.prevent="clear">
			<!-- 遮罩层 -->


			<view class="linkage-content linkage--fixed">
				<view class="linkage__header">

					选择城市
				</view>
				<view class="linkage__box">
					<picker-view :indicator-style="indicatorStyle" indicator-class="indicator-class"
						class="linkage-view" :mask-style="fsdfds" :value="pickerValue" @change="pickerChange">

						<picker-view-column>
							<view class="picker-item" style="height: 92rpx;" v-for="(item,index) in firstLevel"
								:key="index" :class="one==index?'lldl':''">{{item.name}}</view>
						</picker-view-column>
						<picker-view-column v-if="secondShow">
							<view class="picker-item" v-for="(item,index) in secondLevel" :key="index"
								:class="two==index?'lldl':''">{{item.name}}</view>
						</picker-view-column>


					</picker-view>




				</view>



				<view class="button" @click="com">
					确定
				</view>
				<view class="" style="height: 54rpx;">

				</view>
			</view>

		</view>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>

</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		name: "levelLinkage",
		props: {
			// 开启动画
			animation: {
				type: Boolean,
				default: true
			},
			/* 弹出层类型，可选值；
				bottom：底部弹出层
			*/
			type: {
				type: String,
				default: 'bottom'
			},
			// maskClick
			maskClick: {
				type: Boolean,
				default: true
			},
			show: {
				type: Boolean,
				default: true
			},
			maskBgColor: {
				type: String,
				default: 'rgba(0, 0, 0, 0.4)', //背景颜色 rgba(0, 0, 0, 0.4) 为空则调用 uni.scss
			},
			themeColor: {
				type: String,
				default: '', // 主题色
			},
			/* 默认值 */
			pickerValueDefault: {
				type: Array,
				default () {
					return [0, 0]
				}
			},
		},
		data() {
			return {
				indicatorStyle: `height: 45px;color: rgba(51, 51, 51, 0.2);`,
				fsdfds: `opacity: 0.5;font-weight: 600;background: rgba(238, 245, 254, 0);`,
				secondShow: false,
				thirdShow: false,
				ani: '',
				showPopup: false,
				pickerValue: [0, 0],
				firstLevel: [],
				secondLevel: [],
				thirdLevel: [],
				one: 0,
				two: 0,
				emptyList: [{
					id: "",
					name: "",
					code: "",
					"children": []
				}],
				erji: "",
				obj: {},
				num: '',
				tipMsg: '',
				isConfirm: false
			}
		},
		components: {
			ELM
		},
		watch: {
			show(newValue) {
				if (newValue) {
					this.open()
				} else {
					this.close()
				}
			},
			pickerValueDefault() {
				this.init();
			}
		},
		created() {

			this.pull()
			this.init()
		},
		methods: {
			async pull() {
				this.one = 0
				this.two = 0
				this.firstLevel = []
				const res = await this.$myRequest({
					url: 'nostalgia/area/province',
					method: 'GET',
				});
				console.log(res, 'delshoucang');
				if (res.data.code == 200) {
					res.data.data.forEach((item, index) => {
						item.name = item.regionName
					})
					this.firstLevel = res.data.data
					this.erji = res.data.data[0].regionCode
					this.pulling()
				}else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			async pulls(obj) {
				this.one = 0
				this.two = 0
				this.firstLevel = []
				console.log(obj, "[[[[[[]]]]]]")
				obj.name = obj.regionName



				const res = await this.$myRequest({
					url: 'nostalgia/area/province',
					method: 'GET',
				});
				console.log(res, 'delshoucang');
				if (res.data.code == 200) {
					res.data.data.forEach((item, index) => {
						item.name = item.regionName
					})
					this.firstLevel = res.data.data

					for (var i = 0; i < this.firstLevel.length; i++) {
						if (this.firstLevel[i].regionName === obj.regionName) {
							this.firstLevel.splice(i, 1);
							break;
						}
					}
					this.firstLevel.unshift(obj);
					this.erji = this.firstLevel[0].regionCode
					this.obj.index = obj.index
					this.pullings()
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			async pullings() {


				this.two = 0
				const res = await this.$myRequest({
					url: 'nostalgia/area/son',
					data: {
						parentCode: this.erji
					},
					method: 'GET',
				});
				console.log(res, 'delshoucang');
				if (res.data.code == 200) {
					res.data.data.forEach((item, index) => {
						item.name = item.regionName
					})
					this.secondLevel = res.data.data

					this.obj.parentCode1 = this.firstLevel[this.one].parentCode
					this.obj.regionCode1 = this.firstLevel[this.one].regionCode
					this.obj.regionName1 = this.firstLevel[this.one].regionName
					this.obj.parentCode2 = this.secondLevel[0].parentCode
					this.obj.regionCode2 = this.secondLevel[0].regionCode
					this.obj.regionName2 = this.secondLevel[0].regionName
					this.obj.nameing = this.obj.regionName1 + '-' + this.secondLevel[0].regionName


				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			async pulling() {
				this.two = 0


				const res = await this.$myRequest({
					url: 'nostalgia/area/son',
					data: {
						parentCode: this.erji
					},
					method: 'GET',
				});
				console.log(res, 'delshoucang');
				if (res.data.code == 200) {
					res.data.data.forEach((item, index) => {
						item.name = item.regionName
					})

					this.secondLevel = res.data.data
					console.log(this.firstLevel[this.one].regionName, this.secondLevel[0].regionName, "pppppppppppppp")
					this.obj.parentCode1 = this.firstLevel[this.one].parentCode
					this.obj.regionCode1 = this.firstLevel[this.one].regionCode
					this.obj.regionName1 = this.firstLevel[this.one].regionName
					this.obj.parentCode2 = this.secondLevel[0].parentCode
					this.obj.regionCode2 = this.secondLevel[0].regionCode
					this.obj.regionName2 = this.secondLevel[0].regionName
					this.obj.nameing = this.obj.regionName1 + '-' + this.obj.regionName2

				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			init() {
				this.handPickValueDefault(); // 对 pickerValueDefault 做兼容处理
				this.firstLevel = allData;

				this.secondShow = this.pickerValueDefault[1] >= 0 ? true : false;
				this.thirdShow = this.pickerValueDefault[2] >= 0 ? true : false;;
				if (this.secondShow) {
					this.secondLevel = this.firstLevel[this.pickerValueDefault[0]].children.length == 0 ?
						this.emptyList : this.firstLevel[this.pickerValueDefault[0]].children;
				} else {
					this.thirdShow = false;
				}

				if (this.thirdShow) {
					this.thirdLevel = this.secondLevel[this.pickerValueDefault[1]].children.length == 0 ?
						this.emptyList : this.secondLevel[this.pickerValueDefault[1]].children;
				}

				this.pickerValue = this.pickerValueDefault;
			},
			handPickValueDefault() {
				if (this.pickerValueDefault !== [0, 0, 0]) {
					if (this.pickerValueDefault[0] > allData.length - 1) {
						this.pickerValueDefault[0] = allData.length - 1;
					}
					if (this.secondShow && this.pickerValueDefault[1] > allData[this.pickerValueDefault[0]].children
						.length - 1) {
						this.pickerValueDefault[1] = allData[this.pickerValueDefault[0]].children.length - 1;
					}
					if (this.thirdShow &&
						this.pickerValueDefault[2] >
						allData[this.pickerValueDefault[0]].children[this.pickerValueDefault[1]].children.length - 1) {
						this.pickerValueDefault[2] = allData[this.pickerValueDefault[0]].children[this.pickerValueDefault[
							1]].children.length - 1;
					}
				}
			},
			pickerChange(e) {
				console.log(e)
				this.one = e.detail.value[0]
				this.two = e.detail.value[1]
				let changePickerValue = e.detail.value;
				if (this.pickerValue[0] !== changePickerValue[0]) {

					// 第一级发生滚动
					if (this.secondShow) {
						this.secondLevel = this.firstLevel[changePickerValue[0]].children == 0 ?
							this.emptyList : this.firstLevel[changePickerValue[0]].children;
						changePickerValue[1] = 0;

					}

					this.erji = this.firstLevel[this.one].regionCode
					this.pulling()


				} else if (this.pickerValue[1] !== changePickerValue[1]) {
					// 第二级滚动
					if (this.thirdShow) {
						this.thirdLevel = this.secondLevel[changePickerValue[1]].children == 0 ?
							this.emptyList : this.secondLevel[changePickerValue[1]].children;
						changePickerValue[2] = 0;
					}
				}

				this.pickerValue = changePickerValue;
				this.obj.name1 = this.firstLevel[this.pickerValue[0]].name
				this.obj.parentCode1 = this.firstLevel[this.pickerValue[0]].parentCode
				this.obj.regionCode1 = this.firstLevel[this.pickerValue[0]].regionCode
				this.obj.regionName1 = this.firstLevel[this.pickerValue[0]].regionName
				this.obj.parentCode2 = this.secondLevel[this.pickerValue[1]].parentCode
				this.obj.regionCode2 = this.secondLevel[this.pickerValue[1]].regionCode
				this.obj.regionName2 = this.secondLevel[this.pickerValue[1]].regionName
				this.obj.name2 = this.secondLevel[this.pickerValue[1]].name
				this.obj.nameing = this.firstLevel[this.pickerValue[0]].name + '-' + this.secondLevel[this.pickerValue[1]]
					.regionName
				console.log(this.obj, this.pickerValue)


			},
			_$emit(emitName) {
				// 自定义选中项，选中内容
				let firstPick = {};
				let secondPick = {};
				let thirdPick = {};

				firstPick = {
					id: this._getFirst().id,
					name: this._getFirst().name,
					code: this._getFirst().code
				}
				if (this.secondShow) {
					secondPick = {
						id: this._getSecond().id,
						name: this._getSecond().name,
						code: this._getSecond().code
					}
				}

				if (this.thirdShow) {
					thirdPick = {
						id: this._getThird().id,
						name: this._getThird().name,
						code: this._getThird().code
					}
				}

				let pickObj = {
					name: this._getLabel(),
					value: this.pickerValue,
					secondPick: secondPick,
					thirdPick: thirdPick,
					firstPick: firstPick
				};
				this.$emit(emitName, pickObj);
			},
			_getLabel() {
				let pcikerLabel =
					this.firstLevel[this.pickerValue[0]].name;

				if (this.secondShow) {
					pcikerLabel = pcikerLabel + '-' +
						this.secondLevel[this.pickerValue[1]].name
				}

				if (this.thirdShow) {
					pcikerLabel = pcikerLabel + '-' +
						this.thirdLevel[this.pickerValue[2]].name;
				}

				return pcikerLabel;
			},
			_getSecond() {
				if (this.secondShow) {
					return this.secondLevel[this.pickerValue[1]];
				}
				return this.emptyList;
			},
			_getFirst() {
				return this.firstLevel[this.pickerValue[0]];
			},
			_getThird() {
				if (this.thirdShow) {
					return this.thirdLevel[this.pickerValue[2]];
				}
				return this.emptyList;
			},
			clear() {

			},
			com() {
				console.log(this.obj, "=====================")
				this.obj.num = this.num
				this.$emit('take', this.obj);
				this.close();
			},
			hideMask() {
				this._$emit('onCancel');
				this.close();
			},
			pickerCancel() {
				this._$emit('onCancel');
				this.close();
			},
			pickerConfirm() {
				this._$emit('onConfirm');
				this.close();
			},
			open(e) {
				console.log(e)
				this.num = e
				this.showPopup = true
				if (e !== '1') {

				} else {
					this.pull()

				}

			},
			close(type) {

				this.showPopup = false

			}
		}
	}
</script>

<style lang="scss" scoped>
	.button {
		width: 614rpx;
		height: 84rpx;
		background: linear-gradient(86deg, #C2D2F9 0%, #C5C2F3 100%);
		border-radius: 42rpx;
		margin: 0 auto;

		font-size: 28rpx;
		font-family: 'PingFang SC-Regular, PingFang SC';
		font-weight: 400;
		color: #415C9E;
		display: flex;
		align-items: center;
		justify-content: center;
		line-height: 84rpx;
		margin-top: 38rpx;
	}

	/deep/ .indicator-class {
		background: #EEF5FE;
		z-index: -1;
	}

	.linkage {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;

	}

	.linkage-mask {
		position: fixed;
		bottom: 0;
		top: 0;
		left: 0;
		right: 0;

		transition-property: opacity;
		transition-duration: 0.5s;
		opacity: 0;
		/* #ifndef APP-NVUE */
		z-index: 99;
		/* #endif */
	}


	.mask-ani {
		transition-property: opacity;
		transition-duration: 0s;
	}

	.linkage-c-bottom-mask {
		opacity: 1;
	}

	.linkage-c-center-mask {
		opacity: 1;
	}

	.linkage--fixed {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;

		/* #ifndef APP-NVUE */
		z-index: 99;
		/* #endif */

		border-radius: 36rpx 36rpx 0px 0px;
	}

	.linkage-content {
		background-color: #FFFFFF;
	}

	.linkage-c-content-bottom {
		bottom: 0;
		left: 0;
		right: 0;
		transform: translateY(500rpx);
	}

	.content-ani {
		transition-property: transform, opacity;
		transition-duration: 0.1s;
	}

	.linkage-c-bottom-content {
		transform: translateY(0);
	}

	.linkage-c-center-content {
		transform: scale(1);
		opacity: 1;
	}

	.linkage__header {
		position: relative;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		flex-wrap: nowrap;
		justify-content: center;

		font-size: 32rpx;
		font-family: 'PingFang SC-Heavy, PingFang SC';
		font-weight: 800;
		color: #333333;
		line-height: 32rpx;
		margin: 38rpx 0;
	}

	.linkage--fixed-top {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: space-between;
		border-top-color: $uni-border-color;
		border-top-style: solid;
		border-top-width: 1rpx;
	}

	/*弹窗遮罩层*/
	.dsds::before {
		content: "";
		cursor: default;
		z-index: 22;
		background-color: rgba(0, 0, 0, 0.6);
		position: fixed;
		left: 0;
		right: 0;
		top: 0;
		bottom: 0;
	}

	.linkage__header-btn-box {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		height: 92rpx;
	}

	.linkage__header-text {
		text-align: center;
		font-size: $uni-font-size-base;
		color: #1aad19;
		line-height: 70rpx;
		padding-left: 40rpx;
		padding-right: 40rpx;
	}

	.linkage__box {
		position: relative;
		height: 460rpx;
	}

	.linkage-view {
		position: relative;

		bottom: 0;
		left: 0;
		/* #ifndef APP-NVUE */
		width: 100%;
		/* #endif */
		/* #ifdef APP-NVUE */
		width: 750rpx;
		/* #endif */
		height: 460rpx;
		background-color: rgba(255, 255, 255, 1);
	}

	.picker-item {
		text-align: center;
		line-height: 92rpx;
		height: 92rpx !important;
		text-overflow: ellipsis;
		font-size: 36rpx;

		font-family: 'PingFang SC-Regular, PingFang SC';
		color: #B8B8B8;
		z-index: 11;
		opacity: 1;
font-weight: 400;
	}

	.lldl {
		font-family: 'PingFang SC-Bold, PingFang SC';
		font-weight: bold;
		color: #434343;
	}
</style>

<template>
	<view clipchildren=false>
		<!-- <text class="loading" :style="{opacity: lineLeft===0 ? '1' : '0'}">loading...</text> -->
		<view class="fj-slider-box" :style="{opacity: lineLeft!==0 ? '1' : '0'}" clipchildren=false>
		<!-- 	<view class="fj-value">
				<text class="fj-v-l"
					>{{fillMinValue}}</text>
				<text class="fj-v-r" >{{fillValue}}</text>
			</view> -->
			<text class="tip" :style="{left:curValue==100?`${tipLeft-22}px`:`${tipLeft-16}px`,opacity: tipShow ? '1' : '0'}">{{curValue}}</text>
			<view class="fj-slider" clipchildren=false>
				<view class="fj-line" ref="fj-line"></view>
				<view v-if="maxLeft-minLeft>=0" class="fj-line-pull"
					:style="{left: `${minLeft + touchWidth/2}px`, width:`${Math.abs(maxLeft - minLeft)}px`}"></view>
				<view v-else class="fj-line-pull"
					:style="{left: `${minLeft + touchWidth/2- Math.abs(maxLeft - minLeft) }px`, width:`${Math.abs(maxLeft - minLeft)}px`}">
				</view>
				<view class="" style="width: 100rpx;height: 100rpx;position: absolute;display: flex;justify-content: flex-start;margin-top: -40rpx;
				margin-left: -40rpx;"
				 ref="fjtouchleft" @touchstart="touchstart($event, 'min')" :style="{left:`${minLeft}px`}"
						@touchmove="touchmove($event, 'min')" @touchend="touchend($event, 'min')">
					<view class="fj-touch-left" style="margin-top: 40rpx;margin-left: 40rpx;margin-bottom: 20px;"> </view>
				</view>
				<view class="" style="width: 100rpx;height: 100rpx;position: absolute;display: flex;justify-content: flex-start;margin-top: -40rpx;
				margin-right: -40rpx;"  @touchstart="touchstart($event, 'max')" :style="{left:`${maxLeft-4}px`}" 
						@touchmove="touchmove($event, 'max')" @touchend="touchend($event, 'max')">
					<view class="fj-touch-right" 
						 style="margin-top: 40rpx;margin-right: 40rpx;margin-bottom: 20px;">
					</view>
				</view>
				
				
			</view>
		</view>
	</view>
</template>


<script>
	// #ifndef APP-PLUS
	const createSelectorQuery = (that) => {
		let query = uni.createSelectorQuery().in(that);
		// #ifdef MP-ALIPAY
		query = my.createSelectorQuery();
		// #endif 
		return query;
	}
	// #endif

	// #ifdef APP-PLUS
	const dom = weex.requireModule('dom');
	// #endif
	export default {
		name: 's-region-slider',
		props: {
			fillValue: {
				type: Number,
				default: 1000,
			},
			fillMinValue: {
				type: Number,
				default: 0,
			},
			minValue: {
				type: Number,
				default: 0,
			},
			maxValue: {
				type: Number,
				default: 1000,
			},
			step: {
				type: Number,
				default: 50,
			},
		},
		watch: {
			minValue(newVal, oldVla) {
				if (newVal < this.fillMinValue || this.maxValue > this.fillValue) {
					console.error(`请在${this.fillMinValue}-${this.fillValue}范围中设置`)
					return;
				}
				this.sMinValue = newVal - this.fillMinValue;
				this.showMinNum = newVal;
				this.minLeft = this.sMinValue / this.percentage;
			},
			maxValue(newVal, oldVla) {
				if (this.minValue < this.fillMinValue || newVal > this.fillValue) {
					console.error(`请在${this.fillMinValue}-${this.fillValue}范围中设置`)
					return;
				}
				this.sMaxValue = newVal - this.fillMinValue;
				this.showMaxNum = newVal;
				this.maxLeft = this.sMaxValue / this.percentage;
			},
			fillValue(newVal, oldVla) {
				this.sFillValue = this.fillValue;
			}
		},
		data() {
			return {
				tipShow: false,
				tipLeft: 0,

				minLeft: 0,
				maxLeft: 0,

				touchWidth: 30,

				lineWidth: 0,
				lineLeft: 0,

				showMinNum: 0,
				showMaxNum: 0,

				curValue: 0,

				sMinValue: 0,
				sMaxValue: 0,
				sFillValue: 0,
				sFillMinValue: 0,

				percentage: 0
			};
		},
		mounted() {
			this.$nextTick().then(() => {
				this.envir({
					classname: '.fj-touch-left',
					refname: this.$refs.fjtouchleft,
					fn: (ret) => {
						this.touchWidth = ret.width;
					}
				})
				this.envir({
					classname: '.fj-line',
					refname: this.$refs['fj-line'],
					fn: (ret) => {
						this.lineWidth = ret.width;
						this.lineLeft = ret.left; // 

						this.sMinValue = (this.minValue - this.fillMinValue) > 0 ? this.minValue - this
							.fillMinValue : 0;
						this.sMaxValue = (this.maxValue - this.fillMinValue) > 0 ? this.maxValue - this
							.fillMinValue : 0;
						this.sFillValue = (this.fillValue - this.fillMinValue) > 0 ? this.fillValue -
							this.fillMinValue : 0;

						this.percentage = this.sFillValue / this.lineWidth;

						this.minLeft = this.sMinValue / this.percentage;
						this.maxLeft = this.sMaxValue / this.percentage;

						this.showMaxNum = this.sMaxValue + this.fillMinValue;
						this.showMinNum = this.sMinValue + this.fillMinValue;
					}
				})
			})

		},
		methods: {
			envir(opt) {
				setTimeout(() => {
					// #ifdef APP-PLUS
					dom.getComponentRect(opt.refname, ret => {
						const option = ret.size
						opt.fn({
							width: option.width,
							height: option.height,
							top: option.top,
							bottom: option.bottom,
							left: option.left,
							right: option.right,
						})
					})
					// #endif

					// #ifndef APP-PLUS
					createSelectorQuery(this).select(opt.classname).boundingClientRect().exec((data) => {
						const option = data[0];
						opt.fn({
							width: option.width,
							height: option.height,
							top: option.top,
							bottom: option.bottom,
							left: option.left,
							right: option.right,
						})
					})
					// #endif
				}, 200)
			},
			touchstart(e, type) {
				this.$emit('down', {
					...e,
					custom: {
						type,
						minValue: this.showMinNum,
						maxValue: this.showMaxNum
					}
				})
			},
			touchmove(e, type) {
				// #ifndef APP-PLUS
				const disX = e.touches[0].clientX - this.lineLeft
				// #endif
				// #ifdef APP-PLUS
				const disX = e.touches[0].screenX - this.lineLeft
				// #endif
				if (disX < 0 || disX > this.lineWidth) {
					return;
				}

				if (type === 'min') {
					this.minLeft = Math.floor(disX);
					if (this.minLeft < 0) {
						this.minLeft = 0;
						return;
					}
					if (this.maxLeft > this.lineWidth) {
						this.maxLeft = this.lineWidth;
						return;
					}
					//if(this.maxLeft - this.minLeft <= this.touchWidth ) {this.minLeft = this.maxLeft - this.touchWidth; return;}
					this.curValue = Math.floor(this.minLeft * this.percentage);
				}

				if (type === 'max') {
					this.maxLeft = Math.ceil(disX);
					if (this.minLeft < 0) {
						this.minLeft = 0;
						return;
					}
					if (this.maxLeft > this.lineWidth) {
						this.maxLeft = this.lineWidth;
						return;
					}
					// if(this.maxLeft - this.minLeft <= this.touchWidth ) {this.maxLeft = this.minLeft + this.touchWidth;return;}
					this.curValue = Math.round(this.maxLeft * this.percentage);
				}


				this.tipShow = true;
				this.tipLeft = Math.round((this.curValue) / this.percentage);
				this.tipLeft = this.tipLeft >= this.lineWidth ? this.lineWidth : this.tipLeft
				this.tipLeft = this.tipLeft <= 0 ? 0 : this.tipLeft

				this.curValue = this.curValue + this.fillMinValue;

				this.$emit('move', {
					...e,
					custom: {
						type,
						minValue: this.showMinNum,
						maxValue: this.showMaxNum,
						curValue: this.curValue,
					}

				})
			},
			touchend(e, type) {
				if (type === 'min') {
					if (this.step === 1) {
						this.sMinValue = this.curValue - this.fillMinValue;
					} else {
						const stepnum = Math.round((this.minLeft * this.percentage) / this.step);
						this.sMinValue = stepnum * this.step;
						this.minLeft = this.sMinValue / this.percentage;
					}
				}
				if (type === 'max') {
					if (this.step === 1) {
						this.sMaxValue = this.curValue - this.fillMinValue;
					} else {
						const stepnum = Math.round((this.maxLeft * this.percentage) / this.step);
						this.sMaxValue = stepnum * this.step;
						if (this.sFillValue - this.sMaxValue < this.step) {
							this.sMaxValue = this.sFillValue
						}
						this.maxLeft = this.sMaxValue / this.percentage;
					}
				}
				this.tipShow = false;

				if (this.sMinValue <= this.sMaxValue) {
					this.showMaxNum = this.sMaxValue + this.fillMinValue;
					this.showMinNum = this.sMinValue + this.fillMinValue;
				} else {
					this.showMaxNum = this.sMinValue + this.fillMinValue;
					this.showMinNum = this.sMaxValue + this.fillMinValue;
				}

				this.$emit('up', {
					...e,
					custom: {
						type,
						minValue: this.showMinNum < this.fillMinValue ? this.fillMinValue : this.showMinNum,
						maxValue: this.showMaxNum > this.fillValue ? this.fillValue : this.showMaxNum
					}
				})
			},
		},
	};
</script>


<style scoped>
	.fj-slider-box {
		font-size: 12px;
		position: relative;
	}

	.loading {
		font-size: 12px;
	}

	.fj-value {
		position: relative;
		height: 50rpx;width: 686rpx;
		display: flex;align-items: center;
	}


	.fj-v-l {
		
		height: 50rpx;
		font-size: 36rpx;
		font-family: PingFang SC-Regular, PingFang SC;
		font-weight: 400;
		color: #333333;
		line-height: 50rpx;
	}

	.fj-v-r {
		height: 50rpx;
		font-size: 32rpx;
		font-family: PingFang SC-Bold, PingFang SC;
		font-weight: bold;
		color: #446CD2;
		line-height: 50rpx;
		margin-left: 24rpx;
	}

	.fj-slider {
		position: relative;
		height: 30px;
	}

	.fj-touch-left,
	.fj-touch-right {
		position: absolute;
		width: 20rpx;
		height: 20rpx;
		background: #EBEEF3;
		border-radius: 50%;
		z-index: 3;
	}

	.tip {
		position: absolute;
		padding: 10px;
		background: rgba(0, 0, 0, 0.6);
		color: #fff;
		top: -60px;
		border-radius: 10px;
		z-index: 4;
		font-size: 10px;
		justify-content: center
	}

	.fj-line,
	.fj-line-pull {
		height: 5px;
		position: absolute;
		top: 3px;
		left: 0px;
		right: 15px;background: #E6E6E6;
		width: 682rpx;
		border-radius: 4rpx;
		z-index: 1;
	}

	.fj-line-pull {
		z-index: 2;
		background: #446CD2;

	}
</style>

<template>
	<view>
		<view class="date-text">{{ year }}年{{ month }}月{{ day }}日</view>
		<picker-view v-if="visible" :indicator-style="indicatorStyle" :value="value" @change="bindChange"
			@pickend="pickend" class="picker-view" indicator-class="indicator-class" :mask-style="fsdfds">
			<picker-view-column>
				<view class="item" style="text-align: right;" v-for="(item, index) in years" :key="index"
					:class="year == item ? 'lldl' : ''">{{ item }}</view>
			</picker-view-column>
			<picker-view-column>
				<view class="item" v-for="(item, index) in months" :key="index" :class="month == item ? 'lldl' : ''">
					{{ item }}
				</view>
			</picker-view-column>
			<picker-view-column>
				<view class="item" style="text-align: left;" v-for="(item, index) in days" :key="index"
					:class="day == item ? 'lldl' : ''">{{ item }}</view>
			</picker-view-column>
		</picker-view>
	</view>
</template>

<script>
	// import provinces from '../../libs/util/province.js';
	// import citys from '../../libs/util/city.js';
	// import areas from '../../libs/util/area.js';

	/**
	 * picker picker弹出选择器
	 * @description 此选择器有两种弹出模式：一是时间模式，可以配置年，日，月，时，分，秒参数 二是地区模式，可以配置省，市，区参数
	 * @tutorial https://www.uviewui.com/components/picker.html
	 * @property {Object} params 需要显示的参数，见官网说明
	 * @property {String} mode 模式选择，region-地区类型，time-时间类型（默认time）
	 * @property {String Number} start-year 可选的开始年份，mode=time时有效（默认1950）
	 * @property {String Number} end-year 可选的结束年份，mode=time时有效（默认2050）
	 * @property {Boolean} safe-area-inset-bottom 是否开启底部安全区适配（默认false）
	 * @property {Boolean} show-time-tag 时间模式时，是否显示后面的年月日中文提示
	 * @property {String} cancel-color 取消按钮的颜色（默认#606266）
	 * @property {String} confirm-color 确认按钮的颜色（默认#2979ff）
	 * @property {String} default-time 默认选中的时间，mode=time时有效
	 * @property {String} confirm-text 确认按钮的文字
	 * @property {String} cancel-text 取消按钮的文字
	 * @property {String} default-region 默认选中的地区，中文形式，mode=region时有效
	 * @property {String} default-code 默认选中的地区，编号形式，mode=region时有效
	 * @property {Boolean} mask-close-able 是否允许通过点击遮罩关闭Picker（默认true）
	 * @property {String Number} z-index 弹出时的z-index值（默认1075）
	 * @property {Array} default-selector 数组形式，其中每一项表示选择了range对应项中的第几个
	 * @property {Array} range 自定义选择的数据，mode=selector或mode=multiSelector时有效
	 * @property {String} range-key 当range参数的元素为对象时，指定Object中的哪个key的值作为选择器显示内容
	 * @event {Function} confirm 点击确定按钮，返回当前选择的值
	 * @event {Function} cancel 点击取消按钮，返回当前选择的值
	 * @example <u-picker v-model="show" mode="time"></u-picker>
	 */
	export default {
		data: function() {
			const date = new Date();
			const years = [];
			const year = date.getFullYear();
			const months = [];
			const month = date.getMonth() + 1;
			const days = [];
			const day = date.getDate();
			for (let i = 1990; i <= date.getFullYear(); i++) {
				years.push(i);
			}
			for (let i = 1; i <= 12; i++) {
				months.push(i);
			}
			for (let i = 1; i <= 31; i++) {
				days.push(i);
			}
			return {
				title: 'picker-view',
				years,
				year,
				months,
				month,
				days,
				day,
				value: [9999, month - 1, day - 1],
				visible: true,
				indicatorStyle: `height: 45px;color: rgba(51, 51, 51, 0.2);background:rgba(238, 245, 254, 0.5);`,
				fsdfds: `opacity: 0.5;background: rgba(238, 245, 254, 0);`
			};
		},
		methods: {
			pickend(e) {
				console.log(e);
			},
			bindChange: function(e) {
				console.log(e);
				const val = e.detail.value;
				this.year = this.years[val[0]];
				this.month = this.months[val[1]];
				this.day = this.days[val[2]];
				var curTime = this.year + '-' + this.month + '-' + this.day + ' ' + '00' + ':' + '00' + ':' + '00';
				this.$emit('curTime', curTime);
			}
		}
	};
</script>

<style lang="scss" scoped>
	
	.date-text {
		text-align: center;
		font-size: 36rpx;
		// font-family: 'PingFang SC-Bold, PingFang SC';

		color: rgba(67, 67, 67, 0.9);
		margin-bottom: 40rpx;

		font-weight: 400;
	}

	.picker-view {
		width: 750rpx;
		height: 300rpx;
		margin-top: 20rpx;
		background: rgba(238, 245, 254, 0);
	}

	.item {
		height: 90rpx;
		align-items: center;
		justify-content: center;
		text-align: center;
		line-height: 90rpx;
		font-size: 32rpx;
		// font-family:'PingFang SC-Regular, PingFang SC';

		font-weight: 400;
		color: #B8B8B8;

	}
	
	.lldl {
	  font-weight: bold;
	  color: #434343;
	 }
</style>

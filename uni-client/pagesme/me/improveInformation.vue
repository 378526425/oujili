<template>
	<view>
		<view class="w-100  pt30 pb60">
			<view class="pr40 pl72 pt40 rowa" v-if="userInfo.waitApprovedStatus!='PASS'">
				<view class="mw32 mt8 h32 bc000 cfff br-50 tc lh32 fs24">!</view>
				<view class="ml14 c333 fs28 lh44">
					{{userInfo.remarks==null?"请上传个人的真实照片，通过后这样才能匹配到高质量好友哦~":userInfo.remarks}}
				</view>
			</view>
			<view class="pr32 pl32  rowa">
				<view class="relative" @click="showSingleNoPopup(9)">
					<image v-if="userInfo.waitApprovedImg!=null" class="w328 br24 h328 blc "
						:src="userInfo.waitApprovedImg[0]" mode="aspectFill"></image>
					<view class="absolute w328 h328 rowjcic  top0">
						<view class="w186 h52 lh52 tc cfff fs28 br10" style="background-color: rgba(0,0,0,0.3);">头像/封面
						</view>
					</view>
				</view>
				<view class="h328 crowjb">
					<view class="ml30 w-100">
						<view class="rowjbic  pr30 w-100 warp h328">
							<!-- 	<view @click="showSingleNoPopup(index)" v-if="index>0 && index<5"
								v-for="(item,index) in waitApprovedImg" :key='index'>
								<image class="w150 h150 br24 blc" :src="item" mode="aspectFill"></image>
							</view>
							<block v-if="5-waitApprovedImg.length>0" class="fs0">
								<view @click="chooseImg('add')" v-for="(item,index) in 5-waitApprovedImg.length" :key='index'
									class="w150 fs99 tc  h150 br24 blc"
									style="color: #D5D5D5; background-color: #EBEBEB;">
									+
								</view>
							</block> -->

							<view @click="showSingleNoPopup(1)"
								v-if="userInfo.waitApprovedImg!=null&&userInfo.waitApprovedImg.length>1">
								<image class="w150 h150 br24 blc" :src="userInfo.waitApprovedImg[1]" mode="aspectFill">
								</image>
							</view>
							<block v-else class="fs0">
								<view @click="chooseImg('add')" class="w150 fs99 tc  h150 br24 blc"
									style="color: #D5D5D5; background-color: #EBEBEB;">
									+
								</view>
							</block>

							<view @click="showSingleNoPopup(2)"
								v-if="userInfo.waitApprovedImg!=null&&userInfo.waitApprovedImg.length>2">
								<image class="w150 h150 br24 blc" :src="userInfo.waitApprovedImg[2]" mode="aspectFill">
								</image>
							</view>
							<block v-else class="fs0">
								<view @click="chooseImg('add')" class="w150 fs99 tc  h150 br24 blc"
									style="color: #D5D5D5; background-color: #EBEBEB;">
									+
								</view>
							</block>

							<view @click="showSingleNoPopup(3)"
								v-if="userInfo.waitApprovedImg!=null&&userInfo.waitApprovedImg.length>3">
								<image class="w150 h150 br24 blc" :src="userInfo.waitApprovedImg[3]" mode="aspectFill">
								</image>
							</view>
							<block v-else class="fs0">
								<view @click="chooseImg('add')" class="w150 fs99 tc  h150 br24 blc"
									style="color: #D5D5D5; background-color: #EBEBEB;">
									+
								</view>
							</block>

							<view @click="showSingleNoPopup(4)"
								v-if="userInfo.waitApprovedImg!=null&&userInfo.waitApprovedImg.length>4">
								<image class="w150 h150 br24 blc" :src="userInfo.waitApprovedImg[4]" mode="aspectFill">
								</image>
							</view>
							<block v-else class="fs0">
								<view @click="chooseImg('add')" class="w150 fs99 tc  h150 br24 blc"
									style="color: #D5D5D5; background-color: #EBEBEB;">
									+
								</view>
							</block>

						</view>
					</view>

				</view>
			</view>
		</view>
		<view class="w690 p30 bcfff br20 ml30 mt35" style="margin-top: -30rpx;">
			<view class="rowjbic">
				<view class="rowic">
					<image src="../../static/images/indexabout.png" class="w30 h30" mode="aspectFill"></image>
					<view style="color: #434343;" class="fs28 fwb ml10">基础资料</view>
				</view>
				<view class="w120 h52 lh52 tc br100 fs28" @click="gotoeditInfo"
					style="color: #434343; background-color: #F5F6FA;">编辑</view>
			</view>
			<view class="rowic mt40">
				<view class="fs40 fwb" style="color: #4A4A4A;">{{userInfo.nickName}}</view>
				<image class="w48 h48 br-50 ml30"
					:src="userInfo.gender=='MALE' ?'/static/images/like/male.png': '/static/xiugai/nv.png'" mode="aspectFill">
				</image>
			</view>
			<view class="fs28 fwb c333 rowic mt25">
				<view><text v-if="userInfo.age">{{userInfo.age}}</text>岁 · <text
						v-if="userInfo.height">{{userInfo.height}}</text>cm · <text
						v-if="userInfo.constellation">{{userInfo.constellation}}</text></view>
				<view class="fs24 w3 ml30 mr30 h24" style="background-color: #E0E2E8;"></view>
				<view> <text v-if="userInfo.city">{{userInfo.city}} </text></view>
			</view>
			<view class="rowic mt36" v-if="userInfo.emotional!=null">
				<image class="w42 h42" src="../../static/images/indexlable.png" mode="aspectFill"></image>
				<view class="fs24 ml10 c333" v-if="userInfo.emotional=='Single'">正在寻觅</view>
				<view class="fs24 ml10 c333" v-if="userInfo.emotional=='Love'">热恋中</view>
			</view>
			<view class="w690 rowjbic mt56" style="margin-left: -30rpx;">
				<view class="circle"></view>
				<view v-for="(item,index) in 10" :key='index' class="w30 h3" style="background-color:  #C8D3E2;"></view>
				<view class="circleA"></view>
			</view>
			<view class="rowjbic warp mt25">
				<view v-if="userInfo.profession" class="w285 mt25 h60 tc fs28 c333 lh60 br100"
					style="background-color: #E5F4FF;">
					职业：{{userInfo.profession}}</view>
				<view v-if="userInfo.annualSalary" class="w285 mt25 h60 tc fs28 c333 lh60 br100"
					style="background-color: #FFE3DE;">年薪：
					<text v-if="userInfo.annualSalary=='Less_Ten'">小于10w</text>
					<text v-if="userInfo.annualSalary=='Ten_Two'">10w到20w</text>
					<text v-if="userInfo.annualSalary=='Two_Three'">20w到30w</text>
					<text v-if="userInfo.annualSalary=='Three_Five'">30w到50w</text>
					<text v-if="userInfo.annualSalary=='Five_Hundred'">50w到100w</text>
					<text v-if="userInfo.annualSalary=='Greater_Hundred'">大于100w</text>
					<text v-if="userInfo.annualSalary=='Secrecy'">保密</text>
				</view>
				<view v-if="userInfo.marriage" class="w285 mt25 h60 tc fs28 c333 lh60 br100"
					style="background-color: #F2E8F9;">婚姻状况：
					<text v-if="userInfo.marriage=='Unmarried'">未婚</text>
					<text v-if="userInfo.marriage=='Divorce'">离异</text>
					<text v-if="userInfo.marriage=='Widow'">丧偶</text>
				</view>

			</view>
		</view>
		<view class="w690 p30 bcfff br20 mt30 ml30 p30">
			<view class="rowic">
				<image class="w30 h30" src="/static/xiugai/2.png" mode="aspectFill"></image>
				<view style="color: #434343;" class="fs28 fwb ml10">双重认证（{{numtwo}}/2）</view>

			</view>
			<view class="rowjbic mt25">
				<view class="fs28 fwb" style="color: #434343;">实名认证</view>
				<view class="fs24" style="color: #434343;">认证后配对成功率提升200%</view>
				<view v-if="userInfo.identityAuth=='EXAMINE'" class="w120 h52 lh52 tc br100 fs28"
					style="color: #434343; background-color: #F5F6FA;" @click="gotomation()">审核中</view>
				<view v-if="userInfo.identityAuth=='PASS'" class="w120 h52 lh52 tc br100 fs28"
					style="color: #434343; background-color: #F5F6FA;" @click="gotomation()">已认证</view>
				<view v-if="userInfo.identityAuth=='REFUSE'" class="w120 h52 lh52 tc br100 fs28"
					style="color: #434343; background-color: #F5F6FA;" @click="gotomation()">已拒绝</view>
				<view v-if="userInfo.identityAuth==null" class="w120 h52 lh52 tc br100 fs28"
					style="color: #434343; background-color: #F5F6FA;" @click="gotomation()">未认证</view>
			</view>
			<view class="rowjbic mt30">
				<view class="fs28 fwb" style="color: #434343;">学历认证</view>
				<view class="fs24" style="color: #434343;" v-if="userInfo.school">{{userInfo.school}}
					<text class="ml10"></text>
					<text v-if="userInfo.education=='Specialty'">专科</text>
					<text v-if="userInfo.education=='Undergraduate'">本科</text>
					<text v-if="userInfo.education=='Master'">硕士</text>
					<text v-if="userInfo.education=='Doctor'">博士</text>
					·
					<text v-if="userInfo.educationalType=='FullTime'" class="ml10">全日制</text>
					<text v-if="userInfo.educationalType=='NOFullTime'" class="ml10">非全日制</text>
				</view>
				<view v-if="userInfo.educationAuth=='EXAMINE'" class="w120 h52 lh52 tc br100 fs28"
					style="color: #434343; background-color: #F5F6FA;" @click="gotoeducation()">审核中</view>
				<view v-if="userInfo.educationAuth=='PASS'" class="w120 h52 lh52 tc br100 fs28"
					style="color: #434343; background-color: #F5F6FA;" @click="gotoeducation()">已认证</view>
				<view v-if="userInfo.educationAuth=='REFUSE'" class="w120 h52 lh52 tc br100 fs28"
					style="color: #434343; background-color: #F5F6FA;" @click="gotoeducation()">已拒绝</view>
				<view v-if="userInfo.educationAuth==null" class="w120 h52 lh52 tc br100 fs28"
					style="color: #434343; background-color: #F5F6FA;" @click="gotoeducation()">未认证</view>
			</view>
		</view>
		<view class="w690 p30 bcfff br20 mt30 ml30 p30">
			<view class="rowjbic">
				<view class="rowic">
					<image class="w30 h30" src="/static/images/indexinterest.png" mode="aspectFill"></image>
					<view style="color: #434343;" class="fs28 fwb ml10">关于我自己</view>

				</view>
				<view class="w120 h52 lh52 tc br100 fs28" @click="gotoEditText(1,userInfo.aboutMe)"
					style="color: #434343; background-color: #F5F6FA;">编辑</view>
			</view>
			<view class=" mt25 fs28" style="color: #434343; ">
				<view>{{userInfo.aboutMe}}</view>
			</view>
		</view>
		<view class="w690 p30 bcfff br20 mt30 ml30 p30">
			<view class="rowjbic">
				<view class="rowic">
					<image class="w30 h30" src="/static/images/indexlove.png" mode="aspectFill"></image>
					<view style="color: #434343;" class="fs28 fwb ml10">兴趣爱好</view>

				</view>
				<view class="w120 h52 lh52 tc br100 fs28" @click="gotoEditText(2,userInfo.interest)"
					style="color: #434343; background-color: #F5F6FA;">编辑</view>
			</view>
			<view class=" mt25 fs28" style="color: #434343; ">
				<view>{{userInfo.interest}}</view>
			</view>
		</view>
		<view class="w690 p30 bcfff br20 mt30 ml30 p30">
			<view class="rowjbic">
				<view class="rowic">
					<image class="w30 h30" src="/static/images/indexcharacter.png" mode="aspectFill"></image>
					<view style="color: #434343;" class="fs28 fwb ml10">心仪的Ta</view>

				</view>
				<view class="w120 h52 lh52 tc br100 fs28" @click="gotoEditText(3,userInfo.loveRequirement)"
					style="color: #434343; background-color: #F5F6FA;">编辑</view>
			</view>
			<view class=" mt25 fs28" style="color: #434343; ">
				<view>{{userInfo.loveRequirement}}</view>
			</view>

		</view>
		<view class="h40"></view>

		<!-- 单列不滚动 -->
		<u-popup :show="singleNoShow" @close="singleNoClose" mode="bottom">
			<view class="singleNo" style="border-radius: 24rpx 24rpx 0px 0px;">
				<view class="singleNo-item" @click="singleNoItem(1)">替换</view>
				<view class="singleNo-item" @click="singleNoItem(2)" v-if="singleIndex!='9'">删除</view>
				<view class="linkage-close" @click="singleNoClose">
					取消
				</view>
			</view>
		</u-popup>
		<ELM ref="elm" :msg="tipMsg" :isConfirm="isConfirm" @confirm="confirm"></ELM>
	</view>
</template>

<script>
	import ELM from '@/components/elm-toast/index.vue';
	export default {
		data() {
			return {
				multiple: 100, //倍数
				percent: 0, //百分比
				perceptualScore: 0,

				value: 40,
				img: this.$BASE_URL,
				bgColor: 'rgb(232,241,247)',
				userInfo: {},
				// 新上传的图片路径
				newImageSrc: "",
				// 单列不滚动
				singleNoShow: false,
				singleIndex: 0,
				numtwo: 0,
				waitApprovedImg: [],
				tipMsg: '',
				isConfirm: false
			}
		},
		components: {
			ELM
		},
		onLoad(option) {
			//  this.$myRequest({
			// 	url: '/test/delete/fruser/'+51,
			// 	withToken: true,
			// 	method: 'GET',
			// })
			uni.$once('updateAboutMe', this.updateAboutMe)
			uni.$once('updateLoveRequirement', this.updateLoveRequirement)
			uni.$once('updateInterest', this.updateInterest)

		},
		onShow() {
			this.getUserIno()
		},
		methods: {
			gotomation() {
				// uni.navigateTo({
				// 	url:"/pagesme/me/identityauthentication?type="+type
				// })
				uni.navigateTo({
					url: "/pagesme/me/doubleauth"
				})
			},
			gotoeducation(type) {
				// uni.navigateTo({
				// 	url:"/pagesme/me/educationcertification?type="+type
				// })
				uni.navigateTo({
					url: "/pagesme/me/doubleauth?type=" + type
				})
			},
			BackPage(val) {
				console.log(val);
				if (val.type == 1) {
					this.userInfo.aboutMe = val.value
				} else {
					this.userInfo.loveRequirement = val.value
				}
			},
			// 单列不滚动
			singleNoClose() {
				this.singleNoShow = false;
				// userInfo.waitApprovedImg
			},
			showSingleNoPopup(Index) {
				console.log(Index);
				if (Index == 9) {
					this.singleIndex = 9;
				} else {
					// Index++
					this.singleIndex = Index;
				}
				console.log(Index);
				this.singleNoShow = true;
			},
			singleNoItem(type) {
				this.singleNoShow = false;
				if (type == 1) {
					this.chooseImg("REPLACE")
				} else if (type == 2) {
					this.updateImage("DELETE")
				}
			},
			chooseImg(type) {
				console.log(1);
				uni.chooseImage({
					count: 1,
					success: (res) => {
						res.tempFilePaths.forEach((i) => {
							uni.showLoading({
								title: "图片上传中"
							})
							uni.uploadFile({
								url: this.$BASE_URL + '/file/upload',
								filePath: i,
								name: 'file',
								formData: {
									'file': 'file'
								},
								success: (res) => {
									uni.hideLoading();
									let dataing = JSON.parse(res.data)
									console.log(dataing)
									this.newImageSrc = dataing.data.url
									console.log(this.newImageSrc)
									this.updateImage(type);
								},
								fail: (loginErr) => {
									uni.hideLoading();
									this.tipMsg = "上传失败，请重试";
									this.$refs.elm.showDialog();
								},
								complete() {}
							});
						});
					}
				});
			},
			async updateImage(type) {
				var that = this;
				if (type == 'add') {
					var data = {
						"newUrl": that.newImageSrc,
						"oldUrl": "",
						"photoEditType": "ADD"
					}
				} else if (type == 'REPLACE') {
					if (this.singleIndex == 9) {
						this.singleIndex = 0;
					}
					var data = {
						"newUrl": that.newImageSrc,
						"oldUrl": that.userInfo.waitApprovedImg[that.singleIndex],
						"photoEditType": "REPLACE"
					}
				} else if (type == 'DELETE') {
					var data = {
						"newUrl": "",
						"oldUrl": that.userInfo.waitApprovedImg[that.singleIndex],
						"photoEditType": "DELETE"
					}
				}

				// uni.showLoading({
				// 	title:"更新中"
				// })
				const res = await that.$myRequest({
					url: '/nostalgia/fruser/photoEdit',
					withToken: true,
					data: data,
					method: 'PUT',
				});
				uni.hideLoading();
				this.singleNoShow = false;
				if (res.data.code == 200) {
					this.getUserIno();
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			updateAboutMe(e) {
				console.log(e);
				this.userInfo.aboutMe = e.data;
			},
			updateLoveRequirement(e) {
				console.log(e);
				this.userInfo.loveRequirement = e.data;
			},
			updateInterest(e) {
				this.userInfo.interest = e.data;
			},
			change(e) {
				console.log(e);
			},
			gotoEditText(type, value) {
				var that = this;
				if (type) {
					console.log(value);
					uni.setStorageSync("editText", value)
					uni.navigateTo({
						url: "/pagesme/me/editAbout?type=" + type
						// url:"/pagesintroduction/selectSchool/selectSchool"
					})
				} else {

				}
			},
			gotoeditInfo() {
				this.tipMsg = "开源版暂未开放，敬请期待！如需旗舰版，可联系作者微信（MMRWXM）咨询";
				this.$refs.elm.showDialog();
			},
			async getUserIno() {
				const res = await this.$myRequest({
					url: '/nostalgia/fruser/personalInfo',
					withToken: true,
					method: 'GET',
				});
				if (res.data.code == 200) {
					debugger
					this.userInfo = res.data.data;
					this.waitApprovedImg = res.data.data.waitApprovedImg;
				} else {
					this.tipMsg = res.data.msg;
					this.$refs.elm.showDialog();
				}
			},
			sliderChange(e) {
				console.log(e)
				this.perceptualScore = e.detail.value;
			}
		}
	}
</script>

<style lang="scss">
	::v-deep .uni-slider-handle-wrapper {
		background-color: #000000;
	}

	page {
		background-color: #F4F5F9;
	}

	view,
	scroll-view,
	swiper,
	button,
	input,
	textarea,
	label,
	image {
		box-sizing: border-box; //设置padding和border 不会改变盒子原有的大小
	}

	.h3 {
		height: 3rpx;
	}

	.mt30 {
		margin-top: 30rpx;
	}

	.h60 {
		height: 60rpx;
	}

	.lh60 {
		line-height: 60rpx;
	}

	.w285 {
		width: 285rpx;
	}

	.mt56 {
		margin-top: 56rpx;
	}

	.h42 {
		height: 42rpx;
	}

	.w42 {
		width: 42rpx;
	}

	.mt36 {
		margin-top: 36rpx;
	}

	.h24 {
		height: 24rpx;
	}

	.mr30 {
		margin-right: 30rpx;
	}

	.w3 {
		width: 3rpx;
	}

	.mt25 {
		margin-top: 25rpx;
	}

	.h48 {
		height: 48rpx;
	}

	.w48 {
		width: 48rpx;
	}

	.fs40 {
		font-size: 40rpx;
	}

	.mt40 {
		margin-top: 40rpx;
	}

	.br100 {
		border-radius: 100rpx;
	}

	.w120 {
		width: 120rpx;
	}

	.ml10 {
		margin-left: 10rpx;
	}

	.fwb {
		font-weight: bold;
	}

	.h30 {
		height: 30rpx;
	}

	.w30 {
		width: 30rpx;
	}

	.rowic {
		display: flex;
		align-items: center;
	}

	.mt35 {
		margin-top: 35rpx;
	}

	.ml30 {
		margin-left: 30rpx;
	}

	.br20 {
		border-radius: 20rpx;
	}

	.bcfff {
		background-color: #FFFFFF;
	}

	.p30 {
		padding: 30rpx;
	}

	.w690 {
		width: 690rpx;
	}

	.relative {
		position: relative;
	}

	.bc000 {
		background-color: #000000;
	}

	.fs99 {
		font-size: 99rpx;
	}

	.br24 {
		border-radius: 24rpx;
	}

	.h150 {
		height: 150rpx;
	}

	.w150 {
		width: 150rpx;
	}

	.warp {
		flex-wrap: wrap;
	}

	.pr30 {
		padding-right: 30rpx;
	}

	.rowjbic {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.ml30 {
		margin-left: 30rpx;
	}

	.crowjb {
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.br10 {
		border-radius: 10rpx;
	}

	.h52 {
		height: 52rpx;
	}

	.lh52 {
		line-height: 52rpx;
	}

	.w186 {
		width: 186rpx;
	}

	.top0 {
		top: 0rpx;
	}

	.absolute {
		position: absolute;
	}

	.rowjcic {
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.blc {
		background: #eee;
	}

	.h328 {
		height: 328rpx;
	}

	.br24 {
		border-radius: 24rpx;
	}

	.w328 {
		width: 328rpx;
	}

	.pt40 {
		margin-bottom: 40rpx;
	}

	.pl32 {
		padding-left: 32rpx;
	}

	.pr32 {
		padding-right: 32rpx;
	}

	.lh44 {
		line-height: 44rpx;
	}

	.fs28 {
		font-size: 28rpx;
	}

	.c333 {
		color: #333333;
	}

	.ml14 {
		margin-left: 14rpx;
	}

	.fs24 {
		font-size: 24rpx;
	}

	.lh32 {
		line-height: 32rpx;
	}

	.tc {
		text-align: center;
	}

	.br-50 {
		border-radius: 50%;
	}

	.cfff {
		color: #FFFFFF;
	}

	.h32 {
		height: 32rpx;
	}

	.mt8 {
		margin-top: 8rpx;
	}

	.w-100 {
		width: 100%;
	}

	.pt30 {
		padding-top: 30rpx;
	}

	.pb60 {
		padding-bottom: 60rpx;
	}

	.pr40 {
		padding-right: 40rpx;
	}

	.pl72 {
		padding-left: 72rpx;
	}

	.pb60 {
		padding-bottom: 60rpx;
	}

	.rowa {
		display: flex;
	}

	.mw32 {
		max-width: 32rpx;
		min-width: 32rpx;
	}

	.circle {
		width: 33rpx;
		height: 66rpx;
		border-radius: 0 33rpx 33rpx 0;

		background-color: #F4F5F9;
	}

	.circleA {
		width: 33rpx;
		height: 66rpx;
		border-radius: 0 33rpx 33rpx 0;
		transform: rotate(180deg);
		background-color: #F4F5F9;
	}

	.slider-box {
		margin: 0 0;
	}

	// 单列不滚动
	.singleNo {
		width: 100%;
		height: auto;
		background-color: #F7F7F7;

		.singleNo-item {
			width: 100%;
			height: 108rpx;
			background-color: #fff;
			line-height: 108rpx;
			text-align: center;
			font-weight: 400;
			color: #000000;
			font-size: 32rpx;
			border-bottom: 2rpx solid #f7f7f7;
			// -webkit-background-clip: text;
		}

		.linkage-close {
			width: 750rpx;
			height: 108rpx;
			text-align: center;
			line-height: 108rpx;
			font-size: 32rpx;
			font-weight: 400;
			background-color: #fff;
			margin-top: 16rpx;
		}
	}
</style>
<template>
  <div class="app-container">
    <el-form ref="forma" label-position="left" :model="examineData" label-width="80px" v-if="pageType">
      <el-row>
        <el-form-item label="昵称：" class="el-col-8">
          {{examineData.nickName}}
        </el-form-item>
        <el-form-item label="头像：" class="el-col-8">
          <el-image style="width: 80px; height: 80px;" :src="examineData.headPortrait" class="el-avatar--circle"
            :preview-src-list="[examineData.headPortrait]" fit="fill"></el-image>
        </el-form-item>

        <el-form-item label="性别：" class="el-col-8">
          <span v-if="examineData.gender=='MALE'">男</span>
          <span v-if="examineData.gender=='FEMALE'">女</span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="身高：" class="el-col-8">
          {{examineData.height}}cm
        </el-form-item>
        <el-form-item label="出生日期：" label-width="auto" class="el-col-8">
          {{examineData.birthday}}
        </el-form-item>
        <el-form-item label="星座：" class="el-col-8">
          {{examineData.constellation}}
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="毕业学校：" label-width="auto" class="el-col-8">
          {{examineData.school}}
        </el-form-item>
        <el-form-item label="最高学历：" label-width="auto" class="el-col-8">
          <span v-if="examineData.education=='Specialty'">专科</span>
          <span v-if="examineData.education=='Undergraduate'">本科</span>
          <span v-if="examineData.education=='Master'">硕士</span>
          <span v-if="examineData.education=='Doctor'">博士</span>
        </el-form-item>
        <el-form-item label="学历类型：" label-width="auto" class="el-col-8">
          <span v-if="examineData.educationalType=='FullTime'">全日制</span>
          <span v-if="examineData.educationalType=='NOFullTime'">非全日制</span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="感情状况：" label-width="auto" class="el-col-8">
          <span style="" v-if="examineData.emotional=='Single'">单身</span>
          <span style="" v-if="examineData.emotional=='Love'">恋爱</span>
        </el-form-item>
        <el-form-item label="脱单目标：" label-width="auto" class="el-col-8">
          <span style="" v-if="examineData.loveGoal=='One_Year'">一年内</span>
          <span style="" v-if="examineData.loveGoal=='Two_Year'">两年内</span>
          <span style="" v-if="examineData.loveGoal=='Any_Time'">随时</span>
          <span style="" v-if="examineData.loveGoal=='Un_known'">暂不考虑</span>
        </el-form-item>
        <el-form-item label="婚姻状况：" label-width="auto" class="el-col-8">
          <span style="" v-if="examineData.marriage=='Unmarried'">未婚</span>
          <span style="" v-if="examineData.marriage=='Divorce'">离异</span>
          <span style="" v-if="examineData.marriage=='Widow'">丧偶</span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="金币余额：" label-width="auto" class="el-col-8">
          {{examineData.goldBalance}}
        </el-form-item>
        <el-form-item label="城市：" label-width="auto" class="el-col-8">
          {{examineData.city}}
        </el-form-item>
        <el-form-item label="兴趣爱好：" label-width="auto" class="el-col-8">
          {{examineData.interest}}
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="微信号：" label-width="auto" class="el-col-8">
          {{examineData.weChatNumber}}
        </el-form-item>
        <el-form-item label="活跃时间：" label-width="auto" class="el-col-8">
          {{examineData.latelyTime}}
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="用户类型" label-width="auto" class="el-col-8">
            <el-tag v-if="examineData.userType=='Normal'">正常</el-tag>            <el-tag v-if="examineData.userType=='Dummy'">虚拟</el-tag>
        </el-form-item>         <el-form-item label="用户状态" label-width="auto" class="el-col-8">
            <el-tag v-if="examineData.status=='ENABLE'">启用</el-tag>
            <el-tag v-if="examineData.status=='DISABLE'">禁用</el-tag>
            <el-tag v-if="examineData.status=='LOGOFF'">注销</el-tag>
        </el-form-item>
      </el-row>
      <el-form-item label="相册">
        <el-image v-for="item in examineData.imgList" :preview-src-list="examineData.imgList"
          style="width: 80px; height: 80px;margin-right: 10px;border-radius: 10px;" :src="item" fit="fill"></el-image>
        <div v-if="!examineData.imgList">无</div>
      </el-form-item>
      <el-form-item label="待审核相册" label-width="auto" v-if="pageType==1">
        <el-image v-for="item in examineData.waitApprovedImg" :preview-src-list="examineData.waitApprovedImg"
          style="width: 80px; height: 80px;margin-right: 10px;border-radius: 10px;" :src="item" fit="fill"></el-image>
        <div v-if="!examineData.waitApprovedImg">无</div>
      </el-form-item>
      <el-row>
      <el-form-item label-width="auto" label="用户资料认证状态" class="el-col-8" v-if="pageType==1">
         <el-radio v-model="examineData.waitApprovedStatus" label="PASS">通过</el-radio>
         <el-radio v-model="examineData.waitApprovedStatus" label="REFUSE">拒绝</el-radio>
      </el-form-item>
      <el-form-item label-width="auto" label="用户学历认证状态" class="el-col-8" v-if="pageType==2">
        <el-radio v-model="examineData.authStatus" label="PASS">通过</el-radio>
        <el-radio v-model="examineData.authStatus" label="REFUSE">拒绝</el-radio>
      </el-form-item>
      <el-form-item label-width="auto" label="用户身份认证状态" class="el-col-8" v-if="pageType==3">
        <el-radio v-model="examineData.authStatus" label="PASS">通过</el-radio>
        <el-radio v-model="examineData.authStatus" label="REFUSE">拒绝</el-radio>
      </el-form-item>
      </el-row>
      <el-form-item label="审核理由">
        <el-select placeholder="请选择审核理由" v-model="remarksVal" @change="remarksChang($event)"
          style="margin-bottom: 10px;">
          <el-option :label="item.name" :value="item.name" v-for="(item,index) in examineRemarks" :key="index">
          </el-option>
        </el-select>
        <div>
          <el-input type="textarea" style="width: 450px;" placeholder="审核理由" v-model="examineData.remarks"></el-input>
        </div>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="medium" @click="submit()">提交</el-button>
        <el-button size="medium" @click="closeExamine()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {
    getuserInfo,
    putuserExamine,
    getuserEducationInfo,
    putuserEducationExamine,
    getuserIdentityInfo,
    putuserIdentityExamine
  } from '@/api/table'
  import {
    sysconfig
  } from '@/api/system'
  import {
    MessageBox,
    Message
  } from 'element-ui'
  export default {
    filters: {},
    data() {
      return {
        pageType: '',
        // 用户资料详情
        examineData: {},
        // 审核理由
        examineRemarks: [],
        remarksVal: "",
        // 用户id
        dqUserId: ""
      }
    },
    created() {
      const id = this.$route.params && this.$route.params.id
      const type = this.$route.params && this.$route.params.type;
      this.pageType = type;
      this.dqUserId = id;
      console.log(this.$route);
      if (type == 1) {
        this.getuserInfoTy(id);
        this.$route.meta.title = "资料审核详情"
        document.title = this.$route.meta.title;
        this.$route.matched[0].redirect = "/example/table"
      } else if (type == 2) {
        this.getuserInfoTy(id);
        this.$route.meta.title = "学历审核详情"
        document.title = this.$route.meta.title;
        this.$route.matched[0].redirect = "/example/schoolingList"
      } else if (type == 3) {
        this.getuserInfoTy(id);
        this.$route.meta.title = "实名审核详情"
        document.title = this.$route.meta.title;
        this.$route.matched[0].redirect = "/example/identityList"
      } else {
        this.$message({
          message: "非法进入",
          type: 'error'
        })
      }
    },
    methods: {
      // 用户详情
      getuserInfoTy(id) {
        var that = this;
        // 用户资料
        if (that.pageType == 1) {
          getuserInfo(id).then(response => {
            console.log(response.data)
            this.examineData = response.data;
          })
        } else if (that.pageType == 2) {
          // 用户学历
          getuserEducationInfo(id).then(response => {
            console.log(response.data)
            this.examineData = response.data;
          })
        } else if (that.pageType == 3) {
          // 用户身份
          getuserIdentityInfo(id).then(response => {
            console.log(response.data)
            this.examineData = response.data;
          })
        }

        var data = {
          "code": "examineRemarks"
        }
        sysconfig(data).then(response => {
          console.log(JSON.parse(response.data))
          this.examineRemarks = JSON.parse(response.data)
          // this.examineRemarks = response.data;
        })
      },

      // 提交审核
      submit() {
        var that = this;
        debugger
        // 提交用户资料审核
        if (that.pageType == 1) {
          if (that.examineData.waitApprovedStatus != 'PASS' && that.examineData.waitApprovedStatus != 'REFUSE') {
            this.$message({
              message: "请选择用户资料认证状态",
              type: 'error'
            })
            return false;
          }
          var data = {
            "remarks": this.examineData.remarks,
            "result": this.examineData.waitApprovedStatus + "",
            "userId": this.dqUserId,
            "version": this.examineData.version
          }
          console.log(data);
          putuserExamine(data).then(response => {
            if (response.code == 200) {
              Message({
                message: '审核成功！',
                type: 'success',
              })
              that.$router.go(-1);
            }
            console.log(response)
          })
        } else if (that.pageType == 2) {
          if (that.examineData.authStatus != 'PASS' && that.examineData.authStatus != 'REFUSE') {
            this.$message({
              message: "请选择用户学历认证状态",
              type: 'error'
            })
            return false;
          }
          var data = {
            "remarks": this.examineData.remarks,
            "result": this.examineData.authStatus + "",
            "userId": this.dqUserId,
            "version": this.examineData.version
          }
          console.log(data);
          putuserEducationExamine(data).then(response => {
            if (response.code == 200) {
              Message({
                message: '审核成功！',
                type: 'success',
              })
              that.$router.go(-1);
            }
            console.log(response)
          })
        } else if (that.pageType == 3) {
          console.log(that.examineData.authStatus)
          if (that.examineData.authStatus != 'PASS' && that.examineData.authStatus != 'REFUSE') {
            this.$message({
              message: "请选择用户身份认证状态",
              type: 'error'
            })
            return false;
          }
          var data = {
            "remarks": this.examineData.remarks,
            "result": this.examineData.authStatus + "",
            "userId": this.dqUserId,
            "version": this.examineData.version
          }
          console.log(data);
          putuserIdentityExamine(data).then(response => {
            if (response.code == 200) {
              Message({
                message: '审核成功！',
                type: 'success',
              })
              that.$router.go(-1);
            }
            console.log(response)
          })
        }
      },
      // 审核理由
      remarksChang(value) {
        console.log(value);
        this.remarksVal = value;
        this.examineData.remarks = value;
      },
      // 返回上一页
      closeExamine() {
        this.$router.go(-1);
      }
    }
  }
</script>

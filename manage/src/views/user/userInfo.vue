<template>
  <div class="app-container">
    <el-form ref="forma" label-position="left" v-if="pageType == 1">
      <el-row>
        <el-form-item label="昵称：" label-width="110px" class="el-col-8">
          <el-input v-model="nickName" style="width: 75%;" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="出生日期：" label-width="110px" class="el-col-8">
          <el-date-picker v-model="birthday" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" style="width: 75%;" type="date" placeholder="请选择出生日期" />
        </el-form-item>
        <el-form-item label="性别：" label-width="110px" class="el-col-8">
          <el-select v-model="gender" style="width: 75%;" size="medium" placeholder="性别" clearable class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="男" value="MALE" />
            <el-option label="女" value="FEMALE" />
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="毕业学校：" label-width="110px" class="el-col-8">
          <el-input v-model="school" style="width: 75%;" placeholder="请输入毕业学校"></el-input>
        </el-form-item>
        <el-form-item label="最高学历：" label-width="110px" class="el-col-8">
          <el-select v-model="education" style="width: 75%;" size="medium" placeholder="最高学历 " clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="专科" value="Specialty" />
            <el-option label="本科" value="Undergraduate" />
            <el-option label="硕士" value="Master" />
            <el-option label="博士" value="Doctor" />
          </el-select>
        </el-form-item>
        <el-form-item label="学历类型：" label-width="110px" class="el-col-8">
          <el-select v-model="educationalType" style="width: 75%;" size="medium" placeholder="学历类型" clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="全日制" value="FullTime" />
            <el-option label="非全日制" value="NOFullTime" />
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="感情状况：" label-width="110px" class="el-col-8">
          <el-select v-model="emotional" style="width: 75%;" size="medium" placeholder="感情状况 " clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="单身" value="Single" />
            <el-option label="恋爱" value="Love" />
          </el-select>
        </el-form-item>
        <el-form-item label="脱单目标：" label-width="110px" class="el-col-8">
          <el-select v-model="loveGoal" style="width: 75%;" size="medium" placeholder="脱单目标 " clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="一年内" value="One_Year" />
            <el-option label="两年内" value="Two_Year" />
            <el-option label="随时" value="Any_Time" />
            <el-option label="暂不考虑" value="Un_known" />
          </el-select>
        </el-form-item>
        <el-form-item label="婚姻状况：" label-width="110px" class="el-col-8">
          <el-select v-model="marriage" style="width: 75%;" size="medium" placeholder="婚姻状况" clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="未婚" value="Unmarried" />
            <el-option label="离异" value="Divorce" />
            <el-option label="丧偶" value="Widow" />
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="身高：" label-width="110px" class="el-col-8">
          <el-input v-model="height" type="number" style="width: 75%;" placeholder="请输入身高(cm)"></el-input>
        </el-form-item>
        <el-form-item label="职业：" label-width="110px" class="el-col-8">
          <el-input v-model="profession" style="width: 75%;" placeholder="请输入职业"></el-input>
        </el-form-item>
        <el-form-item label="年薪：" label-width="110px" class="el-col-8">
          <el-select v-model="annualSalary" style="width: 75%;" size="medium" placeholder="年薪" clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="小于10w" value="Less_Ten" />
            <el-option label="10w到20w" value="Ten_Two" />
            <el-option label="20w到30w" value="Two_Three" />
            <el-option label="30w到50w" value="Three_Five" />
            <el-option label="50w到100w" value="Five_Hundred" />
            <el-option label="大于100w" value="Greater_Hundred" />
            <el-option label="保密" value="Secrecy" />
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="择偶要求：" label-width="110px" class="el-col-8">
          <el-input v-model="loveRequirement" type="textarea" :rows="2" style="width: 75%;" placeholder="请输入择偶要求">
          </el-input>
        </el-form-item>
        <el-form-item label-width="110px" label="关于我：" class="el-col-8">
          <el-input v-model="aboutMe" type="textarea" :rows="2" style="width: 75%;" placeholder="请输入关于我"></el-input>
        </el-form-item>
        <el-form-item label="兴趣爱好：" label-width="110px" class="el-col-8">
          <el-input v-model="interest" type="textarea" :rows="2" style="width: 75%;" placeholder="请输入兴趣爱好"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label-width="110px" label="相册：">
          <el-upload action="http://1.14.110.117:8089/file/upload" list-type="picture-card"
            :on-preview="handlePictureCardPreview" :on-remove="handleRemove" :file-list="imgList" :show-file-list="true"
            :on-success="handleSuccess">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label-width="110px" label="用户身份认证状态：" class="el-col-8">
          <el-radio v-model="status" label="ENABLE">启用</el-radio>
          <el-radio v-model="status" label="DISABLE">禁用</el-radio>
        </el-form-item>
      </el-row>
      <el-form-item>
        <el-button type="primary" size="medium" @click="submit()">提交</el-button>
        <el-button size="medium" @click="closeExamine()">返回</el-button>
      </el-form-item>
    </el-form>

    <el-form ref="forma" label-position="left" v-if="pageType == 2">
      <el-row>
        <el-form-item label-width="110px" label="头像：">
          <el-image style="width: 80px; height: 80px;" :src="editUserData.headPortrait" class="el-avatar--circle"
            :preview-src-list="[editUserData.headPortrait]" fit="fill"></el-image>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="金币余额：" label-width="110px" class="el-col-8">
          {{editUserData.goldBalance}}
        </el-form-item>
        <el-form-item label="城市：" label-width="110px" class="el-col-8">
          {{editUserData.city}}
        </el-form-item>
        <el-form-item label="星座：" label-width="110px" class="el-col-8">
          {{editUserData.constellation}}
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="微信号：" label-width="110px" class="el-col-8">
          {{editUserData.weChatNumber}}
        </el-form-item>
        <el-form-item label="注册时间：" label-width="110px" class="el-col-8">
          {{editUserData.createTime}}
        </el-form-item>
        <el-form-item label="活跃时间：" label-width="110px" class="el-col-8">
          {{editUserData.latelyTime}}
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="用户类型：" label-width="110px" class="el-col-8">
          <el-tag type="success" v-if="editUserData.userType=='Normal'">正常</el-tag>
          <el-tag type="danger" v-if="editUserData.userType=='Dummy'">虚拟</el-tag>
        </el-form-item>
        <el-form-item label="学历认证状态：" label-width="110px" class="el-col-8">
          <el-tag type="success" v-if="editUserData.authStatus == 'PASS'">通过</el-tag>
          <el-tag type="danger" v-if="editUserData.authStatus == 'REFUSE'">拒绝</el-tag>
        </el-form-item>
        <el-form-item label="身份认证状态：" label-width="110px" class="el-col-8">
          <el-tag type="warning" v-if="editUserData.identityAuth=='EXAMINE'">审核中</el-tag>
          <el-tag type="success" v-if="editUserData.identityAuth=='PASS'">已通过</el-tag>
          <el-tag type="danger" v-if="editUserData.identityAuth=='REFUSE'">已拒绝</el-tag>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="昵称：" label-width="110px" class="el-col-8">
          <el-input v-model="nickName" style="width: 75%;" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="出生日期：" label-width="110px" class="el-col-8">
          <el-date-picker v-model="birthday" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" style="width: 75%;" type="date" placeholder="请选择出生日期" />
        </el-form-item>
        <el-form-item label="性别：" label-width="110px" class="el-col-8">
          <el-select v-model="gender" style="width: 75%;" size="medium" placeholder="性别" clearable class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="男" value="MALE" />
            <el-option label="女" value="FEMALE" />
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="毕业学校：" label-width="110px" class="el-col-8">
          <el-input v-model="school" style="width: 75%;" placeholder="请输入毕业学校"></el-input>
        </el-form-item>
        <el-form-item label="最高学历：" label-width="110px" class="el-col-8">
          <el-select v-model="education" style="width: 75%;" size="medium" placeholder="最高学历 " clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="专科" value="Specialty" />
            <el-option label="本科" value="Undergraduate" />
            <el-option label="硕士" value="Master" />
            <el-option label="博士" value="Doctor" />
          </el-select>
        </el-form-item>
        <el-form-item label="学历类型：" label-width="110px" class="el-col-8">
          <el-select v-model="educationalType" style="width: 75%;" size="medium" placeholder="学历类型" clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="全日制" value="FullTime" />
            <el-option label="非全日制" value="NOFullTime" />
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="感情状况：" label-width="110px" class="el-col-8">
          <el-select v-model="emotional" style="width: 75%;" size="medium" placeholder="感情状况 " clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="单身" value="Single" />
            <el-option label="恋爱" value="Love" />
          </el-select>
        </el-form-item>
        <el-form-item label="脱单目标：" label-width="110px" class="el-col-8">
          <el-select v-model="loveGoal" style="width: 75%;" size="medium" placeholder="脱单目标 " clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="一年内" value="One_Year" />
            <el-option label="两年内" value="Two_Year" />
            <el-option label="随时" value="Any_Time" />
            <el-option label="暂不考虑" value="Un_known" />
          </el-select>
        </el-form-item>
        <el-form-item label="婚姻状况：" label-width="110px" class="el-col-8">
          <el-select v-model="marriage" style="width: 75%;" size="medium" placeholder="婚姻状况" clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="未婚" value="Unmarried" />
            <el-option label="离异" value="Divorce" />
            <el-option label="丧偶" value="Widow" />
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="身高：" label-width="110px" class="el-col-8">
          <el-input v-model="height" type="number" style="width: 75%;" placeholder="请输入身高(cm)"></el-input>
        </el-form-item>

        <el-form-item label="职业：" label-width="110px" class="el-col-8">
          <el-input v-model="profession" style="width: 75%;" placeholder="请输入职业"></el-input>
        </el-form-item>
        <el-form-item label="年薪：" label-width="110px" class="el-col-8">
          <el-select v-model="annualSalary" style="width: 75%;" size="medium" placeholder="年薪" clearable
            class="filter-item ">
            <el-option label="请选择" value="-1" />
            <el-option label="小于10w" value="Less_Ten" />
            <el-option label="10w到20w" value="Ten_Two" />
            <el-option label="20w到30w" value="Two_Three" />
            <el-option label="30w到50w" value="Three_Five" />
            <el-option label="50w到100w" value="Five_Hundred" />
            <el-option label="大于100w" value="Greater_Hundred" />
            <el-option label="保密" value="Secrecy" />
          </el-select>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label-width="110px" label="相册：">
          <el-upload action="http://1.14.110.117:8089/file/upload" list-type="picture-card"
            :on-preview="handlePictureCardPreview" :on-remove="handleRemove" :file-list="imgList" :show-file-list="true"
            :on-success="handleSuccess">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="兴趣爱好：" label-width="110px" class="el-col-8">
          <el-input v-model="interest" type="textarea" :rows="2" style="width: 75%;" placeholder="请输入兴趣爱好"></el-input>
        </el-form-item>
        <el-form-item label="择偶要求：" label-width="110px" class="el-col-8">
          <el-input v-model="loveRequirement" type="textarea" :rows="2" style="width: 75%;" placeholder="请输入择偶要求">
          </el-input>
        </el-form-item>
        <el-form-item label-width="110px" label="关于我：" class="el-col-8">
          <el-input v-model="aboutMe" type="textarea" :rows="2" style="width: 75%;" placeholder="请输入关于我"></el-input>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label-width="110px" label="用户状态：" class="el-col-8">
          <el-radio v-model="status" label="ENABLE">启用</el-radio>
          <el-radio v-model="status" label="DISABLE">禁用</el-radio>
        </el-form-item>
      </el-row>

      <el-form-item>
        <el-button type="primary" size="medium" @click="submit()">提交</el-button>
        <el-button size="medium" @click="closeExamine()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {
    userUpdate,
    getuserInfo
  } from '@/api/user'
  export default {
    filters: {},
    data() {
      return {
        aboutMe: "",
        annualSalary: "",
        birthday: "",
        education: "",
        educationalType: "",
        emotional: "",
        gender: "",
        height: '',
        id: 0,
        imgList: [],
        interest: "",
        loveGoal: "",
        loveRequirement: "",
        marriage: "",
        nickName: "",
        profession: "",
        school: "",
        status: "",
        // 相册
        dialogImageUrl: '',
        dialogVisible: false,
        // 修改时用户id
        editUserId: "",
        pageType: 1, //页面类型1:新增2：修改

        editUserData: "", //用户数据
      }
    },
    created() {
      var that = this;
      const id = this.$route.params && this.$route.params.id
      const type = this.$route.params && this.$route.params.type;
      this.pageType = type;
      this.$route.matched[0].redirect = "/user/index"
      if (type == 1) {
        // 新增
        this.$route.meta.title = "新增前台用户"
      } else if (type == 2) {
        // 修改
        this.editUserId = id;
        this.$route.meta.title = "前台用户详情"
        getuserInfo(id).then(response => {
          console.log(response.data)
          var data = response.data;
          this.editUserData = data;
          this.aboutMe = data.aboutMe;
          this.annualSalary = data.annualSalary;
          this.birthday = data.birthday;
          this.education = data.education;
          this.educationalType = data.educationalType;
          this.emotional = data.emotional;
          this.gender = data.gender;
          this.height = data.height;
          var imgList = data.imgList;
          this.interest = data.interest;
          this.loveGoal = data.loveGoal;
          this.loveRequirement = data.loveRequirement;
          this.marriage = data.marriage;
          this.nickName = data.nickName;
          this.profession = data.profession;
          this.school = data.school;
          this.status = data.status;

          this.$nextTick(()=>{ //下面是图片数组，在页面更新后面赋值！！
              imgList.map((v)=>{
                var item = {url:v,name:"photo"}
                this.imgList.push(item);
              })
            })

          console.log(this.imgList)
          this.examineData = response.data;
        })
      }
      // this.pageType = type;
      // this.dqUserId = id;
      console.log(id);
      console.log(type);
      console.log(this.$route);
      // if (type == 1) {
      //   this.getuserInfoTy(id);
      //   this.$route.meta.title = "资料审核详情"
      //   document.title = this.$route.meta.title;
      //   this.$route.matched[0].redirect = "/example/table"
      // } else if (type == 2) {
      //   this.getuserInfoTy(id);
      //   this.$route.meta.title = "学历审核详情"
      //   document.title = this.$route.meta.title;
      //   this.$route.matched[0].redirect = "/example/schoolingList"
      // } else if (type == 3) {
      //   this.getuserInfoTy(id);
      //   this.$route.meta.title = "实名审核详情"
      //   document.title = this.$route.meta.title;
      //   this.$route.matched[0].redirect = "/example/identityList"
      // } else {
      //   this.$message({
      //     message: "非法进入",
      //     type: 'error'
      //   })
      // }
    },
    methods: {
      handleRemove(file, fileList) {
        for (let i = 0; i < this.imgList.length; i++) {
          let attach = this.imgList[i].url;
          if (file.url == attach) {
            this.imgList.splice(i, 1)
          }
        }
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      // 图片上传成功
      handleSuccess(res, file, fileList) {
        console.log(res)
        console.log(file)
        // console.log(fileList)
        if (res.code === 200) {
          // this.imgList.push(res.data.url)
          var item = {url:res.data.url,name:"photo"}
          			this.imgList.push(item)
        }
        console.log(this.imgList)
      },
      // 返回上一页
      closeExamine() {
        this.$router.go(-1);
      },
      // 添加用户
      submit() {
        var that = this;
        // this.birthday = this.birthday+" 00:00:00";
        // console.log(that.birthday);
        // return false;
        if (this.nickName == "") {
          this.$message({
            message: '昵称不能为空',
            type: 'error'
          })
          return false;
        }
        if (this.birthday == "") {
          this.$message({
            message: '请选择出生日期',
            type: 'error'
          })
          return false;
        }else{
          if(this.birthday.length<15){
            this.birthday = this.birthday+" 00:00:00";
          }
        }
        if (this.gender == "") {
          this.$message({
            message: '请选择性别',
            type: 'error'
          })
          return false;
        }
        if (this.height == "") {
          this.$message({
            message: '请输入身高',
            type: 'error'
          })
          return false;
        }
        if (this.school == "") {
          this.$message({
            message: '学校不能为空',
            type: 'error'
          })
          return false;
        }
        if (this.education == "" || this.education == -1) {
          this.$message({
            message: '请选择最高学历',
            type: 'error'
          })
          return false;
        }
        if (this.educationalType == "" || this.educationalType == -1) {
          this.$message({
            message: '请选择学历类型',
            type: 'error'
          })
          return false;
        }
        if (this.emotional == "" || this.emotional == -1) {
          this.$message({
            message: '请选择感情状况',
            type: 'error'
          })
          return false;
        }
        if (this.loveGoal == "" || this.loveGoal == -1) {
          this.$message({
            message: '请选择脱单目标',
            type: 'error'
          })
          return false;
        }
        if (this.marriage == "" || this.marriage == -1) {
          this.$message({
            message: '请选择婚姻状况',
            type: 'error'
          })
          return false;
        }
        if (this.interest == "") {
          this.$message({
            message: '请输入兴趣爱好',
            type: 'error'
          })
          return false;
        }
        if (this.profession == "") {
          this.$message({
            message: '请输入职业',
            type: 'error'
          })
          return false;
        }
        if (this.annualSalary == "" || this.annualSalary == -1) {
          this.$message({
            message: '请选择年薪',
            type: 'error'
          })
          return false;
        }


        if (this.loveRequirement == "") {
          this.$message({
            message: '请输入择偶要求',
            type: 'error'
          })
          return false;
        }
        if (this.aboutMe == "") {
          this.$message({
            message: '请输入关于我',
            type: 'error'
          })
          return false;
        }
        var imgList = [];
        console.log(this.imgList)
        this.imgList.map((v)=>{
          imgList.push(v.url)
        })
        if(this.editUserId == ''){
          var data = {
            "aboutMe": this.aboutMe,
            "annualSalary": this.annualSalary,
            "birthday": this.birthday,
            "education": this.education,
            "educationalType": this.educationalType,
            "emotional": this.emotional,
            "gender": this.gender,
            "height": this.height,
            // "id": 0,
            "imgList": imgList,
            "interest": this.interest,
            "loveGoal": this.loveGoal,
            "loveRequirement": this.loveRequirement,
            "marriage": this.marriage,
            "nickName": this.nickName,
            "profession": this.profession,
            "school": this.school,
            "status": this.status
          };
        }else{
          var data = {
            "aboutMe": this.aboutMe,
            "annualSalary": this.annualSalary,
            "birthday": this.birthday,
            "education": this.education,
            "educationalType": this.educationalType,
            "emotional": this.emotional,
            "gender": this.gender,
            "height": this.height,
            "id": this.editUserId,
            "imgList": imgList,
            "interest": this.interest,
            "loveGoal": this.loveGoal,
            "loveRequirement": this.loveRequirement,
            "marriage": this.marriage,
            "nickName": this.nickName,
            "profession": this.profession,
            "school": this.school,
            "status": this.status
          };
        }
        userUpdate(data).then(response => {
          console.log(response)
          if (response.code == 200) {
            if(that.pageType == 1){
              this.$message({
                message: '新增成功!',
                type: 'success'
              })
            }else{
              this.$message({
                message: '编辑成功!',
                type: 'success'
              })
            }
            that.$router.go(-1);
          }else{
            this.$message({
              message: response.msg,
              type: 'error'
            })
          }
          console.log(response)
        })

        // console.log(this.imgList)
      }

    }
  }
</script>

<style>
</style>

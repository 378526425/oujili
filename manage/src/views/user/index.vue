<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 15px;">
      <div style="height: 50px;">
        <div class="el-col-6">
          昵称：
          <el-input placeholder="请输入内容" v-model="nickName" clearable style="width: 70%;">
          </el-input>
        </div>
        <div class="el-col-6">
          认证状态：
          <el-select v-model="authStatus" style="width: 70%;" size="medium" placeholder="用户资料状态 " clearable
            class="filter-item ">
            <el-option label="全部" value="-1" />
            <el-option label="审核中" value="EXAMINE" />
            <el-option label="通过" value="PASS" />
            <el-option label="拒绝" value="REFUSE" />
          </el-select>
        </div>
        <div class="el-col-6">
          用户状态：
          <el-select v-model="userStatus" style="width: 70%;" size="medium" placeholder="用户资料状态 " clearable
            class="filter-item ">
            <el-option label="全部" value="-1" />
            <el-option label="启用" value="ENABLE" />
            <el-option label="禁用" value="DISABLE" />
            <el-option label="注销" value="LOGOFF" />
          </el-select>
        </div>
        <div class="el-col-6">
          用户类型：
          <el-select v-model="userType" style="width: 70%;" size="medium" placeholder="用户资料状态 " clearable
            class="filter-item ">
            <el-option label="全部" value="-1" />
            <el-option label="正常" value="Normal" />
            <el-option label="虚拟" value="Dummy" />
          </el-select>
        </div>
      </div>
      <div style="height: 50px;">
        <div class="el-col-6">
          性别：
          <el-select v-model="userSex" style="width: 70%;" size="medium" placeholder="用户资料状态 " clearable
            class="filter-item ">
            <el-option label="全部" value="-1" />
            <el-option label="男" value="MALE" />
            <el-option label="女" value="FEMALE" />
          </el-select>
        </div>
        <div class="el-col-6">
          活跃时间：
          <el-date-picker v-model="latelyTime" type="daterange" style="width: 70%;" value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </div>
        <div class="el-col-6">
          注册时间：
          <el-date-picker v-model="createTime" type="daterange" style="width: 70%;" value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </div>
        <div class="el-col-6">
          <el-button class=" " size="medium" type="primary" icon="el-icon-search"
            @click="searchData">
            搜索
          </el-button>
          <el-button class=" " size="medium" icon="" @click="resetting">
            重置
          </el-button>
        </div>
      </div>
      <div style="height: 50px;">
        <div class="el-col-6">
          <router-link :to="'/user/userInfo/0/1'">
            <el-button class="filter-item el-col-6" size="small" type="primary" icon="" @click="AddUser">
              新增
            </el-button>
          </router-link>
        </div>
      </div>

    </div>

    <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" fit highlight-current-row>
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          <!-- {{ scope.$index }} -->
          {{ scope.row.id }}

        </template>
      </el-table-column>
      <el-table-column label="昵称" width="200">
        <template slot-scope="scope">
          {{ scope.row.nickName }}
        </template>
      </el-table-column>
      <el-table-column label="头像" width="100px">
        <template slot-scope="scope">
          <el-image style="width: 50px; height: 50px;" class="el-avatar--circle" :src="scope.row.headPortrait"
            :preview-src-list="[scope.row.headPortrait]" fit="fill"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="90" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.gender=='MALE'">男</el-tag>
          <el-tag type="danger" v-if="scope.row.gender=='FEMALE'">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="城市">
        <template slot-scope="scope">
          {{ scope.row.city }}
        </template>
      </el-table-column>
      <el-table-column label="申请时间" width="220px">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column label="最近活跃时间" width="220px">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.latelyTime }}
        </template>
      </el-table-column>
      <el-table-column label="用户资料认证状态" width="150" align="center">
        <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.authStatus=='EXAMINE'">审核中</el-tag>
          <el-tag type="success" v-if="scope.row.authStatus=='PASS'">已通过</el-tag>
          <el-tag type="danger" v-if="scope.row.authStatus=='REFUSE'">已拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="用户状态" width="110">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status=='ENABLE'">启用</el-tag>
          <el-tag type="warning" v-if="scope.row.status=='DISABLE'">禁用</el-tag>
          <el-tag type="danger" v-if="scope.row.status=='LOGOFF'">注销</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="用户类型" width="110">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.userType=='Dummy'">虚拟</el-tag>
          <el-tag type="success" v-if="scope.row.userType=='Normal'">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <router-link :to="'/user/userInfo/'+scope.row.id+'/2'">
            <el-button type="primary" size="small" icon="el-icon-edit">
              编辑
            </el-button>
          </router-link>
          <el-button type="danger" style="margin-left: 10px;" @click="showDiaLog(scope.row.id)" size="small" icon="el-icon-delete">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>确定要删除该用户吗</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDiaLog()">取 消</el-button>
        <el-button type="primary" @click="delUserId">确 定</el-button>
      </span>
    </el-dialog>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currPage"
      :page-sizes="[10, 20, 30, 40]" :page-size="10" background layout="total, prev, pager, next" :total="totalCount"
      style="margin: 15px 0;float: right;">
    </el-pagination>
  </div>
</template>

<script>
  import {
    getUserList,
    deleteUser
  } from '@/api/user'
  import {
    sysconfig
  } from '@/api/system'
  import {
    MessageBox,
    Message
  } from 'element-ui'
  export default {
    filters: {
      statusFilter(status) {
        const statusMap = {
          ENABLE: 'success',
          DISABLE: 'gray',
          LOGOFF: 'danger'
        }
        return statusMap[status]
      }
    },
    data() {
      return {
        list: null,
        totalCount: 0,
        totalPage: 1,
        currPage: 1,
        pageSize: 10,
        listLoading: true,
        // 查询条件
        nickName:'',//昵称
        authStatus: '',//认证状态
        userStatus:"",//用户状态
        userType: '',//用户类型
        userSex:"",//用户性别
        latelyTime:"",//活跃时间
        createTime:"",//注册时间
        // 删除弹框
        dialogVisible: false,
        deleteUserId:"",//删除id
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            this.deleteUserId = '';
            done();
          })
          .catch(_ => {});
      },
      showDiaLog(id){
        this.deleteUserId = id;
        this.dialogVisible = true;
      },
      closeDiaLog(){
        this.deleteUserId = '';
        this.dialogVisible = false;
      },
      // 删除数据
      delUserId(){
        var id = this.deleteUserId;
        deleteUser(id).then(response => {
          console.log(response)
          if(response.code == 200){
            this.$message({
              message: '删除成功!',
              type: 'success'
            })
            this.fetchData();
            this.closeDiaLog();
          }
        })
      },
      // 跳转到新增用户
      AddUser(){

      },
      // 分页
      handleSizeChange(val) {
        this.pageSize = val;
        this.fetchData();
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        this.currPage = val;
        this.fetchData();
        console.log(`当前页: ${val}`);
      },
      // 点击搜索
      searchData() {
        this.currPage = 1;
        this.fetchData();
      },
      // 重置搜索
      resetting(){
        console.log(this.latelyTime);

        this.nickName = "";
        this.authStatus = "";
        this.userStatus = "";
        this.userType = "";
        this.userSex = "";
        this.latelyTime = "";
        this.createTime = "";
      },
      // 加载数量
      fetchData() {
        this.listLoading = true;
        if (this.authStatus == -1) {
          var authStatus = '';
        } else {
          var authStatus = this.authStatus;
        }
        if (this.userType == -1) {
          var userType = '';
        } else {
          var userType = this.userType;
        }
        if (this.userStatus == -1) {
          var userStatus = '';
        } else {
          var userStatus = this.userStatus;
        }
        if (this.userSex == -1) {
          var userSex = '';
        } else {
          var userSex = this.userSex;
        }

        console.log(this.latelyTime);
        if(this.latelyTime == null){
          this.latelyTime = ""
        }
        if(this.createTime == null){
          this.createTime = ""
        }
        var data = {
          "pageIndex": this.currPage,
          "pageSize": this.pageSize,
          "authStatus": authStatus,
          "userType": userType,
          "status": userStatus,
          "gender": userSex,
          "latelyTimeStart":this.latelyTime[0],
          "latelyTimeEnd":this.latelyTime[1],
          "createTimeStart":this.createTime[0],
          "createTimeEnd":this.createTime[1]
        }
        getUserList(data).then(response => {
          console.log(response.data.rows)
          this.list = response.data.rows
          this.totalCount = response.data.totalCount
          this.totalPage = response.data.totalPage
          this.currPage = response.data.currPage
          this.pageSize = response.data.pageSize
          this.listLoading = false
        })
      }
    }
  }
</script>

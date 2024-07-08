<template>
  <div class="app-container">
		<div class="filter-container" style="margin-bottom: 15px;">
		  <el-select v-model="authStatus"  size="medium" placeholder="用户学历认证状态 " clearable class="filter-item" style="width: 180px">
			  <el-option label="全部" value="-1" />
		    <el-option label="审核中" value="EXAMINE" />
		    <el-option label="通过" value="PASS" />
		    <el-option label="拒绝" value="REFUSE" />
		  </el-select>
		  <el-select v-model="userType"  size="medium" placeholder="用户类型" style="width: 140px;margin-left: 15px;margin-right: 15px;" class="filter-item">
			  <el-option label="全部" value="-1" />
		    <el-option label="正常" value="Normal" />
		    <el-option label="虚拟" value="Dummy" />
		  </el-select>
		  <el-button class="filter-item"  size="medium" type="primary" icon="el-icon-search" @click="searchData">
		    搜索
		  </el-button>
		</div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          <!-- {{ scope.$index }} -->
          {{ scope.row.id }}

        </template>
      </el-table-column>
      <el-table-column label="昵称"width="200">
        <template slot-scope="scope">
          {{ scope.row.nickName }}
        </template>
      </el-table-column>
      <el-table-column label="头像"width="100px">
        <template slot-scope="scope">
          <el-image
                style="width: 50px; height: 50px;"
                class="el-avatar--circle"
                :src="scope.row.headPortrait"
                :preview-src-list="[scope.row.headPortrait]"
                fit="fill"></el-image>
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
      <el-table-column label="申请时间"width="220px">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column label="最近活跃时间"width="220px">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.latelyTime }}
        </template>
      </el-table-column>
      <el-table-column label="用户学历认证状态" width="150" align="center">
        <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.educationAuth=='EXAMINE'">审核中</el-tag>
          <el-tag type="success" v-if="scope.row.educationAuth=='PASS'">已通过</el-tag>
          <el-tag type="danger" v-if="scope.row.educationAuth=='REFUSE'">已拒绝</el-tag>
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
      <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <router-link :to="'/example/examineinfo/'+scope.row.id+'/2'">
              <!--  @click="showExamine(scope.row.id)" -->
              <el-button type="primary" size="small" icon="el-icon-edit">
                查看详情
              </el-button>
            </router-link>
          </template>
      </el-table-column>
    </el-table>
    <!-- <pagination v-show="totalCount>0" :total="totalCount" :page.sync="currPage" :limit.sync="pageSize" @pagination="fetchData" /> -->
    <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currPage"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="10"
		  background
		  layout="total, prev, pager, next"
          :total="totalCount"
		  style="margin: 15px 0;float: right;"
		  >
        </el-pagination>
  </div>
</template>

<script>
import { geteducationList,getuserEducationInfo,putuserEducationExamine } from '@/api/table'
import {sysconfig} from '@/api/system'


import { MessageBox, Message } from 'element-ui'
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
      totalCount:0,
      totalPage:1,
      currPage:1,
      pageSize:10,
      listLoading: true,
      examineDialog:false,
      examineData:{},
      authStatus:'',
      userType:'',
      dqUserId:"",
      remarksVal:"",
      // 审核理由列表
      examineRemarks:[]
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 审核理由
    remarksChang(value){
      console.log(value);
      this.remarksVal = value;
      this.examineData.remarks = value;
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
    searchData(){
      this.currPage = 1;
      this.fetchData();
    },
    // 提交审核
    submit(){
      // console.log(this.examineData);
      var data = {
        "remarks": this.examineData.remarks,
        "result": this.examineData.authStatus+"",
        "userId": this.dqUserId,
        "version": 0
      }
      console.log(data);
      putuserEducationExamine(data).then(response => {
        if(response.code == 200){
          Message({
            message: '审核成功！',
            type: 'success',
            // duration: 5 * 1000
          })
          this.examineDialog = false;
          this.fetchData()
        }
        console.log(response)
      })
    },
    showExamine(id){
      console.log(id);
      this.dqUserId = id;
      this.getuserInfo(id);

    },
    closeExamine(){
      this.examineDialog = false;
    },
    getuserInfo(id) {
      getuserEducationInfo(id).then(response => {
        console.log(response.data)
        this.examineData = response.data;
        this.examineDialog = true;
      })
        var data = {
          "code": "examineRemarks"
        }
        sysconfig(data).then(response => {
          console.log(JSON.parse(response.data))
          this.examineRemarks =  JSON.parse(response.data)
          // this.examineRemarks = response.data;
        })
    },
    // 加载数量
    fetchData() {
      this.listLoading = true;
	  if(this.authStatus == -1){
	  		var authStatus = '';
	  }else{
		  var authStatus = this.authStatus;
	  }
	  if(this.userType == -1){
	  		  var userType = '';
	  }else{
		  var userType = this.userType;
	  }
      var data = {
        "pageIndex": this.currPage,
        "pageSize": this.pageSize,
        "authStatus": authStatus,
        "userType":userType
      }
      geteducationList(data).then(response => {
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

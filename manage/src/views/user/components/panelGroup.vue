<template>
  <el-row :gutter="20" class="panel-group">
    <el-col :xs="12" :sm="8" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-description">
          <div class="card-panel-text" title="24小时内在线用户数">
            <svg-icon icon-class="userA" style="font-size: 20px;"/>
            24小时在线用户
          </div>
          <count-to :start-val="0" :end-val="panelGroupData.online24" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="8" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-description">
          <div class="card-panel-text" title="24小时内注册用户">
            <svg-icon icon-class="userB" style="font-size: 20px;"/>
            24小时注册用户
          </div>
          <count-to :start-val="0" :end-val="panelGroupData.register24" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="8" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-description">
          <div class="card-panel-text" title="今日在线用户数">
            <svg-icon icon-class="userC" style="font-size: 20px;"/>
            今日在线用户
          </div>
          <count-to :start-val="0" :end-val="panelGroupData.todayOnline" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="8" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-description">
          <div class="card-panel-text" title="今日注册用户">
            <svg-icon icon-class="userD" style="font-size: 20px;" />
            今日注册用户
          </div>
          <count-to :start-val="0" :end-val="panelGroupData.todayRegister" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="8" :lg="12" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-description" title="用户总数">
          <div class="card-panel-text">
            <svg-icon icon-class="userF" style="font-size: 20px;"/>
            用户总数
          </div>
          <count-to :start-val="0" :end-val="panelGroupData.userCount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="8" :lg="12" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-description" title="昨日注册用户">
          <div class="card-panel-text">
            <svg-icon icon-class="userE" style="font-size: 20px;"/>
            昨日注册用户
          </div>
          <count-to :start-val="0" :end-val="panelGroupData.yesterdayRegister" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
  components: {
    CountTo
  },
  props: {
    panelGroupData: {
      type: Object,
      default: {}
    }
  },
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 10px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);


    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      font-weight: bold;
      margin: 26px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space:nowrap;
        min-width: 80px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

</style>

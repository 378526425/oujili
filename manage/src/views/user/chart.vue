<template>
    <div class="dashboard-container">
        <div class="top-class">
            <panel-group :panelGroupData="panelGroupData"/>
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :lg="12">
                    <div class="chart-wrapper">
                        <div>性别占比</div>
                        <pie-chart  :pieChartData="genderPieData"/>
                    </div>
                </el-col>
                <el-col :xs="24" :sm="24" :lg="12">
                    <div class="chart-wrapper">
                        <div>城市柱状图</div>
                        <bar-chart :barChartData="cityBarData"/>
                    </div>
                </el-col>
            </el-row>
        </div>
        <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
            <div class="lineHeader" style="">
                <div>
                    <el-row>
                        <el-col :span="16">
                            <span>用户注册</span>
                            <el-date-picker v-model="latelyTime" type="daterange" style="width: 80%;" value-format="yyyy-MM-dd"
                                range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" size="mini">
                            </el-date-picker>
                        </el-col>
                        <el-col :span="8">
                            <el-button size="mini" type="primary" icon="el-icon-search" @click="getLineData" >
                                搜索
                            </el-button>
                            <el-button size="mini" icon="" @click="resetting">
                                重置
                            </el-button>
                        </el-col>
                    </el-row>

                </div>
                <div>
                    <div class="textBtn" :class="isActive == '1'?'active':''" @click="clickBtn('1')">近7天</div>
                    <div class="textBtn" :class="isActive == '2'?'active':''" @click="clickBtn('2')">近30天</div>
                    <div class="textBtn" :class="isActive == '3'?'active':''" @click="clickBtn('3')">近12个月</div>
                </div>
            </div>
            <line-chart :chart-data="lineChartData" />
        </el-row>
    </div>
  </template>

<script>
import { outline, line} from '@/api/user'
import PanelGroup from './components/panelGroup'
import PieChart from './components/pieChart'
import BarChart from './components/barChart'
import LineChart from './components/lineChart'
import { reactYear,oneMonth,oneWeek,getToday } from '@/utils/index'
  export default {
    name: 'Chart',
    components: {
        PanelGroup,
        LineChart,
        PieChart,
        BarChart,
    },
    data() {
      return {
        cityBarData:{
            yAxisData:[],
            seriesData:[]
        },//城市柱状图数据
        genderPieData:{
            legendData:[],
            seriesData:[]
        },//性别饼图
        panelGroupData:{
            online24:0,//24小时内在线用户数
            register24:0,//24小时内注册用户
            todayOnline:0,//今日在线用户数
            todayRegister:0,//今日注册用户
            userCount:0,//用户总数
            yesterdayRegister:0,//昨日注册用户
        },
        latelyTime:[oneWeek(),getToday()],
        isActive:'1',
        //用户注册折线数据
        lineChartData:{
            xData:[],
            yData:[]
        }
      }
    },
    created() {
        // this.latelyTime = this.timeDefault;
        this.getOutLineData()
        this.getLineData()
    },
    methods:{
        getOutLineData(){
            outline().then(res =>{
                Object.assign(this.panelGroupData,res.data)
                this.genderPieData.legendData = []
                this.genderPieData.seriesData = []
                this.cityBarData.yAxisData = []
                this.cityBarData.seriesData = []
                if(res.data.genderPie&&res.data.genderPie.length){
                    res.data.genderPie.forEach(item =>{
                        this.genderPieData.legendData.push(item.name)
                        this.genderPieData.seriesData.push(item)
                    })
                }
                if(res.data.cityBar&&res.data.cityBar.length){
                    res.data.cityBar.forEach(item =>{
                        this.cityBarData.yAxisData.push(item.name)
                        this.cityBarData.seriesData.push(item.value)
                    })
                }
            })
        },
        getLineData(){
            let param = {
                createTimeStart:this.latelyTime[0]+' '+'00:00:00',
                createTimeEnd:this.latelyTime[1]+' '+'23:59:59',
            }
            line(param).then(res =>{
                this.lineChartData.xData = []
                this.lineChartData.yData = []
                if(res.data&&res.data.length){
                    res.data.forEach(item => {
                        this.lineChartData.xData.push(item.name)
                        this.lineChartData.yData.push(item.value)
                    });
                }
            })
        },
        clickBtn(val){
            this.isActive = val
            if(val == '1'){
                this.latelyTime[0] = oneWeek()
            }else if(val == '2'){
                this.latelyTime[0] = oneMonth()
            }else if (val == '3'){
                this.latelyTime[0] = reactYear()
            }
            this.getLineData()
        },
        resetting(){
            this.latelyTime = [oneWeek(),getToday()];
            this.isActive = '1'
            this.getLineData()
        }
    }
  }
  </script>

  <style lang="scss" scoped>
    .dashboard {
        &-container {
            margin: 20px;
        }
        &-text {
        font-size: 30px;
        line-height: 46px;
        }

    }
    .top-class{
        background: #fff;
        padding:10px 20px;
        margin-bottom: 10px;
        .chart-wrapper {
            padding: 16px 16px 0;
            margin-bottom: 10px;
            background: #fff;
            box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
            border-color: rgba(0, 0, 0, .05);
        }
    }
    .textBtn{
        display: inline-block;
        margin-left: 30px;
        color: #9a9a9a;
        cursor: pointer;
        font-size: 14px;
        &:hover{
            color: rgb(3, 118, 250);
        }
    }
    .active{
        color: rgb(3, 118, 250);
    }
    .lineHeader{
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid #eee;
        padding: 0 0 10px 0;
    }
  </style>

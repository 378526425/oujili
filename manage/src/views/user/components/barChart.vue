<template>
    <div :class="className" :style="{height:height,width:width}" />
  </template>
  
  <script>
  import echarts from 'echarts'
  require('echarts/theme/macarons') // echarts theme
  import resize from './mixins/resize'
  
  const animationDuration = 6000
  
  export default {
    mixins: [resize],
    props: {
      className: {
        type: String,
        default: 'chart'
      },
      width: {
        type: String,
        default: '100%'
      },
      height: {
        type: String,
        default: '300px'
      },
      barChartData:{
        type: Object,
        required: true
      }
    },
    data() {
      return {
        chart: null
      }
    },
    watch: {
      barChartData: {
        deep: true,
        handler(val) {
          this.setOptions(val)
        }
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.initChart()
      })
    },
    beforeDestroy() {
      if (!this.chart) {
        return
      }
      this.chart.dispose()
      this.chart = null
    },
    methods: {
      initChart() {
        this.chart = echarts.init(this.$el, 'macarons')
  
        this.setOptions(this.barChartData)
      },
      setOptions({yAxisData,seriesData } = {}) {
        this.chart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
              type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            left: '20',   //距左边边框的距离
            right: '20',  //距右边边框的距离
            bottom: '10',//距下面边框的距离
            top: '20',    //距上面边框的距离
            containLabel: true,
          },
          xAxis: {
            show:false,
            type: 'value',
            axisLine: {
              show: false, //隐藏y轴
            },
            axisTick: {
              show: false,  //刻度线
            },
            splitLine: {     //网格线
              show: false
            },
            splitArea:{
              show:false
            }

          },
          yAxis: {
            type: 'category',
            data: yAxisData,
            axisTick: {
              show: false,  //刻度线
            },
            axisLine: {
              show: false, //隐藏y轴
            },
            splitLine: {     //网格线
              show: false
            }
          },
          series: [
            {
              data: seriesData,
              type: 'bar',
              label: {
                normal:{
                  show:true,
                  position:'right'
                }
              },
            }
          ]
        })
      }
    }
  }
  </script>
  
<template>
  <!-- 概览统计 -->
  <el-row>

    <el-col :span="6">
      <el-statistic :value="summaryData.effectiveActivityCount">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            市场活动
          </div>
        </template>
        <template #suffix>/{{summaryData.totalActivityCount}}</template>
      </el-statistic>
    </el-col>

    <el-col :span="6">
      <el-statistic title="线索总数" :value="summaryData.totalClueCount" />
    </el-col>

    <el-col :span="6">
      <el-statistic title="客户总数" :value="summaryData.totalCustomerCount" />
    </el-col>

    <el-col :span="6">
      <el-statistic :value="summaryData.successTranAmount">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            交易总额
          </div>
        </template>
        <template #suffix>/{{summaryData.totalTranAmount}}</template>
      </el-statistic>
    </el-col>
  </el-row>

  <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
  <div id="saleFunnelChart" style="width: 48%;height:400px; margin-top: 30px; float: left;"></div>
  <!-- 线索来源饼图 -->
  <div id="sourcePieChart" style="width: 48%;height:400px; margin-top: 30px; float: left;"></div>
</template>

<script>
import {doGet} from "../http/httpRequest.js";
import * as echarts from 'echarts';

export default {
  name: "StatisticView",

  data() {
    return {
      summaryData: {}, // 统计数据对象
    }
  },

  mounted() {
    this.loadSummary();
    this.loadSaleFunnelChart();
    this.loadSourcePieChart();
  },

  methods: {
    // 加载统计数据
    loadSummary(){
      doGet("/api/summary/data", {}).then((resp) => {
        if (resp.data.code === 200) {
          this.summaryData = resp.data.data;
        }
      })
    },

    // 加载销售漏斗图
    loadSaleFunnelChart(){
      // 查询数据
      doGet("/api/saleFunnel/data", {}).then((resp) => {
        if (resp.data.code === 200){
          let saleFunnelData = resp.data.data;
          var chartDom = document.getElementById('saleFunnelChart');
          var myChart = echarts.init(chartDom);
          var option;
          // 配置可选项
          option = {
            // 标题组件，包含主标题和副标题。
            title: {
              // 主标题文本，支持使用 \n 换行。
              text: '销售漏斗图',
              left: 'center',
              top:'bottom'
            },
            tooltip: {
              trigger: 'item', // 触发类型
              // {a}（系列名称），{b}（数据项名称），{c}（数值）, {d}（百分比）
              formatter: '{a} <br/>{b} : {c}'
            },
            // 工具栏
            toolbox: {
              // 各工具配置项
              feature: {
                dataView: { readOnly: false },
                restore: {},
                saveAsImage: {}
              }
            },
            legend: {
              //data: ['线索', '交易', '客户', '成交']
            },
            // 系列
            series: [
              {
                // 系列名称
                name: '销售漏斗数据统计',
                type: 'funnel',
                left: '10%',
                top: 60,
                bottom: 60,
                width: '80%',
                min: 0,
                max: 100,
                minSize: '0%',
                maxSize: '100%',
                sort: 'descending',
                gap: 2,
                label: {
                  show: true,
                  position: 'inside'
                },
                labelLine: {
                  length: 10,
                  lineStyle: {
                    width: 1,
                    type: 'solid'
                  }
                },
                itemStyle: {
                  borderColor: '#fff',
                  borderWidth: 1
                },
                emphasis: {
                  label: {
                    fontSize: 20
                  }
                },
                // 数据项
                // data: [
                //   { value: 60, name: '成交' },
                //   { value: 40, name: '交易' },
                //   { value: 20, name: '客户' },
                //   { value: 100, name: '线索' }
                // ]
                data: saleFunnelData
              }
            ]
          };
          // 如果配置了可选项，就把可选项设置到空白的图标对象中
          option && myChart.setOption(option);
        }
      })
    },

    // 加载线索来源饼图
    loadSourcePieChart(){
      doGet("/api/sourcePie/data", {}).then((resp) => {
        if (resp.data.code === 200){
          let sourcePieData = resp.data.data;
          var chartDom = document.getElementById('sourcePieChart');
          var myChart = echarts.init(chartDom);
          var option;
          option = {
            title: {
              text: '线索来源统计',
              left: 'center',
              top: 'bottom'
            },
            tooltip: {
              trigger: 'item'
            },
            legend: {
              left:'center',
            },
            series: [
              {
                name: '线索来源统计',
                type: 'pie',
                radius: '50%',
                // data: [
                //   { value: 1048, name: 'Search Engine' },
                //   { value: 735, name: 'Direct' },
                //   { value: 580, name: 'Email' },
                //   { value: 484, name: 'Union Ads' },
                //   { value: 300, name: 'Video Ads' }
                // ],
                data: sourcePieData,
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };
          option && myChart.setOption(option);
        }
      })
    }
  }

}
</script>

<style scoped>
  .el-row{
    text-align: center;
  }
</style>
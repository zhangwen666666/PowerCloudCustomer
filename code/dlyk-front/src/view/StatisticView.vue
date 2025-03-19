<template>
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
</template>

<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "StatisticView",

  data() {
    return {
      summaryData: {}, // 统计数据对象
    }
  },

  mounted() {
    this.loadSummary();
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
  }

}
</script>

<style scoped>
  .el-row{
    text-align: center;
  }
</style>
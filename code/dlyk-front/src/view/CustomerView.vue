<template>
  <el-button type="primary" class="btn" @click="batchExportExcel">批量导出(Excel)</el-button>
  <el-button type="success" class="btn" @click="chooseExportExcel">选择导出(Excel)</el-button>

  <el-table
      :data="customerList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column property="ownerDO.name" label="负责人" width="120" />
    <el-table-column property="activityDO.name" label="所属活动"/>
    <el-table-column label="姓名">
      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.clueDO.fullName }}</a>
      </template>
    </el-table-column>
    <el-table-column property="appellationDO.typeValue" label="称呼"/>
    <el-table-column property="clueDO.phone" label="手机" width="120"/>
    <el-table-column property="clueDO.weixin" label="微信" width="120"/>
    <el-table-column property="needLoanDO.typeValue" label="是否贷款"/>
    <el-table-column property="intentionStateDO.typeValue" label="意向状态"/>
    <el-table-column property="stateDO.typeValue" label="线索状态"/>
    <el-table-column property="sourceDO.typeValue" label="线索来源"/>
    <el-table-column property="intentionProductDO.name" label="意向产品"/>
    <el-table-column property="nextContactTime" label="下次联系时间" width="165"/>
    <el-table-column label="操作" width="85">
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)">详情</el-button>
      </template>
    </el-table-column>
  </el-table>
  <p>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="total"
        @prev-click="toPage"
        @next-click="toPage"
        @current-change="toPage"/>
  </p>
</template>

<script>
import {doGet, getToken} from "../http/httpRequest.js";
import axios from "axios";
import {messageTip} from "../util/util.js";

export default {
  name: "CustomerView",
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      total: 0,
      //客户列表数据对象，初始值是空
      customerList : [{
        clueDO : {},
        ownerDO : {},
        activityDO : {},
        appellationDO : {},
        needLoanDO : {},
        intentionStateDO : {},
        stateDO : {},
        sourceDO : {},
        intentionProductDO : {},
      }],
      multipleSelection: []
    }
  },
  mounted() {
    this.getCustomerList();
  },

  methods: {
    // 分页函数,pageNum参数是element-plus组件传过来的
    toPage(pageNum) {
      this.pageNum = pageNum;
      this.getCustomerList(this.pageNum, this.pageSize)
    },

    // 多选时触发
    handleSelectionChange(selections){
      this.multipleSelection = []
      selections.forEach((item) => {
        this.multipleSelection.push(item.id)
      })
    },

    // 获取客户列表
    getCustomerList(){
      doGet("/api/customer/list", {
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then((resp) => {
        if (resp.data.code === 200) {
          this.customerList = resp.data.data.list;
          this.total = resp.data.data.total;
        }
      })
    },

    // 批量导出客户到Excel文件中
    batchExportExcel(){
      this.exportExcel(null);
    },

    // 将选中的客户导出到Excel文件中
    chooseExportExcel(){
      if (this.multipleSelection.length === 0){
        messageTip("请选择要导出的客户", "warning")
        return;
      }
      this.exportExcel(this.multipleSelection.join(","));
    },

    // 导出客户到Excel文件中
    exportExcel(ids){
      let iframe = document.createElement("iframe"); // 创建一个iframe标签
      // 这个iframe标签会自动请求这个地址,并将返回结果输出到浏览器
      if (ids) {
        iframe.src = axios.defaults.baseURL + "/api/exportExcel?Authorization=" + getToken() + "&ids=" + ids;
      } else {
        iframe.src = axios.defaults.baseURL + "/api/exportExcel?Authorization=" + getToken();
      }
      document.body.appendChild(iframe); // 将创建的iframe标签做为body的子元素
      iframe.style.display = "none"; // 不在页面上显示这个元素
    },
  }
}
</script>

<style scoped>

</style>
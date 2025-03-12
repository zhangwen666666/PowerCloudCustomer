<template>
  <!-- 搜索框 -->
  <el-form :inline="true" :model="activityQuery">

    <el-form-item label="负责人">
      <el-select v-model="activityQuery.ownerId" placeholder="请选择负责人" clearable
                 style="width: 150px" @click="loadOwner">
        <el-option
            v-for="item in ownerList"
            :key="item.ownerId"
            :label="item.ownerName"
            :value="item.ownerId"
        />
      </el-select>
    </el-form-item>

    <el-form-item label="活动名称">
      <el-input v-model="activityQuery.user" placeholder="请输入活动名称" clearable />
    </el-form-item>

    <el-form-item label="活动时间">
      <el-date-picker
          v-model="activityQuery.startTime"
          type="datetimerange"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          format="YYYY-MM-DD HH:mm:ss"
          date-format="YYYY/MM/DD ddd"
          time-format="A hh:mm:ss"/>
    </el-form-item>

    <el-form-item label="活动预算">
      <el-input v-model="activityQuery.user" placeholder="请输入活动预算" clearable />
    </el-form-item>

    <el-form-item label="创建时间">
      <el-date-picker
          v-model="activityQuery.createTime"
          type="datetime"
          placeholder="请选择创建时间"
      />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSearch">搜 索</el-button>
      <el-button type="primary" plain @click="onReset">重 置</el-button>
    </el-form-item>

  </el-form>

  <el-button type="primary" @click="add">
    录入市场活动
  </el-button>
  <el-button type="danger" @click="delBatch">批量删除</el-button>

  <!-- 数据列表 -->
  <el-table
      :data="activityList"
      :highlight-current-row="true"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection"/>
    <el-table-column type="index" label="序号" width="70px"/>
    <el-table-column property="ownerName" label="负责人" width="100px"/>
    <el-table-column property="name" label="活动名称" show-overflow-tooltip/>
    <el-table-column property="startTime" label="开始时间" show-overflow-tooltip/>
    <el-table-column property="endTime" label="结束时间" show-overflow-tooltip/>
    <el-table-column property="cost" label="活动预算" show-overflow-tooltip/>
    <el-table-column property="createTime" label="创建时间" show-overflow-tooltip/>
    <el-table-column label="操作" show-overflow-tooltip>
      <template #default="scope">
        <el-button :icon="Search" circle @click="view(scope.row.id)"/><!--查看详情按钮-->
        <el-button type="primary" :icon="Edit" circle @click="edit(scope.row.id)"/><!--修改按钮-->
        <el-button type="danger" :icon="Delete" circle @click="del(scope.row.id)"/><!--删除按钮-->
      </template>
    </el-table-column>
  </el-table>

  <!--分页条-->
  <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @current-change="toPage"
      @prev-click="toPage"
      @next-click="toPage"
  />
</template>

<script>
import {doGet} from "../http/httpRequest.js";
import {Delete, Edit, Search} from "@element-plus/icons-vue";
import {messageTip} from "../util/util.js";

export default {
  name: "ActivityView",

  computed: {
    Delete() {
      return Delete
    },
    Search() {
      return Search
    },
    Edit() {
      return Edit
    },
  },

  mounted() {
    this.getActivityList(this.pageNum, this.pageSize);
  },

  data(){
    return {
      activityQuery: {}, // 查询条件
      activityList: [], // 活动数据列表
      ownerList:[], // 负责人列表
      pageNum: 1, // 当前页数
      pageSize: 10, // 每页展示的记录条数
      total: 0, // 总记录条数
    }
  },

  methods: {
    // 分页函数,pageNum参数是element-plus组件传过来的
    toPage(pageNum) {
      this.pageNum = pageNum;
      this.getActivityList(pageNum, this.pageSize)
    },

    // 查询活动列表数据
    getActivityList(pageNum, pageSize) {
      doGet("/api/activity/list", {
        pageNum: pageNum, // 查询第几页
        pageSize: pageSize // 每页展示几条数据
      }).then((res) => {
        if (res.data.code === 200) {
          this.activityList = res.data.data.list
          this.total = res.data.data.total
        }
      })
    },

    // 加载负责人列表数据（负责人id和姓名）
    loadOwner(){
      doGet("/api/activity/ownerList", {}).then((res) => {
        if (res.data.code === 200) {
          this.ownerList = res.data.data
        }else {
          messageTip(res.data.msg, 'error')
        }
      })
    },

    add(){},

    delBatch(){},

    handleSelectionChange(){}
  }
}
</script>

<style scoped>
  .el-form{
    margin-bottom: 20px;
  }
  .el-table{
    margin-bottom: 20px;
    margin-top: 20px;
  }
</style>
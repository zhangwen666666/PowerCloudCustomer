<template>
  <!-- 搜索框 -->
  <el-form :inline="true" :model="activityQuery" :rules="activityRules">

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
      <el-input v-model="activityQuery.name" placeholder="请输入活动名称" clearable />
    </el-form-item>

    <el-form-item label="活动时间">
      <el-date-picker
          v-model="activityQuery.activityTime"
          type="datetimerange"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          value-format="YYYY-MM-DD HH:mm:ss"/>
    </el-form-item>

    <el-form-item label="活动预算" prop="cost">
      <el-input v-model="activityQuery.cost" placeholder="请输入活动预算" clearable/>
    </el-form-item>

    <el-form-item label="创建时间">
      <el-date-picker
          v-model="activityQuery.createTime"
          type="datetime"
          placeholder="请选择创建时间"
          value-format="YYYY-MM-DD HH:mm:ss"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSearch">搜 索</el-button>
      <el-button type="primary" plain @click="onReset">重 置</el-button>
    </el-form-item>

  </el-form>

  <el-button type="primary" @click="() => this.$router.push('/dashboard/activity/add')">
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
        <!--查看详情按钮-->
        <el-button :icon="Search" circle
                   @click="() => this.$router.push(`/dashboard/activity/${scope.row.id}`)"/>
        <!--修改按钮-->
        <el-button type="primary" :icon="Edit" circle
                   @click="() => this.$router.push(`/dashboard/activity/edit/${scope.row.id}`)"/>
        <el-button type="danger" :icon="Delete" circle @click="del(scope.row.id)"/><!--删除按钮-->
      </template>
    </el-table-column>
  </el-table>

  <!--分页条-->
  <el-pagination
      v-if="total !== 0"
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
      // 市场活动搜索表单验证规则
      activityRules: {
        cost: [{pattern: /^\d+(\.\d{1,2})?$/, message: '必须是数字或两位小数', trigger: 'blur'}]
      }
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
      const [startTime, endTime] = this.activityQuery.activityTime || [];
      // console.log(startTime, endTime)
      doGet("/api/activity/list", {
        pageNum: pageNum, // 查询第几页
        pageSize: pageSize, // 每页展示几条数据
        ownerId: this.activityQuery.ownerId, // 负责人id
        name: this.activityQuery.name, // 活动名称
        startTime: startTime, // 开始时间
        endTime: endTime, // 结束时间
        cost: this.activityQuery.cost, // 活动预算
        createTime: this.activityQuery.createTime // 创建时间
      }).then((res) => {
        if (res.data.code === 200) {
          this.activityList = res.data.data.list
          this.total = res.data.data.total
        }else {
          messageTip(res.data.msg, 'error')
        }
      })
    },

    // 加载负责人列表数据（负责人id和姓名）
    loadOwner(){
      doGet("/api/user/ownerList", {}).then((res) => {
        if (res.data.code === 200) {
          this.ownerList = res.data.data
        }else {
          messageTip(res.data.msg, 'error')
        }
      })
    },

    // 根据条件进行搜索
    onSearch(){
      this.getActivityList(1, this.pageSize);
    },

    // 重置搜索条件
    onReset(){
      this.activityQuery = {};
    },

    delBatch(){},

    handleSelectionChange(){},
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
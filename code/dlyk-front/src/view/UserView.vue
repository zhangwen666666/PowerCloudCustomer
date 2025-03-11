<template>
  <el-button type="primary">添加用户</el-button>
  <el-button type="danger">批量删除</el-button>

  <!-- 数据列表 -->
  <el-table
      ref="multipleTableRef"
      :data="userList"
      :highlight-current-row="true"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection"/>
    <el-table-column type="index" label="序号" width="70px"/>
    <el-table-column property="loginAct" label="账号"/>
    <el-table-column property="name" label="姓名" show-overflow-tooltip />
    <el-table-column property="phone" label="手机" show-overflow-tooltip />
    <el-table-column property="email" label="邮箱" show-overflow-tooltip />
    <el-table-column property="createTime" label="创建时间" show-overflow-tooltip />
    <el-table-column label="操作" show-overflow-tooltip >
      <template #default="scope">
        <el-button :icon="Search" circle @click="view(scope.row.id)" /><!--查看详情按钮-->
        <el-button type="primary" :icon="Edit" circle @click="edit(scope.row.id)"/><!--修改按钮-->
        <el-button type="danger" :icon="Delete" circle @click="del(scope.row.id)"/><!--删除按钮-->
      </template>
    </el-table-column>
  </el-table>

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

export default {
  name: "UserView",
  computed: {
    Delete() {
      return Delete
    },
    Search() {
      return Search
    },
    Edit() {
      return Edit
    }
  },

  data(){
    return {
      // 用户列表数据
      userList: [],
      pageNum: 1, // 当前页数
      pageSize: 10, // 每页展示的记录条数
      total: 0, // 总记录条数
    }
  },

  mounted() {
    this.getUserList(this.pageNum, this.pageSize);
  },

  methods: {
    // 全选或取消全选触发该函数
    handleSelectionChange(){

    },

    // 查询用户列表数据
    getUserList(pageNum, pageSize){
      doGet("/api/user/list", {
        pageNum: pageNum, // 查询第几页
        pageSize: pageSize // 每页展示几条数据
      }).then((res) => {
        if (res.data.code === 200) {
          this.userList = res.data.data.list
          this.total = res.data.data.total
        }
      })
    },

    // 分页函数,pageNum参数是element-plus组件传过来的
    toPage(pageNum){
      this.pageNum = pageNum;
      this.getUserList(pageNum, this.pageSize)
    },

    // 查看用户详情
    view(id){
      this.$router.push(`/dashboard/user/${id}`);
    }
  }
}
</script>


<style scoped>
  .el-pagination{
    margin-top: 12px;
  }
  .el-table{
    margin-top: 20px;
  }
</style>
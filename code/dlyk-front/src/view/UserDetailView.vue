<template>
  <el-form ref="loginRefForm" :model="userDetail" label-width="120px">
    <el-form-item label="id">
      <div class="desc">&nbsp;{{userDetail.id}}</div>
    </el-form-item>

    <el-form-item label="账号">
      <div class="desc">&nbsp;{{userDetail.loginAct}}</div>
    </el-form-item>

    <el-form-item label="姓名">
      <div class="desc">&nbsp;{{userDetail.name}}</div>
    </el-form-item>

    <el-form-item label="手机">
      <div class="desc">&nbsp;{{userDetail.phone}}</div>
    </el-form-item>

    <el-form-item label="邮箱">
      <div class="desc">&nbsp;{{userDetail.email}}</div>
    </el-form-item>

    <el-form-item label="账号是否未过期">
      <div class="desc">&nbsp;{{userDetail.accountNoExpired === 1 ? '是' : '否'}}</div>
    </el-form-item>

    <el-form-item label="账号是否未锁定">
      <div class="desc">&nbsp;{{userDetail.accountNoLocked === 1 ? '是' : '否'}}</div>
    </el-form-item>

    <el-form-item label="账号是否启用">
      <div class="desc">&nbsp;{{userDetail.accountEnabled === 1 ? '是' : '否'}}</div>
    </el-form-item>

    <el-form-item label="创建时间">
      <div class="desc">&nbsp;{{userDetail.createTime}}</div>
    </el-form-item>

    <el-form-item label="创建人">
      <div class="desc">&nbsp;{{userDetail.createrName}}</div>
    </el-form-item>

    <el-form-item label="修改时间">
      <div class="desc">&nbsp;{{userDetail.editTime}}</div>
    </el-form-item>

    <el-form-item label="修改人">
      <div class="desc">&nbsp;{{userDetail.editorName}}</div>
    </el-form-item>

    <el-form-item label="最近登录时间">
      <div class="desc">&nbsp;{{userDetail.lastLoginTime}}</div>
    </el-form-item>

    <el-form-item>
      <el-button type="success" round @click="goBack">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "UserDetailView",

  data() {
    return {
      userDetail: {},// 用户详情对象
    }
  },

  mounted() {
    // 页面加载时查询用户详情
    let id = this.$route.params.id; // 获取路由参数
    this.loadUserDetail(id);
  },

  methods: {
    // 加载用户详情
    loadUserDetail(id) {
      doGet(`/api/user/${id}`, {}).then((resp) => {
        if (resp.data.code === 200) {
          this.userDetail = resp.data.data;
        }
      })
    },

    // 返回
    goBack(){
      this.$router.go(-1);
    }
  },
}
</script>

<style scoped>
  .desc{
    width: 100%;
    background: lightgray;
    padding-left: 10px;
  }
</style>
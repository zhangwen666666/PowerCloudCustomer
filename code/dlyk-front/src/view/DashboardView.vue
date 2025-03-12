<template>
  <el-container>
    <!--左侧-->
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <div class="menuTitle">
        @动力云客管理系统
      </div>
      <el-menu
          active-text-color="#ffd04b"
          background-color="#334157"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
          style="border-right: 0px solid"
          :collapse="isCollapse"
          :collapse-transition="false"
          :router="true"
          :unique-opened="true"
      >
        <!--市场活动菜单-->
        <el-sub-menu index="1">
          <template #title>
            <el-icon><OfficeBuilding/></el-icon>
            <span>市场活动</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon><Notification /></el-icon>
            市场活动
          </el-menu-item>
          <el-menu-item index="1-2">
            <el-icon><Histogram /></el-icon>
            市场统计
          </el-menu-item>
        </el-sub-menu>

        <!--线索管理菜单-->
        <el-sub-menu index="2">
          <template #title>
            <el-icon><Operation /></el-icon>
            <span>线索管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon><Connection /></el-icon>
            线索管理
          </el-menu-item>
          <el-menu-item index="1-2">
            <el-icon><Histogram /></el-icon>
            线索统计
          </el-menu-item>
        </el-sub-menu>

        <!--客户管理菜单-->
        <el-sub-menu index="3">
          <template #title>
            <el-icon><Avatar /></el-icon>
            <span>客户管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon><UserFilled /></el-icon>
            客户管理
          </el-menu-item>
        </el-sub-menu>

        <!--交易管理菜单-->
        <el-sub-menu index="4">
          <template #title>
            <el-icon><Money /></el-icon>
            <span>交易管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon><Coin /></el-icon>
            交易管理
          </el-menu-item>
        </el-sub-menu>

        <!--产品管理菜单-->
        <el-sub-menu index="5">
          <template #title>
            <el-icon><Goods /></el-icon>
            <span>产品管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon><GoodsFilled /></el-icon>
            产品管理
          </el-menu-item>
        </el-sub-menu>

        <!--字典管理菜单-->
        <el-sub-menu index="6">
          <template #title>
            <el-icon><Reading /></el-icon>
            <span>字典管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon><Document /></el-icon>
            字典管理
          </el-menu-item>
        </el-sub-menu>

        <!--用户管理菜单-->
        <el-sub-menu index="7">
          <template #title>
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/dashboard/user">
            <el-icon><User /></el-icon>
            用户管理
          </el-menu-item>
        </el-sub-menu>

        <!--系统管理菜单-->
        <el-sub-menu index="8">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon><Tools /></el-icon>
            系统管理
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!--右侧-->
    <el-container class="rightContent">
      <!--上-->
      <el-header>
        <el-icon class="showMenu" @click="showMenu()"><Fold /></el-icon>

        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">
            {{user.name}}<el-icon class="el-icon--right"><arrow-down /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>我的资料</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <!-- 中 -->
      <el-main>
        <router-view v-if="isRouterAlive"/>
      </el-main>
      <!-- 下 -->
      <el-footer>@版权所有 2000-2099 王德发 山西省大同市云冈区云洲西城A区11栋</el-footer>
    </el-container>
  </el-container>
</template>

<script>
import {doGet, removeToken} from "../http/httpRequest.js";
import {messageConfirm, messageTip} from "../util/util.js";

export default {
  name: "DashboardView",
  data(){
    return {
      isCollapse: false, // 控制左侧菜单左右折叠，true是折叠
      user: {}, // 当前登录用户的信息
      isRouterAlive: true // 控制路由是否重新加载
    }
  },

  // 这里定义的方法，属性，可以传递给子页面
  provide(){
    return {
      reload: () => {
        this.isRouterAlive = false; // 先设为 false
        this.$nextTick(() => {
          this.isRouterAlive = true; // 再设为 true，触发刷新
        });
      }
    }
  },

  mounted() {
    // 加载当前登录用户
    this.loadLoginUser();
  },

  methods: {
    // 左侧菜单左右展开和折叠
    showMenu(){
      this.isCollapse = !this.isCollapse;
    },

    // 加载当前登录用户
    loadLoginUser(){
      doGet("/api/login/info", {}).then((rep) => {
          // console.log(rep)
        this.user = rep.data.data
      })
    },
    
    // 退出登录
    logout(){
      doGet("/api/logout", {}).then((rep) => {
        if (rep.data.code === 200){
          removeToken(); // 删除前端存储的token
          messageTip("退出成功", "success");
          //window.location.href = "/"
          this.$router.push("/")
        } else {
          messageConfirm("退出异常,是否强制退出?").then(() => {
            removeToken();
            messageTip("退出成功", "success");
            // window.location.href = "/"
            this.$router.push("/")
          }).catch(() => {
              messageTip("取消强制退出", "warning");
          })
        }
      })
    },
  }
}
</script>

<style scoped>
  .el-aside{
    background: #1a1a1a;
  }
  .el-header{
    background: lightblue;
    height: 35px;
    line-height: 35px;
  }
  .el-footer{
    background: aliceblue;
    height: 35px;
    line-height: 35px;
    text-align: center;
  }
  .rightContent{
    height: calc(100vh);
  }
  .menuTitle{
    height: 35px;
    color: white;
    text-align: center;
    line-height: 35px;
  }
  .showMenu{
    cursor: pointer;
  }
  .el-dropdown{
    float: right;
    line-height: 35px;
  }
</style>
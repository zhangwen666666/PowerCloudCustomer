<template>
  <el-container>
    <!--  左侧  -->
    <el-aside width="200px">
      <img src="../assets/loginBox.svg">
      <p class="imgTitle">欢迎使用动力云客系统</p>
    </el-aside>
    <!--  右侧  -->
    <el-main>
      <div class="loginTitle">
        欢迎登录
      </div>

      <el-form ref="loginRefForm" :model="user" :rules="loginRules" label-width="120px">
        <el-form-item label="账号" prop="loginAct">
          <el-input v-model="user.loginAct"/>
        </el-form-item>

        <el-form-item label="密码" prop="loginPwd">
          <el-input type="password" v-model="user.loginPwd"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="login()">登 录</el-button>
        </el-form-item>

        <el-form-item>
          <el-checkbox label="记住我" v-model="user.rememberMe"/>
        </el-form-item>
      </el-form>

    </el-main>
  </el-container>
</template>

<script>
import {doGet, doPost, removeToken} from "../http/httpRequest.js";
import {getTokenName, messageTip} from "../util/util.js";

export default {
  // 组件的名称
  name: "LoginView",

  // 定义页面使用到的变量, 初始值为空
  data() {
    return {
      user: {
        loginAct: '',
        loginPwd: '',
        rememberMe: false,
      },
      // 定义登录表单的验证规则
      loginRules: {
        // 定义账号的验证规则，规则可以有多个，所以是数组
        loginAct: [
          {required: true, message: '请输入登录账号', trigger: 'blur'},
        ],
        // 定义密码的验证规则，规则可以有多个，所以是数组
        loginPwd: [
          {required: true, message: '请输入登录密码', trigger: 'blur'},
          {min: 6, max: 16, message: '登录密码长度为6-16位', trigger: 'blur'},
        ]
      }
    }
  },

  mounted() {
    this.freeLogin();
  },

  // 页面上需要使用的js函数，都在methods属性中定义
  methods: {
    // 登录函数
    login() {
      //alert(1223)
      // 执行表单的验证规则，验证输入框的合法性
      this.$refs.loginRefForm.validate((isValid) => {
        // isValid是验证后的结果，true表示通过
        if (!isValid) {
          // 验证不通过
          return;
        }

        // 验证通过，提交登录
        let formData = new FormData();
        formData.append("loginAct", this.user.loginAct);
        formData.append("loginPwd", this.user.loginPwd);
        formData.append("rememberMe", this.user.rememberMe);
        // 这里传数据需要使用FormData格式的数据，不能使用JSON格式的数据
        // 因为后端登录时采用的框架是SpringSecurity，不能解析JSON格式的数据

        doPost("/api/login", formData).then((rep) => {
          // console.log(rep)
          if (rep.data.code !== 200) {
            // 登录失败
            messageTip("登录失败", "error");
            return
          }

          // 登录成功 使用element-plus的组件做消息提示
          messageTip("登录成功", "success");
          // console.log(rep.data.data)

          // 删除历史存储的token
          removeToken();

          // 前端存储jwt
          if (this.user.rememberMe === true) {
            // 选择了记住我，存储到localStorage中
            window.localStorage.setItem(getTokenName(), rep.data.data)
          } else {
            // 存储到sessionStorage中
            window.sessionStorage.setItem(getTokenName(), rep.data.data)
          }
          // 跳转到系统的主页面
          this.$router.push("/dashboard")
          //window.location.href = "/dashboard";
        });
      })
    },

    // 自动登录
    freeLogin(){
      let token = window.localStorage.getItem(getTokenName());
      if(!token){
        return;
      }
      doGet("/api/login/free", {}).then((resp) => {
        if (resp.data.code === 200) {
          // token验证通过，免登录
          this.$router.push("/dashboard")
          //window.location.href = "/dashboard";
        }
      })
    },
  }
}
</script>

<style scoped>
.el-aside {
  background: #1a1a1a;
  width: 40%;
  text-align: center;
}

.el-main {
  height: calc(100vh);
}

img {
  height: 400px;
}

.imgTitle {
  color: white;
  font-size: 28px;
}

.el-form {
  width: 60%;
  margin: auto;
}

.loginTitle {
  text-align: center;
  margin-top: 100px;
  margin-bottom: 25px;
  font-weight: bold;
}

.el-button {
  width: 100%;
}
</style>
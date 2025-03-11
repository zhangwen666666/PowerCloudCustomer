<template>
  <el-button type="primary" @click="dialogVisible = true">
    添加用户
  </el-button>
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
    <el-table-column property="name" label="姓名" show-overflow-tooltip/>
    <el-table-column property="phone" label="手机" show-overflow-tooltip/>
    <el-table-column property="email" label="邮箱" show-overflow-tooltip/>
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

  <!-- 新增用户的弹窗(对话框) -->
  <el-dialog v-model="dialogVisible" title="新增用户" center width="60%" draggable>
    <!-- 表单内容 -->
    <el-form ref="addRefForm" :model="newUser" :rules="newUserRules" label-width="110px">
      <el-form-item label="账号" prop="loginAct">
        <el-input v-model="newUser.loginAct"/>
      </el-form-item>

      <el-form-item label="密码" prop="loginPwd">
        <el-input type="password" v-model="newUser.loginPwd"/>
      </el-form-item>

      <el-form-item label="确认密码" prop="loginPwdConfirm">
        <el-input type="password" v-model="newUser.loginPwdConfirm"/>
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="newUser.name"/>
      </el-form-item>

      <el-form-item label="手机" prop="phone">
        <el-input v-model="newUser.phone"/>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="newUser.email"/>
      </el-form-item>

      <el-form-item label="账号未过期" prop="accountNoExpired">
        <el-select v-model="newUser.accountNoExpired" class="m-2" placeholder="请选择" style="width: 100%">
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>

      <el-form-item label="密码未过期" prop="credentialsNoExpired">
        <el-select v-model="newUser.credentialsNoExpired" class="m-2" placeholder="请选择" style="width: 100%">
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>

      <el-form-item label="账号未锁定" prop="accountNoLocked">
        <el-select v-model="newUser.accountNoLocked" class="m-2" placeholder="请选择" style="width: 100%">
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>

      <el-form-item label="账号是否启用" prop="accountEnabled">
        <el-select v-model="newUser.accountEnabled" class="m-2" placeholder="请选择" style="width: 100%">
          <el-option label="是" value="1"/>
          <el-option label="否" value="0"/>
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="userSubmit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {doGet, doPost} from "../http/httpRequest.js";
import {Delete, Edit, Search} from "@element-plus/icons-vue";
import {messageTip} from "../util/util.js";

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
    },
  },

  data() {
    return {
      // 用户列表数据
      userList: [],
      pageNum: 1, // 当前页数
      pageSize: 10, // 每页展示的记录条数
      total: 0, // 总记录条数
      dialogVisible: false, // 新增用户对话框是否弹出
      newUser: {}, // 新增用户的信息

      // 定义登录表单的验证规则
      newUserRules: {
        // 定义账号的验证规则，规则可以有多个，所以是数组
        loginAct: [
          {required: true, message: '请输入登录账号', trigger: 'blur'},
        ],
        // 定义密码的验证规则，规则可以有多个，所以是数组
        loginPwd: [
          {required: true, message: '请输入登录密码', trigger: 'blur'},
          {min: 6, max: 16, message: '登录密码长度为6-16位', trigger: 'blur'},
        ],
        loginPwdConfirm: [{validator: this.validatePass, trigger: 'blur'}],
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
          {pattern: /^(?:[\u4e00-\u9fa5·]{2,16})$/, message: '必须是中文姓名', trigger: 'blur'}
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {pattern: /^(?:(?:\+|00)86)?1[3-9]\d{9}$/, message: '手机号格式有误', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {
            pattern: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
            message: '邮箱格式有误',
            trigger: 'blur'
          }
        ],
        accountNoExpired: [
          {required: true, message: '请选择账号是否未过期', trigger: 'blur'},
        ],
        credentialsNoExpired: [
          {required: true, message: '请选择密码是否未过期', trigger: 'blur'},
        ],
        accountNoLocked: [
          {required: true, message: '请选择账号是否未被锁定', trigger: 'blur'},
        ],
        accountEnabled: [
          {required: true, message: '请选择账号是否启用', trigger: 'blur'},
        ],
      }
    }
  },

  mounted() {
    this.getUserList(this.pageNum, this.pageSize);
  },

  methods: {
    // 全选或取消全选触发该函数
    handleSelectionChange() {

    },

    // 查询用户列表数据
    getUserList(pageNum, pageSize) {
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
    toPage(pageNum) {
      this.pageNum = pageNum;
      this.getUserList(pageNum, this.pageSize)
    },

    // 查看用户详情
    view(id) {
      this.$router.push(`/dashboard/user/${id}`);
    },

    // 验证确认密码
    validatePass(rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次确认密码'))
      } else if (value !== this.newUser.loginPwd) {
        callback(new Error("两次密码不匹配"))
      } else {
        callback()
      }
    },

    // 新增用户
    userSubmit() {
      this.$refs.addRefForm.validate((isValid) => {
        if (!isValid){
          // 表单验证未通过，不能提交表单
          return
        }
        doPost("api/user", this.newUser).then((resp) => {
          //console.log(resp)
          if (resp.data.code === 200) {
            messageTip("添加成功", "success")
          } else {
            messageTip("添加失败", "error")
          }
        })
        this.dialogVisible = false
        this.getUserList(this.pageNum, this.pageSize)
      })
    }
  }
}
</script>


<style scoped>
.el-pagination {
  margin-top: 12px;
}

.el-table {
  margin-top: 20px;
}
</style>
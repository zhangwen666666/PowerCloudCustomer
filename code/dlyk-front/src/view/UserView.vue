<template>
  <el-button type="primary" @click="addUser">
    添加用户
  </el-button>
  <el-button type="danger" @click="delBatch">批量删除</el-button>

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
  <el-dialog v-model="dialogVisible" :title="newUser.id !== undefined ? '修改用户' : '新增用户'" center width="60%"
             draggable>
    <!-- 表单内容 -->
    <el-form ref="addRefForm" :model="newUser" :rules="newUserRules" label-width="110px">
      <el-form-item label="账号" prop="loginAct">
        <el-input v-model="newUser.loginAct"/>
      </el-form-item>

      <el-form-item label="密码" prop="loginPwd" v-if="newUser.id === undefined">
        <el-input type="password" v-model="newUser.loginPwd"/>
      </el-form-item>

      <el-form-item label="确认密码" prop="loginPwdConfirm" v-if="newUser.id === undefined">
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
          <el-option v-for="option in options" :key="option.value" :label="option.label" :value="option.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="密码未过期" prop="credentialsNoExpired">
        <el-select v-model="newUser.credentialsNoExpired" class="m-2" placeholder="请选择" style="width: 100%">
          <el-option v-for="option in options" :key="option.value" :label="option.label" :value="option.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="账号未锁定" prop="accountNoLocked">
        <el-select v-model="newUser.accountNoLocked" class="m-2" placeholder="请选择" style="width: 100%">
          <el-option v-for="option in options" :key="option.value" :label="option.label" :value="option.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="账号是否启用" prop="accountEnabled">
        <el-select v-model="newUser.accountEnabled" class="m-2" placeholder="请选择" style="width: 100%">
          <el-option v-for="option in options" :key="option.value" :label="option.label" :value="option.value"/>
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
import {doDel, doGet, doPost, doPut} from "../http/httpRequest.js";
import {Delete, Edit, Search} from "@element-plus/icons-vue";
import {messageConfirm, messageTip} from "../util/util.js";

export default {
  name: "UserView",
  inject: ['reload'], // 注入父页面提供的reload函数
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
      options: [
        {label: '是', value: 1},
        {label: '否', value: 0},
      ],

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
      },

      // 当前选中的用户id数组
      userIds: [],
    }
  },

  mounted() {
    this.getUserList(this.pageNum, this.pageSize);
  },

  methods: {
    // 全选或取消全选触发该函数
    handleSelectionChange(selections) {
      this.userIds = []
      // console.log(selections)
      selections.forEach((item) => {
        // console.log(item.id)
        this.userIds.push(item.id)
      })
      console.log(this.userIds)
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

    addUser() {
      this.newUser = {}
      this.dialogVisible = true
    },

    // 提交新增用户(提交编辑用户)
    userSubmit() {
      this.$refs.addRefForm.validate((isValid) => {
        if (!isValid) {
          // 表单验证未通过，不能提交表单
          return
        }
        if (this.newUser.id !== undefined) {
          // 编辑
          messageConfirm("修改之后不能撤销, 是否确认修改").then(() => {
            doPut('api/user', this.newUser).then((resp) => {
              if (resp.data.code === 200) {
                messageTip("修改成功", "success")
                this.reload();
              } else {
                messageTip("修改失败", "error")
              }
            })
          }).catch(() => {
            messageTip("取消修改", "warning")
          })
          return;
        }
        doPost("api/user", this.newUser).then((resp) => {
          //console.log(resp)
          if (resp.data.code === 200) {
            messageTip("添加成功", "success")
            // this.dialogVisible = false
            // this.getUserList(this.pageNum, this.pageSize)
            this.reload();
          } else {
            messageTip("添加失败", "error")
          }
        })
      })
    },

    // 修改用户 (和新增用户公用一个页面)
    edit(id) {
      this.dialogVisible = true
      doGet(`/api/user/${id}`).then((resp) => {
        if (resp.data.code === 200) {
          this.newUser = resp.data.data
        }
      })
    },

    // 删除用户
    del(id) {
      messageConfirm("删除之后不能撤销, 是否确认删除?").then(() => {
        doDel(`/api/user/${id}`, {}).then((resp) => {
          if (resp.data.code === 200) {
            messageTip("删除成功", "success")
            this.reload();
          } else {
            // console.log(resp.data)
            messageTip("删除失败--" + resp.data.msg, "error")
          }
        })
      }).catch(() => {
        messageTip("取消删除", "warning")
      })
    },

    // 批量删除用户
    delBatch() {
      if (this.userIds.length === 0){
        messageTip("请选择要删除的用户", "warning")
        return
      }
      messageConfirm("删除之后不能撤销, 是否确认删除?").then(() => {
        doDel("/api/user", {
          ids: this.userIds.join(",") // 数组转为字符串
        }).then((resp) => {
          if (resp.data.code === 200) {
            messageTip("批量删除成功", "success")
            this.reload();
          } else {
            // console.log(resp.data)
            messageTip("批量删除失败--" + resp.data.msg, "error")
          }
        })
      }).catch(() => {
        messageTip("取消删除", "warning")
      })
    },
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
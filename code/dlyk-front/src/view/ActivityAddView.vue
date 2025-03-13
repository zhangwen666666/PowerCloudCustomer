<template>
  <el-form ref="addRefForm" :model="activity" :rules="activityRules" label-width="110px">
    <el-form-item label="负责人" prop="ownerId">
      <el-select v-model="activity.ownerId" placeholder="请选择负责人" clearable style="width: 100%">
        <el-option
            v-for="item in ownerList"
            :key="item.ownerId"
            :label="item.ownerName"
            :value="item.ownerId"
        />
      </el-select>
    </el-form-item>

    <el-form-item label="活动名称" prop="name">
      <el-input v-model="activity.name" style="width: 100%"/>
    </el-form-item>

    <el-form-item label="开始时间" prop="startTime">
      <el-date-picker
          v-model="activity.startTime"
          type="datetime"
          placeholder="请选择开始时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          style="width: 100%"/>
    </el-form-item>

    <el-form-item label="结束时间" prop="endTime">
      <el-date-picker
          v-model="activity.endTime"
          type="datetime"
          placeholder="请选择结束时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          style="width: 100%"/>
    </el-form-item>

    <el-form-item label="活动预算" prop="cost">
      <el-input v-model="activity.cost"/>
    </el-form-item>

    <el-form-item label="活动描述" prop="description">
      <el-input
          v-model="activity.description"
          :rows="5"
          type="textarea"
          placeholder="请输入活动描述"
      />
    </el-form-item>

    <el-form-item class="buttonBottom">
      <el-button type="primary" @click="submitData">提 交</el-button>
      <el-button type="primary" plain @click="() => this.$router.go(-1)">返回</el-button>
    </el-form-item>
  </el-form>
</template>


<script>
import {doGet, doPost, doPut} from "../http/httpRequest.js";
import {messageConfirm, messageTip} from "../util/util.js";

export default {
  name: "ActivityAddView",

  data(){
    return {
      activity: {}, // 活动对象
      ownerList:[], // 负责人列表
      isEdit: false, // 是否是编辑页面
      activityRules: {
        ownerId: [{required: true, message: '请选择负责人', trigger: 'blur'}],
        name: [{required: true, message: '请输入活动名称', trigger: 'blur'}],
        startTime: [{required: true, message: '请选择开始时间', trigger: 'blur'}],
        endTime: [{required: true, message: '请选择结束时间', trigger: 'blur'}],
        cost: [
          {required: true, message: '请输入活动预算', trigger: 'blur'},
          {pattern: /^\d+(\.\d{1,2})?$/, message: '必须是数字或两位小数', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入活动描述', trigger: 'blur'},
          {min: 5, max: 255, message: '活动描述5-255个字', trigger: 'blur'}
        ]
      }
    }
  },

  mounted() {
    this.loadOwner();
    // 编辑时需要加载数据
    this.loadActivity();
  },

  methods: {
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

    submitData(){
      this.$refs.addRefForm.validate((isValid) => {
        if (!isValid) {
          // 表单验证未通过，不能提交表单
          return
        }
        if (this.isEdit === true) {
          // 编辑
          messageConfirm("修改之后不能撤销, 是否确认修改").then(() => {
            doPut('api/activity', this.activity).then((resp) => {
              if (resp.data.code === 200) {
                messageTip("修改成功", "success")
                this.$router.push("/dashboard/activity")
              } else {
                messageTip("修改失败", "error")
              }
            })
          }).catch(() => {
            messageTip("取消修改", "warning")
          })
          return;
        }
        doPost("api/activity", this.activity).then((resp) => {
          //console.log(resp)
          if (resp.data.code === 200) {
            messageTip("添加成功", "success")
            this.$router.push("/dashboard/activity")
          } else {
            messageTip("添加失败", "error")
          }
        })
      })
    },

    loadActivity(){
      let id = this.$route.params.id;
      if (id === undefined){
        // 新增页面
        return;
      }
      doGet("/api/activity/" + id, {}).then((res) => {
        if (res.data.code === 200) {
          this.activity = res.data.data
        }else {
          messageTip(res.data.msg, 'error')
        }
      })
      this.isEdit = true;
    },
  }
}
</script>

<style scoped>
  .buttonBottom{
    margin-top: 30px;
  }
</style>
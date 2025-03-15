<template>
  <!-- 展示活动详情数据 -->
  <el-form ref="remarkRefForm" :model="remark" label-width="120px" :rules="remarkRules">
    <el-form-item label="id">
      <div class="desc">&nbsp;{{ activityDetail.id }}</div>
    </el-form-item>

    <el-form-item label="负责人">
      <div class="desc">&nbsp;{{ activityDetail.ownerName }}</div>
    </el-form-item>

    <el-form-item label="活动名称">
      <div class="desc">&nbsp;{{ activityDetail.name }}</div>
    </el-form-item>

    <el-form-item label="开始时间">
      <div class="desc">&nbsp;{{ activityDetail.startTime }}</div>
    </el-form-item>

    <el-form-item label="结束时间">
      <div class="desc">&nbsp;{{ activityDetail.endTime }}</div>
    </el-form-item>

    <el-form-item label="预算">
      <div class="desc">&nbsp;{{ activityDetail.cost }}</div>
    </el-form-item>

    <el-form-item label="活动描述">
      <div class="desc">&nbsp;{{ activityDetail.description }}</div>
    </el-form-item>

    <el-form-item label="创建时间">
      <div class="desc">&nbsp;{{ activityDetail.createTime }}</div>
    </el-form-item>

    <el-form-item label="创建人">
      <div class="desc">&nbsp;{{ activityDetail.createrName }}</div>
    </el-form-item>

    <el-form-item label="修改时间">
      <div class="desc">&nbsp;{{ activityDetail.editTime }}</div>
    </el-form-item>

    <el-form-item label="修改人">
      <div class="desc">&nbsp;{{ activityDetail.editorName }}</div>
    </el-form-item>

    <el-form-item label="填写备注" prop="noteContent">
      <el-input
          v-model="remark.noteContent"
          :rows="7"
          type="textarea"
          placeholder="请输入活动备注"
      />
    </el-form-item>

    <el-form-item>
      <el-button type="success" round @click="remarkSubmit">提交</el-button>
      <el-button type="success" round @click="() => this.$router.go(-1)">返回</el-button>
    </el-form-item>
  </el-form>

  <!-- 展示备注数据列表 -->
  <el-table
      :data="activityRemarkList"
      :highlight-current-row="true"
      :table-layout="'auto'"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="70px"/>
    <el-table-column property="noteContent" label="备注内容"/>
    <el-table-column property="createTime" label="备注时间" show-overflow-tooltip/>
    <el-table-column property="createrName" label="备注人" show-overflow-tooltip/>
    <el-table-column property="editTime" label="编辑时间" show-overflow-tooltip/>
    <el-table-column property="editorName" label="编辑人" show-overflow-tooltip/>
    <el-table-column label="操作" width="120px">
      <template #default="scope">
        <!--修改按钮-->
        <el-button type="primary" :icon="Edit" circle
                   @click="edit(scope.row)"/>
        <el-button type="danger" :icon="Delete" circle @click="delRemark(scope.row.id)"/><!--删除按钮-->
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

  <!-- 编辑活动备注的对话框 -->
  <el-dialog v-model="dialogVisible" title="编辑备注" center width="60%" draggable>
    <!-- 表单内容 -->
    <el-form ref="editRefForm" :model="editRemark" :rules="remarkRules" label-width="110px">
      <el-form-item label="活动备注" prop="noteContent">
        <el-input
            v-model="editRemark.noteContent"
            :rows="9"
            type="textarea"
            placeholder="请输入活动备注"
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editRemarkSubmit">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>


<script>
import {doDel, doGet, doPost, doPut} from "../http/httpRequest.js";
import {messageConfirm, messageTip} from "../util/util.js";
import {Delete, Edit} from "@element-plus/icons-vue";

export default {
  name: "ActivityDetailView",
  computed: {
    Delete() {
      return Delete
    },
    Edit() {
      return Edit
    }
  },
  inject: ['reload'],

  data() {
    return {
      activityDetail: {},// 活动详情对象
      remark: {}, // 活动备注对象
      remarkRules: {
        noteContent: [
          {required: true, message: '请输入活动备注', trigger: 'blur'},
          {min: 5, max: 255, message: '活动备注5-255个字', trigger: 'blur'}
        ]
      },
      activityRemarkList: [], // 活动备注列表对象
      pageNum: 1, // 当前备注页
      pageSize: 5, // 每页显示多少条备注
      total: 0, // 备注总数
      dialogVisible: false, // 编辑活动备注弹窗是否弹出
      editRemark: {}, // 编辑备注所绑定的对象
    }
  },
  mounted() {
    // 页面加载时查询活动详情
    let id = this.$route.params.id; // 获取路由参数 (活动id)
    this.loadActivityDetail(id);
    this.loadActivityRemarkList(id);
  },

  methods: {
    // 加载活动详情
    loadActivityDetail(id) {
      doGet(`/api/activity/${id}`, {}).then((resp) => {
        if (resp.data.code === 200) {
          this.activityDetail = resp.data.data;
        }
      })
      this.remark.activityId = id;
    },

    // 活动备注提交
    remarkSubmit() {
      this.$refs.remarkRefForm.validate((isValid) => {
        if (!isValid) {
          // 表单验证未通过，不能提交表单
          return
        }
        messageConfirm("是否确认提交活动备注").then(() => {
          doPost("api/activity/remark", this.remark).then((resp) => {
            //console.log(resp)
            if (resp.data.code === 200) {
              messageTip("提交成功", "success")
              this.reload();
            } else {
              messageTip("提交失败", "error")
            }
          })
        }).catch(() => {
          messageTip("取消提交", "warning")
        })
      })
    },

    // 加载活动备注列表
    loadActivityRemarkList(activityId){
      doGet(`/api/activity/remark`, {
        activityId: activityId,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then((resp) => {
        if (resp.data.code === 200) {
          console.log(resp.data.data)
          this.activityRemarkList = resp.data.data.list;
          this.total = resp.data.data.total;
        }
      })
    },

    // 分页函数,pageNum参数是element-plus组件传过来的
    toPage(pageNum) {
      this.pageNum = pageNum;
      this.loadActivityRemarkList(this.activityDetail.id)
    },

    // 修改活动备注
    edit(remark){
      this.dialogVisible = true;
      this.editRemark = {...remark}; // 创建新对象
    },

    // 提交修改活动备注
    editRemarkSubmit(){
      this.$refs.editRefForm.validate((isValid) => {
        if (!isValid) {
          // 表单验证未通过，不能提交表单
          // alert("表单验证未通过")
          return
        }
        messageConfirm("是否确认修改活动备注").then(() => {
          doPut("api/activity/remark", this.editRemark).then((resp) => {
            if (resp.data.code === 200) {
              messageTip("编辑成功", "success")
              this.reload();
            } else {
              messageTip("编辑失败", "error")
            }
          })
        }).catch(() => {
          messageTip("取消编辑", "warning")
          this.dialogVisible = false;
        })
      })
    },

    // 删除活动备注
    delRemark(id){
      messageConfirm("是否确认删除活动备注").then(() => {
        doDel(`api/activity/remark/${id}`).then((resp) => {
          if (resp.data.code === 200) {
            messageTip("删除成功", "success")
            this.reload();
          }else {
            messageTip("删除失败", "error")
          }
        })
      }).catch(() => {
        messageTip("取消删除", "warning")
      })
    }
  }
}
</script>

<style scoped>
.desc {
  width: 100%;
  background: lightgray;
  padding-left: 10px;
}
</style>
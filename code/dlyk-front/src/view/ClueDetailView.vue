<template>
  <!-- 展示线索详情 -->
  <el-form
      ref="clueRemarkRefForm"
      :model="clueRemark"
      label-width="110px"
      :rules="remarkRules"
      style="max-width: 95%;">

    <el-form-item label="负责人">
      <div class="desc">{{ clueDetail.ownerDO.name }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="所属活动">
      <div class="desc">{{ clueDetail.activityDO.name }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="姓名">
      <div class="desc">{{ clueDetail.fullName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="称呼">
      <div class="desc">{{ clueDetail.appellationDO.typeValue }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="手机">
      <div class="desc">{{ clueDetail.phone }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="微信">
      <div class="desc">{{ clueDetail.weixin }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="QQ">
      <div class="desc">{{ clueDetail.qq }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="邮箱">
      <div class="desc">{{ clueDetail.email }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="年龄">
      <div class="desc">{{ clueDetail.age }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="职业">
      <div class="desc">{{ clueDetail.job }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="年收入">
      <div class="desc">{{ clueDetail.yearIncome }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="住址">
      <div class="desc">{{ clueDetail.address }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="贷款">
      <div class="desc">{{ clueDetail.needLoanDO.typeValue }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="意向状态">
      <div class="desc">{{ clueDetail.intentionStateDO.typeValue }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="意向产品">
      <div class="desc">{{ clueDetail.intentionProductDO.name }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索状态">
      <div class="desc">{{ clueDetail.stateDO.typeValue }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索来源">
      <div class="desc">{{ clueDetail.sourceDO.typeValue }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索描述">
      <div class="desc">{{ clueDetail.description }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="下次联系时间">
      <div class="desc">{{ clueDetail.nextContactTime }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="填写跟踪记录" prop="noteContent">
      <el-input
          v-model="clueRemark.noteContent"
          :rows="8"
          type="textarea"/>
    </el-form-item>
    <el-form-item label="跟踪方式" prop="noteWay">
      <el-select
          v-model="clueRemark.noteWay"
          placeholder="请选择跟踪方式"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in noteWayOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="clueRemarkSubmit">提 交</el-button>
      <el-button type="success" @click="this.convertCustomerDialogVisible = true" v-if="clueDetail.state !== -1">转换客户</el-button>
      <el-button type="success" plain @click="() => this.$router.go(-1)">返 回</el-button>
    </el-form-item>
  </el-form>

  <!-- 线索跟踪记录 -->
  <el-table
      :data="clueRemarkList"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="60"/>
    <el-table-column prop="noteWayDO.typeValue" label="跟踪方式"/>
    <el-table-column prop="noteContent" label="跟踪内容"/>
    <el-table-column property="createTime" label="跟踪时间"/>
    <el-table-column property="createrName" label="跟踪人"/>
    <el-table-column property="editTime" label="编辑时间"/>
    <el-table-column property="editorName" label="编辑人"/>
    <el-table-column label="操作">
      <template #default="scope">
        <!--修改按钮-->
        <el-button type="primary" :icon="Edit" circle
                   @click="edit(scope.row)"/>
        <!--删除按钮-->
        <el-button type="danger" :icon="Delete" circle
                   @click="delRemark(scope.row.id)"/>
      </template>
    </el-table-column>
  </el-table>

  <!-- 分页条 -->
  <el-pagination
      v-if="total > 0"
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @prev-click="toPage"
      @next-click="toPage"
      @current-change="toPage"/>

  <!--线索转换为客户的弹窗（对话框）-->
  <el-dialog v-model="convertCustomerDialogVisible" title="线索转换客户" width="55%" center>
    <el-form ref="convertCustomerRefForm" :model="customerQuery" label-width="110px" :rules="convertCustomerRules">
      <el-form-item label="意向产品" prop="product">
        <el-select v-model="customerQuery.product" placeholder="请选择" style="width: 100%;"
                   @click="loadDicValue('product')">
          <el-option
              v-for="item in productOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="客户描述" prop="description">
        <el-input
            v-model="customerQuery.description"
            :rows="8"
            type="textarea"
            placeholder="请输入客户描述"/>
      </el-form-item>
      <el-form-item label="下次跟踪时间" prop="nextContactTime">
        <el-date-picker
            v-model="customerQuery.nextContactTime"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择下次跟踪时间"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="convertCustomerDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="convertCustomerSubmit">转 换</el-button>
      </span>
    </template>
  </el-dialog>

  <!-- 编辑线索跟踪记录的对话框 -->
  <el-dialog v-model="clueRemarkEditDialogVisible" title="编辑跟踪记录" center width="60%" draggable>
    <!-- 表单内容 -->
    <el-form ref="editRefForm" :model="editRemark" :rules="remarkRules" label-width="110px">
      <el-form-item label="跟踪记录" prop="noteContent">
        <el-input
            v-model="editRemark.noteContent"
            :rows="9"
            type="textarea"
            placeholder="请输入活动备注"
        />
      </el-form-item>

      <el-form-item label="跟踪方式" prop="noteWay">
        <el-select
            v-model="editRemark.noteWay"
            placeholder="请选择跟踪方式"
            style="width: 100%"
            clearable>
          <el-option
              v-for="item in noteWayOptions"
              :key="item.id"
              :label="item.typeValue"
              :value="item.id"/>
        </el-select>
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
  name: "ClueDetailView",
  computed: {
    Edit() {
      return Edit
    },
    Delete() {
      return Delete
    }
  },
  inject:['reload'],

  data() {
    return {
      //线索详情对象，初始值是空
      clueDetail: {
        ownerDO: {},
        activityDO: {},
        appellationDO: {},
        needLoanDO: {},
        intentionStateDO: {},
        intentionProductDO: {},
        stateDO: {},
        sourceDO: {}
      },
      //线索跟踪记录对象，初始值是空
      clueRemark: {},
      //跟踪方式的下拉选项，初始值是空
      noteWayOptions: [{}],
      //线索跟踪记录列表，初始值是空
      clueRemarkList: [{
        noteWayDO:{}
      }],
      pageNum: 1,
      //分页时每页显示多少条数据
      pageSize: 5,
      //分页总共查询出多少条数据
      total: 0,
      //定义转换客户的弹窗是否弹出来，默认是false不弹出来，true就弹出来
      convertCustomerDialogVisible: false,
      //线索转换为客户的form表单对象，初始值是空
      customerQuery: {},
      // 添加跟踪记录的验证规则
      remarkRules: {
        noteContent: [
          {required: true, message: '请输入跟踪备注', trigger: 'blur'},
          {min: 5, max: 255, message: '活动备注5-255个字', trigger: 'blur'}
        ],
        noteWay: [{required: true, message: '请选择跟踪方式', trigger: 'blur'},]
      },
      //定义线索转换为客户的验证规则
      convertCustomerRules: {
        product: [
          {required: true, message: '请选择意向产品', trigger: ['blur', 'change']}
        ],
        description: [
          {required: true, message: '客户描述不能为空', trigger: 'blur'},
          {min: 5, max: 255, message: '客户描述长度为5-255个字符', trigger: 'blur'}
        ],
        nextContactTime: [
          {required: true, message: '请选择下次联系时间', trigger: 'blur'}
        ]
      },
      //意向产品的下拉选项，初始值是空
      productOptions: [{}],
      editRemark: {}, // 编辑线索跟踪记录所绑定的对象
      clueRemarkEditDialogVisible: false, // 编辑线索跟踪记录的对话框是否弹出
    }
  },

  mounted() {
    let id = this.$route.params.id;
    this.loadClueDetail(id);
    this.loadDicValue('noteWay'); // 加载跟踪方式的下拉选项
    this.clueRemark.clueId = id;
    this.getClueRemarkList(id);
    this.loadProduct();
  },

  methods: {
    // 分页函数,pageNum参数是element-plus组件传过来的
    toPage(pageNum) {
      this.pageNum = pageNum;
      this.getClueRemarkList(this.clueDetail.id);
    },

    // 查询线索详情
    loadClueDetail(id){
      doGet(`/api/clue/${id}`,{}).then(resp => {
        if (resp.data.code === 200) {
          this.clueDetail = resp.data.data;
        }else {
          messageTip("查询失败--" + resp.data.msg, "error")
        }
      }).catch(() => {
        messageTip("系统繁忙，稍后重试", "error")
      })
    },

    // 提交线索跟踪记录
    clueRemarkSubmit(){
      this.$refs.clueRemarkRefForm.validate(valid => {
        if(!valid){
          // 验证未通过
          return;
        }
        console.log(this.clueRemark)
        doPost("api/clue/remark", this.clueRemark).then((resp) => {
          if (resp.data.code === 200){
            messageTip("提交成功", "success");
            this.reload();
          }else {
            messageTip("提交失败", "error");
          }
        })
      })
    },

    //加载字典数据
    loadDicValue(typeCode) {
      doGet(`/api/dicvalue/${typeCode}`, {}).then( resp => {
        if (resp.data.code === 200) {
          if (typeCode === 'noteWay') {
            this.noteWayOptions = resp.data.data;
          }
        }
      })
    },

    // 查询线索跟踪记录
    getClueRemarkList(id){
      doGet(`/api/clue/remark`, {
        clueId: id,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then((resp) => {
        if (resp.data.code === 200) {
          //console.log(resp.data.data)
          this.clueRemarkList = resp.data.data.list;
          this.total = resp.data.data.total;
        }
      })
    },

    // 修改线索跟踪备注
    edit(remark){
      this.clueRemarkEditDialogVisible = true;
      this.editRemark = {...remark}; // 创建新对象
    },

    // 提交修改线索跟踪备注
    editRemarkSubmit(){
      this.$refs.editRefForm.validate((isValid) => {
        if (!isValid) {
          // 表单验证未通过，不能提交表单
          // alert("表单验证未通过")
          return
        }
        messageConfirm("是否确认修改跟踪记录").then(() => {
          doPut("api/clue/remark", this.editRemark).then((resp) => {
            if (resp.data.code === 200) {
              messageTip("修改成功", "success")
              this.reload();
            } else {
              messageTip("修改失败", "error")
            }
          })
        }).catch(() => {
          messageTip("取消修改", "warning")
          this.clueRemarkEditDialogVisible = false;
        })
      })
    },

    // 删除线索跟踪备注
    delRemark(id){
      messageConfirm("是否确认删除活动备注").then(() => {
        doDel(`api/clue/remark/${id}`).then((resp) => {
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
    },

    // 加载产品下拉框
    loadProduct(){
      doGet(`/api/product`, {}).then( resp => {
        if (resp.data.code === 200) {
          this.productOptions = resp.data.data;
        }
      })
    },

    // 线索转换为客户
    convertCustomerSubmit(){
      this.$refs.convertCustomerRefForm.validate((isValid) => {
        if (!isValid) {
          // 表单验证未通过，不能提交表单
          // alert("表单验证未通过")
          return
        }
        messageConfirm("是否转换为客户").then(() => {
          this.customerQuery.clueId = this.clueDetail.id;
          doPost("api/customer/clueToCustomer", this.customerQuery).then((resp) => {
            if (resp.data.code === 200) {
              messageTip("转换成功", "success")
              this.reload();
            } else {
              messageTip("转换失败", "error")
            }
          })
        }).catch(() => {
          messageTip("取消转换", "warning")
          this.convertCustomerDialogVisible = false;
        })
      })
    }
  }
}
</script>


<style scoped>
.desc {
  background-color: #F0FFFF;
  width: 100%;
  padding-left: 15px;
}
</style>
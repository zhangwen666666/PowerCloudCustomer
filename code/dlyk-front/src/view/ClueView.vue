<template>
  <el-button type="primary" round @click="addClue">录入线索</el-button>
  <el-button type="success" round @click="importClue">导入线索(Excel)</el-button>
  <el-button type="danger" round @click="batchDelete">批量删除</el-button>

  <!-- 展示数据的列表 -->
  <el-table
      :data="clueList"
      :highlight-current-row="true"
      :table-layout="'auto'"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column property="ownerDO.name" label="负责人" width="120" />
    <el-table-column property="activityDO.name" label="所属活动"/>
    <el-table-column label="姓名">
      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.fullName }}</a>
      </template>
    </el-table-column>
    <el-table-column property="appellationDO.typeValue" label="称呼"/>
    <el-table-column property="phone" label="手机" width="120"/>
    <el-table-column property="weixin" label="微信" width="120"/>
    <el-table-column property="needLoanDO.typeValue" label="是否贷款"/>
    <el-table-column property="intentionStateDO.typeValue" label="意向状态"/>
    <el-table-column property="intentionProductDO.name" label="意向产品"/>
    <el-table-column label="线索状态">
      <template #default="scope">
        <span style="background: lightgoldenrodyellow" v-if="scope.row.state === -1"> {{ scope.row.stateDO.typeValue }} </span>
        <span v-else> {{ scope.row.stateDO.typeValue }} </span>
      </template>
    </el-table-column>
    <el-table-column property="sourceDO.typeValue" label="线索来源"/>
    <el-table-column property="nextContactTime" label="下次联系时间" width="165"/>
    <el-table-column label="操作" width="230">
      <template #default="scope">
        <!--修改按钮-->
        <el-button :icon="Search" circle @click="view(scope.row.id)"/><!--查看详情按钮-->
        <el-button type="primary" :icon="Edit" circle
                   @click="() => this.$router.push(`/dashboard/clue/edit/${scope.row.id}`)"
                   v-has-permission="'clue:edit'"/><!--修改按钮-->
        <el-button type="danger" :icon="Delete" circle @click="del(scope.row.id)" v-has-permission="'clue:delete'"/><!--删除按钮-->
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

  <!-- 导入线索Excel的弹窗 -->
  <el-dialog v-model="dialogVisible" title="导入线索Excel" center width="60%" draggable>
    <el-upload
        ref="uploadRef"
        :http-request="uploadFile"
        :auto-upload="false">
      <template #trigger>
        <el-button type="primary">选择Excel文件</el-button>
      </template>
      仅支持后缀名为.xls或者.xlsx的文件

      <template #tip>
        <div class="fileTip">
          重要提示：
          <ul>
            <li class="tip">上传仅支持后缀名为.xls或.xlsx的文件；</li>
            <li class="tip">给定Excel文件的第一行将视为字段名；</li>
            <li class="tip">请确认您的文件大小不超过50MB；</li>
            <li class="tip">日期值以文本形式保存，必须符合yyyy-MM-dd格式；</li>
            <li class="tip">日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式；</li>
          </ul>
        </div>
      </template>
    </el-upload>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="submitExcel">
          导入
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>


<script>
import {Delete, Edit, Search} from "@element-plus/icons-vue";
import {doDel, doGet, doPost} from "../http/httpRequest.js";
import {messageConfirm, messageTip} from "../util/util.js";

export default {
  name: "ClueView",
  inject: ['reload'],
  computed: {
    Search() {
      return Search
    },
    Edit() {
      return Edit
    },
    Delete() {
      return Delete
    }
  },

  data(){
    return {
      clueList:[{
        ownerDO: {},
        activityDO: {},
        appellationDO: {},
        needLoadDO: {},
        intentionStateDO: {},
        stateDO: {},
        sourceDO: {},
      }], // 线索数据列表
      pageNum:1,  // 当前页数
      pageSize:15, // 每页展示的记录条数
      total:0, // 总记录条数
      selectionIds: [], // 多选时所选中的线索id集合
      dialogVisible: false, // 控制弹窗的显示与隐藏
    }
  },

  mounted(){
    this.getClueList(this.pageNum, this.pageSize)
  },

  methods: {
    // 分页函数,pageNum参数是element-plus组件传过来的
    toPage(pageNum) {
      this.pageNum = pageNum;
      this.getClueList(this.pageNum, this.pageSize)
    },

    // 点击多选按钮时触发该回调函数
    handleSelectionChange(selections){
      this.selectionIds = []
      selections.forEach((item) => {
        this.selectionIds.push(item.id)
      })
    },

    // 获取线索列表数据
    getClueList(pageNum, pageSize){
      doGet("/api/clue/list", {
        pageNum: pageNum,
        pageSize: pageSize,
      }).then(resp => {
        if (resp.data.code === 200) {
          this.clueList = resp.data.data.list;
          this.total = resp.data.data.total;
        }else {
          // this.$message.error("系统繁忙，请稍后重试")
          messageTip(resp.data.msg, "error")
        }
      })
    },

    addClue(){
      this.$router.push('/dashboard/clue/add')
    },

    // 线索导入(Excel)
    importClue(){
      // 弹窗
      this.dialogVisible = true
    },

    // 上传文件
    uploadFile(params){
      // console.log(params)
      let file = params.file; // 获取文件
      let formData = new FormData;
      formData.append("file", file);
      doPost("api/importExcel", formData).then(resp => {
        if (resp.data.code === 200) {
          // 导入成功
          messageTip("导入成功", "success")
          this.$refs.uploadRef.clearFiles();
          // 页面刷新
          this.reload();
        }else {
          messageTip("导入失败", "error")
        }
      })
    },

    // 提交Excel文件
    submitExcel(){
      this.$refs.uploadRef.submit(); // 真正上传时会调用uploadFile方法上传
    },

    // 删除线索
    del(id){
      messageConfirm("是否确认删除线索").then(() => {
        doDel(`/api/clue/${id}`, {}).then(resp => {
          if (resp.data.code === 200) {
            messageTip("删除成功", "success")
            this.reload();
          }else {
            messageTip("删除失败," + resp.data.msg, "error")
          }
        })
      }).catch(() => {
        messageTip("取消删除", "warning")
      })
    },

    // 批量删除线索
    batchDelete(){
      messageConfirm("是否确认删除线索").then(() => {
        doDel("/api/clue/batchDelete", {
          ids: this.selectionIds.join(",")
        }).then(resp => {
          if (resp.data.code === 200) {
            messageTip("删除成功", "success")
            this.reload();
          }else {
            messageTip("删除失败," + resp.data.msg, "error")
          }
        })
      }).catch(() => {
        messageTip("取消删除", "warning")
      })
    },

    // 查看线索详情
    view(id){
      this.$router.push(`/dashboard/clue/detail/${id}`);
    },
  }
}
</script>

<style scoped>
  .el-table{
    margin-top: 20px;
  }
  .fileTip{
    padding-top: 25px;
  }
  .tip{
    margin-top: 10px;
  }
</style>
<template>
  <el-form ref="addRefForm" :model="clue" :rules="clueRules" label-width="100px">
    <el-form-item label="负责人">
      <el-select
          v-model="clue.ownerId"
          placeholder="请选择负责人"
          style="width: 100%"
          clearable
          disabled>
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="所属活动">
      <el-select
          v-model="clue.activityId"
          placeholder="请选择所属活动"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in activityOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="姓名" prop="fullName">
      <el-input v-model="clue.fullName"/>
    </el-form-item>

    <el-form-item label="称呼">
      <el-select
          v-model="clue.appellation"
          placeholder="请选择称呼"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in appellationOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="手机" v-if="clue.id > 0"><!--此时是编辑-->
      <el-input v-model="clue.phone" disabled/>
    </el-form-item>

    <el-form-item label="手机" prop="phone" v-else><!--此时是录入-->
      <el-input v-model="clue.phone"/>
    </el-form-item>

    <el-form-item label="微信">
      <el-input v-model="clue.weixin"/>
    </el-form-item>

    <el-form-item label="QQ" prop="qq">
      <el-input v-model="clue.qq"/>
    </el-form-item>

    <el-form-item label="邮箱" prop="email">
      <el-input v-model="clue.email"/>
    </el-form-item>

    <el-form-item label="年龄" prop="age">
      <el-input v-model="clue.age"/>
    </el-form-item>

    <el-form-item label="职业">
      <el-input v-model="clue.job"/>
    </el-form-item>

    <el-form-item label="年收入" prop="yearIncome">
      <el-input v-model="clue.yearIncome"/>
    </el-form-item>

    <el-form-item label="住址">
      <el-input v-model="clue.address"/>
    </el-form-item>

    <el-form-item label="贷款">
      <el-select
          v-model="clue.needLoan"
          placeholder="请选择是否需要贷款"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in needLoanOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向状态">
      <el-select
          v-model="clue.intentionState"
          placeholder="请选择意向状态"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in intentionStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向产品">
      <el-select
          v-model="clue.intentionProduct"
          placeholder="请选择意向产品"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in productOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索状态">
      <el-select
          v-model="clue.state"
          placeholder="请选择线索状态"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in clueStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索来源">
      <el-select
          v-model="clue.source"
          placeholder="请选择线索来源"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in sourceOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索描述" prop="description">
      <el-input
          v-model="clue.description"
          :rows="5"
          type="textarea"
          placeholder="请输入线索描述"/>
    </el-form-item>

    <el-form-item label="下次联系时间">
      <el-date-picker
          v-model="clue.nextContactTime"
          type="datetime"
          style="width: 100%;"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择下次联系时间"/>
    </el-form-item>

    <el-form-item class="buttonBottom">
      <el-button type="primary" @click="submitData">提 交</el-button>
      <el-button type="primary" plain @click="() => this.$router.go(-1)">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "ClueAddView",
  data() {
    return {
      clue: {}, // 线索对象
      ownerOptions: [], // 负责人下拉框选项
      activityOptions: [], // 活动下拉选项
      appellationOptions: [], // 称呼下拉选项
      needLoanOptions: [], // 是否需要贷款下拉选项
      intentionStateOptions: [], // 意向状态下拉选项
      productOptions: [], // 产品下拉选项
      clueStateOptions: [], // 线索状态下拉选项
      sourceOptions: [], // 线索来源下拉选项
      //录入线索验证规则
      clueRules : {
        phone : [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { pattern : /^1[3-9]\d{9}$/, message: '手机号码格式有误', trigger: 'blur'},
          { validator : this.checkPhone, trigger: 'blur'}
        ],
        fullName : [
          { min: 2, message: '姓名至少2个汉字', trigger: 'blur'},
          { pattern : /^[\u4e00-\u9fa5]{0,}$/, message: '姓名必须为中文汉字', trigger: 'blur'},
        ],
        qq : [
          { min: 5, message: 'QQ号至少为5位', trigger: 'blur'},
          { pattern : /^\d+$/, message: 'QQ号码必须为数字', trigger: 'blur'},
        ],
        email: [
          { pattern : /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: '邮箱格式有误', trigger: 'blur'},
        ],
        age: [
          { pattern : /^\d+$/, message: '年龄必须为数字', trigger: 'blur'},
        ],
        yearIncome: [
          { pattern : /^[0-9]+(\.[0-9]{2})?$/, message: '年收入必须是整数或者两位小数', trigger: 'blur'}
        ],
        description: [
          { min: 5, max: 255, message: '线索描述长度为5-255个字符', trigger: 'blur'},
        ],
      },
    }
  },

  mounted() {
    this.loadDicValue('appellation');
    this.loadDicValue('needLoan');
    this.loadDicValue('intentionState');
    this.loadDicValue('clueState');
    this.loadDicValue('source');
    this.loadDicValue('activity');
    this.loadDicValue('product');
    // this.loadOwner();
    // this.loadLoginUser();
  },

  methods: {
    //加载字典数据
    loadDicValue(typeCode) {
      doGet(`/api/dicvalue/${typeCode}`, {}).then( resp => {
        if (resp.data.code === 200) {
          if (typeCode === 'appellation') {
            this.appellationOptions = resp.data.data;
          } else if (typeCode === 'needLoan') {
            this.needLoanOptions = resp.data.data;
          } else if (typeCode === 'intentionState') {
            this.intentionStateOptions = resp.data.data;
          } else if (typeCode === 'clueState') {
            this.clueStateOptions = resp.data.data;
          } else if (typeCode === 'source') {
            this.sourceOptions = resp.data.data;
          } else if (typeCode === 'activity') {
            this.activityOptions = resp.data.data;
          } else if (typeCode === 'product') {
            this.productOptions = resp.data.data;
          }
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
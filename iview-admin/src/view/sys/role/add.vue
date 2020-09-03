<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formDicRole"
      :model="formDicRole"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="角色名称" prop="ROLE_NAME">
        <Input type="text" v-model="formDicRole.ROLE_NAME" placeholder="角色名称"/>
      </FormItem>
      <FormItem label="注释" prop="REMARK">
        <Input type="text" v-model="formDicRole.REMARK" placeholder="注释"/>
      </FormItem>
      <FormItem label="角色类型" prop="TYPE">
        <Select v-model="formDicRole.TYPE">
          <Option value="1">角色</Option>
          <Option value="2">部门</Option>
          <Option value="3">单位</Option>
          <Option value="4">其它</Option>
        </Select>
      </FormItem>
      <FormItem label="是否私有" prop="IS_PRIVATE">
        <Select v-model="formDicRole.IS_PRIVATE">
          <Option value="1">私有</Option>
          <Option value="2">公开</Option>
        </Select>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formDicRole')">提交</Button>
        <Button @click="handleReset('formDicRole')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
import { save } from '@/api/role'
import { createModelObj } from '@/libs/util'

export default {
  name: 'Add',
  props: {
    value: {
      type: Boolean,
      default: false
    },
    parentId: {
      type: String,
      default: '0'
    }
  },
  data () {
    return {
      formDicRole: {
        
        ROLE_NAME: ''
        ,
        PARENT_ID: '0'
        ,
        REMARK: ''
        ,
        TYPE: '1'
        ,
        IS_PRIVATE: '2'
      },
      ruleValidate: {
        
        ROLE_NAME: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 50, message: '长度最长为50', trigger: 'blur' }
        ]
        ,
        REMARK: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 100, message: '长度最长为100', trigger: 'blur' }
        ]
        ,
        TYPE: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 2, message: '长度最长为2', trigger: 'blur' }
        ],
        
        PARENT_ID: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 50, message: '长度最长为50', trigger: 'blur' }
        ]
        ,
        IS_PRIVATE: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 2, message: '长度最长为2', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          save(Object.assign({}, createModelObj(this.formDicRole, 'dicRole'))).then(
            res => {
              if (res) {
                this.$emit('addRow', res.row)
                this.$emit('input', false)
                this.handleReset(name)
              }
            }
          )
        } else {
          this.$Message.error('验证失败!')
        }
      })
    },
    handleReset (name) {
      this.$refs[name].resetFields()
    }
  },
  watch: {
    parentId: function (val) {
      this.formDicRole.PARENT_ID = val
    }
  }
}
</script>
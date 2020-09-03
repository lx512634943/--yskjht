<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formInline"
      :model="formInline"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="用户类型" prop="TYPE">
        <Select v-model="formInline.TYPE">
          <Option value="1">普通用户</Option>
          <Option value="2">管理员</Option>
        </Select>
      </FormItem>
      <FormItem label="登录名称" prop="USER_CODE">
        <Input type="text" v-model="formInline.USER_CODE" placeholder="登录名称"/>
      </FormItem>
      <FormItem label="用户名" prop="USER_NAME">
        <Input type="text" v-model="formInline.USER_NAME" placeholder="用户名"/>
      </FormItem>
      <FormItem label="备注" prop="REMARK">
        <Input type="textarea" v-model="formInline.REMARK" placeholder="备注"/>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formInline')">提交</Button>
        <Button @click="handleReset('formInline')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
import { update } from '@/api/user'
import { createModelObj, coverProps, coverFormProps } from '@/libs/util'

export default {
  name: 'Edit',
  props: {
    value: {
      type: Boolean,
      default: false
    },
    user: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      formInline: {
        ID: '',
        TYPE: '',
        USER_CODE: '',
        USER_NAME: '',
        REMARK: '',
        IS_DISABLED: ''
      },
      ruleValidate: {
        TYPE: [{ required: true, message: '该值不能为空', trigger: 'change' }],
        USER_CODE: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', min: 3, message: '长度至少为3', trigger: 'blur' }
        ],
        PASSWD: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', min: 6, message: '长度至少为6', trigger: 'blur' }
        ],
        USER_NAME: [
          { required: true, message: '该值不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          update(
            Object.assign({}, createModelObj(this.formInline, 'user'))
          ).then(res => {
            if (res) {
              this.$emit('updateRow', coverProps(this.user, this.formInline))
              this.$emit('input', false)
              this.handleReset(name)
            }
          })
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
    value: function (val, oldVal) {
      this.$refs.formInline.resetFields()
      coverFormProps(this.formInline, this.user)
    }
  }
}
</script>

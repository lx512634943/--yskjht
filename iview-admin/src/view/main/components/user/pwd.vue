<template>
  <Modal
    title="修改密码"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formPassword"
      :model="formPassword"
      :rules="RuleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="旧密码" prop="oldPassword">
        <Input type="password" v-model="formPassword.oldPassword" placeholder="密码"/>
      </FormItem>
      <FormItem label="新密码" prop="newPassword">
        <Input type="password" v-model="formPassword.newPassword" placeholder="密码"/>
      </FormItem>
      <FormItem label="确认密码" prop="renewPassword">
        <Input type="password" v-model="formPassword.renewPassword" placeholder="密码"/>
      </FormItem>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formPassword')">提交</Button>
        <Button @click="handleReset('formPassword')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
import { save, updatePwd } from '@/api/user'

export default {
  name: 'UpdatePwd',
  props: {
    value: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      formPassword: {
        oldPassword: '',
        newPassword: '',
        renewPassword: ''
      },
      RuleValidate: {
        oldPassword: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', min: 6, message: '长度至少为6', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', min: 6, message: '长度至少为6', trigger: 'blur' }
        ],
        renewPassword: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', min: 6, message: '长度至少为6', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        console.log(this.formPassword);
        if (valid) {
          if (this.formPassword.newPassword !== this.formPassword.renewPassword) {
            this.$Message.error('两次输入密码不一致!') 
            return
          }
          updatePwd(Object.assign({}, this.formPassword)).then(
            res => {
              if (res) {
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
  }
}
</script>

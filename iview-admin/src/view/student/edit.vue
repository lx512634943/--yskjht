<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formDicStudent"
      :model="formDicStudent"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >


      <FormItem label="编号" :label-width="100" prop="id">
        <Input type="text" v-model="formDicStudent.id" placeholder="编号"/>
      </FormItem>
      <FormItem label="姓名" :label-width="100" prop="name">
        <Input type="text" v-model="formDicStudent.name" placeholder="姓名"/>
      </FormItem>

      <FormItem label="地址" :label-width="100" prop="address">
        <Input type="text" v-model="formDicStudent.address" placeholder="地址"/>
      </FormItem>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formDicStudent')">提交</Button>
        <Button @click="handleReset('formDicStudent')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { update } from '@/api/student'
  import { createModelObj, coverProps, coverFormProps } from '@/libs/util'

  export default {
    name: 'Edit',
    props: {
      value: {
        type: Boolean,
        default: false
      },
      dicStudent: {
        type: Object,
        default: null
      }
    },
    data () {
      return {
        formDicStudent: {
            id:'',
          name:'',
          address:''
        },
        ruleValidate: {
          id: [
            { required: true, trigger: 'blur' },
            { type: 'string',  max: 200, message: '长度最长为200', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '该值不能为空', trigger: 'blur' },
            { type: 'string', max: 200, message: '长度最长为200', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '该值不能为空', trigger: 'blur' },
            { type: 'string',  max: 200, message: '长度最长为200', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            update(
              Object.assign({}, createModelObj(this.formDicStudent, 'dicStudent'))
            ).then(res => {
              if (res) {
                this.$emit('updateRow', coverProps(this.dicStudent, this.formDicStudent))
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
        this.$refs.formDicStudent.resetFields()
        coverFormProps(this.formDicStudent, this.dicStudent)
      }
    }
  }
</script>

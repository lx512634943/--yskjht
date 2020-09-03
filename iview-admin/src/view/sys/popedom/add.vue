<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formDicPopedom"
      :model="formDicPopedom"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="名称" prop="POPEDOM_NAME">
        <Input type="text" v-model="formDicPopedom.POPEDOM_NAME" placeholder="名称"/>
      </FormItem>
      <FormItem label="路由" prop="FORWARD_ACTION">
        <Input type="text" v-model="formDicPopedom.FORWARD_ACTION" placeholder="路由"/>
      </FormItem>
      <FormItem label="路由名称" prop="NAME">
        <Input type="text" v-model="formDicPopedom.NAME" placeholder="路由名称"/>
      </FormItem>
      <FormItem label="组件路径" prop="COMPONENT">
        <Input type="text" v-model="formDicPopedom.COMPONENT" placeholder="组件路径"/>
      </FormItem>
      <FormItem label="图标路径" prop="NAV_ICON">
        <Input type="text" v-model="formDicPopedom.NAV_ICON" placeholder="图标路径"/>
      </FormItem>
      <FormItem label="类型" prop="TYPE">
        <Select v-model="formDicPopedom.TYPE">
          <Option value="1">菜单</Option>
          <Option value="2">权限url</Option>
          <Option value="3">其它</Option>
        </Select>
      </FormItem>
      <FormItem label="备注" prop="REMARK">
        <Input type="textarea" v-model="formDicPopedom.REMARK" placeholder="备注"/>
      </FormItem>
      <FormItem label="重定向" prop="REDIRECT">
        <Input type="text" v-model="formDicPopedom.REDIRECT" placeholder="重定向"/>
      </FormItem>
      <FormItem label="超链接" prop="HREF">
        <Input type="text" v-model="formDicPopedom.HREF" placeholder="超链接"/>
      </FormItem>
      <FormItem label="是否隐藏" prop="IS_HIDE">
        <Select v-model="formDicPopedom.IS_HIDE">
          <Option value="1">是</Option>
          <Option value="2">否</Option>
        </Select>
      </FormItem>
      <FormItem label="是否不缓存" prop="IS_NOT_CACHE">
        <Select v-model="formDicPopedom.IS_NOT_CACHE">
          <Option value="1">是</Option>
          <Option value="2">否</Option>
        </Select>
      </FormItem>
      <FormItem label="是否审计" prop="IS_LOG">
        <Select v-model="formDicPopedom.IS_LOG">
          <Option value="1">是</Option>
          <Option value="2">否</Option>
        </Select>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formDicPopedom')">提交</Button>
        <Button @click="handleReset('formDicPopedom')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
import { save } from '@/api/popedom'
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
      formDicPopedom: {

        POPEDOM_NAME: '',

        FORWARD_ACTION: '',

        REMARK: '',

        REDIRECT: '',

        NAV_ICON: '',

        TYPE: '1',
        
        PARENT_ID: '0',

        IS_LOG: '2',

        NAME: '',

        COMPONENT: '',

        HREF: '',

        IS_HIDE: '2',

        IS_NOT_CACHE: '2'
      },
      ruleValidate: {

        POPEDOM_NAME: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 50, message: '长度最长为50', trigger: 'blur' }
        ],

        FORWARD_ACTION: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 100, message: '长度最长为100', trigger: 'blur' }
        ],

        REMARK: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 500, message: '长度最长为500', trigger: 'blur' }
        ],

        REDIRECT: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 100, message: '长度最长为100', trigger: 'blur' }
        ],

        NAV_ICON: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 300, message: '长度最长为300', trigger: 'blur' }
        ],

        TYPE: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 2, message: '长度最长为2', trigger: 'blur' }
        ],

        PARENT_ID: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 50, message: '长度最长为50', trigger: 'blur' }
        ],

        IS_LOG: [
          { required: true, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 2, message: '长度最长为2', trigger: 'blur' }
        ],

        NAME: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 50, message: '长度最长为50', trigger: 'blur' }
        ],

        COMPONENT: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 255, message: '长度最长为255', trigger: 'blur' }
        ],

        HREF: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 255, message: '长度最长为255', trigger: 'blur' }
        ],

        IS_HIDE: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 2, message: '长度最长为2', trigger: 'blur' }
        ],

        IS_NOT_CACHE: [
          { required: false, message: '该值不能为空', trigger: 'blur' },
          { type: 'string', max: 2, message: '长度最长为2', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          save(Object.assign({}, createModelObj(this.formDicPopedom, 'dicPopedom'))).then(
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
      this.formDicPopedom.PARENT_ID = val
    }
  }
}
</script>

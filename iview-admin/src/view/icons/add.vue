<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formIcons"
      :model="formIcons"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="招聘职位名称" :label-width="100" prop="icname">
        <Input type="text" v-model="formIcons.icname" placeholder="伙伴名称"/>
      </FormItem>


      <FormItem label="招聘职位图" prop="icon" :label-width="100">
        <div class="demo-upload-list" v-if="formIcons.icon">
          <template v-if="formIcons.icon">
            <img :src="getImageUrl(formIcons.icon)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formIcons.icon)" ></Icon>
              <Icon type="ios-trash-outline" @click.native="handleRemove()"></Icon>
            </div>
          </template>
        </div>
        <Upload
          ref="upload"
          :show-upload-list="false"
          :on-success="handleSuccess"
          :format="['jpg','jpeg','png']"
          :max-size="2048"
          :on-format-error="handleFormatError"
          :on-exceeded-size="handleMaxSize"
          :before-upload="handleBeforeUpload"
          multiple
          type="drag"
          :action="baseURL"
          style="display: inline-block;width:58px;" v-if="!this.formIcons.icon">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formIcons.icon">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>


      <FormItem>
        <Button type="primary" @click="handleSubmit('formIcons')">提交</Button>
        <Button @click="handleReset('formIcons')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save ,delImage} from '@/api/icons'
 import { createModelObj } from '@/libs/util'

  export default {
    name: 'Add',
    props: {
      value: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {
        imageUrl:'',
        formIcons: {
          icname:'',
          icon:''
        },
        ruleValidate: {
        }
      }
    },
    computed: {
      baseURL () {
        return this.$config.urlPath + 'admin/partners/upload'
      }
    },
    methods: {
      handleFormatError (file) {
        this.spinShow = false
        this.$Notice.warning({
          title: file.name + ' 上传错误',
          desc: '请选择 jpg 或 png.'
        })
      },
      handleMaxSize (file) {
        this.spinShow = false
        this.$Notice.warning({
          title: file.name + ' 上传错误',
          desc: '图片上传最大为 2M.'
        })
      },
      handleBeforeUpload () {
        this.spinShow = true
        return true
      },
      handleSuccess (res, file) {
        this.spinShow = false
        this.formIcons.icon = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formIcons.icon
        delImage({'url': url}).then(res => {

          this.spinShow = false
          this.formIcons.icon = ''
        })
      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            save(Object.assign({}, createModelObj(this.formIcons, 'icons'))).then(
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
    }
  }
</script>

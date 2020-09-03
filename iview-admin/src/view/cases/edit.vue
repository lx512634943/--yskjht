<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formProjectCases"
      :model="formProjectCases"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="伙伴名称" :label-width="100" prop="name">
        <Input type="text" v-model="formProjectCases.name" placeholder="伙伴名称"/>
      </FormItem>

      <FormItem label="合作伙伴封面" prop="picture" :label-width="100">
        <div class="demo-upload-list" v-if="formProjectCases.picture">
          <template v-if="formProjectCases.picture">
            <img :src="getImageUrl(formProjectCases.picture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formProjectCases.picture)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formProjectCases.picture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formProjectCases.picture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>

      <FormItem label="合作伙伴封面" prop="pictures" :label-width="100" >
        <div class="demo-upload-list" v-if="formProjectCases.pictures">
          <template v-if="formProjectCases.pictures">
            <img :src="getImageUrl(formProjectCases.pictures)"  style="background-color: green">
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView1(formProjectCases.pictures)" ></Icon>
              <Icon type="ios-trash-outline" @click.native="handleRemove1()"></Icon>
            </div>
          </template>
        </div>
        <Upload
          ref="upload"
          :show-upload-list="false"
          :on-success="handleSuccess1"
          :format="['jpg','jpeg','png']"
          :max-size="2048"
          :on-format-error="handleFormatError"
          :on-exceeded-size="handleMaxSize"
          :before-upload="handleBeforeUpload"
          multiple
          type="drag"
          :action="baseURL"
          style="display: inline-block;width:58px;" v-if="!this.formProjectCases.pictures">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formProjectCases.pictures">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible1">
        <img :src=" getImageUrl(imageUrl)" v-if="visible1" style="width: 100%;height:200px; background-color: green" >
      </Modal>

      <FormItem label="领用详情" :label-width="100" prop="title">
        <Input type="textarea" v-model="formProjectCases.title" placeholder="领用详情"  style="width:200px"/>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formProjectCases')">提交</Button>
        <Button @click="handleReset('formProjectCases')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { update,delImage } from '@/api/cases'
  import { createModelObj, coverProps, coverFormProps } from '@/libs/util'

  export default {
    name: 'Edit',
    props: {
      value: {
        type: Boolean,
        default: false
      },
      projectCases: {
        type: Object,
        default: null
      }
    },
    data () {
      return {
        visible: false,
        visible1: false,
        imageUrl:'',
        formProjectCases: {
          picture:'',
          name:'',
          pictures:'',
          id:'',
          title:''

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
      richTextChange(res){
        this.formProjectCases.introduce = res;
      },
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
        this.formProjectCases.picture = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formProjectCases.picture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formProjectCases.picture = ''
        })
      },


      handleSuccess1 (res, file) {
        this.spinShow = false
        this.formProjectCases.pictures = res.filePath
      },
      handleView1 (imgUrl) {
        this.imageUrl = imgUrl
        this.visible1 = true
      },
      handleRemove1 () {
        this.spinShow = true
        let url = this.formProjectCases.pictures
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formProjectCases.pictures = ''
        })
      },


      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            update(
              Object.assign({}, createModelObj(this.formProjectCases, 'projectCases'))
            ).then(res => {
              if (res) {
                this.$emit('updateRow', coverProps(this.projectCases, this.formProjectCases))
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
        this.$refs.formProjectCases.resetFields()
        coverFormProps(this.formProjectCases, this.projectCases)
      }
    }
  }
</script>

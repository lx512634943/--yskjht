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
      <FormItem label="项目名称" :label-width="100" prop="name">
        <Input type="text" v-model="formProjectCases.name" placeholder="伙伴名称"/>
      </FormItem>

      <FormItem label="项目案例图" prop="picture" :label-width="100">
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

      <FormItem label="项目浮动图" prop="pictures" :label-width="100" >
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

      <FormItem label="项目详情图" prop="pictures" :label-width="100" >
        <div class="demo-upload-list" v-if="formProjectCases.casepicture">
          <template v-if="formProjectCases.casepicture">
            <img :src="getImageUrl(formProjectCases.casepicture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView2(formProjectCases.casepicture)" ></Icon>
              <Icon type="ios-trash-outline" @click.native="handleRemove2()"></Icon>
            </div>
          </template>
        </div>
        <Upload
          ref="upload"
          :show-upload-list="false"
          :on-success="handleSuccess2"
          :format="['jpg','jpeg','png']"
          :max-size="2048"
          :on-format-error="handleFormatError"
          :on-exceeded-size="handleMaxSize"
          :before-upload="handleBeforeUpload"
          multiple
          type="drag"
          :action="baseURL"
          style="display: inline-block;width:58px;" v-if="!this.formProjectCases.casepicture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formProjectCases.casepicture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible2">
        <img :src=" getImageUrl(imageUrl)" v-if="visible2" style="width: 100%;height:200px; background-color: green" >
      </Modal>


      <FormItem label="项目详情" :label-width="100" prop="title">
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
        visible2: false,
        visible: false,
        visible1: false,
        imageUrl:'',
        formProjectCases: {
          picture:'',
          name:'',
          pictures:'',
          id:'',
          title:'',
          casepicture:''

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

      handleSuccess2 (res, file) {
        this.spinShow = false
        this.formProjectCases.casepicture = res.filePath
      },
      handleView2 (imgUrl) {
        this.imageUrl = imgUrl
        this.visible2 = true
      },
      handleRemove2 () {
        this.spinShow = true
        let url = this.formProjectCases.casepicture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formProjectCases.casepicture = ''
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
<style>
  .demo-upload-list{
    display: inline-block;
    width: 60px;
    height: 60px;
    text-align: center;
    line-height: 60px;
    border: 1px solid transparent;
    border-radius: 4px;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
    margin-right: 4px;
  }
  .demo-upload-list img{
    width: 100%;
    height: 100%;
  }
  .demo-upload-list-cover{
    display: none;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0,0,0,.6);
  }
  .demo-upload-list:hover .demo-upload-list-cover{
    display: block;
  }
  .demo-upload-list-cover i{
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
  }
</style>

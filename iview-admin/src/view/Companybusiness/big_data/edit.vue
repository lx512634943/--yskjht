<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formBigData"
      :model="formBigData"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="业务名称" :label-width="100" prop="business_id" >
        <Select v-model="formBigData.business_id" style="width:200px" disabled>
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>
      <FormItem label="信息化案例名字" :label-width="100" prop="name">
        <Input type="text" v-model="formBigData.name" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="业务详情" :label-width="100" prop="title">
        <Input type="textarea" v-model="formBigData.title" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="业务详情1" :label-width="100" prop="title1">
        <Input type="textarea" v-model="formBigData.title1" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="业务详情2" :label-width="100" prop="title2">
        <Input type="textarea" v-model="formBigData.title2" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="大健康案例图片" prop="picture" :label-width="100">
        <div class="demo-upload-list" v-if="formBigData.picture">
          <template v-if="formBigData.picture">
            <img :src="getImageUrl(formBigData.picture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formBigData.picture)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formBigData.picture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formBigData.picture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formBigData')">提交</Button>
        <Button @click="handleReset('formBigData')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { update } from '@/api/data'
  import { delImage } from '@/api/cases'
  import { list1 } from '@/api/businessbyid'
  import { createModelObj, coverProps, coverFormProps } from '@/libs/util'

  export default {
    name: 'Edit',
    props: {
      value: {
        type: Boolean,
        default: false
      },
      bigData: {
        type: Object,
        default: null
      }
    },
    data () {
      return {
        visible: false,
        imageUrl:'',
        cityList:[],
        formBigData: {
          id:'',
          name:'',
          title:'',
          title1:'',
          title2:'',
          picture:''
        },
        ruleValidate: {
        }
      }
    },
    computed: {
      baseURL () {
        return this.$config.urlPath + 'admin/data/upload'
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
        this.formBigData.picture = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formBigData.picture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formBigData.picture = ''
        })

      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            update(
              Object.assign({}, createModelObj(this.formBigData, 'bigData'))
            ).then(res => {
              if (res) {
                this.$emit('updateRow', coverProps(this.bigData, this.formBigData))
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
      },
      initData () {

        list1().then(
          res => {
            this.cityList = res

          }
        )
      }
    },
    watch: {
      value: function (val, oldVal) {
        this.$refs.formBigData.resetFields()
        coverFormProps(this.formBigData, this.bigData)
        this.formBigData.business_id=this.bigData.business_id
        this.initData()
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

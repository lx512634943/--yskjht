<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formSlideshow"
      :model="formSlideshow"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="伙伴介绍" :label-width="100" prop="introduce">
        <Input type="text" v-model="formSlideshow.sign" placeholder="伙伴介绍"/>
      </FormItem>
      <FormItem label="合作伙伴封面" prop="photos" :label-width="100">
        <div class="demo-upload-list" v-if="formSlideshow.photos">
          <template v-if="formSlideshow.photos">
            <img :src="getImageUrl(formSlideshow.photos)">
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formSlideshow.photos)"></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formSlideshow.photos">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formSlideshow.photos">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:600px;">
      </Modal>

      <FormItem label="合作伙伴类型" :label-width="100" prop="pkid">
        <Select v-model="formSlideshow.nid" style="width:200px" >
          <Option v-for="item in cityList" :value="item.id" :key="item.id" >{{ item.title }}</Option>
        </Select>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formSlideshow')">提交</Button>
        <Button @click="handleReset('formSlideshow')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save,list1 } from '@/api/slideshow'
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
        visible: false,
        imageUrl: '',
        cityList:[],
        formSlideshow: {
          sign:'',
          photos:'' ,
          nid:''
        },
        ruleValidate: {
        }
      }
    },
  computed: {
    baseURL () {
      return this.$config.urlPath + 'admin/slideshow/upload'
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
        this.formSlideshow.photos = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formSlideshow.photos
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formSlideshow.photos = ''
        })
      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            save(Object.assign({}, createModelObj(this.formSlideshow, 'slideshow'))).then(
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
        this.initData()
      }
    }
  }
</script>

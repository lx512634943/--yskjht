<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formBusinessbyid"
      :model="formBusinessbyid"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >

      <FormItem label="请选择" :label-width="100" prop="pkid" >
        <Select v-model="formBusinessbyid.businessId" style="width:200px" disabled>
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>
      <FormItem label="合作伙伴封面" prop="picture" :label-width="100">
        <div class="demo-upload-list" v-if="formBusinessbyid.picture">
          <template v-if="formBusinessbyid.picture">
            <img :src="getImageUrl(formBusinessbyid.picture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formBusinessbyid.picture)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formBusinessbyid.picture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formBusinessbyid.picture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formBusinessbyid')">提交</Button>
        <Button @click="handleReset('formBusinessbyid')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { update,delImage,list1 } from '@/api/businessbyid'
  import { createModelObj, coverProps, coverFormProps } from '@/libs/util'

  export default {
    name: 'Edit',
    props: {
      value: {
        type: Boolean,
        default: false
      },
      businessbyid: {
        type: Object,
        default: null
      }
    },
    data () {
      return {
        visible: false,
        imageUrl:'',
        cityList:[],
        formBusinessbyid: {
          id:'',
          picture:'',
          businessId:''
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
        this.formBusinessbyid.picture = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formBusinessbyid.picture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formBusinessbyid.picture = ''
        })

      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            update(
              Object.assign({}, createModelObj(this.formBusinessbyid, 'businessbyid'))
            ).then(res => {
              if (res) {
                this.$emit('updateRow', coverProps(this.businessbyid, this.formBusinessbyid))
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
        this.$refs.formBusinessbyid.resetFields()
        coverFormProps(this.formBusinessbyid, this.businessbyid)
        console.log(this.businessbyid)
        this.formBusinessbyid.businessId=this.businessbyid.businessId
        this.initData()
      }

    }
  }
</script>

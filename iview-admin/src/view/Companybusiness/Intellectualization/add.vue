<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formIntellectualization"
      :model="formIntellectualization"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="业务名称" :label-width="100" prop="business_id" >
        <Select v-model="formIntellectualization.business_id" style="width:200px" >
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>
      <FormItem label="信息化案例名字" :label-width="100" prop="name">
        <Input type="text" v-model="formIntellectualization.name" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="智能化业务详情" :label-width="100" prop="title">
        <Input type="textarea" v-model="formIntellectualization.title" placeholder="新闻标题"/>
      </FormItem>

      <FormItem label="大健康案例图片" prop="picture" :label-width="100">
        <div class="demo-upload-list" v-if="formIntellectualization.picture">
          <template v-if="formIntellectualization.picture">
            <img :src="getImageUrl(formIntellectualization.picture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formIntellectualization.picture)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formIntellectualization.picture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formIntellectualization.picture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formIntellectualization')">提交</Button>
        <Button @click="handleReset('formIntellectualization')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save } from '@/api/intellectualization'
  import { delImage } from '@/api/cases'
  import { list1 } from '@/api/businessbyid'
  import { createModelObj,coverFormProps } from '@/libs/util'

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
        imageUrl:'',
        cityList:[],
        formIntellectualization: {
        },
        ruleValidate: {
          id:'',
          name:'',
          title:'',
          picture:'',
          business_id:''
        }
      }
    },
    computed: {
      baseURL () {
        return this.$config.urlPath + 'admin/intellectualization/upload'
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
        this.formIntellectualization.picture = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formIntellectualization.picture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formIntellectualization.picture = ''
        })

      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            save(Object.assign({}, createModelObj(this.formIntellectualization, 'intellectualization'))).then(
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
            console.log(res)
          }
        )
      }
    },
    watch: {
      value: function (val, oldVal) {
        this.initData()

        coverFormProps(this.formIntellectualization, this.intellectualization)

      }
    }
  }
</script>

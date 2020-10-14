<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formPromotion"
      :model="formPromotion"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="业务名称" :label-width="100" prop="business_id" >
        <Select v-model="formPromotion.business_id" style="width:200px" >
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>

      <FormItem label="大健康案例名字" :label-width="100" prop="name">
        <Input type="text" v-model="formPromotion.name" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="详情案例1" :label-width="100" prop="byid_title">
        <Input type="textarea" v-model="formPromotion.title" placeholder="新闻标题"/>
      </FormItem>

      <FormItem label="详情案例2" :label-width="100" prop="byid_titles">
        <Input type="textarea" v-model="formPromotion.titles" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="大健康案例图片" prop="picture" :label-width="100">
        <div class="demo-upload-list" v-if="formPromotion.picture">
          <template v-if="formPromotion.picture">
            <img :src="getImageUrl(formPromotion.picture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formPromotion.picture)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formPromotion.picture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formPromotion.picture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formPromotion')">提交</Button>
        <Button @click="handleReset('formPromotion')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save } from '@/api/promotion'
  import { createModelObj,coverFormProps } from '@/libs/util'
  import { delImage } from '@/api/cases'
  import { list1 } from '@/api/businessbyid'
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
        formPromotion: {
          id:'',
          name:'',
          picture:'',
          title:'',
          titles:'',
          business_id:''
        },
        ruleValidate: {
        }
      }
    },
    computed: {
      baseURL () {
        return this.$config.urlPath + 'admin/promotion/upload'
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
        this.formPromotion.picture = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formPromotion.picture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formPromotion.picture = ''
        })

      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            save(Object.assign({}, createModelObj(this.formPromotion, 'promotion'))).then(
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

        coverFormProps(this.formPromotion, this.promotion)

      }
    }

  }
</script>

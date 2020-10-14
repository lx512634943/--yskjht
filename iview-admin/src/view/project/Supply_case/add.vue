<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formSupplyChainfinance"
      :model="formSupplyChainfinance"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="环保服务名称" :label-width="100" prop="name">
        <Input type="text" v-model="formSupplyChainfinance.name" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="环保服务详情" :label-width="100" prop="title">
        <Input type="textarea" v-model="formSupplyChainfinance.title" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="大健康案例图片" prop="picture" :label-width="100">
        <div class="demo-upload-list" v-if="formSupplyChainfinance.picture">
          <template v-if="formSupplyChainfinance.picture">
            <img :src="getImageUrl(formSupplyChainfinance.picture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formSupplyChainfinance.picture)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formSupplyChainfinance.picture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formSupplyChainfinance.picture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formSupplyChainfinance')">提交</Button>
        <Button @click="handleReset('formSupplyChainfinance')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save } from '@/api/chainfinance'
  import { delImage } from '@/api/cases'
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
        formSupplyChainfinance: {
          id:'',
          picture:'',
          name:'',
          title:''
        },
        ruleValidate: {
        }
      }
    },
    computed: {
      baseURL () {
        return this.$config.urlPath + 'admin/chainfinance/upload'
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
        this.formSupplyChainfinance.picture = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formSupplyChainfinance.picture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formSupplyChainfinance.picture = ''
        })

      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            save(Object.assign({}, createModelObj(this.formSupplyChainfinance, 'supplyChainfinance'))).then(
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
      value: function (val, oldVal) {
        coverFormProps(this.formSupplyChainfinance, this.supplyChainfinance)
      }
    }
  }
</script>

<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formSupplyChain"
      :model="formSupplyChain"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="业务名称" :label-width="100" prop="business_id" >
        <Select v-model="formSupplyChain.business_id" style="width:200px" disabled>
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>

      <FormItem label="信息化案例名字" :label-width="100" prop="name">
        <Input type="text" v-model="formSupplyChain.name" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="业务详情" :label-width="100" prop="title">
        <Input type="textarea" v-model="formSupplyChain.title" placeholder="新闻标题"/>
      </FormItem>
      <FormItem label="业务详情2" :label-width="100" prop="title1">
        <Input type="textarea" v-model="formSupplyChain.title1" placeholder="新闻标题"/>
      </FormItem>

      <FormItem label="大健康案例图片" prop="picture" :label-width="100">
        <div class="demo-upload-list" v-if="formSupplyChain.picture">
          <template v-if="formSupplyChain.picture">
            <img :src="getImageUrl(formSupplyChain.picture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formSupplyChain.picture)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formSupplyChain.picture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formSupplyChain.picture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>
      <Button type="primary" @click="handleSubmit('formSupplyChain')">提交</Button>
      <Button @click="handleReset('formSupplyChain')" style="margin-left: 8px">重置</Button>

    </Form>
  </Modal>
</template>
<script>
  import { update } from '@/api/chain'
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
      supplyChain: {
        type: Object,
        default: null
      }
    },
    data () {
      return {
        visible: false,
        imageUrl:'',
        cityList:[],
        formSupplyChain: {
          id:'',
          name:'',
          title:'',
          title1:'',
          picture:''
        },
        ruleValidate: {

        }
      }
    },
    computed: {
      baseURL () {
        return this.$config.urlPath + 'admin/chain/upload'
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
        this.formSupplyChain.picture = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formSupplyChain.picture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formSupplyChain.picture = ''
        })

      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            update(
              Object.assign({}, createModelObj(this.formSupplyChain, 'supplyChain'))
            ).then(res => {
              if (res) {
                this.$emit('updateRow', coverProps(this.supplyChain, this.formSupplyChain))
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
        this.$refs.formSupplyChain.resetFields()
        coverFormProps(this.formSupplyChain, this.supplyChain)
        this.formSupplyChain.business_id=this.supplyChain.business_id
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

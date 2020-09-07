<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formBusiness"
      :model="formBusiness"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="项目名称" :label-width="100" prop="name">
        <Input type="text" v-model="formBusiness.name" placeholder="伙伴名称"/>
      </FormItem>

      <FormItem label="业务详情" :label-width="100" prop="title">
        <Input type="text" v-model="formBusiness.title" placeholder="业务详情2"/>
      </FormItem>
      <FormItem label="业务详情2" :label-width="100" prop="titles">
        <Input type="text" v-model="formBusiness.titles" placeholder="业务详情2"/>
      </FormItem>
      <FormItem label="悬浮业务详情" :label-width="100" prop="businessBytitle">
        <Input type="textarea" v-model="formBusiness.businessBytitle" placeholder="领用详情"  style="width:200px"/>
      </FormItem>

      <FormItem label="项目案例图" prop="picture" :label-width="100">
        <div class="demo-upload-list" v-if="formBusiness.picture">
          <template v-if="formBusiness.picture">
            <img :src="getImageUrl(formBusiness.picture)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formBusiness.picture)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formBusiness.picture">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formBusiness.picture">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>

      <FormItem label="项目具体详情" :label-width="100" prop="businesstitle">
        <rich-text style="margin-bottom: 5%" :value="formBusiness.businesstitle"  @on-change="richTextChange($event)"></rich-text>
      </FormItem>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formBusiness')">提交</Button>
        <Button @click="handleReset('formBusiness')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { update ,delImage } from '@/api/business'
  import { createModelObj, coverProps, coverFormProps } from '@/libs/util'
  import richText from "../richText/richText";
  export default {
    name: 'Edit',
    components: {richText},
    props: {
      value: {
        type: Boolean,
        default: false
      },
      business: {
        type: Object,
        default: null
      }
    },
    data () {
      return {
        visible: false,
        imageUrl:'',
        formBusiness: {
          id:'',
          name:'',
          title:'',
          titles:'',
          businessBytitle:'',
          picture:'',
          businesstitle:''
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
        this.formBusiness.businesstitle = res;
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
        this.formBusiness.picture = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formBusiness.picture
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formBusiness.picture = ''
        })
      },

      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            update(
              Object.assign({}, createModelObj(this.formBusiness, 'business'))
            ).then(res => {
              if (res) {
                this.$emit('updateRow', coverProps(this.business, this.formBusiness))
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
        this.$refs.formBusiness.resetFields()
        coverFormProps(this.formBusiness, this.business)
      }
    }
  }
</script>

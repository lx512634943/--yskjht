<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formPartners"
      :model="formPartners"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="伙伴名称" :label-width="100" prop="pname">
        <Input type="text" v-model="formPartners.pname" placeholder="伙伴名称"/>
      </FormItem>
      <FormItem label="伙伴介绍" :label-width="100" prop="introduce">
        <Input type="text" v-model="formPartners.introduce" placeholder="伙伴介绍"/>
      </FormItem>

      <FormItem label="合作伙伴封面" prop="cover" :label-width="100">
        <div class="demo-upload-list" v-if="formPartners.cover">
          <template v-if="formPartners.cover">
            <img :src="getImageUrl(formPartners.cover)">
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formPartners.cover)"></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formPartners.cover">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formPartners.cover">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:600px;">
      </Modal>
      <FormItem label="伙伴分类ID" :label-width="100" prop="pkid ">
        <Input type="text" v-model="formPartners.pkid " placeholder="伙伴分类ID"/>
      </FormItem>
      <FormItem label="商品库存" :label-width="100" prop="banner ">
        <Input type="text" v-model="formPartners.banner " placeholder="商品库存"/>
      </FormItem>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formPartners')">提交</Button>
        <Button @click="handleReset('formPartners')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save } from '@/api/partners'
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
        formPartners: {
          pname:'',
          introduce:'',
          cover:'',
          createdate:'',
          pkid:'',
          banner:''
        },
        ruleValidate: {
        }
      }
    },
    computed: {
      baseURL () {
        return this.$config.urlPath + 'admin/introduce/upload'
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
        this.formPartners.cover = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formPartners.cover
        delImage({'url': url}).then(res => {
          this.spinShow = false
          this.formPartners.cover = ''
        })
      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            save(Object.assign({}, createModelObj(this.formPartners, 'partners'))).then(
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
    }
  }
</script>

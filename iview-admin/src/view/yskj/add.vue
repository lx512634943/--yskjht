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
      <FormItem label="合作伙伴轮播图" prop="pictures" :label-width="100">
        <div class="demo-upload-list" v-if="formPartners.pictures">
          <template v-if="formPartners.pictures">
            <img :src="getImageUrl(formPartners.pictures)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView1(formPartners.pictures)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formPartners.pictures">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formPartners.pictures">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>

      <Modal title="查看图片" v-model="visible1">
        <img :src=" getImageUrl(imageUrl)" v-if="visible1" style="width: 100%;height:200px;">
      </Modal>

      <FormItem label="合作伙伴类型" :label-width="100" prop="pkid">
        <Select v-model="formPartners.pkid" style="width:200px" >
          <Option v-for="item in cityList" :value="item.id" :key="item.id" >{{ item.kinds }}</Option>
        </Select>
      </FormItem>

      <FormItem label="伙伴介绍" :label-width="100" prop="introduce">
        <rich-text style="margin-bottom: 5%" :value="formPartners.introduce"  @on-change="richTextChange($event)"></rich-text>
      </FormItem>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formPartners')">提交</Button>
        <Button @click="handleReset('formPartners')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save,delImage,list1 } from '@/api/partners'

  import { createModelObj, coverProps, coverFormProps } from '@/libs/util'
  import richText from "../richText/richText";

  export default {
    name: 'Add',
    components: {richText},
    props: {
      value: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {
        cityList:[],
        ids:'',
        visible1: false,
        visible: false,
        imageUrl: '',

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
        return this.$config.urlPath + 'admin/partners/upload'
      }
    },
    methods: {
      richTextChange(res){
        this.formPartners.introduce = res;
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


      handleSuccess1 (res, file) {
        this.spinShow = false
        this.formPartners.pictures = res.filePath
      },
      handleView1 (imgUrl) {
        this.imageUrl = imgUrl
        this.visible1 = true
      },
      handleRemove1() {
        this.spinShow = true
        let url = this.formPartners.pictures
        delImage({'url': url}).then(res => {

          this.spinShow = false
          this.formPartners.pictures = ''
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
                  console.log()
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
        coverFormProps(this.formPartners, this.partners)
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

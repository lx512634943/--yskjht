<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formNewsindex"
      :model="formNewsindex"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="新闻标题" :label-width="100" prop="newsid">
        <i-col span="12" style="padding-right:10px">
          <i-select v-model="formNewsindex.newsid" filterable  style="width:200px">
            <i-option v-for="item in cityList" :value="item.id":key="item.id"  >{{ item.title }}</i-option>
          </i-select>
        </i-col>
      </FormItem>

      <FormItem label="新闻封面" prop="pictures" :label-width="100">
        <div class="demo-upload-list" v-if="formNewsindex.pictures">
          <template v-if="formNewsindex.pictures">
            <img :src="getImageUrl(formNewsindex.pictures)" style="width: 58px;height: 58px">
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formNewsindex.pictures)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formNewsindex.pictures">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formNewsindex.pictures">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>
      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formNewsindex')">提交</Button>
        <Button @click="handleReset('formNewsindex')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save,list1,delImage } from '@/api/newsindex'

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
        imageUrl: '',
        cityList:[],
        formNewsindex: {
          id:"",
          pictures:"",
          newsid:""
        },
        ruleValidate: {
        }
      }
    },
    computed: {
      baseURL() {
        return this.$config.urlPath + 'admin/newsindex/upload'
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
        this.formNewsindex.pictures = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formNewsindex.pictures
        delImage({'url': url}).then(res => {

          this.spinShow = false
          this.formNewsindex.pictures= ''
        })
      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            save(Object.assign({}, createModelObj(this.formNewsindex, 'newsindex'))).then(
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

        coverFormProps(this.formNewsindex, this.newsindex)

        this.initData();
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

<template>
  <Modal
    title="添加"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formNews"
      :model="formNews"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >
      <FormItem label="新闻标题" :label-width="100" prop="title">
        <Input type="text" v-model="formNews.title" placeholder="新闻标题"/>
      </FormItem>

      <FormItem label="新闻概要" :label-width="100" prop="outline">
        <Input type="text" v-model="formNews.outline" placeholder="新闻概要"/>
      </FormItem>

      <FormItem label="新闻封面" prop="newcover" :label-width="100">
        <div class="demo-upload-list" v-if="formNews.newcover">
          <template v-if="formNews.newcover">
            <img :src="getImageUrl(formNews.newcover)" >
            <div class="demo-upload-list-cover">
              <Icon type="ios-eye-outline" @click.native="handleView(formNews.newcover)" ></Icon>
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
          style="display: inline-block;width:58px;" v-if="!this.formNews.newcover">
          <div style="width: 58px;height:57px;line-height: 58px;" v-if="!this.formNews.newcover">
            <Icon type="ios-camera" size="20"></Icon>
          </div>
        </Upload>
      </FormItem>

      <Modal title="查看图片" v-model="visible">
        <img :src=" getImageUrl(imageUrl)" v-if="visible" style="width: 100%;height:200px;">
      </Modal>
      <FormItem label="请选择" :label-width="100" prop="nkid">
        <Select v-model="formNews.nkid" style="width:200px">
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.kinds }}</Option>
        </Select>
      </FormItem>
      <FormItem label="内容" :label-width="100" prop="content">
        <rich-text style="margin-bottom: 5%" :value="formNews.content"  @on-change="richTextChange($event)"></rich-text>
      </FormItem>
      <FormItem>
        <Button type="primary" @click="handleSubmit('formNews')">提交</Button>
        <Button @click="handleReset('formNews')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { save,list1,delImage } from '@/api/news'
  import { createModelObj,coverFormProps } from '@/libs/util'
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
        visible: false,
        imageUrl: '',
        cityList:[],
        formNews: {
          id:'',
          title:'',
          outline:'',
          newcover:'',
          createdate:'',
          nkid:'',
          content:'',
        },
        ruleValidate: {
        }
      }

    },
    computed: {
      baseURL() {
        return this.$config.urlPath + 'admin/news/upload'
      }
    },
    methods: {
      richTextChange(res){
        this.formNews.content = res;
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
        this.formNews.newcover = res.filePath
      },
      handleView (imgUrl) {
        this.imageUrl = imgUrl
        this.visible = true
      },
      handleRemove () {
        this.spinShow = true
        let url = this.formNews.newcover
        delImage({'url': url}).then(res => {

          this.spinShow = false
          this.formNews.newcover = ''
        })
      },
      getImageUrl (url) {
        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            save(Object.assign({}, createModelObj(this.formNews, 'news'))).then(
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
        coverFormProps(this.formNews, this.news)
        this.initData()
      }
    }
  }
</script>

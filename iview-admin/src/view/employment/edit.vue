<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formEmployment"
      :model="formEmployment"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >

      <FormItem label="请选择" :label-width="100" prop="iconid">
        <Select v-model="formEmployment.iconid" style="width:200px">
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.icname }}</Option>
        </Select>
      </FormItem>
      <FormItem label="招聘人数" :label-width="100" prop="pnumber">
        <InputNumber :min="1"  type="number" v-model="formEmployment.pnumber" placeholder="招聘人数"    style="width:100%"/>
      </FormItem>

      <FormItem label="招聘详情" :label-width="100" prop="skill">
        <rich-text style="margin-bottom: 5%" :value="formEmployment.skill"  @on-change="richTextChange($event)"></rich-text>
      </FormItem>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formEmployment')">提交</Button>
        <Button @click="handleReset('formEmployment')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { update,list1} from '@/api/employment'
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
      employment: {
        type: Object,
        default: null
      }
    },
    data () {
      return {
        cityList:[],
        formEmployment: {
          iconid:'',
          id:'',
          pnumber:'',
          skill:'',

        },
        ruleValidate: {
        }
      }
    },
    methods: {
      richTextChange(res){
        this.formEmployment.skill = res;
      },
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            update(
              Object.assign({}, createModelObj(this.formEmployment, 'employment'))
            ).then(res => {
              if (res) {
                this.$emit('updateRow', coverProps(this.employment, this.formEmployment))
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
        this.$refs.formEmployment.resetFields()
        coverFormProps(this.formEmployment, this.employment)
        console.log(this.employment)
        this.formEmployment.iconid=this.employment.ids
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

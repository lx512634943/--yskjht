<template >
  <div class="index" style="background-image: url(src/imgage/1.jpg)">
    <Form
      ref="formShangping"
      :model="formShangping"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
      style="margin-left: 700px;margin-top:100px; "
    >
      <h1 style="margin-left: 120px;margin-bottom: 50px">商品领用</h1>
      <FormItem label="商品用途" :label-width="100" prop="citylistname">
        <Select v-model="cityList.id" style="width:200px">
          <Option v-for="item in cityList" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
      </FormItem>

      <FormItem label="商品名称" :label-width="100" prop="sname">
        <i-col span="12" style="padding-right:10px">
          <i-select v-model="formShangping.sname" filterable  style="width:200px">
            <i-option v-for="item in cityList1" :value="item.commodity_name":key="item.id"  >{{ item.commodity_name }}</i-option>
          </i-select>
        </i-col>
      </FormItem>

      <FormItem label="商品数量" :label-width="100" prop="number">
        <InputNumber :min="1"  type="number" v-model="formShangping.number" placeholder="商品数量"    style="width:200px"/>
      </FormItem>
      <FormItem label="领取人姓名" :label-width="100" prop="uname">
        <Input type="text" v-model="formShangping.uname" placeholder="领取人姓名"  style="width:200px"/>
      </FormItem>
      <FormItem label="领用详情" :label-width="100" prop="beizhu">
        <Input type="textarea" v-model="formShangping.beizhu" placeholder="领用详情"  style="width:200px"/>
      </FormItem>
      <FormItem >
        <Button type="primary" @click="handleSubmit('formShangping')">提交</Button>
        <Button @click="handleReset('formShangping')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </div>
</template>
<script>

  import { save,list1 } from '@/api/shangping'
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
        model11: '',
        cityList:[
          {
            id:1,
            name:"日常办公"

          },
          {
            id:2,
            name:"茶水区"

          }
          ,
          {
            id:3,
            name:"仓库贵重酒水区"

          }
        ],
        cityList1:[

        ],
        columns:[
          { type: 'selection', width: 60, align: 'center' },
          list1().then(
            res => {
              this.cityList1 = res
              console.log(res)
            }
          ),

        ],

        formShangping: {
          number:'',
          uname:"",
          beizhu:'',
          sname:"",

        },
        ruleValidate: {
          uname: [
            { required: true, message: '该值不能为空', trigger: 'blur' },
            { type: 'string',  max: 200, message: '长度最长为200', trigger: 'blur' }
          ],


        }
      }
    },
    methods: {
      handleSubmit (name) {
        console.log(this.formShangping.sname)
        console.log(this.formShangping.number)
        if (this.formShangping.sname == "" || this.formShangping.number == ""  ){
          this.$Message.warning('请输入完整的信息');
        }else {
          this.$refs[name].validate(valid => {

            if (valid) {
              save(Object.assign({}, createModelObj(this.formShangping, 'shangping'))).then(
                res => {
                  if (res) {
                    console.log(res)
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
        }

      },
      handleReset (name) {
        this.$refs[name].resetFields()
      },


    },


  }

</script>

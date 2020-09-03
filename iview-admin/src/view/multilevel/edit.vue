<template>
  <Modal
    title="编辑"
    :value="value"
    :mask-closable="true"
    :footer-hide="true"
    @input="$emit('input', $event)"
  >
    <Form
      ref="formMerchandiseNew"
      :model="formMerchandiseNew"
      :rules="ruleValidate"
      label-position="right"
      :label-width="80"
    >

      <FormItem label="编号" :label-width="100" prop="id">
        <Input type="text" v-model="formMerchandiseNew.id" placeholder="编号"/>
      </FormItem>
      <FormItem label="商品名称" :label-width="100" prop="commodity_name">
        <Input type="text" v-model="formMerchandiseNew.commodity_name" placeholder="商品名称"/>
      </FormItem>

      <FormItem label="商品柜号" :label-width="100" prop="container_number">
        <Input type="text" v-model="formMerchandiseNew.container_number" placeholder="商品柜号"/>
      </FormItem>
      <FormItem label="单位" :label-width="100" prop="unit">
        <Input type="text" v-model="formMerchandiseNew.unit" placeholder="单位"/>
      </FormItem>
      <FormItem label="商品库存" :label-width="100" prop="inventory ">
        <Input type="text" v-model="formMerchandiseNew.inventory " placeholder="商品库存"/>
      </FormItem>
      <FormItem label="购入" :label-width="100" prop="purchase">
        <Input type="text" v-model="formMerchandiseNew.purchase" placeholder="购入"/>
      </FormItem>
      <FormItem label="总金额" :label-width="100" prop="money">
        <Input type="text" v-model="formMerchandiseNew.money" placeholder="总金额"/>
      </FormItem>
      <FormItem label="领取数量" :label-width="100" prop="get">
      <Input type="text" v-model="formMerchandiseNew.get" placeholder="领取数量"/>
    </FormItem>
      <FormItem label="领取人" :label-width="100" prop="get_peopel">
      <Input type="text" v-model="formMerchandiseNew.get_peopel" placeholder="领取人"/>
    </FormItem>
      <FormItem label="领取时间" :label-width="100" prop="get_time">
      <Input type="text" v-model="formMerchandiseNew.get_time" placeholder="领取时间"/>
    </FormItem>
      <FormItem label="剩余" :label-width="100" prop="residue">
      <Input type="text" v-model="formMerchandiseNew.residue" placeholder="剩余"/>
    </FormItem>
      <FormItem label="备注" :label-width="100" prop="remark">
        <Input type="text" v-model="formMerchandiseNew.remark" placeholder="备注"/>
      </FormItem>

      <FormItem>
        <Button type="primary" @click="handleSubmit('formMerchandiseNew')">提交</Button>
        <Button @click="handleReset('formMerchandiseNew')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
  </Modal>
</template>
<script>
  import { update } from '@/api/new'
  import { createModelObj, coverProps, coverFormProps } from '@/libs/util'

  export default {
    name: 'Edit',
    props: {
      value: {
        type: Boolean,
        default: false
      },
      merchandiseNew: {
        type: Object,
        default: null
      }
    },
    data () {
      return {
        formMerchandiseNew: {
        },
        ruleValidate: {
        }
      }
    },
    methods: {
      handleSubmit (name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            update(
              Object.assign({}, createModelObj(this.formMerchandiseNew, 'merchandiseNew'))
            ).then(res => {
              if (res) {
                this.$emit('updateRow', coverProps(this.merchandiseNew, this.formMerchandiseNew))
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
        this.$refs.formMerchandiseNew.resetFields()
        coverFormProps(this.formMerchandiseNew, this.merchandiseNew)
      }
    }
  }
</script>

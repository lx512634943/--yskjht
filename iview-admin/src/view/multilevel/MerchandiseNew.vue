<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>查询
          <div slot="content">
            <Form
              ref="formMerchandiseNew"
              :model="formMerchandiseNew"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem label="商品名称" :label-width="100" prop="commodity_name">
                <Input type="text" v-model="formMerchandiseNew.commodity_name" placeholder="商品名称"/>
              </FormItem>

              <FormItem>
                <Button type="primary" @click="handleSubmit('formMerchandiseNew')">查询</Button>
                <Button @click="handleReset('formMerchandiseNew')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <ButtonGroup style="margin-bottom: 15px;">
        <Button @click="handleOpenPage('add')">添加</Button>
        <Add v-model="isOpenAddPage" @addRow="addMerchandiseNew"/>
        <Edit v-model="isOpenEditPage" @updateRow="updateMerchandiseNew" :merchandiseNew="editMerchandiseNew"/>
        <Button @click="batchDelete()">删除</Button>
      </ButtonGroup>
      <Table
        ref="tableMerchandiseNew"
        :data="tableData"
        :columns="columns"
        @on-selection-change="onSelectionChange"
        @on-sort-change="onSortChange"
      />
      <div style="margin: 10px;overflow: hidden">
        <div style="float: right;">
          <Page
            :total="total"
            :current="current"
            :page-size="pageSize"
            @on-page-size-change="changePageSize"
            @on-change="changePage"
            :show-total="true"
            show-sizer
          ></Page>
        </div>
      </div>
      <Button style="margin: 10px 0;" type="primary" @click="exportExcel">导出为Csv文件</Button>
    </Card>
  </div>
</template>

<script>
  import { list, del, batchDelete} from '@/api/new'
  import { createFormObj } from '@/libs/util'
  import Add from './add.vue'
  import Edit from './edit.vue'

  export default {
    name: 'MerchandiseNew',
    components: {
      Add,
      Edit
    },
    data () {
      return {
        formMerchandiseNew: {
          commodity_name:'',
        },
        columns: [
          { type: 'selection', width: 60, align: 'center' },
          { key: 'id', title: '编号', sortable: false},
          { key: 'commodity_name', title: '商品名称', sortable: false},
          { key: 'container_number', title: '商品柜号', sortable: false},
          { key: 'inventory', title: '商品总库存', sortable: false},
          { key: 'unit', title: '单位', sortable: false},
          { key: 'purchase', title: '购入', sortable: false},
          { key: 'money', title: '总金额', sortable: false},
          { key: 'residue', title: '商品剩余库存', sortable: false},
          { key: 'remark', title: '备注', sortable: false},
          { key: 'merchandise', title: '备注', sortable: false},
          {
            title: '操作',
            key: 'action',
            width: 250,
            align: 'center',
            render: (h, params) => {
              return h('div', [

                h(
                  'Button',
                  {
                    props: {
                      type: 'primary',
                      size: 'small'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.editMerchandiseNew = params.row
                        this.handleOpenPage('edit')
                      }
                    }
                  },
                  '编辑'
                ),
                h(
                  'Button',
                  {
                    props: {
                      type: 'error',
                      size: 'small'
                    },
                    on: {
                      click:()=>{
                        this.$Modal.confirm({
                          title: '提示',
                          content: '<p>确定删除吗?</p>',
                          onOk: () => {
                            del({ 'merchandiseNew.id': params.row.id}).then(res => {
                              this.getMerchandiseNewList(this.current, this.pageSize)
                            })
                          },
                          onCancel: () => {}
                        });
                      }
                    }
                  },
                  '删除'
                )
              ])
            }
          }
        ],
        tableData: [],
        total: 0,
        current: 1,
        pageSize: 10,
        isOpenAddPage: false,
        isOpenEditPage: false,
        editMerchandiseNew: {},
        selection: []
      }
    },
    methods: {
      handleSubmit (name) {
        // 查询按钮事件
        this.getMerchandiseNewList(this.current, this.pageSize)
        this.$Message.success('查询成功!')
      },
      handleReset (name) {
        // 重置按钮事件
        this.$refs[name].resetFields()
      },
      handleOpenPage (name) {
        // 打开页面事件(添加页,编辑页...)
        if (name === 'add') this.isOpenAddPage = true
        else if (name === 'edit') {
          this.isOpenEditPage = true
        }
      },
      addMerchandiseNew (row) {
        this.tableData.push(row)
      },
      updateMerchandiseNew (row) {
        this.$set(this.tableData, row._index, row)
      },
      batchDelete () {
        this.$Modal.confirm({
          title: '提示',
          content: '<p>确定删除吗?</p>',
          onOk: () => {
            if (this.selection.length > 0) {
              let ids = ''
              for (var i = 0; i < this.selection.length; i++) {
                ids += ',' + this.selection[i].ID
              }
              batchDelete({ ids: ids }).then(res => {
                for (var i = 0; i < this.selection.length; i++) {
                  this.tableData = this.tableData.filter(
                    item => item.ID !== this.selection[i].ID
                  )
                }
              })
            } else {
              this.$Message.error('未选中记录!')
            }
          },
          onCancel: () => {

          }
        });
      },
      getMerchandiseNewList (current, pageSize, paras) {
        let para = { pageNumber: current, pageSize: pageSize }
        if (paras) Object.assign(para, paras)

        list(this.handleRequestParams(para)).then(res => {
          this.tableData = res.list
          this.current = parseInt(res.pageNumber)
          this.pageSize = parseInt(res.pageSize)
          this.total = parseInt(res.totalRow)
        })
      },
      handleRequestParams (para) {
        Object.assign(para, createFormObj(this.formMerchandiseNew, 'merchandiseNew'))
        return para
      },
      exportExcel () {
        this.$refs.tableMerchandiseNew.exportCsv({
          filename: `table-${new Date().valueOf()}.csv`
        })
      },
      changePage (pageNumber) {
        this.getMerchandiseNewList(pageNumber, this.pageSize)
      },
      changePageSize (pageSize) {
        this.getMerchandiseNewList(this.current, pageSize)
      },
      remove (index) {
        this.data6.splice(index, 1)
      },
      onSelectionChange (selection) {
        this.selection = selection
      },
      onSortChange (obj) {
        if (obj.order === 'normal') {
          this.getMerchandiseNewList(1, this.pageSize)
        } else {
          this.getMerchandiseNewList(1, this.pageSize, {
            field: obj.key,
            order: obj.order
          })
        }
      }
    },
    mounted () {
      this.getMerchandiseNewList(this.current, this.pageSize)
    }
  }
</script>

<style>
</style>

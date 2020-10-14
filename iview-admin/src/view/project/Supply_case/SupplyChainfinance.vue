<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>查询
          <div slot="content">
            <Form
              ref="formSupplyChainfinance"
              :model="formSupplyChainfinance"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem>
                <Button type="primary" @click="handleSubmit('formSupplyChainfinance')">查询</Button>
                <Button @click="handleReset('formSupplyChainfinance')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <ButtonGroup style="margin-bottom: 15px;">
        <Button @click="handleOpenPage('add')">添加</Button>
        <Add v-model="isOpenAddPage" @addRow="addSupplyChainfinance"/>
        <Edit v-model="isOpenEditPage" @updateRow="updateSupplyChainfinance" :supplyChainfinance="editSupplyChainfinance"/>
        <Button @click="batchDelete()">删除</Button>
      </ButtonGroup>
      <Table
        ref="tableSupplyChainfinance"
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
  import { list, del, batchDelete} from '@/api/chainfinance'
  import { createFormObj } from '@/libs/util'

  import Add from './add.vue'
  import Edit from './edit.vue'

  export default {
    name: 'SupplyChainfinance',
    components: {
      Add,
      Edit
    },
    data () {
      return {
        formSupplyChainfinance: {
        },
        columns: [
          { type: 'selection', width: 60, align: 'center' },
          { key: 'id', title: '编号', sortable: false},
          { key: 'name', title: '供应链案例名称', sortable: false},
          { key: 'title', title: '供应链案例详情', sortable: false,tooltip:true},
          {
            key: 'picture', title: '供应链案例详情图片', sortable: false, width: 100,
            render: (h, params) => {
              return h('div', [
                h(
                  'img', {
                    attrs: {
                      src: this.getImageUrl(params.row.picture)
                    },
                    style: {
                      marginRight: '5px',
                      width: '80px',
                      height: '50px'
                    }
                  },
                  ''
                )
              ]);
            }

          },
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
                      size: 'small'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        //自定义代码
                      }
                    }
                  },
                  '自定义'
                ),
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
                        this.editSupplyChainfinance = params.row
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
                            del({ 'supplyChainfinance.id': params.row.id}).then(res => {
                              this.getSupplyChainfinanceList(this.current, this.pageSize)
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
        editSupplyChainfinance: {},
        selection: []
      }
    },
    methods: {
      getImageUrl (url) {

        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        // 查询按钮事件
        this.getSupplyChainfinanceList(this.current, this.pageSize)
        this.$Message.success('Success!')
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
      addSupplyChainfinance (row) {
        this.tableData.push(row)
      },
      updateSupplyChainfinance (row) {
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
          onCancel: () => {}
        });
      },
      getSupplyChainfinanceList (current, pageSize, paras) {
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
        Object.assign(para, createFormObj(this.formSupplyChainfinance, 'supplyChainfinance'))
        return para
      },
      exportExcel () {
        this.$refs.tableSupplyChainfinance.exportCsv({
          filename: `table-${new Date().valueOf()}.csv`
        })
      },
      changePage (pageNumber) {
        this.getSupplyChainfinanceList(pageNumber, this.pageSize)
      },
      changePageSize (pageSize) {
        this.getSupplyChainfinanceList(this.current, pageSize)
      },
      remove (index) {
        this.data6.splice(index, 1)
      },
      onSelectionChange (selection) {
        this.selection = selection
      },
      onSortChange (obj) {
        if (obj.order === 'normal') {
          this.getSupplyChainfinanceList(1, this.pageSize)
        } else {
          this.getSupplyChainfinanceList(1, this.pageSize, {
            field: obj.key,
            order: obj.order
          })
        }
      }
    },
    mounted () {
      this.getSupplyChainfinanceList(this.current, this.pageSize)
    }
  }
</script>

<style>
</style>

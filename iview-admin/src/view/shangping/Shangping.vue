<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>查询
          <div slot="content">
            <Form
              ref="formShangping"
              :model="formShangping"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem>
                <Button type="primary" @click="handleSubmit('formShangping')">查询</Button>
                <Button @click="handleReset('formShangping')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <ButtonGroup style="margin-bottom: 15px;">
        <Button @click="handleOpenPage('add')">添加</Button>
        <Add v-model="isOpenAddPage" @addRow="addShangping"/>
        <Edit v-model="isOpenEditPage" @updateRow="updateShangping" :shangping="editShangping"/>
        <Button @click="batchDelete()">删除</Button>
      </ButtonGroup>
      <Table
        ref="tableShangping"
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
  import { list, del,update, batchDelete} from '@/api/shangping'
  import { createFormObj } from '@/libs/util'

  import Add from '../multilevel/add.vue'
  import Edit from '../multilevel/edit.vue'

  export default {
    name: 'Shangping',
    components: {
      Add,
      Edit
    },
    data () {
      return {
        formShangping: {
        },
        columns: [
          { type: 'selection', width: 60, align: 'center' },
          { key: 'id', title: '编号', sortable: false},
          { key: 'sname', title: '商品名称', sortable: false},
          { key: 'uname', title: '领取人姓名', sortable: false},
          { key: 'time', title: '领取时间', sortable: false},
          { key: 'number', title: '领取数量', sortable: false},
          { key: 'beizhu', title: '备注', sortable: false},
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
                      click:()=>{
                        this.$Modal.confirm({
                          title: '提示',
                          content: '<p>确定通过吗?</p>',
                          onOk: () => {
                            del({ 'shangping.id': params.row.id,'shangping.sname': params.row.sname,'shangping.uname': params.row.uname,'shangping.time': params.row.time,
                              'shangping.number': params.row.number
                            }).then(res => {
                              this.getShangpingList(this.current, this.pageSize)
                            })
                          },
                          onCancel: () => {}
                        });
                      }
                    }
                  },
                  '通过'
                ),
                h(
                  'Button',
                  {
                    props: {
                      type: 'error',
                      size: 'small'
                    },
                    style: {
                      marginRight: '5px'
                    },
                    on: {
                      click: () => {
                        this.$Modal.confirm({
                          title: '提示',
                          content: '<p>确定取消申请吗?</p>',
                          onOk: () => {
                            update({ 'shangping.id': params.row.id,'shangping.sname': params.row.sname,'shangping.uname': params.row.uname,'shangping.time': params.row.time,
                              'shangping.number': params.row.number
                            }).then(res => {
                              this.getShangpingList(this.current, this.pageSize)
                            })
                          },
                          onCancel: () => {}
                        });
                      }
                    }
                  },
                  '取消'
                ),

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
        editShangping: {},
        selection: []
      }
    },
    methods: {
      handleSubmit (name) {
        // 查询按钮事件
        this.getShangpingList(this.current, this.pageSize)
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
      addShangping (row) {
        this.tableData.push(row)
      },
      updateShangping (row) {
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
      getShangpingList (current, pageSize, paras) {
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
        Object.assign(para, createFormObj(this.formShangping, 'shangping'))
        return para
      },
      exportExcel () {
        this.$refs.tableShangping.exportCsv({
          filename: `table-${new Date().valueOf()}.csv`
        })
      },
      changePage (pageNumber) {
        this.getShangpingList(pageNumber, this.pageSize)
      },
      changePageSize (pageSize) {
        this.getShangpingList(this.current, pageSize)
      },
      remove (index) {
        this.data6.splice(index, 1)
      },
      onSelectionChange (selection) {
        this.selection = selection
      },
      onSortChange (obj) {
        if (obj.order === 'normal') {
          this.getShangpingList(1, this.pageSize)
        } else {
          this.getShangpingList(1, this.pageSize, {
            field: obj.key,
            order: obj.order
          })
        }
      }
    },
    mounted () {
      this.getShangpingList(this.current, this.pageSize)
    }
  }
</script>

<style>
</style>

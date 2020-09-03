<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>查询
          <div slot="content">
            <Form
              ref="formDicStudent"
              :model="formDicStudent"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem>
                <Button type="primary" @click="handleSubmit('formDicStudent')">查询</Button>
                <Button @click="handleReset('formDicStudent')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <ButtonGroup style="margin-bottom: 15px;">
        <Button @click="handleOpenPage('add')">添加</Button>
        <Add v-model="isOpenAddPage" @addRow="addDicStudent"/>
        <Edit v-model="isOpenEditPage" @updateRow="updateDicStudent" :dicStudent="editDicStudent"/>
        <Button @click="batchDelete()">删除</Button>
      </ButtonGroup>
      <Table
        ref="tableDicStudent"
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
  import { list, del, batchDelete} from '@/api/student'
  import { createFormObj } from '@/libs/util'

  import Add from './add.vue'
  import Edit from './edit.vue'

  export default {
    name: 'DicStudent',
    components: {
      Add,
      Edit
    },
    data () {
      return {
        formDicStudent: {
        },
        columns: [
          { type: 'selection', width: 60, align: 'center' },
          { key: 'id', title: '编号', sortable: false},
          { key: 'name', title: '姓名', sortable: false},
          { key: 'address', title: '地址', sortable: false},
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
                        this.editDicStudent = params.row
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
                    on: {       click:()=>{
                        this.$Modal.confirm({
                          title: '提示',
                          content: '<p>确定删除吗?</p>',
                          onOk: () => {
                            del({ 'dicStudent.id': params.row.id}).then(res => {
                              this.getDicStudentList(this.current, this.pageSize);
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
        editDicStudent: {},
        selection: []
      }
    },
    methods: {
      handleSubmit (name) {
        // 查询按钮事件
        this.getDicStudentList(this.current, this.pageSize)
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
      addDicStudent (row) {
        this.tableData.push(row)
      },
      updateDicStudent (row) {

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
      getDicStudentList (current, pageSize, paras) {
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
        Object.assign(para, createFormObj(this.formDicStudent, 'dicStudent'))
        return para
      },
      exportExcel () {
        this.$refs.tableDicStudent.exportCsv({
          filename: `table-${new Date().valueOf()}.csv`
        })
      },
      changePage (pageNumber) {
        this.getDicStudentList(pageNumber, this.pageSize)
      },
      changePageSize (pageSize) {
        this.getDicStudentList(this.current, pageSize)
      },
      remove (index) {
        this.data6.splice(index, 1)
      },
      onSelectionChange (selection) {
        this.selection = selection
      },
      onSortChange (obj) {
        if (obj.order === 'normal') {
          this.getDicStudentList(1, this.pageSize)
        } else {
          this.getDicStudentList(1, this.pageSize, {
            field: obj.key,
            order: obj.order
          })
        }
      }
    },
    mounted () {
      this.getDicStudentList(this.current, this.pageSize)
    }
  }
</script>

<style>
</style>

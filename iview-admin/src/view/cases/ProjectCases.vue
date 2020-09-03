<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>查询
          <div slot="content">
            <Form
              ref="formProjectCases"
              :model="formProjectCases"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem>
                <Button type="primary" @click="handleSubmit('formProjectCases')">查询</Button>
                <Button @click="handleReset('formProjectCases')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <ButtonGroup style="margin-bottom: 15px;">
        <Button @click="handleOpenPage('add')">添加</Button>
        <Add v-model="isOpenAddPage" @addRow="addProjectCases"/>
        <Edit v-model="isOpenEditPage" @updateRow="updateProjectCases" :projectCases="editProjectCases"/>
        <Button @click="batchDelete()">删除</Button>
      </ButtonGroup>
      <Table
        ref="tableProjectCases"
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
  import { list, del, batchDelete} from '@/api/cases'
  import { createFormObj } from '@/libs/util'

  import Add from './add.vue'
  import Edit from './edit.vue'

  export default {
    name: 'ProjectCases',
    components: {
      Add,
      Edit
    },
    data () {
      return {
        formProjectCases: {
        },
        columns: [
          { type: 'selection', width: 60, align: 'center' },
          { key: 'id', title: '编号', sortable: false},
          {
            key: 'picture', title: '项目案例图', sortable: false,
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
            key: 'pictures', title: '项目浮动图', sortable: false,
            render: (h, params) => {
              return h('div', [
                h(
                  'img', {
                    attrs: {
                      src: this.getImageUrl(params.row.pictures)
                    },
                    style: {
                      marginRight: '5px',
                      width: '80px',
                      height: '50px',
                      backgroundColor: 'green'
                    }
                  },
                  ''
                )
              ]);
            }
          },
          { key: 'name', title: '项目名称', sortable: false},
          { key: 'title', title: '项目详情', sortable: false,tooltip:true},

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
                        this.editProjectCases = params.row
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
        editProjectCases: {},
        selection: []
      }
    },
    methods: {
      getImageUrl (url) {

        return this.$config.urlPath + url
      },
      handleSubmit (name) {
        // 查询按钮事件
        this.getProjectCasesList(this.current, this.pageSize)
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
      addProjectCases (row) {
        this.tableData.push(row)
      },
      updateProjectCases (row) {
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
      getProjectCasesList (current, pageSize, paras) {
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
        Object.assign(para, createFormObj(this.formProjectCases, 'projectCases'))
        return para
      },
      exportExcel () {
        this.$refs.tableProjectCases.exportCsv({
          filename: `table-${new Date().valueOf()}.csv`
        })
      },
      changePage (pageNumber) {
        this.getProjectCasesList(pageNumber, this.pageSize)
      },
      changePageSize (pageSize) {
        this.getProjectCasesList(this.current, pageSize)
      },
      remove (index) {
        this.data6.splice(index, 1)
      },
      onSelectionChange (selection) {
        this.selection = selection
      },
      onSortChange (obj) {
        if (obj.order === 'normal') {
          this.getProjectCasesList(1, this.pageSize)
        } else {
          this.getProjectCasesList(1, this.pageSize, {
            field: obj.key,
            order: obj.order
          })
        }
      }
    },
    mounted () {
      this.getProjectCasesList(this.current, this.pageSize)
    }
  }
</script>

<style>
</style>

<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>查询
          <div slot="content">
            <Form
              ref="formDicRole"
              :model="formDicRole"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem label="角色名称" prop="ROLE_NAME">
                <Input type="text" v-model="formDicRole.ROLE_NAME" placeholder="角色名称"/>
              </FormItem>
              <FormItem label="录入日期" prop="ENTRY_TIME">
                <DatePicker
                  type="datetimerange"
                  format="yyyy-MM-dd HH:mm:ss"
                  transfer
                  v-model="formDicRole.ENTRY_TIME"
                  style="width:275px"
                ></DatePicker>
              </FormItem>
              <FormItem label="角色类型" prop="TYPE">
                <Select v-model="formDicRole.TYPE">
                  <Option value="1">角色</Option>
                  <Option value="2">部门</Option>
                  <Option value="3">单位</Option>
                  <Option value="4">其它</Option>
                </Select>
              </FormItem>
              <FormItem label="是否私有" prop="IS_PRIVATE">
                <Select v-model="formDicRole.IS_PRIVATE">
                  <Option value="1">私有</Option>
                  <Option value="2">公开</Option>
                </Select>
              </FormItem>
              <FormItem>
                <Button type="primary" @click="handleSubmit('formDicRole')">查询</Button>
                <Button @click="handleReset('formDicRole')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <ButtonGroup style="margin-bottom: 15px;">
        <Add v-model="isOpenAddPage" @addRow="addDicRole" :parentId="parentId"/>
        <Button @click="handleOpenPage('add')">添加</Button>
        <Edit v-model="isOpenEditPage" @updateRow="updateDicRole" :dicRole="editDicRole"/>
        <Button @click="batchDelete()">删除</Button>
      </ButtonGroup>
      <ButtonGroup style="margin-bottom: 15px;margin-left: 15px;">
        <DrawerPage v-model="isOpenDrawerPage" @on-save-popedoms="onSaveRolePopedoms" :roleId="editRoleId"/>
      </ButtonGroup>
      <TreeTable
        :data="tableData"
        :columns="columns"
        @on-load-children="onloadChildren"
        @on-remove-children="onRemoveChildren"
        @on-checked-change="onCheckedChange"
        @on-toggle-expand="onToggleExpand"
        @on-row-click="onRowClick"
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
    </Card>
  </div>
</template>

<script>
import { list, del, batchDelete, savePopedom, batchSavePopedom} from '@/api/role'
import { createFormObj } from '@/libs/util'

import TreeTable from '_c/treetable'
import Add from './add.vue'
import Edit from './edit.vue'
import DrawerPage from './drawer.vue'

export default {
  name: 'DicPopedom',
  components: {
    Add,
    Edit,
    TreeTable,
    DrawerPage
  },
  data () {
    return {
      formDicRole: {
        
        ROLE_NAME: ''
        ,
        ENTRY_TIME: ''
        ,
        TYPE: null
        ,
        IS_PRIVATE: null
      },
      columns: [
        { key: 'ROLE_NAME', width: 220, title: '角色名称', type: 'treeNode' },
        {
          key: 'TYPE',
          title: '角色类型',
		      width: 100,
          render: (h, params) => {
            let dic = {
              
              1 : '角色'
              ,
              2 : '部门'
              ,
              3 : '单位'
              ,
              4 : '其它'
            }
            return h('div', dic[params.row.TYPE])
          }
        },
        {
          key: 'IS_PRIVATE',
          title: '是否私有',
		      width: 100,
          render: (h, params) => {
            let dic = {
              
              1 : '私有'
              ,
              2 : '公开'
            }
            return h('div', dic[params.row.IS_PRIVATE])
          }
        },
        { key: 'REMARK', minWidth: 100, title: '注释' },
        { key: 'XH', width: 100, title: '排序' },
        { key: 'ENTRY_TIME', width: 200, title: '录入时间' },
        {
          title: '操作',
          key: 'action',
          width: 250,
          fixed: 'right',
          align: 'center',
          render: (h, params) => {
            return h('div', [
              this.renderAddButton(h, params)
              ,
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
                      this.editDicRole = params.row
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
                    click: () => {
                      this.$Modal.confirm({
                        title: '提示',
                        content: '<p>确定删除吗?</p>',
                        onOk: () => {
                          del({ 'dicRole.ID': params.row.ID }).then(res => {
                            if (res) {
                              this.tableData.splice(params.index, 1)
                            }
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
      isOpenDrawerPage: false,
      editDicRole: {},
      editRoleId: null,
      checking: [],
      parentId: '0'
    }
  },
  methods: {
    handleSubmit (name) {
      // 查询按钮事件
      this.getDicRoleList(this.current, this.pageSize)
      this.$Message.success('Success!')
    },
    handleReset (name) {
      // 重置按钮事件
      this.$refs[name].resetFields()
    },
    handleOpenPage (name) {
      // 打开页面事件(添加页,编辑页...)
      if (name === 'add') {
        this.parentId = '0'
        this.isOpenAddPage = true
      } else if (name === 'edit') {
        this.isOpenEditPage = true
      }
    },
    onRowClick (row, index) {
      if (row.TYPE === 1) {
        this.editRoleId = row.ID
        this.isOpenDrawerPage = true
      } else {
        this.isOpenDrawerPage = false
      }
    },
    onSaveRolePopedoms (popedomIds) {
      //获取角色ids
      let roleIds = '';
      for(let i=0 ; i<this.checking.length; i++) {
    	  if (this.checking[i].TYPE == 1)
    		 roleIds += this.checking[i].ID+','
      }
      if(roleIds != '') {
        this.$Modal.confirm({
          title: '提示',
          content: '<p>确定为勾选的'+this.checking.length+'个角色分配此权限?</p>',
          onOk: () => {
            batchSavePopedom({popedomIds: popedomIds, roleIds: roleIds})
          },
          onCancel: () => {}
        });
      } else {
        savePopedom({popedomIds: popedomIds, roleId: this.editRoleId})
      }

    },
    addDicRole (row) {
      let insertIndex = 0
      if (row.PARENT_ID === '0') {
        this.tableData.push(row)
      } else {
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].ID === row.PARENT_ID && this.tableData[i]._isExpand === true) {
            insertIndex = i+1
            row._level = this.tableData[i]._level+1
            this.tableData.splice(insertIndex, 0, row)
            break
          }
        }
      }
    },
    updateDicRole (row) {
      this.$set(this.tableData, row._index, row)
    },
    batchDelete () {
      this.$Modal.confirm({
          title: '提示',
          content: '<p>确定删除吗?</p>',
          onOk: () => {
            if (this.checking.length > 0) {
              let ids = ''
              for (var i = 0; i < this.checking.length; i++) {
                ids += ',' + this.checking[i].ID
              }
              batchDelete({ ids: ids }).then(res => {
                if (res) {
                  for (var i = 0; i < this.checking.length; i++) {
                    this.tableData = this.tableData.filter(
                      item => item.ID !== this.checking[i].ID
                    )
                  }
                }
              })
            } else {
              this.$Message.error('未选中记录!')
            }
          },
          onCancel: () => {}
      });
    },
    getDicRoleList (current, pageSize) {
      list(this.handleRequestParams(current, pageSize)).then(res => {
        if (res) {
          this.tableData = this.handleRes(res.list)
          this.current = parseInt(res.pageNumber)
          this.pageSize = parseInt(res.pageSize)
          this.total = parseInt(res.totalRow)
        }
      })
    },
    handleRes (list) {
      return list.map(row => {
        row._level = 1
        row._checked = false
        row._isExpand = false
        return row
      })
    },
    handleRequestParams (current, pageSize) {
      const para = { pageNumber: current, pageSize: pageSize }
      Object.assign(para, createFormObj(this.formDicRole, 'dicRole'))
      return para
    },
    changePage (pageNumber) {
      this.getDicRoleList(pageNumber, this.pageSize)
    },
    changePageSize (pageSize) {
      this.getDicRoleList(this.current, pageSize)
    },
    onCheckedChange (checking) {
      this.checking = checking
    },
    onToggleExpand (row, index, isExpand) {
      this.tableData[index]._isExpand = isExpand
    },
    onloadChildren (row, index, level, callback) {
      let vm = this
      list({ id: row.ID }).then(function (res) {
        if (res) {
          vm.tableData = callback(index, level, res)
        }
      })
    },
    onRemoveChildren (startIndex, howmany) {
      this.tableData.splice(startIndex, howmany)
    },
    renderAddButton(h, params) {
      if (this.tableData[params.index].TYPE === 1) {
        return h(
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
                      this.editRoleId = this.tableData[params.index].ID
                      this.isOpenDrawerPage = true
                    }
                  }
                },
                '权限'
              )
      } else {
        return  h(
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
                        this.parentId = this.tableData[params.index].ID
                        this.isOpenAddPage = true
                      }
                    }
                  },
                  '添加'
                )
      }
             
    }
  },
  mounted () {
    this.getDicRoleList(this.current, this.pageSize)
  }
}
</script>

<style>

</style>
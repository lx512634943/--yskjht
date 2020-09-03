<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>
          查询
          <div slot="content">
            <Form
              ref="formInline"
              :model="formInline"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem label="用户类型" prop="TYPE">
                <Select v-model="formInline.TYPE">
                  <Option value="1">普通用户</Option>
                  <Option value="2">管理员</Option>
                </Select>
              </FormItem>
              <FormItem label="登录名称" prop="USER_CODE">
                <Input type="text" v-model="formInline.USER_CODE" placeholder="登录名称"/>
              </FormItem>
              <FormItem label="用户名" prop="USER_NAME">
                <Input type="text" v-model="formInline.USER_NAME" placeholder="用户名"/>
              </FormItem>
              <FormItem label="是否禁用" prop="IS_DISABLED">
                <Select v-model="formInline.IS_DISABLED">
                  <Option value="1">是</Option>
                  <Option value="2">否</Option>
                </Select>
              </FormItem>
              <FormItem label="录入日期" prop="ENTRY_TIME">
                <DatePicker
                  type="datetimerange"
                  format="yyyy-MM-dd HH:mm:ss"
                  transfer
                  v-model="formInline.ENTRY_TIME"
                  style="width:275px"
                ></DatePicker>
              </FormItem>
              <FormItem>
                <Button type="primary" @click="handleSubmit('formInline')">查询</Button>
                <Button @click="handleReset('formInline')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <ButtonGroup style="margin-bottom: 15px;">
        <Button @click="handleOpenPage('add')">添加</Button>
        <Add v-model="isOpenAddPage" @addRow="addUser"/>
        <Edit v-model="isOpenEditPage" @updateRow="updateUser" :user="editUser"/>
        <Button @click="batchDelete()">删除</Button>
      </ButtonGroup>
      <ButtonGroup style="margin-bottom: 15px;margin-left: 15px;">
        <DrawerPage
          v-model="isOpenDrawerPage"
          @on-save-roles="onSaveUserRoles"
          :userId="editUserId"
        />
      </ButtonGroup>
      <Table
        ref="tables"
        :data="tableData"
        :columns="columns"
        :highlight-row="true"
        @on-selection-change="onSelectionChange"
        @on-sort-change="onSortChange"
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
      <Button style="margin: 10px 0;" type="primary" @click="exportExcel">导出为Csv文件</Button>
    </Card>
  </div>
</template>

<script>
import {
  list,
  del,
  batchDelete,
  isDisable,
  resetPwd,
  saveRole,
  batchSaveRole
} from '@/api/user';
import { createFormObj } from '@/libs/util';

import Add from './add.vue';
import Edit from './edit.vue';
import DrawerPage from './drawer.vue';

export default {
  name: 'User',
  components: {
    Add,
    Edit,
    DrawerPage
  },
  data() {
    return {
      formInline: {
        TYPE: null,
        USER_CODE: '',
        USER_NAME: '',
        IS_DISABLED: null,
        ENTRY_TIME: ''
      },
      columns: [
        { type: 'selection', width: 60, align: 'center' },
        {
          key: 'TYPE',
          title: '用户类型',
          sortable: true,
          render: (h, params) => {
            let dic = {
              1: '普通用户',

              2: '管理员'
            };
            return h('div', dic[params.row.TYPE]);
          }
        },
        { key: 'USER_CODE', title: '登录名称', sortable: true },
        { key: 'USER_NAME', title: '用户名', sortable: true },
        {
          key: 'IS_DISABLED',
          title: '是否禁用',
          sortable: true,
          render: (h, params) => {
            return h(
              'i-switch',
              {
                props: {
                  value: params.row.IS_DISABLED === 1
                },
                on: {
                  'on-change': val => {
                    isDisable({
                      id: params.row.ID,
                      is_disabled: val ? 1 : 2
                    }).then(res => {
                      if (res) {
                        params.row.IS_DISABLED = val ? 1 : 2;
                      }
                    });
                  }
                }
              },
              [
                h('span', { slot: 'open' }, '是'),
                h('span', { slot: 'close' }, '否')
              ]
            );
          }
        },
        { key: 'REMARK', title: '备注' },
        { key: 'ENTRY_TIME', title: '录入时间', sortable: true },
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
                      resetPwd({
                        'user.ID': params.row.ID,
                        'user.PASSWD': '000000'
                      });
                    }
                  }
                },
                '重置密码'
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
                      this.editUser = params.row;
                      this.handleOpenPage('edit');
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
                          del({ 'user.ID': params.row.ID }).then(res => {
                            if (res) {
                              this.tableData.splice(params.index, 1);
                            }
                          });
                        },
                        onCancel: () => {}
                      });
                    }
                  }
                },
                '删除'
              )
            ]);
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
      editUserId: null,
      editUser: {},
      selection: []
    };
  },
  methods: {
    handleSubmit(name) {
      // 查询按钮事件
      this.getUserList(this.current, this.pageSize);
      this.$Message.success('Success!');
    },
    handleReset(name) {
      // 重置按钮事件
      this.$refs[name].resetFields();
    },
    handleOpenPage(name) {
      // 打开页面事件(添加页,编辑页...)
      if (name === 'add') this.isOpenAddPage = true;
      else if (name === 'edit') {
        this.isOpenEditPage = true;
      }
    },
    addUser(user) {
      this.tableData.push(user);
    },
    updateUser(user) {
      this.tableData[user._index] = user;
    },
    batchDelete() {
      this.$Modal.confirm({
        title: '提示',
        content: '<p>确定删除吗?</p>',
        onOk: () => {
          if (this.selection.length > 0) {
            let ids = '';
            for (var i = 0; i < this.selection.length; i++) {
              ids += ',' + this.selection[i].ID;
            }
            batchDelete({ ids: ids }).then(res => {
              if (res) {
                for (var i = 0; i < this.selection.length; i++) {
                  this.tableData = this.tableData.filter(
                    item => item.ID !== this.selection[i].ID
                  );
                }
              }
            });
          } else {
            this.$Message.error('未选中记录!');
          }
        },
        onCancel: () => {}
      });
    },
    getUserList(current, pageSize, paras) {
      let para = { pageNumber: current, pageSize: pageSize };
      if (paras) Object.assign(para, paras);

      list(this.handleRequestParams(para)).then(res => {
        if (res) {
          this.tableData = res.list;
          this.current = parseInt(res.pageNumber);
          this.pageSize = parseInt(res.pageSize);
          this.total = parseInt(res.totalRow);
        }
      });
    },
    handleRequestParams(para) {
      Object.assign(para, createFormObj(this.formInline, 'user'));
      return para;
    },
    exportExcel() {
      this.$refs.tables.exportCsv({
        filename: `table-${new Date().valueOf()}.csv`
      });
    },
    changePage(pageNumber) {
      this.getUserList(pageNumber, this.pageSize);
    },
    changePageSize(pageSize) {
      this.getUserList(this.current, pageSize);
    },
    remove(index) {
      this.data6.splice(index, 1);
    },
    onSelectionChange(selection) {
      this.selection = selection;
    },
    onSortChange(obj) {
      if (obj.order === 'normal') {
        this.getUserList(1, this.pageSize);
      } else {
        this.getUserList(1, this.pageSize, {
          field: obj.key,
          order: obj.order
        });
      }
    },
    onRowClick(row, index) {
      this.editUserId = row.ID;
      this.isOpenDrawerPage = true;
    },
    onSaveUserRoles(roleIds) {
      var userIds = '';
      for (var i = 0; i < this.selection.length; i++) {
        userIds += this.selection[i].ID + ',';
      }

      if (userIds != '') {
        this.$Modal.confirm({
          title: '提示',
          content:
            '<p>确定为勾选的' + this.selection.length + '个用户分配此权限?</p>',
          onOk: () => {
            batchSaveRole({ roleIds: roleIds, roleIds: roleIds });
          },
          onCancel: () => {}
        });
      } else {
        saveRole({ roleIds: roleIds, userId: this.editUserId });
      }
    }
  },
  mounted() {
    this.getUserList(this.current, this.pageSize);
  }
};
</script>

<style>
.ivu-collapse-content > .ivu-collapse-content-box {
  padding-bottom: 0px;
}
</style>

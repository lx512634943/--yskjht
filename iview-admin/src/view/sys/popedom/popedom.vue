<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>
          查询
          <div slot="content">
            <Form
              ref="formDicPopedom"
              :model="formDicPopedom"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem label="名称" prop="POPEDOM_NAME">
                <Input type="text" v-model="formDicPopedom.POPEDOM_NAME" placeholder="名称"/>
              </FormItem>
              <FormItem label="路由" prop="FORWARD_ACTION">
                <Input type="text" v-model="formDicPopedom.FORWARD_ACTION" placeholder="路由"/>
              </FormItem>
              <FormItem label="重定向" prop="REDIRECT">
                <Input type="text" v-model="formDicPopedom.REDIRECT" placeholder="重定向"/>
              </FormItem>
              <FormItem label="类型" prop="TYPE">
                <Select v-model="formDicPopedom.TYPE">
                  <Option value="1">菜单</Option>
                  <Option value="2">权限url</Option>
                  <Option value="3">其它</Option>
                </Select>
              </FormItem>
              <FormItem label="录入日期" prop="ENTRY_TIME">
                <DatePicker
                  type="datetimerange"
                  format="yyyy-MM-dd HH:mm:ss"
                  transfer
                  v-model="formDicPopedom.ENTRY_TIME"
                  style="width:275px"
                ></DatePicker>
              </FormItem>
              <FormItem label="是否审计" prop="IS_LOG">
                <Select v-model="formDicPopedom.IS_LOG">
                  <Option value="1">是</Option>
                  <Option value="2">否</Option>
                </Select>
              </FormItem>
              <FormItem label="是否隐藏" prop="IS_HIDE">
                <Select v-model="formDicPopedom.IS_HIDE">
                  <Option value="1">是</Option>
                  <Option value="2">否</Option>
                </Select>
              </FormItem>
              <FormItem label="是否不缓存" prop="IS_NOT_CACHE">
                <Select v-model="formDicPopedom.IS_NOT_CACHE">
                  <Option value="1">是</Option>
                  <Option value="2">否</Option>
                </Select>
              </FormItem>
              <FormItem>
                <Button type="primary" @click="handleSubmit('formDicPopedom')">查询</Button>
                <Button @click="handleReset('formDicPopedom')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <ButtonGroup style="margin-bottom: 15px;">
        <Button @click="handleOpenPage('add')">添加</Button>
        <Add v-model="isOpenAddPage" @addRow="addDicPopedom" :parentId="parentId"/>
        <Edit v-model="isOpenEditPage" @updateRow="updateDicPopedom" :dicPopedom="editDicPopedom"/>
        <Button @click="batchDelete()">删除</Button>
      </ButtonGroup>
      <TreeTable
        :data="tableData"
        :columns="columns"
        @on-load-children="onloadChildren"
        @on-remove-children="onRemoveChildren"
        @on-checked-change="onCheckedChange"
        @on-toggle-expand="onToggleExpand"
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
import { list, del, batchDelete, syncPopedom } from "@/api/popedom";
import { createFormObj } from "@/libs/util";

import TreeTable from "_c/treetable";
import Add from "./add.vue";
import Edit from "./edit.vue";

export default {
  name: "DicPopedom",
  components: {
    Add,
    Edit,
    TreeTable
  },
  data() {
    return {
      formDicPopedom: {
        POPEDOM_NAME: '',

        FORWARD_ACTION: '',

        REDIRECT: '',

        TYPE: null,

        ENTRY_TIME: '',

        IS_LOG: null,

        IS_HIDE: null,

        IS_NOT_CACHE: null
      },
      columns: [
        {
          type: 'treeNode',
          key: 'POPEDOM_NAME',
          title: '名称',
          minWidth: 200
        },
        {
          key: 'FORWARD_ACTION',
          title: '路由',
          sortable: false,
          minWidth: 200
        },
        { key: 'NAME', title: '路由名称', sortable: false, width: 100 },
        { key: 'COMPONENT', title: '组件路径', sortable: false, minWidth: 200 },
        {
          key: 'TYPE',
          title: '类型',
          sortable: false,
          width: 100,
          render: (h, params) => {
            let dic = {
              1: '菜单',

              2: '权限url',

              3: '其它'
            };
            return h('div', dic[params.row.TYPE]);
          }
        },
        { key: 'ENTRY_TIME', title: '录入时间', sortable: false, width: 200 },
        { key: 'XH', title: '序号', sortable: false, width: 80 },
        {
          key: 'IS_LOG',
          title: '是否审计',
          sortable: false,
          width: 100,
          render: (h, params) => {
            let dic = {
              1: '是',
              2: '否'
            };
            return h('div', dic[params.row.IS_LOG]);
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 250,
          align: 'center',
          fixed: 'right',
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
                      let curr_level = this.tableData[params.index]._level;
                      let url = this.tableData[params.index].FORWARD_ACTION;
                      for (
                        let index = params.index;
                        curr_level !== 1;
                        index--
                      ) {
                        if (curr_level > this.tableData[index]._level) {
                          url =
                            this.tableData[index].FORWARD_ACTION + '/' + url;
                          curr_level = this.tableData[index]._level;
                        }
                      }
                      syncPopedom({ id: params.row.ID, url: url }).then(res => {
                        if (res) {
                          this.getDicPopedomList(this.current, this.pageSize);
                        }
                      });
                    }
                  }
                },
                '同步权限'
              ),
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
                      this.parentId = this.tableData[params.index].ID;
                      this.isOpenAddPage = true;
                    }
                  }
                },
                '添加'
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
                      this.editDicPopedom = params.row;
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
                          del({ 'dicPopedom.ID': params.row.ID }).then(res => {
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
      editDicPopedom: {},
      checking: [],
      parentId: '0'
    };
  },
  methods: {
    handleSubmit(name) {
      // 查询按钮事件
      this.getDicPopedomList(this.current, this.pageSize);
      this.$Message.success('Success!');
    },
    handleReset(name) {
      // 重置按钮事件
      this.$refs[name].resetFields();
    },
    handleOpenPage(name) {
      // 打开页面事件(添加页,编辑页...)
      if (name === 'add') {
        this.parentId = '0';
        this.isOpenAddPage = true;
      } else if (name === 'edit') {
        this.isOpenEditPage = true;
      }
    },
    addDicPopedom(row) {
      let insertIndex = 0;
      if (row.PARENT_ID === '0') {
        this.tableData.push(row);
      } else {
        for (let i = 0; i < this.tableData.length; i++) {
          if (
            this.tableData[i].ID === row.PARENT_ID &&
            this.tableData[i]._isExpand === true
          ) {
            insertIndex = i + 1;
            row._level = this.tableData[i]._level + 1;
            this.tableData.splice(insertIndex, 0, row);
            break;
          }
        }
      }
    },
    updateDicPopedom(row) {
      this.$set(this.tableData, row._index, row);
    },
    batchDelete() {
      this.$Modal.confirm({
        title: '提示',
        content: '<p>确定删除吗?</p>',
        onOk: () => {
          if (this.checking.length > 0) {
            let ids = '';
            for (var i = 0; i < this.checking.length; i++) {
              ids += ',' + this.checking[i].ID;
            }
            batchDelete({ ids: ids }).then(res => {
              if (res) {
                for (var i = 0; i < this.checking.length; i++) {
                  this.tableData = this.tableData.filter(
                    item => item.ID !== this.checking[i].ID
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
    getDicPopedomList(current, pageSize) {
      list(this.handleRequestParams(current, pageSize)).then(res => {
        if (res) {
          this.tableData = this.handleRes(res.list);
          this.current = parseInt(res.pageNumber);
          this.pageSize = parseInt(res.pageSize);
          this.total = parseInt(res.totalRow);
        }
      });
    },
    handleRes(list) {
      return list.map(row => {
        row._level = 1;
        row._checked = false;
        row._isExpand = false;
        return row;
      });
    },
    handleRequestParams(current, pageSize) {
      const para = { pageNumber: current, pageSize: pageSize }
      Object.assign(para, createFormObj(this.formDicPopedom, 'dicPopedom'))
      return para;
    },
    changePage(pageNumber) {
      this.getDicPopedomList(pageNumber, this.pageSize);
    },
    changePageSize(pageSize) {
      this.getDicPopedomList(this.current, pageSize);
    },
    onCheckedChange(checking) {
      this.checking = checking;
    },
    onToggleExpand(row, index, isExpand) {
      this.tableData[index]._isExpand = isExpand;
    },
    onloadChildren(row, index, level, callback) {
      let vm = this;
      list({ id: row.ID }).then(function(res) {
        if (res) {
          vm.tableData = callback(index, level, res);
        }
      });
    },
    onRemoveChildren(startIndex, howmany) {
      this.tableData.splice(startIndex, howmany);
    }
  },
  mounted() {
    this.getDicPopedomList(this.current, this.pageSize);
  }
};
</script>

<style>
.ivu-collapse-content > .ivu-collapse-content-box {
  padding-bottom: 0px;
}
</style>

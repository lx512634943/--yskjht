<template>
  <div>
    <Card>
      <Collapse style="margin-bottom:15px;">
        <Panel>查询
          <div slot="content">
            <Form
              ref="formDicLog"
              :model="formDicLog"
              label-position="right"
              :label-width="80"
              inline
            >
              <FormItem label="SESSION_ID" prop="SESSION_ID">
                <Input type="text" v-model="formDicLog.SESSION_ID" placeholder="SESSION_ID"/>
              </FormItem>
              <FormItem label="登录名称" prop="USER_CODE">
                <Input type="text" v-model="formDicLog.USER_CODE" placeholder="登录名称"/>
              </FormItem>
              <FormItem label="操作功能" prop="POPEDOM_NAME">
                <Input type="text" v-model="formDicLog.POPEDOM_NAME" placeholder="操作功能"/>
              </FormItem>
              <FormItem label="操作uri" prop="FORWARD_ACTION">
                <Input type="text" v-model="formDicLog.FORWARD_ACTION" placeholder="操作uri"/>
              </FormItem>
              <FormItem label="录入日期" prop="ENTRY_TIME">
                <DatePicker
                  type="datetimerange"
                  format="yyyy-MM-dd HH:mm:ss"
                  transfer
                  v-model="formDicLog.ENTRY_TIME"
                  style="width:275px"
                ></DatePicker>
              </FormItem>
              <FormItem label="IP" prop="IP">
                <Input type="text" v-model="formDicLog.IP" placeholder="IP"/>
              </FormItem>
              <FormItem label="浏览器" prop="BROWSER">
                <Input type="text" v-model="formDicLog.BROWSER" placeholder="浏览器"/>
              </FormItem>
              <FormItem label="系统" prop="OS">
                <Input type="text" v-model="formDicLog.OS" placeholder="系统"/>
              </FormItem>
              <FormItem>
                <Button type="primary" @click="handleSubmit('formDicLog')">查询</Button>
                <Button @click="handleReset('formDicLog')" style="margin-left: 8px">重置</Button>
              </FormItem>
            </Form>
          </div>
        </Panel>
      </Collapse>
      <Table
        ref="tables"
        :data="tableData"
        :columns="columns"
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
import { list, del, batchDelete} from '@/api/log'
import { createFormObj } from '@/libs/util'

export default {
  name: 'DicLog',
  data () {
    return {
      formDicLog: {
        
        SESSION_ID: ''
        ,
        USER_CODE: ''
        ,
        POPEDOM_NAME: ''
        ,
        FORWARD_ACTION: ''
        ,
        ENTRY_TIME: ''
        ,
        IP: ''
        ,
        BROWSER: ''
        ,
        OS: ''
      },
      columns: [
        { type: 'selection', width: 60, align: 'center' },
        { key: 'SESSION_ID', title: 'SESSION_ID', sortable: true },
        { key: 'USER_CODE', title: '登录名称', sortable: true },
        { key: 'POPEDOM_NAME', title: '操作功能', sortable: true },
        { key: 'FORWARD_ACTION', title: '操作uri', sortable: true },
        { key: 'ENTRY_TIME', title: '操作时间', sortable: true },
        { key: 'IP', title: 'IP', sortable: true },
        { key: 'BROWSER', title: '浏览器', sortable: true },
        { key: 'OS', title: '系统', sortable: true },
        { key: 'PARAS', title: '参数', sortable: true }
      ],
      tableData: [],
      total: 0,
      current: 1,
      pageSize: 10,
      selection: []
    }
  },
  methods: {
    handleSubmit (name) {
      // 查询按钮事件
      this.getDicLogList(this.current, this.pageSize)
      this.$Message.success('Success!')
    },
    handleReset (name) {
      // 重置按钮事件
      this.$refs[name].resetFields()
    },
    getDicLogList (current, pageSize, paras) {
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
      Object.assign(para, createFormObj(this.formDicLog, 'dicLog'))
      return para
    },
    exportExcel () {
      this.$refs.tables.exportCsv({
        filename: `table-${new Date().valueOf()}.csv`
      })
    },
    changePage (pageNumber) {
      this.getDicLogList(pageNumber, this.pageSize)
    },
    changePageSize (pageSize) {
      this.getDicLogList(this.current, pageSize)
    },
    onSelectionChange (selection) {
      this.selection = selection
    },
    onSortChange (obj) {
      if (obj.order === 'normal') {
        this.getDicLogList(1, this.pageSize)
      } else {
        this.getDicLogList(1, this.pageSize, {
          field: obj.key,
          order: obj.order
        })
      }
    }
  },
  mounted () {
    this.getDicLogList(this.current, this.pageSize)
  }
}
</script>

<style>
</style>
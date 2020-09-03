import Mock from 'mockjs'
import { doCustomTimes, getParams } from '@/libs/util'

export const list = req => {
  const params = getParams(req.url)
  let tableData = []
  doCustomTimes(params.pageSize, () => {
    tableData.push(Mock.mock({
    	'TYPE|1-2': 2,
    	USER_CODE: '@last',
    	USER_NAME: '@cname',
    	REMARK: '@csentence',
    	'IS_DISABLED|1-2': 2,
    	ENTRY_TIME: '@datetime'
    }))
  })
  return {
    list: tableData,
    pageNumber: params.pageNumber,
    pageSize: params.pageSize,
    totalRow: 100,
    
    code: 200,
    msg: ''
  }
}

export const save = req => {
  req = JSON.parse(req.body)
  let row = Mock.mock({
		'TYPE|1-2': 2,
		USER_CODE: '@last',
		USER_NAME: '@cname',
		REMARK: '@csentence',
		'IS_DISABLED|1-2': 2,
		ENTRY_TIME: '@datetime'
	  })
  
  return {
    code: 200,
    data: row,
    msg: ''
  }
}
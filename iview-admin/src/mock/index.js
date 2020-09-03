import Mock from 'mockjs'
import { login, logout, getUserInfo } from './login'
import { getTableData } from './data'
import { list, save } from './sys/user/user'

// 登录相关和获取用户信息
Mock.mock(/\/login/, login)
Mock.mock(/\/get_info/, getUserInfo)
Mock.mock(/\/logout/, logout)
Mock.mock(/\/get_table_data/, getTableData)
Mock.mock(/\/admin\/user\/list/, list)
Mock.mock(/\/admin\/user\/save/, save)

export default Mock

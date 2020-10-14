import axios from '@/libs/api.request'

export const getTableData = () => {
  return axios.request({
    url: '/get_table_data',
    method: 'get'
  })
}

export const list = (paras) => {
  return axios.request({
    url: '/admin/data/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/data/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/data/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/data/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/data/batchDelete',
    data,
    method: 'post'
  })
}

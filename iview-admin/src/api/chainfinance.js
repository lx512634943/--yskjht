import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/chainfinance/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/chainfinance/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/chainfinance/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/chainfinance/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/chainfinance/batchDelete',
    data,
    method: 'post'
  })
}

import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/chain/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/chain/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/chain/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/chain/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/chain/batchDelete',
    data,
    method: 'post'
  })
}

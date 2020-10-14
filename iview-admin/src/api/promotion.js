import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/promotion/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/promotion/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/promotion/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/promotion/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/promotion/batchDelete',
    data,
    method: 'post'
  })
}

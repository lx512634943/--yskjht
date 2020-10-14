import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/intellectualization/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/intellectualization/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/intellectualization/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/intellectualization/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/intellectualization/batchDelete',
    data,
    method: 'post'
  })
}

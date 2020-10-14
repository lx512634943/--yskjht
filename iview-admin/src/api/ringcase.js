import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/ringcase/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/ringcase/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/ringcase/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/ringcase/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/ringcase/batchDelete',
    data,
    method: 'post'
  })
}

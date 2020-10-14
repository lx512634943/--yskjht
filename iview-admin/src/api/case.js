import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/case/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/case/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/case/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/case/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/case/batchDelete',
    data,
    method: 'post'
  })
}

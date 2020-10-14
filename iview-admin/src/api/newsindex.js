import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/newsindex/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/newsindex/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/newsindex/update',
    data,
    method: 'post'
  })
}

export const list1 = (data) => {
  return axios.request({
    url: '/admin/newsindex/list1',
    data,
    method: 'post'
  })
}

export const delImage = (data) => {
  return axios.request({
    url: '/admin/newsindex/delImage',
    data,
    method: 'post'
  })
}
export const del = (data) => {
  return axios.request({
    url: '/admin/newsindex/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/newsindex/batchDelete',
    data,
    method: 'post'
  })
}

import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/new/list',
    params: paras,
    method: 'get'
  })
}
export const list1 = (paras) => {
  return axios.request({
    url: '/admin/new/list1',
    params: paras,
    method: 'get'
  })
}

export const delImage = (data) => {
  return axios.request({
    url: '/admin/news/delImage',
    data,
    method: 'post'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/new/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/new/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/new/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/new/batchDelete',
    data,
    method: 'post'
  })
}

import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/businessbyid/list',
    params: paras,
    method: 'get'
  })
}

export const list1 = (paras) => {
  return axios.request({
    url: '/admin/businessbyid/list1',
    params: paras,
    method: 'get'
  })
}

export const delImage = (data) => {
  return axios.request({
    url: '/admin/businessbyid/delImage',
    data,
    method: 'post'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/businessbyid/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/businessbyid/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/businessbyid/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/businessbyid/batchDelete',
    data,
    method: 'post'
  })
}

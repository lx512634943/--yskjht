import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/icons/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/icons/save',
    data,
    method: 'post'
  })
}
export const delImage = (data) => {
  return axios.request({
    url: '/admin/icons/delImage',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/icons/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/icons/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/icons/batchDelete',
    data,
    method: 'post'
  })
}

import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/cases/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/cases/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/cases/update',
    data,
    method: 'post'
  })
}
export const delImage = (data) => {
  return axios.request({
    url: '/admin/news/delImage',
    data,
    method: 'post'
  })
}
export const del = (data) => {
  return axios.request({
    url: '/admin/cases/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/cases/batchDelete',
    data,
    method: 'post'
  })
}

import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/slideshow/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/slideshow/save',
    data,
    method: 'post'
  })
}

export const list1 = (data) => {
  return axios.request({
    url: '/admin/slideshow/list1',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/slideshow/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/slideshow/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/slideshow/batchDelete',
    data,
    method: 'post'
  })
}

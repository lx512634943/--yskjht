import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/index/list',
    params: paras,
    method: 'get'
  })
}

export const list1 = (paras) => {
  return axios.request({
    url: '/admin/index/list1',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/index/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/index/update',
    data,
    method: 'post'
  })
}


export const del = (data) => {
  return axios.request({
    url: '/admin/index/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/index/batchDelete',
    data,
    method: 'post'
  })
}

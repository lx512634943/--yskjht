import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/role/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/role/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/role/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/role/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/role/batchDelete',
    data,
    method: 'post'
  })
}

export const popedomListByRoleId = (paras) => {
  return axios.request({
    url: '/admin/role/popedomListByRoleId',
    params: paras,
    method: 'get'
  })
}

export const savePopedom = (data) => {
  return axios.request({
    url: '/admin/role/savePopedom',
    data,
    method: 'post'
  })
}

export const batchSavePopedom = (data) => {
  return axios.request({
    url: '/admin/role/batchSavePopedom',
    data,
    method: 'post'
  })
}

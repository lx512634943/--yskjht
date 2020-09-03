import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/news/list',
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
    url: '/admin/news/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/news/update',
    data,
    method: 'post'
  })
}
export const list1 = (paras) => {
  return axios.request({
    url: '/admin/news/list1',
    params: paras,
    method: 'get'
  })
}


export const del = (data) => {
  return axios.request({
    url: '/admin/news/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/news/batchDelete',
    data,
    method: 'post'
  })
}

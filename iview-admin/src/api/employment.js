                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/employment/list',
    params: paras,
    method: 'get'
  })
}
export const list1 = (paras) => {
  return axios.request({
    url: '/admin/employment/list1',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
  return axios.request({
    url: '/admin/employment/save',
    data,
    method: 'post'
  })
}

export const update = (data) => {
  return axios.request({
    url: '/admin/employment/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/employment/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/employment/batchDelete',
    data,
    method: 'post'
  })
}

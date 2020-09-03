import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/popedom/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
	return axios.request({
		url: '/admin/popedom/save',
		data,
		method: 'post'
	})
}

export const update = (data) => {
  return axios.request({
    url: '/admin/popedom/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/popedom/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/popedom/batchDelete',
    data,
    method: 'post'
  })
}

export const syncPopedom = (data) => {
  return axios.request({
    url: '/admin/popedom/syncPopedom',
    data,
    method: 'post'
  })
}
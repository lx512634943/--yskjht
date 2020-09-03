import axios from '@/libs/api.request'

export const login = ({ userName, password }) => {
  const data = {
    'user.USER_CODE':userName,
    'user.PASSWD':password
  }
  return axios.request({
    url: '/admin/signIn',
    data,
    method: 'post'
  })
}

export const getUserInfo = (token) => {
  return axios.request({
    url: '/admin/get_info',
    params: {
      token
    },
    method: 'get'
  })
}

export const logout = (token) => {
  return axios.request({
    url: '/admin/logOut',
    method: 'post'
  })
}

export const list = (paras) => {
  return axios.request({
    url: '/admin/user/list',
    params: paras,
    method: 'get'
  })
}

export const save = (data) => {
	return axios.request({
		url: '/admin/user/save',
		data,
		method: 'post'
	})
}

export const update = (data) => {
  return axios.request({
    url: '/admin/user/update',
    data,
    method: 'post'
  })
}

export const del = (data) => {
  return axios.request({
    url: '/admin/user/delete',
    data,
    method: 'post'
  })
}

export const batchDelete = (data) => {
  return axios.request({
    url: '/admin/user/batchDelete',
    data,
    method: 'post'
  })
}

export const isDisable = (data) => {
  return axios.request({
    url: '/admin/user/isDisable',
    data,
    method: 'post'
  })
}

export const resetPwd = (data) => {
  return axios.request({
    url: '/admin/user/resetPwd',
    data,
    method: 'post'
  })
}

export const roleListByUserId = (paras) => {
  return axios.request({
    url: '/admin/user/roleListByUserId',
    params: paras,
    method: 'get'
  })
}

export const saveRole = (data) => {
  return axios.request({
    url: '/admin/user/saveRole',
    data,
    method: 'post'
  })
}

export const batchSaveRole = (data) => {
  return axios.request({
    url: '/admin/user/batchSaveRole',
    data,
    method: 'post'
  })
}

export const updatePwd = (data) => {
  return axios.request({
    url: '/admin/user/updatePwd',
    data,
    method: 'post'
  })
}
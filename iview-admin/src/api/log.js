import axios from '@/libs/api.request'

export const list = (paras) => {
  return axios.request({
    url: '/admin/log/list',
    params: paras,
    method: 'get'
  })
}

import axios from '@/libs/api.request'

export const getRouter = (paras) => {
  return axios.request({
    url: '/admin/popedomList',
    params: paras,
    method: 'get'
  })
}

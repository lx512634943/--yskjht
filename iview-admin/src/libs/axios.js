import Axios from 'axios'
import baseURL from '_conf/url'
import { Message, LoadingBar, Modal } from 'iview'
import Cookies from 'js-cookie'
import { TOKEN_KEY } from '@/libs/util'
import router from '@/router'
import store from '@/store'
class httpRequest {
  constructor () {
    this.options = {
      method: '',
      url: ''
    }
    // 存储请求队列
    this.queue = {}
  }
  // 销毁请求实例
  destroy (url) {
    delete this.queue[url]
    const queue = Object.keys(this.queue)
    return queue.length
  }
  // 请求拦截
  interceptors (instance, url) {
    // 添加请求拦截器
    instance.interceptors.request.use(config => {
      // if (!config.url.includes('/users')) {
      //   config.headers['x-access-token'] = Cookies.get(TOKEN_KEY)
      // }
      config.headers['x-requested-with'] = 'XMLHttpRequest' //后台区分请求类型
      config.transformRequest=[function (data) {
          let ret = ''
          for (let it in data) {
            ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
          }
          return ret
      }]

      LoadingBar.start()
      // 在发送请求之前做些什么
      return config
    }, error => {
      // 对请求错误做些什么
      LoadingBar.error()
      return Promise.reject(error)
    })
    // 添加响应拦截器
    instance.interceptors.response.use((res) => {

      let { headers } = res
      if (headers.sessionstatus) {
        if (headers.sessionstatus === 'timeout') {
          //debugger
          store.commit('setToken', '')
          location.href = '/login'
          // router.push({
          //   name: 'login'
          // })
        } else if (headers.sessionstatus === 'nopermission') {
          Message.error('无权限')
          console.log('无权限: '+headers.nourl)
        } else if (headers.sessionstatus === 'relogin') {
          Modal.confirm({
            title: '提示',
            content: '<p>被迫下线,该账户已在其它设备登录, 确定重新登录吗?</p>',
            onOk: () => {
              store.dispatch('handleLogOut').then(() => {
                router.push({
                  name: 'login'
                })
                //本地开发代理时，刷新跳主页，可取消此注释
                //location.reload()
              })
              
            },
            onCancel: () => {}
          });
        }
      }

      let { data } = res
      const is = this.destroy(url)
      if (!is) {
          LoadingBar.finish()
      }
      
      if (data.msg) {
        if (data.state === 'ok') {
          Message.success(data.msg)
          return data
        } else {
          Message.error(data.msg)
          return false
        }
      } else {
        if (data.state === 'ok') {
          Message.success('操作成功')
          return data
        } else if (data.state === 'fail') {
          Message.error('操作失败')
          return false
        } else {
          return data
        }
      }

      // if (data.code !== 200) {
      //   // 后端服务在个别情况下回报201，待确认
      //   if (data.code === 401) {
      //     Cookies.remove(TOKEN_KEY)
      //     window.location.href = window.location.pathname + '#/login'
      //     Message.error('未登录，或登录失效，请登录')
      //   } else {
      //     if (data.msg) Message.error(data.msg)
      //   }
      //   return false
      // }
      return data
    }, (error) => {
      Message.error('服务内部错误')
      // 对响应错误做点什么
      return Promise.reject(error)
    })
  }
  // 创建实例
  create () {
    let conf = {
      baseURL: baseURL,
      // timeout: 2000,
      headers: {
        //'Content-Type': 'application/json; charset=utf-8',
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
        'X-URL-PATH': location.pathname
      }
    }
    return Axios.create(conf)
  }
  // 合并请求实例
  mergeReqest (instances = []) {
    //
  }
  // 请求实例
  request (options) {
    var instance = this.create()
    this.interceptors(instance, options.url)
    options = Object.assign({}, options)
    this.queue[options.url] = instance
    return instance(options)
  }
}
export default httpRequest

import { login, logout,  } from '@/api/user'
import { getRouter } from '@/api/routers'
import { setToken, getToken } from '@/libs/util'

export default {
  state: {
    userName: '',
    userId: '',
    avatorImgPath: '',
    token: getToken()
  },
  mutations: {
    setAvator (state, avatorPath) {
      state.avatorImgPath = avatorPath
    },
    setUserId (state, id) {
      state.userId = id
    },
    setUserName (state, name) {
      state.userName = name
    },
    setToken (state, token) {
      state.token = token
      setToken(token)
    }
  },
  actions: {
    // 登录
    handleLogin ({ commit }, {userName, password}) {
      userName = userName.trim()
      return new Promise((resolve, reject) => {
        login({
          userName,
          password
        }).then(res => {
          commit('setToken', res.userId)
          commit('setUserName', res.userCode)
          commit('setUserId', res.userId)
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    // 退出登录
    handleLogOut ({ state, commit }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('setToken', '')
          resolve()
        }).catch(err => {
          reject(err)
        })
        // 如果你的退出登录无需请求接口，则可以直接使用下面三行代码而无需使用logout调用接口
        // commit('setToken', '')
        // commit('setAccess', [])
        // resolve()
      })
    },
    clearToken({ state, commit }) {
      commit('setToken', '')
    },
    // 获取用户相关信息
    handleGetRouter ({ state, commit }, { userId }) {
      return new Promise((resolve, reject) => {
        getRouter({userId}).then(data => {
          const res = data.user
          commit('setToken', res.userId)
          commit('setUserName', res.userCode)
          commit('setUserId', res.userId)
          resolve(data)
        }).catch(err => {
          reject(err)
        })
      })
    }
  }
}

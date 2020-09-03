<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form @on-success-valid="handleSubmit"></login-form>
          <p class="login-tip">超级管员: (避免体验遭破坏，暂不提供)</p>
          <p class="login-tip">系统管理员: admin, 密码: 000000</p>
          <p class="login-tip">测试用户: test, 密码: 000000</p>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import { mapActions } from 'vuex'
import { initRouter } from '@/libs/util'

export default {
  components: {
    LoginForm
  },
  methods: {
    ...mapActions([
      'handleLogin'
    ]),
    handleSubmit ({ userName, password }) {
      this.handleLogin({ userName, password }).then(res => {
        if (res) {
          initRouter(res.userId).then(router => {
            router.push({
              name: 'home'
            })
          })
        }
      })
    }
  }
}
</script>

<style>

</style>

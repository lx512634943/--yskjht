<template>
  <div class="user-avator-dropdown">
    <Dropdown @on-click="handleClick">
      <Avatar style="color: #fff;background-color: #5cadff">{{username}}</Avatar>
      <Icon :size="18" type="md-arrow-dropdown"></Icon>
      <DropdownMenu slot="list">
        <DropdownItem name="updatePassword">修改密码</DropdownItem>
        <DropdownItem name="logout">退出登录</DropdownItem>
      </DropdownMenu>
    </Dropdown>
    <UpdatePwd v-model="isOpenPwdPage" />
  </div>
</template>

<script>
import './user.less'
import { mapActions } from 'vuex'
import UpdatePwd from './pwd.vue'

export default {
  name: 'User',
  components: {
    UpdatePwd
  },
  props: {
    username: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      isOpenPwdPage: false,
    }
  },
  methods: {
    ...mapActions([
      'handleLogOut'
    ]),
    handleClick (name) {
      switch (name) {
        case 'logout':
          this.handleLogOut().then(() => {
            this.$router.push({
              name: 'login'
            })
            //本地开发代理时，刷新跳主页，可取消此注释
            //location.reload()
          })
          break
        case 'updatePassword':
          this.isOpenPwdPage = true
          break
      }
    }
  }
}
</script>

<template>
	<iframe height="100%" width="100%" :src="baseURL" style="border-width: 0px;"></iframe>
</template>

<script>
import baseURL from '_conf/url'
export default {
  name: 'javamelody',
  data () {
    return {
      baseURL: baseURL+'/monitoring'
    }
  }
}
</script>

<style>

</style>

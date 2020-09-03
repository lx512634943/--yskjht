<template>
    <iframe id="druid" height="100%" width="100%" :src="baseURL" style="border-width: 0px;"></iframe>
</template>

<script>
import baseURL from "_conf/url";
export default {
  name: "druid",
  data() {
    return {
      baseURL: baseURL + "/assets/druid/index.html"
    };
  },
  mounted: function() {
    window.druid.onload = () => {
      window.druid.contentWindow.document.getElementsByTagName("footer")[0].style.display = "none";
    }
  }
};
</script>

<style>
</style>

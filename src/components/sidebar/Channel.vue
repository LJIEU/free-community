<template>
  <!-- 频道 -->
  <div class="ui segments m-box">
    <div class="ui secondary segment"><i class="bookmark icon"></i>频道</div>
  </div>
</template>

<script>
export default {
  name: "Channel",
  props: {
    /*随机频道列表*/
    randomChannelList: {
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>

</style>

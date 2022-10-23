<template>
  <div>
    <h1>我的主页</h1>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  created() {
    if (!window.localStorage.getItem('jwtToken')) {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

</style>

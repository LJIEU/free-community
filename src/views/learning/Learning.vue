<template>
  <div>
    <h1>学习模块</h1>
    <div>
      <el-input rows="3" v-model="comment" type="textarea" placeholder="输入您的想法...."
                style="width: 100%; margin-bottom: 10px"></el-input>
    </div>
  </div>
</template>

<script>
export default {
  name: "Learning",
  data(){
    return{
      a: 1,
      comment: ''
    }
  }
}
</script>

<style scoped>

</style>

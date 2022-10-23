<template>
  <div>
    <div>
      <div class="ui padded segment ">
        <el-menu class="el-menu-demo" mode="horizontal">
          <el-menu-item index="1">
            <router-link to="/" class="item" style="font-size: 20px; font-weight: bolder">推荐</router-link>
          </el-menu-item>
          <el-menu-item index="2">
            <router-link to="/hot-list" class="item" style="font-size: 20px; font-weight: bolder">热榜</router-link>
          </el-menu-item>
          <el-menu-item index="3">
            <router-link to="/video" class="item" style="font-size: 20px; font-weight: bolder">视频</router-link>
          </el-menu-item>
        </el-menu>
        <!--  将getForumList 和 forumList totalPage 给子组件 -->
      </div>
      <div>视频模块</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Video"
}
</script>

<style scoped>

</style>

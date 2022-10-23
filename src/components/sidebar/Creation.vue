<template>
  <!-- 创作中心 -->
  <div class="ui segments m-box">
    <!--  头部  -->
    <div class="ui secondary segment">
      <i class="el-icon-s-custom icon"></i>创作中心
      <a target="_blank" href="#" style="float: right">草稿箱<span>{{ message }}</span>
      </a>
    </div>

    <!--  中间部分  -->
    <!--  style="margin: 0 15px" -->
    <div class="ui secondary segment">
      <!--     <icon-svg name="test"></icon-svg>-->
      <!--      <svg><use xlink:href="#test"></use></svg>-->
      <!--      <svg><use xlink:href="#one"></use></svg>-->
      <!--   icon-class=> 就是文件名   -->
      <!--      <svg-icon icon-class="icon-admin" class-name="card-panel-icon" />-->
      <el-row class="my-span" :gutter="80">
        <el-col :span="5">
          <router-link to="/question" class="item">
            <el-button class="my-button" circle>
              <svg-icon icon-class="icon-question" class="my-svg-icon"/>
            </el-button>
            <span style="margin-left: 0">回答问题</span>
          </router-link>
        </el-col>
        <el-col :span="5">
          <el-button class="my-button" circle>
            <svg-icon icon-class="icon-video" class="my-svg-icon"/>
          </el-button>
          <span>发视频</span>
        </el-col>
        <el-col :span="5">
          <router-link to="/write" target="_blank">
            <el-button class="my-button" circle>
              <svg-icon icon-class="icon-article" class="my-svg-icon"/>
            </el-button>
            <span>写文章</span>
          </router-link>
        </el-col>
        <el-col :span="5">
          <el-button class="my-button" circle>
            <svg-icon icon-class="icon-idea" class="my-svg-icon"/>
          </el-button>
          <span>写想法</span>
        </el-col>
      </el-row>
      <!--
            <el-row style="margin-top: 6px; font-size: 6px">
              <span>回答问题</span>
              <span style="margin-left: 40px">发视频</span>
              <span style="margin-left: 40px">写文章</span>
              <span style="margin-left: 45px">写想法</span>
            </el-row>
      -->
    </div>
  </div>
</template>

<script>
export default {
  name: "Creation",
  data() {
    return {
      message: "(0)"
    }
  }
}
</script>

<style scoped>
.my-svg-icon {
  width: 25px;
  height: 25px;
}

.my-span span {
  margin: 5px 5px;
  white-space: nowrap;
  overflow: visible
}

.my-span .my-button {
  margin-bottom: 5px
}
</style>

<template>
  <div class="m-box">
    <el-row :gutter="14">
      <el-col :span="8" style="width: 12.5%">
        <el-card shadow="always" style="height: 150px">
          <span class="jump-font">帖子管理</span>
          <router-link to="/app-management/post">
            <icon-svg name="post-management" class="jump-svg">
            </icon-svg>
          </router-link>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 12.5%">
        <el-card shadow="always" style="height: 150px">
          <span class="jump-font">圈子管理</span>
          <router-link to="/app-management/circle">
            <icon-svg name="circle-management" class="jump-svg">
            </icon-svg>
          </router-link>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 12.5%">
        <el-card shadow="always" style="height: 150px">
          <span class="jump-font">评论管理</span>
          <router-link to="/app-management/comment">
            <icon-svg name="comment-management" class="jump-svg">
            </icon-svg>
          </router-link>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 12.5%">
        <el-card shadow="always" style="height: 150px">
          <span class="jump-font">分类管理</span>
          <router-link to="/app-management/category">
            <icon-svg name="category-management" class="jump-svg">
            </icon-svg>
          </router-link>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 12.5%">
        <el-card shadow="always" style="height: 150px">
          <span class="jump-font">会员管理</span>
          <router-link to="/app-management/vip">
            <icon-svg name="vip-management" class="jump-svg">
            </icon-svg>
          </router-link>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 12.5%">
        <el-card shadow="always" style="height: 150px">
          <span class="jump-font">消息管理</span>
          <router-link to="/app-management/message">
            <icon-svg name="message-management" class="jump-svg">
            </icon-svg>
          </router-link>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 12.5%">
        <el-card shadow="always" style="height: 150px">
          <span class="jump-font">配置管理</span>
          <router-link to="/app-management/config">
            <icon-svg name="config-management" class="jump-svg">
            </icon-svg>
          </router-link>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 12.5%">
        <el-card shadow="always" style="height: 150px">
          <span class="jump-font">轮播管理</span>
          <router-link to="/app-management/carousel">
            <icon-svg name="carousel-management" class="jump-svg">
            </icon-svg>
          </router-link>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Jump'
}
</script>

<style scoped>
.jump-font {
  margin: 0;
  padding-top: 25px;
  font-size: 20px;
  writing-mode: vertical-lr;
}

.jump-svg {
  width: 150px;
  height: 100px;
  padding-left: 25px;
  margin-top: -85px
}
</style>

<template>
  <div>
    <div>
      <div>
        <!--        <div v-show="$route.name==='home'
                || $route.name==='hot-list'
                || $route.name==='video'"
                     class="ui padded segment ">-->
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
      </div>
    </div>
    <div class="line"/>
    <!--  论坛推荐列表  -->
    <!--    <ForumList
            v-show="$route.name==='home'"
            :getForumList="getForumList" :forumList="forumList" :totalPage="totalPage"/> -->
    <ForumList
        v-show="$route.name==='home'"/>


    <!-- 以下作废方案   -->
<!--
    <Article v-show="$route.name==='article'"/>
-->

    <!--  论坛热度榜块  -->
    <HotList v-show="$route.name==='hot-list'"/>

    <!--  论坛视频模块  -->
    <Video v-show="$route.name==='video'"/>
  </div>
</template>

<script>
import ForumList from "@/components/forum/ForumList";
import HotList from "@/views/hotlist/HotList";
import Video from "@/views/video/Video";

export default {
  name: "ForumHome",
  components: {Video, HotList, ForumList},
  data() {
    return {
      forumList: [],
      totalPage: 0,
      getForumListFinish: false
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (from.name !== 'forum') {
        // 其他页面跳转首页 重新获取数据
        // 设置一个flag 让首页的分页组件指向正确的页码
        /*        vm.$store.commit('setIsForumToHome', false)
                vm.getForumList()*/
      } else {
        //如果文章页面是起始访问页，首页将是第一次进入，即缓存不存在，要请求数据
        if (!vm.getForumListFinish) {
          // vm.getForumList()
          console.log(vm)
        }
        // 从文章页面跳转到首页时,使用首页缓存
        vm.$store.commit('setIsForumToHome', true)
      }
    })
  },
  methods: {
    /*    getForumList() {
          // 向服务器获取请求
          getForumList().then(response => {
            console.log('获取帖子列表:', response);
            if (response && response.code === 200) {
              this.forumList = response.data.forumList
            }
          })
        }*/
  }
}
</script>

<style scoped>

</style>

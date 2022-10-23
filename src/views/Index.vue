<template>
  <div class="site">
    <!--顶部导航-->
    <Nav :forum-name="siteInfo.forumName" :categoryList="categoryList"/>
    <div class="main" :class="{'indexHome':
    $route.name==='home'||$route.name==='video'||$route.name==='hot-list'}"
         style="z-index: 100">
      <div class="m-padded-tb-big">
        <div class="ui container">
          <div class="ui stackable grid">
            <!--    16格子宽度        -->
            <!--左侧-->
            <div class="three wide column m-mobile-hide">
            </div>
            <!--中间-->
            <div class="nine wide column">
              <keep-alive include="ForumHome">
                <router-view/>
              </keep-alive>
            </div>
            <!--右侧-->
            <div class="four wide column m-mobile-hide"
                 v-show="$route.name==='home'
                       ||$route.name==='hot-list'
                       ||$route.name==='video'
                       ||$route.name!=='write'">
              <Creation/>
              <!--<RandomBlog :randomBlogList="randomBlogList" :class="{'m-display-none':focusMode}"/>-->
              频道
<!--              <Channel :randomChannelList="randomChannelList" :class="{'m-display-none':focusMode}"/>-->
              <Channel :randomChannelList="randomChannelList"/>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--首页大图 只在首页且pc端时显示-->
    <div class="m-mobile-hide">
      <Header v-show="$route.name==='home'
      ||$route.name==='hot-list'
      ||$route.name==='video'"/>
    </div>

    <!--回到顶部-->
    <el-backtop style="box-shadow: none;background: none;z-index: 9999;">
      <img src="/img/paper-plane.png" style="width: 40px;height: 40px;">
    </el-backtop>

    <!--底部footer-->
    <Footer style="bottom: 0" :siteInfo="siteInfo" :badges="badges"/>
  </div>
</template>

<script>
import Nav from "@/components/index/Nav";
import Header from "@/components/index/Header";
import Footer from "@/components/index/Footer";
import {mapState} from 'vuex'
import Channel from "@/components/sidebar/Channel";
import Creation from "@/components/sidebar/Creation";
import ForumHome from "@/views/home/ForumHome";
import axios from "@/plugins/axios";

export default {
  name: "Index",
  components: {
    ForumHome,
    Creation,
    Channel, Header, Nav, Footer
  },
  data() {
    return {
      siteInfo: {
        forumName: 'JIE'
      },
      categoryList: [],
      tagList: [],
      randomBlogList: [],
      randomChannelList: [], /*随机频道*/
      badges: [],
      newBlogList: [],
      hitokoto: {},
    }
  },
  computed: {
    // ...mapState(['focusMode'])
  },
  watch: {
    //路由改变时，页面滚动至顶部
    /*    '$route.path'() {
          this.scrollToTop()
        }*/
  },
  created() {
    var verifyVo = {
      jwtToken: window.localStorage.getItem('jwtToken'),
      username: window.localStorage.getItem('username')
    }
    // 验证登录是否过期
    axios({
      url: 'app/api/verify',
      method: 'POST',
      data: JSON.stringify(verifyVo),
      dataType: 'json',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
      }
    }).then(response => {
      console.log('验证结果:', response);
      if (response.code === 403) {
        window.localStorage.removeItem('jwtToken')
        window.localStorage.removeItem('username')
        this.$message({
          type: "error",
          message: '重新登录!'
        });
        this.$router.push('/login')
      }
    });
  },
  mounted() {
/*    //保存可视窗口大小
    this.$store.commit(SAVE_CLIENT_SIZE, {
      clientHeight: document.body.clientHeight,
      clientWidth: document.body.clientWidth
    })
    window.onresize = () => {
      this.$store.commit(SAVE_CLIENT_SIZE, {
        clientHeight: document.body.clientHeight,
        clientWidth: document.body.clientWidth
      })
    }*/
  },
  methods: {

  }
}
</script>

<style scoped>
.indexHome {
  margin-top: 37.3% !important;
}

.site {
  display: flex;
  min-height: 100vh; /* 没有元素时，也把页面撑开至100% */
  flex-direction: column;
}

.main {
  margin-top: 40px;
  flex: 1;
}

.main .ui.container {
  width: 1400px !important;
  margin-left: auto !important;
  margin-right: auto !important;
}

.ui.grid .three.column {
  padding: 0;
}

.ui.grid .ten.column {
  padding-top: 0;
}

.m-display-none {
  display: none !important;
}
</style>

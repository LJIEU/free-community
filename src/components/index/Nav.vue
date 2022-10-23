<template>
  <div id="classList" ref="nav" class="ui fixed inverted stackable pointing menu"
       :class="{'transparent':$route.name==='home' && clientSize.clientWidth>768/*,
       'backgroundChange':$route.name==='homePage' && clientSize.clientWidth>768*/}">
    <div class="ui container">
      <router-link to="/">
<!--        <h3 class="ui header item m-blue">{{ forumName }}</h3>-->
        <h3 class="ui header item m-blue">{{ user.name }}</h3>
        <!--        <el-image class="ui header item m-logo" src="/logo.png"/>-->
      </router-link>
      <router-link to="/home" class="item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='home'}">
        <i class="home icon"></i>首页
      </router-link>
      <router-link to="/learning" class="item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='learning'}">
        <i class="book icon"></i>学习
      </router-link>
      <router-link to="/find" class="item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='find'}">
        <i class="find icon"></i>发现
      </router-link>
      <router-link to="/question" class="item" :class="{'m-mobile-hide': mobileHide,'active':$route.name==='question'}">
        <i class="question icon"></i>你问我答
      </router-link>

      <div style="margin-left: 20px;"></div>
      <!--   搜索框   -->
      <el-autocomplete v-model="queryString" :fetch-suggestions="debounceQuery" placeholder="查询..."
                       style="margin: 8px 5px; width: 28%; border: 1px solid rgba(255, 255, 255, .3); border-radius: 100px"
                       class="center item m-search searchClass" :class="{'m-mobile-hide': mobileHide}"
                       popper-class="m-search-item" @select="handleSelect">
        <i class="search icon el-input__icon" slot="suffix"></i>
        <template slot-scope="{ item }">
          <div class="title">{{ item.title }}</div>
          <span class="content">{{ item.content }}</span>
        </template>
      </el-autocomplete>


      <div style="margin-right: 25%;"></div>
      <!--  消息    -->
      <router-link to="/information" class="item"
                   :class="{'m-mobile-hide': mobileHide,'active':$route.name==='information'}">
        <i class="bell icon"></i>消息
      </router-link>
      <!--  私信    -->
      <router-link to="/private-letter" class="item"
                   :class="{'m-mobile-hide': mobileHide,'active':$route.name==='private-letter'}">
        <i class="chat icon"></i>私信
      </router-link>

      <!--   我的   -->
      <div class="avatar" style="padding:0px">
        <!--        <el-dropdown trigger="click" @command="categoryRoute">-->
        <el-dropdown trigger="click">
          <el-avatar class="my-nav-avatar" style="margin: 10px" shape="square" :src="user.avatarURL" :alt="user.name"/>

          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>
              <router-link to="/homePage">
                我的主页
              </router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/home">
                设置
              </router-link>
            </el-dropdown-item>
            <el-dropdown-item @click.native="myQuit">
              退出
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>

      <!--  侧边栏   -->
      <button class="ui menu black icon button m-right-top m-mobile-show" @click="toggle">
        <i class="sidebar icon"></i>
      </button>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import {getUser} from "@/api/user/login";

export default {
  name: "Nav",
/*  props: {
    forumName: {
      type: String,
      required: true
    },
    categoryList: {
      type: Array,
      required: true
    },
  },*/
  data() {
    return {
      mobileHide: true,
      queryString: '',
      queryResult: [],
      timer: null,
      user: {
        avatarURL: '/img/avatar.png',
        name: 'JIE'
        // avatarURL: '~@/assets/img/avatar.png'
      }
    }
  },
  computed: {
    ...mapState(['clientSize'])
  },
  watch: {
    //路由改变时，收起导航栏
    '$route.path'() {
      this.mobileHide = true
    }
  },
  created() {
    /*到顶部*/
    this.scrollToTop();

    if (window.localStorage.getItem('jwtToken')) {
      this.getUserInfo()
    }
  },
  mounted() {
    //监听页面滚动位置，改变导航栏的显示
    window.addEventListener('scroll', () => {
      //首页且不是移动端
      if (this.$route.name === 'home' && this.clientSize.clientWidth > 768) {
        if (window.scrollY > this.clientSize.clientHeight / 2) {
          this.$refs.nav.classList.remove('transparent')
        } else {
          this.$refs.nav.classList.add('transparent')
        }
      }
    })
    //监听点击事件，收起导航菜单
    document.addEventListener('click', (e) => {
      //遍历冒泡
      let flag = e.path.some(item => {
        if (item === this.$refs.nav)
          return true
      })
      //如果导航栏是打开状态，且点击的元素不是Nav的子元素，则收起菜单
      if (!this.mobileHide && !flag) {
        this.mobileHide = true
      }
    })
  },
  methods: {
    toggle() {
      this.mobileHide = !this.mobileHide
    },
    categoryRoute(name) {
      this.$router.push(`/category/${name}`)
    },
    /* 数据查询 */
    debounceQuery(queryString, callback) {
      this.timer && clearTimeout(this.timer)
      this.timer = setTimeout(() => this.querySearchAsync(queryString, callback), 1000)
    },
    querySearchAsync(queryString, callback) {
      if (queryString == null
          || queryString.trim() === ''
          || queryString.indexOf('%') !== -1
          || queryString.indexOf('_') !== -1
          || queryString.indexOf('[') !== -1
          || queryString.indexOf('#') !== -1
          || queryString.indexOf('*') !== -1
          || queryString.trim().length > 20) {
        return
      }
      getSearchBlogList(queryString).then(res => {
        if (res.code === 200) {
          this.queryResult = res.data
          if (this.queryResult.length === 0) {
            this.queryResult.push({title: '无相关搜索结果'})
          }
          callback(this.queryResult)
        }
      }).catch(() => {
        this.msgError("请求失败")
      })
    },
    handleSelect(item) {
      if (item.id) {
        this.$router.push(`/blog/${item.id}`)
      }
    },
    // 退出
    myQuit() {
      // window.localStorage.setItem('jwtToken', null)
      window.localStorage.removeItem('jwtToken')
      this.$message.success('退出成功~')
      this.$router.go(0) // 刷新
    },
    // 获取用户信息
    getUserInfo() {
      let name = window.localStorage.getItem('username');
      // console.log(name);
      getUser(name).then(response => {
        console.log(response)
        this.user.avatarURL = response.data.userInfo.avatar
        this.user.name = response.data.userInfo.username
      })
    }
  }
}
</script>

<style>
.ui.fixed.menu .container {
  width: 1400px !important;
  margin-left: auto !important;
  margin-right: auto !important;
}

.ui.fixed.menu {
  transition: .3s ease-out;
}

.ui.inverted.pointing.menu.transparent {
  background: transparent !important;
}

.ui.inverted.pointing.menu.transparent .active.item:after {
  background: transparent !important;
  transition: .3s ease-out;
}

.ui.inverted.pointing.menu.transparent .active.item:hover:after {
  background: rgba(255, 255, 255, .6) !important;
}

.ui.inverted.pointing.menu.backgroundChange {
  background: rgba(255, 255, 255, .6) !important;
}

.ui.inverted.pointing.menu.backgroundChange .active.item:after {
  background: rgba(255, 255, 255, .6) !important;
  transition: .3s ease-out;
}

.ui.inverted.pointing.menu.backgroundChange .active.item:hover:after {
  background: transparent !important;
}

.el-dropdown-link {
  outline-style: none !important;
  outline-color: unset !important;
  height: 100%;
  cursor: pointer;
}

.el-dropdown-menu {
  margin: 7px 0 0 0 !important;
  padding: 0 !important;
  border: 0 !important;
  background: #1b1c1d !important;
}

.el-dropdown-menu__item {
  padding: 0 15px !important;
  color: rgba(255, 255, 255, .9) !important;
}

.el-dropdown-menu__item:hover {
  background: rgba(255, 255, 255, .08) !important;
}

.el-popper .popper__arrow::after {
  content: none !important;
}

.popper__arrow {
  display: none !important;
}

.m-search {
  min-width: 220px;
  padding: 0 !important;
}

.m-search input {
  color: rgba(255, 255, 255, .9);;
  border: 0px !important;
  background-color: inherit;
  padding: .67857143em 2.1em .67857143em 1em;
}

.m-search i {
  color: rgba(255, 255, 255, .9) !important;
}

.m-search-item {
  min-width: 350px !important;
}

.m-search-item li {
  line-height: normal !important;
  padding: 8px 10px !important;
}

.m-search-item li .title {
  text-overflow: ellipsis;
  overflow: hidden;
  color: rgba(0, 0, 0, 0.87);
}

.m-search-item li .content {
  text-overflow: ellipsis;
  font-size: 12px;
  color: rgba(0, 0, 0, .70);
}

.my-nav-avatar:hover {
  cursor: pointer; /*当鼠标在文本上时将光标I 改成手型*/
}
</style>

<template>
  <div class="login_container">
    <div class="login_box" :class="{'login_box2':!isLogin}" style="background-color: rgba(255, 255, 255, 0.6)">
      <div class="login-title">
        <div @click="loginOrRegis">{{ title }}</div>
      </div>

      <Register v-if="!isLogin" @setLoginForm="setLoginForm(arguments)"></Register>
      <Login v-else :loginForm="loginForm"></Login>
      <!--头像-->
      <!--      <div class="avatar_box" >
                      <img src="/img/avatar.png" alt="">
            </div>-->
      <!--登录表单-->
    </div>
  </div>
</template>

<script>
import Register from "@/views/login/Register";
import Login from "@/views/login/Login";

export default {
  name: "LoginOrRegister",
  components: {Register, Login},
  data() {
    return {
      loginForm: {
        username: 'user',
        password: '123456'
      },
      isLogin: true,
      title: '登录',
    }
  },
  beforeRouteEnter(to, from, next) {
    console.log('路由信息:', to, from)
    // this.fromURL = from.path
    next(vm => {
      vm.fromURL = from.path
      console.log(vm.fromURL);
      if (window.localStorage.getItem('jwtToken')) {
        vm.$router.push(from.path)
      }
    })
  },
  methods: {
    setLoginForm(params) {
      this.isLogin = params[0]
      this.title = '登录'
      console.log(params)
      this.loginForm.username = params[1];
      this.loginForm.password = params[2]
    },
    loginOrRegis() {
      console.log(this.title, '\t', this.isLogin)
      if (this.title === '登录') {
        this.isLogin = false
        this.title = '注册'
      } else {
        this.isLogin = true
        this.title = '登录'
      }
    },
  }
}
</script>

<style scoped>
.login_container {
  box-sizing: unset !important;
  height: 100%;
  width: 100%;
  /*background-image: url("/img/bg_image2.jpg");*/
  background-image: url("/img/bg_image1.png");
  background-repeat: no-repeat; /*不重复*/
  background-attachment: fixed; /*页面固定*/
  background-size: cover; /*覆盖全屏*/
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.login_box2 {
  width: 450px;
  height: 450px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.login_box .avatar_box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  /*border-radius: 50%;*/
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
}

.login_box .avatar_box img {
  width: 100%;
  height: 100%;
  /*border-radius: 50%;*/
  background-color: #eee;
}


.login-title {
  position: absolute;
  margin-top: -10px;
  margin-left: 170px;
  font-size: 60px;
  font-weight: bolder;
  text-align: center;
  text-transform: uppercase;
  color: white;
  text-shadow: 0px 1px 0px #c0c0c0,
  0px 2px 0px #b0b0b0,
  0px 3px 0px #a0a0a0,
  0px 4px 0px #909090,
  0px 5px 10px rgba(0, 0, 0, .9);
}

.login-title:hover {
  cursor: pointer;
  margin-left: 165px;
  font-size: 70px;
}
</style>

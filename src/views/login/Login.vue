<template>
  <el-form ref="loginFormRef" status-icon :model="loginForm" :rules="loginFormRules" class="login_form">
    <el-form-item prop="username">
      <el-input v-model="loginForm.username"
                prefix-icon="el-icon-user-solid"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" show-password
                @keyup.native.enter="login"></el-input>
    </el-form-item>
    <el-form-item label="" prop="">
      <!--     滑动验证 npm install vue-puzzle-vcode --save     -->
      <el-button @click="submit">开始验证</el-button>
      <Vcode :show="isShow"
             @success="onSuccess"
             @close="onClose"
             successText="验证通过!"
             failText="验证失败请重试!"
             sliderText="拖动滑块完成拼图" :imgs="[Img1,Img2]"/>
    </el-form-item>
    <el-form-item class="btns">
      <el-button :disabled="deviation===0" type="primary" @click="login">登录</el-button>
      <el-button type="info" @click="resetLoginForm">重置</el-button>
    </el-form-item>
  </el-form>

</template>

<script>
import {login} from "@/api/user/login";
import Vcode from "vue-puzzle-vcode";
import Img1 from "@/assets/img/1.jpg"
import Img2 from "@/assets/img/2.jpg"

export default {
  components: {
    Vcode
  },
  props: {
    loginForm: {
      type: Object,
      required: true
    }
  },
  name: "Login",
  data() {
    return {
      Img1, Img2,
      deviation: 0,
      isShow: false,
      loginFormRules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ]
      },
      fromURL: '/home'
    }
  },
  methods: {
    onClose() {
      this.isShow = false;
    },
    onSuccess(message) {
      console.log('偏差值:', message)
      this.deviation = message;
      this.isShow = false; // 通过验证后关闭
    },
    submit() {
      this.isShow = true;
    },
    resetLoginForm() {
      this.$refs.loginFormRef.resetFields();
    },
    login() {
      this.$refs.loginFormRef.validate(valid => {
        if (valid) {
          this.deviation = 0;
          login(this.loginForm).then(res => {
            console.log('登录信息:', res);
            if (res.code === 200) {
              this.msgSuccess(res.message)
              // window.localStorage.setItem('adminToken', res.data.token)
              window.localStorage.setItem('jwtToken', res.data.jwt_token);
              window.localStorage.setItem('username', this.loginForm.username)
              console.log('获取Token:', window.localStorage.getItem('jwtToken'));
              // this.$router.push('/home')
              this.$router.push(this.fromURL) // 跳转位置
            } else {
              this.msgError(res.message)
            }
          }).catch(() => {
            this.msgError("请求失败!" + res.message);
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: flex-end;
}

.text-malfunction {
  position: absolute;
  top: 10%;
  left: 51.5%;
  transform: translate(-50%, -50%) scale(2.5);
  width: 220px;
  font-size: 34px;
  font-family: sans-serif;
  color: transparent;
}

</style>

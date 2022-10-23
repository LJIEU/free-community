<template>
  <div style="margin-top: 50px">
    <el-form status-icon :model="formData" mstatus-icon :rules="rules" ref="formData" label-width="100px">
      <!-- ref:表单验证(和提交数据相同) -->
      <el-form-item label="用户名：" prop="username">  <!-- prop的值与rules里面要验证的字段值是对应 -->
        <el-input
            prefix-icon="el-icon-user" style="width: 75%"
            type="text" v-model="formData.username" autocomplete="off">
        </el-input>
      </el-form-item>
      <el-form-item label="密 码：" prop="password1">
        <el-input
            prefix-icon="el-icon-menu"
            style="width: 75%" show-password type="password"
            v-model="formData.password1" autocomplete="off">
        </el-input>
      </el-form-item>
      <el-form-item label="确认密码：" prop="password2">
        <el-input
            prefix-icon="el-icon-menu"
            style="width: 75%" show-password type="password"
            v-model="formData.password2" autocomplete="off">
        </el-input>
      </el-form-item>
      <!--    <el-form-item label="邮箱" prop="email">  &lt;!&ndash; prop的值与rules里面要验证的字段值是对应 &ndash;&gt;
            <el-input
                prefix-icon="el-icon-paperclip" style="width: 75%"
                type="text" v-model="formData.email" autocomplete="off">
            </el-input>
          </el-form-item>-->
      <el-row>
        <el-col :span="18">
          <el-form-item label="手机号" prop="phone">  <!-- prop的值与rules里面要验证的字段值是对应 -->
            <el-input
                prefix-icon="el-icon-paperclip"
                type="text" v-model="formData.phone" autocomplete="off">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-button
              v-show="!showTime"
              style="margin: 5px 4px;" :disabled="!this.formData.phone.match(/^[1][3,4,5,7,8][0-9]{9}$/)"
              size="mini" @click="getCaptcha">获取验证码
          </el-button>
          <el-button
              v-show="showTime"
              style="margin: 5px 4px;" :disabled="!this.formData.phone.match(/^[1][3,4,5,7,8][0-9]{9}$/)"
              size="mini" @click="getCaptcha">{{ sendTime }}秒
          </el-button>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="验证码" prop="code">  <!-- prop的值与rules里面要验证的字段值是对应 -->
            <el-input
                maxlength="6"
                prefix-icon=""
                type="text" v-model="formData.code" autocomplete="off">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <!--          <img :src="captchaPath" @click="getCaptcha()" alt="">-->
          <img style="margin: 2px 10px; width: 60%;" :src="captchaPath" alt="">
        </el-col>
      </el-row>


      <el-form-item>
        <el-button type="primary" @click="register()" style="margin-right: 100px">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>


import {getUser} from "@/api/user/login";
import axios from "@/plugins/axios";

export default {
  name: 'Register',
  data() {
    return {
      captchaPath: '', // 验证码图片
      validityCode: false,
      showTime: false, // 显示倒计时
      sendTime: '',
      timer: null,
      //用户信息
      formData: {
        username: '111',
        password1: '111111',
        password2: '111111',
        phone: '14717477909',
        code: ''
      },
      //两次密码效验
      pas: false,
      //表单规则
      rules: {
        username: [{required: true, message: '用户名不能为空', trigger: 'blur'},
          {min: 2, max: 80, message: '用户名不能小于2位,，大于80位', trigger: 'blur'},
          {
            validator: (rule, value, callback) => {
              if (value.length > 2 && value.length < 80) {
                getUser(value).then((response) => {
                  console.log('创建用户:', value, response)
                  if (response && response.code === 200 && response.data.userInfo != null) {
                    return callback(new Error('该用户已存在!'));
                  } else {
                    return callback();
                  }
                })
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ],  //blur失去焦点
        password1: [{required: true, message: '密码不能为空', trigger: 'blur'},
          {pattern: /^[ a-zA-Z0-9]{6,30}$/, message: '密码不能小于6位,不能大于30位,无特殊字符', trigger: 'blur'},
        ],
        password2: [{required: true, message: '密码不能为空', trigger: 'blur'},
          {
            validator: (rule, value, callback) => {
              if (value === '') {
                callback(new Error('请再次输入密码'));
              } else if (value !== this.$data.formData.password1) {
                callback(new Error('两次输入密码不一致!'));
              } else {
                callback();
              }
            }, trigger: 'blur'
          }],
        /*        email: [{required: true, message: '邮箱不能为空', trigger: 'blur'},
                  {
                    pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/,
                    message: '请输入正确的邮箱',
                    trigger: 'blur'
                  },
                ],  //blur失去焦点*/
        phone: [{required: true, message: '手机不能为空', trigger: 'blur'},
          {
            pattern: /^[1][3,4,5,7,8][0-9]{9}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          },
        ],  //blur失去焦点
        code: [{required: true, message: '验证码不能为空', trigger: 'blur'},
          {
            validator: (rule, value, callback) => {
              // 验证验证码是否正确
              if (value.length !== 6) {
                callback(new Error('验证码长度为6位'))
              } else {
                callback();
              }
            }, trigger: 'blur'
          }]
      },
      isExist: false //判断用户名是否存在
    }
  },
  methods: {
    getCaptcha() {
      console.log('获取验证码')
      // 发送手机号给后端 让后端通过手机号去获取验证码给对应的手机号发送保存验证码
      this.captchaPath = `http://127.0.0.1:8888/app/api/getVerifyCode/${this.formData.phone}`;
      // axios({
      //   method: "GET",
      //   url: `/app/api/getVerifyCode/${this.formData.phone}`,
      // })
      // 倒计时
      const timeCount = 60;
      if (!this.timer) {
        this.sendTime = timeCount;
        this.showTime = true;
        this.timer = setInterval(() => {
          if (this.sendTime > 0 && this.sendTime <= timeCount) {
            this.sendTime--;
          } else {
            this.showTime = false;
            clearInterval(this.timer); // 清除定时器
            this.timer = null;
          }
        }, 1000)
      }
    },
    //注册注册
    register() {
      console.log('注册中!!!')
      this.$refs.formData.validate((valid) => {
        if (valid) {
          const register = {
            username: this.formData.username,
            password: this.formData.password2,
            phone: this.formData.phone,
            code: this.formData.code
          };
          axios({
            method: "POST",
            url: "/app/api/register",
            data: JSON.stringify(register),
            dataType: 'json',
            headers: {
              'Content-Type': 'application/json;charset=UTF-8'
            }
          }).then((response) => {
            console.log('注册结果:', response)

            if (response.code === 200) {
              this.$message.success({
                message: '注册成功',
                type: 'success'
              });
              // 返回登录
              setTimeout(() => {
                // window.alert('跳转界面!')
                // location.href = "/login";
                // 回显
                this.$emit('setLoginForm', true, register.username, register.password)
              }, 1500);

            } else {
              this.$message.error(response.message);
            }
          });
        } else {
          this.$message.error('此提交不符合规则');
          return false;
        }
      });
    },
  }
}
</script>

<style>

</style>

<template>
  <div>
    <!-- 文章标题 -->
    <div class="article-title-container">
      <el-input v-model="article.articleTitle" size="medium" placeholder="输入文章标题"/>
      <el-button
        type="danger"
        size="medium"
        class="save-btn"
        @click="saveArticleDraft"
        v-if="article.id == null || article.status === 3">
        保存草稿
      </el-button>
      <el-button type="danger" size="medium" @click="openModel" style="margin-left: 10px"> 发布文章</el-button>
    </div>


    <mavon-editor v-model="content"
                  ref="md"
                  @change="change"
                  :ishljs="true"
                  @imgAdd="uploadImg"/>
  </div>
</template>
<script>
/* 安装 mavon-editor=> npm install mavon-editor --s */
// 引入组件  及其 样式css
import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import * as imageConversion from 'image-conversion'
import moment from 'moment' // 引入库 npm install moment --save
import Vue from 'vue'
// import axios from 'axios'
// Vue.use(mavonEditor)
Vue.prototype.$moment = moment
export default {
  name: 'articles',
  components: {
    mavonEditor
  },
  // content:输入的markdown
  // html：及时转的html
  data () {
    return {
      content: '',
      html: '',
      headers: {token: sessionStorage.getItem('token')},
      article: {
        id: null,
        articleTitle: this.$moment().format('YYYY-MM-DD'),
        articleContent: '',
        articleCover: '',
        categoryName: null,
        tagNames: [],
        originalUrl: '',
        isTop: 0,
        type: 1,
        status: 1
      }
    }
  },
  methods: {
    // 所有操作都会被解析重新渲染
    change (value, render) {
      // render 为 markdown 解析后的结果[html]
      this.html = render
    },
    // 提交
    submit () {
      console.log(this.content)
      console.log(this.html)
    },
    // 文件上传
    uploadImg (pos, file) {
      // console.log('文件上传数量:', pos)
      // console.log('文件信息:', file)
      // console.log('文件大小:', file.size)
      // console.log('文件类型:', file.type)
      // 第一步.将图片上传到服务器.
      let formData = new FormData()
      console.log(formData.get('file'))
      if (file.size < (1024 * 200)) {
        formData.append('file', file)
        this.$http({
          url: this.$http.adornUrl('/upload?module=articles'),
          method: 'post',
          // data: this.$http.adornData(formData, false)
          data: formData,
          headers: {'Content-Type': 'multipart/form-data'}
        })        /*       axios({
                // 上传
                url: 'http://localhost:8888/upload?module=articles',
                method: 'post',
                data: formdata,
                headers: { 'Content-Type': 'multipart/form-data' }
              }) */.then(response => {
          // console.log('返回结果:', response)
          // console.log(response.message)
          // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
          /**
           * $vm 指为mavonEditor实例，可以通过如下两种方式获取
           * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
           * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
           */
          // this.$refs.md.$img2Url(pos, response.data.data.url)
              })
      } else {
        // 压缩到 200 kb
        imageConversion.compressAccurately(file, 200).then(response => {
          console.log('解压', response)
          formData.append('file', new window.File([response], file.name, {type: file.type}))
          // 压缩后上传
          this.$http({
            url: this.$http.adornUrl('/upload?module=articles'),
            method: 'post',
            data: formData,
            headers: {'Content-Type': 'multipart/form-data'}
          }).then(response => {
            // 因为不能给当前链接的请求头header设置Token 所以会出现 CORB【跨域问题】 主要原因是:【无有效Token】
            // 所以 回显会 失败  所以我们放在 保存环节使用
            // this.$refs.md.$img2Url(pos, response.data.data.url)
          })
        })
      }
    }
  },
  mounted () {
  }
}
</script>

<style scoped>
.article-title-container {
  display: flex;
  align-items: center;
  margin-bottom: 1.25rem;
  margin-top: 2.25rem;
}

.save-btn {
  margin-left: 0.75rem;
  background: #fff;
  color: #f56c6c;
}
</style>

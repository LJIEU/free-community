<template>
  <div>
    <!--顶部导航-->
    <Nav/>
    <div style="margin-top: 70px">
      <mavon-editor
          v-model="postForm.content"
          ref="md"
          @change="change"
          :ishljs="true"
          class="v-note-wrapper v-note-show"
          style="height: 650px"
          @imgAdd="uploadImg">
        <template v-slot:left-toolbar-after>
          <div style=" margin: 10px">
            <el-form :inline="true" ref="postFormRef" :model="postForm" :rules="postFormRules">
              <el-form-item prop="title" label="文章标题:">
                <el-input style="width: 400px;" v-model="postForm.title" placeholder="输入标题"></el-input>
              </el-form-item>
              <!--                <span style="font-weight: bolder;margin: 0px 15px">文章话题:</span>-->
              <el-form-item prop="topic" label="文章话题:">
                <el-autocomplete prefix-icon="el-icon-search"
                                 :fetch-suggestions="querySearch"
                                 placeholder="请输入内容"
                                 v-model="postForm.topic"
                                 @select="handleSelect"
                >
                  <template slot-scope="{ item }">
                    <div class="name">{{ item }}</div>
                  </template>
                </el-autocomplete>
                <!--                <el-button style="margin: 0px 15px" icon="el-icon-plus">添加话题</el-button>-->
              </el-form-item>
            </el-form>
          </div>
        </template>
      </mavon-editor>
    </div>
    <!--底部-->
    <div class="my-write-footer">
      <el-button
          @click="saveContent()"
          size="big"
          type="primary"
          style="float: right;margin: 23px 25px">提交
      </el-button>
    </div>
  </div>
</template>

<script>
import Nav from "@/components/index/Nav";
/*npm install markdown-it-emoji --save*/
/* 安装 mavon-editor=> npm install mavon-editor --s */
// 引入组件  及其 样式css
import {mavonEditor} from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import * as imageConversion from 'image-conversion'
import Vue from "vue";
import {uploadImg} from "@/api/upload/uploadImg";
import Footer from "@/components/index/Footer";
import {getTopicList, savePost} from "@/api/post/post"; // 引入库 npm install moment --save
Vue.use(mavonEditor)
export default {
  name: "Write",
  components: {
    mavonEditor, Nav, Footer
  },
  data() {
    return {
      postFormRules: {
        title: [
          {
            required: true, message: '请输入标题', trigger: 'blur'
          },
          {
            min: 4, max: 50, message: '标题字符长度4~50', trigger: 'blur'
          }, {
            validator: (rule, value, callback) => {
              if (value.match(/^[ ]*$/)) {
                callback(new Error('不能全为空格'));
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ],
        topic: [
          {required: true, message: '请输入话题', trigger: 'blur'},
          {
            validator: (rule, value, callback) => {
              if (value.match(/^[ ]*$/)) {
                callback(new Error('不能全为空格'));
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ]
      },
      postForm: {
        content: this.html,
        title: '',
        topic: '',
        description: '',
        html: ''
      },
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      },
      topicArray: [
        'a',
        'AA',
        'badsfsa',
        'asfsdfsdaf',
        'dfddfd',
      ],
      record: {
        start: 0,
        end: 0
      }
    }
  },
  mounted() {
    getTopicList().then((response) => {
      console.log('获取话题数据:', response)
      if (response && response.code === 200) {
        this.topicArray = response.data.topicList;
      }
    })
  },
  created() {
    // 先登录
    if (!window.localStorage.getItem('jwtToken')) {
      this.$router.push('/login')
    }
  },
  methods: {
    // 提交所有信息
    saveContent() {
      this.$refs.postFormRef.validate(valid => {
        if (valid) {
          if (this.postForm.content.match(/^[ ]*$/)) {
            // console.log('全是空格!')
            this.postForm.content = ''
            this.$message({
              message: '评论内容非法!',
              type: 'error'
            })
            return
          }
          // console.log('提交信息:', this.postForm);
          this.getDesrciption(this.html)
          console.log('提交信息JSON:', JSON.stringify(this.postForm));
          savePost(this.postForm).then(response => {
            console.log('提交结果:', response);
          })
        }
      })
    },
    handleSelect(item) {
      console.log('选择:', item);
      this.postForm.topic = item
    },
    querySearch(queryString, cb) {
      var topicArray = this.topicArray;
      var results = queryString ? topicArray.filter(this.createFilter(queryString)) : topicArray;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (topic) => {
        return (topic.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },
    // 所有操作都会被解析重新渲染
    change(value, render) {
      // render 为 markdown 解析后的结果[html]
      this.html = render
      this.postForm.content = this.html
      this.postForm.html = this.html;
      // if (this.postForm.description.length <= 200) {
      // }
      // this.postForm.content = this.$refs.md.d_render;
      // console.log(this.$refs.md.d_render)
      // console.log(this.$refs.md.d_value)
    },
    getDesrciption(content) {
      // this.postForm.content.substring(0, 180) + "..."
      let str = content; // 重新赋值
      let i = 0
      // 取消选择 <p><img src="http://localhost:8888/show/Firefox_wallpaper.png" alt="Firefox_wallpaper.png" /></p>
      do {
        console.log(str.indexOf("<img src=\""));
        let indexStart = str.indexOf("<img src=\"", i, str.length); // 图片链接
        if (indexStart !== -1) { // 不等于-1 说明找到链接开始
          console.log(str.indexOf("/>", indexStart, str.length));
          let indexEnd = str.indexOf("/>", indexStart, str.length); // 链接结束
          if (indexEnd !== -1) {
            // var s1 = str.substring(0, indexStart);
            // var s2 = str.substring(indexEnd, str.length);
            let s = str.substring(indexStart, indexEnd + 2);
            str = str.replace(s, ' ');
            console.log('截取后的字符串:', str)
            this.record.start = (indexStart)
            this.record.end = (indexEnd + 2)
            indexStart = -1; // 重置
            indexEnd = -1;
            i = indexEnd + 3
          }
        }
        this.postForm.description = str;
      } while (str.length > 0 && this.postForm.description.length < 175 && i++ < content.length)
      console.log('源信息:', content)
      console.log('描述信息:', this.postForm.description + "...")
    },
    // 文件上传
    uploadImg(pos, file) {
      // console.log('文件上传数量:', pos)
      // console.log('文件信息:', file)
      // console.log('文件大小:', file.size)
      // console.log('文件类型:', file.type)
      // 第一步.将图片上传到服务器.
      let formData = new FormData()
      console.log(formData.get('file'))
      if (file.size < (1024 * 200)) {
        formData.append('file', file)
        uploadImg(formData)        /*       axios({
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
          this.$refs.md.$img2Url(pos, response.data.url)
        })
      } else {
        // 压缩到 200 kb
        imageConversion.compressAccurately(file, 200).then(response => {
          console.log('解压', response)
          formData.append('file', new window.File([response], file.name, {type: file.type}))
          // 压缩后上传
          uploadImg(formData).then(response => {
            console.log(response);
            // 因为回显链接需要 Token
            this.$refs.md.$img2Url(pos, response.data.url)
          })
        })
      }
    }
  }
}
</script>

<style scoped>
.my-write-footer {
  left: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, .9);
  min-height: 70px;
  z-index: 1500;
}

.v-note-wrapper {
  /*z-index: 1 !important; !*默认1500*!*/
  /*height: 100px !important;*/

}

.v-note-wrapper.v-note-op { /*顶部栏*/
  z-index: 1500 !important;
  size: 50px;
}

.v-note-panel { /*控制板整体【输入 显示】*/

}

.content-input-wrapper { /*输入栏*/

}

.v-note-show { /*显示栏*/

}


</style>

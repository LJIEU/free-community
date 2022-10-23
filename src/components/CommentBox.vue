<template>
  <div class="row">
    <div style="margin: 0 0;padding: 0;width: 100%">
      <!--  防止输入NULL值 '' 和无限空格    -->
      <el-input show-word-limit maxlength="100" :id="comment.createTime+comment.commentId" rows="3"
                v-model="/*userInfo.commentInfo*/commentInfo.content"
                type="textarea"
                placeholder="输入您的想法...."
                style="width: 100%; margin-bottom: 10px"></el-input>
      <el-button v-if="commentInfo.content" style="float: right" type="primary" @click="saveComment()">发表</el-button>
      <div class="chatIcon">
        <!--表情列表-->
        <el-popover popper-class="" placement="bottom" width="400" trigger="click">
          <div class="emotionList">
            <a href="javascript:void(0);" @click="getEmo(index,comment.createTime+comment.commentId)"
               v-for="(v,index) in faceList" :key="index"
               class="emotionItem">{{ v }}</a>
          </div>
          <el-button
              slot="reference" style="background: none; border: none">
            <svg-icon icon-class="icon-emoji" style="width: 30px; height: 30px"></svg-icon>
          </el-button>
        </el-popover>
      </div>
    </div>
  </div>
</template>

<script>
import emoji from "@/plugins/emoji.json";
import {saveComment} from "@/api/comment/commet";

export default {
  name: "CommentBox",
  props: {
    userInfo: {type: Object},
    replyCommentId: {type: Number},
    articleId: {type: Number},
    showCancel: {type: Boolean, default: true},
    comment: {type: Object}
  },
  created() {
  },
  data() {
    return {
      faceList: [],
      commentInfo: {
        articleId: this.articleId, // 文章ID
        content: '', // 评论信息
        isParent: true, // 是否是父评论
        replyId: '',
        city: '',
        ip: ''
      },
    }
  },
  mounted() {
    // console.log('传递信息:', this.comment);
    for (let i in emoji) {
      this.faceList.push(emoji[i].char);
    }
    this.commentInfo.ip = localStorage.getItem('ip');
    this.commentInfo.city = localStorage.getItem('city');
  },
  methods: {
    saveComment() {
      var content = this.commentInfo.content;
      if (content.match(/^[ ]*$/)) {
        // console.log('全是空格!')
        this.commentInfo.content = ''
        this.$message({
          message: '评论内容非法!',
          type: 'error'
        })
        return
      }
      this.commentInfo.replyId = this.replyCommentId
      // 保存评论  需要用户信息【后台根据jwtToken获取】、帖子信息、是否是父评论
      console.log('评论者信息:', this.commentInfo, '\t', '被回复者信息:', this.replyCommentId)
/*      this.$message({
        message: '评论成功!',
        type: "success"
      })*/
      // console.log('保存评论!')
            saveComment(this.commentInfo).then((response) => {
              if (response && response.code === 200) {
                this.$message({
                  message: '评论成功!',
                  type: "success"
                })
                this.commentInfo.content = ''; // 清空评论区
                // 重新获取评论列表
                this.$emit('getList',this.articleId)
                // this.$router.go(0) // 这里不应该刷新 应该重新获取数据 并渲染
              }
            })
    },
    getEmo(index, id) {
      // console.log(id.toString());
      const textArea = document.getElementById(id);

      function changeSelectedText(obj, str) {
        if (window.getSelection) {
          // 非IE浏览器
          textArea.setRangeText(str);
          // 在未选中文本的情况下，重新设置光标位置
          textArea.selectionStart += str.length;
          textArea.focus()
        } else if (document.selection) {
          // IE浏览器
          obj.focus();
          var sel = document.selection.createRange();
          sel.text = str;
        }
      }

      changeSelectedText(textArea, this.faceList[index]);
      this.textarea = textArea.value;// 要同步data中的数据
      // console.log(this.faceList[index]);
      return;
    }
  }
}
</script>

<style scoped>

</style>

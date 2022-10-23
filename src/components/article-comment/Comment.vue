<template>
  <!--评论列表-->
  <div>
    <div class="comment" v-for="item in commentList"
         :key="item.commentId">
      <el-divider/>
      <div style="margin-left: 50px;margin-top: 20px" class="ui middle aligned mobile computer reversed stackable">
        <!--评论者信息-->
        <div class="avatar">
          <!--评论者头像-->
          <a :href="item.author.avatarURL">
            <el-avatar shape="square" :src="item.author.avatarURL"/>
          </a>

          <div class="my-author">
            <!--评论者名称-->
            <a class="author" style="margin:6px 4px">
              <a href="https://www.baidu.com" class="m-black">{{ item.author.name }}</a>
            </a>
          </div>
        </div>
        <!--评论信息结束------------------>


        <!--评论内容-->
        <div class="my-comment comment" v-html="item.content"/>

        <!--评论时间-->
        <div style="margin-top: 10px">
          {{ item.createTime }}
          <span> · </span>
          {{ item.address }}
        </div>


        <!--评论操作栏基本块-->
        <div class="ui grid">
          <!--            <div class="" style="margin-left: 420px">-->
          <div class="ten wide column"/>
          <!--回复-->
          <div class="two wide column">
            <a class="reply">
              <el-button @click="replyTo(item.commentId)" size="small"
                         style="background: none;border: none;margin-top: 10px">
                <span><svg-icon style="width: 20px;height: 20px;" icon-class="icon-comment"/></span>
                回复
              </el-button>
            </a>
          </div>

          <div class="two wide column">
            <div class="like">
              <el-button @click="isLike(item)" size="small" style="background: none;border: none;margin-top: 10px">
                        <span v-if="item.isLike">
                          <svg-icon style="width: 20px;height: 20px;"
                                    icon-class="icon-like2-on"/></span>
                <span v-else><svg-icon style="width: 20px;height: 20px;" icon-class="icon-like2-off"/></span>
                {{ item.likes }}
                <span v-if="item.isLike===0||item.isLike===null">点赞</span>
              </el-button>
            </div>
          </div>

          <div class="two wide column">
            <div class="operate">
              <el-dropdown @command="handleCommand"
                           style="background: none;border: none;margin-top: 20px">
             <span class="el-dropdown-link">
                 <svg-icon style="width: 20px;height: 18px;margin-top: 3px" icon-class="icon-operate"/>
             </span>

                <el-dropdown-menu slot="dropdown">
                  <!--       自己的评论才可以删除或者贴主           -->
                  <el-dropdown-item v-if="userInfo.author.name===item.author.name||item.author.name===postInfo.username"
                                    :command="beforeHandleCommend('delete',item)">删除
                  </el-dropdown-item>
                  <el-dropdown-item :command="beforeHandleCommend('report',item)">举报</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
        </div>
        <!--评论操作栏基本块结束--------------------->


        <!--评论栏-->
        <comment-box class="comment-box" style="display: none" :id="item.commentId" :comment="item"
                     :replyCommentId="item.commentId"
                     @getList="getList"
                     @submit-box="submitBox"
                     :articleId="articleId"
                     :showCancel="showCancel"></comment-box>
        <!--    评论栏结束======================     -->
      </div>

      <!--子评论--------------------------->
      <div v-if="item.childList!==null" class="ui padded attached" v-for="child_item in item.childList"
           :key="child_item.commentId">
        <div style="margin-left: 90px;margin-top: 20px" class="ui middle aligned mobile computer reversed stackable">
          <!--评论者信息-->
          <div class="avatar">
            <!--评论者头像-->
            <a :href="child_item.author.avatarURL">
              <el-avatar shape="square" :src="child_item.author.avatarURL"/>
            </a>

            <div class="my-author">
              <!--评论者名称-->
              <a class="author" style="margin:6px 4px">
                <!--                <a :href="child_item.author.homeURL" class="m-black">{{ child_item.author.name }}</a>-->
                <a href="https://www.baidu.com" class="m-black">{{ child_item.author.name }}</a>
              </a>

              <!--回复模块    还需要完善!!!-->
              <span v-if="child_item.commentId!=null">
                <!--右箭头 right-arrow-->
                  <svg-icon
                      icon-class="icon-right-arrow"></svg-icon>
                <!--回复对象者名称-->
                <span style="margin-left: 4px">
                  <!--                    <a :href="child_item.replyAuthorHomeURL" class="m-black">{{ child_item.replyAuthorName }}</a>-->
                  <a v-if="" href="https://www.baidu.com" class="m-black">
                    <ParentName :commentId="child_item.commentId"></ParentName>
                  </a>
                </span>
              </span>

            </div>

          </div>
          <!--评论信息结束------------------>


          <!--评论内容-->
          <div class="my-comment comment" v-html="child_item.content"/>

          <!--评论时间-->
          <div style="margin-top: 10px">
            {{ child_item.createTime }}
            <span> · </span>
            {{ child_item.address }}
          </div>


          <!--评论操作栏基本块-->
          <div class="ui grid">
            <!--            <div class="" style="margin-left: 420px">-->
            <div class="ten wide column"/>
            <!--回复-->
            <div class="two wide column">
              <a class="reply">
                <el-button @click="replyTo(child_item.commentId)" size="small"
                           style="background: none;border: none;margin-top: 10px">
                  <span><svg-icon style="width: 20px;height: 20px;" icon-class="icon-comment"/></span>
                  回复
                </el-button>
              </a>
            </div>

            <div class="two wide column">
              <div class="like">
                <el-button @click="isLike(child_item)" size="small"
                           style="background: none;border: none;margin-top: 10px">
                        <span v-if="child_item.isLike">
                          <svg-icon style="width: 20px;height: 20px;"
                                    icon-class="icon-like2-on"/></span>
                  <span v-else><svg-icon style="width: 20px;height: 20px;" icon-class="icon-like2-off"/></span>
                  {{ child_item.likes }}
                  <span v-if="child_item.isLike===0||child_item.isLike===null">点赞</span>
                </el-button>
              </div>
            </div>

            <div class="two wide column">
              <div class="operate">
                <el-dropdown @command="handleCommand"
                             style="background: none;border: none;margin-top: 20px">
             <span class="el-dropdown-link">
                 <svg-icon style="width: 20px;height: 18px;margin-top: 3px" icon-class="icon-operate"/>
             </span>

                  <el-dropdown-menu slot="dropdown">
                    <!--       自己的评论才可以删除或者贴主           -->
                    <el-dropdown-item
                        v-if="userInfo.author.name===child_item.author.name||child_item.author.name===postInfo.username"
                        :command="beforeHandleCommend('delete',child_item)">删除
                    </el-dropdown-item>
                    <el-dropdown-item :command="beforeHandleCommend('report',child_item)">举报</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </div>
          </div>
        </div>

        <!--评论操作栏基本块结束--------------------->
        <!--评论栏-->
        <comment-box class="comment-box" style="display: none" :id="child_item.commentId" :comment="child_item"
                     :replyCommentId="child_item.commentId"
                     @getList="getList"
                     @submit-box="submitBox"
                     :articleId="articleId"
                     :showCancel="showCancel"></comment-box>
        <!--    评论栏结束======================     -->
      </div>
    </div>
    <!--    </div>-->
  </div>
  <!--子评论结束------------------->
</template>

<script>

import CommentBox from "@/components/CommentBox";
import {getList, updateLike} from "@/api/comment/commet";
import ParentName from "@/components/article-comment/ParentName";

export default {
  name: "Comment",
  props: {
    articleId: {
      type: Number
    },
    postInfo: {
      type: Object
    }
  },
  components: {CommentBox, ParentName},
  mounted() {
    this.IP = localStorage.getItem('ip');
    this.city = localStorage.getItem('city');
  },
  data() {
    return {
      isShow: false,
      userInfo: {
        author: {
          name: window.localStorage.getItem('username')
        }
      },
      replyInfo: { // 回复者信息
        id: 2,
        name: 'liu',
        avatarURL: ''
      },
      submitBox: false,
      showCancel: false,
      // child_isShow: false,
      // city: '',
      /*      commentList: [
              {
                id: 1,
                commentNum: 20,
                commentInfo: '',
                // type: comment,
                // resource_type: article,
                hot: false,
                top: false,
                content: "重庆二本非计算机专业应届毕业生，12k薪资，个人认为可能性很小，重庆互联网环境很差，成都这边很多岗位12 k起的，都要求3年以上工作经验，甚至5年以上工作经验，还要作为项目技术负责人",
                score: 0,
                createTime: moment(new Date()).format('YYYY-MM-DD'),
                isDelete: false,
                liked: false,
                liked_count: 471, // 喜欢的数量
                disliked: false,
                disliked_count: 7,
                author: {
                  id: 1,
                  name: "我是评论者",
                  avatarURL: "https://pic1.zhimg.com/v2-845d115b40d59304e669e97b72958d57_l.jpg?source=06d4cd63",
                  homeURL: "https://www.zhihu.com/people/061f9dc4ba6c7e0f6a5b6223fd05e79c"
                },
                child_comment_count: 75,
                child_comments: [
                  {
                    id: 1,
                    commentInfo: '',
                    // type: comment,
                    // resource_type: article,
                    reply_comment_id: 5,
                    // replyAuthorName: '我是评论者',
                    // replyAuthorHomeURL: 'https://www.zhihu.com/people/061f9dc4ba6c7e0f6a5b6223fd05e79c',
                    hot: true,
                    top: false,
                    content: "对的！！ 这种张口自学毕业12K 不太可能",
                    score: 0,
                    createTime: moment(new Date()).format('YYYY-MM-DD'),
                    isDelete: false,
                    liked: false,
                    liked_count: 471, // 喜欢的数量
                    disliked: false,
                    disliked_count: 7,
                    author: {
                      id: 2,
                      name: "我是评论者2",
                      avatarURL: "https://pic3.zhimg.com/v2-e4dbc560c60d872e32a9674a156e7804_l.jpg?source=06d4cd63",
                      homeURL: "https://www.zhihu.com/people/061f9dc4ba6c7e0f6a5b6223fd05e79c"
                    }
                  },
                  {
                    id: 2,
                    // type: comment,
                    // resource_type: article,
                    replyAuthorId: 2,
                    replyAuthorName: '我是评论者2',
                    replyAuthorHomeURL: 'https://www.zhihu.com/people/061f9dc4ba6c7e0f6a5b6223fd05e79c',
                    hot: true,
                    top: false,
                    content: "对的！！ 这种张口自学毕业12K 不太可能",
                    score: 0,
                    createTime: moment(new Date()).format('YYYY-MM-DD'),
                    isDelete: false,
                    liked: false,
                    liked_count: 471, // 喜欢的数量
                    disliked: false,
                    disliked_count: 7,
                    author: {
                      id: 5,
                      name: "我是评论者5",
                      avatarURL: "https://pic3.zhimg.com/v2-e4dbc560c60d872e32a9674a156e7804_l.jpg?source=06d4cd63",
                      homeURL: "https://www.zhihu.com/people/061f9dc4ba6c7e0f6a5b6223fd05e79c"
                    },

                  }
                ]
              },
              {
                id: 2,
                commentNum: 20,
                // type: comment,
                // resource_type: article,
                hot: false,
                top: false,
                content: "重庆二本非计算机专业应届毕业生，12k薪资，个人认为可能性很小，重庆互联网环境很差，成都这边很多岗位12 k起的，都要求3年以上工作经验，甚至5年以上工作经验，还要作为项目技术负责人",
                score: 0,
                createTime: moment(new Date()).format('YYYY-MM-DD'),
                isDelete: false,
                liked: false,
                liked_count: 471, // 喜欢的数量
                disliked: false,
                disliked_count: 7,
                author: {
                  id: 3,
                  name: "我是评论者3",
                  avatarURL: "https://pic1.zhimg.com/v2-845d115b40d59304e669e97b72958d57_l.jpg?source=06d4cd63",
                  homeURL: "https://www.zhihu.com/people/061f9dc4ba6c7e0f6a5b6223fd05e79c"
                },
                child_comment_count: 75,
                child_comments: [
                  {
                    // id: 2,
                    // type: comment,
                    // resource_type: article,
                    hot: true,
                    top: false,
                    content: "对的！！ 这种张口自学毕业12K 不太可能",
                    score: 0,
                    createTime: moment(new Date()).format('YYYY-MM-DD'),
                    isDelete: false,
                    liked: false,
                    liked_count: 471, // 喜欢的数量
                    disliked: false,
                    disliked_count: 7,
                    author: {
                      id: 4,
                      name: "我是评论者4",
                      avatarURL: "https://pic3.zhimg.com/v2-e4dbc560c60d872e32a9674a156e7804_l.jpg?source=06d4cd63",
                    },

                  }
                ]
              }
            ]*/
      commentList: [],
      userName: '',
      whether: false
    }
  },
  created() {
    // 获取评论列表
    this.getList(this.articleId)

    console.log('帖子信息:', this.postInfo, '\t', this.userInfo.author.name);
  },
  methods: {
    // 点赞
    isLike(item) {
      // 需要当前登录用户信息
      if (!window.localStorage.getItem('jwtToken')) {
        this.$router.push('/login')
      }
      var name = this.userInfo.author.name;

      // 需要当前评论ID
      var commentId = item.commentId;

      if (item.isLike === 0 || item.isLike === null) {
        this.whether = false;
      } else {
        this.whether = true
      }

      var info = {
        userName: name,
        commentId: commentId,
        whether: !this.whether
      }

      console.log('点赞:', info)

      // 发送给后端
      updateLike(info).then((response) => {
        console.log('点赞结果:', response);
        if (response && response.code === 200) {
          if (response.date.isLike) {
            this.$message({
              type: "success",
              message: "感谢您的认可!"
            })
          } else {
            this.$message({
              type: "warning",
              message: "可惜了!"
            })
          }
        }
      })

      // 刷新评论
      this.getList(this.articleId)
    },
    // 整理数据
    beforeHandleCommend(command, item) {
      return {
        'command': command,
        'item': item
      }
    },
    // 选择信息
    handleCommand(value) {
      console.log('选择信息:', value);
      if (value.command === 'delete') { // 执行删除该评论信息

      }
      if (value.command === 'report') { // 执行举报该评论

      }
    },
    getList(articleId) {
      this.$emit('getArticle')
      getList(articleId, this.userInfo.author.name).then((response) => {
        console.log('响应信息:', response);
        this.commentList = response.data.commentList
        console.log(this.commentList);
      });
    },
    replyTo: function (uid) {
      // console.log(uid);
      const lists = document.getElementsByClassName("comment-box");
      // console.log(lists);
      for (let i = 0; i < lists.length; i++) {
        lists[i].style.display = 'none'; // 隐藏全部
      }
      // console.log(this.isShow);
      if (!this.isShow) {
        this.isShow = true
        document.getElementById(uid).style.display = 'block'; // 显示被点击的
        this.replyInfo.id = uid
      } else {
        this.isShow = false;
      }
    },
  }
}
</script>
<style scoped>


.my-author {
  margin-top: -30px;
  margin-bottom: 25px;
  margin-left: 50px;
  font-size: 14px;
  font-weight: bolder;
}

.comment-box {
  /*display: none !important;*/
}

.my-comment {
  box-sizing: border-box;
  margin: 5px 30px 0px;
  min-width: 0px;
  color: rgb(68, 68, 68);
  overflow-wrap: break-word;
  font-size: 15px;
  line-height: 21px;
}

/* el-popover是和app同级的，所以scoped的局部属性设置了无效 */
/* 需要设置全局style */
.el-popover {
  height: 200px;
  width: 400px;
  overflow: scroll;
  overflow-x: auto;
}

.chatIcon {
  padding: 0 10px;
  font-size: 25px;
}

.emotionList {
  display: flex;
  flex-wrap: wrap;
  padding: 5px;
}

.emotionItem {
  width: 10%;
  font-size: 20px;
  text-align: center;
}

/*包含以下四种的链接*/
.emotionItem {
  text-decoration: none;
}

/*正常的未被访问过的链接*/
.emotionItem:link {
  text-decoration: none;
}

/*已经访问过的链接*/
.emotionItem:visited {
  text-decoration: none;
}

/*鼠标划过(停留)的链接*/
.emotionItem:hover {
  text-decoration: none;
}

/* 正在点击的链接*/
.emotionItem:active {
  text-decoration: none;
}
</style>

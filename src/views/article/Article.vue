<template>
  <div>
    <Nav v-show="!isNav"/>
    <!--  跟换导航栏  【制作过渡动画】    -->
    <div v-show="isNav" class="ui fixed stackable pointing menu"
         style="height: 50px; background-color: rgba(255,255,255,0.8)">
      <div class="article-menu">
        <h2>{{ this.article.title }}</h2>
      </div>
    </div>
    <!--
        <div style="margin-top: 70px;background-color: white"
             class="ui my-article-div middle aligned m-padded-tb-large">
          <div class="ui middle aligned mobile reversed stackable">
            <div class="item">
              <h1>描述信息</h1>
            </div>
          </div>
        </div>
    -->
    <div style="margin-top: 70px">
      <div class="main">
        <div class="m-padded-tb-big">
          <div class="ui container">
            <div class="ui stackable grid">
              <!--    16格子宽度        -->
              <!--左侧-->
              <div class="one wide column m-mobile-hide">
              </div>
              <!--中间-->
              <div class="eleven wide column">
                <keep-alive>
                  <div class="ui padded attached segment m-padded-tb-large">
                    <div class="ui middle aligned mobile reversed stackable">
                      <div class="ui grid m-margin-lr">
                        <!--分类-->
                        <!--
                          <router-link :to="`/category/${article.category.name}`" class="ui orange large ribbon label"
                                       v-if="article.category">
                            <i class="small folder open icon"></i><span class="m-text-500">{{ article.category.name }}</span>
                          </router-link>
                        -->
                        <div class="row m-padded-tb-no">
                          <div class="column m-padding-left-no">

                            <!--标签不会是一个  一定是多个的-->
                            <!--
                                                        <router-link :to="`/tag/${tag.name}`"
                                                                     class="ui tag label m-text-500 m-margin-small" :class="tag.color"
                                                                     v-for="(tag,index) in article.topic" :key="index">{{ tag.name }}
                                                        </router-link>
                            -->
                            <router-link :to="`/tag/${article.topic}`"
                                         class="ui tag label m-text-500 m-margin-small red"
                                         v-model="article.topic">{{ article.topic }}
                            </router-link>

                          </div>
                        </div>

                        <!--标题-->
                        <div class="row m-padded-tb-small">
                          <h2 class="ui header">{{ article.title }}</h2>
                        </div>

                        <!--屏蔽导航栏-->
                        <!--                        <div :class="{change:isShow}" ref="changeNav"/>-->

                        <!--用户信息-->


                        <!--文章Markdown正文-->
                        <!--          <div class="typo js-toc-content m-padded-tb-small match-braces rainbow-braces markdown-body" v-viewer
                                       v-heightConst v-html="article.content"></div>-->
                        <!--应该设置代码颜色主题【未完成】-->
                        <div class="typo m-padded-tb-small markdown-body" v-if="article.documentTranslate!=null">
                          <vue-markdown :source="article.documentTranslate" v-highlight></vue-markdown>
                        </div>

                        <div class="row">
                          <div class="row m-padded-tb-small">
                            <div class="ui horizontal segments">
                              <!--关注问题-->
                              <div class="ui segment">
                                <el-button type="primary" size="medium">
                                  关注问题
                                </el-button>
                              </div>

                              <!--写回答-->
                              <div class="ui segment">
                                <el-button @click="replyTo(-1)" class="my-article-button" size="medium"
                                           icon="el-icon-edit"
                                           style="background: none;border: 1px solid rgb(5,109,232);">
                                  写回答
                                </el-button>
                              </div>

                              <!--邀请回答-->
                              <div class="ui segment">
                                <el-button class="my-article-button" size="medium" icon="el-icon-s-promotion"
                                           style="background: none;border: 1px solid rgb(5,109,232);">
                                  邀请回答
                                </el-button>
                              </div>

                              <!--点赞-->
                              <div class="ui segment">
                                <el-button size="small" style="background: none;border: none">
                  <span v-if="article.isLike"><svg-icon style="width: 25px;height: 25px;margin-top: -4px"
                                                        icon-class="icon-like-on"/></span>
                                  <span v-else><svg-icon style="width: 25px;height: 25px;margin-top: -4px"
                                                         icon-class="icon-like-off"/></span>
                                  点赞{{ article.likes }}
                                </el-button>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <el-divider/>
                      <!--评论栏-->
                      <!--这里评论默认为0-->
                      <comment-box class="comment-box" style="display: none" id="-1"
                                   :comment="userInfo"
                                   :replyCommentId=0
                                   @getList="flash"
                                   @submit-box="submitBox"
                                   :articleId="articleId"
                                   :showCancel="showCancel"></comment-box>
                      <!--    评论栏结束======================     -->

                    </div>
                  </div>

                </keep-alive>
                <!--评论-->
                <div class="ui padded attached segment m-padded-tb-large m-margin-bottom-big"
                     style="margin-top: 20px">
                  <h4 style="margin-bottom: 4px">{{ article.comments }}&nbsp;&nbsp;评论</h4>
                  <div class="ui middle aligned mobile reversed stackable">
                    <CommentList @getArticle="getArticle" :postInfo="postInfo" :articleId="articleId"/>
                  </div>
                </div>
              </div>
              <!--右侧-->
              <div class="four wide column m-mobile-hide">
                <!--关于作者-->
                <Author/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--
        <div class="ui padded attached segment m-padded-tb-large">
          <div class="ui middle aligned mobile reversed stackable">
            <div class="ui grid m-margin-lr">
              &lt;!&ndash;分类&ndash;&gt;
              &lt;!&ndash;
                <router-link :to="`/category/${article.category.name}`" class="ui orange large ribbon label"
                             v-if="article.category">
                  <i class="small folder open icon"></i><span class="m-text-500">{{ article.category.name }}</span>
                </router-link>
              &ndash;&gt;
              <div class="row m-padded-tb-no">
                <div class="column m-padding-left-no">
                  <router-link :to="`/tag/${tag.name}`"
                               class="ui tag label m-text-500 m-margin-small" :class="tag.color"
                               v-for="(tag,index) in article.tags" :key="index">{{ tag.name }}
                  </router-link>
                </div>
              </div>

              &lt;!&ndash;标题&ndash;&gt;
              <div class="row m-padded-tb-small">
                <h2 class="ui header">{{ article.title }}</h2>
              </div>

              &lt;!&ndash;屏蔽导航栏&ndash;&gt;
              <div :class="{change:isShow}" ref="changeNav"/>

              &lt;!&ndash;用户信息&ndash;&gt;


              &lt;!&ndash;文章Markdown正文&ndash;&gt;
              &lt;!&ndash;          <div class="typo js-toc-content m-padded-tb-small match-braces rainbow-braces markdown-body" v-viewer
                             v-heightConst v-html="article.content"></div>&ndash;&gt;
              &lt;!&ndash;应该设置代码颜色主题【未完成】&ndash;&gt;
              <div class="typo m-padded-tb-small markdown-body">
                <vue-markdown :source="article.content" v-highlight></vue-markdown>
              </div>


              <div class="row m-padded-tb-small">
                <div class="ui horizontal link list">
                  &lt;!&ndash;关注问题&ndash;&gt;
                  <div class="item">
                    <el-button type="primary" size="medium">
                      关注问题
                    </el-button>
                  </div>

                  &lt;!&ndash;写回答&ndash;&gt;
                  <div class="item">
                    <el-button class="my-article-button" size="medium" icon="el-icon-edit"
                               style="background: none;border: 1px solid rgb(5,109,232);">
                      写回答
                    </el-button>
                  </div>

                  &lt;!&ndash;邀请回答&ndash;&gt;
                  <div class="item">
                    <el-button class="my-article-button" size="medium" icon="el-icon-s-promotion"
                               style="background: none;border: 1px solid rgb(5,109,232);">
                      邀请回答
                    </el-button>
                  </div>


                  &lt;!&ndash;点赞&ndash;&gt;
                  <div class="item m-right-top" style="margin: -5px">
                    <el-button size="small" style="background: none;border: none">
                      <span v-if="article.isLike"><svg-icon style="width: 35px;height: 35px;"
                                                            icon-class="icon-like-on"/></span>
                      <span v-else><svg-icon style="width: 35px;height: 35px;" icon-class="icon-like-off"/></span>
                      点赞{{ article.like }}
                    </el-button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        &lt;!&ndash;评论&ndash;&gt;
        <div class="ui padded attached segment m-padded-tb-large m-margin-bottom-big" style="margin-top: 20px">
          <h4 style="margin-bottom: 4px">{{ article.commentNum }}&nbsp;&nbsp;评论</h4>
          <div class="ui middle aligned mobile reversed stackable">
            <CommentList :articleId="articleId"/>
          </div>
        </div>
    -->

  </div>
</template>

<script>
import VueMarkdown from 'vue-markdown'
import {mapState} from "vuex";
import Nav from "@/components/index/Nav";
import Author from "@/views/article/Author";
import {getArticle, getPostUser} from "@/api/post/post";
import CommentBox from "@/components/CommentBox";
import CommentList from "@/components/article-comment/CommentList";

export default {
  name: "Article",
  components: {
    Nav,
    VueMarkdown,
    CommentList,
    Author,
    CommentBox
  },
  computed: {
    articleId() {
      return parseInt(this.$route.params.id)
    },
    ...mapState(['siteInfo', 'focusMode'])
  },
  created() {
    /*到顶部*/
    this.scrollToTop();
    // console.log('ID:', this.articleId);
    // 获取文章信息
    this.getArticle();

    // 查询帖子作者信息
    // console.log(this.articleId)
    this.getPostUserInfo(this.articleId)
  },
  data() {
    return {
      // article: {
      //   title: '测试案例',
      //   category: {
      //     forum: [],
      //     id: null,
      //     name: '分类测试'
      //   },
      //   createTime: '2022-09-10 10:21:12',
      //   content: "在使用yolo系列网络在推理的时候，会出现速度越来越慢的情况\n\n已经排除的问题\n\n1. 温度，温度还没有到达我温度墙 cpu75°左右 gpu70°左右\n2. 显存，我在推理的时候，显存也没有跑满，我是3090 24G显存 大概是使用8G左右\n3. cuda，我显示的我的cuda使用率是20%左右（跑不起来）\n4. cpu，我的cpu是3950x我感觉有可能是cpu不行\n5. 内存条，我的内存条是DDR4 3200HZ 32G*1  我只有一根内存\n\n请有经验的大佬指导一下，可能是哪个地方卡住了，需要加强一点\n\n还有就是ubuntu系统会快很多，win10慢 相同的代码 大概是慢5倍",
      //   id: 29,
      //   password: "12345",
      //   privacy: false,
      //   readTime: 11,
      //   tags: [{
      //     id: null,
      //     name: "标签测试",
      //     color: "red",
      //   }],
      //   top: true,
      //   views: 2000,
      //   words: 1999,
      //   commentNum: 200,
      //   collect: false,
      //   isLike: true,
      //   like: 20,
      //   pictureURL: 'https://pic2.zhimg.com/50/v2-906b44107bdb20ca768a79853c66725b_400x224.jpg'
      // },
      article: {},
      currentScroll: 0,
      isShow: false,
      isNav: false,
      submitBox: false,
      replyInfo: { // 回复者信息 这里是默认回复给 帖子作者

      },
      postInfo: {},
      userInfo: {
        commentId: -1,
        author: {
          name: window.localStorage.getItem('username')
        }
      },
      showCancel: false,
      commentList: []
    }
  },
  mounted() {
    // 监听滚轮位置
    window.onscroll = () => {
      var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
      var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
      var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;

      if (scrollTop > 500 && !this.isNav) {
        this.isNav = true;
        console.log('是否显示', this.isNav);
      } else if (scrollTop < 200) {
        this.isNav = false
      }
      // console.log('滚动条总高度:', document.documentElement.scrollHeight);
      // console.log('可视区域高度:', document.documentElement.clientHeight);
      // console.log('距离顶部距离:', document.documentElement.scrollTop);
    }
  },
  methods: {

    flash(id) {
      this.$router.go(0)
    },

    // 获取帖子作者信息
    getPostUserInfo(postId) {
      getPostUser(postId).then((response) => {
        // console.log('获取帖子作者:', response);
        if (response && response.code === 200) {
          this.replyInfo = response.data.user
          this.postInfo = response.data.user
        }
      });
    },
    replyTo: function (uid) {
      if (window.localStorage.getItem('jwtToken') == null) {
        this.$router.push('/login')
      }

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
    getArticle() {
      getArticle(this.articleId).then((response) => {
        console.log('帖子信息:', response)
        if (response && response.code === 200) {
          this.article = response.data.post
        }
      }).catch(() => {
        this.$router.push('/404')
      })
    }
  }
}

</script>

<style scoped>
.ui.horizontal.segments > .segment {
  border-left: none;
}

.ui.segments {
  box-shadow: none;
  border: none;
}


.article-menu {
  width: 200px;
  margin: 10px 42%
}

.my-article-div {
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.4) !important;
  margin-bottom: 10px;
}

/*鼠标点击时样式*/
.my-article-button:active {
  background: rgba(0, 0, 0, 0.4);
}

/*鼠标悬停时样式*/
.my-article-button:hover {
  background: rgba(0, 0, 0, 0.1) !important;
}

.testenter {
  transform: translateY(0%) !important; /*滚动后的位置*/
  opacity: 1 !important; /*滚动后显现 */
  transition: all .5s ease;
}

.test {
  transform: translateY(10%); /*滚动前的位置*/
  opacity: 0; /*滚动前设置透明隐藏*/
}
</style>

<template>
  <div>
    <div class="ui padded attached segment m-padded-tb-large m-margin-bottom-big m-box"
         v-for="(item,index) in forumList"
         :key="item.invitationId">
      <!--
                <div class="ui large red right corner label" v-if="item.top">
                  <i class="arrow alternate circle up icon"></i>
                </div>
      -->
      <div class="ui middle aligned mobile computer reversed stackable">
        <div class="ui grid m-margin-lr">
          <!--标题==>实现页面跳转-->
          <div class="row m-padded-tb-small">
            <h3 class="ui header m-padding-left-no m-scaleup center" style="margin:10px 2px">
              <!--              <a :href="'Article/'+item.invitationId" target="_blank" @click.prevent="toArticlePage(item)" class="m-black">{{ item.title }}</a>-->
              <a :href="'Article/'+item.invitationId" target="_blank" class="m-black">{{ item.title }}</a>
            </h3>

            <!--文章基本信息-->
            <div class="item m-datetime" style="margin:10px 15px">
              <!--时间-->
              <span style="margin-right: 10px;">{{ item.createTime }}</span>
              <!-- <i class="small clock icon"></i><span>{{ item.createTime  | dateFormat('YYYY-MM-DD')}}</span>-->
              <i class="small clock icon"></i>
              <!--时间差-->
              <span v-if="timeDifference(item.createTime)">{{ timeDescribe }}</span>
            </div>

            <!--观看数  每点击一次浏览全文【触发toPage()方法时】就+1  反馈给后台收集起来-->
            <div class="item wide m-views m-right-top" style="margin: 15px;">
              <i class="small eye icon"></i><span>{{ item.pageviews }}</span>
            </div>
          </div>

          <!--图片-->
          <div :class="{'my-image':item.cover!==null}">
            <el-image v-if="item.cover" :src="item.cover" style="float:left;margin: 0;padding: 0"
                      alt="cover"></el-image>
            <div v-else></div>
          </div>

          <!--文章Markdown描述-->
          <div class="typo m-padded-tb-small line-numbers match-braces rainbow-braces my-span"
               :class="{'my-haveImage':item.cover!==null}">
            <!--            <p @click.prevent="toArticlePage(item)" style="margin: 0; padding: 0;" v-html="item.content"/>-->
            <p style="margin: 0; padding: 0;" @click.prevent="toArticle(item)" v-html="item.content"/>
          </div>

          <!--阅读全文按钮-->
          <div class="row m-padded-tb-small m-margin-top">
            <!--            <a href="javascript:;" @click.prevent="toArticlePage(item)" class="color-btn">阅读全文</a>-->
            <a href="javascript:;" @click.prevent="toArticle(item)" class="color-btn">阅读全文</a>
          </div>

          <!--横线-->
          <div class="ui section divider m-margin-lr-no"/>

          <!--文章简单信息-->
          <div class="row m-padded-tb-small">
            <div class="ui horizontal link list">
              <!--评论-->
              <div class="item">
                <el-button @click.prevent="toArticle(item)" size="small" style="background: none;border: none">
                  <span><svg-icon style="width: 25px;height: 25px;" icon-class="icon-comment"/></span>
                  {{ item.comments }}评论
                </el-button>
              </div>

              <!--收藏-->
              <div class="item">
                <el-button size="small" style="background: none;border: none">
                  <span v-if="item.collect"><svg-icon style="width: 25px;height: 25px;"
                                                      icon-class="icon-collect-on"/></span>
                  <span v-else><svg-icon style="width: 25px;height: 25px;" icon-class="icon-collect-off"/></span>
                  收藏
                </el-button>
              </div>

              <!--举报-->
              <div class="item m-right-top" style="margin: 7px 100px">
                <el-button size="small" style="background: none;border: none">
                  <span><svg-icon style="width: 25px;height: 25px;" icon-class="icon-report"/></span>
                  举报
                </el-button>
              </div>

              <!--分享-->
              <div class="item m-right-top" style="margin: 7px">
                <el-button size="small" style="background: none;border: none">
                  <span><svg-icon style="width: 25px;height: 25px;" icon-class="icon-share"/></span>
                  分享
                </el-button>
              </div>
            </div>
          </div>

        </div>
        <!--   评论区 【没弄明白】     -->
        <!--        <div :id="item.invitationId" class="comment-item" @blur="hide()">
                  <CommentList :postInfo="item" :articleId="item.invitationId"></CommentList>
                </div>-->

      </div>
    </div>
    <div v-show="noForumList" class="ui padded attached segment m-padded-tb-large m-margin-bottom-big m-box">
      <h1>没有更多内容!</h1>
    </div>
  </div>
</template>

<script>
import {getForumList} from "@/api/forum/forumList";
import {mapState} from "vuex";
import CommentList from "@/components/article-comment/CommentList";

export default {
  name: "ForumItem",
  components: {CommentList},
  /*  props: {
      forumList: {
        type: Array,
        required: true
      }
    },*/
  data() {
    return {
      isShow: false,
      forumList: [],
      forumTotal: [],
      /*      forumList: [{
                      title: '测试案例',
                      category: {
                        forum: [],
                        id: null,
                        name: '分类测试'
                      },
                      createTime: '2022-09-10 10:21:12',
                      description: "<p>最近俄乌局势持续升级，刷推看到一些视频，<b>俄军坦克冲撞并碾压在对</b>向车道行驶中的乌民用车（又有消息说是乌军坦克，尚不知真假，但平民的确是乌克兰人）...一位乌克兰父亲含泪将妻儿送上前往安全地区的车（又证实是假消息，与文无关...这年头想看到真实的消息太难了，无论怎样，总归是值得思考的一件事吧）......唉，只能说珍惜当下的和平吧......</p>\n",
                      id: 29,
                      password: "12345",
                      privacy: false,
                      readTime: 11,
                      tags: {
                        id: null,
                        name: "标签测试",
                        color: "red",
                      },
                      top: true,
                      views: 2000,
                      words: 1999,
                      comment: 200,
                      collect: false,
                      pictureURL: 'https://pic2.zhimg.com/50/v2-906b44107bdb20ca768a79853c66725b_400x224.jpg'
            }],*/
      timeDescribe: '',
      isAchieveBottom: false, // 是否到达底部
      noForumList: false, // 是否还有数据
      // forumListNum: 0,
      total: 0
    }
  },
  created() {
    // this.getArticle()
    getForumList().then(response => {
      if (response && response.code === 200) {
        this.forumTotal = response.data.forumList
        this.total = response.data.forumList.length;
        console.log('总记录:', this.total, "\t", this.forumTotal)
      }
    })
  },
  mounted() {
    // this.$store.dispatch('getForumList') // 跳转
    this.checkIsAchieveBottom() // 检测是否滑动到底部
  },
  computed: {
    ...mapState({})
  },
  methods: {
    /*    hide(){
          console.log('失焦')
          const lists = document.getElementsByClassName("comment-item");
          for (let i = 0; i < lists.length; i++) {
            lists[i].style.display = 'none'; // 隐藏全部
          }
        },
        isComment(id) {
          const lists = document.getElementsByClassName("comment-item");
          console.log('列表', lists);
          console.log('id', id);
          for (let i = 0; i < lists.length; i++) {
            lists[i].style.display = 'none'; // 隐藏全部
          }
          // console.log(this.isShow);
          if (!this.isShow) {
            this.isShow = true
            document.getElementById(id).style.display = 'block'; // 显示被点击的
          } else {
            this.isShow = false;
          }

        },*/

    /*    // 获取第一个链接地址
        getFirstURL(str) {
          var pattern = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\*\+,;=.]+$/g;
          console.log(pattern.test(str));
          if (pattern.test(str)){
            return '';
          }
          // console.log(str.match(pattern)[0]);
          // console.log(pattern.exec(str));
          return str.match(pattern)[0];
        },*/
    forumShowList() {
      let total = this.total; // 总长度
      let forumLength = this.forumList.length; // 当前位置
      // console.log('当前长度:', forumLength, '\t', '总长度:', total);
      if (forumLength >= total) { // 如果相等则没数据
        this.noForumList = true
        console.log(this.noForumList)
      } else {
        if (!this.noForumList) {
          if (total - forumLength >= 5) {
            for (let i = 0; i < 5; i++) { // 写入 5 条数据
              this.forumList.push(this.forumTotal[forumLength + i])
            }
          } else { // 写入剩下的
            for (let i = 0; i < total - forumLength; i++) { // 写入 5 条数据
              this.forumList.push(this.forumTotal[forumLength + i])
            }
            this.noForumList = true
          }
        }
      }
    },
    checkIsAchieveBottom() {
      window.onscroll = () => {
        var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
        var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
        var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;

        if (scrollTop + clientHeight >= scrollHeight - 20 && !this.isAchieveBottom && !this.noForumList) {
          this.isAchieveBottom = true;
          // 加载数据
          setTimeout(() => {
            this.forumShowList()
            this.isAchieveBottom = false
          }, 500)
        }
        // console.log('滚动条总高度:', document.documentElement.scrollHeight);
        // console.log('可视区域高度:', document.documentElement.clientHeight);
        // console.log('距离顶部距离:', document.documentElement.scrollTop);
      }
    },
    toArticlePage(item) {
      this.$store.dispatch('toArticlePage', item)
    },
    //             <a :href="'Article/'+item.invitationId" target="_blank" class="my-forumItem-link">
    toArticle(item) {
      var resolve = this.$router.resolve({
            path: `Article/${item.invitationId}`
          }
      );
      window.open(resolve.href, '_blank')
    },
    timeDifference(time) {
      var temp = Date.parse(new Date(time));
      var oldTime = new Date(temp);
      var nowTime = new Date();
      var year = nowTime.getFullYear() - oldTime.getFullYear();
      var month = nowTime.getMonth() - oldTime.getMonth();
      var day = nowTime.getDate() - oldTime.getDate();
      var hours = nowTime.getHours() - oldTime.getHours();
      var minutes = nowTime.getMinutes() - oldTime.getMinutes();
      // console.log(oldTime, '\t', nowTime);
      this.timeDescribe = ''; // 重置
      if (minutes < 5 && hours <= 0 && day <= 0 && month <= 0 && year <= 0) {
        this.timeDescribe = '刚刚'
      } else if (hours <= 0 && day <= 0 && month <= 0 && year <= 0) {
        this.timeDescribe = minutes + '分钟前'
      } else if (day <= 0 && month <= 0 && year <= 0) {
        this.timeDescribe = hours + '小时' + minutes + '分钟前'
      } else if (month <= 0 && year <= 0) {
        this.timeDescribe = day + '天前'
      } else if (year <= 0) {
        this.timeDescribe = month + '月前'
      } else {
        this.timeDescribe = year + '年前'
      }
      return true;
    }
  }
}
</script>

<style scoped>
.comment-item {
  display: none;
}

.my-haveImage {
  margin-top: -110px;
  padding-left: 200px;
  height: 10%;
  overflow: hidden
}

.my-image {
  width: 190px;
  height: 105px;
  float: left;
  margin-bottom: 10px;

  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  border-radius: 10px;
}

.my-span p:hover {
  cursor: pointer; /*当鼠标在文本上时将光标I 改成手型*/
  color: rgba(0, 0, 0, 0.6);
}
</style>

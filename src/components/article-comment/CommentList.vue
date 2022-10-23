<template>
  <Comment @getArticle="getArticle" :postInfo="postInfo" :articleId="articleId"/>
</template>

<script>
import Comment from "@/components/article-comment/Comment";

export default {
  name: "CommentList",
  components: {Comment},
  props: {
    articleId: {
      type: Number,
      required: false
    },
    postInfo: {
      type: Object
    }
  },
  created() {
    // this.init()
  },
  watch: {
    //在文章路由到其它含有评论的页面时，要重新获取评论
    '$route.path'() {
      // this.init()
    }
  },
  methods: {
    getArticle() {
      this.$emit('getArticle')
    },
    init() {
      /*
            //重置评论表单位置
            this.$store.commit('parentCommentId', -1)
            this.$store.commit('articleId', this.articleId)
            this.$store.dispatch('getCommentList')
      */
    }
  }
}
</script>

<style scoped>

</style>

<template>
  <span>{{ this.name }}</span>
</template>

<script>
import {getParent} from "@/api/comment/commet";

export default {
  name: "ParentName",
  props: {
    commentId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      name: ''
    }
  },
  mounted() {
    /*this.name =*/ this.getParentName(this.commentId)
  },
  methods: {
    // 获取父评论信息
    getParentName(commentId) {
      // 将此评论发送给后台  查找父ID
      var userName = '';
      getParent(commentId).then((response) => {
        // console.log('父评论信息:', response);
        if (response && response.code === 200) {
          this.name = response.data.parentUser.name + ''
          // console.log(response.data.parentUser.name);
        }
      });
    },
  }
}
</script>

<style scoped>

</style>

<template>
  <!-- 关于作者 -->
  <div class="ui segments" style="margin-left: -15px"> <!--m-box-->
    <!--  头部  -->
    <div class="ui  segment"><!--secondary-->
      <i class="el-icon-s-custom icon"></i>关于作者
    </div>

    <!--  中间部分  -->
    <div class="ui segment">
      <div style="width: 70px; margin: 0; padding: 0;">
        <el-avatar shape="square" :size="68" :src="author.avatarURL">
          <a :href="author.authorHome"/>
        </el-avatar>
      </div>
      <div class="grid" style="margin-top: -55px;margin-left: 80px">
        <span style="font-size: 22px;font-weight: bolder;" class="author-link">
        <a :href="author.authorHome">
          {{ author.username }} <i>图标</i>
        </a>
        </span>
      </div>
      <div>
        <span style="margin-left: 80px">{{ author.signature }}</span>
      </div>
    </div>
    <!--  尾部  -->
    <div class="ui  segment">
    </div>
  </div>
</template>

<script>
export default {
  name: "Author",
  data() {
    return {
      author: {
        username: "正等正觉",
        authorHome: "https://www.zhihu.com/people/www.zhiyuan.com",
        avatarURL: "https://pica.zhimg.com/v2-8d69b1e8ea162d41c08d744bb68db08e_xl.jpg?source=32738c0c",
        signature: '个性签名'
      }
    }
  }
}
</script>

<style scoped>
.author-link a {
  color: black;
}
</style>

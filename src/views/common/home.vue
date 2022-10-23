<template>
  <div class="mod-home">
    <h1>数据概述</h1>

    <base-info ref="baseInfo"></base-info>

    <div style="margin-bottom: 20px;"></div>
    <!-- 跳转   -->
    <jump ref="Jump"></jump>

    <div style="margin-bottom: 30px"></div>
    <!--  数据统计  -->
    <statistics ref="Statistics"></statistics>
  </div>
</template>

<script>
import BaseInfo from './components/baseInfo'
import Jump from './components/Jump'
import Statistics from './components/Statistics'

export default {
  components: {Statistics, Jump, BaseInfo}
}
</script>

<style>
.mod-home {
  line-height: 1.5;
}
</style>


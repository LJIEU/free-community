<template>
  <div class="m-box">
    <el-row :gutter="12">
      <el-col :span="8" style="width: 25%">
        <el-card shadow="always">
          <h4 style="margin-bottom: 2px">会员充值</h4>
          <el-tag>总额</el-tag>
          <div class="content" v-if="baseDate">
            <span class="content-number">{{baseDate.recharge}}</span>
<!--            <el-divider/>-->
            <span>本月&nbsp;&nbsp;{{baseDate.rechargeMonth}} 元</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 25%">
        <el-card shadow="always">
          <h4 style="margin-bottom: 2px">用户量</h4>
          <el-tag>总量</el-tag>
          <div class="content" v-if="baseDate">
            <span class="content-number">{{baseDate.subscribers}}</span>
<!--            <el-divider/>-->
            <span>今日签到用户&nbsp;&nbsp;{{baseDate.signIn}} 人</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 25%">
        <el-card shadow="always">
          <h4 style="margin-bottom: 2px">帖子数</h4>
          <el-tag>全部</el-tag>
          <div class="content" v-if="baseDate">
            <span class="content-number">{{baseDate.postsNumber}}</span>
<!--            <el-divider/>-->
            <span>待审核&nbsp;&nbsp;{{baseDate.pending}} 条</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8" style="width: 25%">
        <el-card shadow="always">
          <h4 style="margin-bottom: 2px">新增用户</h4>
          <el-tag>今日</el-tag>
          <div class="content" v-if="baseDate">
            <span class="content-number">{{baseDate.newUser}}</span>
<!--            <el-divider/>-->
            <span>昨日数据&nbsp;&nbsp;{{baseDate.yesterdayUser}} 人</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'baseInfo',
  data () {
    return {
      baseDate: {
        recharge: 0.01, /* 充值 */
        rechargeMonth: 0, /* 本月充值 */
        subscribers: 110, /* 用户量 */
        signIn: 9, /* 签到人数 */
        postsNumber: 1000, /* 帖子数 */
        pending: 10, /* 审核 */
        newUser: 5, /* 新增用户 */
        yesterdayUser: 17 /* 昨日新增 */
      }
    }
  }
}
</script>

<style scoped>
.content-number{
  font-size: 38px;
  font-weight: bolder;
  margin-right: 10px;
}
</style>

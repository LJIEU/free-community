<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="文章标题" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button @click="getOnList()">查询所有上架</el-button>
        <el-button @click="getOffList()">查询所有下架</el-button>
        <el-button @click="getNotReviewedList()">查询未审核</el-button>
        <el-button @click="clear()" type="primary" icon="el-icon-refresh">重置查询</el-button>
        <el-button v-if="isAuth('app:invitation:all')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
      </el-form-item>
    </el-form>
    <h3 v-if="offList">查询所有下架</h3>
    <h3 v-else-if="onList">查询所有上架</h3>
    <h3 v-else-if="notReviewed">查询未审核</h3>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="invitationId"
        header-align="center"
        align="center"
        label="帖子ID">
      </el-table-column>

      <el-table-column
        prop="username"
        header-align="center"
        align="center"
        label="用户名称">
      </el-table-column>

      <el-table-column
        prop="avatar"
        width="250"
        header-align="center"
        align="center"
        label="用户头像">
        <template slot-scope="scope">
          <a :href="scope.row.avatar">
            <img :src="scope.row.avatar" style="width: 200px;height: 100px" alt=""/>
          </a>
        </template>
      </el-table-column>

      <el-table-column
        width="150px"
        prop="title"
        header-align="center"
        align="center"
        label="标题">
      </el-table-column>
      <el-table-column
        property="abc"
        show-overflow-tooltip
        prop="content"
        header-align="center"
        align="center"
        label="帖子内容">
        <template slot-scope="scope">
          <div>{{ scope.row.content }}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="document"
        header-align="center"
        align="center"
        label="文件">
        <template slot-scope="scope">
          <div v-if="scope.row.document">查看</div>
          <div v-else>无</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="pageviews"
        header-align="center"
        align="center"
        label="浏览量">
      </el-table-column>
      <el-table-column
        prop="comments"
        header-align="center"
        align="center"
        label="评论数">
      </el-table-column>
      <el-table-column
        prop="likes"
        header-align="center"
        align="center"
        label="点赞数">
      </el-table-column>
      <el-table-column
        prop="topic"
        header-align="center"
        align="center"
        label="话题">
      </el-table-column>
      <el-table-column
        prop="sort"
        header-align="center"
        align="center"
        label="排序">
      </el-table-column>
      <el-table-column
        prop="state"
        header-align="center"
        align="center"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state === 0" size="small" type="danger">下架</el-tag>
          <el-tag v-else-if="scope.row.state === 1" size="small" type="success">上架</el-tag>
          <el-tag v-else size="small" type="warning">待审核</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="address"
        header-align="center"
        align="center"
        label="发帖地址">
      </el-table-column>
      <el-table-column
        width="200px"
        prop="createTime"
        header-align="center"
        align="center"
        label="发布时间">
        <template slot-scope="scope">
          <div>
            <i class="el-icon-time"></i>
            <span v-text="scope.row.createTime"></span>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.invitationId)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.invitationId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
import AddOrUpdate from './invitation-add-or-update'

export default {
  name: 'invitation',
  data () {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      onList: false,
      offList: false,
      notReviewed: false
    }
  },
  components: {
    AddOrUpdate
  },
  created () {
    this.getDataList()
  },
  methods: {
    clear () {
      this.onList = false
      this.offList = false
      this.notReviewed = false
      this.getDataList()
    },
    // 查询未审核列表
    getNotReviewedList () {
      this.onList = false
      this.offList = false

      this.notReviewed = true
      this.getDataList()
    },

    // 查询下架列表
    getOffList () {
      this.onList = false
      this.notReviewed = false
      this.offList = true
      this.getDataList()
    },

    // 查询上架列表
    getOnList () {
      this.offList = false
      this.notReviewed = false

      this.onList = true
      this.getDataList()
    },
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/app/admin/invitation/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.dataForm.key,
          'onList': this.onList,
          'offList': this.offList,
          'notReviewed': this.notReviewed
        })
      }).then(({data: response}) => {
        if (response && response.code === 200) {
          this.dataList = response.data.page.list
          this.totalPage = response.data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle (id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 删除
    deleteHandle (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.invitationId
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/app/admin/invitation/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data: response}) => {
          if (response && response.code === 200) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(response.message)
          }
        })
      })
    }
  }
}
</script>

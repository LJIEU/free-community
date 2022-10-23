<template>
  <div class="mod-config">
    <el-tree
      :data="treeData"
      :props="defaultProps"
      ref="tree"
      @node-click="handleNodeClick"
      highlight-current
      style="float: left"></el-tree>
    <div style="float: right;width: 85%">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.name" size="small" placeholder="类别名称" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="dataForm.id" placeholder="ID" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
          <el-button v-if="isAuth('app:category:all')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
          <el-button v-if="isAuth('app:category:all')" type="danger" @click="deleteHandle()"
                     :disabled="dataListSelections.length <= 0">批量删除
          </el-button>
        </el-form-item>
      </el-form>
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
          prop="categoryId"
          header-align="center"
          align="center"
          label="分类ID">
        </el-table-column>
        <el-table-column
          prop="name"
          header-align="center"
          align="center"
          label="名称">
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
            <el-tag v-if="scope.row.state === 0" size="small" type="danger">禁用</el-tag>
            <el-tag v-else size="small">正常</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          header-align="center"
          align="center"
          label="创建时间">
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.categoryId)">修改</el-button>
            <el-button type="text" size="small" @click="deleteHandle(scope.row.categoryId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="text-align: center"
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
      <!-- 弹窗, 新增 / 修改 -->
      <add-or-update :treeCategory="this.treeData" v-if="addOrUpdateVisible" ref="addOrUpdate"
                     @refreshDataList="getDataList"></add-or-update>
    </div>
  </div>
</template>

<script>
import AddOrUpdate from './category-add-or-update'

export default {
  name: 'category',
  data () {
    return {
      dataForm: {
        name: '',
        id: null
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      treeData: [],
      defaultProps: {
        children: 'childes',
        label: 'name'
      },
      path: []
    }
  },
  components: {
    AddOrUpdate
  },
  activated () {
    this.getDataList()
  },
  created () {
    this.getTree()
  },
  methods: {
    getTree () {
      this.$http({
        url: this.$http.adornUrl('/app/admin/category/treeCategory'),
        method: 'GET'
      }).then(({data: response}) => {
        // console.log('树结构信息:', response)
        this.treeData = response.data.list
      })
    },
    handleNodeClick (data) {
      if (data.childes === null) {
        // console.log('当前节点信息:', data)
        this.dataForm.name = data.name
        this.dataForm.id = data.categoryId
        this.getDataList()
      } else {
        this.dataForm.name = ''
        this.dataForm.id = null
        this.getDataList()
      }
    },
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/app/admin/category/list'),
        method: 'GET',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'name': this.dataForm.name,
          'categoryId': this.dataForm.id
        })
      }).then(({data: response}) => {
        // console.log(response)
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
      // 将路径做好
      if (id) {
        // console.log('修改ID:', id)
        this.getPath(this.treeData, id)
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id, this.path)
        })
      } else {
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      }
    },
    getPath (tree, id) {
      this.$http({
        url: this.$http.adornUrl(`/app/admin/category/getCategoryPath/${id}`),
        method: 'GET'
      }).then(({data: response}) => {
        if (response && response.code === 200) {
          // console.log(response.data.path)
          var arry = response.data.path
          for (let i = 0; i < arry.length; i++) {
            this.path[i] = arry[i] + ''
          }
        } else {
          this.$message.error(response.message)
        }
      })
    },
    // 删除
    deleteHandle (id) {
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.categoryId
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/app/admin/category/delete'),
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

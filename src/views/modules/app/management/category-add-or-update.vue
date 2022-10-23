<template>
  <el-dialog
    :title="!dataForm.categoryId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="父类名称" prop="parentId">
        <!--        <el-cascader
                  v-model="dataForm.path"
                  :options="treeCategory"
                  :props="props"
                  clearable
                  @change="handleChange"></el-cascader>-->
        <el-cascader
          ref="categoryCas"
          @change="handleChange"
          :options="treeCategory"
          :props="{
            value: 'categoryId',
            label: 'name',
            children: 'childes',
            checkStrictly: true // 实现选择任意级别【但是只能点击左边的单选框选择】
         }"
          clearable></el-cascader>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="dataForm.state">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import category from './category'

export default {
  props: {
    treeCategory: {
      type: Array,
      required: true
    }
  },
  created () {
    // console.log('父组件传递:', this.treeCategory)
  },
  data () {
    return {
      visible: false,
      dataForm: {
        path: [],
        categoryId: 0,
        parentId: '',
        name: '',
        sort: '',
        icon: '',
        state: '',
        isDelete: '',
        createTime: ''
      },
      dataRule: {
        /*         parentId: [
                  {required: true, message: '父分类ID不能为空', trigger: 'blur'}
                ], */
        name: [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ],
        sort: [
          {required: true, message: '排序不能为空', trigger: 'blur'}
        ],
        state: [
          {required: true, message: '状态【0:不显示  1:显示】不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    handleChange (value) {
      this.dataForm.path = value
      this.dataForm.parentId = value[value.length - 1]
      // console.log('获取parentID:', value, '\t', this.dataForm.parentId)
      /*
            setInterval(function () {
              document.querySelectorAll('.el-cascader-node__label').forEach(el => {
                el.onclick = function () {
                  if (this.previousElementSibling) this.previousElementSibling.click()
                }
              })
            }, 1000)
      */
    },
    init (id, path) {
      this.dataForm.categoryId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.categoryId) {
          this.$http({
            url: this.$http.adornUrl(`/app/admin/category/info/${this.dataForm.categoryId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data: response}) => {
            if (response && response.code === 200) {
              // console.log('路径信息:', path)
              this.dataForm.path = path
              this.dataForm.parentId = response.data.category.parentId
              this.dataForm.name = response.data.category.name
              this.dataForm.sort = response.data.category.sort
              this.dataForm.icon = response.data.category.icon
              this.dataForm.state = response.data.category.state
              this.dataForm.isDelete = response.data.category.isDelete
              this.dataForm.createTime = response.data.category.createTime
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        // console.log('提交信息:', this.dataForm)
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/app/admin/category/${!this.dataForm.categoryId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'categoryId': this.dataForm.categoryId || undefined,
              'parentId': this.dataForm.parentId,
              'name': this.dataForm.name,
              'sort': this.dataForm.sort,
              'icon': this.dataForm.icon,
              'state': this.dataForm.state,
              'isDelete': this.dataForm.isDelete,
              'createTime': this.dataForm.createTime
            })
          }).then(({data: response}) => {
            if (response && response.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                  category.getTree()
                }
              })
            } else {
              this.$message.error(response.message)
            }
          })
        }
      })
    }
  }
}
</script>

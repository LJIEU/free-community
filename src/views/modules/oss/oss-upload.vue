<template>
  <el-dialog
    title="上传文件"
    :close-on-click-modal="false"
    @close="closeHandle"
    :visible.sync="visible">
    <el-upload
      drag
      :action="url"
      :before-upload="beforeUploadHandle"
      :on-success="successHandle"
      multiple
      :file-list="fileList"
      style="text-align: center;">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
    </el-upload>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      url: '',
      num: 0,
      successNum: 0,
      fileList: [],
      // isUpload: false
      headers: {token: sessionStorage.getItem('token')}

    }
  },
  methods: {
    init (id) {
      this.url = this.$http.adornUrl(`/sys/oss/upload?token=${this.$cookie.get('token')}`)
      this.visible = true
    },
    // 上传之前
    beforeUploadHandle (file) {
      if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
        this.$message.error('只支持jpg、png、gif格式的图片！')
        return false
      }
      this.num++
    },
    // 上传成功
    successHandle (response, file, fileList) {
      this.fileList = fileList
      this.successNum++
      console.log('上传反馈:', response)
      if (response && response.code === 200) {
        if (this.num === this.successNum) {
          this.$confirm('操作成功, 是否继续操作?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500
            })
          }).catch(() => {
            this.visible = false
            this.$message({
              message: '取消操作',
              type: 'info'
            })
          })
        }
      } else {
        this.$message.error(response.message)
      }
    },
    // 弹窗关闭时
    closeHandle () {
      this.fileList = []
      this.$emit('refreshDataList')
    }
  }
}
</script>

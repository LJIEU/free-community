<template>
  <el-dialog
    :title="!dataForm.invitationId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="帖子内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="帖子内容"></el-input>
    </el-form-item>
    <el-form-item label="文件" prop="document">
      <el-input v-model="dataForm.document" placeholder="文件"></el-input>
    </el-form-item>
    <el-form-item label="浏览量" prop="pageviews">
      <el-input v-model="dataForm.pageviews" placeholder="浏览量"></el-input>
    </el-form-item>
    <el-form-item label="评论数" prop="comments">
      <el-input v-model="dataForm.comments" placeholder="评论数"></el-input>
    </el-form-item>
    <el-form-item label="点赞数" prop="likes">
      <el-input v-model="dataForm.likes" placeholder="点赞数"></el-input>
    </el-form-item>
    <el-form-item label="话题" prop="topic">
      <el-input v-model="dataForm.topic" placeholder="话题"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="state">
      <el-radio-group v-model="dataForm.state">
        <el-radio :label="1">上架</el-radio>
        <el-radio :label="0">下架</el-radio>
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
  export default {
    name: 'invitation-add-or-update',
    data () {
      return {
        visible: false,
        dataForm: {
          invitationId: 0,
          title: '',
          content: '',
          document: '',
          pageviews: '',
          comments: '',
          likes: '',
          topic: '',
          sort: '',
          state: '',
          address: '',
          createTime: '',
          isDelete: ''
        },
        dataRule: {
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '帖子内容不能为空', trigger: 'blur' }
          ],
/*           document: [
            { required: true, message: '文件不能为空', trigger: 'blur' }
          ], */
          pageviews: [
            { required: true, message: '浏览量不能为空', trigger: 'blur' }
          ],
          comments: [
            { required: true, message: '评论数不能为空', trigger: 'blur' }
          ],
          likes: [
            { required: true, message: '点赞数不能为空', trigger: 'blur' }
          ],
          topic: [
            { required: true, message: '话题不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ],
          state: [
            { required: true, message: '状态【0: 不显示  1:显示】不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.invitationId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.invitationId) {
            this.$http({
              url: this.$http.adornUrl(`/app/admin/invitation/info/${this.dataForm.invitationId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data: response}) => {
              if (response && response.code === 200) {
                this.dataForm.title = response.data.invitation.title
                this.dataForm.content = response.data.invitation.content
                this.dataForm.document = response.data.invitation.document
                this.dataForm.pageviews = response.data.invitation.pageviews
                this.dataForm.comments = response.data.invitation.comments
                this.dataForm.likes = response.data.invitation.likes
                this.dataForm.topic = response.data.invitation.topic
                this.dataForm.sort = response.data.invitation.sort
                this.dataForm.state = response.data.invitation.state
                this.dataForm.address = response.data.invitation.address
                this.dataForm.createTime = response.data.invitation.createTime
                this.dataForm.isDelete = response.data.invitation.isDelete
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/app/admin/invitation/${!this.dataForm.invitationId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'invitationId': this.dataForm.invitationId || undefined,
                'title': this.dataForm.title,
                'content': this.dataForm.content,
                'document': this.dataForm.document,
                'pageviews': this.dataForm.pageviews,
                'comments': this.dataForm.comments,
                'likes': this.dataForm.likes,
                'topic': this.dataForm.topic,
                'sort': this.dataForm.sort,
                'state': this.dataForm.state,
                'address': this.dataForm.address,
                'createTime': this.dataForm.createTime,
                'isDelete': this.dataForm.isDelete
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

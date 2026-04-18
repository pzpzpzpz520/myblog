<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span>站点资料</span>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" maxlength="64" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="头衔" prop="title">
              <el-input v-model="form.title" maxlength="128" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="学校" prop="university">
              <el-input v-model="form.university" maxlength="128" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专业" prop="major">
              <el-input v-model="form.major" maxlength="128" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="城市" prop="location">
              <el-input v-model="form.location" maxlength="64" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" maxlength="128" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="个人简介" prop="bio">
          <el-input v-model="form.bio" type="textarea" :rows="3" maxlength="255" show-word-limit />
        </el-form-item>
        <el-form-item label="关注方向" prop="focusAreas">
          <el-input v-model="form.focusAreas" type="textarea" :rows="2" placeholder="逗号分隔，例如：Java 后端,系统稳定性" />
        </el-form-item>
        <el-form-item label="亮点信息" prop="highlights">
          <el-input v-model="form.highlights" type="textarea" :rows="2" placeholder="逗号分隔，例如：GPA 3.6,企业核心系统经验" />
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-hasPermi="['blog:profile:edit']">保 存</el-button>
        <el-button @click="getInfo">重 置</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getProfile, updateProfile } from '@/api/blog/profile'

export default {
  name: 'BlogProfile',
  data() {
    return {
      form: {
        id: 1,
        name: '',
        title: '',
        university: '',
        major: '',
        location: '',
        email: '',
        bio: '',
        focusAreas: '',
        highlights: ''
      },
      rules: {
        name: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '头衔不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getInfo()
  },
  methods: {
    getInfo() {
      getProfile().then(response => {
        this.form = response.data || this.form
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return
        }
        updateProfile(this.form).then(() => {
          this.$modal.msgSuccess('保存成功')
          this.getInfo()
        })
      })
    }
  }
}
</script>

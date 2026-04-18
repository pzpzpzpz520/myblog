<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键词" prop="keyword">
        <el-input
          v-model="queryParams.keyword"
          placeholder="标题/摘要"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select v-model="queryParams.categoryId" placeholder="全部分类" clearable style="width: 200px">
          <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="全部状态" clearable style="width: 180px">
          <el-option label="已发布" :value="1" />
          <el-option label="已下线" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['blog:article:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['blog:article:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['blog:article:remove']">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="articleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" prop="id" align="center" width="80" />
      <el-table-column label="标题" prop="title" min-width="220" :show-overflow-tooltip="true" />
      <el-table-column label="分类" prop="categoryName" align="center" width="120" />
      <el-table-column label="标签" min-width="180">
        <template slot-scope="scope">
          <el-tag v-for="tag in scope.row.tags || []" :key="tag" type="info" size="mini" class="mr5">{{ tag }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发布日期" prop="publishDate" align="center" width="120" />
      <el-table-column label="阅读时长" prop="readingTime" align="center" width="100">
        <template slot-scope="scope">{{ scope.row.readingTime }} 分钟</template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="110">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handlePublish(scope.row)"
            v-hasPermi="['blog:article:publish']"
          />
          <span class="status-text">{{ scope.row.status === 1 ? '已发布' : '已下线' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['blog:article:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['blog:article:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="96px">
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="文章标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入文章标题" maxlength="128" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="文章分类" prop="categoryId">
              <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
                <el-option v-for="item in categoryOptions" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="发布状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio :label="1">发布</el-radio>
                <el-radio :label="0">下线</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="阅读时长" prop="readingTime">
              <el-input-number v-model="form.readingTime" :min="1" :max="120" :step="1" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="发布日期" prop="publishDate">
              <el-date-picker v-model="form.publishDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="封面地址" prop="coverImage">
              <el-input v-model="form.coverImage" placeholder="https://..." maxlength="255" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="标签" prop="tagIds">
          <el-select v-model="form.tagIds" multiple filterable clearable placeholder="请选择标签" style="width: 100%">
            <el-option v-for="item in tagOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="文章摘要" prop="summary">
          <el-input v-model="form.summary" type="textarea" :rows="3" placeholder="请输入摘要" maxlength="255" show-word-limit />
        </el-form-item>
        <el-form-item label="Markdown" prop="contentMarkdown">
          <el-input v-model="form.contentMarkdown" type="textarea" :rows="12" placeholder="请输入文章正文（Markdown）" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArticle, getArticle, addArticle, updateArticle, delArticle, publishArticle } from '@/api/blog/article'
import { listCategory } from '@/api/blog/category'
import { listTag } from '@/api/blog/tag'

export default {
  name: 'BlogArticle',
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      articleList: [],
      categoryOptions: [],
      tagOptions: [],
      title: '',
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: undefined,
        categoryId: undefined,
        status: undefined
      },
      form: {},
      rules: {
        title: [{ required: true, message: '文章标题不能为空', trigger: 'blur' }],
        summary: [{ required: true, message: '文章摘要不能为空', trigger: 'blur' }],
        contentMarkdown: [{ required: true, message: '文章正文不能为空', trigger: 'blur' }],
        categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }],
        publishDate: [{ required: true, message: '请选择发布日期', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getCategoryOptions()
    this.getTagOptions()
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listArticle(this.queryParams).then(response => {
        this.articleList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    getCategoryOptions() {
      listCategory({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.categoryOptions = response.rows || []
      })
    },
    getTagOptions() {
      listTag({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.tagOptions = response.rows || []
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: undefined,
        title: undefined,
        summary: undefined,
        contentMarkdown: undefined,
        coverImage: undefined,
        categoryId: undefined,
        publishDate: this.parseTime(new Date(), '{y}-{m}-{d}'),
        readingTime: 5,
        status: 0,
        tagIds: []
      }
      this.resetForm('form')
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '新增文章'
    },
    handleUpdate(row) {
      this.reset()
      const articleId = row.id || this.ids[0]
      getArticle(articleId).then(response => {
        this.form = Object.assign({}, response.data, { tagIds: response.data.tagIds || [] })
        this.open = true
        this.title = '修改文章'
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return
        }
        const action = this.form.id ? updateArticle : addArticle
        action(this.form).then(() => {
          this.$modal.msgSuccess(this.form.id ? '修改成功' : '新增成功')
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const articleIds = row.id ? row.id : this.ids.join(',')
      this.$modal.confirm('确认删除文章编号为 "' + articleIds + '" 的数据项？').then(() => {
        return delArticle(articleIds)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      }).catch(() => {})
    },
    handlePublish(row) {
      const text = row.status === 1 ? '发布' : '下线'
      publishArticle(row.id, row.status).then(() => {
        this.$modal.msgSuccess(text + '成功')
      }).catch(() => {
        row.status = row.status === 1 ? 0 : 1
      })
    }
  }
}
</script>

<style scoped>
.mr5 {
  margin-right: 5px;
}

.status-text {
  margin-left: 8px;
}
</style>

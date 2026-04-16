<template>
  <section class="page-intro surface-card reveal">
    <p class="eyebrow">BLOG INDEX</p>
    <h1>文章归档</h1>
    <p>分类、标签和搜索条件都从后端内容接口驱动；未来接入后台后，可以继续扩展文章管理、栏目维护和展示编排。</p>
  </section>

  <section class="toolbar surface-card reveal">
    <div class="filters">
      <label>
        <span>分类</span>
        <select v-model="selectedCategoryId">
          <option value="">全部分类</option>
          <option v-for="category in siteStore.categories" :key="category.id" :value="String(category.id)">
            {{ category.name }}
          </option>
        </select>
      </label>

      <label class="keyword">
        <span>搜索</span>
        <input v-model="query.keyword" type="text" placeholder="搜索文章标题或摘要" />
      </label>

      <button type="button" class="button-primary" @click="submitFilters">筛选内容</button>
    </div>

    <div class="summary">
      <strong>{{ page.total }}</strong>
      <span>篇内容</span>
    </div>
  </section>

  <section class="tag-panel surface-card reveal">
    <div class="section-heading">
      <div>
        <p class="eyebrow">TAGS</p>
        <h2>标签筛选</h2>
      </div>
      <button v-if="query.tagId" type="button" class="button-secondary" @click="clearTag">清除标签</button>
    </div>
    <TagCloud :tags="siteStore.tags" :active-tag-id="query.tagId" @select="selectTag" />
  </section>

  <section v-if="page.records.length" class="article-grid">
    <ArticleCard v-for="article in page.records" :key="article.id" :article="article" />
  </section>

  <section v-else class="surface-card empty-state">
    <h2>当前筛选条件下还没有内容</h2>
    <p>可以尝试更换分类、清空搜索条件，或者确认数据库中是否已经写入文章、分类与标签数据。</p>
  </section>

  <section class="pager">
    <button type="button" class="button-secondary" :disabled="query.pageNum <= 1" @click="changePage(-1)">上一页</button>
    <span>第 {{ query.pageNum }} 页 / 共 {{ totalPages }} 页</span>
    <button
      type="button"
      class="button-secondary"
      :disabled="query.pageNum >= totalPages"
      @click="changePage(1)"
    >
      下一页
    </button>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import ArticleCard from '@/components/blog/ArticleCard.vue';
import TagCloud from '@/components/blog/TagCloud.vue';
import { fetchArticles } from '@/api/blog';
import { useSiteStore } from '@/stores/site';
import type { ArticleQuery, ArticleSummary, PageResult } from '@/types/blog';

const route = useRoute();
const siteStore = useSiteStore();

const query = reactive<ArticleQuery>({
  pageNum: 1,
  pageSize: 6,
  keyword: ''
});

const selectedCategoryId = ref('');

const page = ref<PageResult<ArticleSummary>>({
  pageNum: 1,
  pageSize: 6,
  total: 0,
  records: []
});

const totalPages = computed(() => Math.max(1, Math.ceil(page.value.total / query.pageSize)));

async function loadArticles() {
  query.categoryId = selectedCategoryId.value ? Number(selectedCategoryId.value) : undefined;
  page.value = await fetchArticles(query);
}

function submitFilters() {
  query.pageNum = 1;
  void loadArticles();
}

function selectTag(tagId: number) {
  query.tagId = tagId;
  query.pageNum = 1;
  void loadArticles();
}

function clearTag() {
  query.tagId = undefined;
  query.pageNum = 1;
  void loadArticles();
}

function changePage(delta: number) {
  query.pageNum += delta;
  void loadArticles();
}

watch(
  () => route.query.tagId,
  (tagId) => {
    query.tagId = tagId ? Number(tagId) : undefined;
    query.pageNum = 1;
    if (siteStore.home) {
      void loadArticles();
    }
  },
  { immediate: true }
);

onMounted(async () => {
  if (!siteStore.home) {
    await siteStore.bootstrap();
  }
  await loadArticles();
});
</script>

<style scoped>
.toolbar,
.tag-panel {
  margin-top: 1.4rem;
  padding: 1.5rem;
}

.filters {
  display: flex;
  flex-wrap: wrap;
  align-items: end;
  gap: 1rem;
}

.filters label {
  display: grid;
  gap: 0.45rem;
}

.filters label span {
  color: var(--text-muted);
  font-size: 0.92rem;
}

.filters input,
.filters select {
  min-width: 210px;
  padding: 0.95rem 1rem;
  border: 1px solid var(--line-soft);
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.82);
}

.keyword input {
  min-width: min(420px, 70vw);
}

.toolbar {
  display: flex;
  justify-content: space-between;
  gap: 1.2rem;
  align-items: center;
}

.summary {
  min-width: 120px;
  text-align: right;
}

.summary strong {
  display: block;
  font-size: 2.2rem;
  line-height: 1;
}

.summary span {
  color: var(--text-muted);
}

.article-grid {
  display: grid;
  gap: 1rem;
  margin-top: 1.4rem;
}

.pager {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  padding: 1.8rem 0 0.2rem;
}

.pager span {
  color: var(--text-secondary);
}

.pager button:disabled {
  cursor: not-allowed;
  opacity: 0.55;
  transform: none;
}

@media (max-width: 920px) {
  .toolbar {
    flex-direction: column;
    align-items: flex-start;
  }

  .summary {
    text-align: left;
  }
}
</style>

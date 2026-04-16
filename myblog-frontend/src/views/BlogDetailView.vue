<template>
  <article v-if="detail" class="detail">
    <section class="hero surface-card reveal">
      <img :src="detail.coverImage" :alt="detail.title" class="hero-image" />
      <div class="hero-copy">
        <p class="eyebrow">ARTICLE</p>
        <div class="meta">
          <span>{{ detail.categoryName }}</span>
          <span>{{ formatDate(detail.publishDate) }}</span>
          <span>{{ detail.readingTime }} 分钟阅读</span>
        </div>
        <h1>{{ detail.title }}</h1>
        <p class="summary">{{ detail.summary }}</p>
        <div class="tag-list">
          <span v-for="tag in detail.tags" :key="tag"># {{ tag }}</span>
        </div>
      </div>
    </section>

    <section class="content-card surface-card reveal">
      <div class="markdown" v-html="renderMarkdown(detail.content)"></div>
    </section>

    <footer class="article-footer surface-card reveal">
      <RouterLink v-if="detail.previousArticleId" :to="`/blog/${detail.previousArticleId}`" class="footer-link">
        上一篇：{{ detail.previousArticleTitle }}
      </RouterLink>
      <RouterLink v-if="detail.nextArticleId" :to="`/blog/${detail.nextArticleId}`" class="footer-link">
        下一篇：{{ detail.nextArticleTitle }}
      </RouterLink>
    </footer>
  </article>

  <section v-else-if="errorMessage" class="surface-card empty-state">
    <h2>文章内容暂时不可用</h2>
    <p>{{ errorMessage }}</p>
  </section>

  <section v-else class="surface-card empty-state">
    <h2>正在加载文章</h2>
    <p>正在从后端内容接口获取文章详情。</p>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { fetchArticleDetail } from '@/api/blog';
import type { ArticleDetail } from '@/types/blog';
import { formatDate } from '@/utils/date';
import { renderMarkdown } from '@/utils/markdown';

const route = useRoute();
const detail = ref<ArticleDetail | null>(null);
const errorMessage = ref('');

async function loadDetail() {
  errorMessage.value = '';

  try {
    detail.value = await fetchArticleDetail(String(route.params.id));
  } catch {
    detail.value = null;
    errorMessage.value = '请确认内容服务已启动，并且数据库中存在对应文章记录。';
  }
}

watch(
  () => route.params.id,
  () => {
    void loadDetail();
  }
);

onMounted(async () => {
  await loadDetail();
});
</script>

<style scoped>
.detail {
  display: grid;
  gap: 1.3rem;
}

.hero,
.content-card,
.article-footer {
  padding: 1.4rem;
}

.hero-image {
  width: 100%;
  height: min(420px, 45vw);
  min-height: 240px;
  border-radius: 26px;
  object-fit: cover;
}

.hero-copy {
  padding-top: 1.3rem;
}

.meta,
.tag-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.7rem;
  color: var(--text-muted);
}

.hero-copy h1 {
  margin: 0.9rem 0 0.8rem;
  font-size: clamp(2.1rem, 4vw, 3.6rem);
  line-height: 1.05;
}

.summary {
  max-width: 54rem;
  color: var(--text-secondary);
  font-size: 1.06rem;
}

.tag-list span {
  padding: 0.45rem 0.72rem;
  border-radius: 999px;
  background: rgba(201, 157, 86, 0.12);
  color: var(--accent-700);
}

.markdown {
  color: var(--text-primary);
  line-height: 1.85;
}

.markdown :deep(h1),
.markdown :deep(h2) {
  margin-bottom: 0.8rem;
  color: var(--brand-700);
}

.markdown :deep(p) {
  color: var(--text-secondary);
}

.markdown :deep(ul) {
  color: var(--text-secondary);
}

.markdown :deep(code) {
  padding: 0.15rem 0.4rem;
  border-radius: 8px;
  background: rgba(63, 106, 151, 0.12);
  color: var(--brand-700);
}

.article-footer {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
}

.footer-link {
  display: inline-flex;
  align-items: center;
  min-height: 52px;
  padding: 0 1rem;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(63, 106, 151, 0.12);
}

@media (max-width: 760px) {
  .article-footer {
    flex-direction: column;
  }
}
</style>

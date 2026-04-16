<template>
  <article class="card surface-card">
    <div class="cover-wrap">
      <img :src="article.coverImage" :alt="article.title" class="cover" />
      <span class="category">{{ article.categoryName }}</span>
    </div>

    <div class="body">
      <div class="meta">
        <span>{{ formatDate(article.publishDate) }}</span>
        <span>{{ article.readingTime }} 分钟阅读</span>
      </div>

      <h3>{{ article.title }}</h3>
      <p class="summary">{{ article.summary }}</p>

      <div class="tags">
        <span v-for="tag in article.tags" :key="tag"># {{ tag }}</span>
      </div>

      <RouterLink class="read-more" :to="`/blog/${article.id}`">阅读全文</RouterLink>
    </div>
  </article>
</template>

<script setup lang="ts">
import type { ArticleSummary } from '@/types/blog';
import { formatDate } from '@/utils/date';

defineProps<{
  article: ArticleSummary;
}>();
</script>

<style scoped>
.card {
  display: grid;
  grid-template-columns: 260px minmax(0, 1fr);
  gap: 1.2rem;
  padding: 1rem;
  transition:
    transform 180ms ease,
    box-shadow 180ms ease;
}

.card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-strong);
}

.cover-wrap {
  position: relative;
  overflow: hidden;
  min-height: 220px;
  border-radius: 22px;
}

.cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.cover-wrap::after {
  content: "";
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, transparent 40%, rgba(18, 28, 41, 0.38));
}

.category {
  position: absolute;
  top: 1rem;
  left: 1rem;
  z-index: 1;
  display: inline-flex;
  padding: 0.48rem 0.78rem;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.88);
  color: var(--brand-700);
  font-size: 0.84rem;
  font-weight: 700;
}

.body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0.3rem 0.35rem 0.3rem 0;
}

.meta,
.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.7rem;
  color: var(--text-muted);
  font-size: 0.9rem;
}

.body h3 {
  margin: 0.8rem 0 0.75rem;
  font-size: clamp(1.35rem, 2.2vw, 1.7rem);
  line-height: 1.2;
}

.summary {
  margin-bottom: 1.1rem;
  color: var(--text-secondary);
}

.tags span {
  padding: 0.4rem 0.7rem;
  border-radius: 999px;
  background: rgba(201, 157, 86, 0.12);
  color: var(--accent-700);
}

.read-more {
  align-self: flex-start;
  margin-top: 1.2rem;
  color: var(--brand-700);
  font-weight: 700;
}

@media (max-width: 768px) {
  .card {
    grid-template-columns: 1fr;
  }

  .cover-wrap {
    min-height: 210px;
  }

  .body {
    padding-right: 0;
  }
}
</style>

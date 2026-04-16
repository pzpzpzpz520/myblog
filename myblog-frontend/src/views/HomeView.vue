<template>
  <template v-if="home && hasHomeContent">
    <section class="hero">
      <div class="hero-main surface-card reveal">
        <p class="eyebrow">PERSONAL ENGINEERING BLOG</p>
        <h1>{{ home.profile.name || 'MyBlog' }}</h1>
        <h2>{{ home.profile.title || '内容由 MySQL 驱动的个人技术博客' }}</h2>
        <p class="hero-copy">
          {{ home.profile.bio || '前端页面基于聚合接口渲染，博客内容、站点区块和展示模块都为后续后台管理预留了动态扩展能力。' }}
        </p>

        <div class="hero-actions">
          <RouterLink class="button-primary" to="/blog">阅读文章</RouterLink>
          <RouterLink class="button-secondary" to="/about">查看经历</RouterLink>
        </div>

        <div class="highlight-list">
          <span v-for="item in home.profile.highlights" :key="item" class="chip-button">{{ item }}</span>
        </div>
      </div>

      <aside class="hero-side surface-card reveal">
        <div v-for="item in stats" :key="item.label" class="stat-item">
          <span>{{ item.label }}</span>
          <strong>{{ item.value }}</strong>
        </div>

        <div class="focus-block">
          <p class="eyebrow">FOCUS</p>
          <div class="focus-chips">
            <span v-for="focus in home.profile.focusAreas" :key="focus">{{ focus }}</span>
          </div>
        </div>
      </aside>
    </section>

    <section class="section">
      <div class="section-heading">
        <div>
          <p class="eyebrow">LATEST</p>
          <h2>最近更新</h2>
        </div>
        <RouterLink class="button-secondary" to="/blog">查看全部</RouterLink>
      </div>

      <div class="article-grid">
        <ArticleCard v-for="article in home.latestArticles" :key="article.id" :article="article" />
      </div>
    </section>

    <section v-for="section in home.homeSections" :key="section.key" class="module surface-card reveal">
      <div class="section-heading">
        <div>
          <p class="eyebrow">{{ moduleText(section, 'eyebrow') || 'MODULE' }}</p>
          <h3>{{ section.title }}</h3>
        </div>
        <p v-if="moduleText(section, 'description')">{{ moduleText(section, 'description') }}</p>
      </div>

      <div v-if="section.moduleType === 'timeline'" class="timeline">
        <article v-for="item in timelineItems(section)" :key="`${item.period}-${item.title}`" class="timeline-item">
          <span>{{ item.period }}</span>
          <h4>{{ item.title }}</h4>
          <p>{{ item.summary }}</p>
        </article>
      </div>

      <div v-else-if="section.moduleType === 'feature-grid'" class="feature-grid">
        <article v-for="card in featureCards(section)" :key="card.title" class="feature-card">
          <h4>{{ card.title }}</h4>
          <p>{{ card.text }}</p>
        </article>
      </div>

      <div v-else-if="section.moduleType === 'column-list'" class="column-grid">
        <article v-for="column in columns(section)" :key="column.name" class="column-card">
          <h4>{{ column.name }}</h4>
          <p>{{ column.description }}</p>
        </article>
      </div>
    </section>
  </template>

  <section v-else class="surface-card empty-state">
    <h2>站点内容暂未加载</h2>
    <p>当前页面已切换为数据库驱动模式。请确认 MySQL 初始化完成、内容服务可访问，并已写入站点资料、文章和模块数据。</p>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import ArticleCard from '@/components/blog/ArticleCard.vue';
import { useSiteStore } from '@/stores/site';
import type { HomeSection } from '@/types/blog';
import { moduleList, moduleText } from '@/utils/siteModules';

interface TimelineItem {
  period: string;
  title: string;
  summary: string;
}

interface FeatureCard {
  title: string;
  text: string;
}

interface ColumnItem {
  name: string;
  description: string;
}

const siteStore = useSiteStore();

onMounted(async () => {
  if (!siteStore.home) {
    await siteStore.bootstrap();
  }
});

const home = computed(() => siteStore.home);

const hasHomeContent = computed(() => {
  if (!home.value) {
    return false;
  }

  return Boolean(
    home.value.profile.name ||
      home.value.latestArticles.length ||
      home.value.homeSections.length ||
      home.value.navigation.length
  );
});

const stats = computed(() => [
  { label: '文章数量', value: String(home.value?.articleCount ?? 0) },
  { label: '栏目数量', value: String(home.value?.categories.length ?? 0) },
  { label: '标签数量', value: String(home.value?.tags.length ?? 0) }
]);

function timelineItems(section: HomeSection) {
  return moduleList<TimelineItem>(section, 'items');
}

function featureCards(section: HomeSection) {
  return moduleList<FeatureCard>(section, 'cards');
}

function columns(section: HomeSection) {
  return moduleList<ColumnItem>(section, 'columns');
}
</script>

<style scoped>
.hero {
  display: grid;
  grid-template-columns: minmax(0, 1.3fr) minmax(300px, 0.7fr);
  gap: 1.25rem;
}

.hero-main,
.hero-side,
.module {
  padding: 2rem;
}

.hero-main h1 {
  margin-bottom: 0.5rem;
  font-size: clamp(2.8rem, 6vw, 4.9rem);
  line-height: 0.95;
}

.hero-main h2 {
  margin-bottom: 1rem;
  color: var(--brand-700);
  font-size: clamp(1.2rem, 2.3vw, 1.6rem);
}

.hero-copy {
  max-width: 46rem;
  color: var(--text-secondary);
  font-size: 1.05rem;
}

.hero-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
  margin: 1.6rem 0;
}

.highlight-list,
.focus-chips {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
}

.hero-side {
  display: grid;
  gap: 1rem;
  align-content: start;
}

.stat-item {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  padding-bottom: 0.95rem;
  border-bottom: 1px solid var(--line-soft);
}

.stat-item span {
  color: var(--text-muted);
}

.stat-item strong {
  font-size: 1.35rem;
}

.focus-block {
  margin-top: 0.75rem;
}

.focus-chips span {
  padding: 0.72rem 0.95rem;
  border-radius: 999px;
  background: rgba(63, 106, 151, 0.1);
  color: var(--brand-700);
}

.section {
  margin-top: 1.7rem;
}

.article-grid,
.feature-grid,
.column-grid {
  display: grid;
  gap: 1rem;
}

.feature-grid {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.column-grid {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.timeline {
  display: grid;
  gap: 1rem;
}

.timeline-item,
.feature-card,
.column-card {
  padding: 1.35rem;
  border-radius: var(--radius-md);
  background: rgba(255, 255, 255, 0.68);
  border: 1px solid rgba(63, 106, 151, 0.1);
}

.timeline-item span {
  color: var(--accent-700);
  font-weight: 700;
}

.timeline-item h4,
.feature-card h4,
.column-card h4 {
  margin: 0.75rem 0 0.5rem;
}

.timeline-item p,
.feature-card p,
.column-card p {
  margin-bottom: 0;
  color: var(--text-secondary);
}

.module {
  margin-top: 1.6rem;
}

@media (max-width: 960px) {
  .hero,
  .feature-grid,
  .column-grid {
    grid-template-columns: 1fr;
  }
}
</style>

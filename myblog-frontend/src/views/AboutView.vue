<template>
  <template v-if="home && hasAboutContent">
    <section class="page-intro surface-card reveal">
      <p class="eyebrow">ABOUT</p>
      <h1>{{ home.profile.name || '关于这个站点' }}</h1>
      <p>
        {{ home.profile.bio || '这里展示的是由 MySQL 内容数据驱动的关于页模块，包括教育背景、工作经历、技能与项目经验，后续可以直接接入后台进行维护。' }}
      </p>
    </section>

    <section class="summary-grid">
      <article class="surface-card summary-card">
        <span>学历背景</span>
        <strong>{{ home.profile.university || '待配置' }}</strong>
        <p>{{ home.profile.major || '请在数据库中维护个人资料信息。' }}</p>
      </article>
      <article class="surface-card summary-card">
        <span>联系邮箱</span>
        <strong>{{ home.profile.email || '待配置' }}</strong>
        <p>{{ home.profile.location || '请在数据库中维护联系信息。' }}</p>
      </article>
      <article class="surface-card summary-card">
        <span>关注方向</span>
        <strong>{{ home.profile.focusAreas.length ? home.profile.focusAreas.length : 0 }} 项</strong>
        <p>后续后台接入后，可继续动态维护重点方向、展示模块和介绍文案。</p>
      </article>
    </section>

    <section v-for="section in home.aboutSections" :key="section.key" class="module surface-card reveal">
      <div class="section-heading">
        <div>
          <p class="eyebrow">{{ moduleText(section, 'eyebrow') || 'SECTION' }}</p>
          <h2>{{ section.title }}</h2>
        </div>
      </div>

      <div v-if="section.moduleType === 'detail-list'" class="detail-list">
        <article v-for="item in detailItems(section)" :key="item.label" class="detail-item">
          <span>{{ item.label }}</span>
          <strong>{{ item.value }}</strong>
        </article>
      </div>

      <div v-else-if="section.moduleType === 'experience-list'" class="experience-list">
        <article v-for="item in experiences(section)" :key="item.title" class="experience-item">
          <div class="experience-head">
            <h3>{{ item.title }}</h3>
            <span>{{ item.period }}</span>
          </div>
          <ul>
            <li v-for="point in item.points" :key="point">{{ point }}</li>
          </ul>
        </article>
      </div>

      <div v-else-if="section.moduleType === 'badge-list'" class="badge-list">
        <span v-for="item in badges(section)" :key="item" class="chip-button">{{ item }}</span>
      </div>

      <div v-else-if="section.moduleType === 'project-showcase'" class="project-grid">
        <article v-for="project in projects(section)" :key="project.name" class="project-card">
          <h3>{{ project.name }}</h3>
          <p class="project-summary">{{ project.summary }}</p>
          <div class="stack-list">
            <span v-for="stack in project.stack" :key="stack">{{ stack }}</span>
          </div>
          <ul>
            <li v-for="highlight in project.highlights" :key="highlight">{{ highlight }}</li>
          </ul>
        </article>
      </div>
    </section>
  </template>

  <section v-else class="surface-card empty-state">
    <h2>关于页内容尚未配置</h2>
    <p>当前关于页已改为纯数据库驱动。请在 MySQL 中补齐个人资料和关于页模块后，这里会自动展示真实内容。</p>
  </section>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useSiteStore } from '@/stores/site';
import type { AboutSection } from '@/types/blog';
import { moduleList, moduleText } from '@/utils/siteModules';

interface DetailItem {
  label: string;
  value: string;
}

interface ExperienceItem {
  title: string;
  period: string;
  points: string[];
}

interface ProjectItem {
  name: string;
  summary: string;
  stack: string[];
  highlights: string[];
}

const siteStore = useSiteStore();

onMounted(async () => {
  if (!siteStore.home) {
    await siteStore.bootstrap();
  }
});

const home = computed(() => siteStore.home);

const hasAboutContent = computed(() => {
  if (!home.value) {
    return false;
  }

  return Boolean(home.value.aboutSections.length || home.value.profile.name || home.value.profile.bio);
});

function detailItems(section: AboutSection) {
  return moduleList<DetailItem>(section, 'items');
}

function experiences(section: AboutSection) {
  return moduleList<ExperienceItem>(section, 'items');
}

function badges(section: AboutSection) {
  return moduleList<string>(section, 'items');
}

function projects(section: AboutSection) {
  return moduleList<ProjectItem>(section, 'projects');
}
</script>

<style scoped>
.summary-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 1rem;
  margin-top: 1.6rem;
}

.summary-card,
.module {
  padding: 1.7rem;
}

.summary-card span {
  display: inline-block;
  margin-bottom: 0.8rem;
  color: var(--text-muted);
}

.summary-card strong {
  display: block;
  margin-bottom: 0.65rem;
  font-size: 1.4rem;
}

.summary-card p {
  margin-bottom: 0;
  color: var(--text-secondary);
}

.module {
  margin-top: 1.4rem;
}

.detail-list,
.project-grid {
  display: grid;
  gap: 1rem;
}

.detail-item,
.experience-item,
.project-card {
  padding: 1.3rem;
  border-radius: var(--radius-md);
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(63, 106, 151, 0.1);
}

.detail-item span {
  display: block;
  margin-bottom: 0.5rem;
  color: var(--text-muted);
}

.detail-item strong {
  font-size: 1.1rem;
}

.experience-list {
  display: grid;
  gap: 1rem;
}

.experience-head {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 0.75rem;
}

.experience-head h3 {
  margin-bottom: 0;
}

.experience-head span {
  color: var(--accent-700);
  white-space: nowrap;
}

.experience-item ul,
.project-card ul {
  margin-bottom: 0;
  color: var(--text-secondary);
}

.badge-list,
.stack-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.7rem;
}

.project-card h3 {
  margin-bottom: 0.7rem;
}

.project-summary {
  color: var(--text-secondary);
}

.stack-list {
  margin: 1rem 0;
}

.stack-list span {
  padding: 0.5rem 0.78rem;
  border-radius: 999px;
  background: rgba(63, 106, 151, 0.1);
  color: var(--brand-700);
}

@media (max-width: 900px) {
  .summary-grid {
    grid-template-columns: 1fr;
  }

  .experience-head {
    flex-direction: column;
  }
}
</style>

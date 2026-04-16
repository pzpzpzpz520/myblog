<template>
  <div class="shell">
    <div class="glow glow-left"></div>
    <div class="glow glow-right"></div>

    <header class="header">
      <div class="brand-block">
        <RouterLink class="brand" to="/">{{ brandName }}</RouterLink>
        <p class="brand-subtitle">{{ brandSubtitle }}</p>
      </div>

      <nav class="nav">
        <template v-for="item in navigation" :key="item.path">
          <a
            v-if="item.external"
            :href="item.path"
            class="nav-link"
            target="_blank"
            rel="noreferrer"
          >
            {{ item.label }}
          </a>
          <RouterLink v-else :to="item.path" class="nav-link">{{ item.label }}</RouterLink>
        </template>
      </nav>

      <div class="status surface-card">
        <span class="status-label">Data</span>
        <strong>{{ siteStore.loading ? 'Loading' : 'MySQL Driven' }}</strong>
      </div>
    </header>

    <main class="content">
      <RouterView />
    </main>

    <footer class="footer surface-card">
      <div>
        <p class="eyebrow">FOOTER</p>
        <h3>{{ footer.signature || 'MyBlog' }}</h3>
        <p>{{ footer.slogan || '内容由后端服务聚合，页面使用 Vue 3 + TypeScript 渲染。' }}</p>
      </div>

      <div class="footer-meta">
        <p v-if="footer.email">{{ footer.email }}</p>
        <p v-if="footer.location">{{ footer.location }}</p>
      </div>

      <div class="footer-links">
        <template v-for="item in footerLinks" :key="item.path">
          <a
            v-if="item.external"
            :href="item.path"
            class="footer-link"
            target="_blank"
            rel="noreferrer"
          >
            {{ item.label }}
          </a>
          <RouterLink v-else :to="item.path" class="footer-link">{{ item.label }}</RouterLink>
        </template>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useSiteStore } from '@/stores/site';
import type { FooterInfo, NavigationItem } from '@/types/blog';

const siteStore = useSiteStore();

const defaultNavigation: NavigationItem[] = [
  { label: '首页', path: '/', external: false },
  { label: '博客', path: '/blog', external: false },
  { label: '关于', path: '/about', external: false }
];

const emptyFooter: FooterInfo = {
  slogan: '',
  signature: '',
  email: '',
  location: '',
  links: []
};

onMounted(async () => {
  if (!siteStore.home && !siteStore.loading) {
    await siteStore.bootstrap();
  }
});

const navigation = computed(() =>
  siteStore.home?.navigation?.length ? siteStore.home.navigation : defaultNavigation
);

const footer = computed(() => siteStore.home?.footer ?? emptyFooter);

const footerLinks = computed(() => footer.value.links.length ? footer.value.links : defaultNavigation);

const brandName = computed(() => siteStore.profile?.name || 'MyBlog');

const brandSubtitle = computed(
  () => siteStore.profile?.title || 'Vue 3 + TypeScript front-end · Spring Cloud back-end'
);
</script>

<style scoped>
.shell {
  position: relative;
  min-height: 100vh;
  overflow: hidden;
  color: var(--text-primary);
}

.glow {
  position: absolute;
  border-radius: 999px;
  filter: blur(32px);
  opacity: 0.45;
  pointer-events: none;
}

.glow-left {
  top: 4rem;
  left: -4rem;
  width: 16rem;
  height: 16rem;
  background: rgba(201, 157, 86, 0.2);
}

.glow-right {
  top: 10rem;
  right: -5rem;
  width: 20rem;
  height: 20rem;
  background: rgba(63, 106, 151, 0.18);
}

.header,
.content,
.footer {
  width: min(1160px, calc(100% - 32px));
  margin: 0 auto;
}

.header {
  position: sticky;
  top: 0;
  z-index: 10;
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto auto;
  align-items: center;
  gap: 1rem;
  padding: 1.25rem 0 1rem;
  backdrop-filter: blur(18px);
}

.brand-block {
  min-width: 0;
}

.brand {
  display: inline-block;
  color: var(--brand-700);
  font-size: 1.35rem;
  font-weight: 800;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.brand-subtitle {
  margin: 0.35rem 0 0;
  color: var(--text-muted);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.nav {
  display: flex;
  flex-wrap: wrap;
  gap: 0.6rem;
  padding: 0.35rem;
  border-radius: 999px;
  border: 1px solid rgba(63, 106, 151, 0.12);
  background: rgba(255, 255, 255, 0.62);
}

.nav-link {
  padding: 0.7rem 1rem;
  border-radius: 999px;
  color: var(--text-secondary);
  transition:
    transform 180ms ease,
    background-color 180ms ease,
    color 180ms ease;
}

.nav-link:hover,
.nav-link.router-link-active {
  background: rgba(63, 106, 151, 0.14);
  color: var(--brand-700);
  transform: translateY(-1px);
}

.status {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  padding: 0.9rem 1rem;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.78);
}

.status-label {
  display: inline-flex;
  align-items: center;
  padding: 0.34rem 0.56rem;
  border-radius: 999px;
  background: var(--accent-100);
  color: var(--accent-700);
  font-size: 0.74rem;
  font-weight: 700;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.content {
  padding: 1.1rem 0 4rem;
}

.footer {
  display: grid;
  grid-template-columns: 1.5fr 0.8fr 0.9fr;
  gap: 1.4rem;
  margin-bottom: 2rem;
  padding: 1.8rem;
}

.footer h3 {
  margin-bottom: 0.65rem;
}

.footer p {
  color: var(--text-secondary);
}

.footer-meta p {
  margin-bottom: 0.5rem;
}

.footer-links {
  display: flex;
  flex-wrap: wrap;
  gap: 0.7rem;
  align-content: flex-start;
}

.footer-link {
  display: inline-flex;
  align-items: center;
  padding: 0.72rem 1rem;
  border-radius: 999px;
  border: 1px solid rgba(63, 106, 151, 0.14);
  background: rgba(255, 255, 255, 0.7);
}

@media (max-width: 980px) {
  .header {
    grid-template-columns: 1fr;
  }

  .footer {
    grid-template-columns: 1fr;
  }
}
</style>

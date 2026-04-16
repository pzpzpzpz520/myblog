<template>
  <section class="not-found surface-card">
    <p class="eyebrow">404</p>
    <h1>页面没有找到</h1>
    <p>你访问的路径不存在，或者这个入口还没有接入到当前前台站点。</p>
    <div class="actions">
      <RouterLink class="button-primary" to="/">回到首页</RouterLink>
      <RouterLink class="button-secondary" to="/blog">查看文章</RouterLink>
    </div>
  </section>
</template>

<script setup lang="ts">
</script>

<style scoped>
.not-found {
  display: grid;
  place-items: center;
  min-height: 65vh;
  padding: 2rem;
  text-align: center;
}

.not-found h1 {
  margin-bottom: 0.8rem;
  font-size: clamp(2.5rem, 5vw, 4.6rem);
}

.not-found p:not(.eyebrow) {
  max-width: 34rem;
  color: var(--text-secondary);
}

.actions {
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
  margin-top: 1.2rem;
}
</style>

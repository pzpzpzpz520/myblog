<template>
  <div class="tag-cloud">
    <button
      v-for="tag in tags"
      :key="tag.id"
      type="button"
      class="tag-chip"
      :class="{ active: activeTagId === tag.id }"
      @click="$emit('select', tag.id)"
    >
      <span># {{ tag.name }}</span>
      <strong>{{ tag.articleCount }}</strong>
    </button>
  </div>
</template>

<script setup lang="ts">
import type { Tag } from '@/types/blog';

defineProps<{
  tags: Tag[];
  activeTagId?: number;
}>();

defineEmits<{
  select: [tagId: number];
}>();
</script>

<style scoped>
.tag-cloud {
  display: flex;
  flex-wrap: wrap;
  gap: 0.8rem;
}

.tag-chip {
  display: inline-flex;
  align-items: center;
  gap: 0.55rem;
  padding: 0.78rem 1rem;
  border-radius: 999px;
  background: rgba(201, 157, 86, 0.12);
  color: var(--accent-700);
  cursor: pointer;
  transition:
    transform 180ms ease,
    box-shadow 180ms ease,
    background-color 180ms ease;
}

.tag-chip:hover,
.tag-chip.active {
  transform: translateY(-2px);
  background: rgba(63, 106, 151, 0.12);
  color: var(--brand-700);
  box-shadow: 0 12px 24px rgba(63, 106, 151, 0.12);
}

.tag-chip strong {
  color: inherit;
}
</style>

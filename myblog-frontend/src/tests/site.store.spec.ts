import { beforeEach, describe, expect, it } from 'vitest';
import { createPinia, setActivePinia } from 'pinia';
import { useSiteStore } from '@/stores/site';

describe('useSiteStore', () => {
  beforeEach(() => {
    setActivePinia(createPinia());
  });

  it('initializes with safe defaults', () => {
    const store = useSiteStore();
    expect(store.profile).toBeNull();
    expect(store.categories).toEqual([]);
    expect(store.tags).toEqual([]);
    expect(store.loading).toBe(false);
  });
});

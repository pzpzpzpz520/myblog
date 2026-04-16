import { defineStore } from 'pinia';
import { fetchCategories, fetchHome, fetchProfile, fetchTags } from '@/api/blog';
import type { Category, HomeData, Tag, UserProfile } from '@/types/blog';

interface SiteState {
  profile: UserProfile | null;
  home: HomeData | null;
  categories: Category[];
  tags: Tag[];
  loading: boolean;
}

export const useSiteStore = defineStore('site', {
  state: (): SiteState => ({
    profile: null,
    home: null,
    categories: [],
    tags: [],
    loading: false
  }),
  actions: {
    async bootstrap() {
      if (this.loading) {
        return;
      }

      this.loading = true;

      try {
        const [home, profile, categories, tags] = await Promise.all([
          fetchHome(),
          fetchProfile(),
          fetchCategories(),
          fetchTags()
        ]);

        this.home = home;
        this.profile = profile ?? home.profile;
        this.categories = categories.length ? categories : home.categories;
        this.tags = tags.length ? tags : home.tags;
      } finally {
        this.loading = false;
      }
    }
  }
});

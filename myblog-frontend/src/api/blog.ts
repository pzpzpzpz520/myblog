import http from '@/api/http';
import {
  getFallbackArticles,
  getFallbackCategories,
  getFallbackHome,
  getFallbackProfile,
  getFallbackTags
} from '@/mocks/site';
import type {
  ApiResponse,
  ArticleDetail,
  ArticleQuery,
  ArticleSummary,
  Category,
  HomeData,
  PageResult,
  Tag,
  UserProfile
} from '@/types/blog';

async function withFallback<T>(request: () => Promise<T>, fallback: () => T) {
  try {
    return await request();
  } catch (error) {
    console.error('[myblog] API request failed, using fallback data:', error);
    return fallback();
  }
}

export async function fetchProfile() {
  return withFallback<UserProfile>(
    async () => {
      const { data } = await http.get<ApiResponse<UserProfile>>('/site/profile');
      return data.data;
    },
    getFallbackProfile
  );
}

export async function fetchHome() {
  return withFallback<HomeData>(
    async () => {
      const { data } = await http.get<ApiResponse<HomeData>>('/site/home');
      return data.data;
    },
    getFallbackHome
  );
}

export async function fetchArticles(params: ArticleQuery) {
  return withFallback<PageResult<ArticleSummary>>(
    async () => {
      const { data } = await http.get<ApiResponse<PageResult<ArticleSummary>>>('/articles', { params });
      return data.data;
    },
    () => getFallbackArticles(params)
  );
}

export async function fetchArticleDetail(articleId: string | number) {
  const { data } = await http.get<ApiResponse<ArticleDetail>>(`/articles/${articleId}`);
  return data.data;
}

export async function fetchCategories() {
  return withFallback<Category[]>(
    async () => {
      const { data } = await http.get<ApiResponse<Category[]>>('/categories');
      return data.data;
    },
    getFallbackCategories
  );
}

export async function fetchTags() {
  return withFallback<Tag[]>(
    async () => {
      const { data } = await http.get<ApiResponse<Tag[]>>('/tags');
      return data.data;
    },
    getFallbackTags
  );
}

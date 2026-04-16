import type {
  ArticleQuery,
  FooterInfo,
  HomeData,
  PageResult,
  Tag,
  UserProfile,
  Category,
  ArticleSummary
} from '@/types/blog';

const emptyProfile: UserProfile = {
  name: '',
  title: '',
  university: '',
  major: '',
  location: '',
  email: '',
  bio: '',
  focusAreas: [],
  highlights: []
};

const emptyFooter: FooterInfo = {
  slogan: '',
  signature: '',
  email: '',
  location: '',
  links: []
};

export function getFallbackProfile() {
  return emptyProfile;
}

export function getFallbackHome(): HomeData {
  return {
    profile: emptyProfile,
    latestArticles: [],
    categories: [],
    tags: [],
    articleCount: 0,
    navigation: [],
    footer: emptyFooter,
    homeSections: [],
    aboutSections: []
  };
}

export function getFallbackCategories(): Category[] {
  return [];
}

export function getFallbackTags(): Tag[] {
  return [];
}

export function getFallbackArticles(query: ArticleQuery): PageResult<ArticleSummary> {
  return {
    pageNum: query.pageNum,
    pageSize: query.pageSize,
    total: 0,
    records: []
  };
}

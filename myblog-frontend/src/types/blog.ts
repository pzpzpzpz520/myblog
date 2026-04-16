export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
}

export interface NavigationItem {
  label: string;
  path: string;
  description?: string;
  external?: boolean;
}

export interface FooterInfo {
  slogan: string;
  signature: string;
  email: string;
  location: string;
  links: NavigationItem[];
}

export interface SiteModule {
  id?: number;
  page: string;
  key: string;
  title: string;
  moduleType: string;
  enabled: boolean;
  sortOrder: number;
  payload: Record<string, unknown>;
}

export type HomeSection = SiteModule;
export type AboutSection = SiteModule;

export interface UserProfile {
  name: string;
  title: string;
  university: string;
  major: string;
  location: string;
  email: string;
  bio: string;
  focusAreas: string[];
  highlights: string[];
}

export interface Category {
  id: number;
  name: string;
  articleCount: number;
}

export interface Tag {
  id: number;
  name: string;
  articleCount: number;
}

export interface ArticleSummary {
  id: number;
  title: string;
  summary: string;
  coverImage: string;
  categoryName: string;
  tags: string[];
  publishDate: string;
  readingTime: number;
}

export interface ArticleDetail extends ArticleSummary {
  content: string;
  previousArticleId?: number;
  previousArticleTitle?: string;
  nextArticleId?: number;
  nextArticleTitle?: string;
}

export interface HomeData {
  profile: UserProfile;
  latestArticles: ArticleSummary[];
  categories: Category[];
  tags: Tag[];
  articleCount: number;
  navigation: NavigationItem[];
  footer: FooterInfo;
  homeSections: HomeSection[];
  aboutSections: AboutSection[];
}

export interface PageResult<T> {
  pageNum: number;
  pageSize: number;
  total: number;
  records: T[];
}

export interface ArticleQuery {
  pageNum: number;
  pageSize: number;
  categoryId?: number;
  tagId?: number;
  keyword?: string;
}

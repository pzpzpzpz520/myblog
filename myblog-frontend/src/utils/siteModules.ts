import type { SiteModule } from '@/types/blog';

type ModuleRecord = Record<string, unknown>;

export function moduleText(module: SiteModule, key: string) {
  const value = module.payload[key];
  return typeof value === 'string' ? value : '';
}

export function moduleList<T = ModuleRecord>(module: SiteModule, key: string) {
  const value = module.payload[key];
  return Array.isArray(value) ? (value as T[]) : [];
}

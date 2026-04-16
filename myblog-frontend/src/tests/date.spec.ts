import { describe, expect, it } from 'vitest';
import { formatDate } from '@/utils/date';

describe('formatDate', () => {
  it('formats ISO date string for zh-CN locale', () => {
    expect(formatDate('2026-04-16')).toContain('2026');
  });
});

import request from '@/utils/request'

// 查询标签列表
export function listTag(query) {
  return request({
    url: '/blog-admin/tags',
    method: 'get',
    params: query
  })
}

// 查询标签详情
export function getTag(tagId) {
  return request({
    url: '/blog-admin/tags/' + tagId,
    method: 'get'
  })
}

// 新增标签
export function addTag(data) {
  return request({
    url: '/blog-admin/tags',
    method: 'post',
    data: data
  })
}

// 修改标签
export function updateTag(data) {
  return request({
    url: '/blog-admin/tags',
    method: 'put',
    data: data
  })
}

// 删除标签
export function delTag(tagId) {
  return request({
    url: '/blog-admin/tags/' + tagId,
    method: 'delete'
  })
}

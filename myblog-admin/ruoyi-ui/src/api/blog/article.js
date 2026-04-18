import request from '@/utils/request'

// 查询文章列表
export function listArticle(query) {
  return request({
    url: '/blog-admin/articles',
    method: 'get',
    params: query
  })
}

// 查询文章详情
export function getArticle(articleId) {
  return request({
    url: '/blog-admin/articles/' + articleId,
    method: 'get'
  })
}

// 新增文章
export function addArticle(data) {
  return request({
    url: '/blog-admin/articles',
    method: 'post',
    data: data
  })
}

// 修改文章
export function updateArticle(data) {
  return request({
    url: '/blog-admin/articles',
    method: 'put',
    data: data
  })
}

// 发布/下线文章
export function publishArticle(articleId, status) {
  return request({
    url: '/blog-admin/articles/' + articleId + '/publish',
    method: 'put',
    data: { status }
  })
}

// 删除文章
export function delArticle(articleIds) {
  return request({
    url: '/blog-admin/articles/' + articleIds,
    method: 'delete'
  })
}

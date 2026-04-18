import request from '@/utils/request'

// 查询站点资料
export function getProfile() {
  return request({
    url: '/blog-admin/site/profile',
    method: 'get'
  })
}

// 更新站点资料
export function updateProfile(data) {
  return request({
    url: '/blog-admin/site/profile',
    method: 'put',
    data: data
  })
}

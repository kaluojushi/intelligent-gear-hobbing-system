import request from '@/utils/request'

// 查询加工路径模块列表
export function listPath(query) {
  return request({
    url: '/algorithm/path/list',
    method: 'get',
    params: query
  })
}

// 查询加工路径模块详细
export function getPath(id) {
  return request({
    url: '/algorithm/path/' + id,
    method: 'get'
  })
}

// 新增加工路径模块
export function addPath(data) {
  return request({
    url: '/algorithm/path',
    method: 'post',
    data: data
  })
}

// 修改加工路径模块
export function updatePath(data) {
  return request({
    url: '/algorithm/path',
    method: 'put',
    data: data
  })
}

// 删除加工路径模块
export function delPath(id) {
  return request({
    url: '/algorithm/path/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询加工实例库列表
export function listCases(query) {
  return request({
    url: '/knowledge/cases/list',
    method: 'get',
    params: query
  })
}

// 查询加工实例库详细
export function getCases(id) {
  return request({
    url: '/knowledge/cases/' + id,
    method: 'get'
  })
}

// 新增加工实例库
export function addCases(data) {
  return request({
    url: '/knowledge/cases',
    method: 'post',
    data: data
  })
}

// 修改加工实例库
export function updateCases(data) {
  return request({
    url: '/knowledge/cases',
    method: 'put',
    data: data
  })
}

// 删除加工实例库
export function delCases(id) {
  return request({
    url: '/knowledge/cases/' + id,
    method: 'delete'
  })
}

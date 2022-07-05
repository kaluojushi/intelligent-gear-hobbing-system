import request from '@/utils/request'

// 查询标准参数库列表
export function listStandard(query) {
  return request({
    url: '/knowledge/standard/list',
    method: 'get',
    params: query
  })
}

// 查询标准参数库详细
export function getStandard(id) {
  return request({
    url: '/knowledge/standard/' + id,
    method: 'get'
  })
}

// 新增标准参数库
export function addStandard(data) {
  return request({
    url: '/knowledge/standard',
    method: 'post',
    data: data
  })
}

// 修改标准参数库
export function updateStandard(data) {
  return request({
    url: '/knowledge/standard',
    method: 'put',
    data: data
  })
}

// 删除标准参数库
export function delStandard(id) {
  return request({
    url: '/knowledge/standard/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询数控系统模块列表
export function listCnc(query) {
  return request({
    url: '/algorithm/cnc/list',
    method: 'get',
    params: query
  })
}

// 查询数控系统模块详细
export function getCnc(id) {
  return request({
    url: '/algorithm/cnc/' + id,
    method: 'get'
  })
}

// 新增数控系统模块
export function addCnc(data) {
  return request({
    url: '/algorithm/cnc',
    method: 'post',
    data: data
  })
}

// 修改数控系统模块
export function updateCnc(data) {
  return request({
    url: '/algorithm/cnc',
    method: 'put',
    data: data
  })
}

// 删除数控系统模块
export function delCnc(id) {
  return request({
    url: '/algorithm/cnc/' + id,
    method: 'delete'
  })
}

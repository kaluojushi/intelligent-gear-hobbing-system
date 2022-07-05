import request from '@/utils/request'

// 查询机床库列表
export function listMachine(query) {
  return request({
    url: '/device/machine/list',
    method: 'get',
    params: query
  })
}

// 查询机床库详细
export function getMachine(id) {
  return request({
    url: '/device/machine/' + id,
    method: 'get'
  })
}

// 新增机床库
export function addMachine(data) {
  return request({
    url: '/device/machine',
    method: 'post',
    data: data
  })
}

// 修改机床库
export function updateMachine(data) {
  return request({
    url: '/device/machine',
    method: 'put',
    data: data
  })
}

// 删除机床库
export function delMachine(id) {
  return request({
    url: '/device/machine/' + id,
    method: 'delete'
  })
}

import request from '@/utils/request'

// 查询滚刀库列表
export function listHob(query) {
  return request({
    url: '/device/hob/list',
    method: 'get',
    params: query
  })
}

// 查询滚刀库详细
export function getHob(id) {
  return request({
    url: '/device/hob/' + id,
    method: 'get'
  })
}

// 新增滚刀库
export function addHob(data) {
  return request({
    url: '/device/hob',
    method: 'post',
    data: data
  })
}

// 修改滚刀库
export function updateHob(data) {
  return request({
    url: '/device/hob',
    method: 'put',
    data: data
  })
}

// 删除滚刀库
export function delHob(id) {
  return request({
    url: '/device/hob/' + id,
    method: 'delete'
  })
}

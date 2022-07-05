import request from '@/utils/request'

// 查询文件库列表
export function listFiles(query) {
  return request({
    url: '/programming/files/list',
    method: 'get',
    params: query
  })
}

// 查询文件库详细
export function getFiles(id) {
  return request({
    url: '/programming/files/' + id,
    method: 'get'
  })
}

// 新增文件库
export function addFiles(data) {
  return request({
    url: '/programming/files',
    method: 'post',
    data: data
  })
}

// 修改文件库
export function updateFiles(data) {
  return request({
    url: '/programming/files',
    method: 'put',
    data: data
  })
}

// 删除文件库
export function delFiles(id) {
  return request({
    url: '/programming/files/' + id,
    method: 'delete'
  })
}

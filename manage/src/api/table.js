import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/wxmapi/admin/user/examine/page',
    method: 'get',
    params
  })
}

export function getuserInfo(id) {
  return request({
    url: '/wxmapi/admin/user/info/'+id,
    method: 'get'
  })
}

export function putuserExamine(params) {
  return request({
    url: '/wxmapi/admin/user/examine',
    method: 'put',
    data:params
  })
}

// 用户学历审核列表
export function geteducationList(params) {
  return request({
    url: '/wxmapi/admin/user/education/page',
    method: 'get',
    params
  })
}
export function getuserEducationInfo(id) {
  return request({
    url: '/wxmapi/admin/user/educationExamine/'+id,
    method: 'get'
  })
}
export function putuserEducationExamine(params) {
  return request({
    url: '/wxmapi/admin/user/education/examine',
    method: 'put',
    data:params
  })
}

// 用户身份审核列表
export function getIdentityList(params) {
  return request({
    url: '/wxmapi/admin/user/identity/page',
    method: 'get',
    params
  })
}
export function getuserIdentityInfo(id) {
  return request({
    url: '/wxmapi/admin/user/identityExamine/'+id,
    method: 'get'
  })
}
export function putuserIdentityExamine(params) {
  return request({
    url: '/wxmapi/admin/user/identity/examine',
    method: 'put',
    data:params
  })
}

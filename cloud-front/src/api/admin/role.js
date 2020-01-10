import { get, post, postWithFromDate, put, del } from '@/utils/request'

const prefix = "/api/admin/"

export function getRolePageList(data) {
  return get(prefix + 'role/getRolePageList', data)
}

export function generateRole(data) {
  return post(prefix + 'role/generateRole', data)
}

export function changeRoleById(data) {
  return put(prefix + 'role/changeRoleById', data)
}

export function expurgateRole(data) {
  return del(prefix + 'role/expurgateRole', data)
}

export function expurgateRoleBatch(data) {
  return del(prefix + 'role/expurgateRoleBatch', data)
}

export function getRoleById(data) {
  return get(prefix + 'role/getRoleById?id=' + data, null)
}
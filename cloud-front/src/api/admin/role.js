import { get, post, put, delWithParams } from '@/utils/request'

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

export function expurgateRoleById(data) {
  return delWithParams(prefix + 'role/expurgateRoleById', data)
}

export function expurgateRoleBatch(data) {
  return delWithParams(prefix + 'role/expurgateRoleBatch', data)
}

export function getRoleById(data) {
  return get(prefix + 'role/getRoleById', data)
}

export function getAllRoles(data) {
  return get(prefix + 'role/getAllRoles', data)
}
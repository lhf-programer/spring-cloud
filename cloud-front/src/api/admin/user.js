import { get, post, postWithFromDate, put, del } from '@/utils/request'

const prefix = "/api/admin/"

export function getUserInfoByToken(data) {
  return get(prefix + 'user/getUserInfoByToken', data)
}

export function getAllMenus(data) {
  return get(prefix + 'menu/getAllMenus', data)
}

export function getPageUsers(data) {
  return get(prefix + 'user/list', data)
}

export function addUser(data) {
  return post(prefix + 'user/add', data)
}

export function editUser(data) {
  return put(prefix + 'user/edit', data)
}

export function delUser(data) {
  return del(prefix + 'user/delete', data)
}

export function getUser(data) {
  return get(prefix + 'user/queryById', data)
}
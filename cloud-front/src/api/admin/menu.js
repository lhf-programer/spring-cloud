import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/admin/"

export function getMenuPageList(data) {
  return get(prefix + 'menu/getMenuPageList', data)
}

export function generateMenu(data) {
  return post(prefix + 'menu/generateMenu', data)
}

export function changeMenuById(data) {
  return put(prefix + 'menu/changeMenuById', data)
}

export function expurgateMenuById(data) {
  return delWithParams(prefix + 'menu/expurgateMenuById', data)
}

export function expurgateMenuBatch(data) {
  return delWithParams(prefix + 'menu/expurgateMenuBatch', data)
}

export function getMenuById(data) {
  return get(prefix + 'menu/getMenuById', data)
}

export function getAllMenusByRoleId(data) {
  return get(prefix + 'menu/getAllMenusByRoleId', data)
}

export function getAllMenus(data) {
  return get(prefix + 'menu/getAllMenus', data)
}

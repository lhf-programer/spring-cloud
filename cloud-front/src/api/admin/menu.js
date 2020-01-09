import { get, post, postWithFromDate, put, del } from '@/utils/request'

const prefix = "/api/admin/"

export function getAllMenus(data) {
  return get(prefix + 'menu/getAllMenus', data)
}
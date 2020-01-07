import { get, post, postWithFromDate, put, del } from '@/utils/request'

const prefix = "/api/admin/"

export function getUserInfoByToken(data) {
  return get(prefix + 'user/list', data)
}

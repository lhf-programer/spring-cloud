import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/admin/"

export function changeRoleResourceByRoleId(data) {
  return put(prefix + 'roleResource/changeRoleResourceByRoleId', data)
}
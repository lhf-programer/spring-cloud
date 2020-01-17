import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/admin/"

export function getUserInfoByToken(data) {
  return get(prefix + 'user/getUserInfoByToken', data)
}

export function getUserPageList(data) {
  return get(prefix + 'user/getUserPageList', data)
}

export function generateUser(data) {
  return post(prefix + 'user/generateUser', data)
}

export function changeUserById(data) {
  return put(prefix + 'user/changeUserById', data)
}

export function changePasswordById(data) {
  return put(prefix + 'user/changePasswordById', data)
}

export function expurgateUserById(data) {
  return delWithParams(prefix + 'user/expurgateUserById', data)
}

export function expurgateUserBatch(data) {
  return delWithParams(prefix + 'user/expurgateUserBatch', data)
}

export function getUserById(data) {
  return get(prefix + 'user/getUserById', data)
}

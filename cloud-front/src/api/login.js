import { get, post, postWithFromDate, put, del } from '@/utils/request'

const prefix = "/api/auth/"

export function login(data) {
  return postWithFromDate(prefix + 'oauth/token', data)
}

export function logout(data) {
  return del(prefix + 'oauth/token', data)
}
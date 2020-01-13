import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/admin/"

export function getButtonPageList(data) {
  return get(prefix + 'button/getButtonPageList', data)
}

export function generateButton(data) {
  return post(prefix + 'button/generateButton', data)
}

export function changeButtonById(data) {
  return put(prefix + 'button/changeButtonById', data)
}

export function expurgateButtonById(data) {
  return delWithParams(prefix + 'button/expurgateButtonById', data)
}

export function expurgateButtonBatch(data) {
  return delWithParams(prefix + 'button/expurgateButtonBatch', data)
}

export function getButtonById(data) {
  return get(prefix + 'button/getButtonById', data)
}
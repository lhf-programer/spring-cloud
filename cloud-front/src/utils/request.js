import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import router from '@/router'
import { getToken } from '@/utils/auth'

// 格式: Basic cloud:cloud
const clientToken = "Basic Y2xvdWQ6Y2xvdWQ="

// 创建请求实列
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      // 携带用户 token
      config.headers['Authorization'] = getToken()
    } else if(config.url === '/api/auth/oauth/token') {
      // 携带登录认证客户端 token
      config.headers['Authorization'] = clientToken
    }
    return config
  },
  error => {
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// 返回拦截器
service.interceptors.response.use(
  /**
   * 如果您想获得http信息，如头信息或状态信息
   * 请返回  response => response
   * 通过自定义代码确定请求状态
   * 这里只是一个例子
   * 你也可以通过HTTP状态码来判断状态
   */
  response => {
    /**
     * 下面的注释为通过response自定义code来标示请求状态，当code返回如下情况为权限有问题，登出并返回到登录页
     * 如通过xmlhttprequest 状态码标识 逻辑可写在下面error中
     */
    const res = response.data;
    if (response.status === 401 || res.status === 40101) {
      MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
        confirmButtonText: '重新登录',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('FrontLogOut').then(() => {
          location.reload(); // 为了重新实例化vue-router对象 避免bug
        });
      })
      return Promise.reject('error');
    }
    if (res.status === 40301) {
      Message({
        message: '当前用户无相关操作权限！',
        type: 'error',
        duration: 5 * 1000
      });
      return Promise.reject('error');
    }
    if (res.status === 40001) {
      Message({
        message: '账户或密码错误！',
        type: 'warning'
      });
      return Promise.reject('error');
    }
    if (response.status !== 200 && res.status !== 200) {
      Message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      });
    } else {
      return response.data;
    }
  },
  error => {
    console.log('err', error.response) // for debug
    const { response } = error
    if(!(response.status === 403 || response.status === 401 )){
      Message({
        message: response.data,
        type: 'error',
        duration: 3 * 1000
      })
    }
    if (response.status === 401 || response.status === 403) {
      let redirect = "/";
      if(router.currentRoute.fullPath){
        redirect = decodeURI(router.currentRoute.fullPath)
        let n = router.currentRoute.fullPath.indexOf("redirect=")
        if(n != -1){
          redirect = redirect.substring(n+9,redirect.length)
          redirect = "/" + redirect
        }
      }
      router.replace({
        path: '/409',
        query: { redirect:redirect }
      }) 
    } else {
      return Promise.reject(error)
    }
  }
)

export default service

// get请求
export function get(url, params = {}) {
  return service.get(url, { params })
}

// postJson请求， data是body数据
export function post(url, data = {}) {
  return service.post(url, data, undefined)
}

// post表单请求， params是表单数据
export function postWithFromDate(url, params = {}) {
  return service.post(url, undefined, { params })
}

// 同上
export function put(url, data = {}, params = {}) {
  return service.put(url, data, { params })
}

// 同上
export function del(url, data = {}, params = {}) {
  return service.delete(url, { data, params })
}

export function delWithParams(url, params = {}) {
  return service.delete(url, { params })
}

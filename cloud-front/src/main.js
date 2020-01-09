import Vue from 'vue';
import App from './App';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'assets/custom-theme/index.css'; // 换肤版本element-ui css
import NProgress from 'nprogress'; // Progress 进度条
import 'nprogress/nprogress.css';// Progress 进度条 样式
import 'normalize.css/normalize.css';// normalize.css 样式格式化
import 'assets/iconfont/iconfont'; // iconfont 具体图标见https://github.com/PanJiaChen/vue-element-admin/wiki
import Sticky from 'components/Sticky'; // 粘性header组件
import IconSvg from 'components/Icon-svg';// svg 组件
import vueWaves from './directive/waves';// 水波纹指令
import errLog from 'store/errLog';// error log组件
import './mock/index.js';  // 该项目所有请求使用mockjs模拟
import { getToken } from 'utils/auth';
import 'babel-polyfill';// 支持IE执行原生script

// 全局配置器
Vue.component('Sticky', Sticky);
Vue.component('icon-svg', IconSvg);
Vue.use(ElementUI);
Vue.use(vueWaves);

// 全局路由
const whiteList = ['/login'];// 不重定向白名单
router.beforeEach((to, from, next) => {
  NProgress.start(); // 开启Progress
  if (getToken()) { // 判断是否有token
    if (to.path === '/login') {
      next({ path: '/' });
    } else {
      if (store.getters.menus === undefined) { // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetUserInfoByToken').then(info => { // 拉取user_info
          const menus = store.getters.menus
          store.dispatch('GenerateRoutes', menus).then(() => { // 生成可访问的路由表
            router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
            next({ ...to }); // hack方法 确保addRoutes已完成
          })
        }).catch(() => {
          store.dispatch('FrontLogOut').then(() => {
            next({ path: '/login' });
          })
        })

        next();
      } else {
        next();
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
    } else {
      next('/login'); // 否则全部重定向到登录页
      NProgress.done(); // 在hash模式下 改变手动改变hash 重定向回来 不会触发afterEach 暂时hack方案 ps：history模式下无问题，可删除该行！
    }
  }
});

router.afterEach(() => {
  NProgress.done(); // 结束Progress
});

Vue.config.productionTip = false;

// 生产环境错误日志
if (process.env.NODE_ENV === 'production') {
  Vue.config.errorHandler = function(err, vm) {
    console.log(err, window.location.href);
    errLog.pushLog({
      err,
      url: window.location.href,
      vm
    })
  };
}

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})

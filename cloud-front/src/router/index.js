import Vue from 'vue';
import Router from 'vue-router';
const _import = require('./_import_' + process.env.NODE_ENV);
// in development env not use Lazy Loading,because Lazy Loading large page will cause webpack hot update too slow.so only in production use Lazy Loading

Vue.use(Router);

/* layout */
import Layout from '../views/layout/Layout';

/**
 * icon : the icon show in the sidebar
 * hidden : if `hidden:true` will not show in the sidebar
 * redirect : if `redirect:noredirect` will no redirct in the levelbar
 * noDropdown : if `noDropdown:true` will has no submenu
 * meta : { role: ['admin'] }  will control the page role
 **/
export const constantRouterMap = [{
  path: '/login',
  component: _import('login/index'),
  hidden: true
},
{
  path: '/authredirect',
  component: _import('login/authredirect'),
  hidden: true
},
{
  path: '/404',
  component: _import('error/404'),
  hidden: true
},
{
  path: '/401',
  component: _import('error/401'),
  hidden: true
},
{
  path: '/',
  component: Layout,
  redirect: '/dashboard',
  name: '首页',
  hidden: true,
  children: [{
    path: 'dashboard',
    component: _import('dashboard/index')
  }]
},
{
  path: '/introduction',
  component: Layout,
  redirect: '/introduction/index',
  icon: 'form',
  noDropdown: true,
  children: [{
    path: 'index',
    component: _import('introduction/index'),
    name: '简述'
  }]
}
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({
    y: 0
  }),
  routes: constantRouterMap
});

export const asyncRouterMap = [{
  path: '/admin',
  component: Layout,
  name: '系统管理配置',
  icon: 'setting',
  authority: '/admin',
  children: [{
    path: '/admin/user',
    icon: 'fa-user',
    component: _import('admin/user/index'),
    name: '用户管理',
    authority: '/admin/user'
  },{
    path: '/admin/role',
    icon: 'fa-user',
    component: _import('admin/role/index'),
    name: '角色管理',
    authority: '/admin/role'
  },{
    path: '/admin/setting',
    icon: 'setting',
    component: _import('admin/menu/index'),
    name: '菜单管理',
    authority: '/admin/menu'
  },{
    path: '/admin/button',
    icon: 'setting',
    component: _import('admin/button/index'),
    name: '按钮管理',
    authority: '/admin/button'
  }]
},{
  path: '/system',
  component: Layout,
  name: '系统监控菜单',
  icon: 'setting',
  authority: '/system',
  children: [{
    path: '/system/service',
    icon: 'fa-user',
    component: _import('system/service/index'),
    name: '服务监控',
    authority: '/system/service'
  }]
}];

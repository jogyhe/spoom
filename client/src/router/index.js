import Vue from 'vue'
import Router from 'vue-router'

const _import = require('./_import_' + process.env.NODE_ENV)

export const constantRouterMap = [
    {
        path: '/login',
        component: _import('login/index'),
        hidden: true
    },
    {
        path: '/404',
        component: _import('errorPage/404'),
        hidden: true
    },
    {
        path: '/',
        component: _import('question'),
        hidden: true
    }
]

Vue.use(Router)
export default new Router({
// mode: 'history', //后端支持可开
    scrollBehavior: () => ({
        y: 0
    }),
    routes: constantRouterMap
})

export const asyncRouterMap = [
    {
        path: '/permission',
        component: _import('question'),
        name: '权限测试',
        icon: 'quanxian',
        meta: { role: ['admin'] }
    },

    { path: '*', redirect: '/404', hidden: true }
]

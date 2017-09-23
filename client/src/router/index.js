import Vue from 'vue'
import Router from 'vue-router'

const _import = require('./_import_' + process.env.NODE_ENV)

import Layout from '../views/layout/Layout'

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
        component: Layout,
        hidden: true
    },
    {
        path: '/question',
        component: Layout,
        name: '问题',
        icon: 'doubt'
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
        component: Layout,
        redirect: '/permission/index',
        name: '权限测试',
        icon: 'survey',
        meta: { role: ['admin'] },
        children: [{ path: '/permission/index', component: _import('question'), icon: 'data', name: '权限测试页', meta: { role: ['admin'] } }]
    },
    {
        path: '/test1',
        component: Layout,
        name: '测试1',
        icon: 'template',
        children: [
            { path: '/test1/index', component: _import('question'), icon: 'integral', name: '介绍 ' },
            { path: '/test1/tinymce', component: _import('question'), icon: 'discount', name: '富文本编辑器' },
            { path: '/test1/markdown', component: _import('question'), icon: 'box', name: 'Markdown' }
        ]
    },
    {
        path: '/test2',
        component: Layout,
        name: '测试2',
        icon: 'accuracy'
    },

    { path: '*', redirect: '/404', hidden: true }
]

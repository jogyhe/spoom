import Vue from 'vue'
import Router from 'vue-router'

const _import = require('./_import_' + process.env.NODE_ENV)

/* login */
const Login = _import('login/index')

import Hello from '@/components/Hello'
import Question from '@/views/Question'

export const constantRouterMap = [{
    path: '/login',
    component: Login,
    hidden: true
}, {
    path: '/',
    component: Question,
    hidden: true
}, {
    path: '/hello',
    component: Hello
}]

Vue.use(Router)
export default new Router({
// mode: 'history', //后端支持可开
    scrollBehavior: () => ({
        y: 0
    }),
    routes: constantRouterMap
})

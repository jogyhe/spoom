import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css' // Progress 进度条样式
import { getToken } from '@/utils/auth' // 验权

function hasPermission(roles, permissionRoles) {
    if (roles.indexOf('ADMIN') >= 0) return true // ADMIN权限 直接通过
    if (!permissionRoles) return true
    return permissionRoles.indexOf(roles) >= 0
}

const whiteList = ['/login', '/authredirect']// 不重定向白名单
router.beforeEach((to, from, next) => {
    NProgress.start() // 开启Progress
    if (getToken()) { // 判断是否有token
        if (to.path === '/login') {
            next({ path: '/' })
        } else {
            if (store.getters.roles.length === 0) {
                store.dispatch('GetUserInfo')
                    .then(res => {
                        const roles = res.data.roles
                        store.dispatch('GenerateRouters', roles)
                            .then(() => {
                                router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
                                next({ ...to })
                            })
                    })
                    .catch(() => {
                        store.dispatch('FedLogout')
                            .then(() => {
                                location.reload()
                            })
                    })
            } else {
                if (hasPermission(store.getters.roles, to.meta.role)) {
                    next()
                } else {
                    next({ path: '/404', query: { noGoBack: true } })
                }
            }
        }
    } else {
        if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
            next()
        } else {
            next('/login') // 否则全部重定向到登录页
            NProgress.done() // 在hash模式下 改变手动改变hash 重定向回来 不会触发afterEach 暂时hack方案 ps：history模式下无问题，可删除该行！
        }
    }
})

router.afterEach(() => {
    NProgress.done() // 结束Progress
})

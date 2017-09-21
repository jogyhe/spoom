import fetch from '@/utils/fetch'

export function loginByEmail(email, password) {
    const data = {
        email: email,
        password: password
    }
    return fetch({
        url: '/auth/login',
        method: 'post'
    })
}

export function logout(userId) {
    return fetch({
        url: '/auth/logout',
        method: 'post',
        params: { userId: userId }
    })
}

export function getUserInfo() {
    return fetch({
        url: '/user/info',
        method: 'get'
    })
}

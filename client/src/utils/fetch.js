import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
import { getToken } from './auth'

const fetch = axios.create({
    baseURL: process.env.BASE_API,
    timeout: 5000
})

fetch.interceptors.request.use(config => {
    if (store.getters.token) {
        config.headers['Token'] = getToken()
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

fetch.interceptors.response.use(response => {
    const res = response.data
    if (res == null) {
        return Promise.reject('error')
    }
    /**
     * code为1001,说明token失效或者没有携带token,前端登出
     */
    if (res.code === 1001) {
        store.dispatch('FedLogout')
            .then(() => {
                location.reload()
            })
    }
    return res
}, error => {
    console.log('err' + error) // for debug
    Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
    })
    return Promise.reject(error)
})
export default fetch

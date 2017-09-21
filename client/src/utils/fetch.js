import axios from 'axios'
import { Message } from 'element-ui'
import { getToken, removeToken } from '@/utils/auth'

const service = axios.create({
    baseURL: process.env.BASE_API,
    timeout: 5000
})

service.interceptors.request.use(config => {
    // config.headers['Token'] = getToken()
    const token = getToken()
    if (token) {
        config.headers['Token'] = token
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

service.interceptors.response.use(response => {
    /**
     * code为1001,说明token失效或者没有携带token
     */
    const res = response.data
    if (res == null) {
        return Promise.reject('error')
    }
    if (res.code === 1001) {
        removeToken()
        location.reload() // 如果发现localStorage中的token无效，则删除并跳转到login页面
        return Promise.reject('error')
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
export default service

import axios from 'axios'
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth'

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
    console.log(config)
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

service.interceptors.response.use(response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    const res = response.data
    if (res == null) {
        return Promise.reject('error')
    } else {
        return res
    }
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

import axios from 'axios'
import { Message } from 'element-ui'

const service = axios.create({
    baseURL: process.env.BASE_API,
    timeout: 5000,
    headers: { 'Token': 'UozENBbdrUW96aTPuSrFOPTlISwEDLZlJpnU2laUjzb1lvVaP9jAbezqV+98uZplue2T0v/UNLoe40/HcGuPbg==' }
})

service.interceptors.request.use(config => {
    // config.headers['Token'] = getToken()
    config.headers['Token'] = 'UozENBbdrUW96aTPuSrFOPTlISwEDLZlJpnU2laUjzb1lvVaP9jAbezqV+98uZplue2T0v/UNLoe40/HcGuPbg=='
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
    console.log(res)
    if (res.code === 1) {
        return response.data
    } else {
        return Promise.reject('error')
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

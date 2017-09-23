import axios from 'axios'
import { MessageBox } from 'element-ui'
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
    if (res.code === 1001) { // 需要添加一个确认框，防止所有无权限访问都跳转到登录界面
        MessageBox.confirm('没有访问权限，确认重新登录?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            store.dispatch('FedLogout')
                .then(() => {
                    location.reload()
                })
        })
    }
    return res
}, error => {
    console.log('err' + error) // for debug
    return Promise.reject(error)
})
export default fetch

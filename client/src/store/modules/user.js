import { getToken, removeToken, setToken } from '../../utils/auth'
import { loginByEmail, logout, getUserInfo } from '../../api/login'

const user = {
    state: {
        email: '',
        nickName: '',
        avatar: '',
        gender: '',
        roles: [],
        token: getToken(),
        status: '',
        code: '',
        setting: {}
    },
    mutations: {
        SET_EMAIL: (state, email) => {
            state.email = email
        },
        SET_NICKNAME: (state, nickName) => {
            state.nickName = nickName
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
        },
        SET_GENDER: (state, gender) => {
            state.gender = gender
        },
        SET_ROLES: (state, roles) => {
            state.roles = roles
        },
        SET_TOKEN: (state, token) => {
            state.token = token
        },
        SET_STATUS: (state, status) => {
            state.status = status
        },
        SET_CODE: (state, code) => {
            state.code = code
        },
        SET_SETTING: (state, setting) => {
            state.setting = setting
        }
    },

    actions: {
        // 用户名登录
        LoginByEmail({ commit }, userInfo) {
            const username = userInfo.username.trim()
            return new Promise((resolve, reject) => {
                loginByEmail(username, userInfo.password).then(response => {
                    if (response.code === 1) {
                        setToken(response.data)
                        commit('SET_TOKEN', response.data)
                        resolve()
                    } else {
                        reject('error')
                    }
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 获取用户信息
        GetUserInfo({ commit, state }) {
            return new Promise((resolve, reject) => {
                getUserInfo(state.token).then(response => {
                    const user = response.data
                    commit('SET_EMAIL', user.email)
                    commit('SET_ROLES', user.role)
                    commit('SET_NICKNAME', user.nickName)
                    commit('SET_AVATAR', user.avatar)
                    commit('SET_GENDER', user.gender)
                    resolve(response)
                }).catch(error => {
                    reject(error)
                })
            })
        },

        // 登出
        Logout({ commit, state }) {
            return new Promise((resolve, reject) => {
                logout().then(() => {
                    commit('SET_TOKEN', '')
                    commit('SET_ROLES', [])
                    removeToken()
                    resolve()
                }).catch(error => {
                    reject(error)
                })
            })
        },

        FedLogout({ commit }) {
            return new Promise(resolve => {
                commit('SET_TOKEN', '')
                removeToken()
                resolve()
            })
        }
    }
}

export default user
